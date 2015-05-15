public class Territory {
	
	private boolean owner; // true if it's owned by the AI.
	private int[] units={0,0,0}; //units[0]=number of infantry,units[1]=number of vehicles, units[2]=aircraft 
	private int resourceVal; //amount of resources this territory generates each turn 
	private Territory[] neighbours; //Not sure if needed.  Abdallah: Keep it for now.
	private final int[] attStats={1, 6, 4, 8}; //divided all stats by 10 to keep it 2 digit.
	private final int[] defStats={1, 7, 4, 3};
	private final int[] evStat={3,1,4,6};
	private int startingRes=10000;
	
	public Territory(boolean owner) {
		this.owner = owner;
		this.resourceVal=startingRes;
	}
	
	// Use only by Map.
	public void setNeighbours(Territory[] neighbours) {
		this.neighbours = neighbours;
	}
	
	
	public boolean ownedbyAI() {
		return owner;
	}
	
	public void changeOwner() {
		owner = !owner;
	}
	
	public void setOwner(boolean b) {
		owner = b;
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
	
	/**
	 * Adds certain type of units from territory. 
	 * index 0,1,2 = infantry,vehicles,aircraft
	 * 
	 * @param index
	 * @param remove
	 */
	public void addUnits(int index,int add){
		units[index]+=add;
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
		if (sumUnits == 0)
			return 1;        // Avoid division by zero.
		else
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
	
	public int getSomeUnit(int index) {
		return units[index];
	}
	
	/**
	 * Sets unit to a specific amount
	 * @param i
	 * @param amount
	 */
	public void setUnits(int i,int amount){
		units[i]=amount;
	}

}
