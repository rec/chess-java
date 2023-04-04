package com.editev.chess.printer;import com.editev.chess.GameHTML;/** Prints an HTML header for a chess game. * *  @see See the source <a href="HeaderPrinter.java">here</a>. */public class HeaderPrinter extends Printer {    /** Print the Javascript definitions, if any. */    public Printer javascript = new JavascriptPrinter();    /** Print the style sheets, if any. */    public Printer styleSheet = new StyleSheetPrinter();    /** Print the refresh header, if any. */    public Printer refresh    = new RefreshPrinter();    /** Prints just the HTML header.     *  @param game the GameHTML with the board status and PrintStream for this board.     */    public void print( GameHTML game ) {    	game.out.print(   "\n<head>\n<title>The complete game of chess.</title>\n");  // title the page    	    	refresh   .print( game );    	javascript.print( game );    	styleSheet.print( game );    	    	game.out.print( "<head>\n");    }}