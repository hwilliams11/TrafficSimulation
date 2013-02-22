package testSim;
import org.apache.commons.math3.distribution.GammaDistribution;

public class TestGamma {
	
	public static void main(String[]args){
		GammaDistribution gd = new GammaDistribution(3,3);
		double d = gd.sample();
		System.out.println("Value: "+d);
	}

}
