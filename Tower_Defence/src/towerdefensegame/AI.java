package towerdefensegame;

import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

public class AI {
	private TiledMap map;
		
	public AI(TiledMap m){
		this.map = m;
	}
	
	public void findNextDirection(int x, int y){
		Image tI = map.getTileImage(x, y, 1);
	}
}
