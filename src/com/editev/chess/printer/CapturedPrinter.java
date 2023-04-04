package com.editev.chess.printer;

import com.editev.chess.GameHTML;
import com.editev.util.Lists;

/** Prints the array of captured pieces. 
 *
 *  @see See the source <a href="CapturedPrinter.java">here</a>.
 */
public class CapturedPrinter extends Printer {
    /** A template for CSS styles for captured pieces.. */
    // private static String STYLE_TEMPLATE = "; font-family: sans-serif }\n";

    /** The name of a style for a captured piece. 
     *
     * @return name of the style representing that piece and square color. 
     */
    public static String styleName( boolean isWhitePiece ) { return  isWhitePiece ? "white" : "black"; }
    
    boolean isWhite;
    public CapturedPrinter( boolean isWhite ) { this.isWhite = isWhite; }
    
    /** Prints the array of captured pieces.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        Lists.Bytes captured = game.getCaptured( isWhite );
        int length = captured.getLength();
        if (length == 0) return;
        int root   = (int) Math.floor( Math.sqrt( length ) );
        if (length == 2 || length > root*root+root) root++;    // make the table wider for better results.
        
        game.out.startTag( "<table>\n" );

        for (int i=0; i<length; i++) {
            if (i%root == 0) game.out.printIndent( "<tr> " );
            game.out.print( "<td class=\"" );                  // target the table entry
            game.out.print( styleName( isWhite ) ); // choose a style based on the color of the piece
            game.out.print( "\">" );                                 // end the target of the table entry
            
            game.out.print( game.getPieceHTML( captured.getAt( i ) ) );

            game.out.print( "</td> " );                             // finally, the end of the table entry.
            if ((i+1)%root == 0 || i==(length-1)) game.out.print( "</tr>\n" );
        }

        game.out.endTag( "</table>\n" );
    }
}