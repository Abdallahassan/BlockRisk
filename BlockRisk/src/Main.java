import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame {
	
	public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;
	
	private static final int HEIGHT = 500;
	private static final int WIDTH  = 800;
	private static final int FPS    = 5;

	public Main(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main("BlockRisk"));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(true);
            app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new SplashScreen());
		this.addState(new MainMenu());
		this.addState(new Game());
		this.enterState(SPLASHSCREEN);
	}

}
