package com.editev.chess.printer;

import com.editev.chess.GameHTML;


/** Print the refresh header, if any. 
 *
 *  @see See the source <a href="RefreshPrinter.java">here</a>.
 */
public class RefreshPrinter extends Printer {

    /** A refresh of NONE means don't refresh at all. */
    public static final int NONE = -1;
    
    /** Prints just the refresh header.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
    	int count = game.getMoveCount();                // number of next moves
        if (game.refresh == NONE || count == 0) return; // no refresh or next moves
    	
	    int move = (int) (Math.random() * count);       // pick a random, legal move.
	    
	    game.out.print( 
	        "  <meta http-equiv=\"refresh\" content=\"" // refresh header
	      + game.refresh                                // period in seconds
	      + ";URL=" + game.moveURL + move               // URL to refresh to
	      + "&refresh=" + game.refresh                  // regenerate refresh tag
	      + "\">\n" );                                    // end meta tag.
    }
}