/**
 * Write a description of class ArrayList here.
 * 
 * isEmpty, size, contains,
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class LinkedList
{
	private Node head;
	private Node tail;
	private int size;


	/**
	 * Constructor for objects of class LinkedList. This creates a single node,
	 * and passes its data parameter as the data for the head Node.
	 */
	public LinkedList(Object data)
	{
		head = new Node(data);
		tail = head;
		size = 1;
	}
	
	
	/**
	 * Returns the node at the given position (not index).
	 * @param position
	 * @return
	 */
	private Node getNode(int position) {
		if(position < 1 || position > size) {
			System.out.println("Invalid Position");
			return null;
		}
		//These two are just for a little optimazation.
		else if(position == size) {
			return tail;
		}
		else if(position == 1) {
			return head;
		}
		//For potentially more significant optimazation.
		else {
			if (position < size / 2) {
				// Go from the head
				Node currentNode = head;
				for (int i = 1; i < position; i++) {
					currentNode = currentNode.nextNode;
				}
				return currentNode;
			}
			else {
				// Go from the tail
				Node currentNode = tail;
				for (int i = size; i > position; i--) {
					currentNode = currentNode.prevNode;
				}
				return currentNode;
			}
		}
	}

	/**
	 * Not yet gonna deal with the border cases. For now, I'll just make
	 * separate methods for them. Also, as I made this before making the tail of
	 * the LL, I automatically started from the head, even though it may be less
	 * efficient.
	 * 
	 * @parem position: The position that this new Node will take.
	 */

	//
	public void add(int position, Object newData)
	{
		//Need these two methods so I don't run into Out Of
		//Bounds exceptions.
		if(position > size || position < 1) {
			System.out.println("Invalid position");
			//Until I figure out how to break in a method
			size--;
		}			
		else if (position == size) {
			add(newData);
		}
		else if (position == 1) {
			addBegin(newData);
		}
		else {
			Node tempNode = getNode(position);
			//Creates the new Node, and changes its prevNode (to the one originally in that position) 
			//and NextNode (to the nextNode of the one originally in that position).
			Node newNode = new Node(newData);
			newNode.nextNode = tempNode.nextNode;
			newNode.prevNode = tempNode;
			//Changes the nextNode and prevNode of the one in that
			//original position.
			tempNode.nextNode.prevNode = newNode;
			tempNode.nextNode = newNode;
		}
		size++;
	}

	/**
	 * Adds a new Node to the end of the list
	 * 
	 * @author Shmuel
	 */
	private void add(Object data)
	{
		Node newNode = new Node(data);
		tail.nextNode = newNode;
		newNode.prevNode = tail;
		newNode.nextNode = null;
		tail = newNode;
		size++;
	}

	/**
	 * Adds a new node to the beginning of the list.
	 * 
	 * @author Shmuel
	 * 
	 */

	private void addBegin(Object data)
	{
		Node newNode = new Node(data);
		newNode.nextNode = head;
		head.prevNode = newNode;
		newNode.prevNode = null;
		head = newNode;
		size++;
	}

	/**
	 * Returns the data at that node. Will decide at the beginning whether it's
	 * faster to go from the front or from the back. Using integers, not
	 * floating, because I don't really care about that level of precision.
	 * 
	 * @author Shmuel
	 * 
	 */
	public Object get(int position)
	{
		if (position < size / 2) {
			// Go from the head
			Node currentNode = head;
			for (int i = 1; i < position; i++) {
				currentNode = currentNode.nextNode;
			}
			return currentNode.data;
		}
		else {
			// Go from the tail
			Node currentNode = tail;
			for (int i = 0; i < size - position; i++) {
				currentNode = currentNode.prevNode;
			}
			return currentNode.data;
		}

	}

	/**
	 * Removes the Object at the given index. Much copy-pasted from the getData
	 * method. 
	 * 
	 * @author Shmuel
	 * 
	 */
	public void remove(int position)
	{
		if(position == 1) {
			removeHead();
		}
		else if(position == size) {
			removeTail();
		}
		else {
			Node currentNode = getNode(position);
			currentNode.nextNode.prevNode = currentNode.prevNode;
			currentNode.prevNode.nextNode = currentNode.nextNode;
		}
		size--;

	}
	
	private void removeHead() {
		head.nextNode = head;
		head.prevNode = null;
	}
	
	private void removeTail() {
		tail.prevNode = tail;
		tail.nextNode = null;
	}
	
	/**
	 * Clears all the data in the linked list, by getting rid of all the 
	 * other nodes past the head.
	 */
	public void clear() {
		head.nextNode = null;
		//? Should this go here??     head.data = null;
		tail = head;
	}
	
	/**
	 * Searches the list to see if the object is contained in that list.
	 * @param object
	 * @return
	 */
	public boolean contains(Object object) {
		Node currentNode = head;
		int i = 0;
		do{
			if(currentNode.data.equals(object)) {
				return true;
			}
			else {
				currentNode = currentNode.nextNode;
				i++;
			}
		} while(i < size);
			return false;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}

	public static class Node
	{
		private Node nextNode;
		private Node prevNode;
		private Object data;

		/**
		 * Note that the constructor does not set the data. That has to be
		 * entered in manually later. I can let it do that, but that will be
		 * ugly for the LL constructor.
		 */

		public Node(Object data)
		{
			this.data = data;
			nextNode = null;
			prevNode = null;
		}
	}
}
