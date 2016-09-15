package gameone.entities;

import java.awt.geom.Rectangle2D;

import gameone.graphics.Screen;
import gameone.graphics.Sprite;
import gameone.level.Level;
import gameone.level.tiles.Tile;

public abstract class Element {
	
	
	public enum Direction {
		
		NORTH(0, -1, -Math.PI*5/8), NORTHEAST(1, -1, -Math.PI*3/8),  EAST(1, 0, -Math.PI/8),
		SOUTHEAST(1, 1, Math.PI/8), SOUTH(0, 1, Math.PI*3/8),        SOUTHWEST(-1, 1, Math.PI*5/8),
		WEST(-1, 0, Math.PI*7/8),   NORTHWEST(-1, -1, -Math.PI*7/8), NOWHERE(0, 0, 0);
		
		
		public final int X, Y;
		public final double DEG;
		
		private Direction(int x, int y, double deg) {
			X = x; Y = y;
			DEG = deg;
		}
		
		
		public static Direction get(int x, int y) {
			if(x < 0  && y < 0)	 return NORTHWEST;
			if(x == 0 && y < 0)  return NORTH;
			if(x > 0  && y < 0)  return NORTHEAST;
			if(x > 0  && y == 0) return EAST;
			if(x > 0  && y > 0)  return SOUTHEAST;
			if(x == 0 && y > 0)  return SOUTH;
			if(x < 0  && y > 0)  return SOUTHWEST;
			if(x < 0  && y == 0) return WEST;
			return NOWHERE;
		}
		
		public static Direction get(double a) {
			if(a >= NORTHWEST.DEG && a <= NORTH.DEG)    return NORTHWEST;
			if(a > NORTHWEST.DEG  && a < NORTHEAST.DEG) return NORTH;
			if(a >= NORTH.DEG 	  && a <= EAST.DEG) 	return NORTHEAST;
			if(a > NORTHEAST.DEG  && a < SOUTHEAST.DEG) return EAST;
			if(a >= EAST.DEG 	  && a <= SOUTH.DEG)	return SOUTHEAST;
			if(a > SOUTHEAST.DEG  && a < SOUTHWEST.DEG) return SOUTH;
			if(a >= SOUTH.DEG 	  && a <= WEST.DEG)	    return SOUTHWEST;
			if(a > WEST.DEG 	  || a < NORTHWEST.DEG) return WEST;
			return NOWHERE;
		}
		
		
		public int getInt() {
			if(this != NOWHERE) return ordinal();
			return -1;
		}
		
		public boolean hasDirection() {
			return this != NOWHERE;
		}
		
	}
					  

	protected static Level level;
	protected static int xEnd, yEnd;
	
	protected double posX, posY;
	protected int width, height, anim = 0;
	protected Direction direction = Direction.NOWHERE;
	
	
	protected Element(int x, int y, int width, int height) {
		posX = x; posY = y;
		this.width = width; this.height = height;
	}
	
	
	public static void init(Level level) {
		Element.level = level;
		xEnd = level.WIDTH*Tile.SIZE;
		yEnd = level.HEIGHT*Tile.SIZE;
	}
	
	
	public int getLeftX() {
		return (int) posX;
	}
	
	public int getTopY() {
		return (int) posY;
	}
	
	public int getCenterX() {
		return (int) (posX + width/2);
	}
	
	public int getCenterY() {
		return (int) (posY + height/2);
	}
	
	public int getRightX() {
		return (int) posX+width;
	}
	
	public int getBotY() {
		return (int) posY+height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public final Rectangle2D.Double getSize() {
		return new Rectangle2D.Double(posX, posY, width, height);
	}

	public Rectangle2D.Double getBounds() {
		return getSize();
	}
	
	
	public boolean onScreen() {
		int xMin = Screen.getX(),
			xMax = xMin + Screen.getWidth(),
			yMin = Screen.getY(),
			yMax = yMin + Screen.getHeight();
		return posX+width > xMin && posX < xMax && posY+height > yMin && posY < yMax;
	}

	
	public abstract Sprite getSprite();
	
	
	public abstract void update();
	
	public void render() {
		Screen.renderEntity((int)posX, (int)posY, this);
	}
	
}
