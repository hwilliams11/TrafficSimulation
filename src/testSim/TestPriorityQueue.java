package testSim;
import generalAlgos.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class TestPriorityQueue {

	public static void main(String[]args){
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new IntComparator());
		final int NUM_VALS = 10;
		List<Integer> list2 = new ArrayList<Integer>();
		Random rand = new Random();
		
		
		for(int i=0;i<NUM_VALS;i++){
			int num = rand.nextInt(20);
			list2.add(num);
		}
		System.out.println(list2);
		pq.buildHeap(list2);
		Collections.sort(list2);
		
		/*
		System.out.println(pq);
		System.out.println("size: "+pq.size());
		Integer val = pq.deleteArbitrary(3);
		System.out.println(val);
		System.out.println("size: "+pq.size());
		System.out.println(pq);

		*/
		
		ArrayList<Integer> allVals = pq.extractAll();
		
		/*
		for( int i = 0;i< NUM_VALS & !pq.isEmpty();i++ ){
			int elem = pq.extractMin();
			
			if( list2.get(i)!= elem ){
				System.err.println("Error values do not match: "+list2.get(i)+" vs "+elem+" index: "+i);
				System.exit(1);
			}
		}*/
		
		for(int i=0;i<allVals.size();i++){
			
			if( allVals.get(i)!= list2.get(i) ){
				System.err.println("Error values do not match: "+list2.get(i)+" vs "+allVals.get(i)+" index: "+i);
				System.exit(1);
			}
			
		}
		System.out.println(list2);
		System.out.println( allVals );
		System.out.println("Success!");
	}
	
	
}
