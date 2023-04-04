package com.editev.chess.printer;

import com.editev.chess.GameHTML;
import com.editev.chess.Move;

/** Prints just a single piece. 
 *
 *  @see See the source <a href="PiecePrinter.java">here</a>.
 */
public class PiecePrinter extends Printer {

    /** Print any promotions. */
    public Printer promotionPrinter = new PromotionPrinter();

    /** Prints just one piece to the PrintStream.
     *
     *  @param game the GameHTML with the board status and PrintStream for this board.
     */
    public void print( GameHTML game ) {
        byte piece  = game.getPieceIndex( game.square );            // get the piece being printed.
        short count = (short) game.counts.getCount( game.square );  // and the number of moves to/from that square.
        
        if (count == NO_MOVE) {                                     // this square has the piece that's moving.
        
            game.out.print( "<font color=\"#FF0000\">" );           // change the color
            game.out.print( game.getPieceHTML( piece ) );           // print the piece
            game.out.print( "</font>" );
            return;
            
        } else if (count == 0      ) {                              // no action here at all.
        
            game.out.print( game.getPieceHTML( piece ) );           // just print the piece
            return;
            
        } else if (game.target != NO_MOVE) {                        // we are halfway through a move.
        
            Move move = game.getMove( game.target );                // get the move we started,
            byte movedPiece = game.getPieceIndex( move.source );    // and the piece being moved.
                    
            if ( (   movedPiece  == White.PAWN    
                  || movedPiece  == Black.PAWN)                     // it's a pawn
                && 
                 (   move.target.row == 0
                  || move.target.row == 7)                          // and it's a promotion! 
               )
            {
                promotionPrinter.print( game );    
                game.moveCount += 4;                                // a promotion consumes 4 moves.
                return;
            }
        }

        if (game.hasJavascript) {
            game.out.print( "<a href=\"" );
            if      (game.target != NO_MOVE) game.out.print( "&{move+'"   + (count-1)      + "'};" ); // we are completing a move with two parts.
            else if (count  >  1           ) game.out.print( "&{target+'" + game.moveCount + "'};" ); // first half of a move with multiple completions
            else                             game.out.print( "&{move+'"   + game.moveCount + "'};" ); // this move is unambiguously determined by the target square.
            game.out.print( "\">" );                                 // finish the <A> tag
            
        } else {
            game.out.print( "<a href=\"" );                             // otherwise, it has to be a link of some type.

            if      (game.target != NO_MOVE) game.out.print( game.moveURL   + (count-1)      ); // we are completing a move with two parts.
            else if (count  >  1           ) game.out.print( game.targetURL + game.moveCount ); // first half of a move with multiple completions
            else                             game.out.print( game.moveURL   + game.moveCount ); // this move is unambiguously determined by the target square.
            
            game.out.print( "\">" );                                    // finish the <A> tag
        }
        if (game.target != NO_MOVE && piece == NO_PIECE) game.out.print( game.textOnly ? "-" : "&nbsp;");
        else                                             game.out.print( game.getPieceHTML( piece ) ); // the piece itself
        game.out.print( "</a>" );                                   // and the closing tag for <a>
        
        game.moveCount += count;                                    // we have consumed 'count' moves this time.
    }
}