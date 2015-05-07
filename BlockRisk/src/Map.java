import java.util.ArrayList;
import java.util.List;


public class Map {
	
	private Territory[] territories;
	private List<IntPair> neighboors;
	
	public Map() {
		territories = new Territory[12]; // 12 territories
		neighboors = new ArrayList<IntPair>();
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
