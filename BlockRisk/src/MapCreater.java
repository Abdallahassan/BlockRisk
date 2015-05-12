
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
	private boolean boundary;
	private IntPair coord;
	int changeTo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		map = new Map();
		
		makeSquare(0, new IntPair(0, 50), new IntPair(195, 145));
		makeSquare(1, new IntPair(0, 150), new IntPair(195, 295));
		makeSquare(2, new IntPair(0, 300), new IntPair(195, 445));
		makeSquare(3, new IntPair(200, 50), new IntPair(345, 195));
		makeSquare(4, new IntPair(200, 200), new IntPair(345, 445));
		makeSquare(5, new IntPair(350, 50), new IntPair(545, 145));
		makeSquare(6, new IntPair(350, 150), new IntPair(545, 245));
		makeSquare(7, new IntPair(350, 250), new IntPair(545, 395));
		makeSquare(8, new IntPair(350, 400), new IntPair(795, 445));
		makeSquare(9, new IntPair(550, 50), new IntPair(695, 295));
		makeSquare(10, new IntPair(550, 300), new IntPair(795, 395));
		makeSquare(11, new IntPair(700, 50), new IntPair(795, 295));
		
		coord = new IntPair(0, 0);
	}
	
	private void makeSquare(int id, IntPair from, IntPair to) {
		for (int i = from.x; i <= to.x; i+=5)
			for (int j = from.y; j <= to.y; j+=5)
				map.changeOwner(new IntPair(i, j), id);
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
				boundary = false;
			} else if (Keyboard.getEventKey() == Keyboard.KEY_T) {
				boundary = true;
			} else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
				map.save();
			} else if (Keyboard.getEventKey() == Keyboard.KEY_L) {
				map.load();
			}
			
			changeTo = getKeyboardInput();
			
			//System.out.println("boundary: " + boundary + " changeTo: " + changeTo);
			
			coord = mouseinput.getCoordinates();
			if (coord.x >= 0 && coord.y >= 50 && coord.x < 800 && coord.y < 450 && mouseinput.leftClick()) {
				if (boundary)
					map.setBound(coord, true);
				else if (changeTo == 12 && !boundary)
					map.setBound(coord, false);
				else
					map.changeOwner(coord, changeTo);
			}
	}
	
	private int getKeyboardInput() {
		switch(Keyboard.getEventKey()) {
		case Keyboard.KEY_0:
			return 0;
		case Keyboard.KEY_1:
			return 1;
		case Keyboard.KEY_2:
			return 2;
		case Keyboard.KEY_3:
			return 3;
		case Keyboard.KEY_4:
			return 4;
		case Keyboard.KEY_5:
			return 5;
		case Keyboard.KEY_6:
			return 6;
		case Keyboard.KEY_7:
			return 7;
		case Keyboard.KEY_8:
			return 8;
		case Keyboard.KEY_9:
			return 9;
		case Keyboard.KEY_A:
			return 10;
		case Keyboard.KEY_B:
			return 11;
		case Keyboard.KEY_C:
			return 12;
		default:
			return changeTo;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
