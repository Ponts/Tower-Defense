package towerdefensegame.objects;

public class Tower {
	private String name;
	private int damage, range, cost, uppgradeMultiplier;
	
	public Tower(String name, int damage, int range, int cost, int uppgradeMultiplier) {
		this.name = name;
		this.damage = damage;
		this.range = range;
		this.cost = cost;
		this.uppgradeMultiplier = uppgradeMultiplier;
	}
	
	private void shoot(){
		// TODO
		//if target within range
		//call target take damage method?
		
	}
	
	private int getCost(){
		return cost;
	}
	
	private void uppgrade(){
		damage *= uppgradeMultiplier;
	}
}
