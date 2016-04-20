package edu.vgu.nids.graph;

import java.util.List;
import java.util.Vector;

import Jama.Matrix;

/**
 * A class represents a connected graph, which uses <b>GraphNode</b> as children.
 */
public class ConnectedGraph {

	private GraphNode centralNode;
	private List<GraphNode> nodes;
	private List<GraphEdge> edges;
	private String name;
	
	public ConnectedGraph() {
		this(new GraphNode());
	}
	public ConnectedGraph(GraphNode centralNode) {
		this.centralNode = centralNode;
		name = "Connected Graph";

		nodes = new Vector<GraphNode>();
		edges = new Vector<GraphEdge>();
		nodes.add(centralNode);
	}
	
	public GraphNode GetCentralNode() {
		return centralNode;
	}
	public void SetCentralNode(GraphNode node) {
		centralNode = node;
	}
	
	public List<GraphNode> GetNodes() {
		return nodes;
	}
	public List<GraphEdge> GetEdges() {
		return edges;
	}
	public int GetSize() {
		return nodes.size();
	}
	public int GetNumEdges() {
		return edges.size();
	}

	public GraphNode NodeAt(int index) {
		return nodes.get(index);
	}
	public GraphEdge EdgeAt(int index) {
		return edges.get(index);
	}
	public int IndexOf(GraphNode node) {
		return nodes.indexOf(node);
	}
	public int IndexOf(GraphEdge edge) {
		return edges.indexOf(edge);
	}

	public void Invalidate() {
		ConnectedGraph graph = GetConnectedGraph(centralNode);
		nodes = graph.nodes;
		edges = graph.edges;
	}
	
	public static ConnectedGraph GetConnectedGraph(GraphNode centralNode) {
		ConnectedGraph graph = new ConnectedGraph(centralNode);

		for (int i = 0; i < graph.nodes.size(); i++) {
			
			GraphNode node = graph.nodes.get(i);
			
			for (GraphNode neighbor: node.GetNeighbors()) {
				if (!graph.nodes.contains(neighbor))
					graph.nodes.add(neighbor);
				
				GraphEdge edge = node.GetEdge(neighbor);
				if (!graph.edges.contains(edge))
					graph.edges.add(edge);
			}
		}
		
		return graph;
	}
	public ConnectedTree GetBreadthFirstTree() {
		
		// Map the nodes into tree nodes
		List<TreeNode> treeNodes = new Vector<TreeNode>();
		for (GraphNode node: nodes)
			treeNodes.add(new TreeNode(null, node, 0));
		
		ConnectedTree tree = new ConnectedTree(treeNodes.get(0));
		
		Vector<GraphNode> checked = new Vector<GraphNode>();
		checked.add(centralNode);
		
		/*
		 * New node is always at the end of edge.
		 * If found a new node, set the begin node as his parent
		 * and assign his level. 
		 */
		for (GraphEdge edge: edges) {
			GraphNode begin = edge.GetEndpoints()[0];
			GraphNode end = edge.GetEndpoints()[1];
			
			if (!checked.contains(end)) {
				checked.add(end);
				
				TreeNode beginTree = treeNodes.get(IndexOf(begin));
				TreeNode endTree = treeNodes.get(IndexOf(end));
				
				endTree.SetParent(beginTree);
				endTree.SetData((int)beginTree.GetData() + 1);
			}
			
			if (checked.size() == GetSize())
				break;
		}
		
		tree.Invalidate();
		tree.SetName(name + " - BreadthFirstTree");
		
		return tree;
	}
	
	public String GetName() {
		return name;
	}
	public void SetName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		String str = "";
		
		str += name + " \tN: " + nodes.size() + " \tE: " + edges.size() + "\n";
		for (int i = 0; i < edges.size(); i++)
			str += "{" + IndexOf(EdgeAt(i).GetEndpoints()[0]) + "," + IndexOf(EdgeAt(i).GetEndpoints()[1]) + "} ";
		
		return str;
	}	

	public Matrix GetLaplacian() {
		double[][] laplacian = new double[GetSize()][GetSize()];
		for (int i = 0; i < GetSize(); i++) {
			for (int j = 0; j < GetSize(); j++) {				
				if (i == j) 
					laplacian[i][j] = NodeAt(i).GetDegree();
				else if (NodeAt(i).IsNeighbor(NodeAt(j)))
					laplacian[i][j] = -1;
				else
					laplacian[i][j] = 0;
			}
		}
		
		return new Matrix(laplacian);
	}
}
