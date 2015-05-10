import org.lwjgl.input.Mouse;

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
	 * @param ulc Upper left corner
	 * @param lrc Lower right corner
	 * @return true if the coordinates are inside the given rectangle
	 */
	public boolean insideRect(IntPair ulc, IntPair lrc) {
		return coordinates.x > ulc.x && coordinates.x < lrc.x && coordinates.y > ulc.y && coordinates.y < lrc.y;
	}

}
