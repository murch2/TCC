/**
 * @author Rodrigo Duarte Louro
 * @dateJul 21, 2014
 */
package com.util;

import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;

public class FacebookFacade implements HTTPResponseListener{

	private final Vector<String> PERMISSIONS = new Vector<String>();  
	private boolean isFetching = false; 
	
	public FacebookFacade () {
		PERMISSIONS.add("user_friends");
		PERMISSIONS.add("email"); 
	}
	
	public void login(final GraphUserCallback callback){

		final Session.NewPermissionsRequest newPermissionsRequest = 
				new Session.NewPermissionsRequest(ResourcesManager.getInstance().activity, PERMISSIONS);
		Session.openActiveSession(ResourcesManager.getInstance().activity, true, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened() && !isFetching) {  
					isFetching = true;
					if (!GameManager.getInstance().getDataInMemory().alreadyLogedInFacebook())
						session.requestNewReadPermissions(newPermissionsRequest);
					Request.newMeRequest(session, callback).executeAsync(); 
				}
				else {
	                if (!session.isOpened())
	                	System.out.println("DEBUG - Session is not open"); 
	                else
	                	System.out.println("DEBUG - Is Fecthing" );
	            }
			}
		});
	} 
	public void getFriends (final Callback callback) {
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				 faceFriends(callback); 
			}
		}); 
	}
	
	private void faceFriends (Callback callback) {
		String fqlQuery = "SELECT uid,name,pic_big FROM user WHERE uid IN " +
		        "(SELECT uid2 FROM friend WHERE uid1 = me())";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);
		Session session = Session.getActiveSession();
		Request request = new Request(session, "/fql", params, HttpMethod.GET, callback); 
		Request.executeBatchAsync(request); 
	}
	
	public void getUserPicture() {
		System.out.println("Get User Picture");
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				 userPicture(); 
			}
		});
	}
	
	private void userPicture() {
		Bundle params = new Bundle();
		params.putBoolean("redirect", false);
		params.putString("height", "300");
		params.putString("type", "large");
		params.putString("width", "300");
		new Request(
		    Session.getActiveSession(),
		    "/me/picture",
		    params,
		    HttpMethod.GET,
		    new Request.Callback() {
		    	
		    	public void onCompleted(Response response) {
		        	try {
		        		JSONObject json = response.getGraphObject().getInnerJSONObject().getJSONObject("data");
						if (!json.getBoolean("is_silhouette")) {
							GameManager.getInstance().setUserPictureURL(json.getString("url"));
							new HTTPPostRequester().asyncPost(FacebookFacade.this, MakeParameters.myPicture()); 
						}		
					} catch (JSONException e) {
						e.printStackTrace();
					}
		        	
		        }
		    }
		).executeAsync();
	}

	@Override
	public void onResponse(JSONObject json) {		
//		TODO 
	}
}