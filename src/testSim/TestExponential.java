package testSim;

import generalAlgos.Exponential;

public class TestExponential {

public static void main(String[] args) {
		
		double num = 0;
		double avg = 0;
		final int NUM_ITERS = 1000;
		double rate = 1/12.;
		
		for( int i = 0;i < NUM_ITERS ;i++){
			num = Exponential.expon(rate);
			avg += num;
		}
		System.out.println( avg/NUM_ITERS +" vs. "+(1/rate));

	}

}
