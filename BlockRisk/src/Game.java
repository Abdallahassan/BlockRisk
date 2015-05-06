
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.util.Random;

public class Game extends BasicGameState {
	
	private MouseInput mouseinput;
	private boolean AIsturn;        // true if it's the Ai's turn to play.
	private Map map;
	private Random random;

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
		for (Territory t: map.getAllTerritories())
			t.draw(g);
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
	 * Generates units for the player (non-AI) depending on amount of territories
	 * captured and their value. 
	 */
	private int genUnitsPlayer(){ //need method like this for the AI
		Territory[] terr=map.getAllTerritories();
		int unitSum=0;
		for(int i=0;i<terr.length;i++){
			if (!terr[i].ownedbyAI()){ //if owned by player
				unitSum+=terr[i].getUnitVal();
			}
		}
		return unitSum;
	}

	@Override
	public int getID() {
		return 2;
	}
	
}
