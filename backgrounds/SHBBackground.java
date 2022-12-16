import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SHBBackground implements Background{
	
	public static Image background1;
	public static Image background2;
	public static Image background3;
	public static Image background4;
	public static Image background5;
	public static Image background6;
	
	public static Image backgroundXWin;
	public static Image backgroundOWin;
	public static Image backgroundDraw;
	public static Image chosenBackground;
	private int backgroundWidth = 100;
	private int backgroundHeight = 100;
	
	public SHBBackground() {
		try { 
			background1 = ImageIO.read(new File("res/tileableBg1.png"));
			background2 = ImageIO.read(new File("res/tileableBg2.png"));
			background3 = ImageIO.read(new File("res/tileableBg3.jpg")); 
			background4 = ImageIO.read(new File("res/tileableBg4.jpg"));
			background5 = ImageIO.read(new File("res/tileableBg5.png"));
			background6 = ImageIO.read(new File("res/tileableBg6.jpg"));
			backgroundXWin = ImageIO.read(new File("res/confetti.jpg"));
			backgroundOWin = ImageIO.read(new File("res/confetti.jpg"));
			backgroundDraw = ImageIO.read(new File("res/confetti.jpg"));
		}
		catch (IOException e){
			
		}
	}

	public static Image getBackground(int i) {
		if (i == 1) {
			return background1;
		} else if (i == 2) {
			return background2;
		} else if (i == 3) {
			return background3;
		} else if (i == 4) {
			return background4;
		} else if (i == 5) {
			return background5;
		} else if (i == 6) {
			return background6;
		} else if (i == 7) {
			return backgroundXWin;
		} else if (i == 8) {
			return backgroundOWin;
		} else if (i == 9) {
			return backgroundDraw;
		}
		return null;
	}
	public static void setChosenBackground(Image background) {
		chosenBackground = background;
	}
	@Override
	public Tile getTile(int col, int row) {
		int x = (col * backgroundWidth);
		int y = (row * backgroundHeight);
		Tile newTile = null;
		newTile = new Tile(chosenBackground, x, y, backgroundWidth, backgroundHeight, false);
		
		return newTile;
	}

	@Override
	public int getCol(double x) {
		int col = 0;
		if (backgroundWidth != 0) {
			col = (int) (x / backgroundWidth);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
	}

	@Override
	public int getRow(double y) {
		int row = 0;	
		if (backgroundHeight != 0) {
			row = (int) (y / backgroundHeight);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
	}

	@Override
	public double getShiftX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShiftY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShiftX(double shiftX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShiftY(double shiftY) {
		// TODO Auto-generated method stub
		
	}
	
}