 import java.util.ArrayList; 
 import java.util.Random;
 import java.awt.*;

public class Snake{


	private Point[] directions = { new Point(0,1), new Point(1,0), new Point(0,-1), new Point(-1,0) };

	private int speed;
	private Point direction;
	public ArrayList<Point> body;
	private int gameScale;

	

	public Snake(int x, int y, int scale){
		this.speed = 1;
		this.gameScale = scale;

		this.direction = this.directions[new Random().nextInt(4)];
		this.body = new ArrayList<Point>();

		this.body.add(new Point(x,y));
	}

	public void updateSpeed() {
		this.speed++;
	}

	public void updateDirection(Point newDirection){
		this.direction = newDirection;
	}

	public Point getDirection(){
		return this.direction;
	}

	public void move(){
		//move the whole snake
		//shift the snake depending on direction	

		if(this.body.size() > 1){
			for (int i = this.body.size()-1; i > 0; i--){
				this.body.get(i).x = this.body.get(i-1).x;
				this.body.get(i).y = this.body.get(i-1).y;
			}	
		}	

		this.body.get(0).x += this.gameScale * this.speed * this.direction.x;
		this.body.get(0).y += this.gameScale * this.speed * this.direction.y;
	}

	public void eatFood() {		
		//add another cell to snake

		int bodyLength = this.body.size();

		Point last = this.body.get(bodyLength - 1);

		//initialize with nonsense values
		Point newLast = new Point(-1 , -1); 

		if (this.direction.x == 0 && this.direction.y == 1){
			//snake is moving down
            //create body part up (smaller y value) from last element
			
			newLast = new Point(last.x, last.y - this.gameScale);

		} else if(this.direction.x == 0 && this.direction.y == 1){
			//snake is moving up
            //create food down from last element

			newLast = new Point(last.x, last.y + this.gameScale);

		} else if(this.direction.x == 1 && this.direction.y == 0){
			//snake is moving right
			//create food left of last element

			newLast = new Point(last.x - this.gameScale, last.y);

		} else if(this.direction.x == -1 && this.direction.y == 0){
			//snake is moving left
	        //create food right of last element

	        newLast = new Point(last.x + this.gameScale, last.y);
		}


		this.body.add(newLast);
	    //this.updateSpeed();
	}
}


