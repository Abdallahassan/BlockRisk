
public class Map {
	
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
