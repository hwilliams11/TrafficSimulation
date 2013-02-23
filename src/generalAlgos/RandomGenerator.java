package generalAlgos;
import java.lang.Math;

/**
 * XOR Random number generator
 * @author Nitin
 *
 */
public class RandomGenerator {
	
	long seed = System.nanoTime();
	/**
	 * 
	 * @return random number uniform (0,1)
	 */
	public double xorrandDouble() {
		  
		long x = seed;
		  
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		  
		seed = x;
		x &= ((1L << 32) - 1);
		int z = (int) x;
		double y = Math.abs((double)z/2147483647);
		return y;
	}
}
