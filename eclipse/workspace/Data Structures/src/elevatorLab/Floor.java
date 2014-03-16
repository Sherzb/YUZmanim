package elevatorLab;

import java.util.ArrayList;

public class Floor
{
	private int floorNumber;
	private ArrayList<Person> allPeople = new ArrayList<Person>();
	
	/**
	 * Constructor
	 * @param floorNumber The number of the floor
	 */
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	/**
	 * @return True if the floor is empty, false otherwise
	 */
	public boolean isEmpty() {
		return allPeople.isEmpty();
	}
	
	/**
	 * @return The floor number
	 */
	public int getNumber() {
		return floorNumber;
	}
	
	/**
	 * @return A String representation of the floor, signified by its floor number
	 */
	public String toString () {
		return Integer.toString(floorNumber);
	}
	
	/**
	 * @return The total number of people on the floor
	 */
	public int peopleOnFloor() {
		return allPeople.size();
	}
	
	/**
	 * @return The ArrayList containing all the people on the floor
	 */
	public ArrayList<Person> getAllPeople() {
		return allPeople;
	}
}
