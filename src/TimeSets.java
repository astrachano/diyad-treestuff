import java.util.*;
import java.io.*;

public class TimeSets {
	public static double timer(ISimpleSet<String> set,
			            		  File input) throws FileNotFoundException {
		Scanner scan = new Scanner(input);
		ArrayList<String> list = new ArrayList<>();
		while (scan.hasNext()) {
			list.add(scan.next().toLowerCase());
		}
		double start = System.nanoTime();
		for(String s : list) {
			set.add(s);
		}
		for(String s : list) {
			set.remove(s);
		}
		for(String s : list) {
			set.add(s);
		}
		double end = System.nanoTime();
		scan.close();
		return (end-start)/1e9;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		String source = "/data/hawthorne.txt";
		File f = new File(source);
		ArrayList<ISimpleSet<String>> sets =
				new ArrayList<>();
		
		sets.add(new TreeSetSet<>());
		sets.add(new BSTSet<>());
		sets.add(new ArraySet<>());
		sets.add(new SimpleHashSet<>());
		sets.add(new SortedArraySet<>());
		sets.add(new LinkedSet<>());
		sets.add(new SortedLinkedSet<>());
		
		System.out.printf("timing for %s\n", f.getName());
		for(ISimpleSet<String> set : sets) {
			double time = timer(set,f);
			System.out.printf("%12s\t%d\t%1.3f\n", set.getClass().getName(),
					                             set.size(),time);
		}
	}
}
