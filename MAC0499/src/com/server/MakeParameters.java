/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.server;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeParameters {
	//Depois pensar melhor em como tirar esse static
	public static JSONObject makeTestparams () {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject(); 
		try {
			params.put("chaveUM", 12); 
			params.put("chaveDois", "Cocozinho");
			result.put("message", params); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		return result; 
	}
}
