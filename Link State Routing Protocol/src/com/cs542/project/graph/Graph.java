package com.cs542.project.graph;

import java.util.ArrayList;

/* This class is used to represent topology */
public class Graph {
	protected ArrayList<Vertex> vertexs;
	protected ArrayList<Edge> edges;

	public Graph() {

	}

	public Graph(ArrayList<Vertex> vertexs, ArrayList<Edge> edges) {
		super();
		this.vertexs = vertexs;
		this.edges = edges;
	}

	public ArrayList<Vertex> getVertexs() {
		return vertexs;
	}

	public void setVertexs(ArrayList<Vertex> vertexs) {
		this.vertexs = vertexs;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

}
