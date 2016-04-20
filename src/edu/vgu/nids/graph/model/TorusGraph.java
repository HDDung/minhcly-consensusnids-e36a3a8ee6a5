package edu.vgu.nids.graph.model;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a Torus-grid graph, which is extended from <b>ConnectedGraph</b> class.
 * Torus-grid graph is defined by the the number of nodes in each dimension.
 */
public class TorusGraph extends ConnectedGraph {

	private RingGraph[] rings;

	/**
	 * The constructor of a Torus-grid graph
	 * @param d1 Number of nodes in the first dimension
	 * @param d2 Number of nodes in the second dimension
	 */
	public TorusGraph(int d1, int d2) {
		
		rings = new RingGraph[d1];
		rings[0] = new RingGraph(d2);

		for (int i = 1; i < d1; i++) {
			rings[i] = new RingGraph(d2);

			// Connect to the previous ring entrywise
			for (int j = 0; j < d2; j++)
				rings[i].MapAt(j).Add(rings[i-1].MapAt(j));
		}

		// Connect the last ring to the first
		for (int j = 0; j < d2; j++)
			rings[d2-1].MapAt(j).Add(rings[0].MapAt(j));

		SetCentralNode(rings[0].GetCentralNode());		
		Invalidate();
		
		SetName("Torus-grid graph " + d1 + "x" + d2);
	}
	
	public GraphNode MapAt(int i, int j) {
		return rings[i].MapAt(j);
	}
}