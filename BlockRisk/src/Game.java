
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.util.Random;

public class Game extends BasicGameState {
	
	private MouseInput mouseinput;
	private boolean AIsturn;        // true if it's the Ai's turn to play.
	private Map map;
	private Random random;
	private Territory[] aiOwned;


	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mouseinput = new MouseInput();
		AIsturn = false;
		map = new Map();
		map.load();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mouseinput.update();
		
	}
	
	/**
	 * Basic combat system where you take difference of roll*units 
	 * for both players. Then divide by k and remove that many units from 
	 * the loser.
	 * @param from
	 * @param to
	 */
	private void attack(Territory from, Territory to) {
		while(from.getUnits()>=0||to.getUnits()>=0){
			int k=5; //constant determining how many units are lost
			int diceFrom=random.nextInt(6)+1; //1 to 6
			int diceTo=random.nextInt(6)+1; //1 to 6
			int fromVal=diceFrom*from.getUnits();
			int toVal=diceFrom*to.getUnits();
			int diff=fromVal-toVal;
			if(diff>0){ //if attacker (from) wins battle
				to.setUnits(diff/k); //remove diff/k amount of units in To territory
			}
			else{
				from.setUnits(diff/k);
			}			
		}
		if(to.getUnits()<=0){ //if attack is succesful
			to.changeOwner();
		}
	}
	
	/**
	 * Updated territory attack system. The amount of units lost for the opponents
	 * will be U=(sum(A)*R)/(sum(D)*avg(E)*K). 
	 * sum(A)=sum of attack stat for owned units in territory
	 * sum(D)=sum of defence stat for enemy units in territory
	 * avg(E)=the average evasion for enemy units in territory
	 * R=a random integer between 1 and 10
	 * K=a constant that affects amount of units lost.
	 * This method is used when player/AI attack a territory. Defender reduces attackers
	 * units with this method. This returns amount of units that defender loses. Reduce
	 * this amount later from the territory using territory class' removeUnits() method
	 * @param from
	 * @param to
	 */
	private void attackNew(Territory from, Territory to){
		Random rand = new Random();
		int[] unitsFrom=from.getUnits();
		int[] unitsTo=from.getUnits();
		int sumA=from.sumAttack();
		int sumD=to.sumDefence();
		int r=rand.nextInt(10)+1; //1<=r<=10
		int k=1; //change later ???
		int avgE=to.averageEvasion();
		int removeSize=(sumA*r)/(sumD*avgE*k);
		to.removeUnits(0,removeUnits/3);
		to.removeUnits(1,removeUnits/3);
		to.removeUnits(2,removeUnits/3);
	}
	
	/**
	 * Generates units for the player (non-AI) depending on amount of territories
	 * captured and their value. 
	 */
	private int genUnitsPlayer(){ //need method like this for the AI too
		Territory[] terr=map.getAllTerritories();
		int resourceSum=0;
		for(int i=0;i<terr.length;i++){
			if (!terr[i].ownedbyAI()){ //if owned by player
				resourceSum+=terr[i].getResourceVal();
			}
		}
		return resourceSum;
	}

	@Override
	public int getID() {
		return 2;
	}
	
	//AI methods below
	/**
	 * Find the weakest neighbour to Territory terr
	 * @param i Represents index in owned
	 * @return Index of weakest neighbour to i
	 */
	private Territory weakestNeigbour(Territory terr){
		Territory[] neighbours = terr.getNeighbours();
		int lowestSum=Integer.MAX_VALUE;
		int weakestTerr=-1; //an index
		for(int i=0;i<neighbours.length;i++){
			if(neighbours[i].numUnits()<lowestSum){
				lowestSum=neighbours[i].numUnits();
				weakestTerr=i;
			}
		}
		return neighbours[weakestTerr];
	}
	
	/**
	 * AI chooses an owned territory at random and
	 * finds the weakest neighbour to the chosen
	 * territory. 
	 * @return The territories it wants to attack 
	 * and where it will attack from
	 */
	private Territory[] planAttack(){
		Territory[] fromTo = new Territory[2];
		int randIndex=random.nextInt(numOwned()); //used for choosing owned territory
		fromTo[0]=aiOwned[randIndex];
		fromTo[1]=weakestNeigbour(fromTo[0]);		
		return fromTo;
	}
	
	/**
	 * Testing
	 * @return
	 */
	private int numOwned(){
		int sum=0;
		for(int i=0;i<map.getAllTerritories().length;i++){
			if(map.getAllTerritories()[i].isOwner()){
				sum++;
			}
		}
		return sum;
	}
	
	/**
	 * 
	 */
	public void aiAttackPhase(){
		Territory[] fromTo=planAttack();
		attackNew(fromTo[0],fromTo[1]);
	}
	/**
	 * Keeps running until player loses
	 */
	public void play(){
		//Needs to get info from user bout attacks.
	}
}
	
	
	
