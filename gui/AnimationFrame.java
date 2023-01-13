import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.TimeUnit;

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
	
	private JButton btnFgColour1;
	private JButton btnFgColour2;
	private JButton btnFgColour3;
	private JButton btnFgColour4;
	private JButton btnFgColour5;
	private JButton btnFgColour6;
	private JButton btnFgColour7;
	private JButton btnFgColour8;
	private JButton btnFgColour9;
	private JButton btnFgColour10;
	private JButton btnFgColour11;
	private JButton btnFgColour12;
	private JButton btnFgColour13;
	
	private JButton btnBgArt1;
	private JButton btnBgArt2;
	private JButton btnBgArt3;
	private JButton btnBgArt4;
	private JButton btnBgArt5;
	private JButton btnBgArt6;
	private JButton btnBgArt7;
	private JButton btnBgArt8;
	private JButton btnBgArt9;
	
	private JButton btnHumanOpp;
	private JButton btnCPUOpp;
	
	private JButton btnGoFirst;
	private JButton btnGoSecond;
	
	private Icon iconArt1;
	private Icon iconArt2;
	private Icon iconArt3;
	private Icon iconArt4;
	private Icon iconArt5;
	private Icon iconArt6;
	private Icon iconArt7;
	private Icon iconArt8;
	private Icon iconArt9;
	
	private JButton btnReplay;
	private JButton btnChangeSettings;
	private JButton btnCloseGame;
	
	private JLabel lblEndGame;
	private int drawCount = 0;

	private static int CPUTurn = 2; //default 2
	private int chosen; 
	private int ogChosen;
	private int currentBackground = 1; // 1 is default, 6 is board
	boolean pause = true; // Post game options
	private Color foregroundColour = Color.WHITE;
	private Color backgroundColour;
	MiniMax minimax = new MiniMax();
	boolean decisionMade = false;
	boolean easterEggFound = false;
	
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
		panel.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);
		getContentPane().add(panel, BorderLayout.CENTER);

		iconArt1 = new ImageIcon("res/tileableBg1.png");
		iconArt2 = new ImageIcon("res/tileableBg2.png");
		iconArt3 = new ImageIcon("res/tileableBg3.jpg");
		iconArt4 = new ImageIcon("res/tileableBg4.jpg");
		iconArt5 = new ImageIcon("res/tileableBg5.png");
		iconArt6 = new ImageIcon("res/tileableBg6.jpg");
		iconArt7 = new ImageIcon("res/tileableBg7.jpg");
		iconArt8 = new ImageIcon("res/tileableBg8.jpg");
		iconArt9 = new ImageIcon("res/tileableBg9.jpg");
		
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
		
		btnFgColour1 = new JButton(" ");
		btnFgColour1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 1);
			}
		});
		
		btnFgColour2 = new JButton(" ");
		btnFgColour2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 2);
			}
		});
		
		btnFgColour3 = new JButton(" ");
		btnFgColour3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 3);
			}
		});
		
		btnFgColour4 = new JButton(" ");
		btnFgColour4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 4);
			}
		});
		
		btnFgColour5 = new JButton(" ");
		btnFgColour5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 5);
			}
		});
		
		btnFgColour6 = new JButton(" ");
		btnFgColour6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 6);
			}
		});
		
		btnFgColour7 = new JButton(" ");
		btnFgColour7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 7);
			}
		});
		
		btnFgColour8 = new JButton(" ");
		btnFgColour8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 8);
			}
		});
		
		btnFgColour9 = new JButton(" ");
		btnFgColour9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 9);
			}
		});
		
		btnFgColour10 = new JButton(" ");
		btnFgColour10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 10);
			}
		});
		
		btnFgColour11 = new JButton(" ");
		btnFgColour11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 11);
			}
		});
		
		btnFgColour12 = new JButton(" ");
		btnFgColour12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 12);
			}
		});
		
		btnFgColour13 = new JButton(" ");
		btnFgColour13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTextColour_mouseClicked(arg0, 13);
			}
		});
		
		btnBgArt1 = new JButton(iconArt1);
		btnBgArt1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 1);
			}
		});
		
		btnBgArt2 = new JButton(iconArt2);
		btnBgArt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 2);
			}
		});
		
		btnBgArt3 = new JButton(iconArt3);
		btnBgArt3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 3);
			}
		});
		
		btnBgArt4 = new JButton(iconArt4);
		btnBgArt4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 4);
			}
		});
		
		btnBgArt5 = new JButton(iconArt5);
		btnBgArt5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 5);
			}
		});
		
		btnBgArt6 = new JButton(iconArt6);
		btnBgArt6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 6);
			}
		});
		
		btnBgArt7 = new JButton(iconArt7);
		btnBgArt7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 7);
			}
		});
		
		btnBgArt8 = new JButton(iconArt8);
		btnBgArt8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 8);
			}
		});
		
		btnBgArt9 = new JButton(iconArt9);
		btnBgArt9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnArt_mouseClicked(arg0, 9);
			}
		});
		
		btnHumanOpp = new JButton("VS. Friend");
		btnHumanOpp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnOpponent_mouseClicked(arg0, 1);
			}
		});
		
		btnCPUOpp = new JButton("VS. CPU");
		btnCPUOpp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnOpponent_mouseClicked(arg0, 2);
			}
		});
		
		btnGoFirst = new JButton("First");
		btnGoFirst.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTurn_mouseClicked(arg0, 1);
			}
		});
		
		btnGoSecond = new JButton("Second");
		btnGoSecond.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTurn_mouseClicked(arg0, 2);
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
		
		btnReplay = new JButton("Replay?");
		btnReplay.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPostGame_mouseClicked(arg0, 1);
			}

		});
		
		btnChangeSettings = new JButton("Change Settings?");
		btnChangeSettings.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPostGame_mouseClicked(arg0, 2);
			}

		});
		
		btnCloseGame = new JButton("Close Game?");
		btnCloseGame.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPostGame_mouseClicked(arg0, 3);
			}

		});
		
		if (currentBackground == 1) {
			setupColourButtons();
		} else if (currentBackground == 2) {
			setupFgColourButtons();
		} else if (currentBackground == 3) {
			setupArtButtons();
		} else if (currentBackground == 4) {
			setupOppButtons();
		} else if (currentBackground == 5) {
			setupTurnButtons();
		} else if (currentBackground == 6) {
			setupBoardButtons();
		}
/* Button template
		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12)); //repeat for each button
		btnPauseRun.setBounds(SCREEN_WIDTH - 64, 20, 48, 32);
		btnPauseRun.setFocusable(false);
		getContentPane().add(btnPauseRun);
		getContentPane().setComponentZOrder(btnPauseRun, 0);
*/
		lblTop = new JLabel("Time: ");
		lblTop.setForeground(Color.BLACK);
		lblTop.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTop.setBounds(115, 22, SCREEN_WIDTH - 16, 30);
		getContentPane().add(lblTop);
		getContentPane().setComponentZOrder(lblTop, 0);
		
		lblTop.addMouseListener(new MouseAdapter() {  //repeat for each button
			@Override
			public void mouseClicked(MouseEvent arg0) {
				easterEgg_mouseClicked(arg0);
			}

		});
		
		lblEndGame = new JLabel(" ");
		lblEndGame.setForeground(Color.WHITE);
		lblEndGame.setFont(new Font("Consolas", Font.BOLD, 40));
		lblEndGame.setBounds(130, 22, SCREEN_WIDTH - 16, 40);
		getContentPane().add(lblEndGame);
		getContentPane().setComponentZOrder(lblEndGame, 0);

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
	
	private void setColourButtons(JButton button, int x, int y, int width, int height, Color backgroundColor) {
		button.setBounds(x,y, width, height);
		button.setFocusable(false);
		button.setBackground(backgroundColor);

		getContentPane().add(button);
		getContentPane().setComponentZOrder(button, 0);
	}
	
	private void setArtButtons(JButton button, int x, int y, int width, int height) {
		button.setBounds(x, y, width, height);
		button.setFocusable(false);
		getContentPane().add(button);
		getContentPane().setComponentZOrder(button, 0);
	}
	
	private void setOppButtons(JButton button, int x, int y, int width, int height) {
		button.setBounds(x,y, width, height);
		button.setFocusable(false);

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
				
				//Game end decision block
				if (pause == false) {
					boolean draw = true;
					for (int i = 0; i < 9; i++) {
						if (board[i] == null) {
							draw = false; //check each tile, if any are empty then game can't be a draw so set it to false
						}
					}
					if (getIsVictorious(1) == true || getIsVictorious(2) == true || draw == true) {				
						pause = true; //move to end game
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						displayVictory();		
					}
				}
				//Computer opponent decision block 
				if (currentTurn == CPUTurn && stop == false && pause == false) {
					int optimal = minimax.findBestMove(true, board, CPUTurn);
					computerMove(optimal);
				}
				
				if (drawCount == 5) {
					easterEggFound = true;
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
		if (currentBackground == 1) { //Resize and change the title label
			this.lblTop.setText("Choose Board Colour");
			lblTop.setBounds(97, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 2) {
			this.lblTop.setText("Choose Mark Colour");
			lblTop.setBounds(102, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 3) {
			this.lblTop.setText("Choose Background");
			lblTop.setBounds(107, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 4) {
			this.lblTop.setText("Choose Opponent");
			lblTop.setBounds(118, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 5) {
			this.lblTop.setText("Choose Turn"); 
			lblTop.setBounds(140, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 6) {
			this.lblTop.setText("Current Turn: " + tile);
			lblTop.setBounds(115, 22, SCREEN_WIDTH - 16, 30);
		} else if (currentBackground == 7 || currentBackground == 8 || currentBackground == 9) {
			this.lblTop.setText(" ");
		}
		
		if (chosen == 1) { //set the text to a good colour for bg 
			this.lblTop.setForeground(Color.WHITE);
		} else if (chosen == 2) {
			this.lblTop.setForeground(Color.DARK_GRAY);
		} else if (chosen == 3) {
			this.lblTop.setForeground(Color.BLACK);
		} else if (chosen == 4) {
			this.lblTop.setForeground(Color.WHITE);
		} else if (chosen == 5) {
			this.lblTop.setForeground(Color.WHITE);
		} else if (chosen == 6) {
			this.lblTop.setForeground(Color.WHITE);
		} else if (chosen == 7) {
			this.lblTop.setForeground(Color.BLACK);
		} else if (chosen == 8) {
			this.lblTop.setForeground(Color.BLACK);
		} else if (chosen == 9) {
			this.lblTop.setForeground(Color.BLACK);
		}
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
	
	private void btnArt_mouseClicked(MouseEvent arg0, int choice) {
		if (choice == 1) {
			chosen = 1; //Chosen for the correct title colour
			ogChosen = 1; //ogChosen to remember for replay
			ShellUniverse.changeBackground(1);
		} else if (choice == 2) {
			chosen = 2;
			ogChosen = 2;
			ShellUniverse.changeBackground(2);
		} else if (choice == 3) {
			chosen = 3;
			ogChosen = 3;
			ShellUniverse.changeBackground(3);
		} else if (choice == 4) {
			chosen = 4;
			ogChosen = 4;
			ShellUniverse.changeBackground(4);
		} else if (choice == 5) {
			chosen = 5;
			ogChosen = 5;
			ShellUniverse.changeBackground(5);
		} else if (choice == 6) {
			chosen = 6;
			ogChosen = 6;
			ShellUniverse.changeBackground(6);
		} else if (choice == 7) {
			chosen = 7;
			ogChosen = 10;
			ShellUniverse.changeBackground(10);
		} else if (choice == 8) {
			chosen = 8;
			ogChosen = 11;
			ShellUniverse.changeBackground(11);
		} else if (choice == 9) {
			chosen = 9;
			ogChosen = 12;
			ShellUniverse.changeBackground(12);
		}
		currentBackground = 4;
		
		setupOppButtons();
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
		currentBackground = 2;
		
		// change to Foreground buttons actual game now with the colour
		setupFgColourButtons();
		
	}
	
	private void btnOpponent_mouseClicked(MouseEvent arg0, int opp) {
		if (opp == 1) {
			CPUTurn = 3;
			currentBackground = 6;
			
			setupBoardButtons();
			pause = false;
		} else if (opp == 2) {
			currentBackground = 5;
			
			setupTurnButtons();
		}
	}
	
	private void btnBoard_mouseClicked(MouseEvent arg0, int position) {
		String text = ""; //Player method to update tiles
		if (currentTurn == 1) {
			text = "X";
		} else if (currentTurn == 2) {
			text = "O";
		}
		if (pause == true) {
			return;
		}
		if (position == 1 && topLOccupied != true) {
			this.btnTopL.setText(text); //assign text
			topLOccupied = true; //Prevent tile from being used
			board[0] = text; //Update internal board for game end purposes
			changeTurn(currentTurn); //update the turn 
		} else if (position == 2 && topMOccupied != true) {
			this.btnTopM.setText(text);
			topMOccupied = true;
			board[1] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 3 && topROccupied != true) {
			this.btnTopR.setText(text);
			topROccupied = true;
			board[2] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 4 && midLOccupied != true) {
			this.btnMidL.setText(text);
			midLOccupied = true;
			board[3] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 5 && midMOccupied != true) {
			this.btnMidM.setText(text);
			midMOccupied = true;
			board[4] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 6 && midROccupied != true) {
			this.btnMidR.setText(text);
			midROccupied = true;
			board[5] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 7 && botLOccupied != true) {
			this.btnBotL.setText(text);
			botLOccupied = true;
			board[6] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 8 && botMOccupied != true) {
			this.btnBotM.setText(text);
			botMOccupied = true;
			board[7] = text;
			changeTurn(currentTurn); //update the turn 
		} else if (position == 9 && botROccupied != true) {
			this.btnBotR.setText(text);
			botROccupied = true;
			board[8] = text;
			changeTurn(currentTurn); //update the turn 
		} 

	}
	
	private void btnTextColour_mouseClicked(MouseEvent arg0, int colour) {
		if (colour == 1) {
			foregroundColour = Color.BLACK;
		} else if (colour == 2) {
			foregroundColour = Color.BLUE;
		} else if (colour == 3) {
			foregroundColour = Color.CYAN;
		} else if (colour == 4) {
			foregroundColour = Color.DARK_GRAY;
		} else if (colour == 5) {
			foregroundColour = Color.GRAY;
		} else if (colour == 6) {
			foregroundColour = Color.LIGHT_GRAY;
		} else if (colour == 7) {
			foregroundColour = Color.GREEN;
		} else if (colour == 8) {
			foregroundColour = Color.MAGENTA;
		} else if (colour == 9) {
			foregroundColour = Color.ORANGE;
		} else if (colour == 10) {
			foregroundColour = Color.PINK;
		} else if (colour == 11) {
			foregroundColour = Color.RED;
		} else if (colour == 12) {
			foregroundColour = Color.WHITE;
		} else if (colour == 13) {
			foregroundColour = Color.YELLOW;
		}
		setupArtButtons();
		
		// change to background
		currentBackground = 3;
		
	}
	
	private void btnTurn_mouseClicked(MouseEvent arg0, int turn) {
		if (turn == 1) {
			CPUTurn = 2;
		} else if (turn == 2) {
			CPUTurn = 1;
		}
		currentBackground = 6;
		
		setupBoardButtons();
		pause = false;
	}
	
	private void btnPostGame_mouseClicked(MouseEvent arg0, int input) {
		getContentPane().remove(btnReplay);
		getContentPane().remove(btnChangeSettings);
		getContentPane().remove(btnCloseGame);
		this.lblEndGame.setText("");
		currentTurn = 1;
		if (getIsVictorious(1) == false && getIsVictorious(2) == false) {
			drawCount++; //used for the gold colour easter egg
		}
		if (input == 1) { //replay
			currentBackground = 6;
			resetBoard();
			ShellUniverse.changeBackground(ogChosen);
			setupBoardButtons();
			pause = false;
			chosen = ogChosen;
		} else if (input == 2) { //change settings
			currentBackground = 1;
			resetBoard();
			ShellUniverse.changeBackground(11111); //reset to neutral background art
			chosen = 3;
			setupColourButtons();
		} else if (input == 3) {
			stop = true;
		}
	}

	private void easterEgg_mouseClicked(MouseEvent arg0) {
		if (currentBackground == 6) {
			System.out.println("You found an easter egg");
			this.lblEndGame.setForeground(Color.ORANGE);
		}
	}
	
	private void resetBoard() { //simple function to allow the board to display as empty and to allow moves on previously used squares
		for (int i = 0; i < 9; i++) {
			board[i] = null;
		}
		topLOccupied = false;
		topMOccupied = false;
		topROccupied = false;
		midLOccupied = false;
		midMOccupied = false;
		midROccupied = false;
		botLOccupied = false;
		botMOccupied = false;
		botROccupied = false;
		btnTopL.setText("");
		btnTopM.setText("");
		btnTopR.setText("");
		btnMidL.setText("");
		btnMidM.setText("");
		btnMidR.setText("");
		btnBotL.setText("");
		btnBotM.setText("");
		btnBotR.setText("");
	}
	private void displayVictory() { //set up the proper post game state depending on game result
		if (getIsVictorious(1) == false && getIsVictorious(2) == false) {
			currentBackground = 9; //Draw
			ShellUniverse.changeBackground(9);
			this.lblEndGame.setBounds(160, 22, SCREEN_WIDTH - 16, 40);
			this.lblEndGame.setText("Draw");
			setupPostGameButtons();
		} else if (getIsVictorious(1) == true) {
			currentBackground = 7; //X win
			ShellUniverse.changeBackground(7);
			this.lblEndGame.setBounds(125, 22, SCREEN_WIDTH - 16, 40);
			this.lblEndGame.setText("X WINS!");
		} else if (getIsVictorious(2) == true) {
			currentBackground = 8; //O win
			ShellUniverse.changeBackground(8);
			this.lblEndGame.setBounds(130, 22, SCREEN_WIDTH - 16, 40);
			this.lblEndGame.setText("O WINS!");
		} 
		setupPostGameButtons();
	}
	
	private void computerMove(int position) {
		String text = ""; //computer method to make moves without mouse clicks
		if (currentTurn == 1) {
			text = "X";
		} else if (currentTurn == 2) {
			text = "O";
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	//Below are the setup buttons function, they remove the previous board's buttons then add their own
	private void setupColourButtons() {
		setColourButtons(btnColour1, 35, 50, 83, 83, Color.BLACK);
		setColourButtons(btnColour2, 118, 50, 83, 83, Color.BLUE);
		setColourButtons(btnColour3, 201, 50, 83, 83, Color.CYAN);
		setColourButtons(btnColour4, 284, 50, 83, 83, Color.DARK_GRAY);
		setColourButtons(btnColour5, 35, 133, 83, 83, Color.GRAY);
		setColourButtons(btnColour6, 118, 133, 83, 83, Color.LIGHT_GRAY);
		setColourButtons(btnColour7, 201, 133, 83, 83, Color.GREEN);
		setColourButtons(btnColour8, 284, 133, 83, 83, Color.MAGENTA);
		if (easterEggFound == true) {
			setColourButtons(btnColour9, 35, 216, 83, 83, Color.ORANGE);
		}
		setColourButtons(btnColour10, 118, 216, 83, 83, Color.PINK);
		setColourButtons(btnColour11, 201, 216, 83, 83, Color.RED);
		setColourButtons(btnColour12, 284, 216, 83, 83, Color.WHITE);
		setColourButtons(btnColour13, 160, 299, 83, 83, Color.YELLOW);
	}
	
	
	private void setupFgColourButtons() {
		getContentPane().remove(btnColour1); // use to remove buttons
		getContentPane().remove(btnColour2);
		getContentPane().remove(btnColour3);
		getContentPane().remove(btnColour4);
		getContentPane().remove(btnColour5);
		getContentPane().remove(btnColour6);
		getContentPane().remove(btnColour7);
		getContentPane().remove(btnColour8);
		getContentPane().remove(btnColour9);
		getContentPane().remove(btnColour10);
		getContentPane().remove(btnColour11);
		getContentPane().remove(btnColour12);
		getContentPane().remove(btnColour13);
		
		setColourButtons(btnFgColour1, 35, 50, 83, 83, Color.BLACK);
		setColourButtons(btnFgColour2, 118, 50, 83, 83, Color.BLUE);
		setColourButtons(btnFgColour3, 201, 50, 83, 83, Color.CYAN);
		setColourButtons(btnFgColour4, 284, 50, 83, 83, Color.DARK_GRAY);
		setColourButtons(btnFgColour5, 35, 133, 83, 83, Color.GRAY);
		setColourButtons(btnFgColour6, 118, 133, 83, 83, Color.LIGHT_GRAY);
		setColourButtons(btnFgColour7, 201, 133, 83, 83, Color.GREEN);
		setColourButtons(btnFgColour8, 284, 133, 83, 83, Color.MAGENTA);
		if (easterEggFound == true) {
			setColourButtons(btnFgColour9, 35, 216, 83, 83, Color.ORANGE);
		}
		setColourButtons(btnFgColour10, 118, 216, 83, 83, Color.PINK);
		setColourButtons(btnFgColour11, 201, 216, 83, 83, Color.RED);
		setColourButtons(btnFgColour12, 284, 216, 83, 83, Color.WHITE);
		setColourButtons(btnFgColour13, 160, 299, 83, 83, Color.YELLOW);
	}
	
	private void setupArtButtons() {
		getContentPane().remove(btnFgColour1);
		getContentPane().remove(btnFgColour2);
		getContentPane().remove(btnFgColour3);
		getContentPane().remove(btnFgColour4);
		getContentPane().remove(btnFgColour5);
		getContentPane().remove(btnFgColour6);
		getContentPane().remove(btnFgColour7);
		getContentPane().remove(btnFgColour8);
		getContentPane().remove(btnFgColour9);
		getContentPane().remove(btnFgColour10);
		getContentPane().remove(btnFgColour11);
		getContentPane().remove(btnFgColour12);
		getContentPane().remove(btnFgColour13);
		
		setArtButtons(btnBgArt1, 50, 50, 100, 100);
		setArtButtons(btnBgArt2, 150, 50, 100, 100);
		setArtButtons(btnBgArt3, 250, 50, 100, 100);
		setArtButtons(btnBgArt4, 50, 150, 100, 100);
		setArtButtons(btnBgArt5, 150, 150, 100, 100);
		setArtButtons(btnBgArt6, 250, 150, 100, 100);
		setArtButtons(btnBgArt7, 50, 250, 100, 100);
		setArtButtons(btnBgArt8, 150, 250, 100, 100);
		setArtButtons(btnBgArt9, 250, 250, 100, 100);
	}
	
	private void setupOppButtons() {
		getContentPane().remove(btnBgArt1);
		getContentPane().remove(btnBgArt2);
		getContentPane().remove(btnBgArt3);
		getContentPane().remove(btnBgArt4);
		getContentPane().remove(btnBgArt5);
		getContentPane().remove(btnBgArt6);
		getContentPane().remove(btnBgArt7);
		getContentPane().remove(btnBgArt8);
		getContentPane().remove(btnBgArt9);
		
		setOppButtons(btnHumanOpp, 55, 50, 284, 150);
		setOppButtons(btnCPUOpp, 55, 200, 284, 150);
	}	
	
	private void setupTurnButtons() {
		getContentPane().remove(btnHumanOpp);
		getContentPane().remove(btnCPUOpp);
		
		setOppButtons(btnGoFirst, 55, 50, 284, 150);
		setOppButtons(btnGoSecond, 55, 200, 284, 150);
	}
	
	private void setupBoardButtons() {
		getContentPane().remove(btnHumanOpp);
		getContentPane().remove(btnCPUOpp);
		getContentPane().remove(btnGoFirst);
		getContentPane().remove(btnGoSecond);
		
		setButtons(btnTopL, 50, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnTopM, 150, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnTopR, 250, 50, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidL, 50, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidM, 150, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnMidR, 250, 150, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotL, 50, 250, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotM, 150, 250, 100, 100, foregroundColour, backgroundColour);
		setButtons(btnBotR, 250, 250, 100, 100, foregroundColour, backgroundColour);
	}
 	
	private void setupPostGameButtons() {
		getContentPane().remove(btnTopL);
		getContentPane().remove(btnTopM);
		getContentPane().remove(btnTopR);
		getContentPane().remove(btnMidL);
		getContentPane().remove(btnMidM);
		getContentPane().remove(btnMidR);
		getContentPane().remove(btnBotL);
		getContentPane().remove(btnBotM);
		getContentPane().remove(btnBotR);
		
		setOppButtons(btnReplay, 55, 60, 284, 100);
		setOppButtons(btnChangeSettings, 55, 160, 284, 100);
		setOppButtons(btnCloseGame, 55, 260, 284, 100);
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
				return true; //win
			}
		}
		for (int i = 0; i <= 7; i+=3) {
			if (board[i] == tile && board[i+1] == tile && board[i+2] == tile) {
				return true; // win
			}
		}
		if (board[0] == tile && board[4] == tile && board[8] == tile) {
			return true; // win
		}
		else if (board[2] == tile && board[4] == tile && board[6] == tile) {
			return true; //win
		}
		else if (board[0] != null && board[1] != null && board[2] != null && board[3] != null && board[4] != null && 
				board[5] != null && board[6] != null && board[7] != null && board[8] != null) {
			return false; //Draw
		}
		return false; //no state
	}
	
	public static int getCPUTurn() {
		return CPUTurn;
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
			scale *= 1.00;
		}
		if (keyboard.keyDown(113)) { //Controls for handling zoom in and zoom out, default is 1.01, 1.00 disables it
			scale /= 1.00;
		}
	}

	class DrawPanel extends JPanel { //Just don't mess with this

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
