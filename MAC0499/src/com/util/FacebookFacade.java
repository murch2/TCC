/**
 * @author Rodrigo Duarte Louro
 * @dateJul 21, 2014
 */
package com.util;

import java.util.Vector;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Session;
import com.facebook.SessionState;
import com.managers.GameManager;
import com.managers.ResourcesManager;

public class FacebookFacade {

	private final Vector<String> PERMISSIONS = new Vector<String>();  
	private boolean isFetching = false; 
	
	public FacebookFacade () {
		PERMISSIONS.add("user_friends");
		PERMISSIONS.add("email"); 
//		PERMISSIONS.add("publish_actions"); 
	}
	
	public void login(final GraphUserCallback callback){

		final Session.NewPermissionsRequest newPermissionsRequest = 
				new Session.NewPermissionsRequest(ResourcesManager.getInstance().activity, PERMISSIONS);
		Session.openActiveSession(ResourcesManager.getInstance().activity, true, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened() && !isFetching) {  
					isFetching = true;
					//talvez isso precise ser usado todas as vezes, mas por enquanto assim está ok.
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
}




/*Inspiração para o login
private void performFacebookLogin()
{
    Log.d("FACEBOOK", "performFacebookLogin");
    final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("email"));
    Session openActiveSession = Session.openActiveSession(this, true, new Session.StatusCallback()
    {
        @Override
        public void call(Session session, SessionState state, Exception exception)
        {
            Log.d("FACEBOOK", "call");
            if (session.isOpened() && !isFetching)
            {
                Log.d("FACEBOOK", "if (session.isOpened() && !isFetching)");
                isFetching = true;
                session.requestNewReadPermissions(newPermissionsRequest);
                Request getMe = Request.newMeRequest(session, new GraphUserCallback()
                {
                    @Override
                    public void onCompleted(GraphUser user, Response response)
                    {
                        Log.d("FACEBOOK", "onCompleted");
                        if (user != null)
                        {
                            Log.d("FACEBOOK", "user != null");
                            org.json.JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
                            String email = graphResponse.optString("email");
                            String id = graphResponse.optString("id");
                            String facebookName = user.getUsername();
                            if (email == null || email.length() < 0)
                            {
                                Logic.showAlert(
                                        ActivityLogin.this,
                                        "Facebook Login",
                                        "An email address is required for your account, we could not find an email associated with this Facebook account. Please associate a email with this account or login the oldskool way.");
                                return;
                            }
                        }
                    }
                });
                getMe.executeAsync();
            }
            else
            {
                if (!session.isOpened())
                    Log.d("FACEBOOK", "!session.isOpened()");
                else
                    Log.d("FACEBOOK", "isFetching");

            }
        }
    });
*/