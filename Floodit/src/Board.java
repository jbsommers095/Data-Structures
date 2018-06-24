import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * A Board represents the current state of the game. Boards know their dimension, 
 * the collection of tiles that are inside the current flooded region, and those tiles 
 * that are on the outside.
 * 
 * @author <put your name here>
 */

public class Board {
  private Map<Coord, Tile> inside, outside;
  private int size;
  
  /**
   * Constructs a square game board of the given size, initializes the list of 
   * inside tiles to include just the tile in the upper left corner, and puts 
   * all the other tiles in the outside list.
   */
  public Board(int size) {
    // A tile is either inside or outside the current flooded region.
    inside = new HashMap<>();
    outside = new HashMap<>();
    this.size = size;
    for (int y = 0; y < size; y++)
      for (int x = 0; x < size; x++) {
        Coord coord = new Coord(x, y);
        outside.put(coord, new Tile(coord));
      }
    // Move the corner tile into the flooded region and run flood on its color.
    Tile corner = outside.remove(Coord.ORIGIN);
    inside.put(Coord.ORIGIN, corner);
    flood(corner.getColor());
  }
  
  /**
   * Returns the tile at the specified coordinate.
   */ 
  public Tile get(Coord coord) {
    if (outside.containsKey(coord))
      return outside.get(coord);
    return inside.get(coord);
  }
  
  /**
   * Returns the size of this board.
   */
  public int getSize() {
    return size;
  }
  
  /**
   * TODO
   * 
   * Returns true iff all tiles on the board have the same color.
   */
  public boolean fullyFlooded() {
    return outside.isEmpty();
  }
  
  /**
   * TODO
   * 
   * Updates this board by changing the color of the current flood region 
   * and extending its reach.
   */
  public void flood(WaterColor color) {
	  //System.out.println(color);
	  //System.out.println(this.inside.size());
    Coord o = Coord.ORIGIN;
    WaterColor original = this.get(o).getColor();
    HashMap<Coord, Tile> choices = new HashMap<Coord, Tile>();
    for (Coord c : this.inside.keySet()) {
    	this.get(c).setColor(color);
    	ArrayList<Coord> nbors = c.neighbors(size);
    	for (Coord d : nbors) {
    		if (this.outside.containsKey(d)) {
    			if (this.get(d).getColor().equals(original) || this.get(d).getColor().equals(color)) {
    				this.get(d).setColor(color);
    				choices.put(d, this.get(d));
    			}
    		}
    	}
    }
    for (Coord c : choices.keySet()) {
    	this.inside.put(c, this.get(c));
    	this.outside.remove(c);
    }
    
  }
  
  /**
   * TODO
   * 
   * Explore a variety of algorithms for handling a flood. Use the same interface 
   * as for flood above, but add an index so your methods are named flood1,
   * flood2, ..., and so on. Then, use the batchTest() tool in Driver to run game
   * simulations and then display a graph showing the run times of all your different 
   * flood functions. Do not delete your flood code attempts. For each variety of 
   * flood, including the one above that you eventually settle on, write a comment
   * that describes your algorithm in English. For those implementations that you
   * abandon, state your reasons.
  
  public void flood1(WaterColor color) {
	   

  }*/
  
  // Floods a board entirely with a specific color
  public void flood2(WaterColor color) {
	  for (Coord c : this.outside.keySet()) {
		  this.inside.put(c, this.get(c));
	  }
	  this.outside.clear();
	  for (Coord c : this.inside.keySet()) {
		  this.get(c).setColor(color);
	  }
    
  }
   
  
  /**
   * TODO
   * 
   * Returns the "best" GameColor for the next move. 
   * 
   * Modify this comment to describe your algorithm. Possible strategies to pursue 
   * include maximizing the number of tiles in the current flooded region, or maximizing
   * the size of the perimeter of the current flooded region.
   * 
   * Uses flood with temporary variables to find the most optimal move
   */
  public WaterColor suggest() {
	  WaterColor[] colors = WaterColor.values();
	  int res = 0;
	  WaterColor bestColor = colors[0];
	  
	  for(WaterColor color : colors) {
		  Map<Coord, Tile> inSuggest = new HashMap<>();
		  Map<Coord, Tile> outSuggest = new HashMap<>();
		  Map<Coord, Tile> choices = new HashMap<>();
		  // Use putAll instead of = or it will mess with this board as well as temporary boards
		  inSuggest.putAll(this.inside);
		  outSuggest.putAll(this.outside);
		  for (Coord c : inSuggest.keySet()) {
			  ArrayList<Coord> nbors = c.neighbors(size);
		    	for (Coord d : nbors) {
		    		if (outSuggest.containsKey(d)) {
		    			if (this.get(d).getColor().equals(this.get(Coord.ORIGIN).getColor()) || this.get(d).getColor().equals(color)) {
		    				choices.put(d, outSuggest.get(d));
		    				outSuggest.remove(d);
		    			}
		    		}
		    	}
		    }
		    for (Coord c : choices.keySet()) {
		    	inSuggest.put(c, choices.get(c));
		    	outSuggest.remove(c);
		    }
		    if (inSuggest.size() > res) {
		    	res = inSuggest.size();
		    	bestColor = color;
		    }
		  }
	  
	  return bestColor;
  }
  
  
  /**
   * Returns a string representation of this board. Tiles are given as their
   * color names, with those inside the flooded region written in uppercase.
   */ 
  public String toString() {
    StringBuilder ans = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        Coord curr = new Coord(x, y);
        WaterColor color = get(curr).getColor();
        ans.append(inside.containsKey(curr) ? color.toString().toUpperCase() : color);
        ans.append("\t");
      }
      ans.append("\n");
    }
    return ans.toString();
  }
  public Map<Coord, Tile> removeFrom(Coord c) {
    this.outside.remove(c);
    return this.outside;
  }
  /**
   * Simple testing.
   */
  public static void main(String... args) {
    // Print out boards of size 1, 2, ..., 5
    int n = 5;
    for (int size = 1; size <= n; size++) {
      Board someBoard = new Board(size);
      //System.out.println(someBoard);
    }
    /*Map<Coord, Tile> map = new HashMap<Coord, Tile>();
    map.put(new Coord(1, 2), new Tile(new Coord(1, 2), WaterColor.BLUE));
    map.remove(new Coord(1, 2));
    System.out.println(map.size());*/
}
}






