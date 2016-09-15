package gameone.entities.projectiles;

import gameone.GameOne;
import gameone.entities.Active;

public abstract class Rocket extends Projectile {

	protected double acceleration, accelerationX, accelerationY;

	
	protected Rocket(Active src, double a, int width, int height, int dist, double spd, double accel) {
		super(src, a, width, height, dist, spd);
		acceleration = accel / GameOne.UPS;
		accelerationX = acceleration * Math.cos(angle);
		accelerationY = acceleration * Math.sin(angle);		
	}
	
	protected Rocket(Active src, int x, int y, int width, int height, int dist, double spd, double accel) {
		this(src, Math.atan2(y-src.getCenterY(), x-src.getCenterX()), width, height, dist, spd, accel);
	}
	
	
	@Override
	public void move() {
		super.move();
		speed += acceleration;
		speedX += accelerationX;
		speedY += accelerationY;
	}

}
