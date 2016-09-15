package gameone.entities.projectiles;

import gameone.entities.Active;
import gameone.entities.Element;
import gameone.level.tiles.Tile;


public abstract class Projectile extends Element {
	
	public static final int SIZE = Tile.SIZE/2;

	protected Active source;

	protected double angle, speed,
					 speedX, speedY;
	protected int damage, distance;
	

	protected Projectile(Active src, double a, int width, int height, int dist, double spd) {
		super(src.getCenterX()-width/2, src.getCenterY()-height/2, width, height);
		
		source = src;
		angle = a;
		distance = dist * Tile.SIZE;
		speed = spd;
		
		speedX = Math.cos(angle) * speed;
		speedY = Math.sin(angle) * speed;
		direction = Direction.get(angle);
		
		damage = source.offense.getDamage();
		
		level.add(this);
	}
	
	protected Projectile(Active src, int x, int y, int width, int height, int dist, double spd) {
		this(src, Math.atan2(y-src.getCenterY(), x-src.getCenterX()), width, height, dist, spd);
	}

	
	public Active getSource() {
		return source;
	}
	
	public int getTeam() {
		return source.getTeam();
	}
	
	public int getDamage() {
		return damage;
	}
	
	
	public void move() {
		posX += speedX; posY += speedY;
		distance -= speed;
		anim++;
	}

	
	@Override
	public void update() {
		if(distance < 0) level.remove(this);
		move();
		if(level.checkHits(this)) level.remove(this);
	}

}
