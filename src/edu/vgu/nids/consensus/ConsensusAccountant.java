package edu.vgu.nids.consensus;

import edu.vgu.nids.core.Accountant;
import edu.vgu.nids.core.State;

public class ConsensusAccountant extends Accountant {

	private int sumIteration;
	
	public void SetOutput(State state, int iteration) {
		SetOutput(state);
		sumIteration += iteration;
	}
	
	public double GetAverageIteration() {
		return (double)sumIteration / GetCount();
	}
}
