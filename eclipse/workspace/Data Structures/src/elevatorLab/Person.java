package elevatorLab;

/**
 * A Person class. Persons are either in the Floor[], on a Floor, or in an Elevator[], in an Elevator. Nothing terribly 
 * complicated here.
 * @author Shmuel
 *
 */
public class Person
{
	private String name;
	private int startFloor;
	private int endFloor;
	private int startTime;
	
	/**
	 * 
	 * @param name The person's name (hehe)
	 * @param startFloor The floor the person is created on
	 * @param endFloor The floor the person gets off of
	 * @param startTime The time the person was created
	 */
	public Person(String name, int startFloor, int endFloor, int startTime) {
		this.name = name;
		this.startFloor = startFloor;
		this.endFloor = endFloor;
		this.startTime = startTime;
	}

	/**
	 * @return The floor the person will get off of
	 */
	public int getDestination() {
		return endFloor;
	}
	
	/**
	 * @return The person's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The time the person was created
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * If the person was created on a higher floor than his destination, he is going downward. Else, he is going upward.
	 * @return The Direction the person is going in.
	 */
	public Direction getDirection() {
		if(startFloor > endFloor) {
			return Direction.DOWN;
		}
		else {
			return Direction.UP;
		}
	}
}
