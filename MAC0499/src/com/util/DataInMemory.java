/**
 * @author Rodrigo Duarte Louro
 * @dateJul 21, 2014
 */
package com.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class DataInMemory {
	 
	private SharedPreferences preferences; 
	private SharedPreferences.Editor editor; 
	
	public DataInMemory(Activity activity, String File) {
		preferences = activity.getSharedPreferences(File, Context.MODE_PRIVATE); 
	}
	
	public void saveData(String key, int value) {
		editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void saveData(String key, boolean value) {
		editor = preferences.edit();
		editor.putBoolean(key, value); 
		editor.commit();
	}
	
	public void saveData(String key, float value) {
		editor = preferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public void saveData(String key, long value) {
		editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public void saveData(String key, String value) {
		editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public int readIntData(String key) {
		return preferences.getInt(key, 0);
	}
	
	public boolean readBooleanData(String key) {
		return preferences.getBoolean(key, false); 
	}
	
	public float readFloatData(String key) {
		return preferences.getFloat(key, (float) 0.0);
	}
	
	public long readLongData(String key) {
		return preferences.getLong(key, (long) 0);  
	}
	
	public String readStringData(String key) {
		return preferences.getString(key, "");  
	}
	
	public boolean alreadyLogedInFacebook() {
		return readBooleanData(Constants.FACEBOOK_LOGIN); 
	}
	
}
