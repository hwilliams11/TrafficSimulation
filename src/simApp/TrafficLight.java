package simApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import generalAlgos.RandomGenerator;

/**
 * Traffic Light class provides traffic light functionality for the program. It alters between different queues 
 * and  determines the delay based on the next item.  
 * @author Holly
 *
 */
public class TrafficLight {
	
ArrayList<TrafficTime> lightTimes;
PTIntersection id;

public TrafficLight(int endTime,HashMap<VehicleDirection,TrafficLightTimings> dirTimes, PTIntersection id){

	lightTimes = createTimes(endTime,dirTimes);
	this.id = id;
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
	
		int maxPedestrianDelay = 5;
		int perceptiontime = 4;
		if( vehicleDirection.rightTurn() ){
			
			/* Check for pedestrians */
			if(Pedestrians()){
				System.out.println("Pedestrians Crossing !!!!! Right turn delayed!");
				RandomGenerator r = new RandomGenerator();
				return (int)(maxPedestrianDelay*r.xorrandDouble());
			}
			return 0;
		}
		int ind = Collections.binarySearch(lightTimes, new TrafficTime(myTime,null));
		//System.out.println("ind: "+ind);
		if( ind < 0 ){
			ind = -1*ind-1;
			if( ind > 0 )
				ind--;
		}
		//System.out.println("previous time: "+lightTimes.get(ind));
		//System.out.println("ind: "+ind);
		TrafficTime t = lightTimes.get(ind);
		if( canGo(t,vehicleDirection) ){
			//System.out.println("At time: "+myTime+" Go!!!");
			
			if(vehicleDirection.leftTurn()){
				
				/* Check for Pedestrians */
				if(Pedestrians()){
					System.out.println("Pedestrians Crossing !!!!!");
					RandomGenerator r = new RandomGenerator();
					return (int)(maxPedestrianDelay*r.xorrandDouble());
				}
			
				/**
				 * Intersections 11 and 12 have no dedicated left turn lights
				 * In those cases, we choose a random number of cars which can pass through from
				 * the other side before the vehicle can make a left turn
				 */
				if(this.id == PTIntersection.ELEVENTH || this.id == PTIntersection.TWELFTH){
					
					System.out.println("Left turns on 11th or 12th street. Wait for some cars to pass!");
					Peachtree pt = Peachtree.getInstance();
					int qsize = pt.getIntersection(id).getQueueSize(vehicleDirection);
					RandomGenerator r = new RandomGenerator();
					int numcarspassed = (int)(qsize*r.xorrandDouble());
					
					Iterator<Vehicle> itr = pt.getIntersection(id).getQueue(vehicleDirection).iterator();
					int index = 0;
					while(index < numcarspassed && itr.hasNext()){
						index = index+1;
					}
					Vehicle v = itr.next();
					double delay;
					if( v.getDelays().size()>0 ){
						delay = v.getDelays().getLast();
					}
					else{
						delay = 0;
					}
					int crossingdelay = (int)delay;
					
					int pdelay = 0;
					if(Pedestrians()){
						System.out.println("Pedestrians Crossing !!!!!");
						pdelay = (int)(maxPedestrianDelay*r.xorrandDouble());
					}
					
					// Add function to propagate delay here
					return pdelay+crossingdelay;
				}
			}
			return perceptiontime;
		}
		else{
			int nextTime = findNextGo( myTime,ind,vehicleDirection );
			System.out.println("At time: "+myTime+" Gotta wait "+vehicleDirection+" delay="+(nextTime-myTime));
			return nextTime - myTime;
		}
		
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
	public static ArrayList<TrafficTime> createTimes(int endTime,HashMap<VehicleDirection, TrafficLightTimings> dirTimes){
		
		int currTime = 0;
		//System.out.println("createTimes initialized");
		
		ArrayList<TrafficTime> traffTimes = new ArrayList<TrafficTime>();
		int ind = 0;
		
		VehicleDirection [] dirKeys = new VehicleDirection[dirTimes.size()];// = (VehicleDirection[]) dirTimes.keySet().toArray();
		dirTimes.keySet().toArray(dirKeys);
		
		while( currTime < endTime ){
			
			VehicleDirection direction = dirKeys[ind];
			traffTimes.add( new TrafficTime( currTime,direction));
			if( ++ind == dirTimes.size())
				ind = 0;
			currTime += (dirTimes.get(direction).greentime+dirTimes.get(direction).yellowtime);
			//System.out.println(currTime);
		}
//		for(TrafficTime t:traffTimes)
//		{
//			System.out.println(t);
//		}
		return traffTimes;
	}
	public String toString(){
		String s="";
		for( TrafficTime t: lightTimes )
			s+=t.toString()+"\n";
		return s;
	}
	
	/* Function to check if there are any pedestrians crossing or not */
	public boolean Pedestrians(){
		
		RandomGenerator rand = new RandomGenerator();
		double a = rand.xorrandDouble();
		Peachtree pt = Peachtree.getInstance();
		double prob = pt.getIntersection(id).getPedestrianProb();
		if(a < prob)
			return true;
		else
			return false;
	}
}
