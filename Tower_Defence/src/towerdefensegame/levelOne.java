package towerdefensegame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class levelOne extends BasicGameState {
	private int ID; 
	Image theMap = null;
	
	
	public levelOne(int levelOne) {
		this.ID = levelOne; 
	}
	
	
	
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		theMap = new Image("res//MAP1.png");
	}


	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(theMap, 0, 0);

	}
	
	public void update(GameContainer container, StateBasedGame sbg, int g) throws SlickException {
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		
		Input input = container.getInput();
		boolean rightMousePressed = input.isMousePressed(input.MOUSE_RIGHT_BUTTON);
		boolean leftMousePressed = input.isMousePressed(input.MOUSE_LEFT_BUTTON);
		
		if(rightMousePressed){
			// user wants to buy a tower or upgrade existing: open buy menu if so and wait for input?
		}
		else if(leftMousePressed){
			// user might want to exit to main menu or if buy menu is up perhaps buy/upgrade a tower
		}
		

	}



	@Override
	public int getID() {
		return this.ID;
	}
}