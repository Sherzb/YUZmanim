package TreeTesting;

/**
 * A pretty crude Binary Tree class, but it's really just here to show the two main testing methods:
 * BSTTest- Takes a BT as a parameter, and returns true if it is a BST, and false otherwise.
 * RBTest- Takes a BT as a parameter, and returns true if it can be made into a RBT just with recoloring, and false otherwise.
 * @author Shmuel
 *
 */
public class BinaryTree
{
	private Node root;

	class Node
	{
		protected Node left;
		protected Node right;
		protected Comparable data;

		public Node(Comparable data)
		{
			this.data = data;
			left = null;
			right = null;
		}
		
		public Node(Node node) 
		{
			data = null;
			left = node;
			right = null;
		}
	}

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		tree.build();
		System.out.println("The tree is a BST Tree: " + tree.BSTTest(tree));
		System.out.println("The tree can be a RB Tree: " + tree.RBTest(tree));
	}



	public boolean BSTTest(BinaryTree tree) 
	{
		return BSTHelper(tree.root);
	}

	/**
	 * Relatively straightforward recursive algorithm. Take a node as a parameter. If the node is the nil node, return 
	 * true (because any node can have a nil child). If not, but one or both of the children are nil, the left node is
	 * less than (or equal to) the parent, and the right node is greater, this is called recursively on the children. If that
	 * doesn't hold, false is returned.
	 * @param node A node in the BT
	 * @return true if the tree is a BST, false otherwise
	 */
	public boolean BSTHelper(Node node)
	{
		if (node.data.equals("nil")) {
			return true;
		}
		else {
			boolean leftIsGood = (node.left.data.equals("nil") || node.data.compareTo(node.left.data) >= 0);
			boolean rightIsGood = (node.right.data.equals("nil") || node.data.compareTo(node.right.data) < 0);
			if(leftIsGood && rightIsGood) {
				return ( BSTHelper(node.left) && BSTHelper(node.right) );
			}
			else {
				return false;
			}
		}
	}

	public boolean RBTest(BinaryTree tree)
	{
		return RBHelper(tree.root);
	}

	/**
	 * Checks whether a tree can be made into a RB tree just by recoloring. As before, if the node checked is the nil node,
	 * return true. If not, then we look at the heights of the nodes two children. If (minHeight * 2) + 1 < maxHeight), then
	 * even if every node of the smaller subtree was black, it wouldn't have the same black height as the longer subtree (as
	 * if there are n nodes, at minimum (n-1)/2 are black). If there was enough, this method was called recursively.
	 * I used the height of the nil node to be 0, to make the math simpler, so I wouldn't have to deal with negative numbers.
	 * @param node
	 * @return
	 */
	public boolean RBHelper(Node node) {
		if (node.data.equals("nil")) {
			return true;
		}

		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		int minHeight = Math.min(leftHeight, rightHeight);
		int maxHeight = Math.max(leftHeight, rightHeight);

		if ((minHeight * 2) + 1 < maxHeight) {
			return false;
		}
		else {
			return (RBHelper(node.left) && RBHelper(node.right));
		}
	}

	/**
	 * The same height method we've always used, except the height of the nil node is 0, not -1.
	 * @param node
	 * @return
	 */
	public int height(Node node) {
		if (node.data.equals("nil")) {
			return 0;
		}
		else {
			double leftHeight = height(node.left);
			double rightHeight = height(node.right);
			return (int)(1 + Math.max(leftHeight, rightHeight));
		}
	}

	/**
	 * Builds the tree on page 310 of the algorithms book. Yes, I wasted space in the array by initializing unused
	 * nodes, but it was neater. 
	 * Note that nodes[49] is the nil node!
	 */
	public void build()
	{
		Node[] nodes = new Node[50];
		for(int i = 0; i < 49; i++) {
			nodes[i] = new Node(i);
		}

		nodes[49] = new Node("nil");
		nodes[49].left = nodes[49];
		nodes[49].right = nodes[49];

		root = nodes[26];
		nodes[26].left = nodes[17];
		nodes[26].right = nodes[41];

		nodes[17].left = nodes[14];
		nodes[17].right = nodes[21];
		nodes[41].left = nodes[30];
		nodes[41].right = nodes[47];

		nodes[14].left = nodes[10];
		nodes[14].right = nodes[16];
		nodes[21].left = nodes[19];
		nodes[21].right = nodes[23];
		nodes[30].left = nodes[28];
		nodes[30].right = nodes[38];
		nodes[47].left = nodes[49];
		nodes[47].right = nodes[49];

		nodes[10].left = nodes[7];
		nodes[10].right = nodes[12];
		nodes[16].left = nodes[15];
		nodes[16].right = nodes[49];
		nodes[19].left = nodes[49];
		nodes[19].right = nodes[20];
		nodes[23].left = nodes[49];
		nodes[23].right = nodes[49];
		nodes[28].left = nodes[49];
		nodes[28].right = nodes[49];
		nodes[38].left = nodes[35];
		nodes[38].right = nodes[39];

		nodes[7].left = nodes[3];
		nodes[7].right = nodes[49];
		nodes[12].left = nodes[49];
		nodes[12].right = nodes[49];
		nodes[15].left = nodes[49];
		nodes[15].right = nodes[49];
		nodes[20].left = nodes[49];
		nodes[20].right = nodes[49];
		nodes[35].left = nodes[49];
		nodes[35].right = nodes[49];
		nodes[39].left = nodes[49];
		nodes[39].right = nodes[49];

		nodes[3].left = nodes[49];
		nodes[3].right = nodes[49];
	}
}
