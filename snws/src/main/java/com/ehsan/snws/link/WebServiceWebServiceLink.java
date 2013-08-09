package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;

public class WebServiceWebServiceLink extends Edge{

	public double dc;
	public double ds;
	
	public int wsiCompWsj;
	public int wsiSubWsj;
	public int wsjReqSubWsi;
	
	public WebServiceWebServiceLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dc: " + dc + ", ds: " + ds;
		return str;
	}

}
