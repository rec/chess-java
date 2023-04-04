package to.ax.games.chess;

import junit.framework.TestCase;

import static to.ax.games.chess.Square.*;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class SquareTest extends TestCase {
  public void assertEquals(int[] expected, int[] actual) {
    assertEquals("Arrays had different length", actual.length, expected.length);
    for (int i = 0; i < actual.length; i++) 
      assertEquals("Items at position " + i + " were different.", expected[i], actual[i]);      
  }
  
  public void testRowColumn() {
    assertEquals(A8.rank, 0); 
    assertEquals(A8.file, 0); 
    assertEquals(H1.rank, 7); 
    assertEquals(H1.file, 7);
  }
  
  public void testNext() {
    int squares = 0;
    for (Square square = A8; square != null; squares++, square = square.next()) {}
    assertEquals(WIDTH * HEIGHT, squares);
  }
  
  public void testDifference() {
    assertEquals(new int [] {0,  7}, A1.offset(A8));
    assertEquals(new int [] {0, -7}, A8.offset(A1));    

    assertEquals(new int [] { 7, 0}, H1.offset(A1));
    assertEquals(new int [] {-7, 0}, A1.offset(H1));    

    assertEquals(new int [] {-2, 1}, C6.offset(E7));
    assertEquals(new int [] { 2,-1}, E7.offset(C6));
    
    for (Square s: Square.values()) {
      for (Square t: Square.values()) {
        int[] difference = s.offset(t);
        int offset = Square.measure(difference);
        assertEquals(s, t.add(difference));
        assertEquals(s, t.next(offset));
        
        int[] inverse_diff = t.offset(s);
        int inverse_off = Square.measure(inverse_diff);
        assertEquals(t, s.add(inverse_diff));
        assertEquals(t, s.next(inverse_off));
        
        assertEquals(inverse_off, -offset);
        for (int i = 0; i < inverse_diff.length; i++) 
          assertEquals(inverse_diff[i], -difference[i]);
      }        
    }
  }
}
