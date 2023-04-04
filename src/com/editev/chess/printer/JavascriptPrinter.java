package com.editev.chess.printer;

import com.editev.chess.GameHTML;

/** Prints any Javascript definitions needed for the chess board. 
 *
 *  @see See the source <a href="JavascriptPrinter.java">here</a>.
 */
public class JavascriptPrinter extends Printer {

    /** Start of the Javascript headers. */
    private static final String HEADER_START =
        "<script language=\"JavaScript\">\n"
      + "<!--\n";
      
    /** Javascript function definitions. */
    private static final String HEADER_FUNCTIONS =
        " function doHist( index ) {\n"
      + "  var m = moves.split('/');\n"
      + "  m.length = index+1;\n"
      + "  return (base + m.join('/'));\n" 
      + " }\n\n";
      
    /** End of the Javascript headers. */
    private static final String HEADER_END =
        "//-->\n"
      + "</script>\n"
      ;

    /** Prints any Javascript definitions needed for the chess board.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        if (!game.hasJavascript) return;

        game.out.print( HEADER_START );
        
        game.out.print( " base    = '"+ game.baseURL   +"?moves=';\n" );
        game.out.print( " moves   = '"+ game.moves     +"';\n"    );
        if (game.moves.length() == 0) {
            game.out.print( " move    = base + moves     ;\n"    );
        } else {
            game.out.print( " move    = base + moves +'/';\n"    );
        }
        game.out.print( " target  = base + moves + '&target=';\n\n"   );
        
        game.out.print( HEADER_FUNCTIONS );
        game.out.print( HEADER_END );
    }
}