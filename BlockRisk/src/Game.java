
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
	private int[] unitsNotPlaced; //units the player can place in territories (non-AI)
	private int res; //player resources
	private int resAI;
	private int[] cost={10,100,250};


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

	@Override
	public int getID() {
		return 2;
	}
	
	
	//Game Logic Section Below
	/*
	 * There are four stages to each players turn. 
	 * 1)Buying Units (after gaining resources)
	 * 2)Placing Units
	 * 3)Attacking territories
	 * 4)Reinforcing a territory (move units from one owned terr to another)
	 */
	
	//0) Gaining resources
	/**
	 * Generates resources for the player (non-AI) depending on resource value of territories. 
	 */
	private int genResPlayer(){ //need method like this for the AI too
		Territory[] terr=map.getAllTerritories();
		int resourceSum=0;
		for(int i=0;i<terr.length;i++){
			if (!terr[i].ownedbyAI()){ //if owned by player
				resourceSum+=terr[i].getResourceVal();
			}
		}
		return resourceSum;
	}
	
	//1) Buying Units
	
	/**
	 * Need error handling here (buy too much, res=0)
	 * @param i
	 */
	private void buyUnits(int i,int amount){
		res-=cost[i]*amount;
		unitsNotPlaced[i]+=amount;
	}
	
	//2)Placing Units
	/**
	 * Error handling needed !!
	 * @param i
	 * @param amount
	 * @param terr
	 */
	private void placeUnits(int i,int amount,Territory terr){
		unitsNotPlaced[i]-=amount;
		terr.addUnits(i,amount);
	}
	
	//3)Attacking
		
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
		int[] unitsFrom=from.getUnits();
		int[] unitsTo=from.getUnits();
		int sumA=from.sumAttack();
		int sumD=to.sumDefence();
		int r=random.nextInt(10)+1; //1<=r<=10
		int k=1; //change later ???
		int avgE=to.averageEvasion();
		int removeSize=(sumA*r)/(sumD*avgE*k);
		to.removeUnits(0,removeSize/3);
		to.removeUnits(1,removeSize/3);
		to.removeUnits(2,removeSize/3);
	}
	//4) Reinforce
	/**
	 * 
	 * @param from
	 * @param to
	 * @param i
	 * @param amount
	 */
	private void reinforce(Territory from,Territory to,int i,int amount){
		from.removeUnits(i,amount);
		to.addUnits(i, amount);
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
	
	
	
