package towerdefensegame.objects;

<<<<<<< HEAD
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy {
	private double walkSpeed;
=======
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

public class Enemy implements Mover{
	private int walkSpeed;
>>>>>>> origin/master
	private int attackDamage;
	private int attackSpeed;
	private int health;
	private int bounty;
	private String special = null;
	private Image sprite;
	private float x,y;
	private String name;
	private int i = 0;
	private int bounty;
	
<<<<<<< HEAD
	private float X;
	private float Y;
	
	private Image sprite;
	
	public Enemy(String type, float x, float y) throws SlickException{
		this.sprite = new Image("res//" + type + ".png");
		this.X = x;
		this.Y = y;
		
		if(type == "tank"){
			this.walkSpeed = 0.03;
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
		
=======
	public Enemy(String name, float xPos, float yPos, int bounty) throws SlickException{
		this.sprite = new Image("res//" + name + ".png");
		this.x=xPos;
		this.y=yPos;
		this.name = name;
		this.bounty = bounty;
	}
	public void initTank(){
		this.walkSpeed = 1;
		this.attackDamage = 2;
		this.attackSpeed = 1;
		this.health = 500;
		//this.special = ...
>>>>>>> origin/master
	}
	
	public Image getSprite(){
		return sprite;
	}
	
	public float getY() {
		return this.y;
	}

	public float getX() {
		return this.x;
	}

	/*public void move(Path path)
	{
		x=path.getStep(i).getX()*32;
		y=path.getStep(i).getY()*32;
		i++;
	}
	*/
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(sprite, x, y);
	}

	public void takeDamage(int damage){
		health = health - damage;
		
	}
	
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

	public void setY(float f, String s) {
		if(s == "d"){
			this.Y += f*this.walkSpeed;
		}
		else if(s == "u"){
			this.Y -= f*this.walkSpeed;
		}
	}

	public void setX(float f, String s) {
		if(s == "r"){
			this.X += f*this.walkSpeed;
		}
		else if(s == "l"){
			this.X -= f*this.walkSpeed;
		}
	}

	public Image getSprite() {
		return this.sprite;
	}
	
	public void setX(float x){
		this.x=x;
	}
	
	public void setY(float y){
		this.y=y;
	}
}

