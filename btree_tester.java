
public class btree_tester {
	public static void main(String[]args) {
		Btree k = new Btree();
		k.insert(k.root, 8);
		k.insert(k.root, 11);
		k.insert(k.root, 6);
		k.insert(k.root, 14);
		k.insert(k.root, 15);
		k.insert(k.root, 18);
		k.insert(k.root, 12);
		k.insert(k.root, 21);
		k.insert(k.root, 9);
		k.insert(k.root, 10);
		k.insert(k.root, 5);
		k.insert(k.root, 22);
		k.insert(k.root, 23);
		k.insert(k.root, 24);
		k.insert(k.root, 16);
		k.insert(k.root, 17);
		k.insert(k.root, 19);
		k.insert(k.root, 3);
		k.insert(k.root, 7);
		k.insert(k.root, 4);
		for (int i = 0; i < 4; i++) {
			System.out.println(k.root.key[i]);
		}
		System.out.println();
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j<4; j++) {
				if(k.root.child[i] != null)
					System.out.println(k.root.child[i].key[j]);
				else 
					System.out.println("null");
			}
			System.out.println();
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j<5; j++) {
				for (int l = 0; l < 4; l++) {
					if (k.root.child[i] != null) {
						if (k.root.child[i].child[j] != null) {
							System.out.println(k.root.child[i].child[j].key[l]);
						}
						else
							System.out.println("null");
					}
				}
				System.out.println();
			}
		}
		

	}
}
