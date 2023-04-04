package to.ax.games.chess;

import to.ax.games.chess.Game;
import junit.framework.TestCase;
import static to.ax.games.chess.Square.*;
import static to.ax.games.chess.Piece.*;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class MoveTest extends TestCase {
  private static final Game INITIAL_STATE = Game.INITIAL_STATE;
  
  public void testApplyMove() {
    Game one = ChessTestHelper.applyMoves(INITIAL_STATE, "e4");
    Game two = ChessTestHelper.applyMoves(one, "d5");
    
    assertEquals(null, one.getPiece(E2));
    assertEquals(INITIAL_STATE.getPiece(E2), one.getPiece(E4));
    assertEquals(INITIAL_STATE.getPiece(E2), WHITE_PAWN);
    
    Game twoPrime = ChessTestHelper.applyMoves(INITIAL_STATE, "e4", "d5");
    assertTrue(two.equals(twoPrime));
    assertTrue(two.equals(two));
  }
  
  public void testEnPassant() {
    Game x = ChessTestHelper.applyMoves(INITIAL_STATE, "e4", "a5", "e5", "d5");
    Game y = ChessTestHelper.applyMoves(INITIAL_STATE,  "e4", "d5", "e5", "a5");
    assertEquals(x.equals(y), false);
    System.out.println(x);
    System.out.println(y);
  }
}
