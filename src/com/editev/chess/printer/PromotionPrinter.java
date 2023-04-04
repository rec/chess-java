package com.editev.chess.printer;

import com.editev.chess.GameHTML;


/** Prints just the promotion area.
 *
 *  @see See the source <a href="PromotionPrinter.java">here</a>.
 */
public class PromotionPrinter extends Printer {
    /** Prints just the promotion area.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        // target printing the form and the selection information. 
        game.out.println();
        
        game.out.startTag( "<table>\n" );

        for ( byte b=0, p = Black.QUEEN; p <= Black.KNIGHT; b++, p++) {      // for each possible promotion.
            if (0 == b%2) {
                game.out.startTag( "<tr>\n" );
                game.out.printIndent();
            }
            game.out.print( "<td> " );
            if (game.hasJavascript) {
                game.out.print( "<a onclick='goMove( \""
                                + (game.moveCount+game.target+b) 
                                + "\" );'>" );                                 // finish the <A> tag
            } else {
                game.out.print( "<a href=\"" );
                game.out.print( game.moveURL + (game.moveCount+game.target+b) ); // with that value of "move"
                game.out.print( "\">" );                                 // finish the <A> tag
            }
            game.out.print( game.getPieceHTML( p ) );           // description of the piece.
            game.out.print( "</a>" );
            game.out.print( "</td>" );
          
            if (1 == b%2) {
                game.out.endTag( "<tr>\n" );
            }
        }
                
        game.out.endTag(   "</table>\n" );
        game.out.printIndent();
    }
}