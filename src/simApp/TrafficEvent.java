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
	protected int time;
	protected Peachtree peachtree;
	
	/**
	 * 
	 * @param vehicle vehicle associated with event
	 * @param time time event was created
	 * @param type type of event
	 */
	public TrafficEvent(Vehicle vehicle,int time, EventType type){
		
		this.vehicle = vehicle;
		this.time = time;
		this.type = type;
		peachtree = Peachtree.getInstance();
		
	}
	public abstract TrafficEvent event();
	

}
