package simEngine;

import generalAlgos.PriorityQueue;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import simApp.Event;
import simApp.Peachtree;
import simApp.SimulationDone;
import simApp.SystemArrival;

/**
 * Event engine class holds the future event list and repeatedly extracts new events from the priority queue
 * for event processing
 * @author Holly
 *
 */
public class EventEngine{

	PriorityQueue<Event> futureEventList;
	SimulationDone test;
	
	public EventEngine(Comparator<Event> cmp, List<Event> list){

		futureEventList = new PriorityQueue<Event>(cmp);
		futureEventList.buildHeap(list);
		test = null;
	}		
	public EventEngine(Comparator<Event> cmp, List<Event> arrivals,SimulationDone test) {
		
		this( cmp,arrivals );
		this.test = test;
	}
	public void process(){
		
		
		boolean pressEnter = false;
		Scanner scan = null;
		
		if( pressEnter )
			scan = new Scanner(System.in); 
		
		while( !futureEventList.isEmpty()  ){
			

			Event event = futureEventList.extractMin();
			if( test!= null && test.done(event))
				break;
			Event next = event.event();
			
			if( next!= null )
				futureEventList.insert(next);
			if( pressEnter)
				scan.nextLine();
			
		}
	}

}
