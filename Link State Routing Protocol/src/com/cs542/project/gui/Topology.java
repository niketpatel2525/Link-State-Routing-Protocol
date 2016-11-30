package com.cs542.project.gui;

import java.util.ArrayList;
import java.util.List;

import com.cs542.project.graph.Edge;
import com.cs542.project.graph.Graph;
import com.cs542.project.graph.Vertex;

public class Topology {

	public Topology() {

	}

	Graph g;
	Vertex v;
	Edge e;
	ArrayList<Vertex> routers;
	ArrayList<Edge> links;

	public Topology(Graph g, Vertex v, Edge e, ArrayList<Vertex> routers, ArrayList<Edge> links) {
		super();
		this.g = g;
		this.v = v;
		this.e = e;
		this.routers = routers;
		this.links = links;
	}

	public Graph getG() {
		g = new Graph(getRouters(), getLinks());
		return g;
	}

	public void setG(Graph g) {
		this.g = g;
	}

	public Vertex getV() {
		return v;
	}

	public void setV(Vertex v) {
		this.v = v;
	}

	public Edge getE() {
		return e;
	}

	public void setE(Edge e) {
		this.e = e;
	}

	public ArrayList<Vertex> getRouters() {
		return routers;
	}

	public void setRouters(ArrayList<Vertex> routers) {
		this.routers = routers;
	}

	public ArrayList<Edge> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Edge> links) {
		this.links = links;
	}

}
