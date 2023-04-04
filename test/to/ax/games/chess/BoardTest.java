package to.ax.games.chess;

import to.ax.games.chess.Board;
import junit.framework.TestCase;
import static to.ax.games.chess.Square.*;
import static to.ax.games.chess.Piece.*;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class BoardTest extends TestCase {
  public void testEquals() {
    Board board = new Board();
    assertEquals(BLACK_ROOK, board.getPiece(A8));
    assertEquals(BLACK_KNIGHT, board.getPiece(B8));
    assertEquals(WHITE_ROOK, board.getPiece(A1));
    
    Board newBoard = new Board(board);
    board.setPiece(A8, null);
    assertEquals(BLACK_ROOK, newBoard.getPiece(A8));
    assertEquals(null, board.getPiece(A8));
    
    assertEquals(board, board);
    assertEquals(newBoard, newBoard);
    assertNotSame(board, newBoard);
  }
}
