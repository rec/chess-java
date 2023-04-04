package com.editev.chess;

import com.editev.util.Filter;
import com.editev.util.Function;
import com.editev.util.Enum;
import com.editev.chess.piece.Piece;
import java.util.Enumeration;

/** A Game that can enumerate its legal Moves.
 *
 *  @see See the source <a href="EnumeratedGame.java">here</a>.
 */
public class EnumeratedGame extends Game {

    /** Enumeration of every legal move for this Game.  
     *  Don't change the game in the middle of getting the enumeration or this might not work.
     *  @return a new Enumeration of all legal moves for this game.
     */
    public Enumeration enumerateAllLegalMoves() {
        return
            new Enum.Filtered(            
                new AcceptLegalMoves(),                     // filter accepts only legal moves..
                new Enum.Compound(                          // for each legal move for each piece.
                    new Enum.Mapped(
                        new MakePieceMoveEnumerator(),     // map Pieces to a list of moves for that Piece.
                        new Enum.Filtered(
                            new AcceptOnTheMove(),         // filter accepts only pieces that are on-the move
                            new Move()                     // for each square on the board,
                        )
                    )
                )
            );
    }

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
    
    /** Constructs a list of Piece moves from a Square. */
    class MakePieceMoveEnumerator implements Function {
        /** Create a new emumeration of the Pieces 
         *  @return an enumeration of moves for the piece moving. 
         *  @param discard ignore this:  we look at the central iterator Move.
         */
        public Object eval( Object move ) { return new PieceMoveEnumerator( (Move) move ); }
    };

    /** A class only accept legal moves. */
    class AcceptLegalMoves implements Filter {
        /** @param move a move to check for legality. 
         *  @return true if the Move is legal.*/
        public boolean accept( Object move ) { return !isIllegal( (Move) move ); }
    };

    /** Only accepts Squares containing pieces that are on-the-move. */
    class AcceptOnTheMove implements Filter {
        /** @param move a move which might have a Piece.
         *  
         *  @return true if the source of the Move contains a Piece and that Piece is on-the-move.
         */
        public boolean accept( Object move ) {
            byte piece = getPieceIndex( ((Move) move).source );
            return piece != NO_PIECE && isWhiteMove() == White.is( piece );
        }
    }    
}

