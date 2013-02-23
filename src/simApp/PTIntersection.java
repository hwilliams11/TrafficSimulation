package simApp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * PTIntersection is for labeling a point in the system
 * @author Holly Williams
 *
 */
public enum PTIntersection {

	
	TENTH_EAST(10),TENTH(11),TENTH_WEST(12),PEACHTREE_SOUTH(1),
	ELEVENTH_EAST(20),ELEVENTH(21),ELEVENTH_WEST(22),
	TWELFTH_EAST(30),TWELFTH(31),TWELFTH_WEST(32),
	THIRTEENTH_EAST(40),THIRTEENTH(41),THIRTEENTH_WEST(42),
	FOURTEENTH_EAST(50),FOURTEENTH(51),FOURTEENTH_WEST(52),PEACHTREE_NORTH(61);
	
	private int value;
	
	private PTIntersection(int value){
		this.value = value;
	}
	private static final List<PTIntersection> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static PTIntersection randomIntersection()  {
		    return VALUES.get(RANDOM.nextInt(SIZE));
	}
	public static List<PTIntersection> getValues(){
		return VALUES;
	}
	public static PTIntersection getPTIntersection( int val ){
		
		for( PTIntersection pi: VALUES ){
			if( pi.value == val )
				return pi;
		}
		return null;
	}
	/**
	 * 
	 * @return numeric value of intersection
	 */
	public int getValue(){
		return value;
	}
	/**
	 * Determines if an intersection is east or west of one intersection
	 * @param p2 other intersection
	 * @return east or west Direction object
	 */
	public Direction eastOrWestOf( PTIntersection p2 ){
		if( value % 10 < p2.value % 10 )
			return Direction.WEST;
		if( value % 10 > p2.value % 10 )
			return Direction.EAST;
		else
			return null;
	}
	/**
	 * Determines if an intersection is east or west of one intersection
	 * @param p2 other intersection
	 * @return north or south Direction object
	 */
	public Direction northOrSouthOf( PTIntersection p2 ){
		if( value/10 > p2.value/10 )
			return Direction.NORTH;
		if( value/10 < p2.value/10 )
			return Direction.SOUTH;
		else
			return null;
	}
}
