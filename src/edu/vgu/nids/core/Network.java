package edu.vgu.nids.core;

import edu.vgu.nids.consensus.Module;
import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a network described by a <b>ConnectedGraph</b>.
 * <b>Network</b> class uses <b>Packet</b> as input and calculate the prior probability
 * for each node.
 */
public final class Network {

	private ConnectedGraph m_graph;
	private Packet m_packet;
	
	public Network(ConnectedGraph graph) {
		m_graph = graph;
		
		for (GraphNode node: m_graph.GetNodes())
			node.SetData(new Module());
	}
	
	public ConnectedGraph GetGraph() {
		return m_graph;
	}
	
	public void Audit(Packet packet) {
		m_packet = packet;

		for (GraphNode node: m_graph.GetNodes())			
			((Module)node.GetData()).Audit(m_packet.PickRandomlyAndRemove());	
	}
}
