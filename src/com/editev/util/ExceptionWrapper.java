package com.editev.util;

/** wrap any Exception inside a RuntimeException */
public class ExceptionWrapper extends RuntimeException {
	private static final long serialVersionUID = 5072942412651810392L;
	public final Exception exception;
	
    public ExceptionWrapper( Exception e			) {				this.exception = e;	 } 
    public ExceptionWrapper( Exception e, String s 	) { super( s );	this.exception = e;  }
}
