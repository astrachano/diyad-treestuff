

import java.util.*;

/**
 * Simple trie with each node mapping characters to children
 * Useful to examine trade-offs in set implementations
 * @author ola
 *
 */

public class TrieSet implements ISimpleSet<String> {

    public static class Node {
        char info;
        boolean isWord;   // true if path ending here is a word

        Map<Character,Node> children;
        Node parent;

        Node(char ch, Node p) {
            info = ch;
            isWord = false;
            children = new TreeMap<Character,Node>();
            parent = p;
        }
    }

    private Node myRoot; // root of entire trie
    private int mySize;  // number of elements in set/trie

    public TrieSet() {
        myRoot = new Node('x', null);
        mySize = 0;
    }

    public int size() {
        return mySize;
    }

    /**
     * Add s to this set
     * @return true if s added, false if s already in set
     */
    public boolean add(String s) {
        Node t = myRoot;
        for (int k = 0; k < s.length(); k++) {
           
            char ch = s.charAt(k);
            Node child = t.children.get(ch);
            if (child == null) {
               child = new Node(ch, t);
               t.children.put(ch,child);
            }
            t = child;
        }
        
        if (!t.isWord) {
            t.isWord = true; // walked down path, mark this as a word
            mySize++;
            return true;
        }
        return false; // already in set
    }
   
    /**
     * Remove s from this set
     * @return true if s removed, else false
     */
    public boolean remove(String s) {
        Node t = myRoot;
        for (int k = 0; k < s.length(); k++) {
            char ch = s.charAt(k);
            t = t.children.get(ch);
            if (t == null)
                return false; // no path below? done
        }
        if (t.isWord) {
            t.isWord = false;
            mySize--;
            return true;
        }
        return false;
    }

    /**
     * Is s in this set?
     * @return true if s is in this set, else false
     */
    public boolean contains(String s) {
        Node t = myRoot;
        for (int k = 0; k < s.length(); k++) {
            char ch = s.charAt(k);
            t = t.children.get(ch);
            if (t == null)
                return false; // no path below? done
        }
        return t.isWord; // was this marked as a word?
    }

    public Iterator<String> iterator() {
        return new TrieIterator(myRoot);
    }

    
    private class TrieIterator implements Iterator<String>
    {
        private Node myCurrent;
        private Node myPrevious;
        private IdentityHashMap<Node, Object> mySeen = new IdentityHashMap<>();
        private  Object thing = new Object();
        StringBuffer myPath;
        
        public TrieIterator(Node root){
            myPrevious = null;
            myPath = new StringBuffer();
            myCurrent = findWordBelow(root);
        }
        
        private Node findWordBelow(Node root){
            
            if (root == null){
                return null;
            }
            if (root.isWord && !mySeen.containsKey(root)){
                mySeen.put(root,thing);
                return root;
            }
            for(Node n : root.children.values()){
                myPath.append(n.info);
                Node recur = findWordBelow(n);
                if (recur != null){
                    return recur;
                }
                myPath.deleteCharAt(myPath.length()-1);
            }
            return null;
        }
        
        private Node nextWord(Node root){
            Node recur = findWordBelow(root);
            if (recur != null){
                return recur;
            }
          
            while (root != null){
                Node last = root;
                root = root.parent;
                if (root == null) break; // backed up through global root
                
                myPath.deleteCharAt(myPath.length()-1);
                for(Node n : root.children.values()) {
                    if (!mySeen.containsKey(n)){
                        myPath.append(n.info);
                        n = findWordBelow(n);
                        if (n != null){
                            return n;
                        }
                    }
                }
            }
            return null;
        }
        
        public boolean hasNext() {
            return myCurrent != null;
        }

        public String next() {
            String s = new String(myPath);
            myPrevious = myCurrent;
            myCurrent = nextWord(myCurrent);
            return s;
        }

        public void remove() {
            if (myPrevious != null){
                myPrevious.isWord = false;
                myPrevious = null;
                mySize--;
            }
            else {
                throw new IllegalStateException("bad remove from Trie iterator");
            }
        }
        
    }

}
