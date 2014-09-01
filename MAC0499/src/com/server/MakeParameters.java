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
	
//	Acho que talvez não precise do parametro e eu possa pegar do gameManager
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
	
	public static JSONObject newGame() {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject();
		try {
			params.put("requestID", "NewGame"); 
			params.put("userID", GameManager.getInstance().getUserID()); 
			params.put("friendID", GameManager.getInstance().getFriendID()); 
			result.put("message", params);
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		return result; 
	}
	
	public static JSONObject randomCard() {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject();
		try {
			params.put("requestID", "RandomCard"); 
			params.put("userID", GameManager.getInstance().getUserID()); 
			params.put("tipoCarta", GameManager.getInstance().getCardID());  
//			Acho que tb vaii ter que passar o id do amigo. 
			result.put("message", params);
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		return result; 
	}
	
	public static JSONObject randomOpponent() {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject();
		try {
			params.put("requestID", "RandomOpponent"); 
			params.put("userID", GameManager.getInstance().getUserID()); 
			result.put("message", params);
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		return result; 
	}
	
	public static JSONObject myPicture() {
		JSONObject params = new JSONObject(); 
		JSONObject result = new JSONObject();
		try {
			params.put("requestID", "MyPicture"); 
			params.put("userID", GameManager.getInstance().getUserID());
			params.put("url", GameManager.getInstance().getUserPictureURL()); 
			result.put("message", params);
		} catch (JSONException e) {
			e.printStackTrace(); 
		}
		return result; 
	}
}
