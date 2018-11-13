

import java.util.*;

/**
 * Simple binary search tree implementation of a set. Operations are O(log n) 
 * in average case and O(n) in the worst case for unbalanced trees.
 * @author Owen Astrachan
 */


public class BSTSet<E extends Comparable<E>> implements ISimpleSet<E> {

	/**
	 * TreeNode class includes parent pointer for iteration and deletion
	 */
    private class TreeNode {
        E info;

        TreeNode left;
        TreeNode right;
        TreeNode parent;

        TreeNode(E element, TreeNode lptr, TreeNode rptr, TreeNode p) {
            info = element;
            left = lptr;
            right = rptr;
            parent = p;
        }
    }

    /**
     * Invariant: mySize is number of elements in set
     * myRoot points to root of entire binary search tree
     */
    private int mySize;
    private TreeNode myRoot;

    /**
     * Create an empty set
     */
    public BSTSet() {
        mySize = 0;
        myRoot = null;
    }

    /**
     * Return number of elements in this set as an O(1) operation
     * @return the number of elements in the set
     */
    public int size() {
        return mySize;
    }

    /**
     * Add an element to the set
     * @param element is value being added to set
     * @return true if element added, false if duplicate and already in set
     */
    public boolean add(E element) {
        if (myRoot == null) {
            myRoot = new TreeNode(element, null, null, null);
            mySize++;
            return true;
        }
        TreeNode root = myRoot;

        while (root != null) {
            int comp = root.info.compareTo(element);
            if (comp == 0)
                return false;
            if (comp > 0) {
                if (root.left == null) {
                    root.left = new TreeNode(element, null, null, root);
                    mySize++;
                    return true;
                } else {
                    root = root.left;
                }
            } else {
                if (root.right == null) {
                    root.right = new TreeNode(element, null, null, root);
                    mySize++;
                    return true;
                } else {
                    root = root.right;
                }
            }
        }
        // can never reach here
        return false;

    }

    /**
     * Remove an element from this set
     * @param element is value being removed
     * @return true if element removed, false if not in set
     */
    public boolean remove(E element) {
        TreeNode root = myRoot;
        while (root != null) {
            int comp = root.info.compareTo(element);
            if (comp == 0) {
                mySize--;
                remove(root);
                return true;
            } else if (comp > 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return false;
    }

    private void remove(TreeNode root) {
        if (root.left == null && root.right == null) {
            // removing leaf
            if (root.parent == null) { // removing root?
                myRoot = null; // tree now empty
            } else {
                if (root.parent.left == root) {
                    root.parent.left = null;
                } else {
                    root.parent.right = null;
                }
            }
        } else if (root.left == null || root.right == null) {
            // one child, not two
            TreeNode child = root.left;  // only child is left?
            if (root.left == null) {     // nope, it's right
                child = root.right;
            }
            if (root.parent == null) {   // new root
                myRoot = child;
            } else if (root.parent.left == root) {
                root.parent.left = child;
            } else {
                root.parent.right = child;
            }
            child.parent = root.parent;
        } else {                          // removing node with two children
            TreeNode successor = root.right;
            if (successor.left == null) {
                root.info = successor.info;
                root.right = successor.right;
                if (successor.right != null) {
                    successor.right.parent = root;
                }
            } else {                    
                // immediate right child of removed node has a left child
                while (successor.left != null) {
                    successor = successor.left;
                }
                root.info = successor.info;
                successor.parent.left = successor.right;
                if (successor.right != null) {
                    successor.right.parent = successor.parent;
                }
            }
        }
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

    /**
     * Determine if a value is in this set
     * @param element is query for containment in this set
     * @return true if and only if element in this set
     */
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

    /**
     * Return iterator over this set
     * @return an iterator for this set
     */
    public Iterator<E> iterator() {
        return new TreeIterator(myRoot);
    }

    private class TreeIterator implements Iterator<E> {

        private TreeNode myCurrent;

        private TreeNode myPrevious;

        public TreeIterator(TreeNode root) {
        	if (root == null) {
        		myCurrent = null;
        		myPrevious = null;
        	}
        	else {
	            while (root.left != null) {
	                root = root.left;
	            }
	            myCurrent = root;
	            myPrevious = null;
        	}
        }

        public boolean hasNext() {
            return myCurrent != null;
        }

        public E next() {
            E data = myCurrent.info;
            myPrevious = myCurrent;
            myCurrent = successor(myCurrent);
            return data;
        }

        public void remove() {
            if (myPrevious == null) {
                throw new IllegalStateException(
                        "cannot remove, no valid next call");
            }
            BSTSet.this.remove(myPrevious);
            myPrevious = null;
            mySize--;
        }
    }

}
