package com.ehsan.snws;

public class Node implements Comparable<Node> {
    
    public int id;
    public String name;    
    
    public Node(int id) {
        this.id = id;
    }
    
    public int compareTo(final Node node) {
        return node == this ? 0 : -1;
    }
 }