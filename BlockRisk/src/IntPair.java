/**
 * Int Pair class...
 * @author Abdallah
 *
 */
public class IntPair {
	public int x;
	public int y;
	
	public IntPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public IntPair add(int x, int y) {
		return new IntPair(x+this.x, y+this.y);
	}

}
