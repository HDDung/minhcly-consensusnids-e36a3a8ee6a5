package edu.vgu.nids.graph;

import java.util.List;
import java.util.Vector;

/**
 * A class represents a node in a graph (which is implemented in <b>ConnectedGraph</b> class).
 */
public class GraphNode {

	private Vector<GraphNode> neighbor;
	private Object data;
	private GraphNode origin;
	
	public GraphNode() {
		this(null, null);
	}
	public GraphNode(GraphNode origin) {
		this(origin, null);
	}
	public GraphNode(Object data) {
		this(null, data);
	}
	public GraphNode(GraphNode origin, Object data) {
		neighbor = new Vector<GraphNode>();
		this.origin = origin;
		this.data = data;
	}
	
	public List<GraphNode> GetNeighbors() {
		return neighbor;
	}
	public GraphNode GetNeighbor(int index) {
		return neighbor.get(index);
	}
	public int GetDegree() {
		return neighbor.size();
	}

	public GraphEdge Add(GraphNode node) {
		if (IsNeighbor(node))
			return null;
		
		neighbor.add(node);
		node.neighbor.add(this);
		return new GraphEdge(this, node);
	}
	public void Remove(GraphNode node) {
		neighbor.remove(node);
		node.neighbor.remove(this);
	}
	public void Remove() {
		for (GraphNode node : neighbor)
			node.neighbor.remove(this);
		neighbor.clear();
	}

	public Object GetData() {
		return data;
	}
	public void SetData(Object data) {
		this.data = data;
	}

	public boolean IsNeighbor(GraphNode node) {
		return neighbor.contains(node);
	}
	public GraphEdge GetEdge(GraphNode node) {
		if (IsNeighbor(node))
			return new GraphEdge(this, node);
		return null;
	}
	
	public GraphNode GetOrigin() {
		return origin;
	}
}
