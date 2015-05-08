import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Map {
	
	private Territory[] territories;
	private List<IntPair> neighboors;
	private Square[][] squares;
	
	public Map() {
		territories = new Territory[12]; // 12 territories
		neighboors = new ArrayList<IntPair>();
		squares = new Square[80][40];
	}
	
	public void changeOwner(IntPair coord, int id) {
		squares[coord.x/10][coord.y/10].setOwnership(id);
	}
	
	public void setBound(IntPair coord, boolean val) {
		squares[coord.x/10][coord.y/10].setBoundary(val);
	}
	
	public void draw(Graphics g) {
		for (Square[] array: squares)
			for (Square s: array) {
				Color tmp;
				int i = s.getOwnership();
					if (i == 0 || i == 6)
						tmp = Color.blue;
					else if (i == 1 || i == 7)
						tmp = Color.cyan;
					else if (i == 2 || i == 8)
						tmp = Color.green;
					else if (i == 3 || i == 9)
						tmp = Color.red;
					else if (i == 4 || i == 10)
						tmp = Color.yellow;
					else if (i == 5 || i == 11)
						tmp = Color.orange;
					else
						tmp = Color.pink;
				s.draw(tmp, g);
			}
	}
	
	// Load and already saved game from file.
	public void load() {
		//TODO
	}
	
	// Save this map to save file.
	public void save() {
		//TODO
	}
	
	public Territory getTerritory(int id) {
		return territories[id];
	}
	
	public Territory[] getAllTerritories() {
		return territories;
	}
	
	public boolean adjacent(int id1, int id2) {
		return neighboors.contains(new IntPair(Math.min(id1, id2), Math.max(id1, id2)));
	}

}
