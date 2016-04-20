package edu.vgu.nids.consensus.weight;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import edu.vgu.nids.graph.ConnectedGraph;
import edu.vgu.nids.graph.GraphEdge;

public final class BestConstantEdgeWeight implements IConsensusWeight {

	@Override
	public void SetEdgeWeight(ConnectedGraph graph) {
		
		Matrix laplacian = graph.GetLaplacian();
		
		EigenvalueDecomposition decompositor = new EigenvalueDecomposition(laplacian);
		double[] eigenvalues = decompositor.getRealEigenvalues();
		
		double constantWeight = 2 / (eigenvalues[1] + eigenvalues[graph.GetSize() - 1]); 
		
		for (GraphEdge edge: graph.GetEdges())
			edge.SetData(constantWeight);
	}

	@Override
	public String GetName() {
		return "Best-constant Edge Weight";
	}

}
