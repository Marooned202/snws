package com.ehsan.snws;

public class User extends Node {

	public double reqResponsetime;
	public double reqSuccessability;
	
	public int interactionCount;
	public int wsEdgeCount;
	
	public User(int argName) {
		super(argName);
		
		reqResponsetime = Math.random();
		reqSuccessability = Math.random();
	}
	
	@Override
	public String toString () {
		String str = "Id: " + id + ", reqResponsetime: " + reqResponsetime + ", reqSuccessability: " + reqSuccessability + ", interactionCount: " + interactionCount + ", wsEdgeCount: " + wsEdgeCount ;
		return str;
	}
	
}
