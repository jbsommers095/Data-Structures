import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * JUnit tests for all TODO methods.
 */

public class Testing {
	  
	  // Test of Coord.onBoard()
	  @Test
	  public void testOnBoard() {
		Coord coord1 = new Coord(3, 4);
		Coord corner = new Coord(0, 0);
		
	    assertFalse(coord1.onBoard(4));
	    assertTrue(coord1.onBoard(5));
	    assertTrue(corner.onBoard(1));
	    assertFalse(corner.onBoard(0));
	  }

	  // Test of Coord.neighbors()
	  @Test
	  public void neighbors() {
		  for (int i = 0; i < 100; i++) {
			  for (int j = 0; j < 100; j++) {
				  Coord c = new Coord(i, j);
				  Coord[] neighs = {c.up(), c.down(), c.left(), c.right()};
				  ArrayList<Coord> nbors1 = c.neighbors(50);
				  ArrayList<Coord> nbors2 = new ArrayList<Coord>();
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
	  
	  // test of Coord.hashCode()
	  @Test
	  public void hashTest() {
		  for (int i = 0; i < 100; i++) {
			  for (int j = 0; j < 100; j++) {
				  Coord c = new Coord(i, j);
				  assertEquals((c.getX() << 5) - c.getX() + c.getY(), c.hashCode());
			  }
		  }
	  }
	  @Test
	  public void singleColorBoard() {
		  Board board = new Board(10);
		  board.flood2(WaterColor.BLUE);
		  assertEquals(WaterColor.BLUE, board.suggest());
		  board.flood2(WaterColor.CYAN);
		// Since board is already filled with a specific color, 
		  //board.suggest() should not change its result 
		  //because you can't click on another colored tile from there
		  assertEquals(WaterColor.BLUE, board.suggest());
		  board.flood2(WaterColor.RED);
		  assertEquals(WaterColor.BLUE, board.suggest());
		  assertTrue(board.fullyFlooded());
	  }
	  
	  @Test
	  public void fullBoard() {
		  Board board = new Board(10);
		  while(!board.fullyFlooded()) {
			  assertFalse(board.fullyFlooded());
			  board.flood(board.suggest());
		  }
		  assertTrue(board.fullyFlooded()); 
		  // This board should print "You win" on GUI assuming it was solved within needed steps
	  }
	  @Test
	  public void testSuggest() {
		  Board board = new Board(5);
		  for (int i = 0; i < 5; i++) {
			  for (int j = 0; j < 5; j++) {
				  switch(i) {
				  case 0:
					  board.get(new Coord(i, j)).setColor(WaterColor.YELLOW);
					  break;
				  case 1:
					  board.get(new Coord(i, j)).setColor(WaterColor.CYAN);
					  break;
				  case 2:
					  board.get(new Coord(i, j)).setColor(WaterColor.RED);
					  break;
				  case 3:
					  board.get(new Coord(i, j)).setColor(WaterColor.PINK);
					  break;
				  case 4:
					  board.get(new Coord(i, j)).setColor(WaterColor.BLUE);
					  break;
				  }
				  System.out.print(board.get(new Coord(i, j)).getColor() + ", ");
			  }
			  System.out.println();
		  }
		  System.out.println();
		  //assertEquals(WaterColor.CYAN, board.suggest());
		  board.flood(board.suggest());
		  //assertEquals(WaterColor.RED, board.suggest());
		  board.flood(board.suggest());
		  //assertEquals(WaterColor.CYAN, board.suggest());
		  board.flood(board.suggest());
		  //assertEquals(WaterColor.BLUE, board.suggest());
		  board.flood(board.suggest());
		  for (int i = 0; i < 5; i++) {
			  for (int j = 0; j < 5; j++) {
				  System.out.print(board.get(new Coord(i, j)).getColor() + ", ");
			  }
			  System.out.println();
		  }
		  }
}