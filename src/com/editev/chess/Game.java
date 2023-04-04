package com.editev.chess;

import com.editev.chess.piece.Pawn;
import com.editev.chess.piece.Piece;

/** A GameState that can tell if moves are legal or not!
 *
 *  @see See the source <a href="EnumeratedGame.java">here</a>.
 */
public class Game extends GameState {

  /** @return true if this move is illegal for this Game. 
   *  Checks a move for legality for this Game.
   *
   *  @param move the move to check for legality.
   */
  public boolean isIllegal( Move move ) {
    //if (isStalemate)            return true;           // all moves are invalid after a stalemate
    if (!move.target.isWithinBoard()) return true;       // don't even consider moving out of the board.
    
    byte    fromPiece   = getPieceIndex( move.source );  // piece on the start square
    byte    toPiece     = getPieceIndex( move.target );  // piece on the end square
    Piece   piece       = getPiece( move             );

    return 
       !isPieceOnMove(      fromPiece   )     // moving opponent's piece
     || isPieceOnMove(      toPiece     )     // capturing your own piece
     || piece.pieceBetween( move, this )      // moving over pieces
     || piece.isIllegal(    move, this  )     // piece rules:  castling, check, promotion.
     || resultsInCheck(     move, piece );    // moving into check
  }

  /** apply a Move to the Game -- in other words, to the board and to the game. <BR> <BR>
   *
   *  @return the index of the captured piece, if any (special case for ep).
   *  @param sq the Move to apply.
   */
  public byte applyMove( Move move ) {
    Piece piece = getPiece( move.source );       // get the Piece from the Board.

    if (piece.isIrreversible( move, this )) {  // Irreversible moves let us clear out the history list.
        irreversibleMove();
        //history.clear();
    } else {
        //history.append( this );                // remember this board position
    }
            
    byte captured = piece.applyMoveToBoard( move, this );
    piece.applyMoveToState( move, this );

    //if (getReversibleMoves() > 50 || thirdTime()) isStalemate = true;

    return captured;        
  }
  
  
  /** Is the king of this color in check? */
  public boolean inCheck( boolean isWhite ) {
    byte     king       = isWhite ? White.KING : Black.KING; // the king for which we are looking!
    Square   square     = findPieceSquare( king );           // the king's location.
    
    if (square == null) {        
        throw new RuntimeException( "Board.resultsInCheck:  no king on the board!" );  // we should never get here.
    }
    
    return isAttacked( isWhite, square );                   // is the king attacked?    
  }

  
  /** Does this move result in check? 
   *  @return true if making this move would result in check. 
   */
  public boolean resultsInCheck( Move move, Piece piece ) {
    boolean  whitesMove = White.is( getPieceIndex( move.source ) ); // is the move white?
    byte     captured   = piece.applyMoveToBoard( move, this );     // make the move, and store any piece captured
    boolean  inCheck    = inCheck( whitesMove );                    // is the king attacked?

    piece.undoMoveToBoard( move, this, captured );                  // unmove the move regardless.

    return inCheck;                                                 // was the king attacked?
  }
  
  /** Is this square attacked? 
   *  @return true if this row/column square is attacked. 
   */
  public boolean isAttacked( boolean whitesMove, Square square ) {
    Move     move   = new Move();
    Square   source = move.source;
    for ( source.row=0; source.row<8; source.row++) {                         // look through the rows
      for ( source.column=0; source.column<8; source.column++) {            // and the columns
        byte    pieceIndex = getPieceIndex( source );                 // the index of the piece at the fromation.
        boolean whitePiece = White.is( pieceIndex );                // is it a white piece?
        
        if (pieceIndex != NO_PIECE && whitePiece != whitesMove) {   // this piece could attack the square!
          Piece piece = Board.PIECES[ pieceIndex ];               // the actual piece.
          piece.firstMoveIndex( move );
          do {
            if ( move.target.equals(       square     )         // the piece could be attacking the square!
             && !piece      .pieceBetween( move, this )         // and there are no pieces in the way!!
             )
            {
              if ( !(piece instanceof Pawn)                   // only Pawns are a special case
                  || piece.isCapture( move, this ) )          // they attack only diagonally.
              {
                return true;                                // we did find a capture!
              }
            }
          } while (piece.incrementMoveIndex( move ));             // while there are more possible moves.
        }
      }
    }  
    return false;   // the square is not attacked!
  }
  
  /** Is this board position appearing for the third time? */
  /*
  public boolean thirdTime() {
      boolean first=false;
      for (int i=history.getLength()-1; i>=0; i--) {
          if (history.getAt( i ).equals( this )) {
              if (first) return true; // found the second dupe of this board.
              first = true;           // found the first dupe.
          }
      }
      return false;
  }
  */    

}

