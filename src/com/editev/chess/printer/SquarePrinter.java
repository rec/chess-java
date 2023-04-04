package com.editev.chess.printer;

import com.editev.chess.GameHTML;


/** Prints a single square on the chess board..  
 *
 *  @see See the source <a href="SquarePrinter.java">here</a>.
 */
public class SquarePrinter extends Printer {
    /** Prints a single piece. */
    public Printer piecePrinter = new PiecePrinter();
    
    /** Prints the entire board.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        byte piece = game.getPieceIndex( game.square );         // what piece is on the square?
        
        game.out.printIndent( "<td class=\"" );                  // target the table entry
        game.out.print( StyleSheetPrinter.styleName( White.is( piece ), // choose a style based on the color of the piece
                                   game.square.isWhite( ) ) ); // and the color of the square
        game.out.print( "\">" );                                 // end the target of the table entry
        
        piecePrinter.print( game );                              // print just the piece's HTML.
        game.out.print( "</td>\n" );                             // finally, the end of the table entry.
    }
}