package simApp;

/**
 * Stopping condition for TrafficSimulation
 * @author Holly
 *
 */
public class TrafficSimulationDone extends SimulationDone {

	Peachtree pt;
	int end;
	
	public TrafficSimulationDone(){
		pt = Peachtree.getInstance();
		end = pt.getSIM_TIME_SECONDS();
	}
	public boolean done( Event event ){
		
		TrafficEvent tevent = (TrafficEvent)event;
	
		if( tevent.getTime() > end ){
			return true;
		}
		return false;
	}
}
