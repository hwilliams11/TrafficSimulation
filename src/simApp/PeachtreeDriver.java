package simApp;

import generalAlgos.PriorityQueue;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

import simEngine.EventEngine;

public class PeachtreeDriver {

	private final static int NUM_RUNS = 10;
	private final static int MEAN = 0;
	private final static int HALF_WIDTH = 1;
	private static EventEngine engine;
	
	public static PriorityQueue<Event> getFutureEventList(){
		
		return engine.getFutureEventList();
		
	}
	public static TrafficStatistics runSimulation(){
		
		Comparator<Event> cmp = new EventComparator();
		Peachtree.reset();
		Peachtree pt = Peachtree.getInstance();
		List<? extends Event> arrivals = pt.createArrivals();
		SimulationDone doneTest = new TrafficSimulationDone();
		engine = new EventEngine(cmp,(List<Event>) arrivals,doneTest);
		engine.process();
		TrafficStatistics stats = pt.getStats();
		System.out.println("Num cars: "+pt.getTotalCars());
		return stats;
		
	}
	public static double[] getConfidenceInterval( double [] values ){
		
		double mean = 0;
		double var = 0;
		double stdev;
		double half;
		double t = 3.25; //for N=10 9 degrees of freedom;
		int N = values.length;
		double [] res = new double[2];
		
		for(int i=0;i<N;i++){
			mean+=values[i];
		}
		mean/=N;
		for(int i=0;i<N;i++){
			var+=Math.pow( ( values[i]-mean ), 2 );
		}
		var/=(N-1);
		stdev = Math.sqrt( var );
		
		half = t*stdev/Math.sqrt(N);
	
		res[MEAN] = mean;
		res[HALF_WIDTH] = half;
		return res;
	}
	public static void main(String[] args) {
		
		Peachtree.setWriteToFile();
		TrafficStatistics []  simRuns = new TrafficStatistics[NUM_RUNS];

		double[] maxTemp = new double[NUM_RUNS];
		double[] avgTemp = new double[NUM_RUNS];
		
		for(int i=0;i<NUM_RUNS;i++){
			System.out.print("Run "+(i+1)+" ");
			simRuns[i]=runSimulation();
			maxTemp[i] = simRuns[i].getTimeInSystemInfo()[TrafficStatistics.MAX];
			avgTemp[i] = simRuns[i].getTimeInSystemInfo()[TrafficStatistics.AVG];
			//System.out.println(simRuns[i].getTimeInSystemInfo()[TrafficStatistics.MAX]);
			//System.out.println(simRuns[i].getTimeInSystemInfo()[TrafficStatistics.AVG]);
		}
		
	
		//make confidence interval
		double [] maxCI = getConfidenceInterval(maxTemp);
		double [] avgCI = getConfidenceInterval(avgTemp);
		DecimalFormat fmt = new DecimalFormat("#.000");
		System.out.println("Max time in system sample max = "+fmt.format(maxCI[0])+" CI: "+fmt.format(maxCI[0] - maxCI[1])+" <= mu <=  "+fmt.format(maxCI[0]+maxCI[1]));
		System.out.println("Avg time in system sample avg = "+fmt.format(avgCI[0])+" CI: "+fmt.format(avgCI[0] - avgCI[1])+" <= mu <=  "+fmt.format(avgCI[0]+avgCI[1]));
		
	}

}