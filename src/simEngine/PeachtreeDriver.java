package simEngine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import simApp.Event;
import simApp.EventComparator;
import simApp.Peachtree;
import simApp.TrafficEvent;

public class PeachtreeDriver {

	
	public static void main(String[] args) {
		
		Comparator<Event> cmp = new EventComparator(); 
		Peachtree.setup();
		List<? extends Event> arrivals = Peachtree.createArrivals();
		EventEngine engine = new EventEngine(cmp,(List<Event>) arrivals);
		engine.process();
		System.out.println("Done");
	}

}
