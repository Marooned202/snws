package com.ehsan.snws;

import java.util.Comparator;

public class WebServiceQoSComparator implements Comparator<WebService> {
	
	@Override
    public int compare(WebService o1, WebService o2) {
		return Double.compare(o1.QoS, o2.QoS) * (-1);  // Times -1 to make sort from big to small
    }
	
}
