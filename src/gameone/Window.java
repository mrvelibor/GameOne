package gameone;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final Cursor BLANK_CURSOR, DEFAULT_CURSOR;
	static {
		DEFAULT_CURSOR = Cursor.getDefaultCursor();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
	}
	
	private GameOne game;
	
	public Window(GameOne o) {
		super(GameOne.TITLE);
		game = o;
		add(game);
		addMenu();
		hideCursor();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(false);
		/*GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice dev = env.getDefaultScreenDevice();
		setUndecorated(true);
		dev.setFullScreenWindow(this);*/
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addMenu() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem radio;
		JCheckBox check;
		ButtonGroup group;
		
		menuBar = new JMenuBar();
		menuBar.setFocusable(false);
		
		menu = new JMenu("Game");
		menuBar.add(menu);
		{
			menuItem = new JMenuItem("Hello");
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// nista
				}
			});
			menuItem.setEnabled(false);
			menu.add(menuItem);
			
			menu.addSeparator();
			
			menuItem = new JMenuItem("Exit");
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.stop();
					dispose();
				}
			});
			menu.add(menuItem);
		}
		
		menu = new JMenu("Options");
		menuBar.add(menu);
		{
			submenu = new JMenu("FPS Limit");
			menu.add(submenu);
			group = new ButtonGroup();
			
			radio = new JRadioButtonMenuItem("30");
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.fpsRatio = 0.5;
				}
			});
			radio.setSelected(game.fpsRatio == 0.5);
			group.add(radio);
			submenu.add(radio);
			
			radio = new JRadioButtonMenuItem("60");
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.fpsRatio = 1;
				}
			});
			radio.setSelected(game.fpsRatio == 1);
			group.add(radio);
			submenu.add(radio);
			
			radio = new JRadioButtonMenuItem("120");
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.fpsRatio = 2;
				}
			});
			radio.setSelected(game.fpsRatio == 2);
			group.add(radio);
			submenu.add(radio);
			
			radio = new JRadioButtonMenuItem("Unlimited");
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.fpsRatio = 0;
				}
			});
			radio.setSelected(game.fpsRatio == 0);
			group.add(radio);
			submenu.add(radio);
			
			menu.addSeparator();
			
			check = new JCheckBox("FPS Counter");
			check.setSelected(game.renderFpsCounter);
			check.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					game.renderFpsCounter = !game.renderFpsCounter;
				}
			});
			menu.add(check);
			
			check = new JCheckBox("Mouse Coordinates");
			check.setSelected(game.renderMouseCoords);
			check.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					game.renderMouseCoords = !game.renderMouseCoords;
				}
			});
			menu.add(check);
			
			check = new JCheckBox("Player Coordinates");
			check.setSelected(game.renderPlayerCoords);
			check.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					game.renderPlayerCoords = !game.renderPlayerCoords;
				}
			});
			menu.add(check);
			
			check = new JCheckBox("Mob Bounds");
			check.setSelected(game.renderMobBounds);
			check.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					game.renderMobBounds = !game.renderMobBounds;
				}
			});
			menu.add(check);
			
			check = new JCheckBox("Projectile Bounds");
			check.setSelected(game.renderProjectileBounds);
			check.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					game.renderProjectileBounds = !game.renderProjectileBounds;
				}
			});
			menu.add(check);
		}
		
		menu = new JMenu("Help");
		menuBar.add(menu);
		{
			menuItem = new JMenuItem("How to play");
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Hello", "Help", JOptionPane.QUESTION_MESSAGE);
				}
			});
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Troubleshoot");
			menuItem.addActionListener(new ActionListener() {
				String testVersion = "1.7.0_25-b16", activeVersion = System.getProperty("java.runtime.version");
				int status = getStatus();
				
				int getStatus() {
					int comp = activeVersion.compareTo(testVersion);
					if(comp > 0) return JOptionPane.QUESTION_MESSAGE;
					else if(comp < 0) return JOptionPane.ERROR_MESSAGE;
					else return JOptionPane.INFORMATION_MESSAGE;
				}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// System.out.print(System.getProperty("java.runtime.version"));
					JOptionPane.showMessageDialog(
							null,
							"Tested on Windows 7 Ultimate 64-Bit\n" + "with Java Runtime Environment:\n    v" + testVersion + "\n\nYou're using:\n    " + System.getProperty("sun.arch.data.model") + "-Bit JRE\n    v" + activeVersion,
							"Troubleshoot", status);
				}
			});
			menu.add(menuItem);
			menu.addSeparator();
			
			menuItem = new JMenuItem("About");
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Game One 0.5\n" + "    by Velibor Bacujkov\n" + "    2013.", "About", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			menu.add(menuItem);
		}
		
		setJMenuBar(menuBar);
	}
	
	public void showCursor() {
		game.setCursor(DEFAULT_CURSOR);
	}
	
	public void hideCursor() {
		game.setCursor(BLANK_CURSOR);
	}
	
}
