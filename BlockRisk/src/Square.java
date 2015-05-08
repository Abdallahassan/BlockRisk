import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Square {
	
	private IntPair coord;
	private boolean isBoundary;
	private int belongsTo;
	
	private static final int SIDE = 10;
	
	public Square(IntPair coord) {
		this.coord = coord;
	}
	
	public void setBoundary(boolean val) {
		isBoundary = val;
	}
	
	public boolean boundaryStatus() {
		return isBoundary;
	}
	
	public void draw(Color color, Graphics g) {
		g.fillRect(coord.x, coord.y, SIDE, SIDE);
	}
	
	public void setOwnership(int t) {
		belongsTo = t;
	}
	
	public int getOwnership() {
		return belongsTo;
	}

}
