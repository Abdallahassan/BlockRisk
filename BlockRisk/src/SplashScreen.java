import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class SplashScreen extends BasicGameState {
	
	Polygon poly;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		float[] cords = {0, 0, 50, 0, 100, 50, 75, 75, 50, 25};
		poly = new Polygon(cords);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setColor(Color.blue);
		arg2.draw(poly);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return 0;
	}


	

}
