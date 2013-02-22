package simApp;

import java.util.LinkedList;

public class VehicleStatsInfo {
	
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
	
	public VehicleStatsInfo(){
	
		delays = new LinkedList<Double>();
		timeInSystem = 0;
	}

	public VehicleStatsInfo(double timeInSystem, LinkedList<Double> delays) {
	
		this.timeInSystem = timeInSystem;
		this.delays = delays;
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
}
