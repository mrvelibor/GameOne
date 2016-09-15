package gameone.entities;

import gameone.GameOne;

public abstract class Active extends Entity {

	protected int team;
	public final OffensiveStats offense;
	
	protected Entity target;
	protected int tarX = -1, tarY = -1;
	protected double delay;
	private long lastShot = 0;
	
	
	protected Active(int x, int y, int width, int height, int team, DefensiveStats def, OffensiveStats off) {
		super(x, y, width, height, team, def);
		offense = off;
		this.team = team;
		delay = GameOne.UPS/offense.attackSpeed;
	}
	
	
	public static class OffensiveStats {

		protected int damage, armorPen;
		protected double attackSpeed, projectileSpeed;
		
		/**
		 * @param dmg
		 * Projectile Damage
		 * @param arPen
		 * Armor Penetration
		 * @param attSpd
		 * Attack Speed
		 * @param projSpd
		 * Projectile Speed
		 */
		public OffensiveStats(int dmg, int arPen, double attSpd, double projSpd) {
			damage = dmg; armorPen = arPen;
			attackSpeed = attSpd; projectileSpeed = projSpd;
		}
		
		
		public int getDamage() {
			return damage;
		}
		
		public int getArmorPen() {
			return armorPen;
		}
		
		public double getAttSpeed() {
			return attackSpeed;
		}
		
		public double getProjectSpeed() {
			return projectileSpeed;
		}
		
	}

	
	@Override
	public int getTeam() {
		return team;
	}
	
	public boolean hasTarget() {
		return tarX >= 0 && tarY >= 0 && tarX < xEnd && tarY < yEnd;
	}
	
	public int getTargetX() {
		return tarX;
	}
	
	public int getTargetY() {
		return tarY;
	}
	
	public Entity getTarget() {
		return target;
	}
	
	
	public void setTarget(Entity e) {
		target = e;
	}
	
	public void setTarget(int x, int y) {
		target = null;
		tarX = x; tarY = y;
	}
	
	public void clearTarget() {
		target = null;
		tarX = tarY = -1;
	}
	
	protected void updateTarget() {
		if(target.isDead()) clearTarget();
		else {
			tarX = target.getCenterX();
			tarY = target.getCenterY();
		}
	}

	
	/**
	 * Attacks in the direction of the given x and y.
	 * @param x
	 * @param y
	 */
	protected void attack(int x, int y) {
		if(GameOne.getUpdates()-lastShot > delay) {
			fire(x, y);
			lastShot = GameOne.getUpdates();
		}
	}
	
	protected void attack() {
		if(GameOne.getUpdates()-lastShot > delay) {
			if(target != null) {
				int distanX = getCenterX()-tarX, distanY = getCenterY()-tarY;
				double angle = Math.atan2(distanY, distanX);
				tarX += target.getSpeedX() * distanX/(offense.projectileSpeed * Math.cos(angle));
				tarY += target.getSpeedY() * distanY/(offense.projectileSpeed * Math.sin(angle));
			}
			fire(tarX, tarY);
			lastShot = GameOne.getUpdates();
		}
	}
	
	protected abstract void fire(int x, int y);
	
	
	@Override
	public void update() {
		if(target != null)
			updateTarget();
		if(hasTarget())
			attack();
	}

}