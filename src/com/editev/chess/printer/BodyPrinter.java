package com.editev.chess.printer;

import com.editev.chess.GameHTML;
import com.editev.chess.Chess;


/** Prints the HTML body for a chess board. 
 *
 *  @see See the source <a href="BodyPrinter.java">here</a>.
 */
public class BodyPrinter extends Printer {
    /** Prints a whole chess game. */
    public Printer gamePrinter = new GamePrinter();

    /** Prints the HTML body for a chess game.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        // start the body of the HTML document.
        // set all the links depending on color (quick hack here).  	
    	if (game.isWhiteMove() != (game.target != Chess.NO_MOVE)) game.out.print("<body link=\"#FFFFFF\" vlink=\"#FFFFFF\" alink=\"#FF0000\" bgcolor=\"#999999\">\n");
    	else                                                      game.out.print("<body link=\"#000000\" vlink=\"#000000\" alink=\"#000000\" bgcolor=\"#999999\">\n");

    	gamePrinter.print( game );
    	game.out.print("</body>\n");                                // finish the body and the file
    }
}