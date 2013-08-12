package com.ehsan.snws;

public class Edge implements Comparable<Edge> {
    
    public Node from, to;
    public int weight;
    public int type = 0;
    
    public Edge(final Node argFrom, final Node argTo){
        from = argFrom;
        to = argTo;
        type = 0;
    }
    
    public int compareTo(final Edge argEdge){
        return weight - argEdge.weight;
    }
    
    @Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Edge)) return false;
		Edge otherEdge = (Edge)other;		
		return (otherEdge.from.id == this.from.id && otherEdge.to.id == this.to.id && otherEdge.type == this.type);
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + from.id;
		hash = 27 * hash + to.id;
		hash = 7 * hash + (type+11);
		return hash;
	}
 }