package towerdefensegame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy {
	private double walkSpeed;
	private int attackDamage;
	private int attackSpeed;
	private int health;
	private int bounty;
	private String special = null;
	
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
		
	}
	
	private void walk(){
		
	}
	
	private void hit(){
		
	}
	
	private void special(){
		
	}
	
	private void bounty(){
		
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
	
}
