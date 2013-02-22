package testSim;
import java.util.ArrayList;
import java.util.Arrays;

import simApp.PTIntersection;
import simApp.Peachtree;
import simApp.SystemArrival;
import simApp.TrafficLight;
import simApp.TrafficTime;
import simApp.Vehicle;
import simApp.VehicleDirection;


public class TestFullQueue {

	public static void main( String[] args ){
		
		Peachtree pt = Peachtree.getInstance();
		
		//set traffic light times for top intersection NS
		//30 60 70 90 110 120 130 140 150 160 170 180 190 210 220
		ArrayList<TrafficTime> times = new ArrayList<TrafficTime>();
		
		int[]arrayTimes = {0, 30, 60, 70, 90, 110, 120, 130, 140, 150, 160, 170, 180, 190, 210, 220};
		TrafficLight eleventh = pt.getIntersection(PTIntersection.ELEVENTH).getLight();
		setupTimes(times,arrayTimes );
		//eleventh.setLightTimes(times);
		
		//set traffic light times for bottom intersection NS
		//25 40 70 100 120 140 160 180 200 220 240 260 280 300 320
		int [] arrayTimes2 = {0, 25, 40, 70, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320};
		TrafficLight tenth = pt.getIntersection(PTIntersection.TENTH).getLight();
		times.clear();
		setupTimes(times,arrayTimes2 );
		//tenth.setLightTimes(times);
		

		
		tenth.getDelay( 0, VehicleDirection.NS );

		System.out.println(" TENTH ");
		System.out.println( tenth );
		
		System.out.println( tenth.getDelay( 0, VehicleDirection.EW ) );
		
	}

	private static void setupTimes(ArrayList<TrafficTime> times,
			int[] arrayTimes) {
		
		int count = 0;
		for(int time: arrayTimes){
			
			switch( count%3 ){
			case 0:
			{
				times.add( new TrafficTime( time, VehicleDirection.EW ) );
				break;
			}
			case 1:
			{
				times.add( new TrafficTime( time, VehicleDirection.NS ) );
				break;
			}
			case 2:
			{
				times.add( new TrafficTime( time, VehicleDirection.EW ) );
				break;
			}
			}
			count++;
		}
		
	}
}
