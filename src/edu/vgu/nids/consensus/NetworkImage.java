package edu.vgu.nids.consensus;

import edu.vgu.nids.core.Network;

public final class NetworkImage {
	
	private Belief[] data;
	
	private NetworkImage(int size) {
		data = new Belief[size];
	}
	
	public static NetworkImage Capture(Network network) {
		NetworkImage image = new NetworkImage(network.GetGraph().GetSize());
		
		for (int i = 0; i < network.GetGraph().GetSize(); i++)
			image.data[i] = ((Module)network.GetGraph().GetNodes().get(i).GetData()).GetBelief();
		
		return image;
	}
	
	public Belief[] GetImage() {
		return data;
	}
}
