// Tree to List Recursion Problem code

public class TLNode {
	int data;
	TLNode left;
	TLNode right;

	public TLNode(int v, TLNode lt, TLNode rt) {
		data = v;
		left = lt;
		right = rt;
	}

	/**
	 * Given two list nodes and b , join them together so b immediately follows a
	 */
	public static void join(TLNode a, TLNode b) {

	}

	/*
	 * helper function -- given two circular doubly linked lists, 
	 * append them and return the new list.
	 */
	public static TLNode append(TLNode a, TLNode b) {
		// TODO Complete append
		// Go to end of list a
		if (a == null) return b;
		if (b == null) return a;
		TLNode aTail = a;
		while (aTail.right != null)
			aTail = aTail.right;
		// What's true about aTail here?
		join(aTail, b);
		return a;
	}

	public static TLNode treeToList(TLNode root) {
		// base case
		if (root == null)
			return null;
		TLNode beforeMe = treeToList(root.left);
		TLNode afterMe = treeToList(root.right);

		// TODO What do you need to do here?

		return root;
	}
	
	public static void printTree(TLNode root) {
		if (root == null) return;
		
		printTree(root.left);
		System.out.println(root.data);
		printTree(root.right);
	}
	
	public static void printList(TLNode head) {
		if (head == null) return;
		System.out.println(head.data);
		// do the rest
		printList(head.right);
	}
	
	public static void main(String[] args) {
		// TODO make the 5 element tree in the problem
		TLNode root;
		
	}
}
