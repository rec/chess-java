package com.editev.util;

/** for patterns like:
Chainable ch = 
  new Fred()
    .chain( new Mike() )
    .chain( new Steve() )
    .chain( new Ethel )
    .chain( etc ).
    
    like Composer

*/
public interface Chainable extends Function {
    Chainable chain( Object f );
}
