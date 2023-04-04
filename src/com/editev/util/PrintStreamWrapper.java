package com.editev.util;

import java.io.PrintStream;

/** Store a PrintStream and perform some useful functions on it. */
public class PrintStreamWrapper {

    /** The stored PrintStream. */
    public final PrintStream out;
    
    /** Wrap a stored PrintStream. */
    public PrintStreamWrapper( PrintStream out  ) { this.out = out;                       }

    /** Print a char. */    
    public void print( char ch                  ) { out.write( ch );                      }
    
    /** Print a char, many times. 
     *
     *  @param ch the character to print.
     *  @param count how many times to print the character.
     */    
    public void print( char ch, int count       ) { while (count-- > 0) out.write( ch );  }

    /** Print a string. */    
    public void print(   String str             ) { out.print( str );                     }
    
    /** Print exactly \n regardless of what system we are on. */    
    public void println(                        ) { out.write( '\n' );                    }
    
    /** Print a string and a \n on any system. */    
    public void println( String str             ) { out.print( str ); out.write( '\n' );  }
    
    /** Flushes the PrintStream output. */    
    public void flush(                          ) { out.flush();  }
}
