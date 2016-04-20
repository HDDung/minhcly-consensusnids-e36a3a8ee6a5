package edu.vgu.nids.core;

import edu.vgu.nids.consensus.weight.IConsensusWeight;

public final class TestPack {

	private Network network;
	private IConsensusWeight weightModel;
	private int index;
	
	private static int count = 0;
	
	public TestPack(Network network, IConsensusWeight weightModel) {
		this.index = count;
		this.network = network;
		this.weightModel = weightModel;
		
		count++;
	}
	
	public int GetIndex() {
		return index;
	}
	public Network GetNetwork() {
		return network;
	}
	public IConsensusWeight GetWeightModel() {
		return weightModel;
	}
	
	public int GetSize() {
		return network.GetGraph().GetSize();
	}
	public String GetName() {
		return network.GetGraph().GetName();
	}
	public String GetWeightName() {
		return weightModel.GetName();
	}
}
