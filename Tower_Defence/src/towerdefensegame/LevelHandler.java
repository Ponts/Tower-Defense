package towerdefensegame;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import towerdefensegame.objects.*;

public class LevelHandler extends BasicGameState implements MusicListener {
	private float mapStartX, mapStartY, mapEndX, mapEndY ;
	private int ID, roundNo, x, y;
	private String mapName;
	
	private TiledMap map;
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;
	private Player player;
	private PathFinder ph;
	private Path path;
	boolean startRound = false;
	private ArrayList<Bullet> bullets;
	private LayerBasedMap blockedMap;
	private Music openingMenuMusic;

	public LevelHandler(int id) {
		this.ID = id;
		switch(id){
			case(2):{
				mapName = "Level1";
				mapStartX = 16 * 32;
				mapStartY = 0;
				mapEndX = 4 * 32;				
				mapEndY = 16 * 32;
				break;
			}
			case(3):{
				mapName = "Level2";
				mapStartX = 5 * 32;
				mapStartY = 0;
				mapEndX = 20 * 32;
				mapEndY = 16 * 32;
				break;
			}
			case(4):{
				mapName = "Level3";
				mapStartX = 0;
				mapStartY = 7 * 32;
				mapEndX = 19 * 32;
				mapEndY = 16 * 32;
				break;
			}
			default:{
				mapName = "Level2";
				mapStartX = 5 * 32;
				mapStartY = 0;
				mapEndX = 20 * 32;
				mapEndY = 16 * 32;
				break;
			}	
		}
	}

	////////////////////// INIT, UPDATE and RENDER methods	/////////////////////////
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		map = load();
		towers = new ArrayList<>();
		enemies = new ArrayList<>();
		player = new Player();
		bullets = new ArrayList<>();
		blockedMap = new LayerBasedMap(map, 2, 32);
		newRound();
		openingMenuMusic = new Music("res//openingMusic.ogg");
		ph = new AStarPathFinder(blockedMap, 1000, false);
		path = ph.findPath(null, (int) mapStartX/32, (int) mapStartY/32, (int) mapEndX/32, (int) mapEndY/32);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		container.setMouseGrabbed(true);

		x = Mouse.getX();
		int yPos = Mouse.getY();
		y = container.getHeight() - yPos;

		if (input.isKeyPressed(Input.KEY_ENTER))
			newRound();
		
		// go back to level select
		if (input.isKeyPressed(Input.KEY_ESCAPE)){
			stopMusic();
			resetLevel(false);
			sbg.enterState(1);
		}
	
		if (input.isKeyPressed(Input.KEY_P)) {
			stopMusic();
			sbg.enterState(1);
		}
		
		// spawn different towers
		checkAndSpawnTower(input, yPos);

		// towers shoot
		towerAct(container);
		
		// Start the round
		if (startRound) 
			startTheRound(sbg, container, delta);

		shotBullet(delta);
	}


	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(0, 0);
		showHealthAndMoney(g);
		g.fillRect(x, y, 32, 32); // Current mouse location

		for (Tower t : towers)
			g.drawImage(t.getSprite(), t.getX(), container.getHeight() - t.getY());

		if (startRound) {
			for (Enemy e : enemies)
				e.render(container, sbg, g);
		}

		for (Bullet b : bullets) 
			b.render(container, sbg, g);
	}

	// ///////////// other methods ////////////////////
	private void checkAndSpawnTower(Input input, float yPos) throws SlickException {
		boolean key1Pressed = input.isKeyPressed(Input.KEY_1);
		boolean key2Pressed = input.isKeyPressed(Input.KEY_2);
		boolean key3Pressed = input.isKeyPressed(Input.KEY_3);
		try{
			int cursorLocationTileId = map.getTileId((x + 16) / 32, (y + 16) / 32, 1);
			
			String whichTower = "";
			if (key1Pressed && cursorLocationTileId != 0) 
				whichTower = "basic";
			
			else if (key2Pressed && cursorLocationTileId != 0) 
				whichTower = "rapid";
			
			else if (key3Pressed && cursorLocationTileId != 0) 
				whichTower = "slow";
			
			if(whichTower != ""){
				Tower tower = new Tower(whichTower, x, yPos);
				if (player.getMoney() >= tower.getCost()) 
					doTower(tower);
				else 
					System.out.println("Not enough money");
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException){
		}
	}

	private void startTheRound(StateBasedGame sbg, GameContainer container,
			int delta) {
		Enemy c;
		
		for (int i = enemies.size() - 1; i >= 0; i--) {
			c = enemies.get(i);
			c.update(delta, path);
			if (	Math.abs(c.getX() - mapEndX) < 10 &&
					Math.abs(c.getY() - mapEndY) < 10 ) {
				enemies.remove(c);
				player.takeDamage(c.getDamage());
				
				if (player.getHealth() <= 0) {
					sbg.enterState(0);
					container.setMouseGrabbed(false);
					System.out.println("Game Over");
				}
			}
		}
	}

	private void resetLevel(boolean gameOver) throws SlickException {
		player.setHealth(100);
		player.setMoney(500);
		enemies.clear();
		towers.clear();
		bullets.clear();
		roundNo = 0;
		openingMenuMusic.stop();
		if (gameOver) {
			gameOverMusic();
		}
	}
	
	private void gameOverMusic() throws SlickException {
		Music end = new Music("res//gameover.ogg");
		end.play();
	}
	
	private void stopMusic() {
		openingMenuMusic.play();
		openingMenuMusic.stop();
	}
	
	private void towerAct(GameContainer container) throws SlickException{
		for(Tower t:towers){
			Enemy e;
			t.reloading();
			for(int i = enemies.size() -1; i >= 0; i-- ){
				e = enemies.get(i);
				
				if(t.shootReady(e, container)){
					float enemyX = e.getX();
					float enemyY = e.getY();	
					bullets.add(new Bullet(t.getX(), container.getHeight() - t.getY(), enemyX, enemyY, t.getBulletName()));
					t.reload();
					e.takeDamage(t.getDamage());
					
					if(!e.isAlive()){
						player.getMoney(e.getBounty());
						enemies.remove(e);
					}
				}
			}
		}
	}
	
	public void shotBullet(int delta) {
		Bullet b;
		for (int i = bullets.size() - 1; i >= 0; i--) {
			b = bullets.get(i);
			b.update(delta);
			
			if (b.reached()) 
				bullets.remove(b);
		}
	}

	private void doTower(Tower tower) {
		if (tower != null)
			towers.add(tower);
		player.pay(tower.getCost());
	}

	private void newRound() throws SlickException {
		for (int i = 0; i < roundNo; i++) 
			if(mapName == "Level1")
				enemies.add(new Enemy("cactiball", mapStartX, mapStartY - (i * 64), 10));
			else if(mapName == "Level2")
				enemies.add(new Enemy("cactiball", mapStartX, mapStartY - (i * 64), 10));
			else if(mapName == "Level3")
				enemies.add(new Enemy("cactiball", mapStartX, mapStartY - (i * 64), 10));
		
		
		roundNo++;
		startRound = true;
	}

	private void showHealthAndMoney(Graphics g) {
		g.drawString("Health " + player.getHealth() + ", Money " + player.getMoney(), 10, 580);
	}

	private TiledMap load() throws SlickException {
		return new TiledMap("res//"+ mapName + ".tmx", "res//" + mapName + "//pictures//tileset.png");
	}
	
	public int getID() {
		return ID;
	}

	@Override
	public void musicEnded(Music arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {
		// TODO Auto-generated method stub

	}

}
