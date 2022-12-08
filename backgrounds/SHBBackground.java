import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SHBBackground implements Background{
	
	public Image background1;
	public Image background2;
	public Image background3;
	public Image background4;
	public Image background5;
	public Image background6;
	public Image chosenBackground;
	private int backgroundWidth = 100;
	private int backgroundHeight = 100;
	
	public SHBBackground() {
		try { 
			this.background1 = ImageIO.read(new File("res/tileableBg1.png"));
			this.background2 = ImageIO.read(new File("res/tileableBg2.png"));
		//	this.background3 = ImageIO.read(new File("res/tileableBg3.jpg")); //find a replacement
			this.background4 = ImageIO.read(new File("res/tileableBg4.jpg"));
		}
		catch (IOException e){
			
		}
	}

	@Override
	public Tile getTile(int col, int row) {
		int x = (col * backgroundWidth);
		int y = (row * backgroundHeight);
		Tile newTile = null;
		newTile = new Tile(background2, x, y, backgroundWidth, backgroundHeight, false);
		
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