import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;


public class Territory {
	private Polygon poly;
	private boolean highlighted;
	private boolean owner; // true if it's owned by the AI.
	
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

}
