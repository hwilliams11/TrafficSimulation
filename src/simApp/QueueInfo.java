package simApp;

/**
 * Holds queue and its max length
 * @author Holly
 *
 */
public class QueueInfo {

	MyQueue<Vehicle> queue;
	int MAX_LEN;
	
	/**
	 * 
	 * @param queue 
	 * @param maxlen maxlen of the queue
	 */
	public QueueInfo( MyQueue<Vehicle> queue, int maxlen ){
		this.queue = queue;
		this.MAX_LEN = maxlen;
	}

	public MyQueue<Vehicle> getQueue() {
		return queue;
	}

	public void setQueue(MyQueue<Vehicle> queue) {
		this.queue = queue;
	}

	public int getMAX_LEN() {
		return MAX_LEN;
	}

	public void setMAX_LEN(int mAX_LEN) {
		MAX_LEN = mAX_LEN;
	}
}
