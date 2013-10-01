package com.ehsan.snws;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebService extends Node implements Comparable<WebService>{		

	public double su;
	public double rt;
	public int cost;
	public int type;
	public double QoS;
	public double OutPut;
	public double InPut;
	public double PreCon;
	public double Effect;
	public int compCount;
	public int reqSubCount;


	public final class Pair {
		private final User user;
		private final int serviceType;
		public Pair(User user, int serviceType) { this.user = user; this.serviceType = serviceType;}
		
		@Override  
	    public int hashCode() {  
	        int hash = 1;  
	        hash = hash * 17 + serviceType;  
	        hash = hash * 31 + user.id;  
	        return hash;  
	    }  
		
		@Override
		public boolean equals(Object other){
			if (other == null) return false;
			if (other == this) return true;
			if (!(other instanceof Pair)) return false;
			Pair otherNode = (Pair)other;
			return (otherNode.serviceType == this.serviceType && otherNode.user.id == this.user.id);
		}
		
	}
	public HashMap<Pair, Integer> historyOfUsersAskingForService = new HashMap<Pair, Integer>();

	public WebService(int argName, int type) {
		super(argName);
		this.type = type;
		su = Math.random();
		rt = Math.random();
		cost = (int) Math.random()*100;
		QoS = Math.random();
		PreCon = Math.random();
		OutPut = Math.random();
		InPut = Math.random();
		Effect = Math.random();
	}

	@Override
	public String toString () {
		String str = "WS Id: " + id + ", type: " + type + ", su: " + su + ", rt: " + rt + ", cost: " + cost + " , QoS: " + QoS + ", compCount: " + compCount + ", reqSubCount: " + reqSubCount;
		return str;
	}

	@Override
	public int compareTo(WebService other) {
		return Double.compare(this.QoS, other.QoS);
	}

	public void addUserToHistory (User user, int serviceType) { 
		Pair pair = new Pair (user, serviceType);
		if (historyOfUsersAskingForService.get(pair) != null)
			historyOfUsersAskingForService.put(pair, historyOfUsersAskingForService.get(pair) + 1); // Increment Count
		else 
			historyOfUsersAskingForService.put(pair,1);
	}

	public List<User> getAllUsersHavingIntractionMoreThanValue (int value, int serviceType) {
		List<User> users = new ArrayList<User>();
		for (Pair pair: historyOfUsersAskingForService.keySet()) {
			if (pair.serviceType == serviceType) {
				if (historyOfUsersAskingForService.get(pair) >= value) {
					users.add(pair.user);
				}
			}
		}
		return users;
	}
	
	public void printHistoryOfUsersService (PrintWriter output) {
		for (Pair pair: historyOfUsersAskingForService.keySet()) {
			System.out.println("\tHistory: UserID: " + pair.user.id + " asked for serviceType: " + pair.serviceType + ", #" + historyOfUsersAskingForService.get(pair) + " times");
			output.println("\tHistory: UserID: " + pair.user.id + " asked for serviceType: " + pair.serviceType + ", #" + historyOfUsersAskingForService.get(pair) + " times");
		}
	}

}
