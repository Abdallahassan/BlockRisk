import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.Polygon;

public class MouseInput {
	
	private IntPair coordinates;
	
	public MouseInput() {
		coordinates = new IntPair(0, 0);
	}
	
	public void update() {
		coordinates.y = Main.HEIGHT - Mouse.getY() - 1;
		coordinates.x = Mouse.getX();                    // Get coordinates of the mouse pointer
	}
	
	/**
	 * @return true if left button is down.
	 */
	public boolean leftClick() {
		return Mouse.isButtonDown(0);
	}
	
	public IntPair getCoordinates() {
		return coordinates;
	}
	
	/**
	 * @param poly
	 * @return true if the mouse is hovering above the given region.
	 */
	public boolean insideRegion(Polygon poly) {
		return poly.contains(coordinates.x, coordinates.y);
	}

}
