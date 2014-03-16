import java.util.HashSet;


public class Graph {
	private HashSet<Node> nodeSet = new HashSet<>();
	private HashSet<Edge> edgeSet = new HashSet<>();
	private static Node headNode;

	public static void main(String[] args) {
		Graph graph = new Graph();
		headNode = graph.newNode("Info...");
	}

	private Edge newEdge(int weight, Node tail, Node head) {
		Edge x = new Edge(weight, tail, head);
		edgeSet.add(x);
		return x;
	}

	private Node newNode(Object data) {
		Node x = new Node(data);
		nodeSet.add(x);
		return x;
	}

	public void depthFirstSearch(Visitor visitor) {
		dfsHelper(headNode, visitor);
	}


	// Recursive
	private void dfsHelper(Node node, Visitor visitor) {
		visitor.visit(node);
		for(Edge edge : node.getAdjList()) { //Won't go if it's empty
			dfsHelper(edge.getNode(), visitor);
			
		}
	}

	public void breadthFirstSearch(Visitor visitor) {
		//TODO
	}

	// Recursive
	private void bfsHelper(Node node, Visitor visitor) {
		//TODO
	}

	public void dijkstra() {

	}

	private class Node {
		private Object data;
		private HashSet<Edge> adjList;

		private Node(Object data) {
			this.data = data;
			adjList = new HashSet<>();
		}

		private HashSet<Node> neighbors() {
			//TODO
			return null;
		}

		public HashSet<Edge> getAdjList() {
			return adjList;
		}
	}

	private class Edge {
		private int weight;
		private Node tail;
		private Node head;

		private Edge(int weight, Node tail, Node head) {
			this.weight = weight;
			this.tail = tail;
			this.head = head;
		}
		
		private Node getNode() {
			return tail;
		}
	}


	public interface Visitor {
		public void visit(Node node);
	}
}


