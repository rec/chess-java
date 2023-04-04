package to.ax.games.chess.converter;

import static to.ax.games.chess.Square.*;
import static to.ax.games.chess.Piece.*;

import junit.framework.TestCase;
import to.ax.games.chess.Board;
import to.ax.games.chess.Game;
import to.ax.games.chess.Move;
import to.ax.games.chess.rules.ChessRules;
import to.ax.games.converter.Converter;
import to.ax.games.converter.chess.BoardToDisplay;
import to.ax.games.converter.chess.BoardToShorthand;
import to.ax.games.converter.chess.GameMoveToString;
import to.ax.games.converter.chess.MoveToString;

/**
 * @author Tom Ritchford (tom@swirly.com)
 *
 */
public class ConverterTest extends TestCase {
  void doTestRoundTrip(Converter.TwoWay<Board, String> formatter, Board board) {
    String formatted = formatter.getConvertFromTo().convert(board);
    Board roundTrip = formatter.getConvertToFrom().convert(formatted);    
    assertEquals(board, roundTrip);
  }

  public void testAllBoard() {
    Board board = new Board();
    doTestRoundTrip(BoardToDisplay.INSTANCE, board);
    doTestRoundTrip(BoardToShorthand.INSTANCE, board);
  }
  
  void doTestRoundTrip(Converter.TwoWay<Move, String> formatter, Move Move) {
    String formatted = formatter.getConvertFromTo().convert(Move);
    Move roundTrip = formatter.getConvertToFrom().convert(formatted);    
    assertEquals(Move, roundTrip);
  }

  public void testAllMove() {
    doTestRoundTrip(MoveToString.INSTANCE, new Move(A1, A2));
    doTestRoundTrip(MoveToString.INSTANCE, new Move(C2, C1, BLACK_QUEEN));
    doTestRoundTrip(MoveToString.INSTANCE, new Move(G4, F3, null, true));
  }
  
  private Game doTestGameRoundTrip(Game game, String moveDesc) {
    GameMoveToString gmts = new GameMoveToString(game);
    final Move move = gmts.reader.convert(moveDesc);
    final String moveDesc2 = gmts.writer.convert(move);
    assertEquals(moveDesc, moveDesc2);
    return ChessRules.INSTANCE.applyMove(game, move);
  }

  private Game doTestGameRoundTrip(Game game, String ...moves) {
    for (String move: moves)
      game = doTestGameRoundTrip(game, move);
    return game;
  }
  
  public void testGameMoveToString() {    
    doTestGameRoundTrip(Game.INITIAL_STATE, "e4", "e5", "n-c3", "n-h6");
  }
}
