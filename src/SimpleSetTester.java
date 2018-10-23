
import javax.swing.*;

import java.io.File;
import java.util.*;

public class SimpleSetTester {
    static JFileChooser ourChooser = 
        new JFileChooser(System.getProperties().getProperty("user.dir"));

    
    public double addAll(String[] list, ISimpleSet<String> set, int expectedSize){
        double start = System.nanoTime();
        for(String s : list){
          set.add(s);
        }
        double end = System.nanoTime();
        if (set.size() != expectedSize){
            throw new RuntimeException("bad addAll");
        }
        return (end-start)/1e9;
    }
    
    public double iterSize(ISimpleSet<String> set){
        int count = 0;
        double start = System.nanoTime();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            count++;
            it.next();
        }
        double end = System.nanoTime();
        if (count != set.size()){
            throw new RuntimeException("bad size in itersize");
        }
        return (end-start)/1e9;
    }
    
    public double iterableSize(ISimpleSet<String> set){
        double start = System.nanoTime();
        int count = 0;
        for(String s : set) count++;
        double end = System.nanoTime();
        if (count != set.size()){
            throw new RuntimeException("bad size in itersize");
        }
        return (end-start)/1e9;
    }
    
    public double removeAll(String[] list, ISimpleSet<String> set){
        double start = System.nanoTime();
        for(String s : list){
            set.remove(s);
        }
        double end = System.nanoTime();
        if (set.size() != 0){
            throw new RuntimeException("non empty set after remove all");
        }
        return (end-start)/1e9;
    }
    
    public double queryAll(String[] list, ISimpleSet<String> set){
        double start = System.nanoTime();
        for(String s : list){
            if (!set.contains(s)){
                throw new RuntimeException("could not find "+s);
            }
        }
        double end = System.nanoTime();
        return (end-start)/1e9;
    }
    
    public double iterateRemove(ISimpleSet<String> set){
        double start = System.nanoTime();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            it.next();
            it.remove();
        }
        double end = System.nanoTime();
        if (set.size() != 0){
            throw new RuntimeException("non empty set in iterate remove");
        }
        return (end-start)/1e9;
    }
    
    public void stress(String[] list, ISimpleSet<String> set, String name, int expectedSize){
    
        System.out.print(name+"\t"+list.length+"\t"+expectedSize+"\t");
        
        double time = addAll(list,set,expectedSize);
        System.out.print(time+"\t");
        
        time = removeAll(list,set);
        System.out.print(time+"\t");
        
        time = addAll(list,set,expectedSize);
        System.out.print(time+"\t");
        
        time = queryAll(list,set);
        System.out.print(time+"\t");
        
        time = iterSize(set);
        System.out.print(time+"\t");
        time = iterableSize(set);
        System.out.print(time+"\t");
        
        time = iterateRemove(set);
        System.out.print(time+"\t");
        
        System.out.println();
    }
    
    public static void main(String[] args){

        int retval = ourChooser.showOpenDialog(null);

        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = ourChooser.getSelectedFile();
            try {
                Scanner s = new Scanner(file);
                ArrayList<String> list = new ArrayList<String>();
                while (s.hasNext()){
                    list.add(s.next());
                }
                String[] array = list.toArray(new String[0]);
                HashSet<String> hashed = new HashSet<String>(list);
                SimpleSetTester sst = new SimpleSetTester();
                ISimpleSet<String> linkedSet = new LinkedSet<String>();
                ISimpleSet<String> arraySet = new ArraySet<String>();
                ISimpleSet<String> sortedArraySet = new SortedArraySet<String>();
                ISimpleSet<String> treeSetSet = new TreeSetSet<String>();
                ISimpleSet<String> bstSet = new BSTSet<String>();
                ISimpleSet<String> avlSet = new AVLSet<String>();
                ISimpleSet<String> hset = new SimpleHashSet<String>();
                int size = hashed.size();
                
                System.out.println("testing: "+file.getName());
                System.out.println("kind\tsize\tssize\tadd\tdel\tadd\tquery\titer\tfor\titrem");
                System.out.println("----\t-----\t----\t---\t---\t---\t-----\t----\t---\t-----");
                
                sst.stress(array,linkedSet, "link",size);
                sst.stress(array,arraySet, "array",size);
                sst.stress(array,sortedArraySet, "sarray",size);
                sst.stress(array,treeSetSet, "trees",size);
                sst.stress(array,bstSet, "bst",size);
                sst.stress(array,hset,"hash",size);
                sst.stress(array,avlSet, "avl",size);
            }
            catch (Exception e){
                System.out.println("trouble "+e);
                e.printStackTrace();
                System.exit(0);
            }
        }
        System.exit(1);
    }
}
