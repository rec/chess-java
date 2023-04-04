package com.editev.chess.printer;

import com.editev.util.Function;
import com.editev.chess.Chess;
import com.editev.chess.GameHTML;

/** A Printer is a Function that prints to a GameHTML -- we put together Printers to display
 *  an entire game as HTML.
 *
 *  @see See the source <a href="Printer.java">here</a>.
 */
abstract public class Printer extends Chess implements Function {

    /** Print to an instance of GameHTML. 
     *  @param gameHTML The GameHTML, which can also be 
     */
    abstract public void print( GameHTML gameHTML );

    /** Print to a GameHTML and return the same object. */
    public Object eval( Object gameHTML ) {
        print( (GameHTML) gameHTML );
        return gameHTML;        
    }
}

