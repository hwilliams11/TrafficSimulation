package simApp;

import java.awt.List;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class TrafficStatistics {
	
	/*
	 * statistics
	 *		time in system
	 *		average delay
	 *		average number in system
	*/
	
	/*
	 * vehicle statistics
	 * ==================
	 * - delay
	 * - time in system
	 * 
	 */
	/*
	 * peachtree statistics
	 * =====================
	 * - max time in system
	 * - average delay
	 * - number in system
	 */
	/*
	 * intersection statistics
	 * =======================
	 * - average delay
	 * - queue backup
	 * - throughput
	 * - origin-destination distribution
	*/
	private HashMap<OrigDestData,LinkedList<VehicleStatsInfo>> vehicleStats;
	private HashMap<PTIntersection,LinkedList<Double>> intersectionTimestamps;
	protected final static int MAX = 0;
	protected final static int AVG = 1;
	private static int run = 0;
	private int id;
	private String timeInSystemFilename = "timeInSystemOutputStats";
	private String isThrougputOutputFilename = "isThrougputOutput";
	private PeachtreeSimOutput timeInSystemOutput;
	private PeachtreeSimOutput isThrougputOutput;
	private boolean writeToFile = true;
	private Peachtree pt;
	
	public TrafficStatistics(){	
		
		
		vehicleStats = new HashMap<OrigDestData,LinkedList<VehicleStatsInfo>>();
		intersectionTimestamps = new HashMap<PTIntersection,LinkedList<Double>>();
		
		id = run++;
		if( writeToFile ){
			timeInSystemFilename = timeInSystemFilename.concat( id+".txt");
			timeInSystemOutput = new PeachtreeSimOutput( timeInSystemFilename );
			isThrougputOutputFilename = isThrougputOutputFilename.concat( id+".txt");
			isThrougputOutput = new PeachtreeSimOutput( isThrougputOutputFilename );
		}
		else{
			timeInSystemOutput = new PeachtreeSimOutput();
			isThrougputOutput = new PeachtreeSimOutput();
		}
	}
	public void updateIntersectionThroughput( Intersection is, double time ){
		
		PTIntersection isName = is.getId();
		
		if( intersectionTimestamps.containsKey( isName ) ){
			
			intersectionTimestamps.get( isName ).add( time );
		}
		else{
			LinkedList<Double> list = new LinkedList<Double>();
			list.add( time );
			intersectionTimestamps.put( isName, list);
		}
	}
	public void updateVehicleStats(Vehicle vehicle, Intersection is,
			VehicleDirection direction, double time) {
		
		if( vehicle.getSystemArrivalTime() < 900 )
			return;
		
		OrigDestData origDest = new OrigDestData( vehicle.getOrigin(), vehicle.getDestination() );
		
		double totalTime = time - vehicle.getSystemArrivalTime();
		VehicleStatsInfo vinfo = new VehicleStatsInfo( totalTime, vehicle.getDelays() );
		
		if( vehicleStats.containsKey( origDest ) ){
			vehicleStats.get( origDest ).add( vinfo ); 
		}
		else{
			LinkedList<VehicleStatsInfo> list = new LinkedList<VehicleStatsInfo>();
			list.add( vinfo );
			vehicleStats.put( origDest, list );
		}
	}
	public double[] getTimeInSystemMaxAvg( ){
		
		double [] vehicleTimeInSystem = new double[2];
		int numCars = 0;
		//avg time in system
		//max time in system
		double max = -1;
		double avg = 0;
		
		Set< OrigDestData> origDestKeys = vehicleStats.keySet();
		
		for( OrigDestData origDest: origDestKeys ){
			
			Collection<VehicleStatsInfo> coll = vehicleStats.get( origDest );
			
			for( VehicleStatsInfo vinfo: coll){
				double time = vinfo.getTimeInSystem();
				avg += time;
				if( time > max )
					max = time;
				numCars++;
			}
			vehicleTimeInSystem[MAX] = max;
			vehicleTimeInSystem[AVG] = (double)avg/coll.size();
			
		}
		
		vehicleTimeInSystem[MAX] = max;
		vehicleTimeInSystem[AVG] = (double)avg/numCars;
		
		return vehicleTimeInSystem;
		
	}
	public double[] getFullStats(){
	
		double [] vehicleTimeInSystem = new double[2];
		
		//avg time in system
		//max time in system
		double max = -1;
		double avg = 0;
		int count = 0;
		
		OrigDestData[] origDest = new OrigDestData[2];
		origDest[0] = new OrigDestData(PTIntersection.PEACHTREE_NORTH,PTIntersection.PEACHTREE_SOUTH);
		origDest[1] = new OrigDestData(PTIntersection.PEACHTREE_SOUTH,PTIntersection.PEACHTREE_NORTH);
		
		for( int i=0;i<2;i++){
			
			Collection<VehicleStatsInfo> coll = vehicleStats.get( origDest[i] );	
			for( VehicleStatsInfo vinfo: coll){
				double time = vinfo.getTimeInSystem();
				
				avg += time;
				if( time > max )
					max = time;
				count++;
			}
		}
		vehicleTimeInSystem[MAX] = max;
		vehicleTimeInSystem[AVG] = (double)avg/count;
		
		return vehicleTimeInSystem;
	}
	public double[] getTimeInSystemMaxAvg( OrigDestData origDest ){
		
		double [] vehicleTimeInSystem = new double[2];
		
		//avg time in system
		//max time in system
		double max = -1;
		double avg = 0;
		Collection<VehicleStatsInfo> coll = vehicleStats.get( origDest );
		
		for( VehicleStatsInfo vinfo: coll){
			double time = vinfo.getTimeInSystem();
			avg += time;
			if( time > max )
				max = time;
		}
		vehicleTimeInSystem[MAX] = max;
		vehicleTimeInSystem[AVG] = (double)avg/coll.size();
		
		return vehicleTimeInSystem;
		
	}
	public void printIntersectionThroughput(){
		
		DecimalFormat fmt = new DecimalFormat("#.0000");
		
		ArrayList<PTIntersection> keys = new ArrayList<PTIntersection>(intersectionTimestamps.size() );
		keys.addAll( intersectionTimestamps.keySet() );
		Collections.sort( keys );
		
		for( PTIntersection intersection: keys ){
			
			int count = intersectionTimestamps.get(intersection).size();
			int orig = intersection.getValue();
			double throughput = (double)count*60/Peachtree.getSIM_TIME_SECONDS();
			isThrougputOutput.writeln( orig+", "+fmt.format(throughput) );			
			
		}
	}
	public void printOrigDestData() {
		
		pt = Peachtree.getInstance();
		DecimalFormat fmt = new DecimalFormat("#.0000");
		Set<OrigDestData> data = vehicleStats.keySet();
		HashMap<Integer,Integer> mappings = pt.getOriginMappings();
		
		for( OrigDestData origDest: data ){
			
			LinkedList<VehicleStatsInfo> list = vehicleStats.get(origDest);
			for( VehicleStatsInfo vinfo:list ){
				int orig = origDest.origin.getValue();
				int dest = origDest.dest.getValue();
				
				int mapOrig = mappings.get(orig);
				int mapDest = mappings.get(dest);
				timeInSystemOutput.writeln( mapOrig+", "+mapDest+", "+fmt.format(vinfo.getTimeInSystem()));
			}
		}
	}
	
}
