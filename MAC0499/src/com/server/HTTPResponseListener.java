/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.server;

import org.json.JSONObject;

public interface HTTPResponseListener {
	public void onResponse(JSONObject json); 
}
