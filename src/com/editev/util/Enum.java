package com.editev.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import com.editev.util.Function;
import com.editev.util.Filter;

/** wrap an enumeration inside another enumeration. */
public class Enum implements Enumeration {
    /** the underlying enumeration being wrapped. */
	public final Enumeration enum_;
	public Enum( Enumeration enum_ ) { this.enum_ = enum_; }
	
    /** @return true if the underlying enum has more elements */
	public boolean hasMoreElements() { return enum_.hasMoreElements(); }

    /** @return the next element from the underlying enum */
	public Object  nextElement()     { return enum_.nextElement();     }
	
	/** print this Enum to System.out */
	public int print( ) { return print( this ); }
	
	/** count any Enumeration */
	public int count( ) { return count( this ); }

	/** get the element at a specific index, if it exists */
	public Object elementAt( int i) { return elementAt( this, i ); }

	/** print this Enum to System.out */
    public static int print( Enumeration enum_ ) {
        int i=0;
        while (enum_.hasMoreElements()) {
            System.out.print( enum_.nextElement()+" " );
            if (++i % 8 == 0) System.out.println();
        }
        if (i % 8 != 0) System.out.println();
        return i;
    }
	
	/** count elements in an Enumeration */
    public static int count( Enumeration enum_ ) {
        int i=0;
        while (enum_.hasMoreElements()) {
            enum_.nextElement();
            i++;
        }
        return i;
    }
	
	/** get the element at a specific index, if it exists */
    public static Object elementAt( Enumeration enum_, int i ) {
        if (i < 0 ) throw new NoSuchElementException();
        while (i-->0) enum_.nextElement();
        return enum_.nextElement();
    }
	
	/** map an Enumeration through a Function */
	public static class Mapped extends Enum {
		public final Function f;
		public Mapped( Function f, Enumeration enum_ ) { super( enum_ ); this.f = f;           }
		
		/** @return nextElement of the underlying enum passed through the Function */
		public Object nextElement()                   { return f.eval( enum_.nextElement() ); }
	}
	
	/** look ahead in some Enumeration or other process and store the next result in "next" */
	public abstract static class Lookahead implements Enumeration {
		/** the next acceptable element if any */
		protected Object next;
		
		/** @return true if there are any acceptable elements left in the underlying enum. */
	    public boolean hasMoreElements() { 
	        if (next == null) next();
            return next != null;
	    }
	    
		/** @return the next acceptable element in the underlying enum.
		 *  also move "next" forward to the next acceptable element after that,
		 *  if any.  */
		public Object nextElement() { 
		    if (!hasMoreElements()) throw new NoSuchElementException();
		    Object ret = next;
		    next = null;
		    return ret;
		}

		/** go to the next element */
		abstract protected void next( );		
	}

	/** filter out some elements of the underlying enum. */
	public static class Filtered extends Lookahead {
    public final Filter      filter;
    public final Enumeration enum_;
        
		public Filtered( Filter filter, Enumeration enum_  ) { 
		    this.filter = filter;
		    this.enum_ = enum_;
		}
		
		/** find the next acceptable element in the underlying enum, or set next to null if 
		 *  there is no such element. */
		protected void next() {
		    do {
	            next = enum_.hasMoreElements() ? enum_.nextElement() : null;
	        } while (next != null && !filter.accept( next ));      // no more elements!
		}		
	}
		
	/** an enumeration of enumerations, where the underlying enum's elements are always instances
	 *  of Enumeration and we run out each of the subenumerations before going to the next. 
	 *  To make life easier, a null is considered to be the same as an empty enum.
	 */
	public static class Compound extends Lookahead {
	    protected Enumeration entry;
	    public final Enumeration enum_; 
	    
	    public Compound( Enumeration enum_ ) { this.enum_ = enum_; }
        protected void next() {
            while (entry == null || !entry.hasMoreElements() ) {
                if (!enum_.hasMoreElements()) {
                    next = null;
                    return;
                }
                entry = (Enumeration) enum_.nextElement();
            }
            next = entry.nextElement();
        }

	}
	
	/** an empty Enumeration with no elements, ever.  */
	public static final Enumeration EMPTY = new Enumeration() {
        /** @return false */
    	public boolean hasMoreElements() { return false; }

        /** @throws NoSuchElementException always */
    	public Object  nextElement()     { throw new NoSuchElementException();    }
	};
    
    /** test the Enum classes by testing Compound */
	public static void main( String[] args ) {
	    Vector v0 = new Vector();   // start with an empty vector

	    Vector v1 = new Vector();	// vector of length 2
	    v1.addElement( new Integer( 1 ) );
	    v1.addElement( new Integer( 2 ) );
	    
	    Vector v2 = new Vector();   // three empty vectors.
	    Vector v25 = new Vector();
	    Vector v26 = new Vector();
	    
	    Vector v3 = new Vector();
	    v3.addElement( new Integer( 3 ) );  // two element vector
	    v3.addElement( new Integer( 4 ) );
	    
	    Vector v4 = new Vector();           // one element vector
	    v4.addElement( new Integer( 5 ) );

	    Vector v5 = new Vector();           // zero element vector.
	    
	    Vector v = new Vector();
	    v.addElement( v0 .elements() );
	    v.addElement( v1 .elements() );
	    v.addElement( v2 .elements() );
	    v.addElement( v25.elements() );
	    v.addElement( v26.elements() );
	    v.addElement( v3 .elements() );
	    v.addElement( v4 .elements() );
	    v.addElement( v5 .elements() );
	    
	    print( new Compound( v.elements() ) );
	}
}
