package com.editev.chess.printer;

import com.editev.chess.GameHTML;

/** Prints the status of Black or White.
 *
 *  @see See the source <a href="StatusPrinter.java">here</a>.
 */
public class StatusPrinter extends Printer {
    public final boolean isWhite;
    public Printer capturedPrinter;
    
    public StatusPrinter( boolean isWhite ) { 
        capturedPrinter = new CapturedPrinter( !isWhite );
        this.isWhite = isWhite; 
    }
    
    /** Prints the status of Black or White.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        capturedPrinter.print( game );
    }
}