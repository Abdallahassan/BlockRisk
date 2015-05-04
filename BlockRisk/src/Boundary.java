import java.util.ArrayList;
import java.util.List;

// This class describes the boundary between two regions(polygons).

	public class Boundary {
		
		private int area1;
		private int area2;
		private List<IntPair> coords;
		
		public Boundary(int area1, int area2) {
			this.area1 = area1;
			this.area2 = area2;
			coords = new ArrayList<IntPair>();
		}
		
		public boolean adjacent(int x, int y) {
			return ((x == area1 && y == area2) || (x == area2 && y == area1));
		}
		
		public void addPoint(IntPair in) {
			coords.add(in);
		}
		
	}