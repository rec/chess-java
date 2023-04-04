package com.editev.chess.piece;

/** A Bishop has no specific rules attached to it, just a list of moves.
 *
 *  @see See the source <a href="Bishop.java">here</a>.
 */
public class Bishop extends Piece {
    /** An array of all the possible Bishop moves as byte offsets. */
    public static final byte[][] MOVES = {
        {-7, -7},                                                                                                                           {7, -7}, 
                  {-6, -6},                                                                                                        {6, -6},
                            {-5, -5},                                                                                      {5, -5},
                                      {-4, -4},                                                                   {4, -4},
                                               {-3,  -3},                                                {3, -3},
                                                          {-2,  -2},                            {2, -2},
                                                                    {-1, -1},          {1, -1},

                                                                    {-1,  1},          {1,  1},
                                                          {-2,   2},                            {2,  2},
                                               {-3,   3},                                                {3,  3},
                                      {-4,  4},                                                                   {4,  4},
                            {-5,  5},                                                                                      {5,  5},
                  {-6,  6},                                                                                                        {6,  6},
        {-7,  7},                                                                                                                           {7,  7}, 
    };
    
    /** This class is a singleton, so the constructor is private. */
    private Bishop() { super( MOVES ); }
    
    /** The unique/singleton instantiation of Bishop. */
    public static final Bishop PIECE = new Bishop();
}
