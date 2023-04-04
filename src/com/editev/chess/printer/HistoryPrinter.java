package com.editev.chess.printer;

import com.editev.chess.GameHTML;
import com.editev.chess.GameHistory;
import com.editev.chess.Move;


/** Prints the move history for a Game.
 *
 *  @see See the source <a href="HistoryPrinter.java">here</a>.
 */
public class HistoryPrinter extends Printer {
    public void printOneMove( GameHTML game, int moveIndex, StringBuffer moveString ) {
        Move move      = game.moveHistory.getAt( moveIndex );

        game.out.print( "<td class=\"hist\"><a href=\"");
        if (game.hasJavascript) {
            game.out.print( "&{doHist(" + moveIndex + ")};" );
        } else {
            if (moveIndex != 0) moveString.append( GameHistory.SEPARATOR );
            moveString.append( game.moveIndices.getAt( moveIndex ) );                
            game.out.print( moveString.toString() );
        }
        game.out.print( "\"><font color=\"#000000\">" );
        game.out.print( move.toString() );
        game.out.print( "</font></a>&nbsp;</td> ");
    }

    public static final int MOVES_PER_TABLE = 25;
    
    /** Prints the move history for a Game.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        int moves = game.getMoves();
        if (moves == 0) return;

        //game.out.startTag( "<table class=\"history\" align=\"right\">\n");
        game.out.startTag( "<table>\n");
        
        StringBuffer str = null;
        if (!game.hasJavascript) {
            str = new StringBuffer( game.baseURL );
            str.append( "?moves=" );
        }
        
        for (int moveIndex=0; moveIndex<moves; moveIndex += 2) {
            game.out.printIndent( "<tr>" );

            game.out.print( "<td class=\"hist\" align=\"right\">");
            int m = 1+moveIndex/2;
            if (game.textOnly) {
                if (m < 100) game.out.print( "&nbsp;" ); 
                if (m < 10 ) game.out.print( "&nbsp;" ); 
            }
            game.out.print( m+"." );
            game.out.print( "</td> ");

            printOneMove( game, moveIndex, str );
            
            if ((moveIndex+1) < moves) {
                printOneMove( game, moveIndex+1, str );

                game.out.print( "</tr>\n" );
                if ( (1+moveIndex/2) % MOVES_PER_TABLE == 0
                  &&  moveIndex+2                   < moves ) 
                {
                    // out of space, start a new table!
                    game.out.endTag( "</table>\n");
                    game.out.endTag( "</td>\n");
                    //game.out.startTag( "<td valign=\"top\">\n");
                    game.out.startTag( "<td class=\"hist\">\n");
                    game.out.startTag( "<table>\n");
                }
            } else {
                game.out.print( "</tr>\n" );
            }
        }
        game.out.endTag( "</table>\n" );        
    }
}