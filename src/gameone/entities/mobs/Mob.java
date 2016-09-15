package gameone.entities.mobs;

import gameone.entities.Active;
import gameone.entities.Entity;
import gameone.level.tiles.Tile;


public abstract class Mob extends Active {

	public static final int SIZE = Tile.SIZE;
	
	public static final double DIAGONAL_SPEED = 1/Math.sqrt(2),
							   SPEED_ZERO = 0.01;


	protected Entity destination;
	protected double destX = -1, destY = -1,
					 speed, treshold,
					 angle, speedX, speedY;
	
	
	protected Mob(int x, int y, int width, int height, int team, DefensiveStats def, OffensiveStats off, double speed) {
		super(x*Tile.SIZE, y*Tile.SIZE, width, height, team, def, off);
		this.speed = speed;
		treshold = speed/2;
		level.add(this);
	}


	public boolean hasDestination() {
		return destX >= 0 && destY >= 0 && destX < xEnd && destY < yEnd;
	}
	
	public int getDestinationX() {
		return (int) destX;
	}
	
	public int getDestinationY() {
		return (int) destY;
	}
	
	public Entity getDestination() {
		return destination;
	}
	
	@Override
	public double getSpeedX() {
		return speedX;
	}
	
	@Override
	public double getSpeedY() {
		return speedY;
	}
	
	
	public void setDestination(Entity e) {
		destination = e;
	}
	
	public void setDestination(double x, double y) {
		destX = x; destY = y;
		angle = Math.atan2(y-getCenterY(), x-getCenterX());
		speedX = Math.cos(angle)*speed; speedY = Math.sin(angle)*speed;
		direction = Direction.get(angle);
	}
	
	public void clearDestination() {
		destination = null;
		destX = destY = -1;
	}
	
	protected void updateDestination() {
		if(destination != null) {
			if(destination.isDead()) clearDestination();
			else setDestination(destination.getCenterX(), destination.getCenterY());
		}
		else setDestination(destX, destY);
	}
	
	
	protected void startMoving() {
		if(++anim < 20) anim = 20;
	}
	
	protected void stopMoving() {
		if(destination != null) return;
		anim = 0;
		destX = destY = -1;
		speedX = speedY = 0;
	}
	
	
	/**
	 * Moves in the given direction.
	 * @param d
	 */
	public void move(Direction d) {
		if(!d.hasDirection()) {
			stopMoving();
			return;
		}
		direction = d;
		startMoving();
		
		speedX = 0; speedY = 0;
		if(d.X != 0) speedX = level.checkXCollision(this, d.X*speed, speed);
		if(d.Y != 0) speedY = level.checkYCollision(this, d.Y*speed, speed);
		if(d.X != 0 && d.Y != 0) {
			speedX *= DIAGONAL_SPEED;
			speedY *= DIAGONAL_SPEED;
		}
		
		posX += speedX; posY += speedY;
	}
	
	protected void move() {
		boolean updateDest = false;
		double distanX = destX-getCenterX(), distanY = destY-getCenterY();
		if(distanX == 0 && distanY == 0) {
			stopMoving();
			return;
		}
		
		startMoving();
		
		if(Math.abs(speedX) > Math.abs(distanX)) speedX = distanX;
		if(Math.abs(speedY) > Math.abs(distanY)) speedY = distanY;
		double moveX = 0, moveY = 0;
		
		moveX = level.checkXCollision(this, speedX, distanX);
		if(Math.abs(moveX) <= SPEED_ZERO) {
			moveY = level.checkYCollision(this, Math.signum(distanY) * speed, distanY);
			if(destination == null) updateDest = true;
		}
		else moveY = level.checkYCollision(this, speedY, distanY);
		if(Math.abs(moveY) <= SPEED_ZERO) {
			moveX = level.checkXCollision(this, Math.signum(distanX) * speed, distanX);
			if(destination == null) updateDest = true;
		}
		
		posX += moveX; posY += moveY;
		if((speedX == 0 || Math.abs(moveX) < Math.abs(speedX)) && (speedY == 0 || Math.abs(moveY) < Math.abs(speedY))) stopMoving();
		else if(updateDest) updateDestination();
	}
	
	
	@Override
	public void update() {
		super.update();
		if(destination != null)
			updateDestination();
		if(hasDestination())
			move();
		else stopMoving();
	}
	
}
