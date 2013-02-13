package simApp;

public class VehicleISInfo {

	private Intersection intersection;
	private VehicleDirection direction;
	
	public VehicleISInfo( Intersection intersection, VehicleDirection direction ){
		this.intersection = intersection;
		this.direction = direction;
	}

	public Intersection getIntersection() {
		return intersection;
	}

	public VehicleDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "VehicleISInfo [intersection=" + intersection.getId() + ", direction="
				+ direction + "]";
	}
}
