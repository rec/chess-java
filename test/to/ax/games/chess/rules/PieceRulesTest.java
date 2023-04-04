package to.ax.games.chess.rules;


import junit.framework.TestCase;

import to.ax.games.chess.Square;
import to.ax.games.chess.ChessTestHelper;
import to.ax.games.chess.Game;
import to.ax.games.chess.Move;
import to.ax.games.chess.rules.GameMove;
import to.ax.games.chess.rules.PieceRules;
import to.ax.games.util.Filter;


import static to.ax.games.chess.Square.*;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class PieceRulesTest extends TestCase {
  static String getBetweenString(Game game, Square from) {
    BetweenFilter filter = new BetweenFilter(game, from);
    return ChessTestHelper.squareFilterToBoardString(filter);    
  }
  
  public void testIsPieceBetweenA1() {
    assertEquals("tfffffft-tffffftf-tfffftff-tffftfff-tfftffff-tftfffff-ffffffff-fftttttt", 
        getBetweenString(Game.INITIAL_STATE, A1));
  }

  public void testIsPieceBetweenE4() {
    assertEquals("tffftfff-ffffffff-ffffffff-ffffffff-ffffffff-ffffffff-ffffffff-ftfftfft", 
        getBetweenString(Game.INITIAL_STATE, E4));
  }
}

class BetweenFilter implements Filter<Square> {
  private final Game game;
  private final Square from;
  
  BetweenFilter(Game game, Square from) {
    this.game = game;
    this.from = from;
  }
  
  PieceRules rules = new PieceRules() {
    public int[][] getMoveOffsets() { return null; }  
  };
  
  public boolean accepts(Square to) {
    if (!isPossibleMove(to))
      return false;
    PieceRulesTest.assertEquals(from + ":" + to, isBetween(to, from), isBetween(from, to));
    return isBetween(to, from);
  }

  private boolean isBetween(Square to, Square from) {
    return rules.isPieceBetween(new GameMove(game, new Move(from, to)));
  }
  
  private boolean isPossibleMove(Square to) {
    int[] offset = to.offset(from);
    return offset[0] * offset[1] == 0 || Math.abs(offset[0]) == Math.abs(offset[1]);
  }
}
