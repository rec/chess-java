package com.editev.chess.printer;

import com.editev.chess.GameHTML;

/** Prints an entire chess game!
 *
 *  @see See the source <a href="GamePrinter.java">here</a>.
 */
public class GamePrinter extends Printer {
    /** Print the status of Black. */
    public Printer blackStatus    = new StatusPrinter( false );

    /** Print the status of White. */
    public Printer whiteStatus    = new StatusPrinter( true );

    /** Print the entire board. */
    public Printer boardPrinter   = new BoardPrinter();

    /** Print the move history. */
    public Printer historyPrinter = new HistoryPrinter();
    
    /** Print a separating line for text only. */
    public void printSeparator( GameHTML game ) {
        if (game.textOnly) game.out.printIndent( "<tr><td>----------------</td></tr>\n" );
    }
    
    /** Print the whole game area including the board, the captured pieces and the game history.
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        game.out.startTag( "<table>\n" );
          game.out.startTag( "<tr>\n" );
            game.out.startTag( "<td>\n" );

              game.out.startTag( "<table>\n" );
              
                game.out.startTag( "<tr>\n" );
                  game.out.startTag( "<td>\n" );
                    blackStatus.print( game );
                  game.out.endTag( "</td>\n" );
                game.out.endTag( "</tr>\n" );
                
                printSeparator( game );
                
                game.out.startTag( "<tr>\n" );
                  game.out.startTag( "<td>\n" );
                    boardPrinter.print( game );
                  game.out.endTag( "</td>\n" );
                game.out.endTag( "</tr>\n" );

                printSeparator( game );

                game.out.startTag( "<tr>\n" );
                  game.out.startTag( "<td>\n" );
                    whiteStatus.print( game );
                  game.out.endTag( "</td>\n" );
                game.out.endTag( "</tr>\n" );
                            
                printSeparator( game );
                
              game.out.endTag( "</table>\n" );
              
            game.out.endTag( "</td>\n" );
            
            game.out.startTag( "<td valign=\"top\">\n" );
              historyPrinter.print( game );
            game.out.endTag( "</td>\n" );
            
          game.out.endTag( "</tr>\n" );
        game.out.endTag( "</table>\n" );
    }
}