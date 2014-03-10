package com.ehsan.snws.link;

import sun.net.NetworkClient;


import com.ehsan.snws.Edge;
import com.ehsan.snws.Graph;
import com.ehsan.snws.Node;
import com.ehsan.snws.WebService;
import com.ehsan.snws.util.Functions;

public class WebServiceWebServiceLink extends Edge{
	
	//public int wsiCompWsj;
	//public int wsiSubWsj;
	//public int wsjReqSubWsi;
	
	public WebServiceWebServiceLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	 
	
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dc: " + Functions.degreeC((WebService)from, (WebService)to) + ", ds: " + Functions.degreeS((WebService)from, (WebService) to) + ", interactionCount: " + interactionCount +",wsiCompWsj:" + wsiCompWsj ;
		return str;
	}

}
