package com.ehsan.snws.util;

import com.ehsan.snws.User;
import com.ehsan.snws.WebService;
import com.ehsan.snws.Graph;
import com.ehsan.snws.link.*;

public class Functions {
	
	public static double degreeC(WebService ws1,WebService ws2){
		if( ws1.type != ws2.type){
	    double x1;
		double x2;
		x1= ws2.Effect - ws2.PreCon;
		x2= Math.abs(x1);
		double dc= ((x2))/2;
	    return dc;
	 
	    
	    }
		else{ double dc=0; 
			return dc;}
	}
	
	public static double degreeS(WebService ws1,WebService ws2){
		if( ws1.type == ws2.type){
		  double ds= ((ws1.Effect - ws2.Effect) + (ws1.OutPut - ws2.OutPut) + (ws1.QoS - ws2.QoS) + (ws1.PreCon - ws2.PreCon) +(ws1.InPut - ws2.InPut))/5;
		  return Math.abs(ds);
	}else{double ds=0;
	return ds;
	}
	}
	
/*	public static int popular(User u1, User u2){
		Graph network= new Graph();
			int u1Edges = network.getAllLinksToOtherUsers(u1).size();
			int u2Edges = network.getAllLinksToOtherUsers(u1).size();
			
			if(u1Edges > u2Eges)
			{
			
			return wsEdgeCount;
			}*/

}