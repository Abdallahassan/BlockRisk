/**
 * Represents the units that the player can use for attacking and defending
 * territories
 * @author Arsalan
 *
 */
public class Unit {
	UnitType type;
	boolean waterTravel;
	Territory territory;
	boolean isAlive;
	
	public Unit(UnitType unitType){
		isAlive=true;
		if(unitType==UnitType.SHIP||unitType==UnitType.AIRCRAFT){
			waterTravel=true;
		}
		else{
			waterTravel=false;
		}
	}
	
	public void destroy(){
		isAlive=false;
	}
	
	public void changeTerritory(Territory newTerritory){
		this.territory=newTerritory;
	}
	
	
	
	
	
	
	
	
}
