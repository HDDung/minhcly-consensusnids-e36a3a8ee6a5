package edu.vgu.nids.graph.model;

import java.util.Random;

import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphNode;

/**
 * A class represents a graph generated randomly. The central node of this graph is the node with highest degree.<br>
 * A random graph is defined by the number of nodes and the number of edges.
 */
public class RandomGraph extends ConnectedGraph {

	public RandomGraph(int numNodes, int numEdges) {

		// Limit check
		if (numEdges < numNodes - 1 || numEdges > numNodes * (numNodes - 1) / 2)
			throw new IllegalArgumentException(numEdges + " edges is invalid for a graph with " + numNodes + " nodes");
		
		GraphNode[] nodes = new GraphNode[numNodes];
		nodes[0] = new GraphNode();
		
		Random rnd = new Random();
		int edgeCount = 0;
		
		// Ensure that all nodes are connected
		for (int i = 1; i < numNodes; i++) {
			int other = rnd.nextInt(i);
			
			nodes[i] = new GraphNode();
			nodes[other].Add(nodes[i]);
			
			edgeCount++;
		}
		
		// Randomly choose other edges
		for (; edgeCount < numEdges; edgeCount++) {
			while (true) {
				int begin = rnd.nextInt(numNodes);
				int end;
				do
					end = rnd.nextInt(numNodes);
				while (begin == end);
				
				if (nodes[begin].Add(nodes[end]) != null)
					break;
			}
		}
		
		// Look for the node with highest degree
		GraphNode maxNode = nodes[0];
		for (int i = 1; i < numNodes; i++)
			if (nodes[i].GetDegree() > maxNode.GetDegree())
				maxNode = nodes[i];
		
		SetCentralNode(maxNode);
		Invalidate();
		
		SetName("Random graph " + numNodes + "-" + numEdges);
	}
	
	
}
