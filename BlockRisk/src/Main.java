import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame {
	
	public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;
    public static final int MAPCREATER   = 3;
	
	public static final int  HEIGHT = 500;
	public static final int WIDTH  = 800;
	private static final int FPS    = 3;
	
	public static final IntPair UPPER_LEFT_CORNER = new IntPair(0, 50);
	public static final IntPair LOWER_RIGHT_CORNER = new IntPair(Main.WIDTH, Main.HEIGHT-50);
	
	private static boolean newGame;

	public Main(String name) {
		super(name);
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
		this.addState(new MapCreater());
		this.enterState(MAPCREATER);
	}
	
	public static boolean isNewGame() {
		return newGame;
	}

	public static void setNewGame(boolean b) {
		newGame = b;
	}

}
