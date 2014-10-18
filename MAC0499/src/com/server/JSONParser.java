/**
 * @author Rodrigo Duarte Louro
 * @dateJul 30, 2014
 */
package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	private JSONObject result; 
	private String jsonString;
	
	private InputStream is; 
	
	public JSONParser(InputStream is) {
		this.is = is;
	}
	
	public JSONObject parse() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 65000);
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			is.close(); 
			jsonString = sb.toString(); 
			result = new JSONObject(jsonString); 
			
			return result; 
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//TODO fazer um json de erro para retornar 
		return null; 
	}
}
