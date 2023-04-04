package com.editev.chess.printer;

import com.editev.chess.GameHTML;

/** Prints the entire board. 
 *
 *  @see See the source <a href="BoardPrinter.java">here</a>.
 */
public class BoardPrinter extends Printer {
    /** Prints just a single square. */
    public static Printer squarePrinter = new SquarePrinter();
    
    /** Prints the entire board.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        if (game.target == NO_MOVE) game.counts.computePieceMoves(   game        );    // compute all starting moves
        else                        game.counts.computePieceTargets( game, game.target );    // compute all final moves.

        game.out.startTag( "<table cellpadding=\"4\" cellspacing=\"4\">\n"  );    // target the table.
        
        for (game.moveCount = 0, game.square.row=0; game.square.row<8; game.square.row++) {     // for each chessboard row.
            game.out.startTag( "<tr>\n" );                                       // target the row.

            for (game.square.column=0; game.square.column<8; game.square.column++) {       // for each column in that row.
                squarePrinter.print( game  );                                             // print the square.
            }
            game.out.endTag( "</tr>\n" );                                        // end the row.
        }

        game.out.endTag( "</table>\n" );                                         // end the table.
    }
}