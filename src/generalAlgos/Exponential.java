package generalAlgos;


public class Exponential {

	private static RandomGenerator rand = null;
	
	public static double expon( double rate ){
		
		if( rand == null ){
			rand = new RandomGenerator();
		}
		 
		double u = rand.xorrandDouble();
		double x = Math.log( 1-u )/(-1*rate);
		return x;
	}

}
