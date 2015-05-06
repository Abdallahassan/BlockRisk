import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;




public class Territory {
	private Polygon poly;
	private boolean highlighted;
	private boolean owner; // true if it's owned by the AI.
	private int units; //amount of units in this territory
	private int unitVal; //amount of units this territory generates each turn 
	
	public Territory(boolean owner) {
		this.owner = owner;
		highlighted = false;
		poly = new Polygon();
	}
	
	public void draw(Graphics g) {
		if (highlighted)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.black);
		g.draw(poly);
		
		if (owner)
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		g.fill(poly);
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