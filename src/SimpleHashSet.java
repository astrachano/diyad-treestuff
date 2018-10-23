

import java.util.*;

/**
 * Hashing implementation of a set. Operations are O(1) in average case 
 * and really bad in the worst case.
 * @author Owen Astrachan
 */

public class SimpleHashSet<E> implements ISimpleSet<E> {

     private static class Node<E> {
        E info;
        Node<E> next;
        Node<E> prev;
        
        public Node(E thing, Node<E> link) {
            info = thing;
            next = link;
        }
    }

    private static final int SIZE = 23179;

    private Node<E>[] myList;
    private int mySize;

    public SimpleHashSet() {
    	@SuppressWarnings({"unchecked"})
        Node<E>[] list = (Node<E>[]) new Node[SIZE];
    	myList = list;
        mySize = 0;
    }

    public int size() {
        return mySize;
    }

    public boolean add(E element) {
        int index = Math.abs(element.hashCode()) % SIZE;

        Node<E> list = myList[index];
        while (list != null) {
            if (list.info.equals(element)) {
                return false;
            }
            list = list.next;
        }
        // didn't find element in this bucket, add to front of bucket
        
        myList[index] = new Node<E>(element, myList[index]);
        if (myList[index].next != null){
            myList[index].next.prev = myList[index];
        }
        mySize++;
        return true;
    }

    public boolean remove(E element) {
        int index = Math.abs(element.hashCode()) % SIZE;
        Node<E> list = myList[index];
        Node<E> before = null;
        while (list != null) {
            if (list.info.equals(element)) {
                
                if (before == null){
                    myList[index] = list.next;
                }
                else {
                    before.next = list.next;
                }
                if (list.next != null){
                		list.next.prev = before;
                }
                mySize--;
                return true;
            }
            before = list;
            list = list.next;
        } 
        return false;
    }

    public boolean contains(E element) {
        int index = Math.abs(element.hashCode()) % SIZE;

        Node<E> list = myList[index];
        while (list != null) {
            if (list.info.equals(element)) {
                return true;
            }
            list = list.next;
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new HashingIterator();
    }

    private class HashingIterator implements Iterator<E>{

        int myCurrentIndex;
        int myLastIndex;
        Node<E> myCurrentNode;
        Node<E> myPrevious;
        public HashingIterator(){
            myLastIndex = -1;
            myCurrentIndex = 0;
            myPrevious = null;
            myCurrentNode = findNonEmptyBucketAfter(-1);
        }
        
        public Node<E> findNonEmptyBucketAfter(int index){
            myLastIndex = myCurrentIndex;
            for(int k= index+1; k < SIZE; k++){
                if (myList[k] != null){
                    myCurrentIndex = k;
                    return myList[k];
                }
            }
            return null;
        }
        
        public boolean hasNext() {
            return myCurrentNode != null;
        }

        public E next() {
            E s = myCurrentNode.info;
            myPrevious = myCurrentNode;
            if (myCurrentNode.next != null){           
                myCurrentNode = myCurrentNode.next;
            }
            else {
                myCurrentNode = findNonEmptyBucketAfter(myCurrentIndex);
            }
            return s;
        }

        public void remove() {
            if (myPrevious ==  null){
                throw new NoSuchElementException("nothing to remove from iterator");
            }
            mySize--;
            if (myPrevious.prev == null){
                myList[myLastIndex] = myPrevious.next;
            }
            else {
                myPrevious.prev.next = myPrevious.next;
            }
            myPrevious = null;
        }
        
    }


}
