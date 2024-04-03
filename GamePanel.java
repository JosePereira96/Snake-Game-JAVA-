import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

	private final int frameWidth;
	private final int frameHeight;
	
	private final int rightBorder; 
	private final int leftBorder;

	private final int scale = 10;

	private boolean running;
	private Timer timer;
	private Random r;
	private Snake snake;
	private Point food;
	private int score;
	private boolean inMenu;
	private JButton startButton;

	private final int BUTTON_SIZE_X = 150;
	private final int BUTTON_SIZE_Y = 30;

	
	GamePanel(int frameWidth, int frameHeight){
		
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		this.rightBorder = (int) 4 * this.frameWidth / 5; 
		this.leftBorder = (int) this.frameWidth / 5;

		this.r = new Random();

		this.running = false;
		this.inMenu = true;

		this.setLayout(null);
		this.startButton = new JButton("Start Game");
		this.startButton.setBounds((this.frameWidth-BUTTON_SIZE_X)/2, this.frameHeight/2, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		
/*
		this.startButton.addActionListener(new ActionListener() {
	    	//@Override
	    	public void actionPerformed(ActionEvent e) {
	    		startButton = null;
				startGame();
	    	}
	    });
*/	    

		this.add(this.startButton);


		this.setPreferredSize(new Dimension(this.frameWidth,this.frameHeight));
		this.setBackground(Color.black);
		this.setFocusable(true);

		this.addKeyListener(new myKeyAdapter());	
		this.startButton.addActionListener(e -> {
			this.remove(this.startButton);
			//this.startButton = null;
			this.startGame();
		});

	}

	public void startGame(){
		this.inMenu = false;
		this.running = true;
		this.score = 0;

		this.timer = new Timer(120,this);
		this.timer.start();

		Point p = this.createRandomPoint();
		this.snake = new Snake(p.x, p.y, this.scale);

		this.summonFood();
	}

	public void summonFood(){
		this.food = this.createRandomPoint();
	}

	public Point createRandomPoint(){
		int x = this.scale * (int)((this.leftBorder + this.r.nextInt(this.rightBorder-this.leftBorder))/this.scale);
		int y = this.scale * (int)((this.r.nextInt(this.frameHeight))/this.scale);

		return (new Point(x,y));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.draw(g);
	}
	
	public void draw(Graphics g) {
		if(this.inMenu){
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			FontMetrics metrics = getFontMetrics(g.getFont());

			String projectString = "Project by: José Pereira";
			g.drawString(projectString, this.frameWidth - metrics.stringWidth(projectString) - 10, this.frameHeight - metrics.getHeight());
		} else if(this.running){
			//Score text
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: " + this.score, (this.frameWidth - metrics1.stringWidth("Score: " + this.score)) - 2*this.scale , 3*this.scale);

			//draw borders
			g.setColor(Color.white);
			//left
			g.fillRect(this.leftBorder,0,this.scale,this.frameHeight);
			//right
			g.fillRect(this.rightBorder,0,this.scale,this.frameHeight);
			//top
			g.fillRect(this.leftBorder,0,this.rightBorder-this.leftBorder,this.scale);
			//bottom
			g.fillRect(this.leftBorder,this.frameHeight-this.scale,this.rightBorder-this.leftBorder,this.scale);

			//draw food
			g.setColor(Color.red);
			g.fillOval(this.food.x, this.food.y, this.scale, this.scale);


			//draw snake
			g.setColor(Color.green);
			for(int i = 0; i < this.snake.body.size(); i++){
				g.fillRect(this.snake.body.get(i).x,this.snake.body.get(i).y,this.scale,this.scale);
			}
		} else if (!this.running){
			gameOver(g);
		}
	}
	
	public void checkFood(){
		Point snakeHead = this.snake.body.get(0);

		if(snakeHead.x == this.food.x && snakeHead.y == this.food.y){
			this.score++;
			this.snake.eatFood();
			this.summonFood();
		}
	}

	public void checkCollisions(){
		Point snakeHead = this.snake.body.get(0);

		//collision with borders
		if(snakeHead.x < this.leftBorder || snakeHead.x > this.rightBorder || snakeHead.y < 0 || snakeHead.y > this.frameHeight){
			this.running = false;
		}

		//collision with itself
		for (int i = 1; i < this.snake.body.size();i++){
			Point bodyPart = this.snake.body.get(i);

			if(snakeHead.x == bodyPart.x && snakeHead.y == bodyPart.y){
				this.running = false;
			}
		}


		if(!this.running){
			this.timer.stop();
		}

	}


	public void gameOver(Graphics g){

		g.setColor(Color.red);

		//Score text
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		String scoreString = "Score: " + this.score;
		g.drawString(scoreString, (frameWidth - metrics1.stringWidth(scoreString))/2, frameHeight/8);

		//Game Over text
		g.setFont( new Font("Arial",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		String gameOverString = "Game Over";
		g.drawString(gameOverString, (frameWidth - metrics2.stringWidth(gameOverString))/2, frameHeight/2);

		//Restart text
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		String restartString = "Restart? (Y/N)";
		g.drawString(restartString, (frameWidth - metrics3.stringWidth(restartString))/2, frameHeight/2 + metrics3.getHeight());

	}
	
	@Override
	public void actionPerformed(ActionEvent e){

		if(this.running){
			this.snake.move();
			this.checkFood();
			this.checkCollisions();
		}
		repaint();
	}



	public class myKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			//coordinates system start at top left corner;
			Point dir = snake.getDirection();

			if(running && e.getKeyCode() == KeyEvent.VK_W && dir.x != 0 && dir.y != 1){
				//up (negative y)
				snake.updateDirection(new Point(0,-1));

			} else if(running && e.getKeyCode() == KeyEvent.VK_A && dir.x != 1 && dir.y != 0){
				//left
				snake.updateDirection(new Point(-1,0));

			} else if(running && e.getKeyCode() == KeyEvent.VK_S && dir.x != 0 && dir.y != -1){
				//down (positive y)
				snake.updateDirection(new Point(0,1));

			} else if(running && e.getKeyCode() == KeyEvent.VK_D && dir.x != -1 && dir.y != 0){
				//right
				snake.updateDirection(new Point(1,0));

			} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				//quit game
				System.exit(0);

			} else if(!running  && e.getKeyCode() == KeyEvent.VK_Y){
				//restart game
				startGame();

			} else if(!running && e.getKeyCode() == KeyEvent.VK_N){
				//quit game
				System.exit(0);
			}
		}
	}
}