import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.Test;
import java.util.*;

/**
 * JUnit tests for all TODO methods.
 */

public class Testing {
	  
	  // Testing to make sure that coordinates are on the board
	  @Test
	  public void testOnBoard() {
		Coord coord1 = new Coord(3, 4);
		Coord corner = new Coord(0, 0);
		
	    assertFalse(coord1.onBoard(4));
	    assertTrue(coord1.onBoard(5));
	    assertTrue(corner.onBoard(1));
	    assertFalse(corner.onBoard(0));
	  }

	  // Testing for neighbors function
	  @Test
	  public void neighbors() {
		  for (int i = 0; i < 100; i++) {
			  for (int j = 0; j < 100; j++) {
				  Coord c = new Coord(i, j);
				  Coord[] neighs = {c.up(), c.down(), c.left(), c.right()};
				  List<Coord> nbors1 = c.neighbors(50);
				  List<Coord> nbors2 = new ArrayList<Coord>();
				  if (c.onBoard(50)) {
				  for (int k = 0; k < neighs.length; k++) {
					  if (neighs[k].onBoard(50)) {
						  nbors2.add(neighs[k]);
					  }
				  }
					  assertEquals(nbors1, nbors2);
				  }
				  else
					  assertEquals(nbors2, new ArrayList<Coord>());
				  
			  }
		  }
	  }
	  
	  // testing for hash codes of Coords
	  @Test
	  public void hashTest() {
		  for (int i = 0; i < 100; i++) {
			  for (int j = 0; j < 100; j++) {
				  Coord c = new Coord(i, j);
				  assertEquals((c.getX() << 5) - c.getX() + c.getY(), c.hashCode());
			  }
		  }
	  }
	  // Testing for fully flooded boards
	  @Test
	  public void singleColorBoard() {
		  Board board = new Board(10);
		  board.flood2(WaterColor.BLUE); // Keep in mind flood2 should fill entire board with single color
		  assertTrue(board.fullyFlooded());
		  assertEquals(WaterColor.BLUE, board.suggest());
		  board.flood2(WaterColor.CYAN);
		// Since board is already filled with a specific color, 
		  //board.suggest() should not change its result 
		  //because board.inside is empty
		  assertEquals(WaterColor.BLUE, board.suggest());
		  board.flood2(WaterColor.RED);
		  assertEquals(WaterColor.BLUE, board.suggest());
		  assertTrue(board.fullyFlooded());
	  }
	  // More fully flooded board testing
	  //@Test
	  public void fullBoard() {
		  Board board = new Board(10);
		  while(!board.fullyFlooded()) {
			  assertFalse(board.fullyFlooded());
			  board.flood(board.suggest());
		  }
		  assertTrue(board.fullyFlooded()); 
		  // This board should print "You win" on GUI assuming it was solved within needed steps
	  }
	  // Testing for suggest on a small board
	  //@Test
	  public void miniSuggest() {
		  WaterColor[][] colors = {{WaterColor.BLUE, WaterColor.RED}, {WaterColor.RED, WaterColor.RED}};
		  Board board = new Board(2, colors);
		  assertEquals(board.suggest(), WaterColor.RED);
		  board.flood(board.suggest());
		  assertTrue(board.fullyFlooded());
	  }
	  // Just another test board for suggest
	  //@Test
	  public void miniSuggest2() {
		  WaterColor[][] colors = {{WaterColor.BLUE, WaterColor.YELLOW, WaterColor.CYAN}, {WaterColor.BLUE, WaterColor.BLUE, WaterColor.YELLOW}, {WaterColor.BLUE, WaterColor.PINK, WaterColor.BLUE}};
		  Board board = new Board(3, colors);
		  assertEquals(WaterColor.YELLOW, board.suggest());
		  board.flood(board.get(Coord.ORIGIN).getColor()); // flooding corner should not change board contents
		  assertEquals(WaterColor.YELLOW, board.suggest());
		  WaterColor[][] colors2 = {{WaterColor.PINK, WaterColor.BLUE, WaterColor.RED}, {WaterColor.BLUE, WaterColor.CYAN, WaterColor.RED}, {WaterColor.BLUE, WaterColor.CYAN, WaterColor.RED}};
		  Board board2 = new Board(3, colors2);
		  assertEquals(WaterColor.BLUE, board2.suggest());
	  }
	  // Testing for suggest on a very large board and also for the possibility of adding other colors into the WaterColor enum
	  @Test
	  public void testSuggestLarge() {
		  WaterColor[] colors = WaterColor.values();
		  WaterColor[][] custom = new WaterColor[colors.length * colors.length][colors.length * colors.length];
		  for (int i = 0; i < custom.length; i++) {
			  for (int j = 0; j < custom[i].length; j++) {
				  int temp = i % colors.length;
				  custom[i][j] = colors[temp];
			  }
		  }
		  Board board = new Board(custom.length, custom);
		  int numFloods = 1; // counts single flood after initialization
		  while (!board.fullyFlooded()) {
			  int flood = numFloods % colors.length;
			  assertEquals(colors[flood], board.suggest());
			  board.flood(board.suggest());
			  numFloods++;
			  }
		  assertTrue(numFloods == custom.length);
}
}