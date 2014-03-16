package DuckDuckDLL;

import java.util.Iterator;

/**
* A circular, doubly linked list with a sentinel from
* pages 206-207 of <i>Introduction to Algorithms</i>, Second
* edition.
*/

public class SentinelDLL extends LinkedList
{
   /** The sentinel. */
   protected final Node nil;

   /** Makes an empty list, consisting of only the sentinel. */
   public SentinelDLL()
   {
	// Create a sentinel and make it into a circular list. 
	nil = new Node();
	nil.next = nil;
	nil.prev = nil;
   }

   /** Inner class for an iterator. */
   public class SentinelDLLIterator extends ListIterator
   {
	/**
	 * A reference to the <code>Node</code> returned by the most
	 * recent call to <code>next</code>.  Initially, it is the
	 * sentinel.
	 */
	protected Node current;

	/** Starts an iteration. */
	public SentinelDLLIterator()
	{
	    current = nil;
	}

	/** Returns <code>true</code> if this iterator has more
	 * elements, <code>false</code> otherwise. */
	public boolean hasNext()
	{
	    return current.next != nil;
	}

	/** Returns the next element in the iteration. */
	public Object next()
	{
	    current = current.next;
	    return current.info;
	}

	/**
	 * Removes the last element returned by the iterator.
	 *
	 * @throws IllegalStateException if <code>next</code> has not
	 * been called.
	 */
	public void remove()
	{
	    if (current == nil)
		throw new IllegalStateException("Called remove before iterator returned an object.");
	    else {
		// Save current's predecessor, since after we remove
		// current, it will be the most recently returned node
		// in the iteration.
		Node newCurrent = current.prev;

		delete(current); // delete the current node

		current = newCurrent;
	    }
	}
   }

   /**
    * Inserts an element at the head of the list.
    *
    * @param o The element to be inserted.
    * @return A handle to the new element.
    */
   public Object insert(Object o)
   {
	Node x = new Node(o);
	x.next = nil.next;
	nil.next.prev = x;
	nil.next = x;
	x.prev = nil;
	return x;
   }

   /**
    * Inserts an element after a given element.
    *
    * @param o The element to be inserted.
    * @param after The element after which the new element is to be
    * inserted.  If <code>null</code>, the new element is inserted at
    * the head of the list.
    * @return A handle to the new element.
    */
   public Object insertAfter(Object o, Object after)
   {
	if (after == null)
	    return insert(o);
	else {
	    Node x = new Node(o);
	    Node predecessor = (Node) after;
	    x.next = predecessor.next;
	    predecessor.next.prev = x;
	    predecessor.next = x;
	    x.prev = predecessor;
	    return x;
	}
   }

   /**
    * Inserts an element at the tail of the list.
    *
    * @param o The element to be inserted.
    * @return A handle to the new element.
    */
   public Object insertAtTail(Object o)
   {
	return insertAfter(o, nil.prev);
   }

   /** Removes an element.
    *
    * @param handle Handle to the element to remove.
    * @throws DeleteSentinelException if <code>handle</code>
    * references the sentinel <code>nil</code>.
    */
   public void delete(Object handle)
   {
	Node x = (Node) handle;
	x.prev.next = x.next;
	x.next.prev = x.prev;
	x.prev = null;
	x.next = null;
   }

   /** Returns <code>true</code> if this list is empty,
    * <code>false</code> otherwise. */
   public boolean isEmpty()
   {
	return nil.next == nil;
   }

   /** Creates and returns an <code>Iterator</code> object for this
    * list. */
   public Iterator iterator()
   {
	return new SentinelDLLIterator();
   }

   public void concatenate(LinkedList l)
   /**
    * Concatenates another linked list onto the end of this list,
    * destroying the other linked list.
    *
    * @param l The linked list to be concatenated onto the end of
    * this list.
    * @throws ClassCastException if <code>l</code> is not a
    * <code>LinearDLL</code> object.
    */
   {
	SentinelDLL other = (SentinelDLL) l;
	nil.prev.next = other.nil.next;
	other.nil.next.prev = nil.prev;
	other.nil.prev.next = nil;
	nil.prev = other.nil.prev;
   }	
}

//$Id: SentinelDLL.java,v 1.1 2003/10/14 16:56:20 thc Exp $
//$Log: SentinelDLL.java,v $
//Revision 1.1  2003/10/14 16:56:20  thc
//Initial revision.
//

