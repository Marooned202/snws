package com.ehsan.snws;


import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Collection;

public class Graph {

	private Map<Node, List<Edge>> adjacencies = new HashMap<Node, List<Edge>>();

	public void addUniqueEdge(Edge edge) { // Checks if Edge exists will not insert it
		
		List<Edge> edges = getAllEdges();		
		
		if (!getAllEdges().contains(edge))
			addEdge (edge);
	}
	
	public void addEdge(Edge edge) {
		List<Edge> list;
		if(!adjacencies.containsKey(edge.from)) {
			list = new ArrayList<Edge>();
			adjacencies.put(edge.from, list);
		} else {
			list = adjacencies.get(edge.from);
		}
		list.add(edge);
	}

	public void addNode(Node node) {
		if(!adjacencies.containsKey(node)) {
			List<Edge> list = new ArrayList<Edge>();
			adjacencies.put(node, list);
		}
	}

	public List<Edge> getAdjacent(Node source) {
		return adjacencies.get(source);
	}

	//   public void reverseEdge(Edge e) {
	//       adjacencies.get(e.from).remove(e);
	//       addEdge(e.to, e.from, e.weight);
	//   }
	//
	//   public void reverseGraph() {
	//       adjacencies = getReversedList().adjacencies;
	//   }

	//   public Graph getReversedList() {
	//       Graph newlist = new Graph();
	//       for(List<Edge> edges : adjacencies.values()) {
	//           for(Edge e : edges) {
	//               newlist.addEdge(e.to, e.from, e.weight);
	//           }
	//       }
	//       return newlist;
	//   }

	public Set<Node> getSourceNodeSet() {
		return adjacencies.keySet();
	}

	public List<Edge> getAllEdges() {
		List<Edge> edges = new ArrayList<Edge>();
		for(List<Edge> e : adjacencies.values()) {
			edges.addAll(e);
		}
		return edges;
	}
	
	public Edge getLinkBetweenTwoNodes (Node from, Node to) {
		for(Edge e : getAllEdges()) {
			if (e.from.equals(from) && e.to.equals(to))
				return e;
		}
		return null;
	}

	public List<Edge> getAllLinksToOtherWebServices(Node source) {
		List<Edge> res = new ArrayList<Edge>();
		List<Edge> edges = adjacencies.get(source);
		for(Edge e : edges) {
			if (e.from.id == source.id) {
				if (e.to instanceof WebService)
					res.add(e);
			}    		       		   
		}
		return res;
	}

	public List<Edge> getAllLinksToOtherUsers(Node source) {
		List<Edge> res = new ArrayList<Edge>();
		List<Edge> edges = adjacencies.get(source);
		for(Edge e : edges) {
			if (e.from.id == source.id) {
				if (e.to instanceof User)
					res.add(e);
			}    		       		   
		}
		return res;
	}
	
	public List<WebService> getAllConnectedWebServices (Node source) {
		List<Edge> edges = getAllLinksToOtherWebServices(source);
		List<WebService> res = new ArrayList<WebService>();
		for (Edge edge: edges) {
			res.add((WebService) edge.to);
		}
		return res;
	}
	
	public List<WebService> getAllConnectedWebServicesOfType (Node source, int serviceType) {
		List<Edge> edges = getAllLinksToOtherWebServices(source);
		List<WebService> res = new ArrayList<WebService>();
		for (Edge edge: edges) {
			WebService ws = (WebService) edge.to;
			if (ws.type == serviceType)
				res.add((WebService) edge.to);
		}
		return res;
	}
	
	public List<User> getAllConnectedUsers (Node source) {
		List<Edge> edges = getAllLinksToOtherUsers(source);
		List<User> res = new ArrayList<User>();
		for (Edge edge: edges) {
			res.add((User) edge.to);
		}
		return res;
	}

	public List<Node> getAllWebServices () {
		List<Node> wsList = new ArrayList<Node>();
		for (Node node: adjacencies.keySet()) {
			if (node instanceof WebService) wsList.add(node);
		}
		return wsList;
	}

	public List<Node> getAllUsers () {
		List<Node> wsList = new ArrayList<Node>();
		for (Node node: adjacencies.keySet()) {
			if (node instanceof User) wsList.add(node);
		}
		return wsList;
	}

	public int getWebServicesCount () {
		return getAllWebServices().size();
	}

	public int getUsersCount () {
		return getAllUsers().size();
	}

	public User getRandomUser () {
		Random rnd = new Random();
		return (User) getAllUsers().get(rnd.nextInt(getUsersCount()));
	}

	public User getRandomWebService () {
		Random rnd = new Random();
		return (User) getAllWebServices().get(rnd.nextInt(getWebServicesCount()));
	}

	// This method finds all web services which are neighbor of "user" and provide service type of "serviceType"
	public ArrayList<WebService> getAllWebServicesUserServiceType(User user, Integer serviceType) {
		ArrayList<WebService> webServices = new ArrayList<WebService>();
		
		webServices.addAll(getAllConnectedWebServicesOfType(user, serviceType));
		for (User neighbor: getAllConnectedUsers(user)) {
			webServices.addAll(getAllConnectedWebServicesOfType(neighbor, serviceType));
		}		
		
		return webServices;
	}
	
	
	public void print (PrintWriter output) {
		System.out.println("Users: ");
		output.println("Users: ");
		for (Node node: adjacencies.keySet()) {
			if (node instanceof User) {
				User user = (User) node;
				System.out.println(user);
				output.println(user);

				List<Edge> userEdges = getAllLinksToOtherUsers(node);
				for (Edge edge: userEdges) {
					System.out.println("\t[U---U] " + edge);
					output.println("\t[U---U] " + edge);
				}

				List<Edge> webServiceEdges = getAllLinksToOtherWebServices(node);
				for (Edge edge: webServiceEdges) {
					System.out.println("\t[U---W] " + edge);
					output.println("\t[U---W] " + edge);
				}
			}
		}

		System.out.println("");
		System.out.println("Web Services: ");
		output.println("");
		output.println("Web Services: ");
		for (Node node: adjacencies.keySet()) {
			if (node instanceof WebService) {
				WebService webService = (WebService) node;
				System.out.println(webService);
				output.println(webService);
				
				List<Edge> userEdges = getAllLinksToOtherUsers(node);
				for (Edge edge: userEdges) {
					System.out.println("\t[W---U] " + edge);
					output.println("\t[W---U] " + edge);
				}

				List<Edge> webServiceEdges = getAllLinksToOtherWebServices(node);
				for (Edge edge: webServiceEdges) {
					System.out.println("\t[W---W] " + edge);
					output.println("\t[W---W] " + edge);
				}
				
				webService.printHistoryOfUsersService(output);
			}
		}
	}
	
	public void printDocFormat (PrintWriter output) {
		System.out.println("digraph G {");
		for (Node node: adjacencies.keySet()) {
			if (node instanceof User) {
				User user = (User) node;
				//System.out.println(user);
				//output.println(user);
				
//				System.out.println("subgraph Users {");				
//				System.out.println("node [style=filled,color=white];");
//				System.out.println("style=filled;");
//				System.out.println("color=lightgrey;");


				//System.out.println("edge [color=brown]");
				List<Edge> userEdges = getAllLinksToOtherUsers(node);
				for (Edge edge: userEdges) {
					System.out.println(" " + edge.from.id + "->" + edge.to.id);// + " [style=bold];");
					//System.out.println("\t[U---U] " + edge);
					//output.println("\t[U---U] " + edge);
					
				}
				
//				System.out.println("}");

				//System.out.println("edge [color=black]");
				List<Edge> webServiceEdges = getAllLinksToOtherWebServices(node);
				for (Edge edge: webServiceEdges) {
					System.out.println(" " + edge.from.id + "->" + edge.to.id);// + " [style=bold];");
					//System.out.println("\t[U---W] " + edge);
					//output.println("\t[U---W] " + edge);
				}
			}
		}

		for (Node node: adjacencies.keySet()) {
			if (node instanceof WebService) {
				WebService webService = (WebService) node;
				//System.out.println(webService);
				//output.println(webService);
				
				List<Edge> userEdges = getAllLinksToOtherUsers(node);
				//System.out.println("edge [color=red]");
				for (Edge edge: userEdges) {
					System.out.println(" " + edge.from.id + "->" + edge.to.id);// + " [style=bold];");
					//System.out.println("\t[W---U] " + edge);
					//output.println("\t[W---U] " + edge);
				}

//				System.out.println("subgraph WebServices {");
				//System.out.println("edge [color=blue]");
				List<Edge> webServiceEdges = getAllLinksToOtherWebServices(node);
				for (Edge edge: webServiceEdges) {
					System.out.println(" " + edge.from.id + "->" + edge.to.id);// + " [style=bold];");
					//System.out.println("\t[W---W] " + edge);
					//output.println("\t[W---W] " + edge);
				}
//				System.out.println("}");
				
				//webService.printHistoryOfUsersService(output);
			}
		}
		
		for (Node node: adjacencies.keySet()) {
			if (node instanceof User) {
				System.out.printf("%d [color=lightblue,style=filled];\n", node.id);
			}
			if (node instanceof WebService) {
				System.out.printf("%d [color=blue,style=filled];\n", node.id);
			}
		}
		
		
		System.out.println("}");
	}
	
	public void report () {
		
	}

}