package com.editev.chess;

import java.util.Enumeration;

import com.editev.chess.piece.Piece;
import com.editev.util.Enum;
import com.editev.util.Filter;
import com.editev.util.Function;

/** A Game that can enumerate its legal Moves.
 *
 *  @see See the source <a href="GameEnum.java">here</a>.
 */
public class GameEnum extends Game {

  /** A Filter that only accept legal moves for this game. */
  public final Filter LEGAL_MOVE_FILTER = new Filter() {
    /** @param move a move to check for legality. 
     *  @return true if the Move is legal.*/
    public boolean accept( Object move ) { return !isIllegal( (Move) move ); }  
  };

  public final Filter ON_THE_MOVE_FILTER = new Filter() {
    /** @param move a move which might have a Piece.
     *  
     *  @return true if the source of the Move contains a Piece and that Piece is on-the-move.
     */
    public boolean accept( Object move ) {
      byte piece = getPieceIndex( ((Move) move).source );
      return piece != NO_PIECE && isWhiteMove() == White.is( piece );
    }
  };
  
  /** Constructs a list of Piece moves from a Square. */
  public final Function MAKE_PIECE_MOVE_ENUMERATOR = new Function() {
    /** Create a new enumeration of the Pieces 
     *  @return an enumeration of moves for the piece moving. 
     *  @param discard ignore this:  we look at the central iterator Move.
     */
    public Object eval( Object move ) { return new PieceMoveEnumerator( (Move) move ); }
  };

  /** An enumeration of all Moves for the piece in the Move. */
  class PieceMoveEnumerator extends Enum.Lookahead {
    /** The Piece that's doing the moving. */
    public final Piece piece;
    
    /** The Move that's being enumerated. */
    public final Move move;

    /** Create the iterator starting with a Move. */
    public PieceMoveEnumerator( Move move ) { 
      this.move = move;
      this.piece = getPiece( move );   // get the Piece for this move.
      next = move;
      piece.firstMoveIndex( move );
    }

    /** Retrieve the next move (legal or not!)
     *  @see com.editev.util.Enum.Lookahead. */
    protected void next() { next = piece.incrementMoveIndex( move ) ? move : null; }
  }
  
  /** Enumeration of every legal move for this Game.  
   *  Don't change the game in the middle of getting the enumeration or this might not work.
   *  @return a new Enumeration of all legal moves for this game.
   */
  public Enumeration enumerateAllLegalMoves() {
    return
      new Enum.Filtered(            
        LEGAL_MOVE_FILTER,              // filter accepts only legal moves..
        new Enum.Compound(              // for each legal move for each piece.
          new Enum.Mapped(
            MAKE_PIECE_MOVE_ENUMERATOR, // map Pieces to a list of moves for that Piece.
            new Enum.Filtered(
              ON_THE_MOVE_FILTER,       // filter accepts only pieces that are on-the move
              new Move()                // for each square on the board,
            )
          )
        )
      );
  }
}

