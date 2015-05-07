import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;


public class Territory {
	
	private List<Square> squares;
	private boolean highlighted;
	private boolean owner; // true if it's owned by the AI.
	private int units; //amount of units in this territory
	private int unitVal; //amount of units this territory generates each turn 
	
	
	public Territory(boolean owner) {
		this.owner = owner;
		squares = new ArrayList<Square>();
	}
	
	public void draw(Graphics g, Color temp) {
		if (owner)
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		
		for (Square s: squares)
			if (s.boundaryStatus()) {
				if (highlighted)
					s.draw(Color.yellow, g);
				else
					s.draw(Color.black, g);
			} else {
				s.draw(temp, g);
			}
	}
	
	public boolean ownedbyAI() {
		return owner;
	}
	
	public void changeOwner() {
		owner = !owner;
	}
	
	public int getUnits(){
		return units;
	}
	
	public void setUnits(int remove){
		units-=remove;
	}

	public int getUnitVal() {
		return unitVal;
	}

}
