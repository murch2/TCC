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
import com.facebook.RequestAsyncTask;
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
	
	//Depois eu tenho que mudar isso pra receber um callback. 

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
		System.out.println("Chamando User picture");
		Bundle params = new Bundle();
		params.putBoolean("redirect", false);
		params.putString("height", "300");
		params.putString("type", "large");
		params.putString("width", "300");
		/* make the API call */
		new Request(
		    Session.getActiveSession(),
		    "/me/picture",
		    params,
		    HttpMethod.GET,
		    new Request.Callback() {
		        public void onCompleted(Response response) {
//		           Acho que aqui só preciso setar o link no gamemanager e em todo lugar que eu for usar pdevo perguntar se eh null ou não,
//		        	Além de setar o link no gameManager eu preciso dar update no banco de dados
//		        	Aqui eu já posso perguntar se é silueta ou não. 
		        	
		        	try {
		        		JSONObject json = response.getGraphObject().getInnerJSONObject().getJSONObject("data");
						if (!json.getBoolean("is_silhouette")) {
							GameManager.getInstance().setUserPictureURL(json.getString("url"));
//							Aqui eu tenho que fazer uma requisição pra setar a foto do cara. 
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
		
	}
	
//	private void faceFriends (Callback callback) {
//		String fqlQuery = "SELECT uid,name,pic_square FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me() )";
//        final Bundle params = new Bundle();
//        params.putString("q", fqlQuery);
//        Request request = new Request(Session.getActiveSession(),  "/fql",  params, HttpMethod.GET, callback);
//        RequestAsyncTask task = new RequestAsyncTask(request);
//        task.execute(); 
//  }
//	
//	private void faceFriends (Callback callback) {
//		Bundle params = new Bundle();
//        params.putString("fields", "name,birthday,picture");
//        params.putBoolean("redirect", false);
//        params.putString("height", "200");
//        params.putString("type", "normal");
//        params.putString("width", "200");
//		new Request(
//			    Session.getActiveSession(),
//			    "me/friends/",
//			    params,
//			    HttpMethod.GET,
//			    callback
//			).executeAsync();
//		
//	}
//	
	
	
}

//	new Request(
//		    Session.getActiveSession(),
//		    "/me/friends",
//		    null,
//		    HttpMethod.GET,
//		    new Request.Callback() {
//		        public void onCompleted(Response response) {
//		            System.out.println(response);
//		        }
//
//		    }
//		).executeAsync();



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