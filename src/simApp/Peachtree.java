package simApp;

import generalAlgos.Exponential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



/**
 * Peachtree class sets up all the intersections and knows the layout of Peachtree street and all its crossings
 * It sets up the simulation and starts it.
 * @author Holly Williams
 *
 */
public class Peachtree {
	
	
	protected static final int NUM_CARS=20;
	protected static final double RATE = 1/8.0;
	private static final int END_TIME = (int) (NUM_CARS*(1/RATE))*3;
	private static int totalCars;
	private static double averageCarsInSystem;
	private static double averageDelay;
	private static int sumTimeInSystem;
	private static double averageTimeInSystem;
	private static HashMap<PTIntersection,Intersection> intersections;
	private final static String trafficLightTimesFile = "lightTimes.txt";
	public static void setup(){
		
		totalCars = 0;
		averageCarsInSystem = 0;
		averageDelay = 0;
		sumTimeInSystem = 0;
		averageTimeInSystem = 0;
		
		HashMap<PTIntersection,HashMap<VehicleDirection,Integer>> lightTimes = readLightTimes();
		intersections = new HashMap<PTIntersection,Intersection>();
		
		intersections.put(PTIntersection.TENTH, new Intersection(PTIntersection.TENTH,lightTimes.get(PTIntersection.TENTH)) );
		intersections.put(PTIntersection.ELEVENTH, new Intersection(PTIntersection.ELEVENTH,lightTimes.get(PTIntersection.ELEVENTH)) );
		intersections.put(PTIntersection.TWELFTH, new Intersection(PTIntersection.TWELFTH,lightTimes.get(PTIntersection.TWELFTH)) );
		intersections.put(PTIntersection.THIRTEENTH, new Intersection(PTIntersection.THIRTEENTH,lightTimes.get(PTIntersection.THIRTEENTH)) );
		intersections.put(PTIntersection.FOURTEENTH, new Intersection(PTIntersection.FOURTEENTH,lightTimes.get(PTIntersection.FOURTEENTH)) );
	}
	/**
	 * Reads the light times from a file
	 * @return map of traffic light times for each intersection in each direction
	 */
	private static HashMap<PTIntersection, HashMap<VehicleDirection, Integer>> readLightTimes() {
		
		HashMap<PTIntersection, HashMap<VehicleDirection, Integer>> hm = new HashMap<PTIntersection, HashMap<VehicleDirection, Integer>>();
		Scanner scan = null;
		try {
			scan = new Scanner( new File( trafficLightTimesFile));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		int street = 0;
		if( scan.hasNext() )
			scan.nextLine();
		while( scan.hasNext() ){
			HashMap<VehicleDirection,Integer> h = new HashMap<VehicleDirection,Integer>(); 
			
			//h.put( new VehicleDirection( Direction.EAST,Direction.NORTH), 0);
			//h.put( new VehicleDirection( Direction.WEST,Direction.SOUTH), 0);
			
			String line = scan.nextLine();
			String [] toks = line.split(",");
			int n = toks.length;
			for(int i=1;i<n;i++){
				int time = Integer.parseInt(toks[i]);
				switch( i ){
					case 1:{
						h.put(new VehicleDirection( Direction.NORTH,Direction.SOUTH),time);
						//h.put(new VehicleDirection( Direction.SOUTH,Direction.NORTH),time);
						break;
						}
					case 2:{
						h.put(new VehicleDirection( Direction.EAST,Direction.WEST),time);
						//h.put(new VehicleDirection( Direction.WEST,Direction.EAST),time);
						break;
						}
					case 3:{
						h.put(new VehicleDirection( Direction.NORTH,Direction.EAST),time);
						//h.put(new VehicleDirection( Direction.SOUTH,Direction.WEST),time);
						break;
						}
					case 4:{
						h.put(new VehicleDirection( Direction.WEST,Direction.NORTH),time);
						//h.put(new VehicleDirection( Direction.EAST,Direction.SOUTH),time);
						break;
					}
					default:
				}
				
			}
			PTIntersection intersection = null;
			
			switch( street ){
				case 0:{ intersection = PTIntersection.TENTH;break; }
				case 1:{ intersection = PTIntersection.ELEVENTH;break;}
				case 2:{ intersection = PTIntersection.TWELFTH;break;}
				case 3:{ intersection = PTIntersection.THIRTEENTH;break;}
				case 4:{ intersection = PTIntersection.FOURTEENTH;break;}
				default:{System.err.println("Too many streets");System.exit(0);}
			}
			
			hm.put(intersection, h);
			street++;
		}
		return hm;
	}
	/**
	 * Creates vehicles and a list of SystemArrivals
	 * @return returns a list of SystemArrival objects
	 */
	public static List<TrafficEvent> createArrivals(){
		
		
		ArrayList<TrafficEvent> arrivals = new ArrayList<TrafficEvent>();
		int currentTime = 0;
		
		for( int i=0; i<NUM_CARS; i++ ){
			currentTime += Exponential.expon(RATE);
			Vehicle v = Vehicle.randVehicle();
			//v.setOrigin(PTIntersection.ELEVENTH_WEST);
			//v.setDestination(PTIntersection.ELEVENTH_WEST);
			//System.out.println("Current time: "+currentTime);
			arrivals.add( new SystemArrival( v,currentTime) );
		}
		
		totalCars++;
		return arrivals;
	}
/**
 * 
 * @param pti intersection
 * @return returns the Intersection object at this intersection
 */
	public static Intersection getIntersection(PTIntersection pti ){
		
		return intersections.get(pti);
	}
	/**
	 * Finds the Peachtree intersection that a car has to cross next based on origin
	 * @param origin origin that a car enters at 
	 * @return the Peachtree intersection
	 */
	public static Intersection findIntersection(PTIntersection origin) {
		
		int val = origin.getValue();
		//System.out.println( origin+" "+origin.getValue());
		
		if( val < 20 ){	//tenth street intersection
			return intersections.get( PTIntersection.TENTH );
		}
		if( val < 30 ){	//eleventh street intersection
			return intersections.get( PTIntersection.ELEVENTH );
		}
		if( val < 40 ){	//twelfth street intersection
			return intersections.get( PTIntersection.TWELFTH );
		}
		if( val < 50 ){	//thirteenth street intersection
			return intersections.get( PTIntersection.THIRTEENTH );
		}
		else{			//fourteenth street intersection
			return intersections.get( PTIntersection.FOURTEENTH );
		}
	}
	/**
	 * Updates the statistics of the system an intersection after a departure
	 * @param is Intersection to update
	 * @param direction direction that car left
	 * @param time time that car departed
	 * @param vehicle Vehicle object that left system
	 */
	public static void updateStatistics(Intersection is,
			VehicleDirection direction, int time, Vehicle vehicle) {
		
		sumTimeInSystem += (time-vehicle.getSystemArrivalTime());
		
		
	}
	/**
	 * Based on the destination of a car and its current location finds the next Peachtree intersection
	 * that a car needs to drive to
	 * @param is Intersection that a car is currently at
	 * @param direction direction that a car is headed
	 * @return next intersection that a car needs to go to
	 */
	public static Intersection getNextIntersection(Intersection is,
			Direction direction) {
	
		PTIntersection pti = is.getId();
		
		if( direction == Direction.NORTH ){
			
			
			switch( pti ){
			
				case TENTH:{ return intersections.get(PTIntersection.ELEVENTH);}
				case ELEVENTH:{return intersections.get(PTIntersection.TWELFTH);}
				case TWELFTH:{return intersections.get(PTIntersection.THIRTEENTH);}
				case THIRTEENTH:{return intersections.get(PTIntersection.FOURTEENTH);}
			}
		}
		if( direction == Direction.SOUTH ){
			
			switch( pti ){
			
				case ELEVENTH:{return intersections.get(PTIntersection.TENTH);}
				case TWELFTH:{return intersections.get(PTIntersection.ELEVENTH);}
				case THIRTEENTH:{return intersections.get(PTIntersection.TWELFTH);}
				case FOURTEENTH:{return intersections.get(PTIntersection.THIRTEENTH);}
			}
		}
		return null;
	}
	/**
	 * Determines if the current intersection is north or south of the vehicle's destination
	 * 
	 * @param is Intersection that a vehicle is currently at
	 * @param vehicle vehicle object containing its destination
	 * @return Direction north or South
	 */
	public static Direction northOrSouthOfDest(Intersection is, Vehicle vehicle) {
		
		//System.out.println("Intersection: "+is.getId().getValue()+" Destination: "+vehicle.getDestination().getValue());
		return is.getId().northOrSouthOf(vehicle.getDestination());
		
	}
	/**
	 * Determines if the current intersection is east or west of the vehicle's destination
	 * @param is Intersection that a vehicle is currently at
	 * @param vehicle current vehicle
	 * @return Direction east or west
	 */
	public static Direction eastOrWestOfDest(Intersection is, Vehicle vehicle) {
		
		//System.out.println("dir: "+is.getId().eastOrWestOf( vehicle.getDestination()) );
		return is.getId().eastOrWestOf( vehicle.getDestination() );
	}
	/**
	 * 
	 * @param vehicle current vehicle
	 * @return determines the direction that a a vehicle is headed when it enters the system
	 */
	public static Direction vehicleEntry(Vehicle vehicle) {
		
		if( vehicle.getOrigin().getValue() %10 == 0 )//from west
			return Direction.WEST;
		if( vehicle.getOrigin().getValue() %10 == 2)//from east
			return Direction.EAST;
		if( vehicle.getOrigin() == PTIntersection.PEACHTREE_NORTH )
			return Direction.NORTH;
		if( vehicle.getOrigin() == PTIntersection.PEACHTREE_SOUTH )
			return Direction.SOUTH;
		System.err.println( "Can't enter from an intersection");
		System.exit(0);
		return null;
	}
	public static int getNumCars(){
		return NUM_CARS;
	}
	public static int getEndTime() {
		return END_TIME;
	}
	public static ArrayList<VehicleISInfo> computeRoute(Vehicle vehicle) {
		
		PTIntersection origin = vehicle.getOrigin();
		return null;
	}
}
