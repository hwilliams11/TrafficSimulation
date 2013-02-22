package simApp;

import java.util.HashMap;


/**
 * Intersection class allows cars to enter and depart an intersection. Each intersection
 * has a traffic light, and the delay that a car will have at a light will be computed.
 * @author Holly Williams
 *
 */
public class Intersection {
	
	private static Double DEFAULT_PROCTIME = 5.0;
	private HashMap<VehicleDirection, QueueInfo> intersectionQs;
	//private HashMap<VehicleDirection,Integer> lightTimes;
	private TrafficLight light;
	private double averageDelay;
	private PTIntersection id;
	private Peachtree peachtree;
	private double PedestrianProb;
	private double processingTime;

	
	/**
	 * Creates an Intersection object at a particular intersection with traffic light times
	 * @param id id of intersection (10th,11th...)
	 * @param lightTimes traffic light times at this intersection
	 */
	public Intersection(PTIntersection id, HashMap<VehicleDirection,TrafficLightTimings> lightTimes){
		
		this( id, lightTimes, DEFAULT_PROCTIME  );
	}
	public Intersection(PTIntersection id,HashMap<VehicleDirection, TrafficLightTimings> lightTimes,Double processingTime) {
		
		this.id = id;
		
		peachtree = Peachtree.getInstance();
		
		intersectionQs = new HashMap<VehicleDirection,QueueInfo>();
		int queuelen = 3;
		
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
		
		averageDelay=0;
		
		light = new TrafficLight(peachtree.getEndTime(),lightTimes,id);
	
		if(id == PTIntersection.TENTH)
			this.PedestrianProb = 0.7;
		else if(id == PTIntersection.ELEVENTH)
			this.PedestrianProb = 0.5;
		else if(id == PTIntersection.TWELFTH)
			this.PedestrianProb = 0.4;
		else if(id == PTIntersection.THIRTEENTH)
			this.PedestrianProb = 0.3;
		else if(id == PTIntersection.FOURTEENTH)
			this.PedestrianProb = 0.8;
		else
			this.PedestrianProb = 0.0;
		
		this.processingTime = processingTime;
	}
	/**
	 * 
	 * @return returns the id of the intersection
	 */
		public PTIntersection getId() {
			return id;
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
 * Gets the time that a car will have to wait before light turns green
 * @param time	time that car entered queue
 * @param direction	direction that car is going
 * @return queue delay
 */
public double getDelay(double time, VehicleDirection direction) {
		
		//System.out.println(light);
		return light.getDelay(time, direction);
}
public double getProcessingTime(Vehicle vehicle) {
	return processingTime+1;
}
public TrafficLight getLight() {
	return light;
}
public Peachtree getPeachtree() {
	return peachtree;
}
public double getPedestrianProb(){
	
	return this.PedestrianProb; 
}
public int getQueueSize(VehicleDirection direction){
	
	return intersectionQs.get(direction).getQueue().size();
}
public MyQueue<Vehicle> getQueue(VehicleDirection direction){
	
	return intersectionQs.get(direction).getQueue();
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
public String toString() {
	return "Intersection [averageDelay="
			+ averageDelay + ", id=" + id + "]";
}


	
}
