package simApp;

import java.util.Collection;
import java.util.HashMap;

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
	private HashMap<Integer,VehicleStatsInfo> vehicleStats;
	protected final static int MAX = 0;
	protected final static int AVG = 1;
	
	public TrafficStatistics(){
		
		vehicleStats = new HashMap<Integer,VehicleStatsInfo>();
	}
	public void updateVehicleStats(Vehicle vehicle, Intersection is,
			VehicleDirection direction, int time) {
		
		int totalTime = time - vehicle.getSystemArrivalTime();
		VehicleStatsInfo vinfo = new VehicleStatsInfo( totalTime, vehicle.getDelays() );
		
		vehicleStats.put( vehicle.getId(), vinfo );
	}
	public double[] getTimeInSystemInfo(){
		
		double [] vehicleTimeInSystem = new double[2];
		
		//avg time in system
		//max time in system
		double max = -1;
		double avg = 0;
		Collection<VehicleStatsInfo> coll = vehicleStats.values();
		
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
	public void printReport(){
	
		double []  tis = getTimeInSystemInfo();
		System.out.println("MAX: "+tis[MAX]+" AVERAGE: "+tis[AVG]);
	}
	
}
