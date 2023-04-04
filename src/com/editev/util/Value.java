package com.editev.util;

/** any Object that many be evaluated to return a value (an object) .*/
public interface Value {
    /** evaluate this Object and return another Object. */
    Object eval();
	
    /** a Value that's just another Object, stored.  mainly used as a base class. */
	class Store       implements Value    	 {
		public final         Object value;		
		public	Store(       Object value  ) { this.value = value;          }
		public	Object eval()                { return value;                }
	}
	
    /** a Value that references another Value.  mainly used as a base class. */
	class Reference    implements Value	     {
		public final         Value  value;
		public	Reference(   Value  value )  { this.value = value;          }
		public	Object eval()                { return value.eval();         }
	}

    /** a Value that constructs a new member of Class when evaluated.  you could
     *  probably do this better with an anonymous inner class. */
	class Constructor implements Value {
		public final         Class  value;		
		public	Constructor( Class  value  ) { this.value = value;          }		
		public	Object eval()                { 
		    try {
		        return value.newInstance();
		    } catch (Exception e) {
		        throw new ExceptionWrapper( e );
		    }
		}
	}
}
