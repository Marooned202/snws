package com.ehsan.snws.link;

import sun.net.NetworkClient;

import com.ehsan.snws.DegreeS;
import com.ehsan.snws.DegreeC;
import com.ehsan.snws.Edge;
import com.ehsan.snws.Graph;
import com.ehsan.snws.Node;
import com.ehsan.snws.WebService;;

public class WebServiceWebServiceLink extends Edge{

	
	public DegreeC dc = new DegreeC();
	public DegreeS ds = new DegreeS();
	
	//public int wsiCompWsj;
	//public int wsiSubWsj;
	//public int wsjReqSubWsi;
	
	public WebServiceWebServiceLink(Node argFrom, Node argTo) {
		super(argFrom, argTo);
	}
	 
	
	
	@Override
	public String toString () {
		String str = "From Id: " + from.id + ", To Id: " + to.id + ", weight: " + weight + ", dc: " + dc + ", ds: " + ds + ", interactionCount: " + interactionCount +",wsiCompWsj:" + wsiCompWsj;
		return str;
	}

}
