import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseMotionAdapter;


public class AnimationFrame extends JFrame {

	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = 400;
	final public static int SCREEN_WIDTH = 400;

	private int screenCenterX = SCREEN_WIDTH / 2;
	private int screenCenterY = SCREEN_HEIGHT / 2;

	private double scale = 1;
	//point in universe on which the screen will center
	private double logicalCenterX = 0;		
	private double logicalCenterY = 0;

	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblTop;
	private JLabel lblBottom;
	
	private JButton btnTopL;
	private JButton btnTopM;
	private JButton btnTopR;
	private JButton btnMidL;
	private JButton btnMidM;
	private JButton btnMidR;
	private JButton btnBotL;
	private JButton btnBotM;
	private JButton btnBotR;
	
	private JButton btnColour1;
	private JButton btnColour2;
	private JButton btnColour3;
	private JButton btnColour4;
	private JButton btnColour5;
	private JButton btnColour6;
	private JButton btnColour7;
	private JButton btnColour8;
	private JButton btnColour9;
	private JButton btnColour10;
	private JButton btnColour11;
	private JButton btnColour12;
	private JButton btnColour13;
	
	private Color foregroundColour = Color.WHITE;
	private Color backgroundColour;
	MiniMax minimax = new MiniMax();
	boolean decisionMade = false;
	
	public static int currentTurn = 1; //1 = X, 2 = O
	private boolean topLOccupied = false;
	private boolean topMOccupied = false;
	private boolean topROccupied = false;
	private boolean midLOccupied = false;
	private boolean midMOccupied = false;
	private boolean midROccupied = false;
	private boolean botLOccupied = false;
	private boolean botMOccupied = false;
	private boolean botROccupied = false;

	private String board[] = new String[9];
	private boolean win = false;
	
	private static boolean stop = false;

	private long current_time = 0;								//MILLISECONDS
	private long next_refresh_time = 0;							//MILLISECONDS
	private long last_refresh_time = 0;
	private long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	private long actual_delta_time = 0;							//MILLISECONDS
	private long elapsed_time = 0;
	private boolean isPaused = false;

	private KeyboardInput keyboard = new KeyboardInput();
	private Universe universe = null;

	//local (and direct references to various objects in universe ... should reduce lag by avoiding dynamic lookup
	private Animation animation = null;
	private DisplayableSprite player1 = null;
	private ArrayList<DisplayableSprite> sprites = null;
	private ArrayList<Background> backgrounds = null;
	private Background background = null;
	boolean centreOnPlayer = false;
	int universeLevel = 0;
	
	
	
	public AnimationFrame(Animation animation)
	{
		super("");
		
		this.animation = animation;
		this.setVisible(true);		
		this.setFocusable(true);
		this.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});
		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				contentPane_mouseMoved(e);
			}
		});
		
		Container cp = getContentPane();
		cp.setBackground(Color.BLACK);
		cp.setLayout(null);

		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);

		btnPauseRun = new JButton("||");	//Example code of how a button works
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});
		
		btnColour1 = new JButton(" ");
		btnColour1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 1);
			}
		});
		
		btnColour2 = new JButton(" ");
		btnColour2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 2);
			}
		});
		
		btnColour3 = new JButton(" ");
		btnColour3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 3);
			}
		});
		
		btnColour4 = new JButton(" ");
		btnColour4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 4);
			}
		});
		
		btnColour5 = new JButton(" ");
		btnColour5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 5);
			}
		});
		
		btnColour6 = new JButton(" ");
		btnColour6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 6);
			}
		});
		
		btnColour7 = new JButton(" ");
		btnColour7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 7);
			}
		});
		
		btnColour8 = new JButton(" ");
		btnColour8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 8);
			}
		});
		
		btnColour9 = new JButton(" ");
		btnColour9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 9);
			}
		});
		
		btnColour10 = new JButton(" ");
		btnColour10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 10);
			}
		});
		
		btnColour11 = new JButton(" ");
		btnColour11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 11);
			}
		});
		
		btnColour12 = new JButton(" ");
		btnColour12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 12);
			}
		});
		
		btnColour13 = new JButton(" ");
		btnColour13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnColour_mouseClicked(arg0, 13);
			}
		});
		
		btnTopL = new JButton(" ");
		btnTopL.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 1);
			}
			
		});
		
		btnTopM = new JButton(" ");
		btnTopM.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 2);
			}

		});
		btnTopR = new JButton(" ");
		btnTopR.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 3);
			}

		});
		btnMidL = new JButton(" ");
		btnMidL.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 4);
			}

		});
		btnMidM = new JButton(" ");
		btnMidM.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 5);
			}

		});
		btnMidR = new JButton(" ");
		btnMidR.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 6);
			}

		});
		btnBotL= new JButton(" ");
		btnBotL.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 7);
			}

		});
		btnBotM = new JButton(" ");
		btnBotM.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 8);
			}

		});
		btnBotR = new JButton(" ");
		btnBotR.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnBoard_mouseClicked(arg0, 9);
			}

		});
		
		setButtons(btnTopL, 50, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnTopM, 150, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnTopR, 250, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidL, 50, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidM, 150, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidR, 250, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotL, 50, 250, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotM, 150, 250, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotR, 250, 250, 100, 100, foregroundColour, backgroundColour);
		
/* Button template
		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12)); //repeat for each button
		btnPauseRun.setBounds(SCREEN_WIDTH - 64, 20, 48, 32);
		btnPauseRun.setFocusable(false);
		getContentPane().add(btnPauseRun);
		getContentPane().setComponentZOrder(btnPauseRun, 0);
*/
		lblTop = new JLabel("Time: ");
		lblTop.setForeground(Color.WHITE);
		lblTop.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTop.setBounds(115, 22, SCREEN_WIDTH - 16, 30);
		getContentPane().add(lblTop);
		getContentPane().setComponentZOrder(lblTop, 0);

		lblBottom = new JLabel("Status");
		lblBottom.setForeground(Color.WHITE);
		lblBottom.setFont(new Font("Consolas", Font.BOLD, 30));
		lblBottom.setBounds(16, SCREEN_HEIGHT - 30 - 16, SCREEN_WIDTH - 16, 36);
		lblBottom.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblBottom);
		getContentPane().setComponentZOrder(lblBottom, 0);

	}


	private void setButtons(JButton button, int x, int y, int width, int height, Color foregroundColor, Color backgroundColor) {
		button.setFont(new Font("Tahoma", Font.BOLD, 48)); //Tahoma, Brush Script MT, Papyrus, 
		button.setBounds(x, y, width, height);
		button.setFocusable(false);
		button.setForeground(foregroundColor); //Text colour
		button.setBackground(backgroundColor); //Box colour
		
		getContentPane().add(button);
		getContentPane().setComponentZOrder(button, 0);
	}

	public void start()
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				animationLoop();
				System.out.println("run() complete");
			}
		};

		thread.start();
		System.out.println("main() complete");

	}	
	
	private void animationLoop() {

		universe = animation.getNextUniverse();
		universeLevel++;
		
		while (stop == false && universe != null) {

			sprites = universe.getSprites();
			player1 = universe.getPlayer1();
			backgrounds = universe.getBackgrounds();
			centreOnPlayer = universe.centerOnPlayer();
			this.scale = universe.getScale();
			this.logicalCenterX = universe.getXCenter();
			this.logicalCenterY = universe.getYCenter();

			// main game loop
			while (stop == false && universe.isComplete() == false) {

				//adapted from http://www.java-gaming.org/index.php?topic=24220.0
				last_refresh_time = System.currentTimeMillis();
				next_refresh_time = current_time + minimum_delta_time;
				
				//sleep until the next refresh time
				while (current_time < next_refresh_time)
				{
					//allow other threads (i.e. the Swing thread) to do its work
					Thread.yield();

					try {
						Thread.sleep(1);
					}
					catch(Exception e) {    					
					} 

					//track current time
					current_time = System.currentTimeMillis();
				}

				//read input
				keyboard.poll();
				handleKeyboardInput();

				//UPDATE STATE
				updateTime();
				
				universe.update(keyboard, actual_delta_time);
				updateControls();

				//REFRESH
				this.logicalCenterX = universe.getXCenter();
				this.logicalCenterY = universe.getYCenter();
				this.repaint();
				
				//Computer opponent decision block **Currently computer must be O
				if (currentTurn == 2) {
					int optimal = minimax.findBestMove(true, board, 2).getPosition();
					computerMove(optimal);
				}
				//Game end decision block 
				if (getIsVictorious(1) == true || getIsVictorious(2) == true) {
					stop = true;
				}
			}

			universe = animation.getNextUniverse();
			keyboard.poll();
		}

		System.out.println("animation complete");
		AudioPlayer.setStopAll(true);
		dispose();	

	}

	private void updateControls() {
		String tile = null;
		if (currentTurn == 1) {
			tile = "X";
		} else {
			tile = "O";
		}
		
		this.lblTop.setText("Current Turn: " + tile);
	//	this.lblTop.setText(String.format("Time: %9.3f;  centerX: %5d; centerY: %5d;  scale: %3.3f", elapsed_time / 1000.0, screenCenterX, screenCenterY, scale));
		this.lblBottom.setText(Integer.toString(universeLevel));
		if (universe != null) {
			this.lblBottom.setText(universe.toString());
		}

	}

	private void updateTime() {

		current_time = System.currentTimeMillis();
		actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);
		last_refresh_time = current_time;
		elapsed_time += actual_delta_time;

	}

	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (isPaused) {
			isPaused = false;
			this.btnPauseRun.setText("||");
		}
		else {
			isPaused = true;
			this.btnPauseRun.setText(">");
		}
	}
	
	private void btnColour_mouseClicked(MouseEvent arg0, int colour) {
		if (colour == 1) {
			backgroundColour = Color.BLACK;
		} else if (colour == 2) {
			backgroundColour = Color.BLUE;
		} else if (colour == 3) {
			backgroundColour = Color.CYAN;
		} else if (colour == 4) {
			backgroundColour = Color.DARK_GRAY;
		} else if (colour == 5) {
			backgroundColour = Color.GRAY;
		} else if (colour == 6) {
			backgroundColour = Color.LIGHT_GRAY;
		} else if (colour == 7) {
			backgroundColour = Color.GREEN;
		} else if (colour == 8) {
			backgroundColour = Color.MAGENTA;
		} else if (colour == 9) {
			backgroundColour = Color.ORANGE;
		} else if (colour == 10) {
			backgroundColour = Color.PINK;
		} else if (colour == 11) {
			backgroundColour = Color.RED;
		} else if (colour == 12) {
			backgroundColour = Color.WHITE;
		} else if (colour == 13) {
			backgroundColour = Color.YELLOW;
		}
		
	}
	
	private void btnBoard_mouseClicked(MouseEvent arg0, int position) {
		String text = ""; //Player method to update tiles
		if (currentTurn == 1) {
			text = "X";
		} else if (currentTurn == 2) {
			text = "O";
		}
		if (position == 1 && topLOccupied != true) {
			this.btnTopL.setText(text); //assign text
			topLOccupied = true; //Prevent tile from being used
			changeTurn(currentTurn); //update the turn 
			board[0] = text; //Update internal board for game end purposes
		} else if (position == 2 && topMOccupied != true) {
			this.btnTopM.setText(text);
			topMOccupied = true;
			changeTurn(currentTurn);
			board[1] = text;
		} else if (position == 3 && topROccupied != true) {
			this.btnTopR.setText(text);
			topROccupied = true;
			changeTurn(currentTurn);
			board[2] = text;
		} else if (position == 4 && midLOccupied != true) {
			this.btnMidL.setText(text);
			midLOccupied = true;
			changeTurn(currentTurn);
			board[3] = text;
		} else if (position == 5 && midMOccupied != true) {
			this.btnMidM.setText(text);
			midMOccupied = true;
			changeTurn(currentTurn);
			board[4] = text;
		} else if (position == 6 && midROccupied != true) {
			this.btnMidR.setText(text);
			midROccupied = true;
			changeTurn(currentTurn);
			board[5] = text;
		} else if (position == 7 && botLOccupied != true) {
			this.btnBotL.setText(text);
			botLOccupied = true;
			changeTurn(currentTurn);
			board[6] = text;
		} else if (position == 8 && botMOccupied != true) {
			this.btnBotM.setText(text);
			botMOccupied = true;
			changeTurn(currentTurn);
			board[7] = text;
		} else if (position == 9 && botROccupied != true) {
			this.btnBotR.setText(text);
			botROccupied = true;
			changeTurn(currentTurn);
			board[8] = text;
		} 
		
	}
	
	private void computerMove(int position) {
		String text = ""; //computer method to make moves without mouse clicks
		if (currentTurn == 1) {
			text = "X";
		} else if (currentTurn == 2) {
			text = "O";
		}
		if (position == 1 && topLOccupied != true) {
			this.btnTopL.setText(text); //update tile
			topLOccupied = true; //prevent tile from being used
			changeTurn(currentTurn); //allow opponent to move
			board[0] = text; //update internal board
		} else if (position == 2 && topMOccupied != true) {
			this.btnTopM.setText(text);
			topMOccupied = true;
			changeTurn(currentTurn);
			board[1] = text;
		} else if (position == 3 && topROccupied != true) {
			this.btnTopR.setText(text);
			topROccupied = true;
			changeTurn(currentTurn);
			board[2] = text;
		} else if (position == 4 && midLOccupied != true) {
			this.btnMidL.setText(text);
			midLOccupied = true;
			changeTurn(currentTurn);
			board[3] = text;
		} else if (position == 5 && midMOccupied != true) {
			this.btnMidM.setText(text);
			midMOccupied = true;
			changeTurn(currentTurn);
			board[4] = text;
		} else if (position == 6 && midROccupied != true) {
			this.btnMidR.setText(text);
			midROccupied = true;
			changeTurn(currentTurn);
			board[5] = text;
		} else if (position == 7 && botLOccupied != true) {
			this.btnBotL.setText(text);
			botLOccupied = true;
			changeTurn(currentTurn);
			board[6] = text;
		} else if (position == 8 && botMOccupied != true) {
			this.btnBotM.setText(text);
			botMOccupied = true;
			changeTurn(currentTurn);
			board[7] = text;
		} else if (position == 9 && botROccupied != true) {
			this.btnBotR.setText(text);
			botROccupied = true;
			changeTurn(currentTurn);
			board[8] = text;
		} 
		
	}
	
	private boolean getIsVictorious(int currentTurn) {
		String tile = null;
		if (currentTurn == 1) {
			tile = "X";
		} else {
			tile = "O";
		}
		for (int i = 0; i <= 2; i++) {
			if (board[i] == tile && board[i+3] == tile && board[i+6] == tile) {
				System.out.println("Victory for " + tile);
				return true; //win
			}
		}
		for (int i = 0; i <= 7; i+=3) {
			if (board[i] == tile && board[i+1] == tile && board[i+2] == tile) {
				System.out.println("Victory for " + tile);
				return true; // win
			}
		}
		if (board[0] == tile && board[4] == tile && board[8] == tile) {
			System.out.println("Victory for " + tile);
			return true; // win
		}
		else if (board[2] == tile && board[4] == tile && board[6] == tile) {
			System.out.println("Victory for " + tile);
			return true; //win
		}
		else if (board[0] != null && board[1] != null && board[2] != null && board[3] != null && board[4] != null && 
				board[5] != null && board[6] != null && board[7] != null && board[8] != null) {
			System.out.println("Draw"); 
			return true; //Draw
		}
		return false; //no state
	}
	
	private void changeTurn(int currentTurn) {
		if (currentTurn == 1) {
			this.currentTurn = 2;
		} else {
			this.currentTurn = 1;
		}
	}

	private void handleKeyboardInput() {
		
		if (keyboard.keyDown(80) && ! isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && isPaused ) {
			btnPauseRun_mouseClicked(null);
		}
		if (keyboard.keyDown(112)) {
			scale *= 1.01;
		}
		if (keyboard.keyDown(113)) {
			scale /= 1.01;
		}
		
		if (keyboard.keyDown(65)) {
			screenCenterX -= 1;
		}
		if (keyboard.keyDown(68)) {
			screenCenterX += 1;
		}
		if (keyboard.keyDown(83)) {
			screenCenterY -= 1;
		}
		if (keyboard.keyDown(88)) {
			screenCenterY += 1;
		}
		
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g)
		{	
			if (universe == null) {
				return;
			}

			if (player1 != null && centreOnPlayer) {
				logicalCenterX = player1.getCenterX();
				logicalCenterY = player1.getCenterY();     
			}

			if (backgrounds != null) {
				for (Background background: backgrounds) {
					paintBackground(g, background);
				}
			}

			if (sprites != null) {
				for (DisplayableSprite activeSprite : sprites) {
					DisplayableSprite sprite = activeSprite;
					if (sprite.getVisible()) {
						if (sprite.getImage() != null) {
							g.drawImage(sprite.getImage(), translateToScreenX(sprite.getMinX()), translateToScreenY(sprite.getMinY()), scaleLogicalX(sprite.getWidth()), scaleLogicalY(sprite.getHeight()), null);
						}
						else {
							g.setColor(Color.BLUE);
							g.fillRect(translateToScreenX(sprite.getMinX()), translateToScreenY(sprite.getMinY()), scaleLogicalX(sprite.getWidth()), scaleLogicalY(sprite.getHeight()));
						}
					}
				}				
			}
		}
		
		private void paintBackground(Graphics g, Background background) {
			
			if ((g == null) || (background == null)) {
				return;
			}
			
			//what tile covers the top-left corner?
			double logicalLeft = (logicalCenterX  - (screenCenterX / scale) - background.getShiftX());
			double logicalTop =  (logicalCenterY - (screenCenterY / scale) - background.getShiftY()) ;
						
			int row = background.getRow((int)(logicalTop - background.getShiftY() ));
			int col = background.getCol((int)(logicalLeft - background.getShiftX()  ));
			Tile tile = background.getTile(col, row);
			
			boolean rowDrawn = false;
			boolean screenDrawn = false;
			while (screenDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					if (tile.getWidth() <= 0 || tile.getHeight() <= 0) {
						//no increase in width; will cause an infinite loop, so consider this screen to be done
						g.setColor(Color.GRAY);
						g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);					
						rowDrawn = true;
						screenDrawn = true;						
					}
					else {
						Tile nextTile = background.getTile(col+1, row+1);
						int width = translateToScreenX(nextTile.getMinX()) - translateToScreenX(tile.getMinX());
						int height = translateToScreenY(nextTile.getMinY()) - translateToScreenY(tile.getMinY());
						g.drawImage(tile.getImage(), translateToScreenX(tile.getMinX() + background.getShiftX()), translateToScreenY(tile.getMinY() + background.getShiftY()), width, height, null);
					}					
					//does the RHE of this tile extend past the RHE of the visible area?
					if (translateToScreenX(tile.getMinX() + background.getShiftX() + tile.getWidth()) > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				if (translateToScreenY(tile.getMinY() + background.getShiftY() + tile.getHeight()) > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					screenDrawn = true;
				}
				else {
					col = background.getCol(logicalLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}

	private int translateToScreenX(double logicalX) {
		return screenCenterX + scaleLogicalX(logicalX - logicalCenterX);
	}		
	private int scaleLogicalX(double logicalX) {
		return (int) Math.round(scale * logicalX);
	}
	private int translateToScreenY(double logicalY) {
		return screenCenterY + scaleLogicalY(logicalY - logicalCenterY);
	}		
	private int scaleLogicalY(double logicalY) {
		return (int) Math.round(scale * logicalY);
	}

	private double translateToLogicalX(int screenX) {
		int offset = screenX - screenCenterX;
		return offset / scale;
	}
	private double translateToLogicalY(int screenY) {
		int offset = screenY - screenCenterY;
		return offset / scale;			
	}
	
	protected void contentPane_mouseMoved(MouseEvent e) {
		MouseInput.screenX = e.getX();
		MouseInput.screenY = e.getY();
		MouseInput.logicalX = translateToLogicalX(MouseInput.screenX);
		MouseInput.logicalY = translateToLogicalY(MouseInput.screenY);
	}

	protected void this_windowClosing(WindowEvent e) {
		System.out.println("windowClosing()");
		stop = true;
		dispose();	
	}
	
	public static int getCurrentTurn() {
		return currentTurn;
	}
}
