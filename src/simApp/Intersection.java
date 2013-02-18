package simApp;

import java.util.HashMap;


/**
 * Intersection class allows cars to enter and depart an intersection. Each intersection
 * has a traffic light, and the delay that a car will have at a light will be computed.
 * @author Holly Williams
 *
 */
public class Intersection {
	
	private HashMap<VehicleDirection, QueueInfo> intersectionQs;
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
		
		intersectionQs = new HashMap<VehicleDirection,QueueInfo>();
		int queuelen = 8;
		
		intersectionQs.put(VehicleDirection.NN, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.NE, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.NS, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.NW, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		
		intersectionQs.put(VehicleDirection.EN, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.EE, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.ES, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.EW, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		
		intersectionQs.put(VehicleDirection.SN, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.SE, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.SS, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.SW, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		
		
		intersectionQs.put(VehicleDirection.WN, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.WE, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.WS, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		intersectionQs.put(VehicleDirection.WW, new QueueInfo(new MyQueue<Vehicle>(),queuelen));
		
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
	public HashMap<VehicleDirection, QueueInfo> getIntersectionQs() {
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
		
		intersectionQs.get(direction).getQueue().add(vehicle);
	//	System.out.println("Addition: Queue size "+direction+" : "+intersectionQs.get(direction).size());
		
	}
/**
 * Remove the next vehicle in a certain direction's queue
 * @param direction direction that vehicle is turning
 * @return the next vehicle
 */
	public Vehicle removeFromQueue(VehicleDirection direction) {
		
		Vehicle v =  intersectionQs.get(direction).getQueue().remove();
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
public int getDelay(int time, VehicleDirection direction, Vehicle vehicle) {
	// TODO Auto-generated method stub
	return light.getDelay(time, direction,vehicle);
}
public int getProcessingTime(Vehicle vehicle) {
	return 5;
}
public TrafficLight getLight() {
	return light;
}
public Peachtree getPeachtree() {
	return peachtree;
}


	
}
