package gameone.graphics;

import gameone.entities.mobs.player.Player;
import gameone.entities.projectiles.Projectile;
import gameone.level.tiles.Tile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class SpriteSheet {

	private static String folder;
	public static SpriteSheet tiles, projectiles, player, enemy,
							  mage;
	static {
		setTextures("low");
	}
	
	
	private final int WIDTH, HEIGHT;
	private int[] pixels;
	
	
	public SpriteSheet(String name, int width, int height) {
		WIDTH = width; HEIGHT = height;
		pixels = new int[WIDTH*HEIGHT];
		load("/textures/" + folder + '/' + name);
	}
	
	public SpriteSheet(String path, int size) {
		this(path, size, size);
	}
	
	
	private void load(String path) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(path));
			//BufferedImage image = ImageIO.read(new File("res" + path));
			int w = image.getWidth(),
				h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot open file\n" + path, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(11);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot open file\nres" + path, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(11);
		}
	}

	
	public static void setTextures(String s) {
		folder = s;
		tiles = new SpriteSheet("tileSheet.png", 16 * Tile.SIZE);
		projectiles = new SpriteSheet("projectileSheet.png", 16 * Projectile.SIZE);
		player = new SpriteSheet("playerSheet.png", 4 * Player.WIDTH, 4 * Player.HEIGHT);
		mage = new SpriteSheet("mage.png", 4 * Player.WIDTH, 4 * Player.HEIGHT);
		enemy = new SpriteSheet("enemySheet.png", 4 * Player.WIDTH, 4 * Player.HEIGHT);
	}
	
	public static String getTextures() {
		return folder;
	}
	
	
	public int getPixel(int x, int y) {
		return pixels[x + y*WIDTH];
	}
	
	public int getPixel(int i) {
		return pixels[i];
	}
}
