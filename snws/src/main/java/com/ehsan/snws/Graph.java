package com.ehsan.snws;


import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collection;

public class Graph {

   private Map<Node, List<Edge>> adjacencies = new HashMap<Node, List<Edge>>();

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

   public Collection<Edge> getAllEdges() {
       List<Edge> edges = new ArrayList<Edge>();
       for(List<Edge> e : adjacencies.values()) {
           edges.addAll(e);
       }
       return edges;
   }
   
   public Collection<Edge> getAllLinksToOtherWebServices(Node source) {
       List<Edge> res = new ArrayList<Edge>();
       for(List<Edge> edges : adjacencies.values()) {
    	   for(Edge e : edges) {
    		   if (e.from.id == source.id) {
    			   if (e.to instanceof WebService)
    				   res.add(e);
    		   }    		       		   
           }
       }
       return res;
   }
   
   public Collection<Edge> getAllLinksToOtherUsers(Node source) {
       List<Edge> res = new ArrayList<Edge>();
       for(List<Edge> edges : adjacencies.values()) {
    	   for(Edge e : edges) {
    		   if (e.from.id == source.id) {
    			   if (e.to instanceof User)
    				   res.add(e);
    		   }    		       		   
           }
       }
       return res;
   }
   
   public void print () {
	   System.out.println("Users: ");
	   for (Node node: adjacencies.keySet()) {
		   if (node instanceof User) {
			   User user = (User) node;
			   System.out.println(user);
			   
			   Collection<Edge> userEdges = getAllLinksToOtherUsers(node);
			   for (Edge edge: userEdges) {
				   System.out.println("\t" + edge);
			   }
			   
			   Collection<Edge> webServiceEdges = getAllLinksToOtherWebServices(node);
			   for (Edge edge: webServiceEdges) {
				   System.out.println("\t" + edge);
			   }
		   }
	   }
	   
	   System.out.println("Web Services: ");
	   for (Node node: adjacencies.keySet()) {
		   if (node instanceof WebService) {
			   WebService webService = (WebService) node;
			   System.out.println(webService);
		   }
	   }
   }
   
   
}