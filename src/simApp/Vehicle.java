package simApp;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Vehicle class holding all the information about a vehicle
 * @author Holly Williams
 *
 */
public class Vehicle {
	
	private int id;
	private int systemArrivalTime;
	private PTIntersection origin;
	private PTIntersection destination;
	private int timeInSystem;
	private VehicleDirection direction;
	private LinkedList<Double>delays;
	private static int vehicleIds = 0;
	private static int vehicleTime = 0;
	private Queue<VehicleISInfo> route;
	private boolean aggressive;
	
	public Vehicle(int id, int systemArrivalTime, PTIntersection origin, PTIntersection destination) {
		this.id = id;
		this.systemArrivalTime = systemArrivalTime;
		this.origin = origin;
		this.destination = destination;
		route = new MyQueue<VehicleISInfo>();
		delays = new LinkedList<Double>();
	}
/**
 * Creates a random vehicle and its entry point and destination
 * @return new random vehicle
 */
	public static Vehicle randVehicle(){
		
	
		PTIntersection orig = getRandomEntryExit();
		PTIntersection dest = getRandomEntryExit();
		return new Vehicle( vehicleIds++,vehicleTime++, orig, dest );
	}
/**
 * Creates a random entry or exit can come from any entry point into Peachtree
 * @return random intersection
 */
	private static PTIntersection getRandomEntryExit() {
		
		Random rand = new Random();
		int randNum = rand.nextInt(12);
		
		switch( randNum ){
		
		case 0 :{ return PTIntersection.TENTH_EAST;}
		case 1 :{return PTIntersection.TENTH_WEST;}
		case 2 :{return PTIntersection.PEACHTREE_SOUTH;}
		case 3 :{return PTIntersection.ELEVENTH_EAST;}
		case 4 :{return PTIntersection.ELEVENTH_WEST;}
		case 5 :{return PTIntersection.TWELFTH_EAST;}
		case 6 :{return PTIntersection.TWELFTH_WEST;}
		case 7 :{return PTIntersection.THIRTEENTH_EAST;}
		case 8 :{return PTIntersection.THIRTEENTH_WEST;}
		case 9 :{return PTIntersection.FOURTEENTH_EAST;}
		case 10:{return PTIntersection.FOURTEENTH_WEST;}
		case 11:{return PTIntersection.PEACHTREE_NORTH;}
		}
		return null;
	}
/**
 * 
 * @return car id
 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @return time that car arrived into system
	 */
	public int getSystemArrivalTime() {
		return systemArrivalTime;
	}
	/**
	 * 
	 * @return origin of car
	 */
	public PTIntersection getOrigin() {
		return origin;
	}
	/**
	 * 
	 * @return destination of car
	 */
	public PTIntersection getDestination() {
		return destination;
	}
	/**
	 * 
	 * @return time that a car has been in the system
	 */
	public int getTimeInSystem() {
		return timeInSystem;
	}
	public Queue<VehicleISInfo> getRoute() {
		return route;
	}
	/**
	 * 
	 * @return direction that vehicle is traveling
	 */
	public VehicleDirection getDirection() {
		return direction;
	}
	/**
	 * Set vehicle id
	 * @param id new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Set time of arrival into system
	 * @param systemArrivalTime new time
	 */
	public void setSystemArrivalTime(int systemArrivalTime) {
		this.systemArrivalTime = systemArrivalTime;
	}
	/**
	 * Set origin of vehicle
	 * @param origin new origin
	 */
	public void setOrigin(PTIntersection origin) {
		this.origin = origin;
	}
	/**
	 * Set destination of vehicle
	 * @param destination new destination
	 */
	public void setDestination(PTIntersection destination) {
		this.destination = destination;
	}
	/**
	 * Sets the vehcile's time in system
	 * @param timeInSystem new time
	 */
	public void setTimeInSystem(int timeInSystem) {
		this.timeInSystem = timeInSystem;
	}
	/**
	 * Set the direction that a car is traveling
	 * @param direction
	 */
	public void setDirection(VehicleDirection direction) {
		this.direction = direction;
	}
	public boolean isAggressive() {
		return aggressive;
	}
	public void setAggressive(boolean aggressive) {
		this.aggressive = aggressive;
	}
	public LinkedList<Double> getDelays() {
		return delays;
	}
	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", directionComingFrom="
				+ direction + ", origin=" + origin + ", destination="
				+ destination + ", systemArrivalTime=" + systemArrivalTime
				 + ", timeInSystem=" + timeInSystem+ "]";
	}	
	
}
