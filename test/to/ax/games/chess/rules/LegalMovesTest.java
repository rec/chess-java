package to.ax.games.chess.rules;

import static to.ax.games.chess.Square.*;
import static to.ax.games.util.Color.*;
import junit.framework.TestCase;


import to.ax.games.chess.Square;
import to.ax.games.chess.ChessTestHelper;
import to.ax.games.chess.Game;
import to.ax.games.chess.Move;
import to.ax.games.chess.rules.ChessRules;
import to.ax.games.chess.rules.PieceRules;
import to.ax.games.util.Color;
import to.ax.games.util.Filter;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class LegalMovesTest extends TestCase {
  private static final Game STATE = Game.INITIAL_STATE;

  /**
   * @author Tom Ritchford (tom@swirly.com)
   *
   */
  public void testOccupiedSquareIterable() {
    doTestSquares(BLACK, A8, A6);
    doTestSquares(WHITE, A2, null);
  }

  private void doTestSquares(Color color, Square start, Square finish) {
    Square current = start;
    Iterable<Square> coloredSquares = PieceRules.occupiedSquareIteratable(STATE, color);
    for (Square square : coloredSquares) {
      assertEquals(current, square);
      current = current.next();
    }
    assertEquals(finish, current);
  }
    
  public void testIsAttacked() {
    String expected[] = { 
        "fttttttf-tttttttt-tttttttt-ffffffff-ffffffff-ffffffff-ffffffff-ffffffff", 
        "ffffffff-ffffffff-ffffffff-ffffffff-ffffffff-tttttttt-tttttttt-fttttttf", 
    };
    for (Color c : Color.values()) {
      final Color color = c;
      Filter<Square> attackFilter = new Filter<Square>() {
        public boolean accepts(Square square) {
          return PieceRules.isAttacked(STATE, square, color);
        }
      };
      String actual = ChessTestHelper.squareFilterToBoardString(attackFilter);
      assertEquals(expected[color.ordinal()], actual);
    }
  }
  
  public void testGetColorToMove() {
    assertEquals(WHITE, ChessRules.getColorToMove(STATE));
    assertEquals(BLACK, ChessRules.getColorToMove(ChessRules.INSTANCE.applyMove(STATE, new Move(D7, D5))));
  }
}

