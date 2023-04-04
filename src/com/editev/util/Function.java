package com.editev.util;
import java.util.Hashtable;

public interface Function {

	Object eval( Object x ) throws RuntimeException;

	final Function IDENTITY = new Identity();
		
	class Identity implements Function {
		public Object eval( Object o )	{ return o;          }
		protected     Identity()        {                    }
		public String toString()		{ return "identity"; }
	}
	
	/** references a function, and acts like that function */	
	class Reference implements Function {
		public final          Function f;
		public Reference(     Function f )  { this.f = f; 				}
		public Object	eval( Object obj )	{ return f.eval( obj );		}
		public String	toString()			{ return "( "+f+" )";		}
	}
	
	/** attach data to a Function Reference. */
	class Evaluate extends Reference implements Value {
		public final                 Object x;
		public Evaluate( Function f, Object x )	{ super( f );  this.x = x;  }
		public Evaluate(             Object x )	{ this( IDENTITY, x );      }
		
		public Object eval()                    { return f.eval( x );       }
		public String toString()                { return f+"( "+x+" )";     }

		/** evaluate this eval and send the value the input (treated as output). */
		public Object eval( Object x )          { return ((Function) x).eval( eval() );	}		
	}
	
	/** call the Function until there's an exception */
	class Loop extends Reference {
		public Loop(        Function f )    { super( f ); }		
		public Object eval(	Object obj )    { while (true) obj = f.eval( obj );	}		
	}
	
	/** Cache the values of a function in a hashtable. */
	class Cached extends Reference {
	    private Hashtable table = new Hashtable();
	    public Cached(      Function f   ) { super( f ); }
	    public Object eval( Object   obj ) {
	        Object ret = table.get( obj );
	        if (ret == null) {
	            ret = f.eval( obj );
	            table.put( obj, ret );
	        }
	        return ret;
	    }
	}
	
	/** any two functions glued together */
	abstract class Pair extends Reference {
		public final Function g;
		public Pair( Function g, Function f )   { super( f ); this.g = g;       }		
		public String	toString()              { return "( "+g+" + "+f+" )";   }
	}
	
	/** the composition of two Functions */
	class Compose extends Pair {
		public Compose(     Function g, Function f ) { super( g,      f           ); }		
		public Object eval( Object   x             ) { return g.eval( f.eval( x ) ); }
	}
	
	/** try one function, and then the second if it fails! */
	class Try extends Pair {
		public Try(         Function g, Function f ) { super( g,      f           ); }		
		public Object eval( Object   x             ) { 
		    try                 { return f.eval( x ); }
		    catch (Exception e) { return g.eval( x ); }
		}
	}
	
	/** wrapper that allows maps to throw Exceptions. */
	abstract class Catch implements Function {
		
		/** eval a object, perhaps throwing an ExceptionWrapper */
		public Object eval( Object x )	{
			try                 { return catchFunction(        x ); } 
			catch (Exception e) { throw  new ExceptionWrapper( e );	}
		}
		abstract public Object catchFunction( Object x ) throws Exception;
	}
}
