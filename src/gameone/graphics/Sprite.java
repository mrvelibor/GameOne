package gameone.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Sprite {

	public final int HEIGHT, WIDTH;
	private BufferedImage image;
	private int[] pixels;

	
	/**
	 * Loads a sprite from the given sprite sheet.
	 */
	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		WIDTH = width; HEIGHT = height;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		load(x, y, sheet);
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this(size, size, x, y, sheet);
	}
	
	public Sprite(int size, int color) {
		WIDTH = HEIGHT = size;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		setColor(color);
	}

	private void load(int x, int y, SpriteSheet sheet) {
		int xSize = x*WIDTH,
			ySize = y*HEIGHT;
		for(int j = 0; j < HEIGHT; j++)
			for(int i = 0; i < WIDTH; i++)
				setPixel(sheet.getPixel(i+xSize, j+ySize), i, j);
	}
	
	private void setPixel(int col, int x, int y) {
		pixels[x + y*WIDTH] = col;
	}
	
	private void setColor(int color) {
		for(int i = 0; i < WIDTH*HEIGHT; i++)
			pixels[i] = color;
	}
	
	
	public int getPixel(int x, int y) {
		return pixels[x + y*WIDTH];
	}
	
	public int getPixel(int i) {
		return pixels[i];
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
}
