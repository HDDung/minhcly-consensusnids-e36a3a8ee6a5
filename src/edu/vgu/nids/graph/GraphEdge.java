package edu.vgu.nids.graph;

public final class GraphEdge {

	private GraphNode[] ends;
	private Object data;

	public GraphEdge(GraphNode e1, GraphNode e2) {
		this(e1, e2, null);
	}
	public GraphEdge(GraphNode e1, GraphNode e2, Object data) {
		ends = new GraphNode[2];
		ends[0] = e1;
		ends[1] = e2;
		this.data = data;
	}

	public GraphNode[] GetEndpoints() {
		return ends;
	}

	@Override
	public boolean equals(Object obj) {
		GraphEdge edge = (GraphEdge)obj;
		return ((ends[0] == edge.ends[0] && ends[1] == edge.ends[1]) ||
				(ends[0] == edge.ends[1] && ends[1] == edge.ends[0]));
	}
	
	public Object GetData() {
		return data;
	}
	public void SetData(Object data) {
		this.data = data;
	}
}
