package simApp;
import java.util.Queue;


/**
 * TrafficEvent that implements an arrival to an intersection's queue
 * @author Holly Williams
 *
 */
public class IntersectionQArrival extends TrafficEvent {
	
	private Intersection is;
	private VehicleDirection direction;
	
	public IntersectionQArrival(Intersection is,VehicleDirection direction2, Vehicle v,double time){
		
		super(v,time,EventType.IS_Q_ARRIVAL);
		this.is = is;
		this.direction = direction2;
		this.is.addToQueue( vehicle, direction );

	}
	/**
	 * Place new arrival into the right direcition's queue and compute its delay then schedule
	 * an IntersectionArrival
	 */
	public TrafficEvent event(){
		
		double delay = 3;	
		delay = is.getDelay( time, direction );
		vehicle.getDelays().add( (double)delay );
		
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s %-15s %-3s\n", "ISQArrival","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"queue delay:",delay);
		peachtree.getSimOutput().write( output );
		IntersectionArrival ia = new IntersectionArrival(is,vehicle,direction,time+delay);
		vehicle.setLastArrival(ia);
		return ia;
	}

}
