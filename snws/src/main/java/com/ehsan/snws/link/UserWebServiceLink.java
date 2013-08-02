package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;

public class UserWebServiceLink extends Edge{

	public double dtr;
	public double itr;
	public double fLevel;
	public double satis;
	
	public UserWebServiceLink(Node argFrom, Node argTo, int argWeight) {
		super(argFrom, argTo, argWeight);
	}
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dtr: " + dtr + ", itr: " + itr + ", fLevel: " + fLevel + ", satis: " + satis;
		return str;
	}

}
