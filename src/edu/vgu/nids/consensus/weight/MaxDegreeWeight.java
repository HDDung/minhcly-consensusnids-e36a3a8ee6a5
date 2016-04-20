package edu.vgu.nids.consensus.weight;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphEdge;
import edu.vgu.nids.graph.GraphNode;

public final class MaxDegreeWeight implements IConsensusWeight {

	@Override
	public void SetEdgeWeight(ConnectedGraph graph) {
		int maxDeg = 0;
		for (GraphNode node: graph.GetNodes())
			if (node.GetDegree() > maxDeg)
				maxDeg = node.GetDegree();
		
		for (GraphEdge edge: graph.GetEdges())
			edge.SetData((double)1 / maxDeg);
	}

	@Override
	public String GetName() {
		return "Max-Degree Weight";
	}

}
