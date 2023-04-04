package com.editev.chess.servlet;

import com.editev.chess.printer.PagePrinter;
import com.editev.chess.printer.Printer;

/** This servlet will display any legal game of chess. <BR> <BR>
 *  
 *  the following HTML parameters are used: <BR> <BR>
 *  
 *    moves   has the list of moves so far separated by commas. <BR>
 *    target   contains the first half of the move if any. <BR>
 *    refresh has a refresh parameter that periodically refreshes the game with a random move. <BR> <BR>
 *
 *  @see   The source is <a href="ChessServlet.java">here</a> <br>
 *         and you can see a demo <a href="http://editev.com/servlet/chess">here</a>. 
 */
public class ChessServlet { // extends HttpServlet {
    public static Printer pagePrinter = new PagePrinter();

//    /** Responds to http GET requests as a good HttpServlet should. */
//    public void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
//    	res.setContentType("text/html");        // html description of the board.
//    	System.out.println(  );
//    	
//    	/*
//    	PrintStream out = new PrintStream(      // buffer the output stream for better performance
//    	    new BufferedOutputStream(           // since most of the outputs are quite short.
//    	        res.getOutputStream() 
//    	    ) 
//    	);
//        */
//        boolean isLynx        = req.getHeader( "User-Agent" ).startsWith( "Lynx" );
//        boolean hasJavascript = false;//!isLynx;
//        boolean hasStyles     = !isLynx;
//        
//        int refresh = com.editev.chess.printer.RefreshPrinter.NONE;
//        String refStr = req.getParameter( "refresh" );
//        
//        if (refStr != null) {
//       	    refresh = 10;                                   // refresh period in seconds.
//    	    try { refresh = Integer.parseInt( refStr ); } catch (Exception e) {}
//        }
//
//        StringBuffer result = new StringBuffer();
//        
//        GameHTML game = new GameHTML(                   // create a new game
//            "chess",
//            //"http://tom.ax.to/servlet/chess",
//            req.getParameter( "moves" ),                // the list of moves already made
//            req.getParameter( "target" ),                // and a start move if this is the second half.
//            isLynx,
//            hasJavascript,
//            hasStyles,
//            refresh,
//            result                                  // to the following output.
//        );
//        
//        pagePrinter.print( game );                  // print the whole HTML page.
//        
//        res.getOutputStream().print( result.toString() );        
//    	res.getOutputStream().close();                                                    // and flush the output, we're finished.
//    }
//
//    /** Responds to "post" requests in the same way as "get" requests. */
//    public void doPost( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException  { doGet( req, res ); }

    /** Describes this servlet with a String. */
    public String getServletInfo()                                                                              { return "a few random chess games."; }
}
