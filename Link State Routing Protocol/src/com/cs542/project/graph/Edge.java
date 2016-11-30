package com.cs542.project.graph;

/* This class is represent links between routers. */
public class Edge {
	protected String edgeId;
	protected Vertex source;
	protected Vertex destination;
	protected int weight;

	public Edge(String edgeId, Vertex source, Vertex destination, int weight) {
		super();
		this.edgeId = edgeId;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(String edgeId) {
		this.edgeId = edgeId;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Edge from " + source.getvertexId() + " to " + destination.getvertexId();
	}

}
