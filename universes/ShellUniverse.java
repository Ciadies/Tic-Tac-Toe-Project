import java.util.ArrayList;

public class ShellUniverse implements Universe {

	private boolean complete = false;	
	private DisplayableSprite player1 = null;
	private static Background background = null;	
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = null;
	private double xCenter = 0;
	private double yCenter = 0;

	public ShellUniverse () {
		background = new SHBBackground();
		backgrounds = new ArrayList<Background>();
		backgrounds.add(background);
	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return 0;
	}

	public double getYCenter() {
		return 0;
	}

	public void setXCenter(double xCenter) {
	}

	public void setYCenter(double yCenter) {
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		complete = true;
	}

	public ArrayList<Background> getBackgrounds() {
		return backgrounds;
	}	

	public DisplayableSprite getPlayer1() {
		return player1;
	}

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}

	public boolean centerOnPlayer() {
		return false;
	}		

	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, keyboard, actual_delta_time);
    	} 
		
		
	}

	public String toString() {
		return " ";
	}
	
	public static void changeBackground(int bg) {
		SHBBackground.chosenBackground = SHBBackground.getBackground(bg);
		background = new SHBBackground();	
	}
}
