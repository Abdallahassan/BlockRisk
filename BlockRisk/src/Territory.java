
public class Territory {
	
	private boolean highlighted;
	private boolean owner; // true if it's owned by the AI.
	private int units; //amount of units in this territory
	private int unitVal; //amount of units this territory generates each turn 
	
	
	public Territory(boolean owner) {
		this.owner = owner;
	}
	
	public boolean ownedbyAI() {
		return owner;
	}
	
	public void changeOwner() {
		owner = !owner;
	}
	
	public int getUnits(){
		return units;
	}
	
	public void setUnits(int remove){
		units-=remove;
	}

	public int getUnitVal() {
		return unitVal;
	}

}
