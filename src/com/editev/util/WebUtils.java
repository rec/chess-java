package com.editev.util;

public class WebUtils {
	public static final int ENCODING_BASE = 36; // a-z, 0-9
	
	public static String encode( long l, int base ) { return Long.toString( l,    base          ); }	
	public static String encode( long l )           { return encode( l,           ENCODING_BASE ); }	
	public static String encode()                   { return encode( System.currentTimeMillis() ); }
}
