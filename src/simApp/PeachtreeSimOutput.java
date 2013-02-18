package simApp;

import java.io.PrintStream;

public class PeachtreeSimOutput {
	
	PrintStream stream;
	
	public PeachtreeSimOutput( PrintStream stream ){
		
		this.stream = stream;
	}
	public void write( String string ){
		
		stream.print( string );
	}
	public void writeln( String string ){
		
		stream.println( string );
	}

}
