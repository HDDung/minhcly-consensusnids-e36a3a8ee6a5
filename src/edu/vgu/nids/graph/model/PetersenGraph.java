package edu.vgu.nids.graph.model;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a Petersen graph, which is extended from <b>ConnectedGraph</b> class.
 */
public class PetersenGraph extends ConnectedGraph {
	
	private RingGraph[] rings;

	public PetersenGraph() {
		
		rings = new RingGraph[2];
		rings[0] = new RingGraph(5); // Outer ring (pentagon)
		rings[1] = new RingGraph(5); // Inner ring (pentagram)

		for (int i = 0; i < 5; i++)
			// Outer ring goes 1, Inner ring goes 2
			rings[0].MapAt(i).Add(rings[1].MapAt(2*i % 5));

		SetCentralNode(rings[0].GetCentralNode());		
		Invalidate();
		
		SetName("Petersen graph");
	}
	
	/**
	 * Get the node corresponding to coordinates
	 * @param ring Pass 0 for Outer ring (pentagon), 1 for Inner ring (pentagram)
	 * @param index The index of the node in that ring
	 * @return The node is mapped as (i, j)
	 */
	public GraphNode MapAt(int ring, int index) {
		if (ring == 0)
			return rings[0].MapAt(index);
		else
			return rings[1].MapAt(2*index % 5);
	}
}
