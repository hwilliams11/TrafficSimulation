package generalAlgos;
import java.lang.Math;

public class RandomGenerator {
	
	long seed = System.nanoTime();
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
