import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class Map {
	
	private Territory[] territories;
	private Square[][] squares;
	
	public Map() {
		territories = new Territory[12]; // 12 territories
		squares = new Square[160][80];
		for (int i = 0; i < 160; i++)
			for (int j = 0; j < 80; j++)
				squares[i][j] = new Square(new IntPair(i*5, (j*5)+50));
	}
	
	public void changeOwner(IntPair coord, int id) {
		squares[coord.x/5][(coord.y-50)/5].setOwnership(id);
	}
	
	public void setBound(IntPair coord, boolean val) {
		squares[coord.x/5][(coord.y-50)/5].setBoundary(val);
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
					
				if (s.boundaryStatus())
					tmp = tmp.darker();
				s.draw(tmp, g);
			}
	}
	
	// Load and already saved game from file.
	public void load() {
		SAXBuilder builder = new SAXBuilder();
		 File xmlFile = new File("res/mapSave.xml");
	 
		  try {
	 
			Document document = (Document) builder.build(xmlFile); //builds XML file
			Element rootNode = document.getRootElement(); //gets rootNode
			List list = rootNode.getChildren("staff"); //creates list out of root's specified child
	 
			for (int i = 0; i < list.size(); i++) {	 
			   Element node = (Element) list.get(i);
			   System.out.println("First Name : " + node.getChildText("firstname"));
			   System.out.println("Last Name : " + node.getChildText("lastname"));
			   System.out.println("Nick Name : " + node.getChildText("nickname"));
			   System.out.println("Salary : " + node.getChildText("salary"));	 
			}	 
		  } catch (IOException io) { //error handling
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) { //error handling
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

}
