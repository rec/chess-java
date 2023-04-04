package com.editev.util;

/** a function that returns an int. */
public interface IntFunction {
    int evalInt( Object value );
    
    class Fixed implements IntFunction {
        public final        int    fixed;
        public Fixed(       int    fixed ) { this.fixed = fixed; }
        public int evalInt( Object value ) { return fixed; }
    }
}

