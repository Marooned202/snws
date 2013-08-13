package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;

public class UserWebServiceLink extends Edge{

	public double dtr;
	public double itr;
	
	
	public UserWebServiceLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dtr: " + dtr + ", itr: " + itr + ", interactionCount: " + interactionCount;
		return str;
	}

}
