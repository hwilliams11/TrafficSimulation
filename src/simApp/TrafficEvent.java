package simApp;

/**
 * Base TrafficEvent class
 * @author Holly
 *
 */
public abstract class TrafficEvent extends Event{
	
	enum EventType{
		SYSTEM_ARRIVAL, SYSTEM_DEPARTURE, IS_ARRIVAL, IS_Q_ARRIVAL, IS_DEPARTURE
	}
	protected EventType type;
	protected Vehicle vehicle;
	protected double time;
	protected Peachtree peachtree;
	protected boolean active;
	
	/**
	 * 
	 * @param vehicle vehicle associated with event
	 * @param time time event was created
	 * @param type type of event
	 */
	public TrafficEvent(Vehicle vehicle,double time, EventType type){
		
		this.vehicle = vehicle;
		this.time = time;
		this.type = type;
		peachtree = Peachtree.getInstance();
		
		
	}
	/**
	 * To be implemented by child classes
	 */
	public abstract TrafficEvent event();
	public EventType getType() {
		return type;
	}
	/**
	 * 
	 * @return vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	/**
	 * 
	 * @return time
	 */
	public double getTime() {
		return time;
	}
	

}
