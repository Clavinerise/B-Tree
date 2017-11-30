public class Btree {
	final int order = 7;
	Node root;
	
	public Btree() {
		root = new Node(order);
	}
	
	public void insert(int key) {
		
	}
	
	public void update(int key) {
		
	}
	
	public void split() {
		
	}
	
	class Node {
		int numKeys;
		int pointer;
		int numChild = numKeys-1;
		int[] key = new int[numKeys];
		Node[] child = new Node[numChild];
		Node parent;
		
		public Node(int x) {
			numKeys = x;
			pointer = 0;
		}
		
		public boolean isFull() {
			if (key[numKeys-1] == 0)
				return false;
			else 
				return true;
		}
		
		public void insert(int key) {
			
			}
		}
		
		public void adjust() {
			
		}
		
		
	}
}
