package edu.vgu.nids.graph;

import java.util.List;
import java.util.Vector;

public class TreeNode extends GraphNode {

	private TreeNode parent;
	private List<TreeNode> children;
	
	public TreeNode(TreeNode parent) {
		this(parent, null, null);
	}
	public TreeNode(TreeNode parent, GraphNode origin, Object data) {
		super(origin, data);
		
		children = new Vector<TreeNode>();		
		SetParent(parent);		
	}
	
	public void SetParent(TreeNode parent) {
		if (this.parent != null) {
			Remove(this.parent);
			this.parent.children.remove(this);
		}
		
		this.parent = parent;
		
		if (parent != null) {
			Add(parent);
			parent.children.add(this);
		}
	}
	public TreeNode GetParent() {
		return parent;
	}
	public List<TreeNode> GetChildren() {
		return children;
	}
	
	public TreeNode ChildAt(int index) {
		return children.get(index);
	}
	public int IndexOf(TreeNode node) {
		return children.indexOf(node);
	}
	
}
