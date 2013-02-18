package simApp;

/**
 * System Departure class is a TrafficEvent that implements a system departure
 * @author Holly Williams
 *
 */
public class SystemDeparture extends TrafficEvent {
	
	private Intersection is;
	private VehicleDirection direction;
	
	public SystemDeparture(Intersection is, Vehicle v, VehicleDirection direction,int time){
		
		super(v, time,EventType.SYSTEM_DEPARTURE);
		this.is = is;
		this.direction = direction;		
	}
	/**
	 * Peachtree and intersection statistics are updated on departure from system
	 */
	public TrafficEvent event(){
		
		//System.out.println( "SystemDeparture id:"+vehicle.getId()+" time: "+time+" origin: "+vehicle.getOrigin()+" destination: "+vehicle.getDestination()+" going "+vehicle.getDirection().getTo()+" on "+vehicle.getDestination());
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s %-6s %-6s %-15s\n", "SystemDeparture","intersection:",is.getId(),
				"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),
				"going:",vehicle.getDirection().getTo()+" on:",vehicle.getDestination());
		
		peachtree.getSimOutput().write( output );
		peachtree.updateStatistics(is,direction,time,vehicle);
		return null;
	}

}
