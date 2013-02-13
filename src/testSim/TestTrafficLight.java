package testSim;
import simApp.Direction;
import simApp.TrafficLight;
import simApp.VehicleDirection;

import java.util.HashMap;

public class TestTrafficLight {
	
	public static void main(String[]args){
		
		int END_TIME = 300;
		VehicleDirection ns = new VehicleDirection(Direction.NORTH,Direction.SOUTH);
		VehicleDirection en = new VehicleDirection(Direction.EAST,Direction.NORTH);
		VehicleDirection ne = new VehicleDirection(Direction.NORTH,Direction.EAST);
		VehicleDirection ew = new VehicleDirection(Direction.EAST,Direction.WEST);
		
		
		HashMap<VehicleDirection,Integer> hm = new HashMap<VehicleDirection,Integer>();
		hm.put(ns, 40);
		hm.put(en, 30);
		hm.put(ew, 30);
		hm.put(ne, 20);
		
		TrafficLight tl = new TrafficLight( END_TIME,hm );
		System.out.println(tl);
		int delay;
		
		for(int i=0;i<END_TIME;i+=50){
			delay = tl.getDelay( i, new VehicleDirection(Direction.SOUTH,Direction.NORTH));
			System.out.println("Delay: "+delay);
		}
	}

}
