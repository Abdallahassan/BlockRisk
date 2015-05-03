import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	
	// This class describes the boundary between two regions(polygons).
	private static class Boundary {
		int area1;
		int area2;
		private List<Integer> xcords;
		private List<Integer> ycords;
		
		public Boundary(int area1, int area2) {
			this.area1 = area1;
			this.area2 = area2;
			xcords = new ArrayList<Integer>();
			ycords = new ArrayList<Integer>();
		}
		
		public boolean adjacent(int x, int y) {
			return ((x == area1 && y == area2) || (x == area2 && y == area1));
		}
		
		public void addPoint(int x, int y) {
			xcords.add(x);
			ycords.add(y);
		}
	}
	
	int mousex;
	int mousey;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mousey = Main.HEIGHT - Mouse.getY() - 1;
		mousex = Mouse.getX();                    // Get coordinates of the mouse pointer
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
