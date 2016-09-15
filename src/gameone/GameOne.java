package gameone;

import gameone.entities.*;
import gameone.entities.mobs.enemies.*;
import gameone.entities.mobs.player.*;
import gameone.graphics.*;
import gameone.input.*;
import gameone.level.*;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;


public class GameOne extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	
	public static final String TITLE = "Game One";
	
	public static final int WIDTH = 426,
					  		HEIGHT = WIDTH * 9/16,
					  		SCALE = 3,
					  		UPS = 60;
	
	public double fpsRatio = 1;
	public boolean renderHealthBarsTop = true,
				   renderFpsCounter = true,
				   renderMouseCoords = false,
			   	   renderPlayerCoords = false,
			   	   renderMobBounds = false,
			   	   renderProjectileBounds = false;
		
	private Thread thread;
	private boolean running = false, paused = false;
	
	private static long totalUpdates = 0, totalFrames = 0;
	private static int frames = 0, updates = 0, 
					   fps = 0, ups = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

	private static Window window;
	@SuppressWarnings("unused")
	private static Screen screen;
	@SuppressWarnings("unused")
	private static Overlay overlay;
	
	private static Keyboard key;
	private static Mouse mouse;
	
	public static Player player1;
	private static Level level;
	
	
	public GameOne() {
		super();
		Dimension size = new Dimension(scaleUp(WIDTH), scaleUp(HEIGHT));
		setPreferredSize(size);
		screen = new Screen(image);
		overlay = new Overlay(image);
		
		key = new Keyboard();
		addKeyListener(key);
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				Keyboard.clear();
				pause();
			}
			@Override
			public void focusGained(FocusEvent e) {
				unpause();
			}
		});
		
		//level = new RandomLevel(4098);
		level = Level.SPAWN;
		Element.init(level);
		
		player1 = new Mage(1, 1);
		
		new Cigan(8, 11);
		//for(int i = 5; i < 30; i++) for(int j = 10; j < 13; j++, j++) new Cigan(i, j);
		
		window = new Window(this);
		createBufferStrategy(2);
		start();
	}
	
	
	/**
	 * @return
	 * Number of frames renderred per second.
	 */
	public static int getFPS() {
		return fps;
	}
	
	/**
	 * @return
	 * Number of updates per second of the game.<br>
	 * The value should always be 60.
	 */
	public static int getUPS() {
		return ups;
	}
	
	/**
	 * @return
	 * Total number of updates since game started
	 */
	public static long getUpdates() {
		return totalUpdates;
	}
	
	/**
	 * @return
	 * Total number of frames since game started
	 */
	public static long getFrames() {
		return totalFrames;
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static int scaleUp(int a) {
		return a*SCALE;
	}
	
	public static int scaleDown(int a) {
		return a/SCALE;
	}
	
	public static int scaleUp(double a) {
		return scaleUp((int) a);
	}
	
	public static int scaleDown(double a) {
		return scaleDown((int) a);
	}
	
	
	
	private void update() {
		level.update();
		updates++; totalUpdates++;
	}
	
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		renderImage();
		renderOverlay();
		g.drawImage(image, 0, 0, scaleUp(WIDTH), scaleUp(HEIGHT), this);
		drawInfo(g);
		
		bs.show();
		g.dispose();
		frames++; totalFrames++;
	}
	
	private void renderImage() {
		level.renderBackground(player1.getRightX(), player1.getBotY());
		Screen.fix();
		level.renderForeground();
	}
	
	private void renderOverlay() {
		if(renderMobBounds)
			Overlay.renderMobBounds();
		if(renderProjectileBounds)
			Overlay.renderProjectileBounds();
		Overlay.renderMobHealthBars(renderHealthBarsTop);
		Overlay.renderDestination(player1);
		Overlay.renderHUD(4, 4);
		Overlay.renderCursor();
	}
	
	private void drawInfo(Graphics g) {
		g.setFont(Overlay.FONT);
		if(renderPlayerCoords)
			Overlay.drawPlayerCoords(g, WIDTH-100, 8);
		if(renderMouseCoords)
			Overlay.drawMouseCoords(g, WIDTH-100, 16);
		if(renderFpsCounter)
			Overlay.drawFpsCounter(g, WIDTH-100, HEIGHT-4);
	}
	
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public synchronized void pause() {
		window.showCursor();
		paused = true;
	}
	
	public synchronized void unpause() {
		window.hideCursor();
		paused = false; notify();
	}
	
	@Override
	public void run() {
		try {
			long lastTime = System.nanoTime(),
				 timer = System.currentTimeMillis();
			final double ns = 1000000000.0 / UPS;
			double dUps = 0, dFps = 0;
	
			requestFocus();
			
			while(running) {
				synchronized(this) {
					if(paused) {
						wait();
						timer = System.currentTimeMillis();
						lastTime = System.nanoTime();
					}
				}
				long now = System.nanoTime();
				dUps += (now-lastTime) / ns;
				dFps += (now-lastTime) / ns * fpsRatio;
				lastTime = now;
				
				while(dUps >= 1) {
					update();
					dUps--;
				}
				
				if(fpsRatio != 0) {
					if(dFps >= 1) {
						render();
						dFps = 0;
					}
				}
				else render();
				
				if(System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					ups = updates; fps = frames;
					updates = 0; frames = 0;
				}
			}
			
			stop();
		} catch(Throwable e) {
			window.showCursor();
			JOptionPane.showMessageDialog(null, e.getStackTrace(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GameOne();
	}

}
