package binaryTreeAndTraversal;

import java.util.Random;
import java.util.Scanner;

/**
 * Binary Tree stuff. This program can either build and display a random binary tree, or build and display one
 * based off the inorder and preorder inputs.
 * @author Shmuel
 *
 */

public class BinaryTree
{
	private Random rand = new Random();
	protected Node root = null;

	/**
	 * Main method. The user inputs whether they want a random or non-random tree, and if nonrandom, they input the inorder and
	 * preorder parameters, separated by commas.
	 * @param args Leave blank
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please type 1 if you want to construct a tree from inorder and preorder \n" +
				" and type 2 if you want to construct a random tree.");
		if(Integer.parseInt(scan.nextLine()) == 1){
			System.out.println("Please type in the inorder transversal separated by commans.");
			String in = scan.nextLine();
			System.out.println("Please type in the preorder transversal separated by commans.");
			String pre = scan.nextLine();
			BinaryTree b = new BinaryTree();
			//I did it without the commas, so I got rid of them
			b.recursiveBuild(in.replace(",", ""), pre.replace(",", ""));
			System.out.println(b.toString());
		} 
		else {
			BinaryTree b = new BinaryTree();
			b.buildRandomTree();
			System.out.println(b.toString());
		}
		scan.close();
	}

	protected class Node
	{
		private Node left, right;
		private Object data;

		//Constructor
		public Node(Object data) {
			this.data = data;
			left = null;
			right = null;
		}
		
		/**
		 * Returns the data of the node
		 */
		public String toString()
		{
			return data.toString();
		}

		/**
		 * Taken from the code given to us. The idea is that it'll print out the nodes in reverse-inorder, starting
		 * with the rightmost, and going leftward. That way, when you view the console on a 90 degree rotation, it'll
		 * look like the tree. So it'll find the rightmost, and print it out at its correct depth (Distance = 2 * its
		 * depth in the tree). It'll then move its way leftward and upward, printing a new line for each new node.
		 * @param depth The depth (distance from the left in the console) of the node
		 * @return The String representation of the tree
		 */
		public String toString(int depth)
		{
			String result = "";

			//Keeps going to the right, adding one to the depth as it goes.
			if (right != null)
				result += right.toString(depth + 1);

			//Once there is no more rightnode, make the spacing be 2 * its depth.
			for (int i = 0; i < depth; i++)
				result += "  ";

			//Append the actual data to the spacing, and skip a line
			result += toString() + "\n";

			//Do the same for the left
			if (left != null)
				result += left.toString(depth + 1);

			return result;
		}
	}

	/**
	 * Calls the toString method on the root, with a 0 initial depth. 
	 * @return String the String representation of the tree, starting from the root.
	 */
	public String toString()
	{
		return root.toString(0);
	}

	/**
	 * The recursive tree building method. The base cases is where the inorder String length is equal to 1, 
	 * where it a a child with no children- it'll just returns the node.
	 * The recursive part works as follows: It first takes the first element of the preorder (which is the root), finds the
	 * location of that in the inorder, and splits the inorder into 3 parts- the rootNode, the leftSide (left of the rootNode), and the
	 * rightSide (right of the rootNode). It then makes a new Node, with the data being the rootNode (and if the whole tree's root
	 * is still null, it makes that the root). It then passes the leftSide, and the leftSide representation in the preorder, as
	 * parameters for the newNode's left variable, and the rightSide, anf the rightSide representation in the preorder, as the 
	 * parameters for the newNode's right variable.
	 * @param inorder The inorder representation of the tree
	 * @param preorder The preorder representation of the tree
	 * @return The node created (which will end up being an entire tree).
	 */
	public Node recursiveBuild(String inorder, String preorder) {
		if (inorder.length() == 1) {
			if(root == null) {
				//In case you're building a tree of a single node...
				root = new Node(inorder);
				return root;
			}
			else {
				return new Node(inorder);
			}			
		}
		else {
			String rootNode = preorder.substring(0,1);
			int rootIndex = inorder.indexOf(rootNode);
			String leftSide = getLeft(rootIndex, inorder);
			String rightSide = getRight(rootIndex, inorder);
			Node newNode = new Node(rootNode);
			if(this.root == null) {
				//As in, this is the first iteration through this method, and we have not yet assigned the root to a node
				this.root = newNode;
			}			
			//Trust me on the preorder substring length...
			if (leftSide != "") {
				newNode.left = recursiveBuild(leftSide, preorder.substring(1, leftSide.length() + 1));
			}
			if(rightSide != "") {
				newNode.right = recursiveBuild(rightSide, preorder.substring(leftSide.length() + 1, preorder.length()));
			}
			return newNode;
		}
	}

	/**
	 * Given the index of the root, it'll return "" if there is no left side, and the left side 
	 * if there is one.
	 * @param index The index of the root
	 * @param string The inorder string
	 * @return The left side of the inorder string
	 */
	public String getLeft(int index, String string) {
		if(index == 0) {
			return "";
		}
		else {
			return string.substring(0, index);
		}
	}

	/**
	 * Given the index of the root, it'll return "" if there is no right side, and the right side 
	 * if there is one.
	 * @param index The index of the root
	 * @param string The inorder string
	 * @return The right side of the inorder string
	 */
	public String getRight(int index, String string) {
		if (index + 1 == string.length()) {
			return "";
		}
		else {
			return string.substring(index + 1, string.length());
		}
	}
	
	
	/**
	 * This method first chooses a random number of nodes for the tree, and a random int of data. If i == 0 (ie the root
	 * is null), it sets the root to that data. If not, it passes the data along to the recursive randomInsert method, which
	 * randomly (kinda...) places that data.
	 */
	public void buildRandomTree() {
		int numNodes = 5 + rand.nextInt(15);
		for(int i = 0; i <= numNodes; i++) {
			int nodeData = rand.nextInt(99) + 1;
			if(i == 0) {
				root = new Node(nodeData);
			}
			else {				
				randomInsert(nodeData, root);
			}
		}
	}

	/**
	 * Takes a int data, and a node. It first randomly says whether this data should be placed to the left or right.
	 * If it's to the left, it sees whether that nodes left is null. IF it is, it places that data there. If not, it calls
	 * this method on that data, and on the node to the left of the node we just tried to place it. 
	 * If it's to the right, then the same thing happens, but with the right side, instead of the left.
	 * @param data An int, corresponding to the nodes data.
	 * @param node The (potential) parent node of this data.
	 */
	public void randomInsert(Object data, Node node){
		boolean tryLeft = rand.nextBoolean();
		if(tryLeft) {
			if(node.left == null) {
				node.left = new Node(data);
			}
			else {
				randomInsert(data, node.left);
			}
		}
		else {
			if(node.right == null) {
				node.right = new Node(data);
			}
			else {
				randomInsert(data, node.right);
			}

		}
	}
}
