package simApp;

/**
 * Abstract Event class
 * @author Holly
 *
 */
public abstract class Event {

	protected boolean active = true;
	/**
	 * 
	 * @return event state
	 */
	public boolean getActive(){
		return active;
	}
	/**
	 * Method to be implemented for a specific type of Event
	 * @return Event created
	 */
	public abstract Event event();
}
