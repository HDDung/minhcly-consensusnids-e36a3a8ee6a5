package edu.vgu.nids.consensus;

import java.util.List;
import java.util.Vector;

import edu.vgu.nids.graph.GraphNode;

public final class NodeLog {
	
	List<Belief> log;
	GraphNode node;
	
	public NodeLog(GraphNode node) {
		this.node = node;
		log = new Vector<Belief>();
	}
	
	public void Capture() {
		log.add(((Module)node.GetData()).GetBelief());
	}
	
	public Belief[] GetLog() {
		return log.toArray(new Belief[0]);
	}
}
