package generalAlgos;

import java.util.Random;

public class Exponential {

	static Random rand = new Random();
	public static void setSeed(int seed){
		
		rand = new Random(seed);
	}
	public static double expon( double rate ){
		
		if( rand == null )
			rand = new Random();
		
		double u = rand.nextDouble();
		double x = Math.log( 1-u )/(-1*rate);
		return x;
	}

}
