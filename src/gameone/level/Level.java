package gameone.level;

import gameone.entities.Entity;
import gameone.entities.Element;
import gameone.entities.mobs.Mob;
import gameone.entities.projectiles.Projectile;
import gameone.graphics.Screen;
import gameone.level.tiles.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Level {
	
	public static final Level SPAWN = new Level("spawn");
	
	
	public final int WIDTH, HEIGHT, 
					 X_MIN, Y_MIN, X_MAX, Y_MAX;
	protected int[] tiles;

	public List<Entity> elements = new ArrayList<Entity>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	
	private static Comparator<Entity> elemComp = new Comparator<Entity>() {
        @Override
		public int compare(Entity a, Entity b) {
            return a.getBotY() - b.getBotY();
        }
	};
	
	
	protected Level(int width, int height) {
		WIDTH = width; HEIGHT = height;
		tiles = new int[WIDTH*HEIGHT];
		X_MIN = -Tile.SIZE*2; Y_MIN = X_MIN;
		X_MAX = (WIDTH+2) * Tile.SIZE; Y_MAX = (HEIGHT+2) * Tile.SIZE;
	}
	
	public Level(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/maps/" + path + ".png"));
			//image = ImageIO.read(new File("res/maps/" + path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot open file\nres/maps/" + path + ".png", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(12);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot open file\nres/maps/" + path + ".png", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(12);
		}
		
		WIDTH = image.getWidth(); HEIGHT = image.getHeight();
		tiles = new int[WIDTH*HEIGHT];
		X_MIN = -Tile.SIZE*2; Y_MIN = X_MIN;
		X_MAX = (WIDTH+2) * Tile.SIZE; Y_MAX = (HEIGHT+2) * Tile.SIZE;
		
		loadLevel(image);
	}
	
	
	private void loadLevel(BufferedImage image) {
		image.getRGB(0, 0, WIDTH, HEIGHT, tiles, 0, WIDTH);
		
		for(int i = 0; i < tiles.length; i++) {
			switch(tiles[i])
			{
			case GrassTile.MAP_COLOR:
				setTile(i, GrassTile.LEVEL_CODE);
				break;
			case WaterTile.MAP_COLOR:
				setTile(i, WaterTile.LEVEL_CODE);
				break;
			case RockTile.MAP_COLOR:
				setTile(i, RockTile.LEVEL_CODE);
				break;
			case PlankTile.MAP_COLOR:
				setTile(i, PlankTile.LEVEL_CODE);
				break;
			default:
				setTile(i, VoidTile.LEVEL_CODE);
			}
		}
	}

	protected void setTile(int i, int tile) {
		tiles[i] = tile;
	}

	protected void setTile(int x, int y, int tile) {
		tiles[x + y*WIDTH] = tile;
	}


	public void add(Entity e) {
		elements.add(e);
	}
	
	public void remove(Entity e) {
		elements.remove(e);
	}
	
	public void add(Projectile p) {
		projectiles.add(p);
	}
	
	public void remove(Projectile p) {
		projectiles.remove(p);
	}
	
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return Tile.empty;
		switch(tiles[x + y*WIDTH])
		{
		case GrassTile.LEVEL_CODE:
			return Tile.grass;
		case WaterTile.LEVEL_CODE:
			return Tile.water;
		case RockTile.LEVEL_CODE:
			return Tile.rock;
		case PlankTile.LEVEL_CODE:
			return Tile.plank;
		default:
			return Tile.empty;
		}
	}
	
	
	public double checkXCollision(Element e, double move, double dest) {
		if(Math.abs(move) > Math.abs(dest)) move = dest;
		Rectangle2D.Double r = e.getBounds();
		int x0 = (int) (r.getX()+move)/Tile.SIZE,
			x1 = (int) (r.getMaxX()+move)/Tile.SIZE,
			y0 = (int) r.getY()/Tile.SIZE,
			y1 = (int) r.getMaxY()/Tile.SIZE;
		
		if(move < 0) {
			double m = (x0+1)*Tile.SIZE - r.getX();
			if(getTile(x0, y0).isCollidable())
				if(move < m) move = m;
			if(getTile(x0, y1).isCollidable())
				if(move < m) move = m;
		}
		else {
			double m = x1*Tile.SIZE - r.getMaxX() - Mob.SPEED_ZERO;
			if(getTile(x1, y0).isCollidable())
				if(move > m) move = m;
			if(getTile(x1, y1).isCollidable())
				if(move > m) move = m;
		}
		r.x += move;
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i) == e) continue;
			if(elements.get(i).getBounds().intersects(r)) return 0;
		}
		return move;
	}
	
	public double checkYCollision(Element e, double move, double dest) {
		if(Math.abs(move) > Math.abs(dest)) move = dest;
		Rectangle2D.Double r = e.getBounds();
		int x0 = (int) r.getX()/Tile.SIZE,
			x1 = (int) r.getMaxX()/Tile.SIZE,
			y0 = (int) (r.getY()+move)/Tile.SIZE,
			y1 = (int) (r.getMaxY()+move)/Tile.SIZE;
		
		if(move < 0) {
			double m = (y0+1)*Tile.SIZE - r.getY();
			if(getTile(x0, y0).isCollidable())
				if(move < m) move = m;
			if(getTile(x1, y0).isCollidable())
				if(move < m) move = m;
		}
		else {
			double m = y1*Tile.SIZE - r.getMaxY() - Mob.SPEED_ZERO;
			if(getTile(x0, y1).isCollidable())
				if(move > m) move = m;
			if(getTile(x1, y1).isCollidable())
				if(move > m) move = m;
		}
		r.y += move;
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i) == e) continue;
			if(elements.get(i).getBounds().intersects(r)) return 0;
		}
		return move;
	}
	
	public boolean checkHits(Projectile p) {
		Rectangle2D.Double r = p.getBounds();
		for(int i = 0; i < elements.size(); i++) {
			Entity e = elements.get(i);
			if(e == p.getSource() || e.getTeam() == p.getTeam()) continue;
			if(e.getHitBox().intersects(r)) {
				e.onHit(p);
				return true;
			}
		}
		return false;
	}

	
	@SuppressWarnings("unused")
	private void time() {
		
	}
	
	public void update() {
		for(int i = 0; i < elements.size(); i++) {
			Entity e = elements.get(i);
			if(e.onScreen()) e.update();
		}
		
		for(int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).update();
		
		Collections.sort(elements, elemComp);
	}

	
	public void renderBackground(int xScroll, int yScroll) {
		xScroll -= Screen.getWidth()/2;
		yScroll -= Screen.getHeight()/2;
		if(xScroll < X_MIN) xScroll = X_MIN;
		if(xScroll > X_MAX - Screen.getWidth()) xScroll = X_MAX - Screen.getWidth();
		if(yScroll < Y_MIN) yScroll = Y_MIN;
		if(yScroll > Y_MAX - Screen.getHeight()) yScroll = Y_MAX - Screen.getHeight();
		Screen.setOffsets(xScroll, yScroll);
		
		int x0 = xScroll / Tile.SIZE,
			x1 = (xScroll + Screen.getWidth()) / Tile.SIZE,
			y0 = yScroll / Tile.SIZE,
			y1 = (yScroll + Screen.getHeight()) / Tile.SIZE;
			
		for(int y = y0; y <= y1; y++)
			for(int x = x0; x <= x1; x++)
				getTile(x, y).render(x, y);
	}
	
	public void renderForeground() {
		for(Entity e : elements) e.render();
		for(Projectile p : projectiles) p.render();
	}
	
}
