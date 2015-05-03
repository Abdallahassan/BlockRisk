import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
	
	Polygon poly;
	int time;
	int mousey;
	int mousex;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		float[] cords = {0, 0, 50, 0, 100, 50, 75, 75, 50, 25};
		poly = new Polygon(cords);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.red);
		g.draw(poly);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		time += delta;
		
		if (time > 3000) {     // After 3 seconds.
			sbg.enterState(2);
		}
		
		mousey = Main.HEIGHT - Mouse.getY() - 1;
		mousex = Mouse.getX();                    // Get coordinates of the mouse pointer
		
	}

	@Override
	public int getID() {
		return 1;
	}

}
