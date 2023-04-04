package to.ax.games.chess;

import to.ax.games.chess.Piece;
import junit.framework.TestCase;
import static to.ax.games.chess.Piece.*;
import static to.ax.games.chess.Piece.Type.*;
import static to.ax.games.util.Color.*;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class PieceTest extends TestCase {
  public void testNot() {
    assertEquals(WHITE, BLACK.not());
    assertEquals(BLACK, WHITE.not());
    assertEquals(KING.ordinal() + 1, Piece.TYPE_COUNT);
    assertEquals(WHITE_PAWN, BLACK_PAWN.not());
    assertEquals(BLACK_PAWN, WHITE_PAWN.not());
    assertEquals(WHITE_KING, BLACK_KING.not());
    assertEquals(BLACK_KING, WHITE_KING.not());
    
    assertEquals(BLACK_PAWN, Piece.getPiece(PAWN, BLACK));
    assertEquals(WHITE_PAWN, Piece.getPiece(PAWN, WHITE));
    assertEquals(BLACK_KING, Piece.getPiece(KING, BLACK));
    assertEquals(WHITE_KING, Piece.getPiece(KING, WHITE));
  }
}
