package com.ehsan.snws;

public class Edge implements Comparable<Edge> {
    
    public Node from, to;
    public int weight;
    
    public Edge(final Node argFrom, final Node argTo){
        from = argFrom;
        to = argTo;
    }
    
    public int compareTo(final Edge argEdge){
        return weight - argEdge.weight;
    }
 }