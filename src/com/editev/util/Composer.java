package com.editev.util;

public class Composer extends Lists implements Function, Chainable {
	protected Object   newList(     int capacity      ) { return new Function[ capacity ];}
	protected int      getCapacity(                   ) { return ((Function[]) list).length; }
	public    Function getAt(       int i             ) { return ((Function[]) checkList( i ) )[ i ]; }
	public    void     setAt(       int i, Function x ) { ((Function[]) checkList( i ) )[ i ] = x; }
	
	public Object eval( Object x ) {
	    for (int i=0; i<length; i++) x = getAt( i ).eval( x );
	    return x;
	}
	
	public void add( Function f ) {
	    int length = getLength();
	    setLength( length + 1 );
	    setAt( length, f );
	}
	
	public Chainable chain( Object f ) { add( (Function) f ); return this; }
	
	// large number of convenience initializers!
	
	public Composer() {}
	public Composer( Function f1 ) { 
	                 add( f1 );       
	}
	public Composer( Function f1, Function f2 ) { 
	                 add( f1 );   add( f2 );    
	}
	public Composer( Function f1, Function f2, Function f3 ) { 
	                 add( f1 );   add( f2 );   add( f3 );  
	}
	public Composer( Function f1, Function f2, Function f3, Function f4 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );              
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );           
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );        
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );      
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7, Function f8 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );   add( f8 );   
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7, Function f8, Function f9 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );   add( f8 );   add( f9 );   
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7, Function f8, Function f9, Function f10 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );   add( f8 );   add( f9 );   add( f10 ); 
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7, Function f8, Function f9, Function f10, Function f11 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );   add( f8 );   add( f9 );   add( f10 );   add( f11 ); 
	}
	public Composer( Function f1, Function f2, Function f3, Function f4, Function f5, Function f6, Function f7, Function f8, Function f9, Function f10, Function f11, Function f12 ) { 
	                 add( f1 );   add( f2 );   add( f3 );   add( f4 );   add( f5 );   add( f6 );   add( f7 );   add( f8 );   add( f9 );   add( f10 );   add( f11 );   add( f12 ); 
	}
}
	
