/**
 * Represents the units that the player can use for attacking and defending
 * territories. Vehicles travel on land only (such as tanks and jeeps).
 * @author Arsalan
 *
 */
public class Unit {
	UnitType type;
	boolean waterTravel;
	Territory territory;
	boolean isAlive;
	int attack;
	int defence; //more sturdy units like vehicles can take more damage
	int evasive; //more agile units are more likely to avoid enemy attack
	
	/* Infantry have low attack and defense and have moderate evasion but are cheap to produce.
	 * 
	 */
	
	public Unit(UnitType unitType){
		this.type=unitType;
		isAlive=true;
		switch(unitType){
		case INFANTRY:
			waterTravel=false;
		break;
		}
		
	}
	
	public void destroy(){
		isAlive=false;
	}
	
	public void changeTerritory(Territory newTerritory){
		this.territory=newTerritory;
	}
	
	
	
	
	
	
	
	
}
