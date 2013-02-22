package simApp;

/**
 * TrafficEvent that implements an intersection departure
 * @author Holly Williams
 *
 */
public class IntersectionDeparture extends TrafficEvent{

	private Intersection is;
	
	public IntersectionDeparture(Intersection is, Vehicle v,double time){
		
		super(v,time,EventType.IS_DEPARTURE);
		this.is = is;
	}
	/**
	 * Go to the next intersection or depart the system
	 */
	public TrafficEvent event(){
		
		
		int travelTime = 5;
		
		if( vehicle.getRoute().size() == 0 ){
			
			return new SystemDeparture( is,vehicle,vehicle.getDirection(), time+travelTime);
		}
		
		VehicleISInfo vinfo = peachtree.nextIntersection(vehicle);
		Intersection nextIs = vinfo.getIntersection();
		VehicleDirection direction = vinfo.getDirection();
		
		
		vehicle.setDirection(direction);
		IntersectionQArrival iqa = new IntersectionQArrival( nextIs,direction,vehicle,time+travelTime);
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s %-15s %-3s %-15s %-15s\n", "ISDeparture","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"leaving:",is.getId()," to intersection",nextIs.getId());
		peachtree.getSimOutput().write( output );
		
		return iqa;
		
		
	}

}
