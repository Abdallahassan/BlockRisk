import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MapCreater extends BasicGameState {
	
	private MouseInput mouseinput;
	private Map map;
	private Polygon[] regions;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		map = new Map();
		regions = new Polygon[12];
		
		float[] cords1 = {0, 50, 200, 50, 200, 150, 0, 150};
		regions[0] = new Polygon(cords1);
		float[] cords2 = {0, 150, 200, 150, 200, 300, 0, 300};
		regions[1] = new Polygon(cords2);
		float[] cords3 = {0, 300, 200, 300, 200, 450, 0, 450};
		regions[2] = new Polygon(cords3);
		float[] cords4 = {200, 50, 350, 50, 350, 200, 200, 200};
		regions[3] = new Polygon(cords4);
		float[] cords5 = {200, 200, 350, 200, 350, 450, 200, 450};
		regions[4] = new Polygon(cords5);
		float[] cords6 = {350, 50, 550, 50, 550, 150, 350, 150};
		regions[5] = new Polygon(cords6);
		float[] cords7 = {350, 150, 550, 150, 550, 250, 350, 250};
		regions[6] = new Polygon(cords7);
		float[] cords8 = {350, 250, 550, 250, 550, 400, 350, 400};
		regions[7] = new Polygon(cords8);
		float[] cords9 = {350, 400, 800, 400, 800, 450, 350, 450};
		regions[8] = new Polygon(cords9);
		float[] cords10 = {550, 50, 700, 50, 700, 300, 550, 300};
		regions[9] = new Polygon(cords10);
		float[] cords11 = {550, 300, 800, 300, 800, 400, 550, 400};
		regions[10] = new Polygon(cords11);
		float[] cords12 = {700, 50, 800, 50, 800, 300, 700, 300};
		regions[11] = new Polygon(cords12);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(Main.UPPER_LEFT_CORNER.x, Main.UPPER_LEFT_CORNER.y, Main.LOWER_RIGHT_CORNER.x - Main.UPPER_LEFT_CORNER.x, Main.LOWER_RIGHT_CORNER.y - Main.UPPER_LEFT_CORNER.y);
		for (int i = 0; i < 12; i++) {
			if (i == 0 || i == 6)
				g.setColor(Color.blue);
			else if (i == 1 || i == 7)
				g.setColor(Color.cyan);
			else if (i == 2 || i == 8)
				g.setColor(Color.green);
			else if (i == 3 || i == 9)
				g.setColor(Color.red);
			else if (i == 4 || i == 10)
				g.setColor(Color.yellow);
			else if (i == 5 || i == 11)
				g.setColor(Color.orange);
			else
				g.setColor(Color.pink);
			
			g.fill(regions[i]);
		}
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
