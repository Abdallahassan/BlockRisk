
public class Map {
	
	private static final IntPair UPPER_LEFT_CORNER = new IntPair(0, 50);
	private static final IntPair LOWER_RIGHT_CORNER = new IntPair(Main.WIDTH, Main.HEIGHT);
	
	private Territory[] territories;
	
	public Map() {
		territories = new Territory[12]; // 12 territories
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

}
