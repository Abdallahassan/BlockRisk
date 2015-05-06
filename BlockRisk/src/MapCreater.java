import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MapCreater extends BasicGameState {
	
	private MouseInput mouseinput;
	private Map map;
	private Boundary[][] borders;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		map = new Map();
		borders = new Boundary[13][13];
		
		for (int i = 0; i < 13; i++)
			for (int j = 0; j < 13; j++)
				borders[i][j] = new Boundary(i, j);
		
		borders[0][1].addPoint(Main.UPPER_LEFT_CORNER);
		borders[0][1].addPoint(Main.UPPER_LEFT_CORNER.add(200, 0));
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
