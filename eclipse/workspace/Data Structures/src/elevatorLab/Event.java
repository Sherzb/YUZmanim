package elevatorLab;

/**
 * The complicated event class. Each event is associated (has the field) with an elevator, the time it was called (which is
 * how its stacked on the priority queue), and the name of the event (an enum). Events are called, which perform certain things,
 * and then add other events to the priority queue.
 * @author Shmuel
 *
 */
public class Event implements Comparable<Event>
{
	//All the constants. I think this is better code then having random number appear in the rest of the code ("magic numbers")
	public static final int MOVE_TIME = 2;
	public static final int OPEN_TIME = 1;
	public static final int CLOSE_TIME = 1;
	public static final int EXIT_TIME = 2;
	public static final int BOARD_TIME = 3;

	private Elevator elevator;
	private int time;
	private EventEnum eventName;


	/**
	 * @param time The time the event should happen
	 * @param eventName The name of the event
	 * @param elevator The elevator that will be doing this event
	 */
	public Event(int time, EventEnum eventName, Elevator elevator) {
		this.time = time;
		this.eventName = eventName;
		this.elevator = elevator;
	}

	/**
	 * @return The time the event should happen
	 */
	public int getTime() {
		return time;
	}

	/**
	 * PriorityQueue is a minheap, so if this event's time is greater than a different event's time, compareTo will return 
	 * a number greater than 1, and it will go lower in the heap.
	 */
	public int compareTo(Event event) {
		return time - event.getTime();
	}

	/**
	 * The main event (hehe). This is called from the run() method in the main class, on an event that is popped off the
	 * priority queue. It first prints out the time the event is occurring, and gets the floor it is happening on (because a bunch
	 * of different events need to know the floor). There are 6 possible events:
	 * 1) MOVE. First the elevator changes floors, and the floor is updated. It then sees if someone on (closed) the elevator is getting off,
	 * (so OPEN_EXIT is added to the pq), if someone on the floor is getting on (so OPEN_BOARD is added to the pq), or neither (so
	 * another MOVE is added to the pq)
	 * 2) OPEN_BOARD. The elevator begins opening to let someone on. Follow by BOARD
	 * 3) BOARD. It first sees if the floor is empty (because multiple people may board on one floor). If it is not, it checks to see
	 * if anyone is going in the same direction as the elevator. If they are, it takes that person, and then re-adds this event to the
	 * pq (after waiting however long it takes to board). If the floor is empty, or nobody is going in that direction, it begins closing.
	 * 4) CLOSE. Prints out that the elevator is beginning to close, and adds MOVE to the pq after however long it takes the elevator
	 * to close.
	 * 5) OPEN_EXIT. If a person is getting off on the floor the elevator is on, prints out that the elevator began opening to let someone
	 * off, and adds EXIT to the pq after however long it takes for the elevator to open.
	 * 6) EXIT. If the elevator is not empty, and someone is getting off on the floor, calls a method that gets the person off the 
	 * elevator. If the elevator is empty, or nobody is getting off on that floor, it begins closing.
	 */
	public void run() {
		System.out.print("Time " + time + ": ");
		Floor floor = Simulator.getAllFloors()[elevator.getFloor() - 1];

		switch(eventName) {
		case MOVE:
			elevator.nextFloor(); //Changes the floor
			floor = Simulator.getAllFloors()[elevator.getFloor() - 1]; //Gets the floor for later usage
			if(gettingOffClosedElevator(floor, elevator)) { //True if someone is getting off, false otherwise
				break;
			}
			else if(gettingOnElevator(floor)) { //If someone is getting on
				break;
			}
			else {				
				Simulator.getPQ().add(new Event(time + MOVE_TIME, EventEnum.MOVE, elevator)); //Nobody is getting on or off
				break;
			}
		case OPEN_BOARD:
			Simulator.getPQ().add(new Event(time, EventEnum.BOARD, elevator));
			System.out.println("Elevator-" + elevator.getElevNum() + " opened to let someone on");
			break;
		case BOARD:
			if(!floor.isEmpty()) {
				for(Person person: floor.getAllPeople()) {
					if (person.getDirection() == elevator.getDirection() && elevator.getTotalPeople() < Elevator.TOTAL_CAPACITY) {
						elevator.addPerson(person);
						floor.getAllPeople().remove(person);
						Simulator.getPQ().add(new Event(time + BOARD_TIME, EventEnum.BOARD, elevator));
						System.out.println(person.getName() + " began boarding Elevator-" + elevator.getElevNum() + " on floor " + floor.getNumber() + " going to " + person.getDestination());
						return; //Return, not break, to get out of both the for loop and the switch loop
					}
				}
			}
			System.out.println("Elevator-" + elevator.getElevNum() + " on floor " + elevator.getFloor() + " begins closing");
			Simulator.getPQ().add(new Event(time + CLOSE_TIME, EventEnum.CLOSE, elevator));
			break;
		case CLOSE:
			System.out.println("Elevator-" + elevator.getElevNum() + " on floor " + elevator.getFloor() + " just closed");
			Simulator.getPQ().add(new Event(time + MOVE_TIME, EventEnum.MOVE, elevator));
			break;
		case OPEN_EXIT:
			System.out.println("Elevator-" + elevator.getElevNum() + " opened to let someone off");
			Simulator.getPQ().add(new Event(time, EventEnum.EXIT, elevator));
			break;
		case EXIT:
			if(!elevator.isEmpty()) {
				if(gettingOffOpenElevator(floor)) {
					break;
				}
			}
			//Either it's empty, or it's not, but nobody is getting off on this floor.
			System.out.println("Elevator-" + elevator.getElevNum() + " on floor " + elevator.getFloor() + " began to close");
			Simulator.getPQ().add(new Event(time + 1, EventEnum.CLOSE, elevator));
			break;
		}
	}

	/**
	 * Called to see if someone is getting off the closed elevator (after it reaches a floor, before it opens). If they are, it adds
	 * an OPEN_EXIT to the pq. If not, it returns false, and doesn't add anything (the run() method will then see what the other
	 * possibilities are).
	 * @param floor The floor the elevator is on.
	 * @param elevator The elevator that reached this floor.
	 * @return True if someone is getting off on this floor, false otherwise
	 */
	public boolean gettingOffClosedElevator(Floor floor, Elevator elevator) {
		for(Person person: elevator.getPeople()) {
			if(person.getDestination() == elevator.getFloor()) {
				System.out.println("Elevator-" + elevator.getElevNum() + " begins to open to let someone out");
				Simulator.getPQ().add(new Event(time   + OPEN_TIME, EventEnum.OPEN_EXIT, elevator));
				return true;
			}
		}
		return false;
	}

	/**
	 * Sees if someone is getting on this elevator on this floor. It first makes sure that the elevator isn't full, and that
	 * there is at least someone on the floor. It then iterates through the people on the floor, seeing if anyone is going in
	 * the same direction as the elevator. If so, it returns true, and adds OPEN_BOARD to the pq. If not, it returns false, and
	 * run() takes care of the other options.
	 * @param floor The floor the elevator is on
	 * @return True if someone is getting on this elevator on this floor, false otherwise
	 */
	public boolean gettingOnElevator(Floor floor) {
		if(floor.isEmpty() || elevator.getTotalPeople() >= Elevator.TOTAL_CAPACITY) {
			return false;
		}
		for(Person person: floor.getAllPeople()) {
			if(person.getDirection() == elevator.getDirection()) {
				Simulator.getPQ().add(new Event(time + OPEN_TIME, EventEnum.OPEN_BOARD, elevator));
				return true;
			}
		}
		return false;
	}

	/**
	 * Sees if anyone is getting off the open elevator. It iterates through the people on the elevator. If someone is getting off,
	 * they are removed from the elevator, and their wait time (plus however long it took to get off the elevator) is added to the
	 * total counter. If that happens, another EXIT is added to the pq, in case someone else is also getting off. 
	 * @param floor The floor the elevator is on
	 * @return True if someone is getting off this open elevator, false otherwise
	 */
	public boolean gettingOffOpenElevator(Floor floor) {
		for(Person person: elevator.getPeople()) {
			if(person.getDestination() == elevator.getFloor()) {
				Simulator.addPerson();
				elevator.getPeople().remove(person);
				Simulator.getPQ().add(new Event(time + EXIT_TIME, EventEnum.EXIT, elevator));
				System.out.println(person.getName() + " began getting off Elevator-" + elevator.getElevNum() + " on floor " + floor.getNumber());
				int waitTime = time - person.getStartTime() + EXIT_TIME;
				System.out.println("He waited " + waitTime + " seconds");
				Simulator.addToWait(waitTime);
				return true;
			}
		}
		return false;
	}
}
