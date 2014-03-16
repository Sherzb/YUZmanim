package DuckDuckDLL;

import java.util.Iterator;

/**
 * An iterator for a circular DLL. Basically the same thing as a regular DLL iterator, but when
 * the iterator reaches the iterator (i.e. the end of the list), it loops back to the first
 * element of the DLL.
 * @author Shmuel
 *
 */
public class CircularSentinelDLL extends SentinelDLL {
	
	/**
	 * This is so that an iterator of type CSDLLIterator is returned, instead of that of
	 * SentinelDLLIterator. I know it has a warning, but I can't override iterator if I change 
	 * the signature. And anyway, I think it should be generic...
	 */
	@Override
	public Iterator iterator() {
		return new CSDLLIterator();
	}
	
	public class CSDLLIterator extends SentinelDLLIterator{
		
		public CSDLLIterator() {
			current = nil;
		}
		
		/**
		 * @return true if the list is not empty, false if it is
		 */
		public boolean hasNext() {
			return nil.next != nil;
			//The only time the list will be empty is if nil's next is also nil, as nil is the sentinel.
		}
		
		/**
		 * Simply returns the next Object's info, or the one after that, if the next one is the sentinel.
		 * @return The object that is next in the iteration. 
		 */
		public Object next() {
			//Advance the iterator
			current = current.next;
			//Advance it again if it's on the sentinel
			if(current == nil) {
				current = current.next;
			}
			return current.info;
		}
	}
}
