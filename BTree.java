import java.util.Arrays;

public class Btree {
	final int order = 7;
	Node root;
	
	public Btree() {
		root = new Node(order);
	}
	
	public void insert(Node z, int key) {
		if(!z.hasChild()) {
			if(!z.isFull()) 
				z.add(key);
			else {
				z.add(key);
				z.split();
				root = z.parent;
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
	
	public void update() {
		
	}
	
	public void select() {
		
	}

	

	
	class Node {
		int pointer;
		int numChild;
		int numKeys;
		int minKeys;
		Integer[] key = new Integer[numKeys + 1];
		Node[] child = new Node[numChild];
		Node parent;
		
		public Node(int x) {
			numChild = x;
			numKeys = x-1;
			pointer = 0;
			minKeys = numKeys/2;
		}
		
		public boolean isFull() {
			if (key[numKeys-1] == null)
				return false;
			else 
				return true;
		}
		
		public void add(int y) {
			key[pointer] = y;
			pointer++;
			Arrays.sort(key);
		}
		
		public boolean hasChild() {
			if (child[0] == null) 
				return true;
			else 
				return false;
		}
		
		public void split() {
			pointer = 0;
			parent = new Node(numChild);
			parent.child[1] = new Node(numChild);
			parent.add(key[minKeys+1]);
			key[minKeys+1] = null;
			for (int i = minKeys+1; i <= numKeys; i++) {
				parent.child[1].add(key[i]);
				key[i] = null;
			}
		}
		
	}
}
