package avlTree;

import java.util.Scanner;

public class AVLTreeAriel extends BinarySearchTree {

	
	public static void main(String[] args) {
		AVLTreeAriel tree = new AVLTreeAriel();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insert random set of numbers seperated by commas");
		
		String[] data = scan.nextLine().split(",");
		
		for(int i = 0; i < data.length; i++) {
			if(i + 1 == data.length) {
				int x = 1;
				x++;
			}
			tree.insert(data[i]);
			System.out.println(tree.toString());
			System.out.println("-----------------");
		}
				
		System.out.println(tree.toString());
		
	}
	
	public int height(Node node) {
	    if (node.right == nil && node.left == nil) {
	        return 0;
	    }
	    else {
	        return 1 + Math.max(height(node.left), height(node.right));
	    }
	}
	
	public int balance(Node node) {
		System.out.println("The balance of Node " + node + " is " + (height(node.left) - height(node.right)));
		return height(node.left) - height(node.right);
	}
	
	public Object insert(Comparable data) {
		Node z = new Node(data);
		treeInsert(z);

		return z;
	}
	
	/**
	 * Inserts a node into the tree.
	 * 
	 * @param z
	 *            The node to insert.
	 */
	protected void treeInsert(Node z) {
		super.treeInsert(z);
		
		//checking balance
		Node a = z.parent;
		Node b = z;
		
		while (Math.abs(balance(a)) == 1) {
				b = a;
				a = a.parent;
		}
		
		if (balance(a) == 2) {
			//do rotations and break (i think) and there may be 4 cases, two in each depending where u come from
			if (balance(b) == 1) {
				rotateRight(a);
			}
			else {
				rotateLeftRight(a);
			}
		}
		
		if (balance(a) == -2) {
			//do rotations and break (i think)
			if (balance(b) == -1) {
				rotateLeft(a);
			}
			else {
				rotateRightLeft(a);
			}
		}
	}

	//then do the same by the cases of deletion
	
	public void delete(Node z) {
		super.delete(z);
		
		Node a = z.parent;
		
		while (a != nil) {
			
			if (balance(a) == 2) {
				//do rotations and break (i think) and there may be 4 cases, two in each depending where u come from
				if (balance(a.left) == -1) {
					rotateLeftRight(a);
				}
				else {
					rotateRight(a);
				}
			}
			
			if (balance(a) == -2) {
				//do rotations and break (i think)
				if (balance(a.right) == 1) {
					rotateRightLeft(a);
				}
				else {
					rotateLeft(a);
				}
			}	
			
			a = a.parent;
		}
	}
	
	private void rotateRight(Node a) {
		//node b
		Node b = a.left;
		
		if (root == a) {
			root = b;
		}
		
		else if (a.parent.left == a) {
			a.parent.left = b;
			
		}
		else {
			a.parent.right = b;
		}
		//put b at the top
		b.parent = a.parent;
		
		//make b's right into a's left
		a.left = b.right;
		b.right.parent = a;
		
		//make a into b's right
		a.parent = b;
		b.right = a;
	}
	
	private void rotateLeft(Node a) {
		//node b
		Node b = a.right;
				
		if (root == a) {
			root = b;
		}
		
		else if (a.parent.left == a) {
			a.parent.left = b;
			
		}
		else {
			a.parent.right = b;
		}
		//put b at the top
		b.parent = a.parent;
		
		//make b's left into a's right
		a.right = b.left;
		b.left.parent = a;
		
		//make a into b's left
		a.parent = b;
		b.left = a;
	}
	
	public void rotateLeftRight(Node a) {
		rotateLeft(a.left);
		rotateRight(a);
	}

	public void rotateRightLeft(Node a) {
		rotateRight(a.right);
		rotateLeft(a);
	}
	
}

