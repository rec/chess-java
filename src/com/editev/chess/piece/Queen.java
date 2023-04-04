package com.editev.chess.piece;

/** A Queen has no specific rules attached to it, just a list of moves.
 *
 *  @see See the source <a href="Queen.java">here</a>.
 */
public class Queen extends Piece {
    /** An array of all the possible Queen moves as byte offsets. */
    public static final byte[][] MOVES = {
        {-7, -7},                                                             {0, -7},                                                      {7, -7}, 
                  {-6, -6},                                                   {0, -6},                                             {6, -6},
                            {-5, -5},                                         {0, -5},                                     {5, -5},
                                      {-4, -4},                               {0, -4},                            {4, -4},
                                               {-3,  -3},                     {0, -3},                   {3, -3},
                                                          {-2,  -2},          {0, -2},          {2, -2},
                                                                    {-1, -1}, {0, -1}, {1, -1},
        {-7,  0}, {-6,  0}, {-5,  0}, {-4,  0}, {-3,  0}, {-2,  0}, {-1,  0},          {1,  0}, {2,  0}, {3,  0}, {4,  0}, {5,  0}, {6,  0}, {7,  0},
                                                                    {-1,  1}, {0,  1}, {1,  1},
                                                          {-2,  2},           {0,  2},          {2,  2},
                                                {-3,  3},                     {0,  3},                   {3,  3},
                                      {-4,  4},                               {0,  4},                            {4,  4},
                            {-5,  5},                                         {0,  5},                                     {5,  5},
                  {-6,  6},                                                   {0,  6},                                             {6,  6},
        {-7,  7},                                                             {0,  7},                                                      {7,  7}, 
    };
    
    /** This class is a singleton, so the constructor is private. */
    private Queen() { super( MOVES ); }

    /** The unique/singleton instantiation of Queen. */
    public static final Queen PIECE = new Queen();
}
