package edu.vgu.nids.graph;

public class ConnectedTree extends ConnectedGraph {

	public ConnectedTree() {
		this(new TreeNode(null));
	}
	public ConnectedTree(TreeNode centralNode) {
		super(centralNode);
	}
	
	public TreeNode GetRoot() {
		return (TreeNode)GetCentralNode();
	}
}
