package simApp;
import java.util.Comparator;

/**
 * Comparator that compares two events
 * @author Holly Williams
 *
 */
public class EventComparator implements Comparator<Event> {

	/**
	 * Events are compared by time
	 * @param arg0 first event
	 * @param arg1 second event
	 */
	public int compare(Event arg0, Event arg1) {
		
		double diff = ((TrafficEvent)arg0).time-((TrafficEvent)arg1).time;
		if( diff < 0 )
			return -1;
		else if(diff > 0 )
			return 1;
		else
			return 0;
	}

}
