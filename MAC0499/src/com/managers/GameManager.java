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
	private int userCoins; 
	private int userPowerUps;
	private String userPictureURL;
	private int cardTypeID; 
//	private int cardID; 
	
	private String friendID;
	private String friendPictureURL; 
	private String friendName; 

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

	public int getUserCoins() {
		return userCoins;
	}

	public void setUserCoins(int userCoins) {
		this.userCoins = userCoins;
	}

	public int getUserPowerUps() {
		return userPowerUps;
	}

	public void setUserPowerUps(int userPowerUps) {
		this.userPowerUps = userPowerUps;
	}

	public String getUserPictureURL() {
		return userPictureURL;
	}

	public void setUserPictureURL(String userPictureURL) {
		this.userPictureURL = userPictureURL;
	}

	public String getFriendID() {
		return friendID;
	}

	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}

	public String getFriendPictureURL() {
		return friendPictureURL;
	}

	public void setFriendPictureURL(String friendPictureURL) {
		this.friendPictureURL = friendPictureURL;
	}

	public int getCardTypeID() {
		return cardTypeID;
	}

	public void setCardTypeID(int cardID) {
		this.cardTypeID = cardID;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

//	public int getCardID() {
//		return cardID;
//	}
//
//	public void setCardID(int cardID) {
//		this.cardID = cardID;
//	}
}
