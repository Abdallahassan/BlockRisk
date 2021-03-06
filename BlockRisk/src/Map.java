import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import sun.misc.IOUtils;


public class Map {
	
	private Territory[] territories;
	private Square[][] squares;
	private int highlight; // Do not save in xml file.
	
	public Map() {
		territories = new Territory[12]; // 12 territories enumerated 0 to 11.
		for (int i = 0; i < 6; i++)
			territories[i] = new Territory(false);
		for (int i = 6; i < 12; i++)
			territories[i] = new Territory(true);
		
		initNeighbours(0, new int[]{1, 3});
		initNeighbours(1, new int[]{0, 2, 3, 4});
		initNeighbours(2, new int[]{1, 4});
		initNeighbours(3, new int[]{0, 1, 4, 5, 6});
		initNeighbours(4, new int[]{1, 2, 3, 6, 7, 8});
		initNeighbours(5, new int[]{3, 6, 9});
		initNeighbours(6, new int[]{3, 4, 5, 7, 9});
		initNeighbours(7, new int[]{4, 6, 8, 9, 10});
		initNeighbours(8, new int[]{4, 7, 10});
		initNeighbours(9, new int[]{5, 6, 7, 10, 11});
		initNeighbours(10, new int[]{7, 8, 9, 11});
		initNeighbours(11, new int[]{9, 10});
		
		squares = new Square[160][80];
		for (int i = 0; i < 160; i++)
			for (int j = 0; j < 80; j++)
				squares[i][j] = new Square(new IntPair(i*5, (j*5)+50));
				
		highlight = -1;
		
		//tmpInit();
	}
	
	// This is a temporary method just to test the game mechanics before the map is done.
	private void tmpInit() {
		makeSquare(0, new IntPair(0, 50), new IntPair(195, 145));
		makeSquare(1, new IntPair(0, 150), new IntPair(195, 295));
		makeSquare(2, new IntPair(0, 300), new IntPair(195, 445));
		makeSquare(3, new IntPair(200, 50), new IntPair(345, 195));
		makeSquare(4, new IntPair(200, 200), new IntPair(345, 445));
		makeSquare(5, new IntPair(350, 50), new IntPair(545, 145));
		makeSquare(6, new IntPair(350, 150), new IntPair(545, 245));
		makeSquare(7, new IntPair(350, 250), new IntPair(545, 395));
		makeSquare(8, new IntPair(350, 400), new IntPair(795, 445));
		makeSquare(9, new IntPair(550, 50), new IntPair(695, 295));
		makeSquare(10, new IntPair(550, 300), new IntPair(795, 395));
		makeSquare(11, new IntPair(700, 50), new IntPair(795, 295));
		initBorders();
	}
	
	// Temporary method too.
	private void makeSquare(int id, IntPair from, IntPair to) {
		for (int i = from.x; i <= to.x; i+=5)
			for (int j = from.y; j <= to.y; j+=5)
				changeOwner(new IntPair(i, j), id);
	}
	
	// Temporary
	private boolean Ok(IntPair sq) {
		return sq.x >= 0 && sq.x < 160 && sq.y >= 0 && sq.y < 80;
	}
	
	// Temporary
	private List<IntPair> near(IntPair sq) {
		List<IntPair> sqs = new ArrayList<IntPair>();
		IntPair add = new IntPair(sq.x -1, sq.y -1);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x+1, sq.y);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x+1, sq.y);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x, sq.y+1);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x, sq.y+1);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x-1, sq.y);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x-1, sq.y);
		if (Ok(add))
			sqs.add(add);
		add = new IntPair(add.x, sq.y-1);
		if (Ok(add))
			sqs.add(add);
		return sqs;
	}
	
	// Temporary
	public void initBorders() {
		for (int i = 0; i < 160; i++)
			for (int j = 0; j < 80; j++)
				for (IntPair ip : near(new IntPair(i, j))){
					if (squares[i][j].getOwnership() != squares[ip.x][ip.y].getOwnership())
							squares[i][j].setBoundary(true);
				}
	}
	
	// Temporary draw method.
	public void tmpdraw(Graphics g) {
		for (Square[] array: squares)
			for (Square s: array) {
				Color tmp;
				if (territories[s.getOwnership()].ownedbyAI())
					tmp = Color.blue;
				else
					tmp = Color.red;
				
				if (s.getOwnership() == highlight || s.boundaryStatus())
					tmp = tmp.darker();
				s.draw(tmp, g);
			}
	}
	
	// Use only by MapCreater
	public void changeOwner(IntPair coord, int id) {
		squares[coord.x/5][(coord.y-50)/5].setOwnership(id);
	}
	
	// Use only by Mapcreater
	public void setBound(IntPair coord, boolean val) {
		squares[coord.x/5][(coord.y-50)/5].setBoundary(val);
	}
	
	private void initNeighbours(int id, int[] neighbourIDs) {
		Territory[] neighbours = new Territory[neighbourIDs.length];
		int n = 0;
		for (int i: neighbourIDs) {
			neighbours[n] = territories[i];
			n++;
		}
		territories[id].setNeighbours(neighbours);
	}
	
	public void draw(Graphics g) {
		for (Square[] array: squares)
			for (Square s: array) {
				Color tmp;
				if (territories[s.getOwnership()].ownedbyAI())
					tmp = Color.blue;
				else
					tmp = Color.red;
					
				if (s.boundaryStatus() || s.getOwnership() == highlight)
					tmp = tmp.darker();
				s.draw(tmp, g);
			}
	}
	/**
	 * Load an already saved game from file.
	 * As of now, it loads information about all of the squares.
	 * @throws URISyntaxException 
	 */
	
	public void load() {
		SAXBuilder builder = new SAXBuilder();
		 File xmlFileold = new File("res/mapSave.xml");
		 
		 InputStream xmlFile = ResourceLoader.getResourceAsStream("res/mapSave.xml");
		 
		  try {
	 
			Document document = (Document) builder.build(xmlFile); //builds XML file
			Element rootNode = document.getRootElement(); //gets rootNode					
			
			//retrieves all squares
			Element squareNode=rootNode.getChild("Squares");
			for(int j=0;j<squares.length;j++){
				Element squareRowChild=squareNode.getChild("row"+j);
				for(int k=0;k<squares[j].length;k++){
					Element squareColChild=squareRowChild.getChild("column"+k);
					Element coord = squareColChild.getChild("coordinates");
					Element isBoundary = squareColChild.getChild("isBoundary");
					Element belongsTo = squareColChild.getChild("belongsTo");
					Element xCoord=coord.getChild("x");
					Element yCoord=coord.getChild("y");
					
					squares[j][k].setIsBoundary(Boolean.parseBoolean(isBoundary.getText()));
					squares[j][k].setBelongsTo(Integer.parseInt(belongsTo.getText()));
					squares[j][k].getCoord().x=Integer.parseInt(xCoord.getText());
					squares[j][k].getCoord().y=Integer.parseInt(yCoord.getText());
				}
			}
			
		  } catch (IOException io) { //error handling
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) { //error handling
			System.out.println(jdomex.getMessage());
		  }		
	}
	
	/**
	 * initialises mapSave.xml, used for when no data of map
	 * exists/mapSave.xml is empty . 
	 * Arsalan-THIS METHOD WONT BE NEEDED ANY LONGER.
	 */
	public void initMapSave() {
		 
		  try {
	 
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File("res/mapSave.xml");
	 
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
	 
						
			for(int i=0;i<territories.length;i++){
				Territory territory=territories[i];
				Element terrNode = new Element("Terr"+i);
				rootNode.addContent(terrNode);
				
				Element terrOwner = new Element("owner");
				Element terrUnits = new Element("units");
				Element inf=new Element("infantry");
				Element veh=new Element("vehicles");
				Element air=new Element("aircraft");
				
				int infNum=territory.getUnits()[0];
				inf.setText(Integer.toString(infNum));
				
				int vehNum=territory.getUnits()[1];
				veh.setText(Integer.toString(vehNum));
				
				int airNum=territory.getUnits()[2];
				air.setText(Integer.toString(airNum));
				
				terrUnits.addContent(inf);
				terrUnits.addContent(veh);
				terrUnits.addContent(air);
				
				terrNode.addContent(terrUnits);
				terrNode.addContent(terrOwner);
									
			}
						 
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("res/mapSave.xml"));
	 
			// xmlOutput.output(doc, System.out);
	 
			System.out.println("File updated!");
		  } catch (IOException io) {
			io.printStackTrace();
		  } catch (JDOMException e) {
			e.printStackTrace();
		  }
		}
	
	
	// Save this map to save file.
	// Doesn't work!
	public void save() {
		 
		  try {
	 
			SAXBuilder builder = new SAXBuilder();
			File xmlFileold = new File("res/mapSave.xml");
			
			InputStream xmlFile = ResourceLoader.getResourceAsStream("res/mapSave.xml");
	 
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
	 
						
			for(int i=0;i<territories.length;i++){
				Territory territory=territories[i];
				int[] terrUnits=territory.getUnits();
				Element terr=rootNode.getChild("Terr"+i);
				Element unitNode=terr.getChild("units");
				//updates unit values
				unitNode.getChild("infantry").setText(Integer.toString(terrUnits[0]));
				unitNode.getChild("vehicles").setText(Integer.toString(terrUnits[1]));
				unitNode.getChild("aircraft").setText(Integer.toString(terrUnits[2]));
				
				terr.getChild("owner").setText(Boolean.toString(territory.ownedbyAI()));									
			}
			//stores all squares
			Element squareNode=rootNode.getChild("Squares");
			for(int j=0;j<squares.length;j++){
				Element squareRowChild=squareNode.getChild("row"+j);
				for(int k=0;k<squares[j].length;k++){
					Square square = squares[j][k];
					Element squareColChild=squareRowChild.getChild("column"+k);
					Element coord = squareColChild.getChild("coordinates");
					Element isBoundary = squareColChild.getChild("isBoundary");
					Element belongsTo = squareColChild.getChild("belongsTo");
										
					isBoundary.setText(Boolean.toString(square.boundaryStatus()));
					belongsTo.setText(Integer.toString(square.getOwnership()));
					
					IntPair pair=square.getCoord();
					Element x=coord.getChild("x");
					Element y=coord.getChild("y");
					
					x.setText(Integer.toString(pair.x));
					y.setText(Integer.toString(pair.y));
											
				}
			
			}
					
	
	 
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("res/mapSave.xml"));
	 
			// xmlOutput.output(doc, System.out);
	 
			System.out.println("File updated!");
		  } catch (IOException io) {
			io.printStackTrace();
		  } catch (JDOMException e) {
			e.printStackTrace();
		  }
	}
	
	public void initNewGame() {
		for (int i = 0; i < 6; i++)
			territories[i].setOwner(false);
		for (int i = 6; i < 12; i++)
			territories[i].setOwner(true);
	}
	
	/**
	 * @param Coord The coordinates of the mouse input. The input MUST BE VALID (i.e. inside the map).
	 * @return The corresponding Territory ID.
	 */
	public int getTerritoryID(IntPair coord) {
		return squares[coord.x/5][(coord.y-50)/5].getOwnership();
	}
	
	public boolean ownedbyAI(int id) {
		return territories[id].ownedbyAI();
	}
	
	public boolean areNeighbours(int id1, int id2) {
		return territories[id1].isNeighbour(territories[id2]);
	}
	
	public Territory getTerritory(int id) {
		return territories[id];
	}
	
	public Territory getTerritory(IntPair coord) {
		return territories[squares[coord.x/5][(coord.y-50)/5].getOwnership()];
	}
	
	public Territory[] getAllTerritories() {
		return territories;
	}
	
	public int getHighlight() {
		return highlight;
	}

	public void setHighlight(int highlight) {
		this.highlight = highlight;
	}

}
