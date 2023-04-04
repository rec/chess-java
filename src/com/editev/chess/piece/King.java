package com.editev.chess.piece;

import com.editev.chess.Game;
import com.editev.chess.Board;
import com.editev.chess.Move;
import com.editev.chess.Square;

/** The King is a Piece with a lot of special purpose code relating to the castling move.
 *
 *  @see See the source <a href="King.java">here</a>.
 */
public class King extends Piece {

    /** Is this King move illegal (for a King-specific reason)?  The only case is in fact
     *  castling, where a castle is only legal if the king hasn't moved, the rook hasn't moved,
     *  the king isn't in check and doesn't pass over a square that's under attack.
     *
     * @return true if this is an illegal king move. 
     */
    public boolean isIllegal( Move move, Game game ) {        
        if (Math.abs( move.target.column - move.source.column ) < 2) return false;

        // it's a castling move!        
        byte     king    = game.getPieceIndex( move.source );                 // king from the board.
        byte     rook    = (byte) (king + (Black.ROOK-Black.KING));     // translate into the same color rook.               
        boolean  isWhite = White.is( king );                            // black or white king?
        boolean  kSide   = 2 == getColumnOffset( move );                // is it a king-side castle?
        Square   rookSq  = new Square( kSide ? 7 : 0,  move.source.row );      // the square with the rook.
        Square   kCross  = new Square( kSide ? 5 : 3,  move.source.row );      // the square the king passes over
                
        return 
              game.kingMoved(      king            )             // the king has moved.
         ||   game.rookMoved(      king,    kSide  )             // that rook has moved
         || ( game.getPieceIndex(  rookSq          ) != rook )   // that rook has been captured (having never moved).
         ||   game.isAttacked(     isWhite, move.source   )      // the king is in check.
         ||   game.isAttacked(     isWhite, kCross )             // the king passes through check.
         || (!kSide                                               // Queen side castle only:
              && game.hasPiece( new Square( 1, move.source.row )));     // Q rook can't pass through a piece on file "b".
         
        // we don't have to check if the king *ends* in check after the castle 
        // because that is done for all moves automatically anyway (and this is an expensive
        // operation!)
    }

    /** Handles castling and also "uncastling" (undo the previous castle). 
     *
     *  @param move the move we are doing or undoing
     *  @param board the board that we are applying the move to.
     *  @param forward true if are we castling, false if we are "uncastling".
     */
    private void castle( Move move, Board board, boolean forward ) {
        byte cols = getColumnOffset( move );
        
        if (Math.abs( cols ) != 2) return;                      // not a castling move, we're OK.
        
        boolean kingSide = cols > 0;                            // K side castles move right, Q side move left.
                
        byte rookFrom = (byte) (kingSide ? 7 : 0);              // column where rook starts...
        byte rookTo   = (byte) (kingSide ? 5 : 3);              // ...where rook ends 
        
        byte startCol = (byte) (forward ? rookFrom : rookTo  ); // start col of rook's travel.
        byte endCol   = (byte) (forward ? rookTo   : rookFrom); // end col of rook's travel!
        
        Square start = new Square( startCol, move.target.row ); // start square for rook
        Square end   = new Square( endCol,   move.target.row ); // end square for rook
        
        byte rook = board.getPieceIndex( start );                    // get the actual rook that's moving.
        
        if (rook != Black.ROOK && rook != White.ROOK) {         // no rook there!  should never happen.
            throw new RuntimeException( "There's no rook for this castle! "+rook );
        }
        board.setPieceIndex( start, NO_PIECE );                      // move the piece of out that square.
        board.setPieceIndex( end,   rook     );                      // move the rook into that 
    }

    /** Apply this King move to the Board.  @return the index of the piece captured, if any, or NO_PIECE */
    public byte applyMoveToBoard( Move move, Board board ) {
        byte piece = super.applyMoveToBoard( move, board );     // save the captured piece if any.
        castle( move, board, true );                            // move the rook for castling if needed.
        return piece;                                           // return the captured piece.
    }
    
    /** Undo the effect of the last move to the board.  
     *
     *  @param move the move we are doing or undoing
     *  @param board the board that we are applying the move to.
     *  @param captured the piece that was captured, if any.
     */
    public void undoMoveToBoard( Move move, Board board, byte captured ) {
        super.undoMoveToBoard( move, board, captured );         // upcapture any piece that was captured.
        castle( move, board, false );                           // undo the castling move if any.
    }
    
    /** Apply a King move to the Game game by marking the King as moved. */
    public void applyMoveToState( Move move, Game game  ) {
        super.applyMoveToState( move, game );
        game.moveKing( game.getPieceIndex( move.target ) );  // mark this king as moved.
    }  

    /** An array of all the possible King moves as byte offsets. */
    public static final byte[][] MOVES = {
                    {-1, -1 }, { 0, -1 }, { 1, -1 }, 
        {-2,  0 },  {-1,  0 },            { 1,  0 }, { 2,  0 },
                    {-1,  1 }, { 0,  1 }, { 1,  1 }
    };

    /** This class is a singleton, so the constructor is private. */
    private King() { super( MOVES ); }
    
    /** The unique/singleton instantiation of King. */
    public static final King PIECE = new King();

}
