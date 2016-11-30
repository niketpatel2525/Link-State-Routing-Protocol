package com.cs542.project.testcase;

import java.io.*;
import java.util.*;

import com.cs542.project.graph.*;

/* This class is used to read file and create topology. 
 * All necessary file operations are handled from here. 
 * */
public class FileOperation {
	private static ArrayList<Vertex> nodes;
	private static ArrayList<Edge> edges;
	private static Graph graph;
	private static int[][] topology1;
	public static int LINK_ID = 0;

	public FileOperation() {
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		graph = new Graph();

	}

	public static int[][] getTopology1() {
		return topology1;
	}

	public static ArrayList<Vertex> getNodes() {
		return nodes;
	}

	public static ArrayList<Edge> getEdges() {
		return edges;
	}

	public static Graph getGraph() {
		return graph;
	}

	public static void topology(String filename) throws Exception {
		Scanner s = new Scanner(new File(filename));
		int rows = 0;
		int columns = 0;
		String costs;
		String[] cost_tokens;
		while (s.hasNextLine()) {
			cost_tokens = s.nextLine().split(" ");
			rows++;
			columns = cost_tokens.length;
		}
		String cost_values;
		int i;
		int j;

		topology1 = new int[rows][columns];
		String[] cost;
		s.close();
		Scanner sc = new Scanner(new File(filename));
		cost_values = sc.nextLine();
		for (i = 0; i < rows; i++) {
			cost = cost_values.split(" ");
			for (j = 0; j < cost.length; j++) {
				topology1[i][j] = Integer.parseInt(cost[j]);
			}
			if (sc.hasNextLine()) {
				cost_values = sc.nextLine();
			}
		}

		for (int p = 0; p < rows; p++) {
			Vertex location = new Vertex("" + p, true);
			nodes.add(location);
		}

		for (int k = 0; k < rows; k++) {
			for (int l = 0; l < columns; l++) {
				if (topology1[k][l] >= 0) {
					Edge e = new Edge("" + ++LINK_ID, nodes.get(k), nodes.get(l), topology1[k][l]);
					edges.add(e);
				}

			}

			graph.setVertexs(nodes);
			graph.setEdges(edges);
		}

	}

}