import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JFrame{

	private final int frameWidth = 1300;
	private final int frameHeight = 700;

	

	//private HomePage homePage;
	//private GamePanel gamePanel;

	//private JButton startButton;

	
	GameFrame(){
		this.add(new GamePanel(frameWidth,frameHeight));
		/*

		this.homePage = new HomePage(frameWidth,frameHeight);
		this.gamePanel = new GamePanel(frameWidth,frameHeight);

		this.startButton = new JButton("Start Game");
		this.startButton.setBounds((this.frameWidth-BUTTON_SIZE_X)/2, this.frameHeight/2, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		this.startButton.addActionListener(new ActionListener() {
			
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		System.out.println("click! ");
	    		System.out.println(this);

	    		//homePage.dispose();
	    		//super.add(gamePanel);
	    		//homePage.setVisible(false);
	    		this.remove(this.homePage);
	    		this.add(this.gamePanel);
	    		this.gamePanel.startGame();
	    		//gamePanel.setVisible(true);

	    		//this.dispose()
		        //super.add(new GamePanel(frameWidth,frameHeight));
	    	}
	    	
		});


		this.homePage.add(this.startButton);
		this.homePage.setVisible(true);

		//gamePanel.setVisible(false);

		//this.add(gamePanel);
		this.add(this.homePage);
		*/

		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}