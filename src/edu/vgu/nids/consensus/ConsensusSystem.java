package edu.vgu.nids.consensus;

import edu.vgu.nids.consensus.weight.IConsensusWeight;
import edu.vgu.nids.core.ISystem;
import edu.vgu.nids.core.Network;
import edu.vgu.nids.core.Report;
import edu.vgu.nids.core.State;
import edu.vgu.nids.graph.GraphEdge;
import edu.vgu.nids.graph.GraphNode;
import edu.vgu.nids.math.PreciseNumber;

public final class ConsensusSystem implements ISystem {

	private Network network;
	private NetworkImage initImage;
	private NodeLog nodeLog;
	private static final int MAX_ITERATION = 10000;

	private double adjustment;
	
	public ConsensusSystem(Network network, IConsensusWeight weightModel, double adjustment) {
		this.network = network;
		this.adjustment = adjustment;
		weightModel.SetEdgeWeight(network.GetGraph());
	}
	
	@Override
	public Report Evaluate() {
		nodeLog = new NodeLog(network.GetGraph().NodeAt(0));	// Log at node 0
		
		// Consensus phase -----------------------------------------
		int cost = 0, iteration = 0;
		
		// Log the priors to get the belief
		for (GraphNode node: network.GetGraph().GetNodes())
			((Module)node.GetData()).MakeBelief();
		
		initImage = NetworkImage.Capture(network);				// Capture an image at time 0
		nodeLog.Capture();
		
		boolean stop = false;
		while (!stop && iteration < MAX_ITERATION) {
			iteration++;
			
			// Exchange belief between nodes via edge
			for (GraphEdge edge: network.GetGraph().GetEdges()) {
				GraphNode ep1 = edge.GetEndpoints()[0];
				GraphNode ep2 = edge.GetEndpoints()[1];
				
				((Module)ep1.GetData()).ExchangeBelief((Module)ep2.GetData(), (double)edge.GetData());
				
				cost += 2;
			}
			
			// Update the belief after an iterations
			stop = true;
			for (GraphNode node: network.GetGraph().GetNodes())
				if (!((Module)node.GetData()).UpdateBelief())
					stop = false;
			
			nodeLog.Capture();
		}
		
		// Reclaim the priors from the belief
		for (GraphNode node: network.GetGraph().GetNodes())
			((Module)node.GetData()).SetBelief();
	
		// Decision phase --------------------------------------------
		PreciseNumber[] result = ((Module)network.GetGraph().GetCentralNode().GetData()).GetPriors();
		
		State state;
		if (result[0].Log10() - result[1].Log10() > adjustment)
			state = State.NORMAL;
		else
			state = State.ATTACK;
		
		return new Report(state, cost, result, iteration);
	}

	public NetworkImage GetInitImage() {
		return initImage;
	}
	public NodeLog GetNodeLog() {
		return nodeLog;
	}
}
