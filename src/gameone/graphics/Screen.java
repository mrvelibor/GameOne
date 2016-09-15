package gameone.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import gameone.entities.Element;
import gameone.level.tiles.Tile;

public class Screen {
	
	private static int width, height;
	private static int xOffset, yOffset;
	private static BufferedImage image;
	private static int[] pixels;
	private static Graphics graphics;
		
	
	public Screen(int w, int h) {
		width = w; height = h;
		pixels = new int[width*height];
	}
	
	public Screen(BufferedImage img) {
		image = img;
		width = image.getWidth(); height = image.getHeight();
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		graphics = image.getGraphics();
	}
	
	public static int getPixel(int x, int y) {
		return pixels[x + y*width];
	}
	
	public static int getPixel(int i) {
		return pixels[i];
	}
	
	public static void setPixel(int col, int x, int y) {
		pixels[x + y*width] = col;
	}
	
	public static void setPixel(int col, int i) {
		pixels[i] = col;
	}

	public static void setOffsets(int x, int y) {
		xOffset = x; yOffset = y;
	}
	
		
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getX() {
		return xOffset;
	}
	
	public static int getY() {
		return yOffset;
	}
	
	public static void clear() {
		for(int i = 0; i < width*height; i++)
			setPixel(0xff000000, i);
	}
	
	public static void fix() {
		for(int i = 0; i < -xOffset; i++)
			for(int j = 0; j < height; j++)
				setPixel(0xff000000, i, j);
		for(int j = 0; j < -yOffset; j++)
			for(int i = 0; i < width; i++)
				setPixel(0xff000000, i, j);
	}
	
	public static void renderTile(int x, int y, Sprite tile) {
		x = x*Tile.SIZE - xOffset;
		y = y*Tile.SIZE - yOffset;
		
		for(int j = 0; j < Tile.SIZE; j++) {
			int yPos = j + y; {
				if(yPos < -Tile.SIZE || yPos >= height) break;
				if(yPos < 0) yPos = 0;
			}
			for(int i = 0; i < Tile.SIZE; i++) {
				int xPos = i + x; {
					if(xPos < -Tile.SIZE || xPos >= width) break;
					if(xPos < 0) xPos = 0;
				}
				setPixel(tile.getPixel(i, j), xPos, yPos);
			}
		}
	}
	
	public static void renderEntity(int x, int y, Element ent) {
		if(ent.onScreen())
			graphics.drawImage(ent.getSprite().getImage(), x-xOffset, y-yOffset, null);
	}
}
