/**
 * Represents the units that the player can use for attacking and defending
 * territories. Vehicles travel on land only (such as tanks and jeeps).
 * @author Arsalan
 *
 */
public class Unit {
	
	private UnitType type;
	private boolean waterTravel;
	private Territory territory;
	private boolean isAlive;
	private int attack;
	private int defence; //more sturdy units like vehicles can take more damage
	private int evasion; //more agile units are more likely to avoid enemy attack
	
		
	public Unit(UnitType unitType,Territory territory){
		this.type=unitType;
		isAlive=true;
		this.territory=territory;
		switch(unitType){
		case INFANTRY:
			waterTravel=false;
		break;
		case VEHICLE:
			waterTravel=false;
		break;
		case AIRCRAFT:
			waterTravel=true;
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
