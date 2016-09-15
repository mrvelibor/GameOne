package gameone.level;

import java.util.Random;


public class RandomLevel extends Level {

	public RandomLevel(int width, int height) {
		super(width, height);
		generateLevel();
	}
	
	public RandomLevel(int size) {
		this(size, size);
	}
	
	
	protected void generateLevel()  {
		Random rand = new Random();
		for(int y = 0; y < HEIGHT; y++)
			for(int x = 0; x < WIDTH; x++)
				setTile(x, y, rand.nextInt(4));
	}
}
