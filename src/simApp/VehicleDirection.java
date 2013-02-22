package simApp;

/**
 * Vehicle Direction class holds the direction that a vehicle is traveling
 * @author Holly Wiliams
 */
public class VehicleDirection implements Comparable<VehicleDirection> {
	
	private Direction from;
	private Direction to;
	
	public static VehicleDirection NN = new VehicleDirection(Direction.NORTH,Direction.NORTH );
	public static VehicleDirection NE = new VehicleDirection(Direction.NORTH,Direction.EAST );
	public static VehicleDirection NS = new VehicleDirection(Direction.NORTH,Direction.SOUTH );
	public static VehicleDirection NW = new VehicleDirection(Direction.NORTH,Direction.WEST );
	
	public static VehicleDirection EN = new VehicleDirection(Direction.EAST,Direction.NORTH);
	public static VehicleDirection EE = new VehicleDirection(Direction.EAST,Direction.EAST);
	public static VehicleDirection ES = new VehicleDirection(Direction.EAST,Direction.SOUTH);
	public static VehicleDirection EW = new VehicleDirection(Direction.EAST,Direction.WEST);
	
	public static VehicleDirection SN = new VehicleDirection(Direction.SOUTH,Direction.NORTH);
	public static VehicleDirection SE = new VehicleDirection(Direction.SOUTH,Direction.EAST);
	public static VehicleDirection SS = new VehicleDirection(Direction.SOUTH,Direction.SOUTH);
	public static VehicleDirection SW = new VehicleDirection(Direction.SOUTH,Direction.WEST);
	
	public static VehicleDirection WN = new VehicleDirection(Direction.WEST,Direction.NORTH);
	public static VehicleDirection WE = new VehicleDirection(Direction.WEST,Direction.EAST);
	public static VehicleDirection WS = new VehicleDirection(Direction.WEST,Direction.SOUTH);
	public static VehicleDirection WW = new VehicleDirection(Direction.WEST,Direction.WEST);
		
	public VehicleDirection(Direction from,Direction to){
		
		this.from = from;
		this.to = to;
		
	}
/**
 * 
 * @return from direction
 */
	public Direction getFrom() {
		return from;
	}
/**
 * 
 * @param from sets from direction
 */
	public void setFrom(Direction from) {
		this.from = from;
	}
/**
 * 
 * @return returns to direction
 */
	public Direction getTo() {
		return to;
	}
/**
 * 
 * @param to sets the to direction
 */
	public void setTo(Direction to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleDirection other = (VehicleDirection) obj;
		if (from != other.from)
			return false;
		if (to != other.to)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleDirection [from=" + from + ", to=" + to + "]";
	}
/**
 * Determines if two directions are opposite one another. For example northwest and southeast are opposite each other
 * @param dir direction
 * @return opposing vehicle direction
 */
	public boolean opposite(VehicleDirection dir) {

		//if( opposite(from,dir.getFrom()) && opposite(to,dir.getTo()))
		if( from.isOpposite(dir.getFrom()) && to.isOpposite(dir.getTo()))
				return true;
		return false;
	}
/**
 * Determines if a turn is a right turn
 * @return true if right turn false if not
 */
	public boolean rightTurn() {
		
		if( from == Direction.EAST && to == Direction.NORTH )
			return true;
		if( from == Direction.WEST && to == Direction.SOUTH )
			return true;
		if( from == Direction.SOUTH && to == Direction.EAST )
			return true;
		if( from == Direction.NORTH && to == Direction.WEST )
			return true;
		return false;
	}
public boolean uturn() {
	
	return from == to;
}
public boolean sameDir(VehicleDirection dir) {
	
	return ((from==dir.from) && (to==dir.to));
}
public boolean leftTurn() {
	
	if( from == Direction.EAST && to == Direction.SOUTH )
		return true;
	if( from == Direction.WEST && to == Direction.NORTH )
		return true;
	if( from == Direction.SOUTH && to == Direction.WEST )
		return true;
	if( from == Direction.NORTH && to == Direction.EAST )
		return true;
	return false;
}
@Override
public int compareTo(VehicleDirection arg0) {
	
	//NORTH = 1 EAST = 3 SOUTH = 2 WEST = 4
	int [][]vd = new int[2][2];
	
	for(int i=0;i<2;i++){
		
		switch( from ){
			case NORTH: vd[i][0] = 1;break;
			case EAST: vd[i][0] = 1;break;
			case SOUTH: vd[i][0] = 1;break;
			case WEST: vd[i][0] = 1;break;
		}
		switch( to ){
			case NORTH: vd[i][1] = 1;break;
			case EAST: vd[i][1] = 2;break;
			case SOUTH: vd[i][1] = 3;break;
			case WEST: vd[i][1] = 4;break;
		}
	}

	return ( vd[0][0] - vd[1][0])+ (vd[0][1] - vd[1][1] );
}

}
