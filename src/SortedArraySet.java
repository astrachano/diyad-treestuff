

import java.util.*;

public class SortedArraySet<E extends Comparable<E>> extends ArraySet<E> {

    public boolean add(E element) {
        int index = Collections.binarySearch(myList,element);
        if (index < 0){
            index = -(index+1);
            myList.add(index,element);
            return true;
        }
        return false;
    }

    public boolean remove(E element) {
        int index = Collections.binarySearch(myList,element);
        if (index < 0){
           return false;
        }
        myList.remove(index);
        return true;
    }

    public boolean contains(E element) {
        return Collections.binarySearch(myList,element) >= 0;
    }

   
    public Iterator<E> iterator() {
        return myList.iterator();
    }

}
