package com.editev.util;

import java.io.OutputStream;


public class BytesOutputStream extends OutputStream {
    public final byte bytes[];
    protected    int  length = 0;

    public BytesOutputStream( byte[] bytes )             { this.bytes = bytes;                               }
    public void   write( int b                        )  { bytes[ length++ ] = (byte)b;                      }
    public void   write( byte[] src, int pos, int len )  { System.arraycopy( src, pos, bytes, length, len);  }
    public void   reset()                                { length = 0;                                       }
    public int    getLength()                            { return length;                                    }
    public String toString()                             { return new String( bytes, 0, length);             }
}
