package simApp;

import java.util.ArrayList;

/**
 * TrafficEvent that implements a system arrival
 * @author Holly Williams
 *
 */
public class SystemArrival extends TrafficEvent {
	
	public SystemArrival(Vehicle v,double currentTime){
		
		super(v,currentTime,EventType.SYSTEM_ARRIVAL);
		v.setSystemArrivalTime(currentTime);
	}
/**
 * SystemArrival event that processes a car entering into the system. A car will be placed into an intersection's
 * queue
 */
	public TrafficEvent event() {
		
		
		int travelTime = 5;
		
		peachtree.computeRoute( vehicle );
		
		//int travelTime = vehicle.computeTravelTime( is, nextIs );;//compute based on speed and distance between intersections
	
		VehicleISInfo vinfo = peachtree.nextIntersection( vehicle );
		Intersection is = vinfo.getIntersection();
		VehicleDirection direction = vinfo.getDirection();
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s\n", "SystemArrival","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection());
		peachtree.getSimOutput().write( output );
		IntersectionQArrival iqa = new IntersectionQArrival(is,direction,vehicle,travelTime+time);
		
		return iqa;
	}
}