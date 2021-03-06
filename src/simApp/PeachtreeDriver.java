package simApp;

import generalAlgos.PriorityQueue;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

import simEngine.EventEngine;

/**
 * Driver of simulation
 * @author Holly
 *
 */
public class PeachtreeDriver {

	protected static int NUM_BATCHES = 5;
	protected static int NUM_RUNS = 5;
	
	private final static int MEAN = 0;
	private final static int HALF_WIDTH = 1;
	private static EventEngine engine;
	
	/**
	 * 
	 * @return future event list
	 */
	public static PriorityQueue<Event> getFutureEventList(){
		
		return engine.getFutureEventList();
		
		
	}
	/**
	 * Runs a simulation 
	 * @return statistics of this run
	 */
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
	/**
	 * Gets the confidence interval of an array of data
	 * @param values values to compute confidence interval for
	 * @return average and standard deviation in a len-2 array
	 */
	public static double[] getConfidenceInterval( double [] values ){
		
		double mean = 0;
		double var = 0;
		double stdev;
		double half;
		double t; //for N=10 9 degrees of freedom;
		double [] tValues = {	6.314000, 2.920000, 2.353000, 2.132000, 2.015000, 
								1.943000, 1.895000, 1.860000, 1.833000, 1.812000, 
								1.796000, 1.782000, 1.771000, 1.761000, 1.753000, 
								1.746000, 1.740000, 1.734000, 1.729000, 1.725000, 
								1.721000, 1.717000, 1.714000, 1.711000, 1.708000, 
								1.706000, 1.703000, 1.701000, 1.699000, 1.697000, 
								1.684000, 1.671000, 1.664000, 1.660000, 1.646000, 
								1.645000}; 
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
		
		if( N>=tValues.length)
			t = tValues[tValues.length-1];
		else
			t = tValues[N-1];
		
		half = t*stdev/Math.sqrt(N);
	
		res[MEAN] = mean;
		res[HALF_WIDTH] = half;
		return res;
	}
	/**
	 * Run multiple replications for simulation
	 */
	public static void multipleReplications(){
		

			NUM_BATCHES = 1;
			NUM_RUNS = 5;
			Peachtree.setWriteToFile();
			TrafficStatistics []  simRuns = new TrafficStatistics[NUM_RUNS];

			double[] maxTemp = new double[NUM_RUNS];
			double[] avgTemp = new double[NUM_RUNS];
			OrigDestData orig = new OrigDestData(PTIntersection.PEACHTREE_SOUTH,PTIntersection.PEACHTREE_NORTH);
			
			for(int i=0;i<NUM_RUNS;i++){
				System.out.print("Run "+(i+1)+" ");
				simRuns[i]=runSimulation();
				maxTemp[i] = simRuns[i].getFullStats()[TrafficStatistics.MAX];
				avgTemp[i] = simRuns[i].getFullStats()[TrafficStatistics.AVG];
				//maxTemp[i] = simRuns[i].getTimeInSystemMaxAvg(orig)[TrafficStatistics.MAX];
				//avgTemp[i] = simRuns[i].getTimeInSystemMaxAvg(orig)[TrafficStatistics.AVG];
				simRuns[i].printOrigDestData();
				simRuns[i].printIntersectionThroughput();
			

			}
			
		
			//make confidence interval
			double [] maxCI = getConfidenceInterval(maxTemp);
			double [] avgCI = getConfidenceInterval(avgTemp);
			DecimalFormat fmt = new DecimalFormat("#.000");
			//System.out.println("Max time in system sample max = "+fmt.format(maxCI[0])+" CI: "+fmt.format(maxCI[0] - maxCI[1])+" <= mu <=  "+fmt.format(maxCI[0]+maxCI[1]));
			System.out.println("Avg time in system sample avg = "+fmt.format(avgCI[0])+" CI: "+fmt.format(avgCI[0] - avgCI[1])+" <= mu <=  "+fmt.format(avgCI[0]+avgCI[1]));
			
	}
	/**
	 * Run batch means
	 */
	public static void batchMeans(){

		TrafficStatistics  simRun = new TrafficStatistics();

		double[] maxTemp = new double[NUM_RUNS];
		double[] avgTemp = new double[NUM_RUNS];
		
		System.out.println("Running simulation - batch means");
		OrigDestData orig = new OrigDestData(PTIntersection.PEACHTREE_SOUTH,PTIntersection.PEACHTREE_NORTH);
		simRun=runSimulation();		
		
		for( int i=0;i<NUM_BATCHES;i++){
			int start = i*Peachtree.TWO_HOURS;
			int end = start + Peachtree.TWO_HOURS;

			maxTemp[i] = simRun.getFullStats(start,end)[TrafficStatistics.MAX];
			avgTemp[i] = simRun.getFullStats(start,end)[TrafficStatistics.AVG];
			//maxTemp[i] = simRuns[i].getTimeInSystemMaxAvg(orig)[TrafficStatistics.MAX];
			//avgTemp[i] = simRuns[i].getTimeInSystemMaxAvg(orig)[TrafficStatistics.AVG];
			//System.out.println(simRuns[i].getTimeInSystemInfo()[TrafficStatistics.MAX]);
			//System.out.println(simRuns[i].getTimeInSystemInfo()[TrafficStatistics.AVG]);
		}
		//make confidence interval
		double [] maxCI = getConfidenceInterval(maxTemp);
		double [] avgCI = getConfidenceInterval(avgTemp);
		DecimalFormat fmt = new DecimalFormat("#.000");
		//System.out.println("Max time in system sample max = "+fmt.format(maxCI[0])+" CI: "+fmt.format(maxCI[0] - maxCI[1])+" <= mu <=  "+fmt.format(maxCI[0]+maxCI[1]));
		System.out.println("Avg time in system sample avg = "+fmt.format(avgCI[0])+" CI: "+fmt.format(avgCI[0] - avgCI[1])+" <= mu <=  "+fmt.format(avgCI[0]+avgCI[1]));
	
	}
	public static void main(String[] args) {
		
		Peachtree.setWriteToFile();
		batchMeans();	
		multipleReplications();
		
	}

}