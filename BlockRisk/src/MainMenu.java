
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
	
	Polygon poly;
	int time;
	MouseInput mouseinput;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		float[] cords = {0, 0, 50, 0, 100, 50, 75, 75, 50, 25};
		poly = new Polygon(cords);
		
		mouseinput = new MouseInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.red);
		g.fill(poly);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		time += delta;
		
		if (time > 3000) {     // After 3 seconds.
			sbg.enterState(2);
		}
		
		mouseinput.update();
		
	}

	@Override
	public int getID() {
		return 1;
	}

}
