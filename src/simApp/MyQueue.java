package simApp;
import java.util.Collection;
import java.util.Iterator;
import java.util.*;

/**
 * LinkedList Queue implementation
 * @author Holly
 *
 * @param <AnyType>
 */
public class MyQueue<AnyType> implements Queue<AnyType>{

	private LinkedList<AnyType> queue;
	
	public MyQueue(){
		queue = new LinkedList<AnyType>();
	}
	public boolean addAll(Collection<? extends AnyType> arg0) {
		// TODO Auto-generated method stub
		return queue.addAll(arg0);
	}
	public void clear() {
		// TODO Auto-generated method stub
		queue.clear();
	}

	
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return queue.contains(arg0);
	}

	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return queue.containsAll(arg0);
	}

	
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return queue.isEmpty();
	}

	
	public Iterator<AnyType> iterator() {
		// TODO Auto-generated method stub
		return queue.iterator();
	}

	
	public boolean remove(Object arg0) {
		
		return queue.remove(arg0);
	}

	
	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return queue.removeAll(arg0);
	}

	
	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return queue.retainAll(arg0);
	}

	
	public int size() {
		// TODO Auto-generated method stub
		return queue.size();
	}

	
	public AnyType[] toArray() {
		// TODO Auto-generated method stub
		return (AnyType[]) queue.toArray();
	}

	
	public AnyType[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return (AnyType[])queue.toArray(arg0);
	}

	public boolean add(AnyType arg0) {
		// TODO Auto-generated method stub
		return queue.add(arg0);
	}

	
	public AnyType element() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean offer(AnyType arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public AnyType peek() {
		// TODO Auto-generated method stub
		return queue.getFirst();
	}

	
	public AnyType poll() {
		
		AnyType item = queue.removeFirst();
		return item;
	}

	
	public AnyType remove() {
		// TODO Auto-generated method stub
		return queue.removeFirst();
	}
	@Override
	public String toString() {
		return "MyQueue [queue=" + queue + "]";
	}
}
