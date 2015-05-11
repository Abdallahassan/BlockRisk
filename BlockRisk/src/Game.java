import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Game extends BasicGameState {
	
	private MouseInput mouseinput;
	private boolean AIsturn;        // true if it's the Ai's turn to play.
	private Map map;
	private Random random;
	private Territory[] aiOwned;
	private int[] unitsNotPlaced; //units the player can place in territories (non-AI)
	private int[] unitsNotPlacedAI;
	private int res; //player resources
	private int resAI; //AI resources
	private int[] cost={10,100,250}; //cost of infantry, vehicles and aircraft
	private Texture texture;


	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		try {
			this.texture = TextureLoader.getTexture("JPG", new FileInputStream(new File("res/headerFit.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		glEnable(GL_TEXTURE_2D);
		mouseinput = new MouseInput();
		AIsturn = false;
		map = new Map();
		//map.load();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		GL11.glColor3f(1f, 1f, 1f);
		texture.bind();
		drawTexture();
		map.draw(g);
	}
	
	private void drawTexture() {
		glBegin(GL_QUADS);  // Draw texture on specified coordinates.
		glTexCoord2f(0, 0);
		glVertex2i(0, 0);
		glTexCoord2f(1, 0);
		glVertex2i(Main.WIDTH, 0);
		glTexCoord2f(1, 1);
		glVertex2i(Main.WIDTH, 50);
		glTexCoord2f(0, 1);
		glVertex2i(0, 50);
		glEnd();
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
	 * @param units Player or AI units 
	 */
	private void buyUnits(int i,int amount,int[] units){
		res-=cost[i]*amount;
		units[i]+=amount;
	}
	
	//2)Placing Units
	/**
	 * Error handling needed !!
	 * @param i
	 * @param amount
	 * @param terr
	 */
	private void placeUnits(int i,int amount,Territory terr,int[] units){
		units[i]-=amount;
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
	private void attack(Territory from, Territory to){
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
	 * Only one reinforcement allowed per turn
	 * @param from
	 * @param to
	 * @param i
	 * @param amount
	 */
	private void reinforce(Territory from,Territory to,int i,int amount){
		from.removeUnits(i,amount);
		to.addUnits(i, amount);
	}
	
	//AI Logic and Methods Below.
	
	//1) Purchasing Strategies
	
	// How to divide given resources among units
	private void equalist(int resources){
		//calc available amount somehow
		int amount=resources/(cost[0]+cost[1]+cost[2]);
		buyUnits(0,amount/3,unitsNotPlacedAI);
		buyUnits(1,amount/3,unitsNotPlacedAI);
		buyUnits(2,amount/3,unitsNotPlacedAI);
	}
	/**
	 * Allocates all to unit[i]
	 * @param resources
	 */
	private void highAllocation(int resources,int i){
		int amount=resources/cost[i];
		buyUnits(i,amount,unitsNotPlacedAI);
	}
	
	/**
	 * Splits 50/50 between tanks and aircraft,
	 * no infantry purchased
	 */
	private void highOffence(int resources){
		int amount=resources/(cost[1]+cost[2]);
		buyUnits(1,amount/2,unitsNotPlacedAI);
		buyUnits(2,amount/2,unitsNotPlacedAI);
	}
	
		
	//2) Placing Strategies
	
	
	/**
	 * Places all allocated units in one territory
	 * @param amount
	 * @param terr
	 */
	private void blob(int amount,Territory terr){
		placeUnits(0,amount/3,terr,unitsNotPlacedAI);
		placeUnits(1,amount/3,terr,unitsNotPlacedAI);
		placeUnits(2,amount/3,terr,unitsNotPlacedAI);
	}
	//3) Attack Strategies
	/**
	 * Attacks weakest neighbour of given Territory terr
	 * @param terr
	 */
	private void attackWeakestNeighbour(Territory terr){
		Territory[] neighbours = terr.getNeighbours();
		int lowestSum=Integer.MAX_VALUE;
		int weakestTerr=-1; //an index
		for(int i=0;i<neighbours.length;i++){
			if(neighbours[i].numUnits()<lowestSum){
				lowestSum=neighbours[i].numUnits();
				weakestTerr=i;
			}
		}
		Territory toAttack=neighbours[weakestTerr];
		attack(terr,toAttack);
	}
	
	private void aiTurn(){
		int allocatedRes=resAI*4/5; //allocated 80% of owned resources this turn
		//maybe save between 0 and 20 percent randomly???
		int j=random.nextInt(3); 
		switch(j){	//selects purchasing strategy randomly
		
		case 0:
			equalist(allocatedRes);
		break;
		
		case 1:
			int i=random.nextInt(3); //0<=i<3
			highAllocation(allocatedRes,i); //purchases inf,veh or aircraft at random
		break;
			
		case 2:
			highOffence(allocatedRes);
		break;
		}
		//placement 
		int k=random.nextInt(2); 
		switch(k){	
		
		case 0:
			
		break;
		
		case 1:
			int a=random.nextInt(map.getAllTerritories().length);
			Territory terr=map.getAllTerritories()[a];
			int amount=numUnits(unitsNotPlacedAI);
			blob(amount,terr);
		break;
		}
		int n=0;
		//keeps changing n until it produces a territory owned by AI
		while(!map.getAllTerritories()[n].ownedbyAI()){
			n=random.nextInt(map.getAllTerritories().length);
		}
		attackWeakestNeighbour(map.getAllTerritories()[n]);		
		}
	
	
	private int numUnits(int[] units){
		int sum=0;
		for(int i=0;i<units.length;i++){
			sum+=units[i];
		}
		return sum;
	}
	
	
	
}
	
	
	
