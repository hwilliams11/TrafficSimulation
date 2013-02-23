package generalAlgos;

import java.util.*;

import testSim.IntComparator;

/**
 * Heap based Priority Queue
 *
 * @param <AnyType>
 */

public class PriorityQueue<AnyType> {
	
	private ArrayList<AnyType> pq;
	private Comparator<AnyType> cmp;
	private final static int DEFAULT_SIZE = 20;
	int size;
	
	public PriorityQueue(Comparator<AnyType> cmp){
		this(DEFAULT_SIZE,cmp);
	}
	public PriorityQueue(int size,Comparator<AnyType> cmp){
		this.cmp = cmp;
		//pq = new ArrayList<AnyType>(size);
		pq = new ArrayList<AnyType>(size);
		pq.add( null );
		size = 0;
	}
	/**
	 * Builds heap from a list of elements
	 * @param elements list of items to insert
	 */
	public void buildHeap(List<AnyType> elements ){
	
		int lastIndex;
		
		for( AnyType elem: elements){
			pq.add(elem);
			lastIndex = ++size;
			bubbleUp(lastIndex);
		}
	}
	/**
	 * Insert item into priority queue
	 * @param item item to insert
	 */
	public void insert(AnyType item){
		pq.add(item);
		bubbleUp(++size);
	}
	/**
	 * Finds the minimum value
	 * @return minimum value
	 */
	public AnyType extractMin(){
		
		AnyType result = pq.get(1);
		pq.set(1, pq.get(size));
		pq.remove(size--);
        bubbleDown(1);
        return result;
	}
	/**
	 * Restore heap property on the way up
	 * @param index index to start at
	 */
	private void bubbleUp (int index) {
        pq.set(0, pq.get(index));  // Acts as sentinal so we don't fall off the array
        int parent = index / 2;
        while (cmp.compare(pq.get(parent), pq.get(index)) > 0) {
            AnyType temp = pq.get(parent);
            pq.set(parent, pq.get(index));
            pq.set(index, temp);
            index = parent;
            parent = index / 2;
        }
    }
	/**
	 * Restore heap property on the way down
	 * @param index index to start at
	 */
	private void bubbleDown (int index) {
		
        while (true) {
            int left = 2 * index;
            if (left > size) break;
            int right = left + 1;
            int min = right;
            
            if (right > size || cmp.compare(pq.get(left), pq.get(right)) <= 0) 
            	min = left;
            if (cmp.compare(pq.get(index), pq.get(min)) <= 0) 
            	break;
            
            AnyType temp = pq.get(index);
            pq.set(index, pq.get(min));
            pq.set(min, temp);
            index = min;
        }

    }
	/**
	 * Delete a particular element in the priority queue
	 * @param elem element to delete
	 * @return the element found in the priority queue
	 */
	public AnyType deleteArbitrary(AnyType elem){
		
		int j = 0;
		while( cmp.compare(pq.get(j),elem)!=0 && j!=size ){
			
			j++;
		}
		if( j==size ){
			System.out.println("Value not found");
			return null;
		}
		else{
			AnyType found  = pq.get(j);
			pq.set(j, pq.get(size));
			pq.remove(size--);
			bubbleDown(j);
			return found;
		}
		
	}
	public String toString(){
		String s = "";
		for( int i=1;i<pq.size();i++){
			s+=pq.get(i)+" ";
		}
		return s;
	}
	public int size() {
		return size;
	}
	/**
	 * Test if the priority queue is empty
	 * @return true/false
	 */
	public boolean isEmpty() {
		return size==0;
	}
	/**
	 * Get all elements from queue
	 * @return sorted list of the elements in the queue.
	 */
	public ArrayList<AnyType> extractAll() {
		
		ArrayList<AnyType>list = new ArrayList<AnyType>();
		while( !isEmpty() ){
			AnyType val = extractMin();
			System.out.println("Foo: "+size+" val: "+val);
			list.add(val);
		}
		return list;
	}
	/**
	 * Remove itemse from priority queue
	 */
	public void clear(){
		pq.clear();
		pq.add( null );
		size=0;
	}
}
