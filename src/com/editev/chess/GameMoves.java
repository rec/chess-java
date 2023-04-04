package com.editev.chess;

import java.util.Enumeration;

/** A GameMoves is a Game with a list of legal Moves for that Game.
 *
 *  @see See the source <a href="GameMoves.java">here</a>.
 */
public class GameMoves extends GameEnum {

    /** Estimate of max legal moves per position:  max ever actually recorded in a random game is 71. */
    public static final short MAX_MOVES = 80;
    
    /** Store all the legal next moves from this board position. */
    final Moves allMoves = new Moves( MAX_MOVES );
    
    /** Have we enumerated the next moves for this position yet? */
    protected boolean movesEnumerated = false;

    /** Have we enumerated ALL the next moves for this position yet? */
    protected boolean movesCompleted  = false;
    
    /** Count all the the possible legal moves -- don't call this if you want to avoid enumerating 
     *  all possible legal moves for efficiency reasons, go right to getMove(). 
     *
     *  @return count of the number of legal next Moves for this Game.
     */
    public short getMoveCount() { computeMoves(); return (short) allMoves.getLength(); }
    
    /** @return the legal move at this location -- only computes legal moves up to this point for efficiency.
     *
     *  @param move the index of the legal move we're locating.
     *
     *  @throw IndexOutOfBoundsException if there is no legal move with this index. */
    public Move getMove( short move ) { 
        computeMoves( move );                   // get all moves to this point.
        if (move >= allMoves.getLength() || move < 0) {    // no such move.
            throw new IndexOutOfBoundsException( "accessed move indexed "+move+ " out of "+allMoves.getLength() ); 
        }
        return allMoves.getAt( move );          // get the move from the List.
    }

    /** An enumeration of all the legal moves, might be null or partially consumed. */
    private Enumeration moveEnumeration;    

    /** Retrieves or creates an enumeration of all the legal moves. 
     *
     *  @return an Enumeration of all the legal moves.
     */
    protected Enumeration getMoveEnumeration() {
        if (!movesEnumerated) {
            moveEnumeration = enumerateAllLegalMoves();
                
            movesEnumerated = true;                     // we got the move enumeration.
            movesCompleted = false;                     // we haven't run out of moves (redundant).
            allMoves.clear();
        }
        
        return moveEnumeration;                         // return the enumeration if any.
    }

    /** Computes all next legal moves up to a certain move index. 
     *
     *  @param moveNeeded the index of the last legal move we need (or Short.MAX_SHORT for all!)
     */
    protected void computeMoves( short moveNeeded ) {        
        if (movesCompleted || (movesEnumerated && (moveNeeded < allMoves.getLength())) ) return; // already reached that point.

        Enumeration moves = getMoveEnumeration();           // get all the (remaining) moves.
        while ( moves.hasMoreElements()                     // while there are more elements to retrieve
             && moveNeeded >= allMoves.getLength()             // and we haven't found the desired move
             )                                   
        {
            allMoves.append( (Move) moves.nextElement() );
        }
        
        if (!moves.hasMoreElements()) {
            movesCompleted = true;                          // no more moves in the enumeration.
            moveEnumeration = null;                         // send enumeration to GC
        }
    }
    
    /** Compute all the legal moves. */
    protected void computeMoves(  ) { computeMoves( Short.MAX_VALUE ); }

    /** Apply a specific move to this Game.
     *  Apply the move to the parent class -- but then invalidate the stored list of legal moves.
     *
     *  @return the index of the captured piece, if any (special case for ep).
     *  @see com.editev.chess.Game. */
    public byte applyMove( Move move ) {
        byte captured = super.applyMove( move );            // apply the move.
        movesCompleted  = false;            // now invalidate the cached enumeration of moves.
        movesEnumerated = false;
        
        allMoves.clear();
        moveEnumeration = null; // redundant.
        return captured;
    }

    public Object clone() { return cloneGameMoves(); }
    
    public GameMoves cloneGameMoves() { 
        GameMoves game = (GameMoves) super.clone();
        return game;
    }        
}

  