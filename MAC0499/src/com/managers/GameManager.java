/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.managers;

import com.util.Constants;
import com.util.DataInMemory;

public class GameManager {
	private static GameManager INSTANCE = new GameManager(); 
	private DataInMemory dataInMemory = new DataInMemory(ResourcesManager.getInstance().activity, Constants.FILE_SAVE_DATA);
	private boolean loggedUser = false; 
	private String userName; 
	private String userID; 	

	public static GameManager getInstance() {
		return INSTANCE; 
	}
	
	public GameManager (){
	}

	public DataInMemory getDataInMemory() {
		return dataInMemory;
	}

	public boolean isLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(boolean loggedUser) {
		this.loggedUser = loggedUser;
	}
	

	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
