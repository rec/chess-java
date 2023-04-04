package com.editev.chess.piece;

import com.editev.chess.Board;
import com.editev.chess.Chess;
import com.editev.chess.Game;
import com.editev.chess.Move;
import com.editev.chess.Square;

/** Contains a list of possible piece moves as byte offsets, 
 *  and encompasses all the rules about legal moves and actually performing 
 *  the move as an operation on the Game.
 *
 *  @see See the source <a href="Piece.java">here</a>.
 */
abstract public class Piece extends Chess {

    /** Is there a piece between the "from" and "target" squares? 
     *  Only the Knight overrides this method. */
    public boolean pieceBetween( Move move, Board board ) {    
        byte c       = getColumnOffset( move );   // number of columns and rows to check.
        byte r       = getRowOffset(    move );
        
        byte squares = (byte) Math.max( Math.abs( c ), 
                                        Math.abs( r ) );   // number of squares between from and to.
                                                         
        if (squares > 1) {                     // there might be pieces between!
            byte dc = (byte) (c / squares);    // increment for the col and row...
            byte dr = (byte) (r / squares);    // dc and dr can only be 0, +1 or -1.
            
            Square sq = new Square(        // increment through all squares between
                (byte) (move.source.column+dc),   // first column between
                (byte) (move.source.row   +dr)    // first row between.
            );
            
            for (byte s=1;          // first square
                 s < squares;       // up to but not including the end.
                 sq.column += dc,  // increment column, row, square
                 sq.row    += dr,
                 s++) 
            {
                if (board.hasPiece( sq )) return true; // we found a piece in the way!
            }
        }
        return false;   // we found no pieces between "from" and "to".
    }

    /** Adjust the board's pieces only.  @return the captured piece if any.
     * 
     *  Only the King and Pawn override this method. */
    public byte applyMoveToBoard( Move move, Board board ) {
        byte toPiece = board.getPieceIndex( move.target );              // save the captured piece.

        board.setPieceIndex( move.target, board.getPieceIndex( move.source ) );   // move the piece to "target"...
        board.setPieceIndex( move.source, NO_PIECE                           );            // from "from".

        return toPiece;                                                 // return the captured piece on  
    }
    
    /** Undo a move, using the piece captured, if any.  
     * 
     *  Only the King and Pawn override this method. */
    public void undoMoveToBoard( Move move, Board board, byte captured ) {  
        board.setPieceIndex( move.source, board.getPieceIndex( move.target )   );   // undo the move.
        board.setPieceIndex( move.target,   captured                    );   // replace any captured pieces.
    }
    
    /** Adjust the board's game, which is everything that isn't the board's squares: 
     *  handles promotions, e.p., castling rules, 50 move rule.   We DO need the Game and
     *  and not just the game because we need to see if the move is a capture...  
     * 
     *  The King, the Pawn and the Rook override this method. */
    public void applyMoveToState( Move move, Game game ) {
        game.incrementMoves();
        game.clearEP();
    }
    
    /** Go to the first move Index.
     *  @return false if there are no more possible moves to go to. 
     * 
     *  Only the Pawn overrides this method. */
    public void firstMoveIndex( Move move ) { setMoveIndex( move, (byte) 0 ); }

    /** Go to a specific move index.
     *  @return false if there are no more possible moves to go to. 
     * 
     *  Only the Pawn overrides this method. */
    public void setMoveIndex( Move move, byte index ) {
        move.index         = index;
        move.target.column = (byte) (move.source.column + getColumnOffset( move ) );
        move.target.row    = (byte) (move.source.row    + getRowOffset(    move ) );
        move.resetPromotion();
    }
    
    /** Go to next possible move.
     *  @return false if there are no more possible moves to go to. 
     * 
     *  Only the Pawn overrides this. */
    public boolean incrementMoveIndex( Move move ) {
        if (!moreMoves( move ) ) return false;
        setMoveIndex( move, ++move.index );
        return true;
    }
    
    /** Any more possible moves?
     *  @return true if there are more possible moves after this move. 
     * 
     *  Only the Pawn overrides this. */
    public boolean moreMoves( Move move              ) { return move.index < (moves.length-1);  }
    
    /** Is this move a capture on this board?
     *  @return whether this move is a capture for this board.
     * 
     *  Only the Pawn overrides this, for e.p. captures and to prevent capturing by advancing straight */
    public boolean isCapture( Move move, Board board ) { return board.hasPiece( move.target ); }
    
    /** Is this move irreversible?
     *  @return true if this move restarts the 50 move rule.
     * 
     *  Only the Pawn overrides this.
     */
    public boolean isIrreversible( Move move, Board board ) { return isCapture( move, board ); }
    
    /** Is this illegal because of a piece-specific rule?
     *  @return true if the piece makes this move illegal for this game.
     * 
     *  We only override this for King and Pawn. */
    public boolean isIllegal( Move move, Game  game  ) { return false; }

    /** The moves describe all the possible moves in left-to-right, top-to-bottom order. */
    public final  byte[][] moves;
    public Piece( byte[][] moves ) { this.moves = moves; }

    /** Convenience function to translate a move's index into a column offset from the move table.*/
    public final byte getColumnOffset( Move move ) { return moves[ move.index ][ 0 ]; }

    /** Convenience function to translate a move's index into a row offset from the move table.*/
    public final byte getRowOffset(    Move move ) { return moves[ move.index ][ 1 ]; }    
}
