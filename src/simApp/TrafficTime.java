package simApp;

/**
 * TrafficTime class holds information about the length of a traffic light in a certain direction
 * @author Holly
 *
 */
public class TrafficTime implements Comparable<TrafficTime>{
	
	int time;
	VehicleDirection direction;
	
	/**
	 * @param time time that the light is green
	 * @param direction direction of green light
	 */
	public TrafficTime( int time, VehicleDirection direction){
		this.time = time;
		this.direction = direction;
	}
	public String toString(){
		return time+": "+direction;
	}
	@Override
	public int compareTo(TrafficTime arg0) {
		return time - arg0.time;
	}
	
}