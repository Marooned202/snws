package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;

public class UserUserLink extends Edge{

    
	public int useriWsUserj;
	
	public UserUserLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ",useriWsUserj: "+ useriWsUserj + ", interactionCount: " + interactionCount;
		return str;
	}

}
