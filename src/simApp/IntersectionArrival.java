package simApp;

import java.util.Iterator;
import java.util.Queue;


/**
 * TrafficEvent that implements an intersection arrival 
 * @author Holly
 *
 */
public class IntersectionArrival extends TrafficEvent {

	private Intersection is;
	private VehicleDirection direction;
	
	
public IntersectionArrival(Intersection is, Vehicle v,VehicleDirection direction, double d){
		
		super(v,d,EventType.IS_ARRIVAL);
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
		
		Queue<VehicleISInfo> route = vehicle.getRoute();
		
		/*
		if( route.size()>0 ){	
				//next intersection
				VehicleISInfo vinfo = vehicle.getRoute().peek();
			
				Intersection nextIs = vinfo.getIntersection();
				QueueInfo qinfo = nextIs.getIntersectionQs().get(vinfo.getDirection());
				Queue<Vehicle> queue = qinfo.getQueue();
				//if next intersection's queue full
				if( queue.size()==qinfo.getMAX_LEN() ){
					System.out.println("1Wait intersection full "+vehicle.getId()+" time: "+time);
					
					
					//compute delay before we can go
					//int nextIsdelay = nextIs.getDelay( time+processingTime, vinfo.getDirection() );
					Iterator<Vehicle> itr = is.getIntersectionQs().get(direction).getQueue().iterator();
					Vehicle v = null;
					//skip current car
					if( itr.hasNext() )
						v = itr.next();
					 
					
					double nextIsdelay = v.getDelays().getLast();
					//see if we can go through this intersection's light 
					double myDelay = is.getDelay(time+processingTime+nextIsdelay, direction);
					System.out.println("WAIT!!!");
					IntersectionArrival ia = new IntersectionArrival(is,vehicle,direction,time+myDelay);
					vehicle.setLastArrival(ia);
					
					
					
					//add delay to all the cars behind the current car in the queue
				
					while( itr.hasNext() ){
						
						Vehicle nextVehicle = itr.next();
						System.out.println(nextVehicle.getId()+" time: "+time+" last arrival: "+nextVehicle.getLastArrival());
						peachtree.getSimOutput().writeln(nextVehicle.getId()+" time: "+time+" last arrival: "+nextVehicle.getLastArrival());
						System.out.println(nextVehicle.getId()+" time: "+time+" last arrival: "+nextVehicle.getLastArrival().time);
						peachtree.getSimOutput().writeln(nextVehicle.getId()+" time: "+time+" last arrival: "+nextVehicle.getLastArrival().time);
						double lastTime = nextVehicle.getLastArrival().getTime();
						IntersectionArrival currIa = new IntersectionArrival( is,nextVehicle,direction,lastTime+nextIsdelay );
						nextVehicle.getLastArrival().active = false;
						nextVehicle.setLastArrival(currIa);
						PeachtreeDriver.getFutureEventList().insert(currIa);
						
					}
					
				
					System.out.println("2Wait intersection full "+vehicle.getId()+" time: "+time);
					return ia;
			}
		}
		*/
		//System.out.println("DIRECTION: "+direction);
		
		Vehicle v2 = is.removeFromQueue( direction );
		IntersectionDeparture id = new IntersectionDeparture(is,vehicle,time+processingTime);
		String output = String.format("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-3s\n", "ISArrival","intersection:",is.getId(),"time",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"service time:",processingTime);
		peachtree.getSimOutput().write( output );
	
		return id;
		
	}
}
