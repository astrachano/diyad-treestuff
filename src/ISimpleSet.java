

/**
 * This generic class illustrates simple imlementations
 * of sets for the purposes of understanding and reasoning
 * about tradeoffs. For example, we consider hashing, search trees,
 * AVL trees and tries as set implementations.
 * @author Owen Astrachan
 * @version 2.0, October 2006
 */

import java.util.Iterator;

public interface ISimpleSet<E> extends Iterable<E> {
    /**
     * Returns the number of elements in this set.
     * @return size of set
     */
    public int size();
    
    /**
     * Adds an element to the set (no duplicates). Returns true if and only if
     * the element was added, i.e., not already stored in the set.
     * @param element to be added to set
     * @return true if element not previously in set, false otherwise
     */
    public boolean add(E element);
    
    /**
     * Removes an element from the set and returns true if removal successful.
     * @param element to be removed from set
     * @return true if element removed, false otherwise
     */
    public boolean remove(E element);
    
    /**
     * Returns true if element in set, false otherwise.
     * @param element is candidate for query in set
     * @return true if element in set, false otherwise
     */
    public boolean contains(E element);
    
    /**
     * Returns an iterator, necessary for Iterable interface.
     * @return an iterator over this set's elements
     */
    public Iterator<E> iterator();
}
