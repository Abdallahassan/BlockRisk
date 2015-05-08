
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
	private boolean inputMode;
	private IntPair coord;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		map = new Map();
		
		makeSquare(0, new IntPair(0, 50), new IntPair(190, 140));
		makeSquare(1, new IntPair(0, 150), new IntPair(190, 290));
		makeSquare(2, new IntPair(0, 300), new IntPair(190, 440));
		makeSquare(3, new IntPair(200, 50), new IntPair(340, 190));
		makeSquare(4, new IntPair(200, 200), new IntPair(340, 440));
		makeSquare(5, new IntPair(350, 50), new IntPair(540, 140));
		makeSquare(6, new IntPair(350, 150), new IntPair(540, 240));
		makeSquare(7, new IntPair(350, 250), new IntPair(540, 390));
		makeSquare(8, new IntPair(350, 400), new IntPair(790, 440));
		makeSquare(9, new IntPair(550, 50), new IntPair(690, 290));
		makeSquare(10, new IntPair(550, 300), new IntPair(790, 390));
		makeSquare(11, new IntPair(700, 50), new IntPair(790, 290));
		
		inputMode = false;
		coord = new IntPair(0, 0);
	}
	
	private void makeSquare(int id, IntPair from, IntPair to) {
		for (int i = from.x; i <= to.x; i+=10)
			for (int j = from.y; j <= to.y; j+=10)
				map.changeOwner(new IntPair(i, j-50), id);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(Main.UPPER_LEFT_CORNER.x, Main.UPPER_LEFT_CORNER.y, Main.LOWER_RIGHT_CORNER.x - Main.UPPER_LEFT_CORNER.x, Main.LOWER_RIGHT_CORNER.y - Main.UPPER_LEFT_CORNER.y);
		map.draw(g);
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
