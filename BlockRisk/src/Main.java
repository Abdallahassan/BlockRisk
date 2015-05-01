import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Main {
	
	private static final int HEIGHT = 480;
	private static final int WIDTH  = 640;
	private static final int FPS    = 5;
	int niter;                              // Numbers of iterations since the start.
	
	public Main() {
		// Initialize window
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); // static window size
			Display.setTitle("BlockRisk");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		// Initialize GL.
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		niter = 0;
		
		// Loop until program closes
		while (!Display.isCloseRequested()) { 
			glClear(GL_COLOR_BUFFER_BIT);
			
			logic(); // Input handling goes here.
			draw();  // Drawing to the display goes here.
			
			Display.update();
			Display.sync(FPS);
			niter++;
		}
		Display.destroy();
	}

	private void draw() {
		// TODO Auto-generated method stub
		
	}

	private void logic() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
