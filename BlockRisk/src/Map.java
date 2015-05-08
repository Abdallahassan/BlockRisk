import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
//need to import jDOM here for map load and save
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class Map {
	
	private Territory[] territories;
	private List<IntPair> neighboors;
	private Square[][] squares;
	
	public Map() {
		territories = new Territory[12]; // 12 territories
		neighboors = new ArrayList<IntPair>();
		squares = new Square[80][40];
		for (int i = 0; i < 80; i++)
			for (int j = 0; j < 40; j++)
				squares[i][j] = new Square(new IntPair(i*10, (j*10)+50));
	}
	
	public void changeOwner(IntPair coord, int id) {
		squares[coord.x/10][coord.y/10].setOwnership(id);
	}
	
	public void setBound(IntPair coord, boolean val) {
		squares[coord.x/10][coord.y/10].setBoundary(val);
	}
	
	public void draw(Graphics g) {
		for (Square[] array: squares)
			for (Square s: array) {
				Color tmp;
				int i = s.getOwnership();
					if (i == 0 || i == 6)
						tmp = Color.blue;
					else if (i == 1 || i == 7)
						tmp = Color.cyan;
					else if (i == 2 || i == 8)
						tmp = Color.green;
					else if (i == 3 || i == 9)
						tmp = Color.red;
					else if (i == 4 || i == 10)
						tmp = Color.yellow;
					else if (i == 5 || i == 11)
						tmp = Color.orange;
					else
						tmp = Color.pink;
				s.draw(tmp, g);
			}
	}
	
	// Load and already saved game from file.
	public void load() {
 
	  SAXBuilder builder = new SAXBuilder();
	  File xmlFile = new File("c:\\file.xml");
 
	  try {
 
		Document document = (Document) builder.build(xmlFile); //builds xml file
		Element rootNode = document.getRootElement(); //gets  a root
		List list = rootNode.getChildren("staff"); //gets a list of all children of root
 
		//iterate over all children to root
		for (int i = 0; i < list.size(); i++) {
 
		   Element node = (Element) list.get(i);
 
		   System.out.println("First Name : " + node.getChildText("firstname"));
		   System.out.println("Last Name : " + node.getChildText("lastname"));
		   System.out.println("Nick Name : " + node.getChildText("nickname"));
		   System.out.println("Salary : " + node.getChildText("salary"));
 
		}

 		//erro handling
	  } catch (IOException io) { 
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
	}

	
	// Save this map to save file.
	public void save() {
		//TODO
	}
	
	public Territory getTerritory(int id) {
		return territories[id];
	}
	
	public Territory[] getAllTerritories() {
		return territories;
	}
	
	public boolean adjacent(int id1, int id2) {
		return neighboors.contains(new IntPair(Math.min(id1, id2), Math.max(id1, id2)));
	}

}
