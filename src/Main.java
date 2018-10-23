

import java.io.*;
import java.util.*;
public class Main {
    
    public static void print(ISimpleSet<String> ss){
        for(String s : ss){
            System.out.println(s);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        Scanner scan = new Scanner(new File("/data/poe.txt"));
        ISimpleSet<String> set = new TreeSetSet<>();
        TreeSet<String> treeset = new TreeSet<>();
        set = new SortedArraySet<>();
        set = new AVLSet<>();
        set = new SortedLinkedSet<>();
        
        while (scan.hasNext()) {
        		String s = scan.next().toLowerCase();
        		set.add(s);
        		treeset.add(s);
        }
        print(set);
        System.out.printf("size = %d\n", set.size());
        scan.close();
        
        int size = set.size();
        for(String s : treeset) {
        	    set.remove(s);
        	    size--;
        	    if (set.size() != size) {
        	    		System.out.printf("error on removing %s at %d\n",s,size);
        	    }      	    
        }
        
    }
}
