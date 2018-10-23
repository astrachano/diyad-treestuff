

import java.util.Iterator;

/**
 * Linked list implementation of sets. All operations are O(n).
 * @author Owen Astrachan
 */

public class LinkedSet<E> implements ISimpleSet<E> {
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
    
    public LinkedSet(){
        mySize = 0;
        myFirst = new Node(null,null,null); // header node
    }
    
    private Node find(E element){
        Node list = myFirst.next;
        while (list != null && ! list.info.equals(element)){
            list = list.next;
        }
        return list;
    }
    
    public int size() {
        return mySize;
    }

    public boolean add(E element) {
        Node list = find(element);
        if (list == null){
            Node added = new Node(element,myFirst,myFirst.next);
            myFirst.next = added;
            if (added.next != null){
                added.next.prev = added;
            }
            mySize++;
            return true;
        }
        return false;
    }

    public boolean remove(E element) {
        Node list = find(element);
        if (list != null){
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
        return find(element) != null;
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
