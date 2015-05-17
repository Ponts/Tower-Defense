package towerdefensegame;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import towerdefensegame.objects.*;

public class LevelTwo extends BasicGameState {
	private int ID;
	private TiledMap map = null;
	private int x = 0;
	private int y = 0;
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;
	private Player player;
	
	
	public LevelTwo(int id){
		this.ID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		map = load();
		towers = new ArrayList<>();
		enemies = new ArrayList<>();
		player = new Player();
		System.out.println(map.getLayerIndex("collision")); 
		System.out.println(map.getLayerIndex("Scenery") );
		System.out.println(map.getLayerIndex("Road"));
		System.out.println(map.getLayerCount());
		System.out.println();
		
		System.out.println(map.getTileId(1,1,2));
		System.out.println(map.getTileId(1,1,0));
		// Add enemies
		
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.render(0, 0);
		g.fillRect(x, y, 32, 32); // Current mouse location
		for(Tower t: towers){
			g.drawImage(t.getSprite(), t.getX(), container.getHeight() - t.getY());
		}
		
	}
	

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		
		container.setMouseGrabbed(true);
		x = xPos;
		y = 600-yPos;
		//go back to level select
		if(input.isKeyPressed(input.KEY_ESCAPE)) {
			sbg.enterState(1);
		}
		//spawn different towers
		//Basic Tower
		if(input.isKeyPressed(input.KEY_1) && map.getTileId((x+16)/32,(y+16)/32,1) != 0) {
			Tower tower = new Tower("basic", xPos, yPos);
			if(player.getMoney() >= tower.getCost()){
			addTower(tower);
			}
			else{
				System.out.println("Not enough money");
			}
		}
		/*if(input.isKeyPressed(input.KEY_2)) {
			Tower tower = new Tower("mortar", xPos, yPos);
		}*/
		
		//Towers act TODO
		/*for(Tower tower : towers) {
			tower.act();
		}*/
		
		
		
		
		//Make enemies move TODO
		
	}

	@Override
	public int getID() {
		
		return ID;
	}

	private TiledMap load() throws SlickException {
		return new TiledMap("res//Level2.tmx", "res//pictures//tileset.png");
	}
	
	private void addTower(Tower tower){
		towers.add(tower);
		player.pay(tower.getCost());
	}
}
