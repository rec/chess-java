package com.editev.chess.piece;

import com.editev.chess.Board;
import com.editev.chess.Move;

/**  A Knight is a Piece can jump any other Piece but otherwise has no special rules.
 *
 *  @see See the source <a href="Knight.java">here</a>.
 */
public class Knight extends Piece {

    /** The knight jumps all pieces so piecesBetween always returns false. 
     *  @return false always. */
    public boolean pieceBetween( Move move, Board board ) { return false; }

    /** An array of all the possible Knight moves as byte offsets. */
    public static final byte[][] MOVES = {
                  {-1, -2},         {1, -2},
        {-2, -1},                              {2, -1},
        
        {-2,  1},                              {2,  1},
                  {-1,  2},         {1,  2},
    };
    
    /** This class is a singleton, so the constructor is private. */
    private Knight() { super( MOVES ); }

    /** The unique/singleton instantiation of Knight. */
    public static final Knight PIECE = new Knight();

}
