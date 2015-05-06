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
	int attack;
	int defence; //more sturdy units can take more damage
	int evasive; //more agile units are more likely to avoid enemy attack
	
	public Unit(UnitType unitType){
		isAlive=true;
		switch(unitType)
		
	}
	
	public void destroy(){
		isAlive=false;
	}
	
	public void changeTerritory(Territory newTerritory){
		this.territory=newTerritory;
	}
	
	
	
	
	
	
	
	
}
