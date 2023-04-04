package to.ax.games.chess.rules;

import java.util.List;

import junit.framework.TestCase;
import to.ax.games.chess.Game;
import to.ax.games.chess.Move;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class ChessRulesTest extends TestCase {  
  public void testPrintAllSecondMoves() {
    Game game = Game.INITIAL_STATE;
    List<Move> moves = ChessRules.INSTANCE.getLegalMoves(game);
    System.out.println(moves.size());
    for (Move move: moves) {
      //final Game applyMove = 
      ChessRules.INSTANCE.applyMove(game, move);
      // System.out.println("*****\n\n" + applyMove);
    }
    // The Evergreen game.
  }
}
