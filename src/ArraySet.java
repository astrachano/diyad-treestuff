
import java.util.*;

/**
 * Simple implementation of set using an ArrayList for
 * implementation. Operations add, contains, and remove
 * are all O(n) for an n-element set.
 * <P>
 * @author Owen Astrachan
 */
public class ArraySet<E> implements ISimpleSet<E>{

    protected ArrayList<E> myList;
    
    public ArraySet(){
        myList = new ArrayList<E>();
    }
    public int size() {
        return myList.size();
    }

    public boolean add(E element) {
       if (myList.indexOf(element) < 0){
           myList.add(element);
           return true;
       }
       else {
           return false;
       }
    }

    public boolean remove(E element) {
        int index = myList.indexOf(element);
        if (index < 0){
            return false;
        }
        else {
            myList.remove(element);
            return true;
        }
    }

    public boolean contains(E element) {
        return myList.indexOf(element) >= 0;
    }

   
    public Iterator<E> iterator() {
        return myList.iterator();
    }

}
