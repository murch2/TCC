/**
 * @author Rodrigo Duarte Louro
 * @dateJul 21, 2014
 */
package com.util;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Session;
import com.facebook.SessionState;
import com.managers.ResourcesManager;

public class FacebookFacade {
	
	public void login(final GraphUserCallback callback){

		Session.openActiveSession(ResourcesManager.getInstance().activity, true, new Session.StatusCallback() {
			// callback when session changes state
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened()) { 
					Request.newMeRequest(session, callback).executeAsync(); 
				}
			}
		});
	}
}
