package com.editev.chess.piece;

import com.editev.chess.Game;
import com.editev.chess.Move;

/** Once a Rook has moved, it may no longer castle, but otherwise has no specific rules.
 *
 *  @see See the source <a href="Rook.java">here</a>.
 */
public class Rook extends Piece {
    /** If you move a rook, you can no longer castle with that rook -- the Game must reflect this. */
    public void applyMoveToState( Move move, Game game ) {         
        super.applyMoveToState( move, game );
             if (move.source.column == 0) game.moveRook( game.getPieceIndex( move.target ), false ); // mark that Queen's rook as moved
        else if (move.source.column == 7) game.moveRook( game.getPieceIndex( move.target ), true  ); // mark that King's  rook as moved
    }

    /** An array of all the possible Rook moves as byte offsets. */
    public static final byte[][] MOVES = {
                                                                       {0, -7}, 
                                                                       {0, -6}, 
                                                                       {0, -5}, 
                                                                       {0, -4}, 
                                                                       {0, -3}, 
                                                                       {0, -2}, 
                                                                       {0, -1}, 
        {-7,  0}, {-6,  0}, {-5,  0}, {-4,  0}, {-3,  0}, {-2,  0}, {-1,  0},           {1,  0}, {2,  0}, {3,  0}, {4,  0}, {5,  0}, {6,  0}, {7,  0},
                                                                       {0,  1}, 
                                                                       {0,  2}, 
                                                                       {0,  3}, 
                                                                       {0,  4}, 
                                                                       {0,  5}, 
                                                                       {0,  6}, 
                                                                       {0,  7}, 
    };
    /** This class is a singleton, so the constructor is private. */
    private Rook() { super( MOVES ); }
    
    /** The unique/singleton instantiation of Rook. */
    public static final Rook PIECE = new Rook();

}
