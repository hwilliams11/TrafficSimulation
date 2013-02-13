package simApp;



public class OldTrafficEvent {
	
	enum typeArrival{
		SYSTEM_ARRIVAL, SYSTEM_DEPARTURE, IS_ARRIVAL, IS_DEPARTURE,GENERAL
	}
	protected typeArrival type;
	protected Vehicle vehicle;
	protected int time;
	protected static Intersection [] intersections;
	
	public OldTrafficEvent(Vehicle vehicle,int time){
		type = typeArrival.GENERAL;
		this.vehicle = vehicle;
		this.time = time; 
		
	}
	public static void setIntersections(Intersection[]is){
		intersections = is;
	}
	public TrafficEvent event(){
		
		return null;
	}
	

}
