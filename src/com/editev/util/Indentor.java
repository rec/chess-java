package com.editev.util;

import java.io.PrintStream;

public class Indentor extends PrintStreamWrapper {
    /** How many space to indent HTML structures? */
    public final byte indentation;
    
    /** Default indentation. */
    public static final byte INDENTATION = 1;
    
    public Indentor( PrintStream out, byte indentation ) { super( out ); this.indentation = indentation; }
    public Indentor( PrintStream out                   ) { this(  out, INDENTATION  ); }

    /** Tracks the HTML indenting for each element. */
    private byte indent;

    /** Indents the following code one level. */
    void indent() { indent += indentation; }
    
    /** Unindent the following code one level. */
    void unindent() { 
        if (indent < indentation) {
            throw new IndexOutOfBoundsException( "Can't outdent "+ indent + " by "+ indentation +" positions.");
        }
        indent -= indentation; 
    }

    /** Print the indent at the start of a new line. */    
    public void printIndent(            ) { print( ' ', indent ); }
    
    /** Print the indent at the start of a new line. */    
    public void printIndent( String str ) { printIndent(); print( str );                    }
    
    /** print a starting line and indent it. */
    public void startTag(    String tag ) { printIndent( tag ); indent();                   }
    
    /** unindent and print an ending line. */
    public void endTag(      String tag ) { unindent(); printIndent( tag );                 }
}
