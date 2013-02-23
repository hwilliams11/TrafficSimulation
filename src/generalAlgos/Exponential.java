package generalAlgos;

/**
 * Exponential random number generator
 * @author Holly
 *
 */
public class Exponential {

	private static RandomGenerator rand = null;
	
	/**
	 * 
	 * @param rate rate parameter for expon
	 * @return random number from this exponential distribution
	 */
	public static double expon( double rate ){
		
		if( rand == null ){
			rand = new RandomGenerator();
		}
		 
		double u = rand.xorrandDouble();
		double x = Math.log( 1-u )/(-1*rate);
		return x;
	}

}
