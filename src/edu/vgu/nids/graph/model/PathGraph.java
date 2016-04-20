package edu.vgu.nids.graph.model;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a Path graph, which is extended from <b>ConnectedGraph</b> class.
 */
public class PathGraph extends ConnectedGraph {

	private GraphNode[] map;
	
	public PathGraph(int numNodes) {
		map = new GraphNode[numNodes];
		GraphNode node = map[0] = new GraphNode();
		
		for (int i = 1; i < numNodes; i++) {
			GraphNode next = new GraphNode();
			node.Add(next);
			map[i] = node = next;
		}
		
		SetCentralNode(map[numNodes/2]);
		Invalidate();

		SetName("Path graph " + numNodes);
	}
	
	public GraphNode MapAt(int index) {
		return map[index];
	}
}
