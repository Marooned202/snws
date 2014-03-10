package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Graph;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;
import com.ehsan.snws.WebService;

public class UserWebServiceLink extends Edge{
	Graph network = new Graph();
	public double dtr;
	public double itr;
	public static int y=0;
	
	public UserWebServiceLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	 
	//compute the weight between web services and user
	//Edge weight= network.getLinkBetweenTwoNodes(user, webService2);
	//weight.weight= (user.reqResponsetime * webService2.su) / (user.reqSuccessability*webService2.su);
		 
	 
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dtr: " + dtr + ", itr: " + itr + ", interactionCount: " + interactionCount;
		return str;
	}

}
