package com.editev.chess;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import com.editev.util.ExceptionWrapper;

/** Represents a move in the game of chess. 
 *
 *  A move is identified with its start square.  
 *  There's also a target square, and perhaps a promotion piece.
 *  This class is used promiscuously as an iterator, perhaps the
 *  ultimate reverse of an immutable class.
 *
 *  @see See the source <a href="Move.java">here</a>.
 */
public class Move implements Enumeration, Cloneable {
    /** The target square that this move is going to. */
    //public final Square source   = this;
    public Square source   = new Square();

    /** The target square that this move is going to. */
    public Square target   = new Square();

    /** Index the move in the series of offsets in the Piece. 
     *  The Piece manipulates this variable to iterate through all the
     *  the offsets that represent possible moves.
     */
    public byte  index;

    /** Implements Enumeration in row-major order. */
    public boolean hasMoreElements() { return source.row < 7 || source.column < 7; }
    
    /** Implements Enumeration in row-major order. */
    public Object nextElement() {
        if (!hasMoreElements()) throw new NoSuchElementException();
        if (++source.column > 7) {
            source.column = 0;
            source.row++;
        }
        return this;
    }

    /** What piece is being created if this move is a promotion?
     *  Most of the time, Moves are not promotions and this field is wasted.
     */
    public byte  promotion = Chess.Black.QUEEN;
    
    /** Reset the promotion variable to the start. */
    public void  resetPromotion() { promotion = Chess.Black.QUEEN; }
    
    /** Two Moves are equal if they are equal as squares and their targets are equal as Squares. */
    public boolean equals( Object object ) {
        if (!(object instanceof Move)) return false;
        Move move = (Move) object;
        return super.equals( move ) && 
            target.equals( move.target ) && 
            promotion == move.promotion;
    }
    
    /** Clone this Move as an Object.  @return an Object that clones this Move. */
    public Object clone() { return cloneMove(); }
    
    /** Clone this Move as a Move.  @return a Move that clones this Move. */
    public Move cloneMove() {
        try { 
            Move move   = (Move)   super.clone();
            move.source = new Square( source.column, source.row );
            move.target = new Square( target.column, target.row );
            return move;
        }
        catch (CloneNotSupportedException e) { 
            throw new ExceptionWrapper( e );  // should never get here!
        }
    }
    
    /** Represent the Move as the source and target Squares.*/    
    public String toString() { return source.toString() +"-"+target.toString(); }
    
    /** Copy the contents of another Move into this Move. */
    public void copyFrom( Move move ) {
        target.copyFrom( move.target );
        source.copyFrom( move.source );
        index         = move.index;
        promotion     = move.promotion;
    }
}
