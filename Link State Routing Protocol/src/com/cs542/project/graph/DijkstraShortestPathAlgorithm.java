package com.cs542.project.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* This class is a backbone of Link state routing protocol. 
 * All the needed functions are implemented here and all GUI uses it as per their need.
 * Algo structure is pure object oriented and it uses graph object to parse routers and links among those routers in topology.
 * topology is provided by other class.
 */

public class DijkstraShortestPathAlgorithm {
	/*
	 * each router's default reachable distance is set to maximum integer value.
	 */
	protected static final int NOT_REACHABLE = Integer.MAX_VALUE;

	/*
	 * This two data structure is used to manage routers and their links
	 * throughout the link state protocol.
	 */
	protected List<Vertex> routers;
	protected List<Edge> links;

	/*
	 * This data structure is used to manage visited and unvisited nodes while
	 * algo is executing.
	 */

	protected Set<Vertex> visitedNodes;
	protected Set<Vertex> unVisitedNodes;

	/*
	 * This data structure is used to keep track of path for making link to each
	 * router from src to dest router.
	 */
	protected HashMap<Vertex, Vertex> lastVisitedNode;

	/*
	 * This data structure is used to keep their shortest distancec from src
	 * node.
	 */
	protected HashMap<Vertex, Integer> distance;

	/*
	 * This data structure used to return all possible shortest path from src to
	 * dest router.
	 */
	ArrayList<String> allPath;

	/* ASPL works as HashMap */
	protected ArrayList<ASPL> aspl;

	/* Constructor used for initialize data structure. */
	public DijkstraShortestPathAlgorithm(Graph graph) {
		routers = new ArrayList<Vertex>(graph.getVertexs());
		links = new ArrayList<Edge>(graph.getEdges());
	}

	/*
	 * This function is the beginning of algo. it starts from src node and tries
	 * to reach all the working routers.
	 */
	public void findPath(Vertex source) {
		checkRouterStatus();
		visitedNodes = new HashSet<Vertex>();
		unVisitedNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		lastVisitedNode = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unVisitedNodes.add(source);
		aspl = new ArrayList<ASPL>();

		do {
			Vertex nearestNode = findNearestReachableNode(unVisitedNodes);

			unVisitedNodes.remove(nearestNode);
			visitedNodes.add(nearestNode);
			findReachableNodeOfNewlyAddedNode(nearestNode);

		} while (unVisitedNodes.size() > 0);

	}

	/*
	 * This function is used to add working router to the router's data
	 * structure.
	 */

	private void checkRouterStatus() {
		// TODO Auto-generated method stub
		List<Edge> upLinks = new ArrayList<Edge>();
		for (Edge e : links) {
			if (e.getSource().isUp() && e.getDestination().isUp()) {
				// links.remove(e);
				upLinks.add(e);
			}
		}
		links = upLinks;
	}

	/*
	 * This function is used to find reachable Nodes of newly added Node in
	 * visited node. It find minimum weight from visited nodes to unvisited node
	 * and add node with minimum weight.
	 */
	private void findReachableNodeOfNewlyAddedNode(Vertex v) {
		// TODO Auto-generated method stub
		ArrayList<Vertex> neighbour = whoIsMyNeighbour(v);
		for (Vertex destination : neighbour) {

			if (myDistance(destination) > myDistance(v) + distanceBetweenTwoAdjacentNode(v, destination)) {
				distance.put(destination, myDistance(v) + distanceBetweenTwoAdjacentNode(v, destination));
				unVisitedNodes.add(destination);
				lastVisitedNode.put(destination, v);
			}
			if (myDistance(destination) == myDistance(v) + distanceBetweenTwoAdjacentNode(v, destination)) {
				aspl.add(new ASPL(destination, v));
			}
		}
	}

	/*
	 * This function returns arraylist of direct reachable nodes from given
	 * node.
	 */
	private ArrayList<Vertex> whoIsMyNeighbour(Vertex v) {
		// TODO Auto-generated method stub
		ArrayList<Vertex> neighbour = new ArrayList<Vertex>();
		for (Edge link : links) {
			if (link.getSource().equals(v) && !isNearestNodeIsVisted(link.getDestination())) {
				neighbour.add(link.getDestination());
			}
		}
		return neighbour;
	}

	/* This function returns weight between given two nodes. */
	private int distanceBetweenTwoAdjacentNode(Vertex source, Vertex destination) {
		// TODO Auto-generated method stub
		for (Edge e : links) {
			if (e.getSource().equals(source) && e.getDestination().equals(destination))
				return e.getWeight();
		}
		return NOT_REACHABLE;
	}

	/* This function returns a node with minimum weight from visited nodes. */
	private Vertex findNearestReachableNode(Set<Vertex> unVisitedNodes) {
		// TODO Auto-generated method stub
		Vertex nNode = null;
		for (Vertex v : unVisitedNodes) {
			if (nNode == null) {
				nNode = v;
			} else {
				int d1 = myDistance(nNode);
				int d2 = myDistance(v);
				if (d1 > d2 && d1 >= 0 && d2 >= 0) {
					nNode = v;
				}
			}
		}
		return nNode;
	}

	/* This function returns exact path from src to dest router. */
	public ArrayList<Vertex> makeDijkstraPathFromSourceTo(Vertex destination) {

		if (!lastVisitedNode.containsKey(destination)) {
			return null;
		} else {
			ArrayList<Vertex> reversePath = new ArrayList<Vertex>();
			Vertex currentRouter = destination;

			reversePath.add(currentRouter);
			do {
				currentRouter = lastVisitedNode.get(currentRouter);
				reversePath.add(currentRouter);
			} while (lastVisitedNode.get(currentRouter) != null);

			Collections.reverse(reversePath);

			return reversePath;
		}
	}

	/*
	 * this function check the nearest node from given node is visited or not.
	 */
	private boolean isNearestNodeIsVisted(Vertex destination) {
		// TODO Auto-generated method stub
		boolean visited = false;
		for (Vertex v : visitedNodes) {
			if (v.equals(destination)) {
				visited = true;
				break;
			}
		}
		return visited;
	}

	/*
	 * this funciton returns its distance from distance data structure and if it
	 * is not yet inserted then returns NOT_REACHABLE.
	 */
	private int myDistance(Vertex nNode) {
		// TODO Auto-generated method stub
		try {
			int dist = (int) distance.get(nNode);
			return dist;
		} catch (Exception e) {
			return NOT_REACHABLE;
		}
	}

	/*
	 * this is the function which gives routing table for called router. It find
	 * shortest path first and find 1st link to which is going to forward from
	 * its location and that link dest router is the o/p link.
	 */
	public HashMap<Vertex, Vertex> fetchRoutingTable() {
		HashMap<Vertex, Vertex> routingTable = new HashMap<Vertex, Vertex>();

		for (Vertex v : routers) {
			if (v.isUp()) {
				ArrayList<Vertex> routePath = makeDijkstraPathFromSourceTo(v);
				if (routePath == null) {
					routingTable.put(v, null);
				} else {
					routingTable.put(v, routePath.get(1));
				}
			}
		}

		return routingTable;
	}

	/* this function returns broadcast router with its broadcasting cost. */

	public String broadCastRouter(Graph g) {
		HashMap<Vertex, Integer> broadCast = new HashMap<Vertex, Integer>();
		for (Vertex sv : routers) {

			int cost = 0;
			for (Vertex dv : routers) {
				if (sv.isUp() && dv.isUp()) {
					DijkstraShortestPathAlgorithm dij = new DijkstraShortestPathAlgorithm(g);
					dij.findPath(sv);

					int v = dij.costToReach(dv);
					System.out.println("INDV:" + v);

					if (v != NOT_REACHABLE)
						cost += v;
					else {
						cost = -999;
						break;
					}
					dij = null;
				} else {
					System.out.println("" + sv.getvertexId() + " DOWN");
				}
			}
			System.out.println("SV:" + sv.getvertexId() + "  cost:" + cost);
			if (sv.isUp())
				if (cost != -999)
					broadCast.put(sv, cost);
		}

		HashMap<Integer, Vertex> result = findMinmalCostVertex(broadCast);

		Iterator it = result.entrySet().iterator();
		String id = "";
		String value = "";

		Map.Entry<Integer, Vertex> b = (Entry) it.next();
		value = b.getKey() + "";
		id = b.getValue().getvertexId();
		System.out.println(value + id);

		return "Broadcast Router: Router: " + id + " with " + value + " cost";
	}

	/*
	 * this is a recursive function which will add all possible path to the data
	 * structure.
	 */
	public void gasp(Vertex key, Vertex value, String str) {
		str += value.getvertexId() + " ";
		if (isAvailable(value)) {
			for (ASPL a : aspl) {
				if (a.getKey().getvertexId().equals(value.getvertexId())) {
					gasp(value, a.getValue(), str);
				}
			}
		} else {
			allPath.add(str);
		}
	}

	/*
	 * it is a kind of terminating condition for recursive function. it checks
	 * for availability of router in a ASPL
	 */
	private boolean isAvailable(Vertex v) {
		// TODO Auto-generated method stub
		for (ASPL a : aspl) {
			if (a.getKey().getvertexId().equals(v.getvertexId()))
				return true;
		}

		return false;
	}

	/*
	 * This function returns arraylist of all possible path from src to dest
	 * routers.
	 */
	public ArrayList<String> giveAllShortestPath(Vertex destination) {

		allPath = new ArrayList<String>();
		for (ASPL a : aspl) {
			if (a.getKey().getvertexId().equals(destination.getvertexId())) {
				String str = a.getKey().getvertexId() + " ";
				gasp(a.getKey(), a.getValue(), str);
			}

		}

		return allPath;
	}

	/* this function will find minimal cost from broadcast router arraylist. */
	private HashMap<Integer, Vertex> findMinmalCostVertex(HashMap<Vertex, Integer> broadCast) {
		// TODO Auto-generated method stub
		HashMap<Integer, Vertex> result = new HashMap<Integer, Vertex>();
		Iterator it = broadCast.entrySet().iterator();
		int min = broadCast.get(routers.get(0));
		Vertex id = routers.get(0);

		while (it.hasNext()) {
			Map.Entry<Vertex, Integer> b = (Entry) it.next();
			System.out.println(" K:" + b.getKey().getvertexId() + " V:" + b.getValue());
			if (min > b.getValue()) {
				min = b.getValue();
				id = b.getKey();

			}
		}

		result.put(min, id);
		return result;
	}

	/*
	 * this is the actual function which gives cost to reach dest router from
	 * src router.
	 */
	public int costToReach(Vertex vertex) {
		// TODO Auto-generated method stub
		return myDistance(vertex);
	}

	/* this is a data structure used for */
	private class ASPL {
		private Vertex key;
		private Vertex value;

		public ASPL() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ASPL(Vertex key, Vertex value) {
			// TODO Auto-generated constructor stub
			this.key = key;
			this.value = value;
		}

		public Vertex getKey() {
			return key;
		}

		public void setKey(Vertex key) {
			this.key = key;
		}

		public Vertex getValue() {
			return value;
		}

		public void setValue(Vertex value) {
			this.value = value;
		}
	}
}
