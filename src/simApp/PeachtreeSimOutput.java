package simApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PeachtreeSimOutput {
	
	PrintStream stream;
	
	public PeachtreeSimOutput(){
		
		this( System.out );
	}
	public PeachtreeSimOutput( String filename ){
		
		try {
			stream = new PrintStream( new File(filename) );
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		
	}
	public PeachtreeSimOutput( PrintStream stream ){
		
		this.stream = stream;
	}
	public void write( String string ){
		
		stream.print( string );
	}
	public void writeln( String string ){
		
		stream.println( string );
	}
	public void close() {
		
		stream.close();
	}

}
