package com.editev.chess;

import com.editev.chess.piece.Bishop;
import com.editev.chess.piece.King;
import com.editev.chess.piece.Knight;
import com.editev.chess.piece.Pawn;
import com.editev.chess.piece.Piece;
import com.editev.chess.piece.Queen;
import com.editev.chess.piece.Rook;
import com.editev.util.ExceptionWrapper;

/** Represent just the Pieces on the 64 Squares of the chess board as byte indices.
 *
 *  @see See the source <a href="Board.java">here</a>.
 */
public class Board extends Chess implements Cloneable {
	/** The actual 64 squares in an 8x8 array.  Pieces are represented as simple byte indices for efficiency. */
  private final byte[][] squares = {
      { Black.ROOK,   Black.KNIGHT,   Black.BISHOP,   Black.QUEEN,    Black.KING,     Black.BISHOP,   Black.KNIGHT,   Black.ROOK  },
      { Black.PAWN,   Black.PAWN,     Black.PAWN,     Black.PAWN,     Black.PAWN,       Black.PAWN,     Black.PAWN,   Black.PAWN  },
      { NO_PIECE,     NO_PIECE,        NO_PIECE,       NO_PIECE,       NO_PIECE,         NO_PIECE,        NO_PIECE,     NO_PIECE  },   
      { NO_PIECE,     NO_PIECE,        NO_PIECE,       NO_PIECE,       NO_PIECE,         NO_PIECE,        NO_PIECE,     NO_PIECE  },   
      { NO_PIECE,     NO_PIECE,        NO_PIECE,       NO_PIECE,       NO_PIECE,         NO_PIECE,        NO_PIECE,     NO_PIECE  },   
      { NO_PIECE,     NO_PIECE,        NO_PIECE,       NO_PIECE,       NO_PIECE,         NO_PIECE,        NO_PIECE,     NO_PIECE  },   
      { White.PAWN,   White.PAWN,     White.PAWN,     White.PAWN,     White.PAWN,       White.PAWN,     White.PAWN,   White.PAWN  },
      { White.ROOK,   White.KNIGHT,   White.BISHOP,   White.QUEEN,    White.KING,     White.BISHOP,   White.KNIGHT,   White.ROOK  }
    };
    
  /** Get a piece index by Square. */
  public byte getPieceIndex(Square sq) {
    return squares[sq.row][sq.column];
  }

  /** Set a piece index by Square. */
  public void setPieceIndex(Square sq, byte piece) {
    squares[sq.row][sq.column] = piece;
  }

  /** Is there a piece on this square? @return true if there is a piece at this Square */
  public boolean hasPiece(Square sq) {
    return squares[sq.row][sq.column] != NO_PIECE;
  }

  /** A list of Pieces for each piece index which is
   *  used to translate the indices representing pieces in the Board into Pieces with rules. */
  public static Piece PIECES[] = { 
      null, King.PIECE, Queen.PIECE, Rook.PIECE, Bishop.PIECE, Knight.PIECE, Pawn.Black.PIECE, 
      null, King.PIECE, Queen.PIECE, Rook.PIECE, Bishop.PIECE, Knight.PIECE, Pawn.White.PIECE, };

  /** Gets the Piece at the given square. 
   *  @return the Piece at that square or null if there is no piece at that Square.
   *
   *  @param sq the Square on the Board containing the Piece.
   */
  public Piece getPiece(Square sq) {
    return PIECES[getPieceIndex(sq)];
  }

  /** Gets the Piece being Moved.. 
   *  @return the Piece at the source of the Move or null if there is no piece at the source..
   *
   *  @param sq the Square on the Board containing the Piece.
   */
  public Piece getPiece(Move move) {
    return getPiece(move.source);
  }

  /** Find a Piece on the board.  
   *  @return a square containing the first matching piece on the board when searched
   *  in row-major order or null if no such piece. */
  public Square findPieceSquare(byte piece) {
    Square sq = new Square();

    for (sq.row = 0; sq.row < 8; sq.row++)
      // look through the rows
      for (sq.column = 0; sq.column < 8; sq.column++)
        // and the columns
        if (getPieceIndex(sq) == piece) // found that piece!
          return sq; // return its location.

    return null;
  }

  /** Two boards are equal if all their squares are equal. */
  public boolean equals(Object x) {
    if (!(x instanceof Board))
      return false;
    Board board = (Board) x;
    Square square = new Square();
    for (square.row = 0; square.row < 8; square.row++) {
      for (square.column = 0; square.column < 8; square.column++) {
        if (getPieceIndex(square) != board.getPieceIndex(square)) {
          return false;
        }
      }
    }
    return true;
  }

  /** A deep copy clone().  
   *  @return a deep copy clone of the board with entirely new arrays. */
  public Object clone() {
    return cloneBoard();
  }

  /** A deep copy clone() that returns a Board. 
   *  @return a deep copy clone of the board with entirely new arrays. */
  public Board cloneBoard() {
    Board board;
    try {
      board = (Board) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new ExceptionWrapper(e);
    } // can't happen!
    for (byte b = 0; b < 8; b++)
      board.squares[b] = (byte[]) board.squares[b].clone();
    return board;
  }

  /** Character identifiers for the pieces. */
  public static final char[] PIECE_NAMES = { '.', 'k', 'q', 'r', 'b', 'n', 'p',
      '.', 'K', 'Q', 'R', 'B', 'N', 'P' };

  /** @return a String version of the board for debugging purposes. */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    Square sq = new Square();
    for (sq.row = 0; sq.row < 8; sq.row++) { // look through the rows
      for (sq.column = 0; sq.column < 8; sq.column++) { // and the columns
        buf.append(PIECE_NAMES[getPieceIndex(sq)]); // the index of the piece at the square.
      }
      buf.append('\n');
    }
    return buf.toString();
  }
}
