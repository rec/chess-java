package com.editev.util;

import java.util.Enumeration;

/** Cache an enumeration so it looks like an indexed list, except that later terms aren't
 *  evaluated unless they are requested (excessively clever optimization).
 *
 *  @see See the source <a href="CachedEnumeration.java">here</a>.
 */
public class CachedEnumeration extends Lists.Objects {

    /** The enumeration to cache.  */
    public final Enumeration enum_;

    public    CachedEnumeration( Enumeration enum_               )    { this.enum_ = enum_;                     }
    public    CachedEnumeration( Enumeration enum_, int capacity )    { super( capacity ); this.enum_ = enum_;  }

    /** Run through the entire enumeration and report the total number of elements.
     *
     *  @return total number of elements in the enumeration.
     */
    public int getLength() { 
        getUntil( Integer.MAX_VALUE ); 
        return super.getLength(); 
    }
    
    /** @return The object at that index in the Enumeration if any.  
     *  Won't evaluate any further than is necessary.
     *
     *  @param move the index of the Object in the enumeration
     *
     *  @throw IndexOutOfBoundsException if there is no Object in the enumeration with this index. */
    public Object getAt( int index ) { 
        getUntil( index );                       // get all moves to this point.
        if (index >= getLength() || index < 0) { // no such move.
            throw new IndexOutOfBoundsException( "accessed move indexed "+index+ " out of "+getLength() ); 
        }
        return super.getAt( index );             // get the move from the List.
    }

    /** Computes all Objects out of the Enumeration up to a certain index. 
     *
     *  @param moveNeeded the index of the last legal move we need (or Short.MAX_SHORT for all!)
     */
    void getUntil( int index ) {
        while ( index >= getLength() && enum_.hasMoreElements() ) {
            append( enum_.nextElement() );
        }
    }
}

  
