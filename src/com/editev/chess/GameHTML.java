package com.editev.chess;

import com.editev.util.StringIndentor;

/** A GameHTML is a GameHistory that can also be displayed in HTML format. 
 *
 *  @see See the source <a href="GameHTML.java">here</a>.
 */
public class GameHTML extends GameHistory {

    /** The base URL for this servlet. */
    public final String baseURL;

    /** The list of previous moves, if any. */
    public final String moves;

    /** The base URL for moves. */
    public final String moveURL;

    /** The base URL for this servlet's targeted moves. */
    public final String targetURL;

    /** If this is the second part of a move, what is the first target square? */
    public final short target;
    
    /** An Indentor wraps a PrintStream in some useful functions for outputting HTML. */
    public final StringIndentor out;
    
    /** Is this a text-only browser? */
    public final boolean textOnly;
    
    /** Does this browser support Javascript? */
    public final boolean hasJavascript;
    
    /** Does this browser support style sheets? */
    public final boolean hasStyles;
    
    /** How often do we refresh (with a random move)? */
    public final int refresh;
    
    /** The actual game that we're representing. */
    //public final GameHistory game;
    
    /** Create a GameHTML representing a chess position and a URL. 
     *
     * @param baseURL       the base URL for this servlet
     * @param moves         a slash-separated string of moves.
     * @param startString   the "target" move, if this is the second half of a Piece move, or null otherwise.
     * @param textOnly      is this a text-only browser (current, just Lynx)?
     * @param hasJavascript does this browser support Javascript?
     * @param hasStyles     does this browser support CSS style sheets?
     * @param refresh       How often do we refresh (with a random move)?
     * @param out           the PrintStream for this HTTP socket connection.
     *
     */
    public GameHTML( 
        String       baseURL, 
        String       moves, 
        String       targetString, 
        boolean      textOnly,
        boolean      hasJavascript,
        boolean      hasStyles,
        int          refresh,
        //GameHistory  game,
        StringBuffer out
    ) 
    {
        this.baseURL   = baseURL;
        
        short target   = NO_MOVE;
        try { target   = Short.parseShort( targetString ); } catch (Exception e) {}
        this. target   = target;
        
        boolean empty  = (moves == null);
        if (empty) {
            this.moves = "";
        } else {
            this.moves = moves;
            applyMoves( moves );
        }
        
        this.moveURL   = baseURL +  (empty ? "?moves="  : ("?moves=" + moves + SEPARATOR  ) );
        this.targetURL = baseURL +  (empty ? "?target=" : ("?moves=" + moves + "&target=" ) );
        
        this.textOnly       = textOnly;
        this.hasJavascript  = hasJavascript;
        this.hasStyles      = hasStyles;
        this.refresh        = refresh;
        this.out            = new StringIndentor( out );
        //this.game           = game;
    }

    /** HTML representing black and white pieces only. */
    public static final String[] PIECE_HTML = {
        "&nbsp;", "K", "Q", "R", "B", "N", "P",     "&nbsp;", "K", "Q", "R", "B", "N", "P"
    };
    
    /** HTML representing black and white pieces only. */
    public static final String[] PIECE_TEXT = {
        "&nbsp;", "k", "q", "r", "b", "n", "p",     "&nbsp;", "K", "Q", "R", "B", "N", "P"
    };
    
    /** Get the HTML for this piece! */
    public String getPieceHTML( byte piece  ) { return textOnly      ? PIECE_TEXT[ piece ] : PIECE_HTML[ piece ]; }
    
    /** Counts the number of next moves for each square on the board. */
    public final Counts counts = new Counts();
    
    /** Counts the moves that we've already displayed. */
    public short moveCount;
    
    /** An enumerator for squares. */
    public Square square = new Square();    
}
