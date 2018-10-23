
import java.util.*;

public class AVLSet<E extends Comparable<E>> implements ISimpleSet<E> {

    private class TreeNode {
        E info;

        TreeNode left;
        TreeNode right;
        TreeNode parent;
        int height;

        TreeNode(E element, TreeNode lptr, TreeNode rptr, TreeNode p) {
            info = element;
            left = lptr;
            right = rptr;
            parent = p;
            height = 1;
        }
    }

    private int mySize;

    private TreeNode myRoot;

    public AVLSet() {
        mySize = 0;
        myRoot = null;
    }

    public int size() {
        return mySize;
    }
    
    private int height(TreeNode t){
        if (t == null) return 0;
        else return t.height;
    }
    
    private TreeNode doLeft(TreeNode t){
        TreeNode newRoot = t.left;
        t.left = newRoot.right;
        if (newRoot.right != null){
            newRoot.right.parent = t;
        }
        newRoot.right = t;
        newRoot.parent = t.parent;
        t.parent = newRoot;
        t.height = 1 + Math.max(height(t.left), height(t.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;     
    }
    
    private TreeNode doRight(TreeNode t){
        TreeNode newRoot = t.right;
        t.right = newRoot.left;
        if (newRoot.left != null){
            newRoot.left.parent = t;
        }
        newRoot.left = t;
        newRoot.parent = t.parent;
        t.parent = newRoot;
        t.height = 1 + Math.max(height(t.left), height(t.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    private TreeNode insert(E element, TreeNode t, TreeNode parent){
        if (t == null){
            return new TreeNode(element,null,null,parent);
        }
        else {
            int comp = element.compareTo(t.info);
            if (comp < 0){
                // left subtree at least as deep as right
                t.left = insert(element, t.left,t);
                if (height(t.left) - height(t.right) > 1){
                    if (element.compareTo(t.left.info) < 0){
                        t = doLeft(t);
                    }
                    else {
                        t.left = doRight(t.left);
                        t = doLeft(t);
                    }
                }
            }
            else if (comp > 0){
                t.right = insert(element,t.right,t);
                if (height(t.right) - height(t.left) > 1){
                    if (element.compareTo(t.right.info) > 0){
                        t = doRight(t);
                    }
                    else {
                        t.right = doLeft(t.right);
                        t = doRight(t);
                    }
                }
            }
            t.height = 1 + Math.max(height(t.left), height(t.right));
            return t;
        }
    }
    
    public boolean add(E element) {
        if (myRoot == null) {
            myRoot = new TreeNode(element, null, null, null);
            mySize++;
            return true;
        }
        if (contains(element)) return false;
        mySize++;
        myRoot = insert(element,myRoot,null);
        return true;

    }

    public boolean remove(E element) {
        return false;
    }

    private TreeNode successor(TreeNode t) {
        if (t == null)
            return null; // no successor
        else if (t.right != null) {
            t = t.right;
            while (t.left != null) {
                t = t.left;
            }
            return t;
        } else {
            TreeNode parent = t.parent;
            while (parent != null && parent.right == t) {
                t = parent;
                parent = t.parent;
            }
            return parent;
        }
    }

    public boolean contains(E element) {
        TreeNode root = myRoot;
        while (root != null) {
            int comp = root.info.compareTo(element);
            if (comp == 0)
                return true;
            else if (comp > 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new TreeIterator(myRoot);
    }

    private class TreeIterator implements Iterator<E> {

        private TreeNode myCurrent;
        private TreeNode myPrevious;

        public TreeIterator(TreeNode root) {
            while (root.left != null) {
                root = root.left;
            }
            myCurrent = root;
            myPrevious = null;
        }

        public boolean hasNext() {
            return myCurrent != null;
        }

        public E next() {
            E data = myCurrent.info;
            myPrevious = myCurrent;
            myCurrent = successor(myCurrent);
            //System.out.println(myCurrent.info);
            return data;
        }

        public void remove() {
           throw new UnsupportedOperationException("no AVL iterator remove");
        }
    }

}
