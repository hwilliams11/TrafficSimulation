package simApp;

import java.util.HashMap;


/**
 * Intersection class allows cars to enter and depart an intersection. Each intersection
 * has a traffic light, and the delay that a car will have at a light will be computed.
 * @author Holly Williams
 *
 */
public class Intersection {
	
	private HashMap<VehicleDirection, MyQueue<Vehicle>> intersectionQs;
	//private HashMap<VehicleDirection,Integer> lightTimes;
	private TrafficLight light;
	private HashMap<VehicleDirection,Double> delays;
	private double averageDelay;
	private PTIntersection id;
	private Peachtree peachtree;
	
	/**
	 * Creates an Intersection object at a particular intersection with traffic light times
	 * @param id id of intersection (10th,11th...)
	 * @param lightTimes traffic light times at this intersection
	 */
	public Intersection(PTIntersection id, HashMap<VehicleDirection,Integer> lightTimes){
		this.id = id;
		
		peachtree = Peachtree.getInstance();
		
		intersectionQs = new HashMap<VehicleDirection,MyQueue<Vehicle>>();
		
		intersectionQs.put(new VehicleDirection(Direction.NORTH,Direction.NORTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.NORTH,Direction.EAST), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.NORTH,Direction.SOUTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.NORTH,Direction.WEST), new MyQueue<Vehicle>());
		
		intersectionQs.put(new VehicleDirection(Direction.EAST, Direction.NORTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.EAST, Direction.EAST), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.EAST, Direction.SOUTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.EAST, Direction.WEST), new MyQueue<Vehicle>());
		
		intersectionQs.put(new VehicleDirection(Direction.SOUTH, Direction.NORTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.SOUTH, Direction.EAST), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.SOUTH, Direction.SOUTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.SOUTH, Direction.WEST), new MyQueue<Vehicle>());
		
		
		intersectionQs.put(new VehicleDirection(Direction.WEST,Direction.NORTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.WEST,Direction.EAST), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.WEST,Direction.SOUTH), new MyQueue<Vehicle>());
		intersectionQs.put(new VehicleDirection(Direction.WEST,Direction.WEST), new MyQueue<Vehicle>());
		
		//intitialize delays 
		delays = new HashMap<VehicleDirection,Double>();
		
		delays.put(new VehicleDirection(Direction.NORTH,Direction.NORTH), 0.0);
		delays.put(new VehicleDirection(Direction.NORTH,Direction.EAST), 0.0);
		delays.put(new VehicleDirection(Direction.NORTH,Direction.SOUTH), 0.0);
		delays.put(new VehicleDirection(Direction.NORTH,Direction.WEST), 0.0);
		
		delays.put(new VehicleDirection(Direction.EAST, Direction.NORTH), 0.0);
		delays.put(new VehicleDirection(Direction.EAST, Direction.EAST), 0.0);
		delays.put(new VehicleDirection(Direction.EAST, Direction.SOUTH), 0.0);
		delays.put(new VehicleDirection(Direction.EAST, Direction.WEST), 0.0);
		
		delays.put(new VehicleDirection(Direction.SOUTH, Direction.NORTH), 0.0);
		delays.put(new VehicleDirection(Direction.SOUTH, Direction.EAST), 0.0);
		delays.put(new VehicleDirection(Direction.SOUTH, Direction.SOUTH), 0.0);
		delays.put(new VehicleDirection(Direction.SOUTH, Direction.WEST), 0.0);
		
		
		delays.put(new VehicleDirection(Direction.WEST,Direction.NORTH), 0.0);
		delays.put(new VehicleDirection(Direction.WEST,Direction.EAST), 0.0);
		delays.put(new VehicleDirection(Direction.WEST,Direction.SOUTH), 0.0);
		delays.put(new VehicleDirection(Direction.WEST,Direction.WEST), 0.0);
		
		averageDelay=0;
		
		light = new TrafficLight(peachtree.getEndTime(),lightTimes);
	
		//setup traffic light times
		/*
		lightTimes = new HashMap<VehicleDirection,Integer>();
		
		lightTimes.put(new VehicleDirection(Direction.NORTH,Direction.NORTH), 0);
		lightTimes.put(new VehicleDirection(Direction.NORTH,Direction.EAST), 0);
		lightTimes.put(new VehicleDirection(Direction.NORTH,Direction.SOUTH), 0);
		lightTimes.put(new VehicleDirection(Direction.NORTH,Direction.WEST), 0);
		
		lightTimes.put(new VehicleDirection(Direction.EAST, Direction.NORTH), 0);
		lightTimes.put(new VehicleDirection(Direction.EAST, Direction.EAST), 0);
		lightTimes.put(new VehicleDirection(Direction.EAST, Direction.SOUTH), 0);
		lightTimes.put(new VehicleDirection(Direction.EAST, Direction.WEST), 0);
		
		lightTimes.put(new VehicleDirection(Direction.SOUTH, Direction.NORTH), 0);
		lightTimes.put(new VehicleDirection(Direction.SOUTH, Direction.EAST), 0);
		lightTimes.put(new VehicleDirection(Direction.SOUTH, Direction.SOUTH), 0);
		lightTimes.put(new VehicleDirection(Direction.SOUTH, Direction.WEST), 0);
		
		
		lightTimes.put(new VehicleDirection(Direction.WEST,Direction.NORTH), 0);
		lightTimes.put(new VehicleDirection(Direction.WEST,Direction.EAST), 0);
		lightTimes.put(new VehicleDirection(Direction.WEST,Direction.SOUTH), 0);
		lightTimes.put(new VehicleDirection(Direction.WEST,Direction.WEST), 0);
		*/
	}
	/**
	 *	 
	 * @return returns delays at intersection
	 */
	public HashMap<VehicleDirection, Double> getDelays() {
		return delays;
	}
	/**
	 * 
	 * @return returns the queues at the intersection
	 */
	public HashMap<VehicleDirection, MyQueue<Vehicle>> getIntersectionQs() {
		return intersectionQs;
	}
/**
 * 
 * @return returns the average delay at an intersection
 */
	public double getAverageDelay() {
		return averageDelay;
	}
/**
 * 
 * @return returns the id of the intersection
 */
	public PTIntersection getId() {
		return id;
	}

	public String toString() {
		return "Intersection [delays=" + delays + ", averageDelay="
				+ averageDelay + ", id=" + id + "]";
	}
/**
 * Adds a vehicle to a particular direction's queue
 * @param vehicle vehicle to add
 * @param direction direction that vehicle is going
 */
	public void addToQueue(Vehicle vehicle, VehicleDirection direction) {
		
		intersectionQs.get(direction).add(vehicle);
	//	System.out.println("Addition: Queue size "+direction+" : "+intersectionQs.get(direction).size());
		
	}
/**
 * Remove the next vehicle in a certain direction's queue
 * @param direction direction that vehicle is turning
 * @return the next vehicle
 */
	public Vehicle removeFromQueue(VehicleDirection direction) {
		
		Vehicle v =  intersectionQs.get(direction).remove();
	//	System.out.println("Removal: Queue size "+direction+" : "+intersectionQs.get(direction).size());
		return v;
	}
/**
 * Gets the time that a car will have to wait before light turns green
 * @param time	time that car entered queue
 * @param direction	direction that car is going
 * @return queue delay
 */
	public int getDelay(int time, VehicleDirection direction) {
		
		//System.out.println(light);
		return light.getDelay(time, direction);
	}
public int getProcessingTime(Vehicle vehicle) {
	return 5;
}


	
}
