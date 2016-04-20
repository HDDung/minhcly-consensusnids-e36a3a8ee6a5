package edu.vgu.nids.consensus.weight;

import edu.vgu.nids.graph.ConnectedGraph;

public interface IConsensusWeight {

	public void SetEdgeWeight(ConnectedGraph graph);
	public String GetName();
}
