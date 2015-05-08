
public class Territory {
	
	private boolean highlighted;
	private boolean owner; // true if it's owned by the AI.
	private int[] units; //amount of units in this territory
	private int resourceVal; //amount of resources this territory generates each turn 
	private Territory[] neighbours; 
	
	public Territory(boolean owner) {
		this.owner = owner;
	}
	
	public boolean ownedbyAI() {
		return owner;
	}
	
	public void changeOwner() {
		owner = !owner;
	}
	
	public int[] getUnits(){
		return units;
	}
	
	
	public int getResourceVal() {
		return resourceVal;
	}
	
	/**
	 * Removes certain type of units from territory. 
	 * index 0,1,2 = infantry,vehicles,aircraft
	 * 
	 * @param index
	 * @param remove
	 */
	public void removeUnits(int index,int remove){
		units[index]-=remove;
	}

	
	public int sumAttack(){
		int sum=0;
		for(int i=0;i<units.length;i++){
			int numUnit=units[i];
			int prod=numUnit*attStats[i];
			sum+=prod;
		}
		return sum;
	}
	
	public int sumDefence(){
		int sum=0;
		for(int i=0;i<units.length;i++){
			int numUnit=units[i];
			int prod=numUnit*defStats[i];
			sum+=prod;
		}
		return sum;
	}
	
	public int averageEvasion(){
		int sumEv=0;
		int sumUnits=0;
		for(int i=0;i<units.length;i++){
			int numUnit=units[i];
			sumUnits+=numUnit;
			sumEv+=numUnit*evStat[i];
		}		
		return sumEv/sumUnits;
	}
	
	public boolean isNeighbour(Territory to){
		for(int i=0;i<neighbours.length;i++){
			if(neighbours[i].equals(to)){
				return true;
			}			
		}		
		return false;
	}
	
	public int numUnits(){
		int sum=0;
		for(int i=0;i<units.length;i++){
			sum+=units[i];
		}
		return sum;
	}
	
	public Territory[] getNeighbours(){
		return neighbours;
	}

}
