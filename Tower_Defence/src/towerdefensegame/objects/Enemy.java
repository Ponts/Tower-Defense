package towerdefensegame.objects;

import java.util.ArrayList;

import org.newdawn.slick.util.pathfinding.PathFinder;

public class Enemy {
	private int walkSpeed;
	private int attackDamage;
	private int attackSpeed;
	private int health;
	private String special = null;
	private PathFinder pf;
	
	public Enemy(){
		
	}
	public void initTank(){
		this.walkSpeed = 1;
		this.attackDamage = 2;
		this.attackSpeed = 1;
		this.health = 500;
		//this.special = ...
	}
	
	public void draw(){
		g.drawCircle();
	}
	
	private void hit(){
		
	}
	
	private void special(){
		
	}
	
	private void bounty(){
		
	}
	
}
