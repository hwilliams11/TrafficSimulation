package simApp;

import java.util.LinkedList;

public class VehicleStatsInfo{
	
	 /* vehicle statistics
	 * ==================
	 * - delay
	 * 		-average
	 * 		-max
	 * 		-total
	 * - time in system
	 */

	private LinkedList<Double>delays;
	private double timeInSystem;
	private double arrival;
	
	public VehicleStatsInfo(){
	
		 
		delays = new LinkedList<Double>();
		timeInSystem = 0;
		setArrival(0);
	}

	public VehicleStatsInfo(double timeInSystem, LinkedList<Double> delays,double arrival) {
	
		this.timeInSystem = timeInSystem;
		this.delays = delays;
		this.setArrival(arrival);
	}

	public LinkedList<Double> getDelays() {
		return delays;
	}

	public void setDelays(LinkedList<Double> delays) {
		this.delays = delays;
	}

	public double getTimeInSystem() {
		return timeInSystem;
	}

	public void setTimeInSystem(double timeInSystem) {
		this.timeInSystem = timeInSystem;
	}

	public double getArrival() {
		return arrival;
	}

	public void setArrival(double arrival) {
		this.arrival = arrival;
	}

}