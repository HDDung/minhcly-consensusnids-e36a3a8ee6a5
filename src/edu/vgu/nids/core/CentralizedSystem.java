package edu.vgu.nids.core;

import edu.vgu.nids.consensus.Module;
import edu.vgu.nids.graph.ConnectedTree;
import edu.vgu.nids.graph.GraphNode;
import edu.vgu.nids.math.PreciseNumber;

public final class CentralizedSystem implements ISystem {

	private int cost;
	private Network network;
	
	private double adjustment;
	
	public CentralizedSystem(Network network, double adjustment) {
		this.network = network;
		this.adjustment = adjustment;
		
		ConnectedTree breadthFirstTree = network.GetGraph().GetBreadthFirstTree();
		
		cost = 0;
		for (GraphNode node: breadthFirstTree.GetNodes())
			cost += (int)node.GetData();
	}
	
	@Override
	public Report Evaluate() {
		
		PreciseNumber[] product = new PreciseNumber[2];
		product[0] = new PreciseNumber(1);
		product[1] = new PreciseNumber(1);
		
		for (GraphNode node: network.GetGraph().GetNodes()) {
			Module module = (Module)node.GetData();
			product[0] = product[0].mul(module.GetPriors()[0]);
			product[1] = product[1].mul(module.GetPriors()[1]);
		}
		
		product[0] = PreciseNumber.Exp10(product[0].Log10() / network.GetGraph().GetSize());
		product[1] = PreciseNumber.Exp10(product[1].Log10() / network.GetGraph().GetSize());
		
		State state;
		if (product[0].Log10() - product[1].Log10() > adjustment)
			state = State.NORMAL;
		else
			state = State.ATTACK;		
		
		return new Report(state, cost, product);
	}

}
