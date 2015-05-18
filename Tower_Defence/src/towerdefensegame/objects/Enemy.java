package towerdefensegame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

public class Enemy implements Mover{
	private int attackDamage, attackSpeed, health, bounty, stepCounter;
	private String special = null;
	private Image sprite;
	private String name;
	private int i = 0;
	private float X,Y, walkSpeed;
	
	
	public Enemy(String type, float x, float y) throws SlickException{
		this.sprite = new Image("res//" + type + ".png");
		this.X = x;
		this.Y = y;
		this.stepCounter = 0;
		
		if(type == "tank"){
			this.walkSpeed = 0.03f;
			this.attackDamage = 4;
			this.attackSpeed = 1;
			this.health = 700;
			this.bounty = 100;
			//this.special = ...
		}
		else if(type == "small"){
			this.walkSpeed = 2;
			this.attackDamage = 1;
			this.attackSpeed = 2;
			this.health = 150;
			this.bounty = 20;
			//this.special = ...
		}
	}
	

	public void move(Path path)
	{
		try{
			System.out.println("steps taken is" + stepCounter);
			
			this.X += path.getX(stepCounter)* 32 * walkSpeed;
			System.out.println("X = " +  X) ;
			this.Y += path.getY(stepCounter)* 32 * walkSpeed;
			System.out.println("Y = " + Y);
			this.stepCounter++;
		}
		catch(Exception IndexOutOfBoundsException){
			System.out.println("unable to move further steps");
		}
	}
	
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(this.sprite, this.X, this.Y);
	}

	public void takeDamage(int damage){
		health -= damage;
		
	}
	
	///////////// getters and setters /////////////////////
	public int getHealth(){
		return health;
	}
	
	public int getBounty(){
		return bounty;
	}

	public float getX() {
		return this.X;
	}

	public float getY() {
		return this.Y;
	}
	
	public Image getSprite() {
		return this.sprite;
	}
	
	public void setX(float x){
		this.X=x;
	}
	
	public void setY(float y){
		this.Y=y;
	}
}

