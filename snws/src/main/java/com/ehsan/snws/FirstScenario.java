package com.ehsan.snws;

import java.util.List;
import java.util.Random;

import com.ehsan.snws.link.UserUserLink;
import com.ehsan.snws.link.UserWebServiceLink;

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
		
		for (int i = 0; i < 5; i++) {
			User user = new User(Util.serial++);	
			network.addNode(user);
		}
		
		for (int i = 0; i < 5; i++) {
			WebService webService = new WebService(Util.serial++, rnd.nextInt(2));	
			network.addNode(webService);
		}		
	}
	
	public void initializeConnections() {
		
		Random rnd = new Random();
		
		for (Node node: network.getAllUsers()) {
			UserUserLink uuLink = new UserUserLink(node, network.getAllUsers().get(rnd.nextInt(network.getAllUsers().size())));
			network.addEdge(uuLink);	
			
			int p = rnd.nextInt(10);
			if (p > 3) {
				UserWebServiceLink uwcLink = new UserWebServiceLink(node, network.getAllWebServices().get(rnd.nextInt(network.getAllWebServices().size())));
				network.addEdge(uwcLink);
			}
					
		}
		
		
		
			
	}
}
