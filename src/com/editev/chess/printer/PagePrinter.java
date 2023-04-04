package com.editev.chess.printer;

import com.editev.chess.GameHTML;


/** Prints an HTML page representing a chess game.
 *
 *  @see See the source <a href="PagePrinter.java">here</a>.
 */
public class PagePrinter extends Printer {
    public static final String  DOCTYPE_STRING = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">"; 
    public static       Printer headerPrinter  = new HeaderPrinter();
    public static       Printer bodyPrinter    = new BodyPrinter();
    
    /** Print an entire HTML page representing a Game.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        game.out.print(      DOCTYPE_STRING ); 
        headerPrinter.print( game );
        bodyPrinter.print(   game );
    }
}