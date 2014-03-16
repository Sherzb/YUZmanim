package elevatorLab;

import java.util.Random;
import java.util.PriorityQueue;

import javax.swing.JFrame;

public class Simulator
{
	//All the constants. I think this is better code then having random number appear in the rest of the code ("magic numbers")
	//Also I should have instantiated Simulator, instead of having everything static
	public final static int PERSON_CHANCE = 2; //1 to anything
	public final static int NUM_FLOORS = 1000; //2 to 10000
	public final static int NUM_ELEVATORS = 100; //1 to 2000
	public final static int SIMULATION_TIME = 72000; //1 to anything
	public final static int WAIT_TIME = 0; //In milliseconds
			
	private static Random rand = new Random();
	private static Floor[] allFloors = new Floor[NUM_FLOORS];
	private static Elevator[] allElevators = new Elevator[NUM_ELEVATORS];
	private static PriorityQueue<Event> pq = new PriorityQueue<Event>();
	private static int personDroppedOffCounter = 0;
	private static int totalPeopleCounter = 0; //Even if they haven't been dropped off
	private static int totalWaitTime = 0;
	private static BetterGUI elevatorDisplay = new BetterGUI();
	public static int totalTime = 0;
	
	public static void main(String[] args) {
		setup();		
		run();
	}
	
	/**
	 * Sets up the simulation by initializing the floors and elevators. The floor is simply initialized
	 * with the floor number it represents, while the elevator is initialized with its initial floor, number, and direction.
	 * It also adds a move event to the PriorityQueue for each elevator.
	 */
	public static void setup() {
		//Floors
		for(int i = 1; i < NUM_FLOORS + 1; i++) {
			allFloors[i - 1] = new Floor(i);			
		}
		//Elevators
		for(int i = 0; i < NUM_ELEVATORS; i++) {
			int initialFloor = rand.nextInt(NUM_FLOORS - 1) + 1;
			Direction direction;
			if(initialFloor == 1 || rand.nextBoolean()) {
				direction = Direction.UP;
			}
			else {
				direction = Direction.DOWN;
			}
			allElevators[i] = new Elevator(direction, i + 1, initialFloor);
			pq.add(new Event(0, EventEnum.MOVE, allElevators[i]));
			System.out.println("Created Elevator number " + i);
		}
		//GUI
		elevatorDisplay.setVisible(true);
		elevatorDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * The main running part. It runs up to the number of seconds specified in the simulation. It first has a random chance
	 * of generating a person. After that, it sees if an event is supposed to take place at the time the simulation is up to.
	 * If it does, the event is run.
	 * After the simulation runs, it divides the number of seconds by the number of people, and returns that as the average
	 * wait time.
	 */
	public static void run() {
		for(int i = 0; i <= SIMULATION_TIME; i++) {
			totalTime++;
			personGenerator(i);
			elevatorDisplay.repaint();
			waitTime();
			while(pq.peek().getTime() == i) {
				Event event = pq.remove();
				event.run();
			}
		}
		System.out.println();
		System.out.println("Average wait time was " + getAverageWait() + " seconds");	
	}
	
	/**
	 * Gives a random chance to generate a person. If a person is counted, the number of people is incremented. Then a random
	 * beginning and end floor is made for the person, making sure the end floor is different than the start floor. It also
	 * prints out that the person was created.
	 * @param startTime The time the person will be created at
	 */
	public static void personGenerator(int startTime) {
		int chance = rand.nextInt(PERSON_CHANCE);
		if (chance == 0) {
			totalPeopleCounter++;
			int startFloor = rand.nextInt(NUM_FLOORS) + 1;
			int endFloor = rand.nextInt(NUM_FLOORS) + 1;
			while(startFloor == endFloor) {
				endFloor = rand.nextInt(NUM_FLOORS) + 1;
			}
			Person person = new Person(" Ike-Sultan-" + totalPeopleCounter, startFloor, endFloor, startTime);
			allFloors[startFloor - 1].getAllPeople().add(person);
			System.out.println();
			System.out.print("Time " + startTime + ":");
			System.out.println(person.getName() + " added to floor " + startFloor + " going to " + endFloor);
			System.out.println();
		}
	}
	
	/**
	 * Called to have a certain amount of milliseconds between each event. Used to make the following of the GUI
	 * manageable.
	 */
	public static void waitTime() {
		try {
		    Thread.sleep(WAIT_TIME);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Adds a person to the personCounter, which counts the number of people that have been dropped off
	 */
	public static void addPerson() {
		personDroppedOffCounter++;
	}
	
	//A whole bunch of get functions, for basically all of the variables in the Simulator class. Not sure if
	//this is better or worse style than having them be public, but I think better...
	
	/**
	 * Adds to the total wait time. This is called by the Event class, when a person gets off the elevator.
	 * @param time The time it has taken the person to go from a floor to being dropped off
	 */
	public static void addToWait(int time) {
		totalWaitTime += time;
	}
	
	/**
	 * @return The Priority Queue
	 */
	public static PriorityQueue<Event> getPQ() {
		return pq;
	}
	
	/**
	 * @return The Floor[] array
	 */
	public static Floor[] getAllFloors() {
		return allFloors;
	}
	
	/**
	 * @return The Elevator[] array
	 */
	public static Elevator[] getAllElevators() {
		return allElevators;
	}
	
	/**
	 * @return The total time that the simulator has been running
	 */
	public static int getTotalTime() {
		return totalTime;
	}
	
	/**
	 * @return The average wait time per person that has been dropped off, or 0 if nobody has yet been dropped off
	 */
	public static double getAverageWait() {
		if(personDroppedOffCounter == 0 || totalWaitTime == 0) {
			return 0;
		}
		else {
			return (double) totalWaitTime/ (double)personDroppedOffCounter;
		}	
	}
	
	/**
	 * @return The total number of people that have been dropped off
	 */
	public static int getTotalPeople() {
		return personDroppedOffCounter;
	}
}


