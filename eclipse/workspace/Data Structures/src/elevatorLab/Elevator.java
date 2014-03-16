package elevatorLab;

import java.util.ArrayList;

/**
 * An elevator simulation. It has fields direction, elevNumber, floor (the current floor it is on), and an ArrayList that holds all
 * the people inside it. It also keeps track of its total capacity.
 * @author Shmuel
 *
 */
public class Elevator
{
	private Direction direction;
	private int elevNumber;
	public int floor;
	private ArrayList<Person> people = new ArrayList<>();

	public static final int TOTAL_CAPACITY = 10;

	/**
	 * Straightforward constructor...
	 * @param direction
	 * @param number
	 * @param floor
	 */
	public Elevator(Direction direction, int number, int floor) {
		this.direction = direction;
		elevNumber = number;
		this.floor = floor;
	}

	/**
	 * Increments the floor the elevator is on, making sure it changes direction if it is on the top or bottom floor.
	 */
	public void nextFloor() {
		if(direction == Direction.UP) {
			floor++;
		}
		else {
			floor--;
		}

		//Sees if the direction should be changed
		if(floor == Simulator.NUM_FLOORS) {
			direction = Direction.DOWN;
		}
		else if(floor == 1) {
			direction = Direction.UP;
		}
		
		System.out.println("Elevator-" + elevNumber + " moved to floor " + floor);
	}

	/**
	 * Adds a person to the Elevator
	 * @param person The person to be added to the elevator
	 */
	public void addPerson(Person person) {
		people.add(person);
	}
	
	//All the getters for the Elevator class

	/**
	 * @return True if the Elevator is empty, false if not
	 */
	public boolean isEmpty() {
		return people.isEmpty();
	}

	/**
	 * @return The array containing all the people in the elevator
	 */
	public ArrayList<Person> getPeople() {
		return people;
	}

	/**
	 * @return The Elevator number
	 */
	public int getElevNum() {
		return elevNumber;
	}

	/**
	 * @return The floor number the elevator is currently on
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return The total number of people in the elevator
	 */
	public int getTotalPeople() {
		return people.size();
	}

	/**
	 * @return The Direction the Elevator is currently going in
	 */
	public Direction getDirection() {
		return direction;
	}
}
