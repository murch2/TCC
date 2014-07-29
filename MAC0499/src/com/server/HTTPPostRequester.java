/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class HTTPPostRequester {

	private String url = "http://192.168.0.149/Requisicao.php"; 
	
	
//	public JSONObject teste() {
//		String json = "{\"message\":\"This is a message\"}";
//
//	    HttpClient httpClient = new DefaultHttpClient();
//
//	    try {
//	        HttpPost request = new HttpPost(url);
//	        StringEntity params = new StringEntity("message=" + json);
//	        request.addHeader("content-type", "application/x-www-form-urlencoded");
//	        request.setEntity(params);
//	        HttpResponse response = httpClient.execute(request);
//
//	        // handle response here...
//
//	        
////	        System.out.println(org.apache.http.util.EntityUtils.toString(response.getEntity()));
//	        StringBuilder sb = new StringBuilder();
//			try {
//			    BufferedReader reader = 
//			           new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
//			    String line = null;
//
//			    while ((line = reader.readLine()) != null) {
//			        sb.append(line);
//			    }
//			}
//			catch (IOException e) { e.printStackTrace(); }
//			catch (Exception e) { e.printStackTrace(); }
//			System.out.println("AQUI FDP " + sb.toString());
//			return null;
//
//	    } catch (Exception ex) {
//	        // handle exception here
//	    } finally {
//	        httpClient.getConnectionManager().shutdown();
//	    }
//		return null;
//	}


	public JSONObject post(JSONObject jsonParams) {
		HttpClient httpClient = new DefaultHttpClient(); 
		HttpPost post = new HttpPost(url);
		try {
			StringEntity entityParams = new StringEntity("message=" + jsonParams.toString());
			
			post.setEntity(entityParams);
		    post.addHeader("content-type", "application/x-www-form-urlencoded");
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			StringBuilder sb = new StringBuilder();

			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			System.out.println("finalResult " + sb.toString());
			return new JSONObject(sb.toString());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void asyncPost(HTTPResponseListener httpResponseListener, JSONObject obj) {
		new HttpPostRequest(httpResponseListener).execute(url, obj.toString());
	}

	private class HttpPostRequest extends AsyncTask<String, String, JSONObject> {

		private HTTPResponseListener responseListener;

		public HttpPostRequest(HTTPResponseListener responseListener) {
			this.responseListener = responseListener; 
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject obj = null;
			try {
				obj = new JSONObject(params[1]);
			} catch (JSONException e) {
				e.printStackTrace(); 
			} 
			System.out.println("DEBUG - dentro da classe estranha obj = " + obj);
			return HTTPPostRequester.this.post(obj);
//			return HTTPPostRequester.this.teste(); 
		}

		protected void onPostExecute(JSONObject result) {
			System.out.println("DEBUG - result na classe estranha : " + result);
			responseListener.onResponse(result); 
		}
	}

}
