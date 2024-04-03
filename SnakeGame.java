import java.awt.event.*;

public class SnakeGame {
	
	public static void main(String[] args) {
		//enable OpenGL to get accelerated performance and fix laggy animations
		//it's enabled by default in Windows, but not in Linux
		System.setProperty("sun.java2d.opengl", "true");
		
		new GameFrame();
	}
}