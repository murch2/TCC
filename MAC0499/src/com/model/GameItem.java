/**
 * @author Rodrigo Duarte Louro
 * @dateSep 8, 2014
 */
package com.model;

import java.util.StringTokenizer;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;

public class GameItem extends SpriteMenuItem {
	
	public enum Status {
		PLAY, 
		POKE, 
	}
	
	public Sprite backgroundCell;
	private Sprite friendPicture; 
	private String friendID;
	private String friendURLPicture; 
	private String friendNameString;
	private Text friendNameText;
	private JSONObject jsonFriendInfo;
	private Status status; 
	private int friendScore;
	private Text friendScoreText;
	private Text myNameText; 
	private int myScore;
	private Text myScoretext;
	private Sprite statusSprite; 
	
	public final int MAX_CHAR_NAME = 5;
	 
	public GameItem(int idButton, String idFriend, JSONObject friendInfo) {
		super(idButton, ResourcesManager.getInstance().gameItemBackGroundRegion, ResourcesManager.getInstance().vbom);
		this.jsonFriendInfo = friendInfo; 
		setFriendID(idFriend); 
		createPicture(); 
		createFriendNameText();
		createMyText();
		createStatus();
	}
	
	public void createStatus() {
		ITextureRegion btnStatus; 
		String status;
		try {
			status = jsonFriendInfo.getString("gameStatus");
			if (status.equals("PLAY")) {
				setStatus(Status.PLAY); 
				btnStatus = ResourcesManager.getInstance().btnStatusPlayRegion; 
			}
			else if (status.equals("POKE")) {
				setStatus(Status.POKE); 
				btnStatus = ResourcesManager.getInstance().btnStatusPokeRegion; 
			}
			else {
				return; 
			}
		} catch (JSONException e) {
			return; 
		}
		
		statusSprite = new Sprite(getWidth() * 0.85f, getHeight() * 0.5f, btnStatus,
				ResourcesManager.getInstance().vbom);
		attachChild(statusSprite);
		
		Text statusText = new Text(0, 0, ResourcesManager.getInstance().gameFont, status, ResourcesManager.getInstance().vbom); 
		statusText.setPosition(statusSprite.getWidth() * 0.5f, statusSprite.getHeight() * 0.6f);  
		statusText.setScale(0.8f);
		statusText.setColor(Color.WHITE); 
		statusSprite.attachChild(statusText);
	}

	private void createPicture() {
		friendPicture = new Sprite(0, 0, ResourcesManager.getInstance().manDefault, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		friendPicture.setPosition(this.getWidth() * 0.15f, this.getHeight() * 0.5f);
		attachChild(friendPicture);
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					setFriendURLPicture(jsonFriendInfo.getString("pictureOpponent")); 
					Sprite updatePicture = ImageDownloader.downloadImage(getFriendURLPicture());
					updatePicture.setWidth(friendPicture.getWidth());
					updatePicture.setHeight(friendPicture.getHeight());
					updatePicture.setPosition(friendPicture); 
					detachChild(friendPicture); 
					friendPicture = updatePicture; 
					attachChild(friendPicture);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}); 
	}
	
	private void createFriendNameText() {
		try {
			setFriendName(this.jsonFriendInfo.getString("nameOpponent")); 
			friendNameText = new Text(0, 0, ResourcesManager.getInstance().arialFont, nameTreatment(friendNameString), ResourcesManager.getInstance().vbom);
			friendNameText.setAnchorCenter(0f, 0.5f); 
			friendNameText.setPosition(Constants.WIDTH_PICKER_CELL * 0.27f, Constants.HEIGHT_PICKER_CELL * 0.7f);  
			friendNameText.setColor(Color.WHITE); 
			attachChild(friendNameText); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}
	
	public void createMyText() { 
		myNameText = new Text(0, 0, ResourcesManager.getInstance().arialFont, "eu", ResourcesManager.getInstance().vbom);
		myNameText.setAnchorCenter(0.0f, 0.5f); 
		myNameText.setPosition(Constants.WIDTH_PICKER_CELL * 0.6f, Constants.HEIGHT_PICKER_CELL * 0.7f);  
		myNameText.setColor(Color.WHITE); 
		attachChild(myNameText);
	}
	
	public String getFriendID() {
		return friendID;
	}
	
	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}

	public String getFriendURLPicture() {
		return friendURLPicture;
	}

	public void setFriendURLPicture(String friendURLPicture) {
		this.friendURLPicture = friendURLPicture;
	}

	public String getFriendName() {
		System.out.println("Testando o nome " + friendNameString);
		return friendNameString;
	}

	public void setFriendName(String friendNameString) {
		this.friendNameString = friendNameString;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getFriendScore() {
		return friendScore;
	}

	public void setFriendScore(int friendScore) {
		this.friendScore = friendScore;
	}
	
	public Text getFriendScoreText() {
		return friendScoreText;
	}

	public void setFriendScoreText(Text friendScoreText) {
		this.friendScoreText = friendScoreText;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}

	public Text getMyScoretext() {
		return myScoretext;
	}

	public void setMyScoretext(Text myScoretext) {
		this.myScoretext = myScoretext;
	}
	
	private String nameTreatment(String name) {
		StringTokenizer str = new StringTokenizer(name);  
		
		if (str.hasMoreTokens()) {
			name = str.nextToken(); 
			
			if (name.length() > MAX_CHAR_NAME) {
				name = name.substring(0, MAX_CHAR_NAME - 1);
				name = name + ".";
			}
		}
		return name; 
	}
}
