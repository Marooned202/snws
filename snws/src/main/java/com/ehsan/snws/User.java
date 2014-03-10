package com.ehsan.snws;

import com.ehsan.snws.Graph;
import com.ehsan.snws.Node;

public class User extends Node {

	 public static int userEdgesCounter;
	public double reqResponsetime;
	public double reqSuccessability;
	
	public int interactionCount;
	//public static int wsEdgeCount=0;
	
	public User(int argName) {
		super(argName);
		
		reqResponsetime = Math.random();
		reqSuccessability = Math.random();
		
	}
	
	@Override
	public String toString () {
		String str = "User Id: " + id + ", reqResponsetime: " + reqResponsetime + ", reqSuccessability: " + reqSuccessability + ", interactionCount: " + interactionCount + ", wsEdgeCount: " + Graph.wsEdgeCount + "userEdgesCounter:"+ userEdgesCounter;
		return str;
	}
	
}
