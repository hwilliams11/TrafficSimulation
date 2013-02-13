package simEngine;

import java.util.Comparator;
import java.util.List;
import simApp.Event;
import simApp.EventComparator;
import simApp.Peachtree;

public class PeachtreeDriver {

	
	public static void main(String[] args) {
		
		Comparator<Event> cmp = new EventComparator(); 
		Peachtree pt = Peachtree.getInstance();
		System.out.println(pt);
		List<? extends Event> arrivals = pt.createArrivals();
		EventEngine engine = new EventEngine(cmp,(List<Event>) arrivals);
		engine.process();
		System.out.println("Done");
	}

}
