import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Square {
	
	private IntPair coord;
	private boolean isBoundary;
	private int belongsTo;
	
	public static final int SIDE = 5;
	
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
		g.setColor(color);
		g.fillRect(coord.x, coord.y, SIDE, SIDE);
	}
	
	public void setOwnership(int t) {
		belongsTo = t;
	}
	
	public int getOwnership() {
		return belongsTo;
	}

	public IntPair getCoord() {
		return coord;
	}

	public void setCoord(IntPair coord) {
		this.coord = coord;
	}

	public void setIsBoundary(boolean n){
		isBoundary=n;
	}
	
	public void setBelongsTo(int n){
		belongsTo=n;
	}
}
