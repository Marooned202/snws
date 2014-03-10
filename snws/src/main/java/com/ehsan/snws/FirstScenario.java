package com.ehsan.snws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ehsan.snws.link.UserUserLink;
import com.ehsan.snws.link.UserWebServiceLink;
import com.ehsan.snws.link.WebServiceUserLink;
import com.ehsan.snws.link.WebServiceWebServiceLink;
import com.ehsan.snws.util.Functions;

public class FirstScenario implements Scenario {

	Graph network = new Graph();


	public final static int INTERATION_NUM =100;
	public final static int USER_NUM =15;
	public final static int WEB_SERVICE_NUM =15;


	public final static int WEB_SERVICE_TYPES = 2;
	public final static int NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED = 3;

	public final static int INITIAL_USER_WEBSERVICE_CONNECTION_CHANCE = 30;
	public final static int INITIAL_WEBSERVICE_WEBSERVICE_CONNECTION_CHANCE = 30;

	//initialWsWsEdgeNo
	public static float x=0;
	//initialWsUserEdgeNo
	public static float initialWsUserEdgeNo=0;
	public static float WsUserEdgeNo =0;
	//initialUserUserEdgeNo
	public static float initialUserUserEdgeNo=0;
	public static float UserUserEdgeNo =0;
	//newWsWsEdgeNo
	public static float xx=0;
	public static float xxx=0;
	//to switch off the user social network
	public boolean social= true;
	//how many ws for each user
	// public static int wsEdgeCount;

	@Override
	public void run() {

		File file = new File("output-"+new java.util.Date().getTime()+".txt");  
		FileWriter writer;

		File filebefore = new File("c:\\ex\\sc1.txt");  
		FileWriter writerbefore;
		File fileafter = new File("c:\\ex\\sc2.txt");  
		FileWriter writerafter;

		try {
			writer = new FileWriter(file, true);

			writerafter = new FileWriter(fileafter, true);
			writerbefore = new FileWriter(filebefore, true);

			PrintWriter output = new PrintWriter(writer);  		 

			PrintWriter outputafter = new PrintWriter(writerafter);  
			PrintWriter outputbefore = new PrintWriter(writerbefore);

			initializeNodes();
			initializeConnections();

			//network.addEdge(source, target, weight);

			network.print(output);
			//network.printDocFormat(outputbefore);
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
			//network.printDocFormat(outputafter);

			output.close();
			outputbefore.close();
			outputafter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	private void performCompositeService(List<Integer> serviceTypes, User user, PrintWriter output) {

		List<WebService> bestWebServices = new ArrayList<WebService>();						

		for (Integer serviceType: serviceTypes) {
			boolean found = false;
			ArrayList <WebService> webServices = network.getAllWebServicesUserServiceType (user, serviceType);
			if (!webServices.isEmpty()) { // Found a web service proding service "serviceType"
				Collections.sort(webServices, new WebServiceQoSComparator()); // Choosing the one with highest QoS

				found = true;

				WebService bestWebservice = webServices.get(0);

				System.out.println("Found WS1: " + bestWebservice );
				output.println("Found WS1: " + bestWebservice );

				bestWebServices.add(bestWebservice);

				// Connect User to Web service and vise versa
				UserWebServiceLink uwsLink = new UserWebServiceLink(user, bestWebservice);
				// Add properties to link if needed here
				//wsEdgeCount++;
				// ...
				// ...
				network.addUniqueEdge(uwsLink);

				//wsEdgeCount++;

				WsUserEdgeNo++;



				WebServiceUserLink wsuLink = new WebServiceUserLink(bestWebservice, user);
				// Add properties to link if needed here
				// wsEdgeCount++;
				// ...
				// ...
				network.addUniqueEdge(wsuLink);			


				// Increase Interaction count of the link bet user and webservice and the link between webservice and user
				Edge link = network.getLinkBetweenTwoNodes(user, bestWebservice);
				link.interactionCount++;
				Edge link2 = network.getLinkBetweenTwoNodes(bestWebservice, user);
				link2.interactionCount++;


				//compute the weight between web services and user


				// Add the user history in webservice, that this webservice has done service type for user
				bestWebservice.addUserToHistory(user, serviceType);


				if(social)
				{   // Now check for all users who asked for this service more than N times, and connect them together
					List<User> users = bestWebservice.getAllUsersHavingIntractionMoreThanValue(NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED, serviceType);
					for (User u: users) {
						if (u.id != user.id) {
							UserUserLink uuLink = new UserUserLink(u, user);
							network.addUniqueEdge(uuLink);
							u.interactionCount++;
							uuLink.interactionCount++;
							UserUserLink uuLink2 = new UserUserLink(user, u);
							network.addUniqueEdge(uuLink2);
							user.interactionCount++;
							uuLink2.interactionCount++;
						}
					}
				}
			} 
			if (!found) { // if no webservice arround the user can do that job
				found = recommendWebserviceProvidngServiceType (user, serviceType, output); // Webservices around User will try to find web services proving the required service type
				/*if (!found) {

						Random rnd= new Random();
						int size = network.getAllWebServicesOfType(serviceType).size();
						WebService ws = (WebService) network.getAllWebServicesOfType(serviceType).get(rnd.nextInt(size));

						System.out.println("Found WS*: " + ws);
						output.println("Found WS*: " + ws );

						// Connect User to Webservice and vise versa
						UserWebServiceLink uwsLink = new UserWebServiceLink(user, ws);
						network.addUniqueEdge(uwsLink);
						WsUserEdgeNo++;

						WebServiceUserLink wsuLink = new WebServiceUserLink(ws, user);
						network.addUniqueEdge(wsuLink);	
						//WsUserEdgeNo++;


						//add to history
						ws.addUserToHistory(user, serviceType);
						//add to list of best web services
						bestWebServices.add(ws);
				}*/
			}
		}
		//linedws


		if (!social){
			for (WebService ws1: bestWebServices) {
				for (WebService ws2: bestWebServices) {
					if (Functions.degreeC(ws1,ws2) > 0.6){

						if (ws1.equals(ws2)) continue; 

						// We do not link webservices to same webservices
						WebServiceWebServiceLink wswsLink = new WebServiceWebServiceLink(ws1, ws2);

						// Add properties to link if needed here

						network.addUniqueEdge(wswsLink);

						xx++;
						// Other way link
						WebServiceWebServiceLink wswsLink2 = new WebServiceWebServiceLink(ws1, ws2);
						// Add properties to link if needed here
						// ...
						// ...
						// ...
						network.addUniqueEdge(wswsLink2);
						xx++;
					}
					else if(Functions.degreeS(ws1, ws2) >0.6){
						if (ws1.equals(ws2)) continue; 

						// We do not link webservices to same webservices
						WebServiceWebServiceLink wswsLink = new WebServiceWebServiceLink(ws1, ws2);

						// Add properties to link if needed here

						network.addUniqueEdge(wswsLink);

						xx++;
						// Other way link
						WebServiceWebServiceLink wswsLink2 = new WebServiceWebServiceLink(ws1, ws2);
						// Add properties to link if needed here
						// ...
						// ...
						// ...
						network.addUniqueEdge(wswsLink2);
						xx++;
					}
				}
			}
		}


		// Connecting bestWebservices together (the webservice who did the job)
		if(social){
			for (WebService webService1: bestWebServices) {
				for (WebService webService2: bestWebServices) {

					if (webService1.equals(webService2)) continue; 

					// We do not link webservices to same webservices

					WebServiceWebServiceLink wswsLink = new WebServiceWebServiceLink(webService1, webService2);

					// Add properties to link if needed here
					// ...
					// ...
					// ...
					network.addUniqueEdge(wswsLink);

					xx++;
					// Other way link
					WebServiceWebServiceLink wswsLink2 = new WebServiceWebServiceLink(webService2, webService1);
					// Add properties to link if needed here
					// ...
					// ...
					// ...
					network.addUniqueEdge(wswsLink2);

					xx++;


					//increase wsicompwsj
					if(!(webService1.type == webService2.type))
					{
						Edge linkWs1= network.getLinkBetweenTwoNodes(webService1, webService2);
						linkWs1.wsiCompWsj++;
						Edge linkws4 = network.getLinkBetweenTwoNodes(webService2, webService1);
						linkws4.wsiCompWsj++;

					}else{
						Edge linkWs1= network.getLinkBetweenTwoNodes(webService1, webService2);
						linkWs1.wsiSubWsj++;
						Edge linkws4 = network.getLinkBetweenTwoNodes(webService2, webService1);
						linkws4.wsiSubWsj++;

					}

				}

			}
		}



	}

	private boolean recommendWebserviceProvidngServiceType(User user, Integer serviceType, PrintWriter output) {

		boolean found = false;

		//List<WebService> WsList= new ArrayList<WebService>();

		for (WebService webService : network.getAllConnectedWebServices(user))	{
			for (WebService webService2 : network.getAllConnectedWebServicesOfType(webService, serviceType)) {

				found = true;
				List<WebService> WsList = network.getAllConnectedWebServicesOfType(webService2, serviceType);

				System.out.printf("Recommending (Connecting) webservice %d for user %d, which was looking for service type %d\n", webService2.id, user.id, serviceType );
				output.printf("Recommending (Connecting) webservice %d for user %d, which was looking for service type %d\n", webService2.id, user.id, serviceType);

				WsList.add(webService2);



				// chose the best one
				Collections.sort(WsList , new WebServiceQoSComparator());
				WebService ws= WsList.get(0);

				// Connect User to Webservice and vise versa
				UserWebServiceLink uwsLink = new UserWebServiceLink(user, ws);
				// Add properties to link if needed here
				// ...
				// ...
				// ...
				network.addUniqueEdge(uwsLink);
				//3
				UserWebServiceLink.y++;
				WsUserEdgeNo++;

				WebServiceUserLink wsuLink = new WebServiceUserLink(ws, user);
				// Add properties to link if needed here
				// ...
				// ...
				// ...

				network.addUniqueEdge(wsuLink);	
				//WsUserEdgeNo++;



				//compute the weight between web services and user

				// add to the history 
				ws.addUserToHistory(user, serviceType);

			}
		}
		return found;

	}

	private void performSingleService(Integer serviceType, User user, PrintWriter output) {

		boolean found = false;
		ArrayList <WebService> webServices = network.getAllWebServicesUserServiceType (user, serviceType);
		if (!webServices.isEmpty()) { // Found a web service providing service "serviceType"
			found= true;

			Collections.sort(webServices, new WebServiceQoSComparator()); // Choosing the one with highest QoS


			WebService bestWebservice = webServices.get(0);

			System.out.println("Found WS: " + bestWebservice);
			output.println("Found WS: " + bestWebservice);

			// Connect User to Webservice and vise versa (if it exists will not be added, we are using addUniqueEdge)
			UserWebServiceLink uwsLink = new UserWebServiceLink(user, bestWebservice);
			// Add properties to link if needed here
			// ...
			// ...
			// ...1
			network.addUniqueEdge(uwsLink);

			WsUserEdgeNo++;

			WebServiceUserLink wsuLink = new WebServiceUserLink(bestWebservice, user);
			// Add properties to link if needed here
			// ...
			// ...
			// ...
			network.addUniqueEdge(wsuLink);	
			//WsUserEdgeNo++;

			// Increase Interaction count of the link bet user and webservice and the link between webservice and user
			Edge link = network.getLinkBetweenTwoNodes(user, bestWebservice);
			link.interactionCount++;
			Edge link2 = network.getLinkBetweenTwoNodes(bestWebservice, user);
			link2.interactionCount++;

			// Add the user history in webservice, that this webservice has done service type for user
			bestWebservice.addUserToHistory(user, serviceType);

			if(social)
			{ 
				// Now check for all users who asked for this service more than N times, and connect them together
				List<User> users = bestWebservice.getAllUsersHavingIntractionMoreThanValue(NUMBER_OF_SERVICES_PERFORMED_TO_GET_INTRODUCED, serviceType);
				for (User u: users) {
					if (u.id != user.id) {

						UserUserLink uuLink = new UserUserLink(u, user);
						network.addUniqueEdge(uuLink);
						u.interactionCount++;
						uuLink.interactionCount++;
						UserUserLink uuLink2 = new UserUserLink(user, u);
						network.addUniqueEdge(uuLink2);
						user.interactionCount++;
						uuLink2.interactionCount++;
					}
				}
			}
		} else { // if no webservice arround the user can do that job
			if(!found){
				recommendWebserviceProvidngServiceType (user, serviceType, output); // Webservices around User will try to find web services proving the required service type

				/*if (!found){
				// Random rnd= new Random();
				//int size = network.getAllWebServicesOfType(serviceType).size();
				//	Node ws = (WebService) network.getAllWebServicesOfType(serviceType).get(rnd.nextInt(size));

				List<WebService>   WSList = network.getAllWebServicesOfType(serviceType);
				Collections.sort(WSList, new WebServiceQoSComparator());
				WebService ws= (WebService) WSList.get(0);

				 System.out.println("Found WS*: " + ws);
				 output.println("Found WS*: " + ws);

					// Connect User to Webservice and vise versa
				 UserWebServiceLink uwsLink = new UserWebServiceLink(user, ws);
				 network.addUniqueEdge(uwsLink);
				 WsUserEdgeNo++;

				 WebServiceUserLink wsuLink = new WebServiceUserLink(ws, user);
				 network.addUniqueEdge(wsuLink);	
				 //WsUserEdgeNo++;

				 //add to history
				 ws.addUserToHistory(user, serviceType);

			 }*/

			}
		}
	}



	private List<Integer> getRandomServicesNeeded() {

		ArrayList<Integer> services = new ArrayList<Integer>();
		Random rnd = new Random();

		for (int i = 0; i < WEB_SERVICE_TYPES; i++)
		{
			if (rnd.nextDouble() > 0.1) { // 50% chance of needing service i
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

		int counter=0;
		Random rnd = new Random();
		for (Node node: network.getAllUsers()) {
			ArrayList<Node> allNodesButNode = (ArrayList<Node>)network.getAllUsers();
			allNodesButNode.remove(node);
			if(social)
			{ 
				initialUserUserEdgeNo++;
				UserUserLink uuLink = new UserUserLink(node, allNodesButNode.get(rnd.nextInt(allNodesButNode.size())));
				network.addUniqueEdge(uuLink);	
				UserUserLink uuLink2 = new UserUserLink(uuLink.to, uuLink.from);
				network.addUniqueEdge(uuLink2);

				uuLink.interactionCount++;
				uuLink2.interactionCount++;
				node.interactionCount++;
				counter= network.getAllLinksToOtherUsers(node).size();

			}

			int p = rnd.nextInt(100);
			if (p > INITIAL_USER_WEBSERVICE_CONNECTION_CHANCE) { // more than 100%-30% chance (p is from 0 to 9)

				UserWebServiceLink uwsLink = new UserWebServiceLink(node, network.getAllWebServices().get(rnd.nextInt(network.getAllWebServices().size())));
				network.addUniqueEdge(uwsLink);
				WebServiceUserLink wsuLink = new WebServiceUserLink(uwsLink.to, uwsLink.from);
				network.addUniqueEdge(wsuLink);
				initialWsUserEdgeNo++;
			}

		}

		for (Node node: network.getAllWebServices()) {
			int p = rnd.nextInt(100);
			if (p > INITIAL_WEBSERVICE_WEBSERVICE_CONNECTION_CHANCE) { // more than 100%-30% chance (p is from 0 to 9)

				WebServiceWebServiceLink wswsLink = new WebServiceWebServiceLink(node, network.getAllWebServices().get(rnd.nextInt(network.getAllWebServices().size())));
				network.addUniqueEdge(wswsLink);

				WebServiceWebServiceLink wswsLink2 = new WebServiceWebServiceLink(wswsLink.to, wswsLink.from);
				network.addUniqueEdge(wswsLink2);
				wswsLink2.edgeCountws++; 
				xxx++;
			}

		}

	}
}

