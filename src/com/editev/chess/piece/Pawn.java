package com.editev.chess.piece;

import com.editev.chess.Game;
import com.editev.chess.Move;
import com.editev.chess.Board;
import com.editev.chess.Square;
import com.editev.chess.Chess;

/** The Pawn has four special cases; first move, en passant, capturing and promotion.
 *  This is the most complicated of all the Pieces, and there are two derived classes,
 *  Pawn.Black and Pawn.White.
 *
 *  @see See the source <a href="Pawn.java">here</a>.
 */
abstract public class Pawn extends Piece {
    
    /** Is this Pawn move legal?  It may only move two squares on the first move.
     *  It moves forward to empty squares, and captures diagonally.  
     *  En passant occurs if the opponent's last move was the two square first move
     *  and the player could have captured the pawn if it only moved one square.
     *  Promotion isn't involved here. 
     *  
     *  @return true if this Pawn move is illegal
     */
    public boolean isIllegal( Move move, Game game ) {
        byte    rows     = getRowOffset(   move );                  // moving how many rows
        byte    columns  = getColumnOffset( move );                 // number of columns to move....
        boolean isWhite  = rows < 0;                                // is it a white piece?
        boolean occupied = game.getPieceIndex( move.target ) != NO_PIECE;   // is there a piece on the target square?
        byte    home     = (byte) (isWhite ? 6 : 1);                // home space for an unmoved pawn.
        
        if (2 == Math.abs( rows )) {                    // moving 2 rows?
            return occupied                             // illegal to move forward onto another piece.
                   || move.source.row != home;            // can't have moved this pawn before.
        }
        
        if (columns == 0) return occupied;              // illegal to move forward on other piece.
        
        if (game.getPieceIndex( move.target ) == NO_PIECE) {    // can only be en passant
                                                        // because we are moving diagonally, but no piece!
            if (game.getEP() != move.target.column) return true;
                                                        // not ep'ing the correct column.
            byte epRow = (byte) (isWhite ? 3 : 4);      // can only ep from one possible row.
            return move.source.row != epRow;              // fail if wrong row.
        }
        return false;
    }
    
    /** Increment the move to the next one.  This is overridden in order to 
     *  count through the promotions. 
     */
    public boolean incrementMoveIndex( Move move ) {
        if (!moreMoves( move )) return false;
        if (isPromotion( move ) ) {
            if (++move.promotion <= Chess.Black.KNIGHT) return true;            // there's another piece in the promotion cycle
            move.promotion = Chess.Black.QUEEN;                                 // start a new promotion cycle next time.
            if (!super.moreMoves( move ))   return false;           // no more moves. 
        }
        return super.incrementMoveIndex( move );                         // otherwise, continue with a new possible move.
    }
    
    /** Any more moves? This is also overridden to take care of the promotions. */
    public boolean moreMoves( Move move                ) { 
        return   super.moreMoves( move ) 
            || (    move.index == (moves.length-1) 
                 && isPromotion( move )
                 && move.promotion < Chess.Black.KNIGHT 
               );
    }

    
    /** Apply the move to the squares of the board.  
     *  In the case of a promotion, we need to replace the Pawn with another Piece.
     *  In the case of an e.p. capture, we need to remove the captured enemy Pawn.
     */
    public byte applyMoveToBoard( Move move, Board board ) {
        boolean isEP    = isepCapture(              move, board );  // have to do this first before changing the board!
        byte    piece   = super.applyMoveToBoard(   move, board );        
        
        // check for promotion.
        //        
             if (isPromotion( move, WHITE )) board.setPieceIndex( move.target, (byte) (move.promotion+WHITE) );
        else if (isPromotion( move, BLACK )) board.setPieceIndex( move.target, (byte) (move.promotion+BLACK) );
        else if (isEP) {                                             // an en passant capture!
            Square sq = new Square( move.target.column, move.source.row  );
            board.setPieceIndex( sq, NO_PIECE );                     // erase the actual captured pawn.
            piece = EP_CAPTURE;                                 // special flag to unmove squares tells of e.p. pawn capture.
        }

        return piece;                                           // return the name of the captured piece, if any....
    }  

    /** Unapply the move to the squares of the board.  
     *  We have the same special cases as applyMoveToBoard, namely promotion and e.p. 
     */
    public void undoMoveToBoard( Move move, Board board, byte piece ) {  
        if (piece == EP_CAPTURE ) {
            super.undoMoveToBoard( move, board, NO_PIECE );           // move the pawn back, leave an empty square.
            board.setPieceIndex( new Square( move.target.column, move.source.row ),
                                  (move.source.row == 3) ? Chess.Black.PAWN : Chess.White.PAWN );
                                                                            // undo the e.p. capture.
            return;
        }        
        
        super.undoMoveToBoard( move, board, piece );           // move the pawn back, replacing any captured piece.
        if (isPromotion( move, WHITE )) {
            board.setPieceIndex( move.source, Chess.White.PAWN );  // undo the white promotion
        } else if (isPromotion( move, BLACK )) {
            board.setPieceIndex( move.source, Chess.Black.PAWN );  // undo the black promotion
        }
    }
    
    /** Change the GameState component of the game for this pawn move -- we need to override this method
     *  because we need to store the e.p. game to validate next pawn moves for a possible e.p. capture.
     */
    public void applyMoveToState( Move move, Game game ) {
        super.applyMoveToState( move, game );       // superclass.
        
        // if we move two spaces then there is a possibility for e.p. capture next move.
        //
        if (isTwoSquare( move ) ) game.setEP( move.source.column );
    }
    
    /** Pawn moves are never reversible.
     *  @return false.
     */
    public boolean isIrreversible( Move move, Board board )       { return true; }

    /** We have to override this method because Pawns only capture diagonally. */    
    public boolean isCapture(          Move move, Board board ) { return move.source.column != move.target.column; }  

    /** Is this move a promotion for the given color? */
    public static boolean isPromotion( Move move, byte  color ) { return !isTwoSquare( move ) && move.target.row == notColor( color ); }

    /** Is this move a promotion for either color? */
    public static boolean isPromotion( Move move              ) { return isPromotion( move, WHITE ) || isPromotion( move, BLACK ); }

    /** Is this a two square move for the pawn? */
    public static boolean isTwoSquare( Move move              ) { return Math.abs( move.source.row - move.target.row ) > 1; }

    /** Special piece indicates an en passant (e.p.) capture. */
    public static byte EP_CAPTURE = (byte) (NO_PIECE - 1);    
    
    /** Is this legal move an e.p. capture? */
    public boolean isepCapture(        Move move, Board board ) { return     isCapture( move, board ) 
                                                                   && !super.isCapture( move, board ); }
                                               // ep captures are pawn captures where the "target" square is empty.
                                               
    protected Pawn( byte[][] moves ) { super( moves ); }
    
    /** This derivation of Pawn represents a Black pawn, and only differs from the White pawn by its list of moves. */
    public static class Black extends Pawn {
    
        /** An array of all the possible Pawn.Black moves as byte offsets. */
        public static final byte[][] MOVES = {

            {-1,  1}, {0,  1}, {1,  1},
                      {0,  2},

        };
        
        /** This class is a singleton, so the constructor is private. */
        private Black() { super( MOVES ); }
    
        /** The unique/singleton instantiation of Pawn.Black. */
        public static final Black PIECE = new Black();
    }

    /** This derivation of Pawn represents a White pawn, and only differs from the Black pawn by its list of moves. */
    public static class White extends Pawn {
    
        /** An array of all the possible Pawn.White moves as byte offsets. */
        public static final byte[][] MOVES = {
        
                      {0, -2},
            {-1, -1}, {0, -1}, {1, -1},

        };
        /** This class is a singleton, so the constructor is private. */
        private White() { super( MOVES ); }
        
        /** The unique/singleton instantiation of Pawn.White. */
        public static final White PIECE = new White();
    }
}
