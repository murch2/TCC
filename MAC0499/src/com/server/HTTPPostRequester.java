/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.server;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.test.UiThreadTest;

public class HTTPPostRequester {

//	private String url = "http://172.16.4.169:80/Requisicao.php"; //IME (USPNET)
//	private String url = "http://192.168.0.149/Requisicao.php"; //CASA
	private String url = "http://192.168.0.10/Requisicao.php"; //AP

	
	public JSONObject post(JSONObject jsonParams) {
		HttpClient httpClient = new DefaultHttpClient(); 
		HttpPost post = new HttpPost(url);
		try {
			StringEntity entityParams = new StringEntity("message=" + jsonParams.toString());
			
			post.setEntity(entityParams);
		    post.addHeader("content-type", "application/x-www-form-urlencoded");
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) 
				return new JSONParser(entity.getContent()).parse();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		TODO fazer um json com mensagem de erro
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
			return HTTPPostRequester.this.post(obj);
		}

		protected void onPostExecute(JSONObject result) {
			responseListener.onResponse(result); 
		}
	}
}
