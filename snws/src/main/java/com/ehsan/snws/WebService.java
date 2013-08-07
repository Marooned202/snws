package com.ehsan.snws;

public class WebService extends Node {

	public double su;
	public double rt;
	public int cost;
	public int type;
	public double QoS;
	public int compCount;
	public int reqSubCount;
	
	public WebService(int argName, int type) {
		super(argName);
		this.type = type;
		su = Math.random();
		rt = Math.random();
		cost = (int) Math.random()*100;
		QoS = Math.random();	     
	}
	
	@Override
	public String toString () {
		String str = "Id: " + id + ", type: " + type + ", su: " + su + ", rt: " + rt + ", cost: " + cost + " , QoS:" + QoS + ", compCount"+ compCount +",reqSubCount:"+ reqSubCount;
		return str;
	}

}
