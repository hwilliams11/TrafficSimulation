package simApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Output for program can be written to file or screen
 * @author Holly
 *
 */
public class PeachtreeSimOutput {
	
	PrintStream stream;
	
	/**
	 * Prints to System.out
	 */
	public PeachtreeSimOutput(){
		
		this( System.out );
	}
	/**
	 * Prints to a file
	 * @param filename 
	 */
	public PeachtreeSimOutput( String filename ){
		
		try {
			stream = new PrintStream( new File(filename) );
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		
	}
	/**
	 * Prints to a stream already defined
	 * @param stream
	 */
	public PeachtreeSimOutput( PrintStream stream ){
		
		this.stream = stream;
	}
	/**
	 * Write string
	 * @param string
	 */
	public void write( String string ){
		
		stream.print( string );
	}
	/**
	 * Write string
	 * @param string
	 */
	public void writeln( String string ){
		
		stream.println( string );
	}
	/**
	 * Close stream
	 */
	public void close() {
		
		stream.close();
	}

}
