import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SplashScreen extends BasicGameState {
	
	Polygon poly;
	private int time;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		float[] cords = {0, 0, 50, 0, 100, 50, 75, 75, 50, 25};
		poly = new Polygon(cords);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.blue);
		g.draw(poly);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		time += delta;
		
		if (time > 6000) {     // After 6 seconds.
			sbg.enterState(1);
		}
		
	}

	@Override
	public int getID() {
		return 0;
	}


	

}
