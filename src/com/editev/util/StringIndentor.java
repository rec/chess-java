package com.editev.util;


/** How many space to indent HTML structures? */
public class StringIndentor {
    /** Flushes the PrintStream output. */    
    public final byte indentation;
    
    /** Default indentation. */
    public static final byte INDENTATION = 1;
    
    /** Wrap a stored StringBuffer. */
    public StringIndentor( StringBuffer out, byte indentation ) { this.out = out; this.indentation = indentation; }
    public StringIndentor( StringBuffer out                   ) { this(  out, INDENTATION  ); }

    /** The stored StringBuffer. */
    public final StringBuffer out;
    
    /** Print a char. */    
    public void print( char ch              ) { out.append( ch );                      }
    
    /** Print a char, many times. 
     *
     *  @param ch the character to print.
     *  @param count how many times to print the character.
     */    
    public void print( char ch, int count       ) { while (count-- > 0) out.append( ch );  }

    /** Print a string. */    
    public void print(   String str             ) { out.append( str );                     }
    
    /** Print exactly \n regardless of what system we are on. */    
    public void println(                        ) { out.append( '\n' );                    }
    
    /** Print a string and a \n on any system. */    
    public void println( String str             ) { out.append( str ).append( '\n' );  }
    
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
