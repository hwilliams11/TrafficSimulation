package simApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Traffic Light class provides traffic light functionality for the program. It alters between different queues 
 * and  determines the delay based on the next item.  
 * @author Holly
 *
 */
public class TrafficLight {
	
ArrayList<TrafficTime> lightTimes;

public ArrayList<TrafficTime> getLightTimes() {
	return lightTimes;
}
public void setLightTimes(ArrayList<TrafficTime> lightTimes) {
	this.lightTimes = lightTimes;
}
public TrafficLight(int endTime,HashMap<VehicleDirection,Integer> dirTimes){

	lightTimes = createTimes(endTime,dirTimes);
	//System.out.println(this);
}
/**
 * getDelay computes the time a vehicle must wait before it can move towards the intersection
 * This number is computed based on the traffic light timings of a particular intersection
 * 
 * @param myTime  current time that a vehicle enters a queue
 * @param vehicleDirection  the direction that the vehicle is trying to turn
 * @return  the time a vehicle must wait before its queues turn 
 */
public int getDelay(int myTime, VehicleDirection vehicleDirection) {
	
	
		Peachtree pt = Peachtree.getInstance();
		if( pt.getIntersection(PTIntersection.THIRTEENTH).getLight() == this ){
			
		}
		if( vehicleDirection.rightTurn() )
			return 0;
		int ind = Collections.binarySearch(lightTimes, new TrafficTime(myTime,null));
		//System.out.println("ind: "+ind);
		if( ind < 0 ){
			ind = -1*ind-1;
			if( ind > 0 )
				ind--;
		}
		//System.out.println("ind: "+ind);
		TrafficTime t = lightTimes.get(ind);
		if( canGo(t,vehicleDirection) ){
			//System.out.println("At time: "+myTime+" Go!!!");
			return 0;
		}
		else{
			int nextTime = findNextGo( myTime,ind,vehicleDirection );
			//System.out.println("At time: "+myTime+" Gotta wait "+vehicleDirection+" delay="+(nextTime-myTime));
			return nextTime - myTime;
		}
		
	}
public int getDelay(int myTime, VehicleDirection vehicleDirection, Vehicle v) {
	
	
	if( vehicleDirection.rightTurn() )
		return 0;
	int ind = Collections.binarySearch(lightTimes, new TrafficTime(myTime,null));
	//System.out.println("ind: "+ind);
	if( ind < 0 ){
		ind = -1*ind-1;
		if( ind > 0 )
			ind--;
	}
	//System.out.println("ind: "+ind);
	TrafficTime t = lightTimes.get(ind);
	if( canGo(t,vehicleDirection) ){
		//System.out.println("At time: "+myTime+" Go!!!");
		return 0;
	}
	else{
		int nextTime = findNextGo( myTime,ind,vehicleDirection, v );
		//System.out.println("At time: "+myTime+" Gotta wait "+vehicleDirection+" delay="+(nextTime-myTime));
		return nextTime - myTime;
	}
	
}
private int findNextGo(int myTime, int ind, VehicleDirection vehicleDirection,
		Vehicle v) {
	
	
	return 0;
}
/**
 * Determines the next timestamp that this vehicle will be able to move in its queue
 * @param myTime - vehicle time
 * @param ind	- index of where time would be placed in the traffic time list
 * @param vehicleDirection	- direction to go
 * @return	- returns time of the next light that this vehicle will be able to move with
 */
	private int findNextGo(int myTime, int ind, VehicleDirection vehicleDirection) {
	
		for(int i=ind;i<lightTimes.size();i++){
			if( canGo(lightTimes.get(i),vehicleDirection))
					return lightTimes.get(i).time;
		}
		System.out.println("***************************");
		System.out.println("Error after simulation time");
		System.out.println(vehicleDirection+" time: "+myTime);
		System.out.println(this);
		System.out.println("***************************");
		System.out.println("Error after simulation time");
		System.exit(0);
	return -300;
}
	/**
	 * Determines if a light is green for a vehicle in its direction
	 * @param trafficTime current trafficTime object
	 * @param vehicleDirection direction that the vehicle goes
	 * @return boolean signaling if a car can go
	 */
	private boolean canGo(TrafficTime trafficTime,
			VehicleDirection vehicleDirection) {
		
		if( trafficTime.direction.equals((vehicleDirection)) ){
			return true;
		}
		if( trafficTime.direction.opposite( vehicleDirection )){
			return true;
		}
		VehicleDirection traffDir = trafficTime.direction;
		
		//WW, EE, SS,NN u turn
		if( vehicleDirection.uturn() ){
			
			switch( vehicleDirection.getFrom() ){
			case NORTH:{ if( traffDir.sameDir(VehicleDirection.NE))return true; }//can make u turn when this lane is green
			case EAST :{ if( traffDir.sameDir(VehicleDirection.ES))return true;}
			case SOUTH:{ if( traffDir.sameDir(VehicleDirection.SW))return true;}
			case WEST :{ if( traffDir.sameDir(VehicleDirection.WN))return true;}
			default: return false;
			}
		}
		return false;
	}
	/**
	 * Creates a list of times and sets which direction can proceed and at what time
	 * @param endTime last timestamp
	 * @param dirTimes contains (direction,time) pairs of the traffic light times
	 * @return list of trafficTime objects
	 */
	public static ArrayList<TrafficTime> createTimes(int endTime,HashMap<VehicleDirection, Integer> dirTimes){
		
		int currTime = 0;
		
		
		ArrayList<TrafficTime> traffTimes = new ArrayList<TrafficTime>();
		int ind = 0;
		
		VehicleDirection [] dirKeys = new VehicleDirection[dirTimes.size()];// = (VehicleDirection[]) dirTimes.keySet().toArray();
		dirTimes.keySet().toArray(dirKeys);
		
		while( currTime < endTime ){
			
			VehicleDirection direction = dirKeys[ind];
			traffTimes.add( new TrafficTime( currTime,direction));
			if( ++ind == dirTimes.size())
				ind = 0;
			currTime += dirTimes.get(direction);
			
		}
		return traffTimes;
	}
	public String toString(){
		String s="";
		for( TrafficTime t: lightTimes )
			s+=t.toString()+"\n";
		return s;
	}
}
