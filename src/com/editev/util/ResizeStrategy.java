package com.editev.util;

/** design pattern for strategies for resizing dynamic lists */
public interface ResizeStrategy {
    /** design pattern Strategy for list resizing!
     */
    int computeResize( int old, int nu );
    
    /** either double the space or fit exactly, which ever is larger. */
    class Doubling implements ResizeStrategy {
        public int computeResize( int old, int nu ) { return (old > nu) ? old : Math.max( nu, 2*old ); }
    }
    public static final ResizeStrategy DOUBLING = new Doubling();
}
