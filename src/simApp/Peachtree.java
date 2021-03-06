package simApp;

import generalAlgos.Exponential;
import generalAlgos.RandomGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.math3.distribution.GammaDistribution;



/**
 * Peachtree class sets up all the intersections and knows the layout of Peachtree street and all its crossings
 * It sets up the simulation and starts it.
 * @author Holly Williams
 *
 */
public class Peachtree {
	
	
	
	private static Peachtree instance = null;
	private static boolean writeToFile = false;
	private TrafficStatistics stats;
	private PeachtreeSimOutput simOutput;
	private String outputFilename = "TrafficSimulationOutput.txt";
	protected  final double RATE = 1/8.0;
	private final double AGGRESSIVENESS_PROB = .08;
	private int totalCars;
	private int [][] originDestinationData;
	private HashMap<Integer, OriginInfo> originDestinationMap;
	private HashMap<PTIntersection,Intersection> intersections;
	private HashMap<Integer, Double> originRates;
	private static  int NUM_BATCHES;
	public static final int TWO_HOURS = 7200;
	private static int SIM_TIME_SECONDS;
	private static int END_TIME;
	private final  String trafficLightTimesFile = "lightTimes.csv";
	private final  String originDestinationDataFile = "originDestinationDistributionData.csv";
	private boolean useGamma = true;
	private final String gammaDataFile = "gammaTrafficSim.csv"; 
	private final int MULT_15MIN = 8;
	private HashMap<PTIntersection,Double[]> gammaData;
	private String processingFilename = "serverProcessingTime.csv";
	private static HashMap<Integer,Integer> simOriginMappings;
	
	private Peachtree(){
		
		totalCars = 0;
		stats = new TrafficStatistics();
		if( writeToFile ){

			simOutput = new PeachtreeSimOutput( outputFilename ); 
		}
		else{
			simOutput = new PeachtreeSimOutput( System.out );
		}
		simOriginMappings = new HashMap<Integer,Integer>();
		simOriginMappings.put(50, 115);
		simOriginMappings.put(30, 121);
		simOriginMappings.put(20, 122);
		simOriginMappings.put(10, 123);
		simOriginMappings.put(12, 102);
		simOriginMappings.put(22, 103);
		simOriginMappings.put(32, 111);
		simOriginMappings.put(42, 112);
		simOriginMappings.put(52, 113);
		simOriginMappings.put( 1, 101);
		simOriginMappings.put(61, 114);
		
		NUM_BATCHES = PeachtreeDriver.NUM_BATCHES;
		SIM_TIME_SECONDS = NUM_BATCHES*TWO_HOURS;
		END_TIME = NUM_BATCHES*10000;
		
	}
	/**
	 * Read the arrival event probability distribution data and save
	 */
	private void setup(){
		
		if( useGamma )
			setupGamma();
		else
			setupExpon();
	}
	/**
	 * Read data files and create data for using the exponential distribution
	 */
	private void setupExpon(){
		
		HashMap<PTIntersection,HashMap<VehicleDirection,TrafficLightTimings>> lightTimes = readLightTimes();
		HashMap<PTIntersection,Double> processingTimes = readProcessingTimes();
		
		intersections = new HashMap<PTIntersection,Intersection>();		

		intersections.put(PTIntersection.TENTH, new Intersection(PTIntersection.TENTH,lightTimes.get(PTIntersection.TENTH),processingTimes.get(PTIntersection.TENTH)) );
		intersections.put(PTIntersection.ELEVENTH, new Intersection(PTIntersection.ELEVENTH,lightTimes.get(PTIntersection.ELEVENTH),processingTimes.get(PTIntersection.ELEVENTH)) );
		intersections.put(PTIntersection.TWELFTH, new Intersection(PTIntersection.TWELFTH,lightTimes.get(PTIntersection.TWELFTH),processingTimes.get(PTIntersection.TWELFTH)) );
		intersections.put(PTIntersection.THIRTEENTH, new Intersection(PTIntersection.THIRTEENTH,lightTimes.get(PTIntersection.THIRTEENTH),processingTimes.get(PTIntersection.THIRTEENTH)) );
		intersections.put(PTIntersection.FOURTEENTH, new Intersection(PTIntersection.FOURTEENTH,lightTimes.get(PTIntersection.FOURTEENTH),processingTimes.get(PTIntersection.FOURTEENTH)) );
		
	}
	
	private HashMap<PTIntersection, Double> readProcessingTimes() {
		
		HashMap<PTIntersection, Double> time = new HashMap<PTIntersection, Double>();
		
		Scanner scan = null;
		
		try {
			scan = new Scanner( new File(processingFilename) );
		} catch (FileNotFoundException e) {e.printStackTrace();	}
		
		while( scan.hasNext() ){
			String line = scan.nextLine();
			String []tokens = line.split(",");
			
			int id = Integer.parseInt( tokens[0] );
			PTIntersection is = PTIntersection.getPTIntersection(id);
			time.put(is, Double.parseDouble(tokens[1]));
		}
		
		return time;
	}
	private void setupGamma(){
		
		HashMap<PTIntersection,HashMap<VehicleDirection,TrafficLightTimings>> lightTimes = readLightTimes();
		gammaData = new HashMap<PTIntersection,Double[]>();
		intersections = new HashMap<PTIntersection,Intersection>();
		
		Scanner scan = null;
		try {
			scan = new Scanner( new File(gammaDataFile) );
		} catch (FileNotFoundException e) {e.printStackTrace();	}
		
		if( scan.hasNext() )
			scan.nextLine();
		
		while( scan.hasNext() ){
			String line = scan.nextLine();
			String [] toks = line.split(",");
			
			int origin = Integer.parseInt( toks[0] );
			double shape = Double.parseDouble( toks[1] );
			double scale = Double.parseDouble( toks[2] );
			PTIntersection ptiOrigin = PTIntersection.getPTIntersection( origin );
			Double [] shapeScale = new Double[2];
			shapeScale[0] = shape;
			shapeScale[1] = scale;
			gammaData.put( ptiOrigin, shapeScale );
			
			
		}				
		
		intersections.put(PTIntersection.TENTH, new Intersection(PTIntersection.TENTH,lightTimes.get(PTIntersection.TENTH)));
		intersections.put(PTIntersection.ELEVENTH, new Intersection(PTIntersection.ELEVENTH,lightTimes.get(PTIntersection.ELEVENTH)));
		intersections.put(PTIntersection.TWELFTH, new Intersection(PTIntersection.TWELFTH,lightTimes.get(PTIntersection.TWELFTH)));
		intersections.put(PTIntersection.THIRTEENTH, new Intersection(PTIntersection.THIRTEENTH,lightTimes.get(PTIntersection.THIRTEENTH)));
		intersections.put(PTIntersection.FOURTEENTH, new Intersection(PTIntersection.FOURTEENTH,lightTimes.get(PTIntersection.FOURTEENTH)));
		
	}
	
	public static void reset() {
		
		instance = null;
	}
	public static Peachtree getInstance(){
		
		if( instance == null ){
			instance = new Peachtree();
			instance.setup();
		}
		return instance;
	}
	/**
	 * Reads the light times from a file
	 * @return map of traffic light times for each intersection in each direction
	 */
	private  HashMap<PTIntersection, HashMap<VehicleDirection, TrafficLightTimings>> readLightTimes() {
		
		HashMap<PTIntersection, HashMap<VehicleDirection, TrafficLightTimings>> hm = new HashMap<PTIntersection, HashMap<VehicleDirection, TrafficLightTimings>>();
		Scanner scan = null;
		try {
			scan = new Scanner( new File( trafficLightTimesFile));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		int street = 0;
		HashMap<VehicleDirection,TrafficLightTimings> h = new HashMap<VehicleDirection,TrafficLightTimings>(); 
		if( scan.hasNext() )
			scan.nextLine();
		while(scan.hasNext())
		{
			int linenumbr = 1;
			
			
			while( linenumbr != 5 ){
			
				//h.put( new VehicleDirection( Direction.EAST,Direction.NORTH), 0);
				//h.put( new VehicleDirection( Direction.WEST,Direction.SOUTH), 0);
			
				String line = scan.nextLine();
				String [] toks = line.split(",");
				int n = toks.length;
				
				double times = 0.0;
				int time[] = new int [n];
				for(int i=1;i<n;i++){
					times = Double.parseDouble(toks[i]);
					time[i] = (int) times;
				}
				switch( linenumbr ){
					case 1:{
						h.put(new VehicleDirection(Direction.WEST,Direction.NORTH),new TrafficLightTimings(time[0],time[1],time[2]));
						h.put(new VehicleDirection(Direction.WEST,Direction.EAST), new TrafficLightTimings(time[3],time[4],time[5]));
						//h.put(new VehicleDirection( Direction.SOUTH,Direction.NORTH),time);
						break;
						}
					case 2:{
						h.put(new VehicleDirection(Direction.EAST,Direction.SOUTH),new TrafficLightTimings(time[0],time[1],time[2]));
						h.put(new VehicleDirection(Direction.EAST,Direction.WEST), new TrafficLightTimings(time[3],time[4],time[5]));
						//h.put(new VehicleDirection( Direction.WEST,Direction.EAST),time);
						break;
						}
					case 3:{
						h.put(new VehicleDirection(Direction.SOUTH,Direction.WEST),new TrafficLightTimings(time[0],time[1],time[2]));
						h.put(new VehicleDirection(Direction.SOUTH,Direction.NORTH), new TrafficLightTimings(time[3],time[4],time[5]));
						//h.put(new VehicleDirection( Direction.SOUTH,Direction.WEST),time);
						break;
						}
					case 4:{
						h.put(new VehicleDirection(Direction.NORTH,Direction.EAST),new TrafficLightTimings(time[0],time[1],time[2]));
						h.put(new VehicleDirection(Direction.NORTH,Direction.SOUTH), new TrafficLightTimings(time[3],time[4],time[5]));
						break;
					}
					default:
				}
				linenumbr++;
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
			
			if(scan.hasNext())
				scan.nextLine();
			if(scan.hasNext())
				scan.nextLine();
		}
		
		scan.close();
		return hm;
	}
	/**
	 * Creates vehicles and a list of SystemArrivals
	 * @return returns a list of SystemArrival objects
	 */
	public List<TrafficEvent> createArrivals(){
		
		//return test();
		if( useGamma )
			return createArrivalsGamma();
		else
			return createArrivalsExpon();
		
	}
	/**
	 * Creates vehicles and a list of SystemArrivals using exponential distribution
	 * @return returns a list of SystemArrival objects
	 */
	private  List<TrafficEvent> createArrivalsExpon(){
		
		getOriginData();
		LinkedList<TrafficEvent> arrivals = new LinkedList<TrafficEvent>();
		double currentTime;
		
		for( Integer origin: originDestinationMap.keySet() ){
			
			currentTime = 0;
			int dest;
			double rate = originRates.get( origin );
			//System.out.println("ORIGIN: "+origin+" rate: "+rate);
			while( currentTime < SIM_TIME_SECONDS ){
				
				
				dest = instance.generateRandomDestination( origin );
				currentTime += Exponential.expon(rate);
				
				//System.out.println(" currentTime: "+currentTime);
				if( currentTime > SIM_TIME_SECONDS ){
					break;
				}
				
				else{
					PTIntersection ptiOrigin = PTIntersection.getPTIntersection( origin );
					PTIntersection ptiDest = PTIntersection.getPTIntersection( dest );
					Vehicle v = new Vehicle(totalCars++,(int)currentTime,ptiOrigin,ptiDest);
					if( (new RandomGenerator()).xorrandDouble()<AGGRESSIVENESS_PROB ){
						v.setAggressive( true );
					}
					arrivals.add( new SystemArrival( v,(int)currentTime) );
				}
				
			}
		}
		
		return arrivals;
	}
	/**
	 * Creates vehicles and a list of SystemArrivals using Gamma distribution
	 * @return returns a list of SystemArrival objects
	 */
	private List<TrafficEvent> createArrivalsGamma(){
		
		getOriginData();
		LinkedList<TrafficEvent> arrivals = new LinkedList<TrafficEvent>();
		double currentTime;
		String interArrivaltimes = "simInterArrivalTimes.txt";
		PeachtreeSimOutput 	interArrivalOutput;
		
		if( writeToFile ){
			interArrivalOutput = new PeachtreeSimOutput( interArrivaltimes );
		}
		else{
			interArrivalOutput = new PeachtreeSimOutput();
		}
		
		for( Integer origin: originDestinationMap.keySet() ){
			
			currentTime = 0;
			int dest;
			
			PTIntersection ptiOrigin = PTIntersection.getPTIntersection( origin );
			double shape = gammaData.get( ptiOrigin )[0];
			double scale = gammaData.get( ptiOrigin )[1];
			
			GammaDistribution gamma = new GammaDistribution(shape,scale);
			//System.out.println("ORIGIN: "+origin+" rate: "+rate);
			while( currentTime < SIM_TIME_SECONDS ){
				
				
				dest = instance.generateRandomDestination( origin );
				
				double iaTime = gamma.sample();
				currentTime += iaTime;
				if( (ptiOrigin.getValue() == PTIntersection.PEACHTREE_NORTH.getValue()) || (ptiOrigin.getValue() == PTIntersection.PEACHTREE_SOUTH.getValue()))
					interArrivalOutput.writeln(simOriginMappings.get(origin)+", "+iaTime);
				
				
				if( currentTime > SIM_TIME_SECONDS ){
					break;
				}
				
				else{
					
					PTIntersection ptiDest = PTIntersection.getPTIntersection( dest );
					Vehicle v = new Vehicle(totalCars++,(int)currentTime,ptiOrigin,ptiDest);
					if( (new RandomGenerator()).xorrandDouble()<AGGRESSIVENESS_PROB ){
						v.setAggressive( true );
					}
					arrivals.add( new SystemArrival( v,currentTime) );
				}
				
			}
		}
		interArrivalOutput.close();
		return arrivals;
		
	}
/**
 * 
 * @param pti intersection
 * @return returns the Intersection object at this intersection
 */
	public  Intersection getIntersection(PTIntersection pti ){
		
		return intersections.get(pti);
	}
	/**
	 * Finds the Peachtree intersection that a car has to cross next based on origin
	 * @param origin origin that a car enters at 
	 * @return the Peachtree intersection
	 */
	public  Intersection findIntersection(PTIntersection origin) {
		
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
	 * Based on the destination of a car and its current location finds the next Peachtree intersection
	 * that a car needs to drive to
	 * @param is Intersection that a car is currently at
	 * @param direction direction that a car is headed
	 * @return next intersection that a car needs to go to
	 */
	public  Intersection getNextIntersection(Intersection is,
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
	public  Direction northOrSouthOfDest(Intersection is, Vehicle vehicle) {
		
		//System.out.println("Intersection: "+is.getId().getValue()+" Destination: "+vehicle.getDestination().getValue());
		return is.getId().northOrSouthOf(vehicle.getDestination());
		
	}
	/**
	 * Determines if the current intersection is east or west of the vehicle's destination
	 * @param is Intersection that a vehicle is currently at
	 * @param vehicle current vehicle
	 * @return Direction east or west
	 */
	public  Direction eastOrWestOfDest(Intersection is, Vehicle vehicle) {
		
		//System.out.println("dir: "+is.getId().eastOrWestOf( vehicle.getDestination()) );
		return is.getId().eastOrWestOf( vehicle.getDestination() );
	}
	/**
	 * 
	 * @param vehicle current vehicle
	 * @return determines the direction that a a vehicle is headed when it enters the system
	 */
	public  Direction vehicleEntry(Vehicle vehicle) {
		
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
	public  void computeRoute(Vehicle vehicle) {
		
		Intersection is = instance.findIntersection( vehicle.getOrigin() );
		VehicleDirection direction = null;
		
		Direction entry = instance.vehicleEntry( vehicle );
	
		Direction dir;

		dir = instance.northOrSouthOfDest(is, vehicle);
		
		if( dir == null )
			dir = instance.eastOrWestOfDest(is, vehicle);
		
		direction = new VehicleDirection(entry,dir.getOppositeDir()); 
		vehicle.setDirection(direction);
		Queue<VehicleISInfo> route = vehicle.getRoute();
		route.add( new VehicleISInfo( is, direction ) );
		Intersection nextIs = is;
		
		while( nextIs != null ){
			
			Direction northOrSouth = instance.northOrSouthOfDest(nextIs, vehicle);
			
			if( sameLevel( nextIs, vehicle ) ){ 
				//keep going through and schedule system departure
				
				if( vehicle.getDestination().getValue()%10==0 ){//heading west
					
					direction = new VehicleDirection( vehicle.getDirection().getFrom(), Direction.WEST );
					route.add( new VehicleISInfo( nextIs, direction ));
					nextIs = null;
				}
				else if( vehicle.getDestination().getValue()%10==2 ){//heading east
					direction = new VehicleDirection( vehicle.getDirection().getFrom(), Direction.EAST );
					route.add( new VehicleISInfo( nextIs, direction ));
					nextIs = null;
				}
				
				
			}
			else if( northOrSouth == Direction.NORTH ){  //go south
				
				direction = new VehicleDirection( northOrSouth, Direction.SOUTH);
				
				
				if( nextIs.getId()==PTIntersection.TENTH&&vehicle.getDestination()==PTIntersection.PEACHTREE_SOUTH ){//departing system
				
					nextIs = null;
				}
				else{
					nextIs = instance.getNextIntersection( nextIs, Direction.SOUTH );
					route.add( new VehicleISInfo( nextIs, direction ));
				}
			}
			else if( northOrSouth == Direction.SOUTH ){	//go north
				
				direction = new VehicleDirection( northOrSouth, Direction.NORTH );
				
				if( nextIs.getId()==PTIntersection.FOURTEENTH&&vehicle.getDestination()==PTIntersection.PEACHTREE_NORTH ){//departing system
			
					nextIs = null;
				}
				else{
					nextIs = instance.getNextIntersection( nextIs, Direction.NORTH );
					route.add( new VehicleISInfo( nextIs, direction ));
				}
			}
		}
		/*
		System.out.println("Vehicle: "+vehicle.getId()+" "+vehicle.getOrigin()+" "+vehicle.getDestination());
		System.out.println("Route: "+route.size());
		for( VehicleISInfo vi: route)
			System.out.println(vi);
		System.out.println("TIME: "+END_TIME);
		*/
		//System.exit(0);
	}
	/**
	 * Determines if a car's destination is east or sest of this destination
	 * @param is Intersection
	 * @param vehicle vehicle
	 * @return true if destination is east or west of the current intersection
	 */
	private  boolean sameLevel( Intersection is, Vehicle vehicle) {
		return is.getId().getValue()/10 == vehicle.getDestination().getValue()/10;
	}
	/**
	 * Get the next intersection to prcoess
	 * @param vehicle the route we want to remove
	 * @return the first intersection
	 */
	public  VehicleISInfo nextIntersection(Vehicle vehicle) {
		
		return vehicle.getRoute().remove();
	}
	/**
	 * Read origin distribution into memory. Read rates into memory
	 */
	public  void getOriginData(){
		
		Scanner scan = null;
		String line = null;
		String []tokens;
		
		originDestinationMap = new HashMap<Integer,OriginInfo>();
		originRates = new HashMap<Integer,Double>();
		
		try {
			scan = new Scanner( new File( originDestinationDataFile) );
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		if( scan.hasNext() )
			line = scan.nextLine();
		tokens = line.split(",");
		
		
		int numDestinations = tokens.length-2;
		originDestinationData = new int[numDestinations][numDestinations];
		
		for( int i=2;i<tokens.length;i++){
			
			//System.out.println(tokens[i]);
			originDestinationMap.put(Integer.parseInt(tokens[i]),new OriginInfo((i-2),-1));
		}
		
		
		while ( scan.hasNext() ){
			line = scan.nextLine();
			//System.out.println(line);
			tokens = line.split(",");
			int origin = Integer.parseInt( tokens[0] );
			int totalCars = Integer.parseInt( tokens[1] );
			originDestinationMap.get(origin).setTotalCars(totalCars);
			double rate = (double)totalCars/SIM_TIME_SECONDS*MULT_15MIN ;
			if( !useGamma )
				originRates.put( origin, rate );
			int mapping = originDestinationMap.get(origin).getMapping();
			for(int i=0;i<numDestinations;i++){
				originDestinationData[mapping][i]=Integer.parseInt( tokens[i+2] );
			}
		}
		scan.close();		
		
		//System.out.println(originRates);
	}
	/**
	 * Prints the distribution of traveling from the origin to each destination to screen
	 */
	public void printOriginDestinationData(){
		Set<Integer> keySet  = originDestinationMap.keySet();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.addAll(keySet);
		Collections.sort(keys);
		
		for(Integer key: keys ){
			OriginInfo oinfo = originDestinationMap.get(key);
			int mapping = oinfo.getMapping();
			int totalCars = oinfo.getTotalCars();
			System.out.printf("%4d %4d ",key,totalCars );
			for(int j=0;j<originDestinationData[mapping].length;j++){
				System.out.printf("%4d ",originDestinationData[mapping][j]);
			}
			System.out.println();
		}
	}
	/**
	 * Based on origin and the distribution used, generate a random destination
	 * @param origin origin that car starts at
	 * @return a random destinatino
	 */
	public  int generateRandomDestination( int origin ){
		
		Random rand = new Random();
		OriginInfo oinfo = originDestinationMap.get( origin );
		int mapping = oinfo.getMapping();
		int totalCars = oinfo.getTotalCars();
		
		int randomNum = rand.nextInt( totalCars );
		int sum = 0;
		Set<Entry<Integer,OriginInfo>> entrySet = new HashSet<Entry<Integer,OriginInfo>>();
		entrySet.addAll(originDestinationMap.entrySet() );
		
		for( int i = 0;i< originDestinationData[mapping].length;i++){
		
			sum+=originDestinationData[mapping][i];
			if( randomNum < sum ){
				for( Entry<Integer, OriginInfo> entry: entrySet ){
					if( entry.getValue().getMapping() == i ){
						return entry.getKey();
					}
				}
			}
		}
		return -1;
	}
	/**
	 * Prints results of generating random destinations. Results should be close
	 * the input distriution
	 */
	public  void runDistributionTest(){
		
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.addAll( originDestinationMap.keySet() );
		Collections.sort( keys );
		int n = keys.size();
		final int TOTAL_CARS = 100;
		double [][] distr = new double[n][n];
		
		
		for( int origin: keys ){
			int originMapping = originDestinationMap.get( origin ).getMapping();
			for( int i = 0;i<TOTAL_CARS;i++){
				int dest = instance.generateRandomDestination( origin );
				int destMapping = originDestinationMap.get( dest ).getMapping();
				distr[originMapping][destMapping]+=1;
			}
		}
		
		System.out.printf("%4s\t", "");
		for( int dest: keys ){
			System.out.printf("%4d\t", dest);
		}
		System.out.println();
		for( int origin: keys ){
			
			int mapping = originDestinationMap.get(origin).getMapping();
			System.out.printf("%4d\t", origin);
			for( int i=0;i<distr[mapping].length;i++){
				System.out.printf("%-3.2f\t", (double)distr[mapping][i]/TOTAL_CARS);
			}
			System.out.println();
		}
	}
	/**
	 * 
	 * @return mapping
	 */
	public HashMap<Integer, Integer> getOriginMappings(){
		return simOriginMappings;
	}
	/**
	 * 
	 * @return number of seconds in simulation
	 */
	public static int getSIM_TIME_SECONDS() {
		return SIM_TIME_SECONDS;
	}
	/**
	 * set write to file = true;
	 */
	public static void setWriteToFile() {
		writeToFile = true;		
	}
	/**
	 * Updates the statistics of the system an intersection after a departure
	 * @param is Intersection to update
	 * @param direction direction that car left
	 * @param time time that car departed
	 * @param vehicle Vehicle object that left system
	 */
	public  void updateStatistics(Intersection is,
			VehicleDirection direction, double time, Vehicle vehicle) {
		
		stats.updateVehicleStats(vehicle, is, direction, time);
		
	}
	/**
	 * 
	 * @return total cars
	 */
	public  int getTotalCars(){
		return totalCars;
	}
	/**
	 * 
	 * @return end time of simulaton
	 */
	public  int getEndTime() {
		return END_TIME;
	}
	/**
	 * 
	 * @return stas objects going
	 */
	public TrafficStatistics getStats() {
		return stats;
	}
	/**
	 *
	 * @return writeToFile
	 */ 
	public boolean getWritetoFile() {
		return writeToFile;
	}
	/**
	 * 
	 * @return output
	 */
	public PeachtreeSimOutput getSimOutput() {
		
		return simOutput;
	}
	public static void main(String[]args){
		
		Peachtree pt = Peachtree.getInstance();
		pt = Peachtree.getInstance();
		pt = Peachtree.getInstance();
		pt.getOriginData();
		int dest = pt.generateRandomDestination(61);
		List<TrafficEvent> arrivals = pt.createArrivals();
		/*System.out.println(arrivals.size());
		*/
		//instance.runDistributionTest();
	}
}
