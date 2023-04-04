package com.editev.util;

/** fast, unsynchronized grow-only vector */
abstract public class Lists implements Cloneable {
	/** list null is exactly the same as a list of length 0 -- we often do this for efficiency. */ 
	protected Object list;

    /** create a new list of a given size */
	abstract protected Object newList( int capacity );
	
	/** get the number of elements in the list. */
	public int getLength()              { return  length;	}

	/** reset the number of elements in the list. */
	public void setLength( int length ) { setCapacity( length ); this.length = length; }

    /** get the possible capacity of the current list. */
	abstract protected int  getCapacity();
	
	protected int length = 0;
	public ResizeStrategy resize = ResizeStrategy.DOUBLING;
	
	/** the public method is a front end for the abstract makeList 
	     which actually does the work. */
	public void setCapacity( int space ) {
	    if (space <= length) return;
	    int newLength = resize.computeResize( length, space );
	    
        if (newLength != length) {
            list = (newLength == 0) ? null : makeList( newLength );
        }
	}
	
	public Object makeList( int newLen ) {
        Object newList = newList( newLen );
	    if (length != 0) listCopy( list, newList, length );
	    return newList;
	}

	public Object makeList( )         { return makeList( length ); }
	protected Object checkList( int i ) {  
	    if (i >= length) throw new ArrayIndexOutOfBoundsException( "length="+length+" i="+i );	    
	    return list; 	
	}

	public static void listCopy( Object oldList, Object newList, int length ) {
	    System.arraycopy( oldList, 0, newList, 0, length );
	}
	
    public Object clone() { return cloneLists(); }
    
    public Lists cloneLists() { 
        Lists that;
        try                                     { that = (Lists) super.clone();   } 
        catch (CloneNotSupportedException e)    { throw new ExceptionWrapper( e ); }    // can't happen!
        that.list = this.newList( getCapacity() );
        listCopy( list, that.list, length );
        return that;
    }        


    /** Clears the list */
	public void clear( )    { setLength( 0 ); }
	
	public Lists() {}
	public Lists( int capacity ) { setCapacity( capacity ); }

	public static class Chars extends Lists {
    	protected Object newList( int capacity ) { return new char[ capacity ];   }
    	public char[]  getList()                 { return (char[]) list; }
    	protected int  getCapacity()             { return getList().length; }
    	public    char getAt( int i )            { return ((char[]) Chars.this.checkList( i ) )[ i ]; }
    	public    void setAt( int i, char x )    { ((char[]) Chars.this.checkList( i ) )[ i ] = x; }
    	public    void append( char x )          { Chars.this.setLength( length+1); setAt( length-1, x ); }
        public    Chars()                        { }
        public    Chars( int capacity )          { super( capacity ); }
	}	
	public static class Ints extends Lists {
    	protected Object newList( int capacity ) { return new int[ capacity ];}
    	public int[]  getList()                  { return (int[]) list; }
    	protected int  getCapacity()             { return getList().length; }
    	public    int  getAt( int i )            { return ((int[]) Ints.this.checkList( i ) )[ i ]; }
    	public    void setAt( int i, int x )     { ((int[]) Ints.this.checkList( i ) )[ i ] = x; }
    	public    void append( int  x )          { Ints.this.setLength( length+1); setAt( length-1, x ); }
        public    Ints()                         { }
        public    Ints( int capacity )           { super( capacity ); }
	}	
	public static class Shorts extends Lists {
    	protected Object newList( int capacity ) { return new short[ capacity ];}
    	public short[]  getList()                { return (short[]) list; }
    	protected int  getCapacity()             { return getList().length; }
    	public    short getAt( int i )           { return ((short[]) Shorts.this.checkList( i ) )[ i ]; }
    	public    void setAt( int i, short x )   { ((short[]) Shorts.this.checkList( i ) )[ i ] = x; }
    	public    void append( short x )         { Shorts.this.setLength( length+1); setAt( length-1, x ); }
        public    Shorts()                       { }
        public    Shorts( int capacity )         { super( capacity ); }
	}	
	public static class Bytes extends Lists {
    	protected Object newList( int capacity ) { return new byte[ capacity ];}
    	public byte[]  getList()                 { return (byte[]) list; }
    	protected int  getCapacity()             { return getList().length; }
    	public    byte getAt( int i )            { return ((byte[]) Bytes.this.checkList( i ) )[ i ]; }
    	public    void setAt( int i, byte x )    { ((byte[]) Bytes.this.checkList( i ) )[ i ] = x; }
    	public    void append( byte x )          { Bytes.this.setLength( length+1 ); setAt( length-1, x ); }
        public    Bytes()                        { }
        public    Bytes( int capacity )          { super( capacity ); }
	}	
	public static class Objects extends Lists {
    	protected Object newList( int capacity ) { return new Object[ capacity ];}
    	public Object[]  getList()               { return (Object[]) list; }
    	protected int  getCapacity()             { return getList().length; }
    	public    Object getAt( int i )          { return ((Object[]) Objects.this.checkList( i ) )[ i ]; }
    	public    void setAt( int i, Object x )  { ((Object[]) Objects.this.checkList( i ) )[ i ] = x; }
    	public    void append( Object x )        { Objects.this.setLength( length+1 ); setAt( length-1, x ); }
        public    Objects()                      { }
        public    Objects( int capacity )        { super( capacity ); }
	}	
}


