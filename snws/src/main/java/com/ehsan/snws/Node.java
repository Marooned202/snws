package com.ehsan.snws;

public class Node {

	public int id;
	public String name; 
	public int compCount;
	public int interactionCount;

	public Node(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Node)) return false;
		Node otherNode = (Node)other;
		return (otherNode.id == this.id);
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + id;
		return hash;
	}
}