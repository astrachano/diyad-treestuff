

import java.util.*;
 
public class TreeSetSet<E> implements ISimpleSet<E> {
    private TreeSet<E> mySet;
    public TreeSetSet(){
        mySet = new TreeSet<E>();
    }
    public int size() {
        return mySet.size();
    }

    public boolean add(E element) {
        return  mySet.add(element);
    }

    public boolean remove(E element) {
        return mySet.remove(element);
    }

    public boolean contains(E element){
        return mySet.contains(element);
    }

    public Iterator<E> iterator() {
       return mySet.iterator();
    }

}
