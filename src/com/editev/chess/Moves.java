package com.editev.chess;

import com.editev.util.Lists;

/** A growing, unsynchronized list of Move Objects. 
 *
 *  @see See the source <a href="Moves.java">here</a>.
 */
public class Moves extends Lists {
    /** Actually allocates a new list of the right class and size. */
	protected Object newList( int capacity ) { return new Move[ capacity ];   }

    /** @return the list as an array of Move Objects. */
	protected Move[]  getList()              { return (Move[]) list; }

    /** @return the possibly capacity of the list -- if you want the length, use Lists.getLength(). */
	protected int  getCapacity()             { return getList().length; }

    /** @return the Move at a given integer index.
     *  @param i index of the Move.
     * 
     *  @throws ArrayIndexOutOfBoundsException if the index is not less than getLength()
     */
	public    Move getAt( int i ) {
	    Move[] moves = ((Move[]) Moves.this.checkList( i ) );
	    if (moves[ i ] == null) moves[ i ] = new Move();
	    return moves[ i ];
	}

    /** Puts a Move at the end of the list.
     * 
     *  @param move Move to append.
     * 
     */
	public void append( Move move )    {
	    int l = getLength();
	    setLength( l+1 );
	    getAt( l ).copyFrom( move );
	}
	
    /** Sets a Move at a given index
     *  @param i index of the Move.
     *  @param m Move to set
     * 
     *  @throws ArrayIndexOutOfBoundsException if the index is not less than getLength()
     */
	//public    void setAt( int i, Move m )    { ((Move[]) Moves.this.checkList( i ) )[ i ] = m; }

    /** Creates an empty Move with the default capacity. */
	public Moves()                           { }

    /** Creates an empty Move with the specified capacity. */
	public Moves( int capacity )             { super( capacity ); }
}