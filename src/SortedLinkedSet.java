import java.util.Iterator;



public class SortedLinkedSet<E extends Comparable<E>> implements ISimpleSet<E> {
	private class Node{
		E info;
		Node next;
		Node prev;
		Node(E element, Node before,Node after){
			info = element;
			prev = before;
			next = after;
		}
	}
	private int mySize;     // number of elements in set
	private Node myFirst;   // points at header node
	private Node myLast;    // points at last node

	public SortedLinkedSet(){
		mySize = 0;
		myFirst = new Node(null,null,null); // header node
		myLast = myFirst;                   // initially no last node
	}

	/**
	 * Returns node containing element if there is such a node.
	 * Otherwise returns first node greater than element if there is such a node
	 * Otherwise returns null;
	 * @param element
	 * @return
	 */
	private Node find(E element){
		Node list = myFirst.next;
		while (list != null){
			if (list.info.compareTo(element) >= 0) {
				return list;
			}
			list = list.next;
		}
		return null;   // not found and greater than last element
	}

	public int size() {
		return mySize;
	}

	public boolean add(E element) {
		Node list = find(element);
		
		if (list == null){
			Node added = new Node(element,myLast,null);
			myLast.next = added;
			myLast = myLast.next;
			mySize++;
			return true;
		}
		if (list.info.compareTo(element) > 0) {
			Node added = new Node(element,list.prev,list);
			list.prev.next = added;
			list.prev = added;
			mySize++;
			return true;
		}
		return false;
	}

	public boolean remove(E element) {
		Node list = find(element);
		if (list != null ){
			if (list == myLast) {
				myLast = myLast.prev;
			}
			list.prev.next = list.next;
			if (list.next != null){
				list.next.prev = list.prev;
			}
			mySize--;
			return true;
		}
		return false;
	}

	public boolean contains(E element) {
		Node elt = find(element);
		if (elt == null) return false;
		if (elt.info.compareTo(element) > 0) return false;
		return true;
	}

	public Iterator<E> iterator() {
		return new LinkedIterator();
	}

	private class LinkedIterator implements Iterator<E>{

		private Node myPrevious;
		private Node myCurrent;
		public LinkedIterator(){
			myPrevious = null;
			myCurrent = myFirst.next;
		}
		public boolean hasNext() {
			return myCurrent != null;
		}

		public E next() {
			E element = myCurrent.info;
			myPrevious = myCurrent;
			myCurrent = myCurrent.next;
			return element;
		}

		public void remove() {
			myPrevious.prev.next = myCurrent;
			mySize--;
			if (myCurrent != null){
				myCurrent.prev = myPrevious.prev;
			}
		}

	}

}
