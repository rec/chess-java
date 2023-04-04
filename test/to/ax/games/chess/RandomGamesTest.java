package to.ax.games.chess;

import static to.ax.games.chess.Square.*;
import static to.ax.games.chess.Piece.Type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;
import to.ax.games.chess.rules.ChessRules;
import to.ax.games.converter.chess.GameMoveToString;
import to.ax.games.util.Color;

/**
 * @author Tom Ritchford (tom@swirly.com)
 * TODO(tom): move the random games code into non-test code.
 * TODO(tom): collect stats about number of possible moves per position, capture ratios and all.
 */
public class RandomGamesTest extends TestCase {
  private static final int MAX_MOVE_COUNT = 500;
  private static final int GAME_COUNT = 20;
  private static final Random RANDOM = new Random(1);
  private HashMap<String, Integer> counts;
  private int dotCount;
  
  public Move getRandomLegalMove(Game game) {
    final List<Move> moves = ChessRules.INSTANCE.getLegalMoves(game);
    final int size = moves.size();
    if (size == 0)
      return null;
    final Move move = moves.get(RANDOM.nextInt(size));
    return move;
  }
  
  Game doTestGameRoundTrip(Game game, Move move) {
    GameMoveToString gmts = new GameMoveToString(game);
    final String moveDesc = gmts.writer.convert(move);
    final Move move2 = gmts.reader.convert(moveDesc);
    assertEquals(move, move2);
    return ChessRules.INSTANCE.applyMove(game, move);
  }

  // Look for weirdnesses that are impossible or very improbable in a game.
  private void doTestGameForWeirdness(Game game) {
    for (Square square = A1; square != null; square = square.next()) 
      assertNotSame(PAWN, Piece.getType(game.getPiece(square)));
    for (Square square = A8; square != A7; square = square.next()) 
      assertNotSame(PAWN, Piece.getType(game.getPiece(square)));
    
    for (Color color: Color.values()) {
      boolean kingMissing = game.getSquareForPiece(Piece.getPiece(KING, color)) == null;
      assertTrue("Missing the " + color.toString().toLowerCase() + " king.", !kingMissing);
    }
  }
  
  public void emitDot(char ch) {
    System.out.print(ch);
    dotCount++;
    if ((dotCount % 80) == 0)
      System.out.println();
  }
  
  public void testRandomGames() {
    dotCount = 0;
    counts = new HashMap<String, Integer>();
    for (int i = 0; i < GAME_COUNT; ++i) {
      boolean finished = false;
      Game game = Game.INITIAL_STATE;
      for (int j = 0; j < MAX_MOVE_COUNT; ++j) {
        final Move move = getRandomLegalMove(game);
        if (move == null) {
          final String winningCondition = ChessRules.INSTANCE.getTerminalState(game);
          //System.out.println("\n\nTerminated in a " + winningCondition);
          //System.out.println("\n" + game);
          countCondition(winningCondition);
          finished = true;
          if (winningCondition.toLowerCase().contains("white"))
            emitDot('W');
          else if (winningCondition.toLowerCase().contains("black"))
            emitDot('B');
          else
            emitDot('-');
          break;
        }
        
        game = doTestGameRoundTrip(game, move);
        doTestGameForWeirdness(game);
      }
      if (!finished) {
        countCondition("ran out of moves");
        emitDot('.');
      }
    }
    System.out.println();
    final Set<Entry<String, Integer>> entrySet = counts.entrySet();
    for (Entry<String, Integer> entry: entrySet) 
      System.out.println(entry.getKey() + ": " + entry.getValue());
  }

  private void countCondition(final String winningCondition) {
    Integer count = counts.get(winningCondition);
    if (count == null)
      count = new Integer(1);
    else
      count = count + 1;
    counts.put(winningCondition, count);
  }   
}
