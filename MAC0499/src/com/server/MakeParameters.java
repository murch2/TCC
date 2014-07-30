/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.server;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.model.GraphUser;
import com.managers.GameManager;

//Tentar tirar os static depois 
public class MakeParameters {

	public static JSONObject signUpParams (GraphUser user) {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject(); 
		try {
			params.put("requestID", "SignUp"); 
			params.put("userID", user.getId()); 
			params.put("userName", user.getName());
			params.put("userCoins", 0); 
			params.put("userPowerUps", 0); 
			result.put("message", params); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return result; 
	}
	
	public static JSONObject getUserInfo(String userID) {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject();
		try {
			params.put("requestID", "UserInfo"); 
			params.put("userID", userID); 
			result.put("message", params); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return result; 
	}
	
}
