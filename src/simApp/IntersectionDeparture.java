package simApp;

/**
 * TrafficEvent that implements an intersection departure
 * @author Holly Williams
 *
 */
public class IntersectionDeparture extends TrafficEvent{

	private Intersection is;
	
	public IntersectionDeparture(Intersection is, Vehicle v,int time){
		
		super(v,time,EventType.IS_DEPARTURE);
		this.is = is;
	}
	/**
	 * Based on vehicle destination and current intersection determine the next intersection to go
	 * to and place in that intersection's queue
	 */
	public TrafficEvent event(){
		
		
		VehicleDirection direction = null;
		int travelTime = 5;
		Intersection nextIs = null;
		
		Direction northOrSouth = Peachtree.northOrSouthOfDest(is, vehicle);
		
		if( sameLevel() ){ 
			//keep going through and schedule system departure
			SystemDeparture sd = null;
			if( vehicle.getDestination().getValue()%10==0 ){//heading west
				
				direction = new VehicleDirection( vehicle.getDirection().getFrom(), Direction.WEST );
				vehicle.setDirection(direction);
				sd = new SystemDeparture( is,vehicle, direction, time+travelTime);
			}
			if( vehicle.getDestination().getValue()%10==2 ){//heading east
				direction = new VehicleDirection( vehicle.getDirection().getFrom(), Direction.EAST );
				vehicle.setDirection(direction);
				sd = new SystemDeparture( is,vehicle,direction, time+travelTime);
			}
			
			return sd;
			
		}
		else if( northOrSouth == Direction.NORTH ){  //go south
			
			direction = new VehicleDirection( northOrSouth, Direction.SOUTH);
			vehicle.setDirection(direction);
			
			if( is.getId()==PTIntersection.TENTH&&vehicle.getDestination()==PTIntersection.PEACHTREE_SOUTH ){//departing system
				
				return new SystemDeparture( is,vehicle,direction, time+travelTime);
			}
			
			
			nextIs = Peachtree.getNextIntersection( is, Direction.SOUTH );
		}
		else if( northOrSouth == Direction.SOUTH ){	//go north
			
			direction = new VehicleDirection( northOrSouth, Direction.NORTH );
			vehicle.setDirection(direction);
			
			if( is.getId()==PTIntersection.FOURTEENTH&&vehicle.getDestination()==PTIntersection.PEACHTREE_NORTH ){//departing system
				return new SystemDeparture( is,vehicle,direction, time+travelTime);
			}
			
			nextIs = Peachtree.getNextIntersection( is, Direction.NORTH );
		}
		//Leave queue and depart intersection
		IntersectionQArrival iqa = new IntersectionQArrival( nextIs,direction,vehicle,time+travelTime);
		//System.out.println("Departing intersection: "+is.getId()+" now at time: "+time+" headed: "+vehicle.getDirection()+" "+vehicle);
		//System.out.println(vehicle.getId()+" Origin: "+vehicle.getOrigin()+" Destination: "+vehicle.getDestination()+" Direction: "+vehicle.getDirection()+
		//		" time in system: "+(time - vehicle.getSystemArrivalTime()));
		//System.out.println("ISDeparture vehicle id: "+vehicle.getId()+" time: "+time+" origin: "+vehicle.getOrigin()+" destination: "+vehicle.getDestination()+" direction: "+vehicle.getDirection()+" leaving intersection: "+is.getId()+" going "+northOrSouth.getOppositeDir()+" to intersection: "+nextIs.getId());
		System.out.printf("%-15s %-10s %-15s %-10s %-15s %-10s %-3s %-7s %-15s %-10s %-15s %-10s %-15s %-15s %-3s %-15s %-15s\n", "ISDeparture","intersection:",is.getId(),"time:",time,"vehicle id:",vehicle.getId(),"origin:",vehicle.getOrigin(),"destination:",vehicle.getDestination(),"direction:",vehicle.getDirection(),"leaving:",is.getId()," to intersection",nextIs.getId());
		return iqa;
		
	}
	/**
	 * Determines if car is traveling east or west
	 * @return true if car is traveling east or west
	 */
	private boolean sameLevel() {
		return is.getId().getValue()/10 == vehicle.getDestination().getValue()/10;
	}

}
