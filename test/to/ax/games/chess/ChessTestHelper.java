package to.ax.games.chess;

import to.ax.games.chess.rules.ChessRules;
import to.ax.games.converter.chess.GameMoveToString;
import to.ax.games.util.Filter;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class ChessTestHelper {
  public static String squareFilterToBoardString(Filter<Square> rule) {
    StringBuffer result = new StringBuffer();
    for (Square square: Square.values()) {
      if (square.file == 0 && square.rank != 0) 
        result.append('-');
      result.append(rule.accepts(square) ? 't' : 'f'); 
    }
    return result.toString();
  }

  public static Game applyMoves(Game game, String ...moves) {
    for (int i = 0; i < moves.length; i++) {
      String string = moves[i];
      final Move move = new GameMoveToString(game).reader.convert(string);
      game = ChessRules.INSTANCE.applyMove(game, move);
    }
    return game;
  }
}

  

