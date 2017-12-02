import java.util.Arrays;
import java.util.Comparator;

public class Btree {
	final int order = 5;
	int minKeys = order/2;
	Node root;
	
	public Btree() {
		//initialize the btree by creating an empty root node
		root = new Node(order);
	}
	
	//insert a key to the btree
	public void insert(Node z, int key) {
		//to use node z must always be the root node
		//check if node has a child since we only add to the deepest layer
		if(!z.hasChild()) {
			//add key into node
			z.add(key);
			//if the node is full/exceeds the max amount of keys then split
			if(z.isFull()) 
				split(z);
		}
		//if it is not the deepest layer find the deepest layer
		else {
			for (int i = 0; i < order; i++) {
				if (z.key[i]==null || key < z.key[i]) {
					insert(z.child[i], key);
					break;
				}
			}
		}
	}
	
	//split function
	public void split(Node z) {
		//we need to check if the node to be split is the root node or not
		//if it is root node do the following
		if (!z.hasParent()) {
			//creates a new node that will become the new root node
			z.parent = new Node(order);
			root = z.parent;
			//add in the middle key into the root node
			root.add(z.key[minKeys]);
			z.key[minKeys] = null;
			//make the original root node the child of the new root node
			root.child[0] = z;
			//create a new child node for the root node
			Node child2 = new Node(order);
			//move the later half of the original node to the new child 
			for (int i = minKeys + 1; i < order; i++) {
				child2.add(z.key[i]);
				z.key[i] = null;
			}
			//add the child into the new root node
			root.addChild(child2);
			//assign root as parent of child
			child2.parent = z.parent;
			//special reseter
			z.reset();
		}
		//if it is not the root node do the following
		else {
			//since it is not a root node, it has a parent
			//move the middle key into the parent node
			z.parent.add(z.key[minKeys]);
			z.key[minKeys] = null;
			//create a new child due to the split
			Node newChild = new Node(order);
			//move later half of the node into the new child
			for (int i = minKeys + 1; i < order; i++) {
				newChild.add(z.key[i]);
				z.key[i] = null;
			}
			//accomplish parent child relationship for the new node
			z.parent.addChild(newChild);
			newChild.parent = z.parent;
			//special reseter
			z.reset();
			//since we are adding a key to the parent then the parent will fill up, so we need to add
			//condition if the parent itself becomes full
			if (z.parent.isFull()) {
				Node parent = z.parent;
				//splits the parent
				split(parent);
				//when the parent is split a new node is created but the new node is childless
				//move later half of the children of the parent node
				for (int i = minKeys+1; i < order+1; i++) {
					parent.parent.child[1].addChild(parent.child[i]);
					parent.child[i] = null;
				}
				//establish parent child relationship of new node
				for (int i = 0; i < minKeys; i++) {
					parent.parent.child[1].child[i].parent = parent.parent.child[1];
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
		
		//instantiates node of order x
		public Node(int x) {
			numChild = x;
			numKeys = x-1;
			keyPointer = 0;
			childPointer = 1;
			minKeys = numKeys/2;
			key = new Integer[numKeys+1];
			child = new Node[numChild+1];
		}
		
		//check if the node is full/overloaded
		public boolean isFull() {
			if (key[numKeys] == null)
				return false;
			else 
				return true;
		}
		
		//adds a new key and sorts the keys in order
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
		
		//check if the node has a child
		public boolean hasChild() {
			if (child[0] == null) 
				return false;
			else 
				return true;
		}
		
		//check if the node has a parent
		public boolean hasParent() {
			if (parent == null) 
				return false;
			else
				return true;
		}
		
		//add a child to the node and sorts it
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
		
		//special reseter for the pointers used to add children
		public void reset() {
			childPointer = minKeys+1;
			keyPointer = minKeys;
		}
	}
}