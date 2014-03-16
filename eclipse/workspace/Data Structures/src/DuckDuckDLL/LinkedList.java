package DuckDuckDLL;

import java.util.Iterator;

abstract public class LinkedList
{
	/** Inner class for nodes of a linked list. */
	protected static class Node
	{
		public Node next;
		public Node prev;
		public Object info;

		/** Makes an empty node. */
		public Node()
		{
			next = null;
			prev = null;
			info = null;
		}

		/**
		 * Makes a node storing an object.
		 *
		 * @param o The object to store.
		 */
		public Node(Object o)
		{
			next = null;
			prev = null;
			info = o;
		}
	}

	/**
	 * Returns the object stored in a node.
	 *
	 * @param node The node.
	 * @throws ClassCastException if <code>node</code> does not
	 * reference a <code>Node</code> object.
	 */
	public static Object dereference(Object node)
	{
		return ((Node) node).info;
	}

	/** Abstract inner class for an iterator. */
	abstract public class ListIterator implements Iterator<Object>
	{
		/** Returns <code>true</code> if this iterator has more
		 * elements, <code>false</code> otherwise. */
		abstract public boolean hasNext();

		/** Returns the next element in the iteration. */
		abstract public Object next();

		/** Removes the last element returned by the iterator. */
		abstract public void remove();
	}

	/**
	 * Inserts an element at the head of the list.
	 *
	 * @param o The element to be inserted.
	 * @return A handle to the new element.
	 */
	abstract public Object insert(Object o);

	/**
	 * Inserts an element after a given element.
	 *
	 * @param o The element to be inserted.
	 * @param after The element after which the new element is to be
	 * inserted.  If <code>null</code>, the new element is inserted at
	 * the head of the list.
	 * @return A handle to the new element.
	 */
	abstract public Object insertAfter(Object o, Object after);

	/** Removes an element.
	 *
	 * @param handle Handle to the element to remove.
	 */
	abstract public void delete(Object handle);

	/** Returns <code>true</code> if this list is empty,
	 * <code>false</code> otherwise. */
	abstract public boolean isEmpty();

	/** Creates and returns an <code>Iterator</code> object for this
	 * list. */
	abstract public Iterator<Object> iterator();

	/**
	 * Concatenates another linked list onto the end of this list,
	 * destroying the other linked list.
	 *
	 * @param l The linked list to be concatenated onto the end of
	 * this list.
	 */
	abstract public void concatenate(LinkedList l);

	/** Returns the <code>String</code> representation of this list. */
	public String toString()
	{
		String result = "";
		Iterator<Object> iter = iterator();

		while (iter.hasNext())
			result += iter.next() + "\n";

		return result;
	}

	/**
	 * Copies each element of this list into an array.
	 *
	 * @param array The array, assumed to be already allocated.
	 */
	public void toArray(Object[] array)
	{
		Iterator<Object> iter = iterator();
		int i = 0;

		while (iter.hasNext())
			array[i++] = iter.next();
	}
}
