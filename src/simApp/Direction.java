package simApp;
/**
 * Direction enum
 * @author Holly Wiliams
 *
 */
public enum Direction {

	NORTH,SOUTH,EAST,WEST;

	/**
	 * 
	 * @return finds the opposite direction
	 */
	public Direction getOppositeDir() {
		
		if( this == Direction.NORTH )
			return Direction.SOUTH;
		if( this == Direction.EAST )
			return Direction.WEST;
		if( this == Direction.SOUTH )
			return Direction.NORTH;
		if( this == Direction.WEST )
			return Direction.EAST;
		return null;
	}
	/**
	 * 
	 * @param dir direction to test
	 * @return returns if a direction is opposite to dir
	 */
	public boolean isOpposite( Direction dir){
		return this.getOppositeDir()==dir;
	}
}
