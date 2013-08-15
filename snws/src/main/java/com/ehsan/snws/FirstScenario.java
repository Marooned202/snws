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
import com.ehsan.snws.link.WebServiceWebServiceLink;

public class FirstScenario implements Scenario {

	Graph network = new Graph();

	public final static int INTERATION_NUM = 50;
	public final static int USER_NUM = 20;
	public final static int WEB_SERVICE_NUM = 20;
	public final static int WEB_SERVICE_TYPES = 2;
	public final static int NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED = 3;

	@Override
	public void run() {

		File file = new File("output-"+new java.util.Date().getTime()+".txt");  
		FileWriter writer;

		try {
			writer = new FileWriter(file, true);

			PrintWriter output = new PrintWriter(writer);  		 

			initializeNodes();
			initializeConnections();

			//network.addEdge(source, target, weight);

			network.print(output);
			Random rnd = new Random();

			for (int i = 0; i < INTERATION_NUM; i++) {		

				System.out.println("Iteration #" + i);
				output.println("Iteration #" + i);

				User user = network.getRandomUser();			

				List<Integer> serviceTypes = getRandomServicesNeeded();						

				String tmp = "";
				for (int service : serviceTypes) {
					tmp = tmp + ", " + service;
				}

				System.out.printf("User %d is looking for services %s\n", user.id, tmp);
				output.printf("User %d is looking for services %s\n", user.id, tmp);

				if (serviceTypes.size() == 1) {
					performSingleService (serviceTypes.get(0), user, output);
				} else {
					performCompositeService (serviceTypes, user, output);
				}
			}

			network.print(output);

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	private void performCompositeService(List<Integer> serviceTypes, User user, PrintWriter output) {

		List<WebService> bestWebServices = new ArrayList<WebService>();						
		
		for (Integer serviceType: serviceTypes) {
			
			ArrayList <WebService> webServices = network.getAllWebServicesUserServiceType (user, serviceType);
			if (!webServices.isEmpty()) { // Found a web service proding service "serviceType"
				Collections.sort(webServices, new WebServiceQoSComparator()); // Choosing the one with highest QoS

				WebService bestWebservice = webServices.get(0);

				System.out.println("Found WS: " + bestWebservice);
				output.println("Found WS: " + bestWebservice);
				
				bestWebServices.add(bestWebservice);

				// Connect User to Webservice and vise versa
				UserWebServiceLink uwsLink = new UserWebServiceLink(user, bestWebservice);
				// Add properties to link if needed here
				// ...
				// ...
				// ...
				network.addUniqueEdge(uwsLink);

				WebServiceUserLink wsuLink = new WebServiceUserLink(bestWebservice, user);
				// Add properties to link if needed here
				// ...
				// ...
				// ...
				network.addUniqueEdge(wsuLink);			
				
				// Increase Interaction count of the link bet user and webservice and the link between webservice and user
				Edge link = network.getLinkBetweenTwoNodes(user, bestWebservice);
				link.interactionCount++;
				Edge link2 = network.getLinkBetweenTwoNodes(bestWebservice, user);
				link2.interactionCount++;
				
				// Add the user history in webservice, that this webservice has done service type for user
				bestWebservice.addUserToHistory(user, serviceType);
				
				// Now check for all users who asked for this service more than N times, and connect them together
				List<User> users = bestWebservice.getAllUsersHavingIntractionMoreThanValue(NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED, serviceType);
				for (User u: users) {
					if (u.id != user.id) {
						UserUserLink uuLink = new UserUserLink(u, user);
						network.addUniqueEdge(uuLink);	
						UserUserLink uuLink2 = new UserUserLink(user, u);
						network.addUniqueEdge(uuLink2);
					}
				}
				
			}										
		}
		
		// Connecting bestWebservices together (the webservice who did the job)
		for (WebService webService1: bestWebServices) {
			for (WebService webService2: bestWebServices) {
				if (webService1.equals(webService2)) continue; // We do not link webservices to same webservices
				WebServiceWebServiceLink wswsLink = new WebServiceWebServiceLink(webService1, webService2);
				// Add properties to link if needed here
				// ...
				// ...
				// ...
				network.addUniqueEdge(wswsLink);
				
				// Other way link
				WebServiceWebServiceLink wswsLink2 = new WebServiceWebServiceLink(webService2, webService1);
				// Add properties to link if needed here
				// ...
				// ...
				// ...
				network.addUniqueEdge(wswsLink2);
			}						
		}

	}

	private void performSingleService(Integer serviceType, User user, PrintWriter output) {

		ArrayList <WebService> webServices = network.getAllWebServicesUserServiceType (user, serviceType);
		if (!webServices.isEmpty()) { // Found a web service proding service "serviceType"
			Collections.sort(webServices, new WebServiceQoSComparator()); // Choosing the one with highest QoS

			WebService bestWebservice = webServices.get(0);

			System.out.println("Found WS: " + bestWebservice);
			output.println("Found WS: " + bestWebservice);

			// Connect User to Webservice and vise versa (if it exists will not be added, we are using addUniqueEdge)
			UserWebServiceLink uwsLink = new UserWebServiceLink(user, bestWebservice);
			// Add properties to link if needed here
			// ...
			// ...
			// ...
			network.addUniqueEdge(uwsLink);

			WebServiceUserLink wsuLink = new WebServiceUserLink(bestWebservice, user);
			// Add properties to link if needed here
			// ...
			// ...
			// ...
			network.addUniqueEdge(wsuLink);	
			
			// Increase Interaction count of the link bet user and webservice and the link between webservice and user
			Edge link = network.getLinkBetweenTwoNodes(user, bestWebservice);
			link.interactionCount++;
			Edge link2 = network.getLinkBetweenTwoNodes(bestWebservice, user);
			link2.interactionCount++;
			
			// Add the user history in webservice, that this webservice has done service type for user
			bestWebservice.addUserToHistory(user, serviceType);
			
			// Now check for all users who asked for this service more than N times, and connect them together
			List<User> users = bestWebservice.getAllUsersHavingIntractionMoreThanValue(NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED, serviceType);
			for (User u: users) {
				if (u.id != user.id) {
					UserUserLink uuLink = new UserUserLink(u, user);
					network.addUniqueEdge(uuLink);	
					UserUserLink uuLink2 = new UserUserLink(user, u);
					network.addUniqueEdge(uuLink2);
				}
			}
			
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

			ArrayList<Node> allNodesButNode = (ArrayList<Node>)network.getAllUsers();
			allNodesButNode.remove(node);

			UserUserLink uuLink = new UserUserLink(node, allNodesButNode.get(rnd.nextInt(allNodesButNode.size())));
			network.addUniqueEdge(uuLink);	
			UserUserLink uuLink2 = new UserUserLink(uuLink.to, uuLink.from);
			network.addUniqueEdge(uuLink2);

			int p = rnd.nextInt(10);
			if (p > 3) { // more than 100%-30% chance (p is from 0 to 9)
				UserWebServiceLink uwsLink = new UserWebServiceLink(node, network.getAllWebServices().get(rnd.nextInt(network.getAllWebServices().size())));
				network.addUniqueEdge(uwsLink);
				WebServiceUserLink wsuLink = new WebServiceUserLink(uwsLink.to, uwsLink.from);
				network.addUniqueEdge(wsuLink);
			}

		}

	}
}

