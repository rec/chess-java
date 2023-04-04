package com.editev.chess;


/** This class associates a count with each square on a chess board.
 *  If we are choosing a piece to move, the count represents how many 
 *  legal moves a Piece on that square would have.  If we have chosen 
 *  a piece to move and are displaying the target squares, then 
 *  we are assigning indices to the possible target moves.
 *
 *  @see See the source <a href="Counts.java">here</a>.
 */
public class Counts {
    /** Contains one byte of count for each piece on a chess board. */
    public final short[][] counts = {
        {0, 0,  0, 0,    0, 0,  0, 0 },
        {0, 0,  0, 0,    0, 0,  0, 0 },

        {0, 0,  0, 0,    0, 0,  0, 0 },
        {0, 0,  0, 0,    0, 0,  0, 0 },

        {0, 0,  0, 0,    0, 0,  0, 0 },
        {0, 0,  0, 0,    0, 0,  0, 0 },

        {0, 0,  0, 0,    0, 0,  0, 0 },
        {0, 0,  0, 0,    0, 0,  0, 0 },
    };
    
    /** @return the count for a specific Square. */
    public short getCount(     Square sq            ) { return counts[ sq.row ][ sq.column ]                  ; }

    /** Clears the count for a specific Square. */
    public void  clearCount(   Square sq            ) {        counts[ sq.row ][ sq.column ] = 0              ; }

    /** Increments the count for a specific Square. */
    public void  incCount(     Square sq            ) {        counts[ sq.row ][ sq.column ]++                ; }

    /** Sets the count for a specific Square to indicate that it's the start piece for a move. */
    public void  markStart(    Square sq            ) {        counts[ sq.row ][ sq.column ] = -1             ; }

    /** Sets the count for a square to be a specific move number. */
    public void  setMove( Square sq, short move ) {
        if (counts[ sq.row ][ sq.column ] != 0) return;     // a promotion, don't overwrite it!
        counts[ sq.row ][ sq.column ] = (short) (move+1);   // must add one because 0 means "no moves" and not "move 0"
    }

    /** Clears all 64 counts. */
    public void clearCounts() {
        Square square = new Square();
        for (square.row=0; square.row<8; square.row++) {
            for (square.column=0; square.column<8; square.column++) {
                clearCount( square );
            }
        }
    }
    
    /** Computes the starting move index for each Piece that has legal moves. */
    public void computePieceMoves( GameMoves game ) {
        clearCounts();

        short moveCount = game.getMoveCount();
        for (short m=0; m < moveCount; m++) {
            incCount( game.getMove( m ).source );
        }
    }
        
    /** Count NO_PIECE on the square of the piece that's moving, and the move index on all valid target squares.
     *  Used when we are displaying a board with possible targets for a Piece move. */
    public void computePieceTargets( GameMoves game, short startMove ) {
        clearCounts();
        Move move       = game.getMove( startMove );
        short moveCount  = game.getMoveCount();

        markStart( move.source );

        setMove( move.target, startMove++ );
        
        while (startMove < moveCount) {
            move = game.getMove( startMove );
            if (getCount( move.source ) != -1) return;
            setMove( move.target, startMove++ );
        }
    }
    
    /** @return A String description of the Counts. */
    public String toString() {
        StringBuffer b = new StringBuffer(); 
        Square s = new Square();
        for (s.row=0; s.row<8; s.row++) {
            for (s.column=0; s.column<8; s.column++) {
                short c = getCount( s );
                String st = c+" ";
                if (st.length() < 3) b.append( " " );
                b.append( st );
            }
            b.append( '\n' );
        }
        return b.toString();
    }
}

