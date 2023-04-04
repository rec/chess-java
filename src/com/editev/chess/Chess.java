package com.editev.chess;

/** Constants defining the game of chess.
 *  @see See the source <a href="Chess.java">here</a>.*/
abstract public class Chess {
    /** Represents no piece on a square. */
    public static final byte NO_PIECE    = 0;

    /** Represents no move from a list. */
    public static final byte NO_MOVE     = -1;

    /** A set of constants representing Black's pieces. */
    public static class Black {
        /** Black's home rank, also used as an identifier for Black */
        public static final byte BLACK   = 0; 
        
        /** Piece index for Black's King. */
        public static final byte KING    = 1;
        
        /** Piece index for Black's Queen. */
        public static final byte QUEEN   = 2;
        
        /** Piece index for Black's Rook. */
        public static final byte ROOK    = 3;
        
        /** Piece index for Black's Bishop. */
        public static final byte BISHOP  = 4;
        
        /** Piece index for Black's Knight. */
        public static final byte KNIGHT  = 5;
        
        /** Piece index for Black's Pawn. */
        public static final byte PAWN    = 6;

        /** @return true if the piece is Black (empty pieces are neither Black nor White). 
         * 
         *  @param piece the byte index of the Piece
         */
        public static final boolean is( byte piece ) { return piece >= KING && piece <= PAWN; }
    }     
    
    /** A set of constants representing White's pieces. */
    public static class White {    
        /** White's home rank, also used as an identifier for White */
        public static final byte WHITE   = 7; 
        
        /** Piece index for White's King. */
        public static final byte KING    = 8;
        
        /** Piece index for White's Queen. */
        public static final byte QUEEN   = 9;
        
        /** Piece index for White's Rook. */
        public static final byte ROOK    = 10;
        
        /** Piece index for White's Bishop. */
        public static final byte BISHOP  = 11;
        
        /** Piece index for White's Knight. */
        public static final byte KNIGHT  = 12;
        
        /** Piece index for White's Pawn. */
        public static final byte PAWN    = 13;

        /** @return true if the piece is White (empty pieces are neither Black nor White). 
         * 
         *  @param piece the byte index of the Piece
         */
        public static final boolean is( byte piece ) { return piece >= KING && piece <= PAWN; }
    }
    
    /** Black's home rank, also used as an identifier for Black */
    public static final byte BLACK = Black.BLACK;

    /** White's home rank, also used as an identifier for White */
    public static final byte WHITE = White.WHITE;
    
    /** Exchange WHITE and BLACK.  @parameter color a color, either BLACK or WHITE. @return the other color, White or Black.*/
    public static final byte notColor( byte color ) { return (byte) (WHITE - color); }
}