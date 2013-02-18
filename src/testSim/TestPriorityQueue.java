package testSim;
import generalAlgos.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class TestPriorityQueue {

	public static void testInsert(){
		
		//for different N create a priority queue and insert each value into the queue
		//record the average insert time.
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>( new IntComparator() );
		Random rand = new Random();
		final int MAX_VALUE = 1000000;
		
		int [] nValues = {100,1000,10000,100000,1000000,10000000,20000000};
		double [] averageTimes = new double[nValues.length];
		
		//generate N random numbers and insert into the priority queu
		for( int i=0;i < nValues.length;i++ ){
			
			int N = nValues[i];
			//pq.clear();
			pq = new PriorityQueue<Integer>( new IntComparator() );
			
			long start = System.currentTimeMillis();
			for( int j=0;j<N;j++){
				
				int nextVal = rand.nextInt( MAX_VALUE );
				
				//System.out.println("J: "+j);
				pq.insert( nextVal );
				
				//averageTimes[i]+= (end-start);
			}
			//averageTimes[i] = averageTimes[i]/N;
			long end = System.currentTimeMillis();
			averageTimes[i] = (end-start);
		}
		for( int i=0;i<averageTimes.length;i++)
			System.out.println(nValues[i]+", "+averageTimes[i]);
	}
	public static void testCorrect(){
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
	public static void main(String[]args){

		testInsert();
	}
	
	
}
