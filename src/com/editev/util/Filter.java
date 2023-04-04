package com.editev.util;

public interface Filter {
    /** does the filter accept the object x?
     * @return false if we need to throw out the Object x. */
	boolean accept( Object x );

    /** store another filter and act like it. */
    class Store implements Filter {
        Filter filter;
        public Store(          Filter filter ) { this.filter = filter; }        
    	public boolean accept( Object x      ) { return filter.accept( x ); }
    }

    /** a filter that's the inverse of a filter */
    class Not extends Store {
        public Not(            Filter filter ) { super( filter ); }
    	public boolean accept( Object x      ) { return !filter.accept( x ); }
    }
    
    /** filter out all nulls. */
    class NonNull implements Filter {
        public boolean accept( Object x ) { return x != null; }
    };    
    
    public final Filter NON_NULL = new NonNull();
}

