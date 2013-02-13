package simEngine;

import generalAlgos.PriorityQueue;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import simApp.Event;
import simApp.Peachtree;
import simApp.SystemArrival;

/**
 * Event engine class holds the future event list and repeatedly extracts new events from the priority queue
 * for event processing
 * @author Holly
 *
 */
public class EventEngine{

	PriorityQueue<Event> futureEventList;
	
	public EventEngine(Comparator<Event> cmp, List<Event> list){

		futureEventList = new PriorityQueue<Event>(cmp);
		futureEventList.buildHeap(list);
	}		
	public void process(){
		
		
		boolean pressEnter = false;
		Scanner scan = null;
		
		if( pressEnter )
			scan = new Scanner(System.in);
		
		while( !futureEventList.isEmpty() ){
			
			
			Event event = futureEventList.extractMin();
			Event next = event.event();
			
			if( next!= null )
				futureEventList.insert(next);
			if( pressEnter)
				scan.nextLine();
			
		}
		System.out.println("END TIME: "+Peachtree.getEndTime());
	}

}
