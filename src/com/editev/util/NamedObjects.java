package com.editev.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
 *  A database that's represented by filename->file.  simple yet effective. 
 *
 *  @see See the source <a href="NamedObjects.java">here</a>.
 */
public class NamedObjects {

	/** Base location of file to use as directory. */
	public final String base;
	
	/** Create a database of objects starting at a given point in the filesystem. */
	public NamedObjects( String base ) 	{ this.base = base; }
	public NamedObjects(  )				{ this( "." );       }
	
	/** Try to get an object of a certain name, or throw an exception if none. */	
	public Object read( String name ) throws IOException {
		File file=new File( base, name );
		//if (!readable( file )) throw new FileNotFoundException( "Can't read '"+name+"'" );
		
		ObjectInputStream	objStream = new ObjectInputStream( new FileInputStream( file ) );
		Object				ret;
		
		try {
			try									{ ret = objStream.readObject();	} 
			catch (ClassNotFoundException e)	{ throw new ExceptionWrapper( e ); 	}
		} finally { 
			try					  				{ objStream.close(); }
			catch (IOException e) 				{} // don't report errors on close.
		}
		return ret;
	}
	
	/** Write an object to a given name, or throw an exception if none. */	
	public void write( String name, Object object ) throws IOException {
		File file=new File( base, name );
		new File( file.getParent( ) ).mkdirs();
		//if (!writeable( file )) throw new FileNotFoundException( "Can't write '"+name+"'" );
		
		ObjectOutputStream objStream = new ObjectOutputStream( new FileOutputStream( file ) );
		
		try		{ objStream.writeObject( object );	} // write the object
		finally	{ objStream.close();				} // and always close the file.
	}
	
	
	public static final void p( Object s ) { System.out.println( s.toString() ); }

	public static void main( String args[] ) {
		p("*** started to create NamedObjects ");
		try {
		    System.out.println( File.separator );
			NamedObjects no = new NamedObjects( );
			
			Object obj1 = "object 1";
			Object[] obj2 = {"object", "2"};
			Object obj3 = new Integer( 3 );
			
			p("*** started to write objects ");
			
			no.write( "1/1", obj1 );
			no.write( "1/2", obj2 );
			no.write( "2/3", obj3 );
			
			p("*** started to read objects ");
			
			p( no.read( "1/1" ) );
			p( no.read( "1/2" ) );
			p( no.read( "2/3" ) );

			p("****** finished! read objects ");
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
}
