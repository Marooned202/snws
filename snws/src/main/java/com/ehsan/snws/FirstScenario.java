package com.ehsan.snws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ehsan.snws.link.UserUserLink;
import com.ehsan.snws.link.UserWebServiceLink;

public class FirstScenario implements Scenario {

	Graph network = new Graph();

	public final static int INTERATION_NUM = 100;
	public final static int USER_NUM = 5;
	public final static int WEB_SERVICE_NUM = 5;
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

				List<Integer> servicesNeeded = getRandomServicesNeeded();						

				String tmp = "";
				for (Integer ser : servicesNeeded) {
					tmp = tmp + ", " + ser;
				}

				System.out.printf("User %d is looking for services %s\n", user.id, tmp);
				output.printf("User %d is looking for services %s\n", user.id, tmp);

			}

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
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

