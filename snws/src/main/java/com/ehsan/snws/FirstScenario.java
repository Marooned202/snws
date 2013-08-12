package com.ehsan.snws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ehsan.snws.link.UserUserLink;
import com.ehsan.snws.link.UserWebServiceLink;
import com.ehsan.snws.link.WebServiceUserLink;

public class FirstScenario implements Scenario {

	Graph network = new Graph();

	public final static int INTERATION_NUM = 5;
	public final static int USER_NUM = 2;
	public final static int WEB_SERVICE_NUM = 2;
	public final static int WEB_SERVICE_TYPES = 2;

	@Override
	public void run() {

		File file = new File("output.txt");  
		FileWriter writer;
		
		try {
			writer = new FileWriter(file, true);

			PrintWriter output = new PrintWriter(writer);  		 

			initializeNodes();
			initializeConnections();

			//network.addEdge(source, target, weight);

			network.print();
			Random rnd = new Random();

			for (int i = 0; i < INTERATION_NUM; i++) {		

				System.out.println("#" + i);
				output.println("#" + i);

				User user = network.getRandomUser();			

				List<Integer> serviceTypes = getRandomServicesNeeded();						

				String tmp = "";
				for (int service : serviceTypes) {
					tmp = tmp + ", " + service;
				}

				System.out.printf("User %d is looking for services %s\n", user.id, tmp);
				output.printf("User %d is looking for services %s\n", user.id, tmp);
				
				if (serviceTypes.size() == 1) {
					performSingleService (serviceTypes.get(0), user);
				} else {
					performCompositeService (serviceTypes, user);
				}
				
				for (int service: serviceTypes) {
					
				}

			}
			
			network.print();

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	private void performCompositeService(List<Integer> serviceTypes, User user) {
		
		
	}

	private void performSingleService(Integer serviceType, User user) {
		 
		ArrayList <WebService> webServices = network.getAllWebServicesUserServiceType (user, serviceType);
		Collections.sort(webServices);
		
	}


	private List<Integer> getRandomServicesNeeded() {

		ArrayList<Integer> services = new ArrayList<Integer>();
		Random rnd = new Random();

		for (int i = 0; i < WEB_SERVICE_TYPES; i++)
		{
			if (rnd.nextDouble() > 0.5) { // 50% chance of needing service i
				services.add(i);
			}					
		}

		if (services.isEmpty()) {
			services.add(rnd.nextInt(WEB_SERVICE_TYPES));
		}

		return services;
	}

	public void initializeNodes() {

		Random rnd = new Random();

		for (int i = 0; i < USER_NUM; i++) {
			User user = new User(Util.serial++);	
			network.addNode(user);
		}

		for (int i = 0; i < WEB_SERVICE_NUM; i++) {
			WebService webService = new WebService(Util.serial++, rnd.nextInt(WEB_SERVICE_TYPES));	
			network.addNode(webService);
		}		

	}

	public void initializeConnections() {

		Random rnd = new Random();

		for (Node node: network.getAllUsers()) {
			
			ArrayList<Node> allNodesButNode = (ArrayList<Node>)network.getAllUsers();
			allNodesButNode.remove(node);
			
			UserUserLink uuLink = new UserUserLink(node, allNodesButNode.get(rnd.nextInt(allNodesButNode.size())));
			network.addUniqueEdge(uuLink);	
			UserUserLink uuLink2 = new UserUserLink(uuLink.to, uuLink.from);
			network.addUniqueEdge(uuLink2);

			int p = rnd.nextInt(10);
			if (p > 3) {
				UserWebServiceLink uwcLink = new UserWebServiceLink(node, network.getAllWebServices().get(rnd.nextInt(network.getAllWebServices().size())));
				network.addUniqueEdge(uwcLink);
				WebServiceUserLink wcuLink = new WebServiceUserLink(uwcLink.to, uwcLink.from);
				network.addUniqueEdge(wcuLink);
			}

		}

	}
}

