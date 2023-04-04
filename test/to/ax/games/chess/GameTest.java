package to.ax.games.chess;

import to.ax.games.chess.Game;
import junit.framework.TestCase;


/**
 * @author Tom Ritchford (tom@swirly.com)
 * @see MoveTest for more testing of this.
 */
public class GameTest extends TestCase {
  public void testEquals() {
    assertEquals(Game.INITIAL_STATE, Game.INITIAL_STATE);
  }
}
