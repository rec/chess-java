package com.editev.chess;


/** This class represents a single game of chess. <BR>
 *  It comprises the board, which is the location of the pieces; <BR>
 *  the game, containing all other information about the game, like castling, ep, 50 move rule, etc.  <BR>
 *
 *  @see See the source <a href="GameState.java">here</a>.*/
public class GameState extends Board {

  /** Has the white king moved yet? */
  boolean    whiteKingMoved;                      

  /** Has the black king moved yet? */
  boolean    blackKingMoved;                      

  /** Has the black queen rook moved yet? */
  boolean    whiteQRookMoved;                      

  /** Has the white king rook moved yet? */
  boolean    whiteKRookMoved;                      

  /** Has the black queen rook moved yet? */
  boolean    blackQRookMoved;                      

  /** Has the black king rook moved yet? */
  boolean    blackKRookMoved;                      

  /** Moves since the last irreversible move. */
  int          reversibleMoves = 0;

  /** Moves since the start of the game. */
  int          moves             = 0;

  /** This constant represents the very common case where the last 
   *  move didn't introduce the possibility of an en passant capture in the next move. */
  public static final byte NO_EP_COLUMN   = (byte) -1;

  /** Which row the previous (2-square, pawn) move was in, for a possible e.p., or NO_EP_COLUMN if none. */
  byte         epColumn     = NO_EP_COLUMN;
  

  /** Moves since start of game. 
   *  @return the number of moves since the start of the game 
   */
  public int     getMoves()                             { return moves;            }

  /** Moves since start of game. 
   *  @return the number of moves since the start of the game 
   */
  public void    incrementMoves()                       { moves++;                }

  /** Is white on the move? 
   *  @return true if White is on the move. 
   */
  public boolean isWhiteMove()                { return 0 == (moves & 1); }
  
  /** Does this piece belong to the player on-the-move? 
   * @return true if this is a piece on the move. 
   */
  public boolean isPieceOnMove( byte piece )  { return isWhiteMove() ? White.is( piece ) : Black.is( piece ); }
  
  /** Can this piece be captured by the player on the move?
   *  @return true if this is a piece that can be captured. 
   */
  public boolean canBeCaptured( byte piece )  { return isWhiteMove() ? Black.is( piece ) : White.is( piece ); }
  

  /** Has a king been moved?
   *
   *  @return true if the king has been moved.  
   *  @param color a piece to indicate the color. 
   */
  public boolean kingMoved( byte king ) { return White.is( king ) ? whiteKingMoved : blackKingMoved;            }
  
  /** Marks a king as having been moved.
   *
   * @param color a piece to indicate the color. 
   */
  public void    moveKing(  byte king                ) { 
    if( White.is( king ) ) whiteKingMoved = true; 
    else                   blackKingMoved = true;   
  }
  
  /** Has a rook been moved?
   *
   *  @return true if the rook has been moved. 
   *  @param color a piece to indicate the color. 
   *  @param isKingSide true if we are looking at the kingside rook 
   */
  public boolean rookMoved( byte rook, boolean kSide ) { return White.is( rook ) ? (kSide ? whiteKRookMoved:whiteQRookMoved) : (kSide ? blackKRookMoved:blackQRookMoved); }

  /** Mark a rook as having been moved.
   *
   *  @param color a piece to indicate the color. 
   *  @param isKingSide true if we are looking at the kingside rook 
   */
  public void    moveRook(  byte rook, boolean kSide ) {
    if ( White.is( rook ) ) {
      if (kSide ) whiteKRookMoved = true; 
      else        whiteQRookMoved = true;
    } else {
      if (kSide ) blackKRookMoved = true; 
      else        blackQRookMoved = true;
    }
  }

         
  /** An irreversible move has occurred, which must be a capture or a pawn move (50 move rule). 
   */
  public void    irreversibleMove()       { reversibleMoves = 0; }

  /** Get the number of moves since an irreversible move has occurred (50 move rule). 
   */
  public int     getReversibleMoves()     { return reversibleMoves; }

  /** Set the e.p. column indicating that the last pawn move was two squares (first move). 
   */
  public void    setEP( byte column )     { epColumn = column;   }

  /** Clear the enpassant column, the last move wasn't a pawn or wasn't two squares. 
   */
  public void    clearEP()                { setEP( NO_EP_COLUMN ); }

  /** @return e.p. column if there's a potential e.p. capture this turn. 
   */
  public byte    getEP(  )                { return epColumn;   }    
}

