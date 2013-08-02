package com.ehsan.snws.link;

import com.ehsan.snws.Edge;
import com.ehsan.snws.Node;
import com.ehsan.snws.User;

public class UserUserLink extends Edge{

	public float dtr;
	public float atr;
	public float fLevel;
	
	public UserUserLink(Node argFrom, Node argTo, int argWeight) {
		super(argFrom, argTo, argWeight);
	}

}
