package edu.vgu.nids.graph.model;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a Ring graph, which is extended from <b>ConnectedGraph</b> class.<br>
 * Ring graph is defined by the number of nodes in the graph.
 */
public class RingGraph extends ConnectedGraph {

	private GraphNode[] map;
	
	public RingGraph(int numNodes) {
		map = new GraphNode[numNodes];
		GraphNode node = map[0] = new GraphNode();
		
		for (int i = 1; i < numNodes; i++) {
			GraphNode next = new GraphNode();
			node.Add(next);
			map[i] = node = next;
		}		
		node.Add(map[0]);
		
		SetCentralNode(map[0]);		
		Invalidate();
		
		SetName("Ring graph " + numNodes);
	}

	public GraphNode MapAt(int index) {
		return map[index];
	}
}
