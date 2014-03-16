package avlTree;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * An AVL Tree, extending a BinarySearchTree. The main additions it adds are the rotate features, to keep the tree
 * balanced after insertions or deletions.
 * When the program is run, an inputted number of nodes is added to the tree. After that, random nodes are deleted from 
 * the tree, until it is empty. After each step, the tree is printed.
 * Comments indicating which nodes are being added and deleted, and when rotations are occuring, were added.
 * @author Shmuel
 *
 */
public class AVLTree extends BinarySearchTree
{
	private String letterBank = "abcdefghijklmnopqrstuvwxyz"; //For the random Strings
	private Random r = new Random(); //Also for the random Strings
	private ArrayList<String> wordBank = new ArrayList<String>(); //For deleting the Nodes
	private int numWords;

	public static void main(String[] args)
	{
		AVLTree tree = new AVLTree();
		//How many words
		Scanner s = new Scanner(System.in);
		System.out.println("How many words will be in the tree? 10 works well, but you can do more or less");
		tree.numWords = s.nextInt();
		s.close();
		//Insertion
		for(int i = 0; i < tree.numWords; i++) {
			String addition = tree.randomString();
			System.out.println("Inserting: " + addition);
			System.out.println();
			tree.insert(addition);
			System.out.println(tree);
			System.out.println("-----------------");
		}
		//Deletion
		while(tree.wordBank.size() > 0) {
			String deleteString = tree.wordBank.get(tree.r.nextInt(tree.wordBank.size()));
			System.out.println("Deleting " + deleteString);
			System.out.println();
			tree.delete(deleteString);
			tree.wordBank.remove(deleteString);
			System.out.println(tree);
			System.out.println("-----------------");
		}
		System.out.println();
		System.out.println("Scroll to the top, and read from the top down, to see the " +
		"building and deleting of the whole tree");
	}

	/**
	 * Gets the height of a node, which is needed to calculate the node's balance
	 * @param node A node in the tree
	 * @return The height of the node in the tree
	 */
	public int height(Node node) {
		if (node == nil) {
			return -1;
		}
		else {
			int leftHeight = height(node.left);
			int rightHeight = height(node.right);
			return 1 + Math.max(leftHeight, rightHeight);
		}
	}

	/**
	 * Basically the same thing as the BST's insert, except I add a call to insertBalance after inserting the node
	 */
	@Override
	protected void treeInsert(Node z)
	{
		super.treeInsert(z);
		insertBalance(z);
	}

	/**
	 * Feel free to skip the first paragraph
	 * A bit more complicated. Ideally I would have just liked to call super.delete(node), and then a balance method. However, 
	 * what type of Object is the Object "node"? One on hand, it should be a key (which is what BST's comments say). However, BST's
	 * delete method includes a call to deleting the successor, NOT the key of the successor. In that case, it's deleting a Node. When it
	 * does that, it has to search for the Node, and it calls the Node's compareTo, but compares "this" to "(Node)node.data". Since the 
	 * data is a String, it can't be compared to a Node, and it crashes.
	 * 
	 * Long story short, I had to make sure not to call search on a Node, which is why I used instanceof.
	 * 
	 * In any event, this method finds the Node, calls super.delete on it, and then calls deleteBalance on the parent
	 */
	@Override
	public void delete(Object node)
	{
		if (!(node instanceof Node)) {
			node = (Node)search(((Comparable)node));
		}
		Node parent = ((Node)node).parent;
		super.delete(node);
		deleteBalance(parent);
	}

	/**
	 * Though insertBalance and deleteBalance are similar, I split them up, because you stop balancing in insert a lot earlier
	 * potentially than in delete. But maybe I could have refactored it... 
	 * 
	 * Also I did a while(true) and return combination, instead of a boolean didRotation. Not sure which is better
	 * @param node The node that may be unbalanced
	 */
	public void insertBalance(Node node) 
	{
		Node currentNode = node.parent;

		while(true) {

			if (currentNode == nil) {
				return;
			}

			int balance = height(currentNode.left) - height(currentNode.right); //I decided not to make it a method, no need

			if (balance == 0) {
				return;
			}
			else if (balance == 1 || balance == -1) {
				currentNode = currentNode.parent; //Go up to the parent, and then rebalance potentially
			}
			else {
				rebalance(currentNode, balance); //For when the balance is +2 or -2
			}
		}
	}

	/**
	 * Basically the same thing as insertBalance, except it's while loop is changed, and it still goes up after rebalancing
	 * @param node The node that may be unbalanced
	 */
	public void deleteBalance(Node node) 
	{
		Node currentNode = node;

		while(currentNode != nil) {

			int balance = height(currentNode.left) - height(currentNode.right);

			if (balance == 2 || balance == -2) {
				rebalance(currentNode, balance);
			}
			//Balance is -1, 0, or 1, or a rebalancing happened
			currentNode = currentNode.parent;
		}
	}

	/**
	 * This will only be called if the balance is +2 or -2.
	 * Also I did "equals to or greater than", instead of just greater than. You can have equals in cases of deletions, 
	 * and there, it's more efficient to only do a single rotation.
	 * @param currentNode The node that is unbalanced
	 * @param balance The "Balance" of that node
	 */
	public void rebalance(Node currentNode, int balance)
	{
		if (balance == 2) { //So we know the left side is "heavier" than the right

			if (height(currentNode.left.left) >= height(currentNode.left.right)) {  //So it's double left
				rightRotate(currentNode);
			}
			else {
				leftRotate(currentNode.left);
				rightRotate(currentNode);
			}
		}
		else if (balance == -2) { //Wrote that for neatness
			if (height(currentNode.right.right) >= height(currentNode.right.left)) {  //So it's double right
				leftRotate(currentNode);
			}
			else {
				rightRotate(currentNode.right);
				leftRotate(currentNode);
			}
		}
	}

	/**
	 * Rotates the inputted node to the right (this node is the one moving, NOT the piot). The actually methedology
	 * is explained in the method.
	 * @param node The node to be rotated.
	 */
	public void rightRotate(Node node) 
	{
		System.out.println("The node with the data " + node.data + " was rotated to to the right");
		System.out.println();
		Node pivot = node.left;

		//Make the parent of the moving node be the parent of the pivot
		pivot.parent = node.parent;
		if (node.parent.left == node) {
			node.parent.left = pivot;
		}
		else if (node.parent.right == node ){
			node.parent.right = pivot;
		}

		//Move T2 under the moving node
		node.left = pivot.right;
		if (pivot.right != nil) {
			pivot.right.parent = node;
		}

		//Move the pivot on top of the moving node
		pivot.right = node;
		node.parent = pivot;

		//Check for root issues
		if (node == root) {
			root = pivot;
		}
	}

	/**
	 * Rotates the inputted node to the left (this node is the one moving, NOT the piot). The actually methedology
	 * is explained in the method.
	 * @param node The node to be rotated
	 */
	public void leftRotate(Node node) 
	{
		Node pivot = node.right;
		System.out.println("The node with the data " + node.data + " was rotated to to the left");
		System.out.println();

		//Make the parent of the moving node be the parent of the pivot
		pivot.parent = node.parent;
		if (node.parent.left == node) {
			node.parent.left = pivot;
		}
		else if (node.parent.right == node ){
			node.parent.right = pivot;
		}

		//Move T2 under the moving node
		node.right = pivot.left;
		if (pivot.left != nil) {
			pivot.left.parent = node;
		}

		//Move the pivot on top of the moving node
		pivot.left = node;
		node.parent = pivot;

		if (node == root) {
			root = pivot;
		}
	}

	/**
	 * Outputs a random String of 4 characters (4 just for ease of reading sake. There actually is no automated way to
	 * do this, oddly enough- there is no r.nextChar() method. The way I did it was something I saw on StackOverflow, and
	 * it is straightforward enough, if not necessarily the most efficient.
	 * Also, I kept it lowercase, again just for neatness.
	 * @return A random string of 4 letters
	 */
	public String randomString() 
	{
		String str = "";
		for(int i = 0; i < 4; i++) { 
			str += letterBank.charAt(r.nextInt(26)) + "";
		}
		wordBank.add(str);
		return str;
	}
}