
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MapCreater extends BasicGameState {
	
	private MouseInput mouseinput;
	private Map map;
	private Territory[] regions;
	private boolean inputMode;
	private IntPair coord;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		map = new Map();
		regions = new Territory[12];
		
		makeSquare(0, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(1, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(2, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(3, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(4, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(5, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(6, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(7, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(8, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(9, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(10, new IntPair(0, 0), new IntPair(0, 0));
		makeSquare(11, new IntPair(0, 0), new IntPair(0, 0));
		
		inputMode = false;
		coord = new IntPair(0, 0);
	}
	
	private void makeSquare(int id, IntPair from, IntPair to) {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(Main.UPPER_LEFT_CORNER.x, Main.UPPER_LEFT_CORNER.y, Main.LOWER_RIGHT_CORNER.x - Main.UPPER_LEFT_CORNER.x, Main.LOWER_RIGHT_CORNER.y - Main.UPPER_LEFT_CORNER.y);
		Color tmp;
		for (int i = 0; i < 12; i++) {
			if (i == 0 || i == 6)
				tmp = Color.blue;
			else if (i == 1 || i == 7)
				tmp = Color.cyan;
			else if (i == 2 || i == 8)
				tmp = Color.green;
			else if (i == 3 || i == 9)
				tmp = Color.red;
			else if (i == 4 || i == 10)
				tmp = Color.yellow;
			else if (i == 5 || i == 11)
				tmp = Color.orange;
			else
				tmp = Color.pink;
			
			regions[i].draw(g, tmp);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mouseinput.update();
		
			if (Keyboard.getEventKey() == Keyboard.KEY_F) {
				inputMode = false;
			} else if (Keyboard.getEventKey() == Keyboard.KEY_T) {
				inputMode = true;
			}
			
						
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
