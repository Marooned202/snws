package com.ehsan.snws;

public class WebService extends Node {

	public double su;
	public double rt;
	public int cost;
	public int type;
	
	public WebService(int argName, int type) {
		super(argName);
		this.type = type;
		su = Math.random();
		rt = Math.random();
		cost = (int) Math.random()*100;
	}
	
	@Override
	public String toString () {
		String str = "Id: " + id + ", type: " + type + ", su: " + su + ", rt: " + rt + ", cost: " + cost;
		return str;
	}

}
