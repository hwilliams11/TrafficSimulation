package simApp;

/**
 * Stores the test data info cars per origin
 * @author Holly
 *
 */
public class OriginInfo {

	int mapping;
	int totalCars;
	
	/**
	 * 
	 * @param mapping vertex mapping
	 * @param totalCars cars that pass through
	 */
	public OriginInfo( int mapping, int totalCars ){
		this.mapping = mapping;
		this.totalCars = totalCars;
	}
/**
 * 
 * @return mapping
 */
	public int getMapping() {
		return mapping;
	}
/**
 * 
 * @return number of cars
 */
	public int getTotalCars() {
		return totalCars;
	}
/**
 * 
 * @param mapping new mapping value
 */
	public void setMapping(int mapping) {
		this.mapping = mapping;
	}
/**
 * 
 * @param totalCars new total cars value
 */
	public void setTotalCars(int totalCars) {
		this.totalCars = totalCars;
	}

	@Override
	public String toString() {
		return "OriginInfo [mapping=" + mapping + ", totalCars=" + totalCars
				+ "]";
	}
}
