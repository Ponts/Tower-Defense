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
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;
import org.newdawn.slick.util.pathfinding.PathFinder;

import towerdefensegame.objects.*;

public class LevelTwo extends BasicGameState {
	private int ID;
	private TiledMap map = null;
	private int x = 0;
	private int y = 0;
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;
	private Player player;
	private PathFinder ph;
	private Path path;
	private boolean[][] tileTypes;

	public LevelTwo(int id) {
		this.ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		map = load();
		System.out.println(map.getHeight());
		System.out.println(map.getWidth());
		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();
		player = new Player();
		LayerBasedMap blockedMap = new LayerBasedMap(map, 1);

		tileTypes = new boolean[map.getWidth()][map.getHeight()];

		// creates a map which gives us information about if each tile is blocked or not.
		for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
				int tileID = map.getTileId(xAxis, yAxis, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if (value == "true")
					tileTypes[xAxis][yAxis] = true;
				else
					tileTypes[xAxis][yAxis] = false;
			}
		}
		// Add enemies
		enemies.add(new Enemy("cactiball", 5 * 32, 0));
		ph = new AStarPathFinder(blockedMap, 1000, false);

		// TODO

		path = ph.findPath(enemies.get(0), 5, 0, 5, 5);

	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.render(0, 0);

		g.drawString(
				"Health " + player.getHealth() + ", Money " + player.getMoney(),
				10, 580);
		g.fillRect(x, y, 32, 32); // Current mouse location

		for (Tower t : towers) {
			g.drawImage(t.getSprite(), t.getX(),
					container.getHeight() - t.getY());
		}
		for (Enemy e : enemies) {
			e.render(container, sbg, g);
			// g.destroy(); // v�rf�r f�rst�r du graphics? :)
		}
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();

		int xPos = Mouse.getX();
		int yPos = Mouse.getY();

		container.setMouseGrabbed(true);
		x = xPos;
		y = container.getHeight() - yPos;

		// go back to level select
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(1);
		}

		// spawn different towers
		// Basic Tower
		if (input.isKeyPressed(Input.KEY_1)
				&& map.getTileId((x + 16) / 32, (y + 16) / 32, 1) != 0) { // Tile
																			// is
																			// a
																			// scenery
																			// tile
			Tower tower = new Tower("basic", xPos, yPos);
			if (player.getMoney() >= tower.getCost()) {
				doTower(tower);
			} else {
				System.out.println("Not enough money");
			}
		}

		/*
		 * if(input.isKeyPressed(input.KEY_2)) { Tower tower = new
		 * Tower("mortar", xPos, yPos); }
		 */

		// Towers act TODO
		/*
		 * for(Tower tower : towers) { tower.act(); }
		 */

		checkTowerRange(container);

		Enemy c;
		for (int i = enemies.size() - 1; i >= 0; i--) {
			c = enemies.get(i);
			if (Mouse.isButtonDown(0))
				c.move(path);
		}
		// Make enemies move TODO
	}

	@Override
	public int getID() {

		return ID;
	}

	private void checkTowerRange(GameContainer container) {
		for (Tower t : towers) {
			Enemy e;
			for (int i = enemies.size() - 1; i >= 0; i--) {
				e = enemies.get(i);
				float X = e.getX();
				float Y = e.getY();
				if (t.getX() - X < t.getRange()
						&& container.getHeight() - t.getY() - Y < t.getRange()) {
					enemies.remove(e);
				}
			}
		}
	}

	private TiledMap load() throws SlickException {
		return new TiledMap("res//Level2.tmx", "res//pictures//tileset.png");
	}

	private void doTower(Tower tower) {
		towers.add(tower);
		player.pay(tower.getCost());
	}
}
