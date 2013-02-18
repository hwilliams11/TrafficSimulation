package simApp;

/**
 * TrafficEvent that implements an intersection arrival 
 * @author Holly
 *
 */
public class IntersectionArrival extends TrafficEvent {

	private Intersection is;
	private VehicleDirection direction;
	
	
public IntersectionArrival(Intersection is, Vehicle v,VehicleDirection direction, int time){
		
		super(v,time,EventType.IS_ARRIVAL);
		this.is = is;
		this.direction = direction;
	}
/**
 * Remove the vehicle from the queue and schedule a departure
 */
	public TrafficEvent event(){
		
		//System.out.println("IntersectionArrival "+is.getId()+": "+is.getId().getValue()+" in queue: "+vehicle.getDirection());
		int processingTime = is.getProcessingTime(vehicle);
		//System.out.println("ISArrival intersection: "+is.getId()+" vehicle id: "+vehicle.getId()+" origin: "+vehicle.getOrigin()+" destination: "+vehicle.getDestination()+" direction: "+vehicle.getDirection()+" service time: "+processingTime);
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-3s\n", "ISArrival","intersection:",is.getId(),"time",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"service time:",processingTime);
		peachtree.getSimOutput().write( output );
		Vehicle v2 = is.removeFromQueue( direction );

		IntersectionDeparture id = new IntersectionDeparture(is,v2,time+processingTime);
		return id;
		
	}
}
