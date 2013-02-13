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
	
	public IntersectionQArrival(Intersection is,VehicleDirection direction2, Vehicle v,int time){
		
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
		
		//System.out.println("IntersectionQArrival->: "+time);
		int delay = 3;	//calculate delay based on which lanes are in movement
						//how long the light will be green
						//the car's position in the queue
		//System.out.println("direction: "+direction);
		delay = is.getDelay( time, direction );
		vehicle.setSumDelay( vehicle.getSumDelay()+delay );
		//System.out.println("ISQArrival intersection: "+is.getId()+" vehicle id: "+vehicle.getId()+" origin: "+vehicle.getOrigin()+" destination: "+vehicle.getDestination()+" direction: "+vehicle.getDirection()+" queue delay: "+delay);
		System.out.printf("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s %-15s %-3s\n", "ISQArrival","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"queue delay:",delay);
		IntersectionArrival ia = new IntersectionArrival(is,vehicle,direction,time+delay);
		return ia;
	}

}
