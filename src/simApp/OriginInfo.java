package simApp;

public class OriginInfo {

	int mapping;
	int totalCars;
	
	public OriginInfo( int mapping, int totalCars ){
		this.mapping = mapping;
		this.totalCars = totalCars;
	}

	public int getMapping() {
		return mapping;
	}

	public int getTotalCars() {
		return totalCars;
	}

	public void setMapping(int mapping) {
		this.mapping = mapping;
	}

	public void setTotalCars(int totalCars) {
		this.totalCars = totalCars;
	}

	@Override
	public String toString() {
		return "OriginInfo [mapping=" + mapping + ", totalCars=" + totalCars
				+ "]";
	}
}
