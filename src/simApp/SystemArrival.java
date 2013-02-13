package simApp;

import java.util.ArrayList;

/**
 * TrafficEvent that implements a system arrival
 * @author Holly Williams
 *
 */
public class SystemArrival extends TrafficEvent {
	
	public SystemArrival(Vehicle v,int time){
		
		super(v,time,EventType.SYSTEM_ARRIVAL);
		v.setSystemArrivalTime(time);
	}
/**
 * SystemArrival event that processes a car entering into the system. A car will be placed into an intersection's
 * queue
 */
	public TrafficEvent event() {
		
		
		Intersection is = Peachtree.findIntersection( vehicle.getOrigin() );
		int travelTime = 5;
		VehicleDirection direction = null;
		
		Direction entry = Peachtree.vehicleEntry( vehicle );
	
		Direction dir;

		dir = Peachtree.northOrSouthOfDest(is, vehicle);
		
		if( dir == null )
			dir = Peachtree.eastOrWestOfDest(is, vehicle);
		
		direction = new VehicleDirection(entry,dir.getOppositeDir()); 
		vehicle.setDirection(direction);
		
		ArrayList<VehicleISInfo> route = Peachtree.computeRoute( vehicle );
		
		//int travelTime = vehicle.computeTravelTime( is, nextIs );;//compute based on speed and distance between intersections
		
		//System.out.println( "SystemArrival -> "+vehicle);
		//System.out.println(dir+" of destination");
		//System.out.println("Arrival vehicle id: "+vehicle.getId()+" origin: "+vehicle.getOrigin()+" destination: "+vehicle.getDestination()+" direction: "+vehicle.getDirection());
		System.out.printf("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s\n", "SystemArrival","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection());
		IntersectionQArrival iqa = new IntersectionQArrival(is,direction,vehicle,travelTime+time);
		
		return iqa;
	}
}
