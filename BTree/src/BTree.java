import java.util.Arrays;
import java.util.Comparator;

public class BTree {
	final int order = 7;
	int minKeys = order/2;
	Node root;
	
	public BTree() {
            root = new Node(order);
	}
	
	public void insert(Node z, int key) {
		if(!z.hasChild()) {
			if(!z.isFull()) 
				z.add(key);
			else {
				z.add(key);
				split(z);
			}
		}
		else {
			for (int i = 0; i < order; i++) {
				if (z.key[i]==null) {
					insert(z.child[i], key);
					break;
				}
				else if (key < z.key[i]) {
					insert(z.child[i], key);
					break;
				}
			}
		}
	}
	
	public void split(Node z) {
		if (!z.hasParent()) {
			z.parent = new Node(order);
			root = z.parent;
			root.add(z.key[minKeys]);
			z.key[minKeys] = null;
			root.child[0] = z;
			Node child2 = new Node(order);
			for (int i = minKeys + 1; i < order; i++) {
				child2.add(z.key[i]);
				z.key[i] = null;
			}
			root.child[1] = child2;
		}
		
		else {
			z.parent.add(z.key[minKeys]);
			z.key[minKeys] = null;
			Node newChild = new Node(order);
			for (int i = minKeys + 1; i < order; i++) {
				newChild.add(z.key[i]);
				z.key[i] = null;
			}
			z.parent.addChild(newChild);
			if (z.parent.isFull()) {
				split(z.parent);
				for (int i = minKeys+1; i < order; i++) {
					z.parent.child[2].addChild(z.child[i]);
					z.child[i] = null;
				}
			}
		}
	}

	
	class Node {
		int keyPointer;
		int childPointer;
		int numChild;
		int numKeys;
		int minKeys;
		Integer[] key;
		Node[] child;
		Node parent;
		
		public Node(int x) {
			numChild = x;
			numKeys = x-1;
			keyPointer = 0;
			childPointer = 2;
			minKeys = numKeys/2;
			key = new Integer[numKeys+1];
			child = new Node[numChild+1];
		}
		
		public boolean isFull() {
			if (key[numKeys-1] == null)
				return false;
			else 
				return true;
		}
		
		public void add(int y) {
			key[keyPointer] = y;
			keyPointer++;
			Arrays.sort(key, new Comparator<Integer>() {
				 public int compare(Integer i, Integer j) {
					 if (i == null && j == null) {
						 return 0;
						 }
					 if (i == null) {
						 return 1;
						 }
					 if (j == null) {
						 return -1;
						 }
					 return i.compareTo(j);
					 }
				 });
		}
		
		public boolean hasChild() {
			if (child[0] == null) 
				return false;
			else 
				return true;
		}
		
		public boolean hasParent() {
			if (parent == null) 
				return false;
			else
				return true;
		}
		
		public void addChild(Node x) {
			child[childPointer] = x;
			childPointer++;
			Arrays.sort(child, new Comparator<Node>() {
				 public int compare(Node i, Node j) {
					 if (i == null && j == null) {
						 return 0;
						 }
					 if (i == null) {
						 return 1;
						 }
					 if (j == null) {
						 return -1;
						 }
					 return i.key[0].compareTo(j.key[0]);
					 }
				 });
		}
		
		 
	}
}