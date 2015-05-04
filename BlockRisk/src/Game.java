
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	
	MouseInput mouseinput;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mouseinput.update();
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
