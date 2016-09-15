package gameone.entities;

import gameone.entities.projectiles.Projectile;

import java.awt.geom.Rectangle2D;


public abstract class Entity extends Element {

	protected int team;
	public final DefensiveStats defense;
	
	protected boolean dead = false;
	
	protected Entity(int x, int y, int width, int height, int team, DefensiveStats def) {
		super(x, y, width, height);
		this.team = team;
		defense = def;
	}
	
	
	public static class DefensiveStats {

		protected int health, maxHealth,
					  armor, resistance;
		
		/**
		 * @param hp
		 * Health
		 * @param arm
		 * Armor
		 * @param resist
		 * Magic Resistance
		 */
		public DefensiveStats(int hp, int arm, int resist) {
			health = maxHealth = hp;
			armor = arm; resistance = resist;
		}
		
		
		public int getHealth() {
			return health;
		}
		
		public int getMaxHealth() {
			return maxHealth;
		}
		
		public double getHealthPercent() {
			return (double) health/maxHealth;
		}
		
		public int getArmor() {
			return armor;
		}
		
		public int getResistance() {
			return resistance;
		}
		

		protected void damage(int dmg) {
			if(dmg <= 0) return;
			health -= dmg;
			if(health <= 0) health = 0;
		}
		
		protected void heal(int dmg) {
			if(dmg <= 0) return;
			health += dmg;
			if(health > maxHealth)
				health = maxHealth;
		}
		
	}
	
	public void onHit(Projectile p) {
		int dmg = p.getDamage();
		if(dmg <= 0) return;
		int arm = defense.armor - p.getSource().offense.armorPen;
		if(arm > 0) {
			dmg -= arm;
			if(dmg < 1) dmg = 1;
		}
		defense.damage(dmg);
		if(defense.health <= 0) {
			dead = true;
			level.remove(this);
		}
	}
	
	
	public Rectangle2D.Double getHitBox() {
		return getSize();
	}
	
	public int getTeam() {
		return team;
	}
	
	public boolean isDead() {
		return dead;
	}

	public double getSpeedX() {
		return 0;
	}

	public double getSpeedY() {
		return 0;
	}

}
