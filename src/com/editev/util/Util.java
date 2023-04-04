package com.editev.util;


public class Util {
    /** replace characters from one list of chars with other characters. */
    public static String replace( String source, char[] find, char[] replace ) {
        int length = Math.min( find.length, replace.length );
        char[] s = source.toCharArray();
        // int reps = 0;
        for (int i = s.length-1; i>=0; i--) {
            char c = s[i];
            for (int j = length-1; j>=0; j--) {
                if (find[j] == c) {
                    s[i] = replace[j];
                    //reps++;
                    break;
                }
            }
        }
        // System.out.println( reps + " replacements made." );
        return new String( s );
    }
    /*
    public static byte toByte(   long  i ) {
        if (i < 0) throw new RuntimeException( "end of input reached!");    // -1 is from stupid read() method.
        return (byte) (i <= 0x7f ? i : i-0x100); 
    }
    */
    public static int  fromByte( byte b ) {
        // no test here, we can always do this.
        return        (b >= 0    ? b : b+0x100); 
    }
    
    
	public static void p( Object o ) { System.out.println( o.toString() ); }
	public static void d( Object o ) { p( new java.util.Date() +": "+ o ); }


    // the MOST significant byte has the SMALLEST byte index in these functions.
    //
    public static byte[] fromLong( long value, int    bytes, int bits ) { return fromLong( value, new byte[ bytes ], bits ); }
    public static byte[] fromLong( long value                         ) { return fromLong( value, 8                 ); }
    public static byte[] fromLong( long value, int    bytes           ) { return fromLong( value, new byte[ bytes ] ); }
    public static byte[] fromLong( long value, byte[] bytes           ) { return fromLong( value, bytes,          8 ); }
    public static byte[] fromLong( long value, byte[] bytes, int bits ) {
        int mask = (1 << bits)-1;
        for (int i=bytes.length-1; i>=0; i--) { // start with least significant bites
            long l = value & mask;
            bytes[i] = (byte) ( (l > 0xFF) ? l : (l-0x100) );
            value >>>= bits;
        }
        return bytes;
    }

    public static long toLong( byte[] bytes             ) { return toLong( bytes, 8 ); }

    public static long toLong(byte[] b, int bits) {
        int mask = (1 << bits)-1;
        long val = 0;
        for (int i = 0; i < b.length ; i++) {
            val <<= bits;
            val |= (b[i] & mask);
        }
        return val;
    }

  
    /** extracts some specified contiguous bits from the given long
        Stolen from K & R 'C' book.
     */
    public static long getBits(long x, int pos, int len) {
       return (x >>> (pos + 1 - len)) & ~(~0 <<len);
     }


    /** Extracts some specified set of contiguous bytes from the given long.
     *  pos starts at the right most bit, beginning at 0.
     *  len goes from left to right
     */
    public static byte[] getBytes(long x, int pos, int len) { return getBytes(x, pos, len, false); }
  

    /** Extracts some specified set of contiguous bytes from the given long.
     *  pos starts at the right most bit, beginning at 0.
     *  len goes from left to right
     */
    public static byte[] getBytes(long x, int pos, int len, boolean reverse) {
      if (pos < 0 || len <= 0) throw new RuntimeException("Position and Length vars must be >= 0");
      long bits = getBits((int)x, pos*8 +7, len*8);
      byte[] b = new byte[len];
      for (int i = 0; i < len; i++) {
        int exp = reverse ? i : len - i - 1;
        b[i] = (byte) ((bits & (0xFFL * (long)Math.pow(0x100, exp))) >>> exp*8);
      }
      return b;
    }

    public static void main( String[] args ) {
        long test1 = 1234568791;
        byte[] b = fromLong( test1, 4, 8 );
        for (int i=0; i<b.length; i++) {
            p( ""+b[i] );
        }
        p( ""+toLong( fromLong( test1, 4, 8 ), 8 ) );
        p( ""+toLong( fromLong( test1, 4, 7 ), 7 ) ) ;

        p("" + getBits(0xFF00, 15, 8));
        byte[] a = getBytes(0xFF7F0000, 3, 3);
        for (int i = 0; i < a.length; i++) p(" " + a[i]);
        p("");
        byte[] z = getBytes(0xFF7F0000, 3, 3, true);
        for (int i = 0; i < z.length; i++) p(" " + z[i]);
        p("");
    }
}
