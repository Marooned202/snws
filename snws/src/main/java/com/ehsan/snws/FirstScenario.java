package com.ehsan.snws;

import java.util.Random;

public class FirstScenario implements Scenario {
	
	Graph network = new Graph();
	
	@Override
	public void run() {
		
		initializeNodes();
		initializeConnections();
		
		
		//network.addEdge(source, target, weight);
		
		network.print();
		
	}
	
	public void initializeNodes() {
		
		Random rnd = new Random();
		
		for (int i = 0; i < 10; i++) {
			User user = new User(Util.serial++);	
			network.addNode(user);
		}
		
		for (int i = 0; i < 10; i++) {
			WebService webService = new WebService(Util.serial++, rnd.nextInt(3));	
			network.addNode(webService);
		}		
	}
	
	public void initializeConnections() {
		
		Random rnd = new Random();
		
		for (int i = 0; i < 10; i++) {
			User user = new User(Util.serial++);	
			network.addNode(user);
		}
		
		for (int i = 0; i < 10; i++) {
			WebService webService = new WebService(Util.serial++, rnd.nextInt(3));	
			network.addNode(webService);
		}		
	}
}
