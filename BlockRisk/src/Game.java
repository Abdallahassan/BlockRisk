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
	//private Territory[] aiOwned;
	private int[] unitsNotPlaced; //units the player can place in territories (non-AI)
	private int[] unitsNotPlacedAI;
	private int res; //player resources
	private int resAI; //AI resources
	private int[] cost={10,100,250};
	private Texture texture;
	private boolean gameOver;
	
	private int actionFrom;
	private boolean attackMode;
	private boolean inuserTerritory;
	private final static IntPair saveButtonFrom = new IntPair(310, 5);
	private final static IntPair saveButtonTo   = new IntPair(488, 45);
	private final static IntPair mainMenuButtonFrom = new IntPair(50, 5);
	private final static IntPair mainMenuButtonTo   = new IntPair(230, 45);
	private final static IntPair buyButtonFrom = new IntPair(565, 5);
	private final static IntPair buyButtonTo   = new IntPair(745, 45);
	private boolean buying;
	private Picbox soldier;
	private String[] inputArgs;
	private final static IntPair soldierFrom = new IntPair(354, 455);
	private final static IntPair soldierTo   = new IntPair(450, 495);
	private final static IntPair vehicleFrom = new IntPair(500, 455);
	private final static IntPair vehicleTo   = new IntPair(610, 495);
	private final static IntPair airplaneFrom = new IntPair(650, 455);
	private final static IntPair airplaneTo   = new IntPair(750, 495);

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		try {
			this.texture = TextureLoader.getTexture("JPG", new FileInputStream(new File("res/headerNew.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		glEnable(GL_TEXTURE_2D);
		mouseinput = new MouseInput();
		AIsturn = false;
		map = new Map();
		map.load();
		if (Main.isNewGame())
			//map.initNewGame();
		
		actionFrom = -1;
		attackMode = false;
		
		unitsNotPlaced = new int[3];
		
		inputArgs = new String[18];
		random = new Random(System.currentTimeMillis());                                                                                                                                                                                   //
		soldier = new Picbox(new IntPair(0,450), new IntPair(800,500), "res/FooterNew.jpg", new IntPair[]{new IntPair(60,460), new IntPair(170,460), new IntPair(305,460), new IntPair(425,460), new IntPair(585,460), new IntPair(730,460), new IntPair(70,90), new IntPair(75, 210), new IntPair(55, 355), new IntPair(265,110), new IntPair(255,300), new IntPair(445,65), new IntPair(425, 190), new IntPair(430,310), new IntPair(635,400), new IntPair(580,130), new IntPair(715,305), new IntPair(710,120)});
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		GL11.glColor3f(1f, 1f, 1f);
		texture.bind();
		drawTexture();
		map.draw(g);
		
		//numofVehicles;
		//numofAircraft;
		
		if (actionFrom < 0 || map.ownedbyAI(actionFrom)) {
			inputArgs[0] = " ";
			inputArgs[1] = " ";
			inputArgs[2] = " ";
		} else {
			inputArgs[0] = Integer.toString(map.getTerritory(actionFrom).getSomeUnit(0));
			inputArgs[1] = Integer.toString(map.getTerritory(actionFrom).getSomeUnit(1));
			inputArgs[2] = Integer.toString(map.getTerritory(actionFrom).getSomeUnit(2));
			inputArgs[3] = Integer.toString(unitsNotPlaced[0]);
			inputArgs[4] = Integer.toString(unitsNotPlaced[1]);
			inputArgs[5] = Integer.toString(unitsNotPlaced[2]);
		}
		for (int i=6; i < 18; i++)
			inputArgs[i] = Integer.toString(map.getTerritory(i-6).numUnits());
		
		soldier.draw(inputArgs, Color.yellow);
		
		if (attackMode) {
			// draw something.
		}
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

		if (mouseinput.leftClick()) {
			System.out.println(mouseinput.getCoordinates());
			/*if (attackMode) {
			// do something.
		}*/
		if (mouseinput.insideRect(Main.UPPER_LEFT_CORNER, Main.LOWER_RIGHT_CORNER) && !AIsturn) { // change to else if later
			 inuserTerritory = !map.ownedbyAI(map.getTerritoryID(mouseinput.getCoordinates()));
			 if (inuserTerritory) {
				 actionFrom = map.getTerritoryID(mouseinput.getCoordinates());
				 map.setHighlight(actionFrom);
			 } else if (actionFrom >= 0 && actionFrom < 12) {
				 attack(map.getTerritory(actionFrom), map.getTerritory(mouseinput.getCoordinates()));
			 }
		}
		else if (mouseinput.insideRect(saveButtonFrom, saveButtonTo))
			map.save();
		else if (mouseinput.insideRect(mainMenuButtonFrom, mainMenuButtonTo)) {
			map.save();
			sbg.enterState(1);
		} else if (mouseinput.insideRect(saveButtonFrom, saveButtonTo)) {
			buying = true;
		} else if (mouseinput.insideRect(soldierFrom, soldierTo) && actionFrom >= 0) {
			placeUnits(0,1,map.getTerritory(actionFrom));
		} else if (mouseinput.insideRect(vehicleFrom, vehicleTo) && actionFrom >= 0) {
			placeUnits(1,1,map.getTerritory(actionFrom));
		} else if (mouseinput.insideRect(airplaneFrom, airplaneTo) && actionFrom >= 0) {
			placeUnits(2,1,map.getTerritory(actionFrom));
		}
		}
		
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
		private int genResPlayer(){ 
			Territory[] terr=map.getAllTerritories();
			int resourceSum=0;
			for(int i=0;i<terr.length;i++){
				if (!terr[i].ownedbyAI()){ //if owned by player
					resourceSum+=terr[i].getResourceVal();
				}
			}
			return resourceSum;
		}
		
		/**
		 * Generates resources for the AI depending on resource value of territories. 
		 */
		private int genResAI(){ //need method like this for the AI too
			Territory[] terr=map.getAllTerritories();
			int resourceSum=0;
			for(int i=0;i<terr.length;i++){
				if (terr[i].ownedbyAI()){ //if owned by AI
					resourceSum+=terr[i].getResourceVal();
				}
			}
			return resourceSum;
		}
		
		//1) Buying Units
		
		/**
		 * Need error handling here (buy too much, res=0)
		 * @param i Unit you want to purchase 
		 * @param amount: The amount of that unit 
		 * @param units: unitsNotPlaced or unitsNotPlacedAI, these
		 * arrays store the units so that they can be placed later. 
		 */
		private void buyUnitsPlayer(int i,int amount){
			if(cost[i]*amount>res){
				//ALERT MESSAGE HERE,ILLEGAL AMOUNT
			}
			else{
			res-=cost[i]*amount;
			unitsNotPlaced[i]+=amount;
			}
		}
		
		/**
		 * Buys units for the AI
		 * @param i
		 * @param amount
		 */
		private void buyUnitsAI(int i,int amount){
			if(cost[i]*amount>resAI){
				//ALERT MESSAGE HERE,ILLEGAL AMOUNT
			}
			else{
			resAI-=cost[i]*amount;
			unitsNotPlacedAI[i]+=amount;
			}
		}
		
		//2)Placing Units
		/**
		 * Error handling needed !!
		 * @param i Type of unit
		 * @param amount
		 * @param terr The territory you want to place it in.
		 * @param unitsNotPlaced or unitsNotPlacedAI. Takes units from these arrays
		 */
		private void placeUnits(int i,int amount,Territory terr){
			if(amount>unitsNotPlaced[i]){
				//ERROR MESSAGE
			}
			else{
			unitsNotPlaced[i]-=amount;
			terr.addUnits(i,amount);
			}
		}
		
		/**
		 * Error handling needed !!
		 * @param i Type of unit
		 * @param amount
		 * @param terr The territory you want to place it in.
		 * @param unitsNotPlaced or unitsNotPlacedAI. Takes units from these arrays
		 */
		private void placeUnitsAI(int i,int amount,Territory terr){
			if(amount>unitsNotPlacedAI[i]){
				//ERROR MESSAGE
			}
			else{
			unitsNotPlacedAI[i]-=amount;
			terr.addUnits(i,amount);
			}
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
			if(sumD==0){
				sumD=1;
			}
			if(avgE==0){
				avgE=1;
			}
			int removeSize=(sumA*r)/(sumD*avgE*k);			
			to.removeUnits(0,removeSize/3);
			to.removeUnits(1,removeSize/3);
			to.removeUnits(2,removeSize/3);
		}
		
		
		
		/**
		 * Same as attack method but for the defending
		 * team.
		 * @param from
		 * @param to
		 */
		private void defend(Territory from,Territory to){
			int sumA=to.sumAttack();
			int sumD=from.sumDefence();
			int r=random.nextInt(10)+1; //1<=r<=10
			int k=1; //change later ???
			int avgE=from.averageEvasion();
			if(sumD==0){
				sumD=1;
			}
			if(avgE==0){
				avgE=1;
			}
			int removeSize=(sumA*r)/(sumD*avgE*k);
			from.removeUnits(0,removeSize/3);
			from.removeUnits(1,removeSize/3);
			from.removeUnits(2,removeSize/3);
		}
		
		/**
		 * Combat between player and AI territories
		 * @param from Attackers territory
		 * @param to
		 * @param resum Resumes the attack, if false, returns to map
		 * @return win: If the attacker won, win is True
		 * 
		 */
		private boolean combat(Territory from,Territory to,boolean resume){
			boolean win=false;
			while(resume){ //IF ATTACKER WINS, LEFT OVER UNITS STAY IN "to" TERRITORY
				attack(from,to);
				defend(to,from);
				if(numUnits(to.getUnits())<=0){//defender loses
					win=true;
					to.changeOwner();
				}
				if(numUnits(from.getUnits())<=0){//attacker loses
					//leave one infantry in the 
					from.setUnits(0,1);
					from.setUnits(1,0);
					from.setUnits(1,0);
				}
				//needs some mouse input here to change resume !!!
			}
			return win;
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
			buyUnitsAI(0,amount/3);
			buyUnitsAI(1,amount/3);
			buyUnitsAI(2,amount/3);
		}
		/**
		 * Allocates all to unit[i]
		 * @param resources
		 */
		private void highAllocation(int resources,int i){
			int amount=resources/cost[i];
			buyUnitsAI(i,amount);
		}
		
		/**
		 * Splits 50/50 between tanks and aircraft,
		 * no infantry purchased
		 */
		private void highOffence(int resources){
			int amount=resources/(cost[1]+cost[2]);
			buyUnitsAI(1,amount/2);
			buyUnitsAI(2,amount/2);
		}
		
			
		//2) Placing Strategies
		
		
		/**
		 * Places all allocated units in one territory
		 * @param amount
		 * @param terr
		 */
		private void blob(int amount,Territory terr){
			placeUnitsAI(0,amount/3,terr);
			placeUnitsAI(1,amount/3,terr);
			placeUnitsAI(2,amount/3,terr);
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
			combat(terr,toAttack,true);
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
		
			int a=random.nextInt(map.getAllTerritories().length);
			Territory terr=map.getAllTerritories()[a];
			int amount=numUnits(unitsNotPlacedAI);
			blob(amount,terr);
			
			
			int n=0;
			//keeps changing n until it produces a territory owned by AI
			while(!map.getAllTerritories()[n].ownedbyAI()){
				n=random.nextInt(map.getAllTerritories().length);
			}
			attackWeakestNeighbour(map.getAllTerritories()[n]);	//initiate an attack, no retreat
			}
		
		/**
		 * Finds total number of units. Can be used on terr.getUnits().
		 * @param units
		 * @return
		 */
		private int numUnits(int[] units){
			int sum=0;
			for(int i=0;i<units.length;i++){
				sum+=units[i];
			}
			return sum;
		}
		
		/**
		 * Checks if the game is over. If it is, a game over pop up menu
		 * will show up saying whether you won or lost. Game is over when 
		 * all territories are owned by player or AI
		 */
		private void gameOverCheck(){
			Territory[] terr=map.getAllTerritories();
			int sumOwnedAI=0;
			for(int i=0;i<terr.length;i++){
				if(terr[i].ownedbyAI()){
					sumOwnedAI++;
				}
			}
			if(sumOwnedAI==0){ //player wins
				gameOver=true;
			}
			else if(sumOwnedAI==terr.length){ //AI wins
				gameOver=true;
			}
		}
		
		
	}
