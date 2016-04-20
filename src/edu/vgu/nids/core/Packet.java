package edu.vgu.nids.core;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import weka.core.Instance;

/**
 * A class describes a set of <b>Instances</b> which will be used as input for the <b>Network</b>.
 */
public final class Packet {

	private List<Instance> data;
	private Random rnd;
	
	public Packet(List<Instance> input) {
		data = input;
		rnd = new Random();
	}
	public Packet() {
		this(new Vector<Instance>());
	}
	
	public List<Instance> GetData() {
		return data;
	}
	public void Add(Instance instance) {
		data.add(instance);
	}

	public Instance PickRandomlyAndRemove() {
		int index = rnd.nextInt(data.size());
		
		Instance instance = data.get(index);
		data.remove(index);
		
		return instance;
	}
}
