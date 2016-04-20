package edu.vgu.nids.consensus.weight;

import edu.vgu.nids.graph.GraphNode;
import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphEdge;

public final class LocalDegreeWeight implements IConsensusWeight {

	@Override
	public void SetEdgeWeight(ConnectedGraph graph) {
		for (GraphEdge edge: graph.GetEdges()) {
			GraphNode[] ends = edge.GetEndpoints();
			edge.SetData((double)1 / Math.max(ends[0].GetDegree(), ends[1].GetDegree()));
		}
	}

	@Override
	public String GetName() {
		return "Local-Degree Weight";
	}

}
