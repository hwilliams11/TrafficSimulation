package testSim;
import simApp.Direction;
import simApp.PTIntersection;
import simApp.TrafficLight;
import simApp.TrafficLightTimings;
import simApp.VehicleDirection;

import java.util.HashMap;

public class TestTrafficLight {
	
	public static void main(String[]args){
		
		int END_TIME = 300;
		VehicleDirection ns = new VehicleDirection(Direction.NORTH,Direction.SOUTH);
		VehicleDirection en = new VehicleDirection(Direction.EAST,Direction.NORTH);
		VehicleDirection ne = new VehicleDirection(Direction.NORTH,Direction.EAST);
		VehicleDirection ew = new VehicleDirection(Direction.EAST,Direction.WEST);
		
		
		HashMap<VehicleDirection,TrafficLightTimings> hm = new HashMap<VehicleDirection,TrafficLightTimings>();
		hm.put(ns, new TrafficLightTimings( 40, 5, 2 ));
		hm.put(en, new TrafficLightTimings( 30, 5, 2 ));
		hm.put(ew, new TrafficLightTimings( 30, 5, 2 ));
		hm.put(ne, new TrafficLightTimings( 20, 5, 2 ));
		
		TrafficLight tl = new TrafficLight( END_TIME,hm,PTIntersection.ELEVENTH );
		System.out.println(tl);
		int delay;
		
		for(int i=0;i<END_TIME;i+=50){
			delay = tl.getDelay( i, new VehicleDirection(Direction.SOUTH,Direction.NORTH));
			System.out.println("Delay: "+delay);
		}
	}

}
