/**
 * @author Rodrigo Duarte Louro
 * @dateSep 8, 2014
 */
package com.model;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;

public class GameItem extends SpriteMenuItem {
	
	private enum Status {
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
	private int myScore;
	private Text myScoretext;  
	 
	public GameItem(int idButton, String idFriend, JSONObject friendInfo) { 
		super(idButton, ResourcesManager.getInstance().gameItemBackGroundRegion, ResourcesManager.getInstance().vbom);
		this.jsonFriendInfo = friendInfo; 
		friendID = idFriend; 
		createPicture(); 
		createNameText(); 
	}

	private void createPicture() {
		friendPicture = new Sprite(0, 0, ResourcesManager.getInstance().defaultPictureRegion2, ResourcesManager.getInstance().vbom) {
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
				String link; 
				try {
					link = jsonFriendInfo.getString("pictureOpponent");
					Sprite updatePicture = ImageDownloader.testeImage(link);
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
	
	private void createNameText() {
		try {
			friendNameString = this.jsonFriendInfo.getString("nameOpponent");
			friendNameText = new Text(0, 0, ResourcesManager.getInstance().font, friendNameString, ResourcesManager.getInstance().vbom);
			friendNameText.setAnchorCenter(0f, 0.5f); 
			friendNameText.setPosition(Constants.WIDTH_PICKER_CELL * 0.25f, Constants.HEIGHT_PICKER_CELL * 0.3f);  
			friendNameText.setColor(Color.WHITE); 
			attachChild(friendNameText); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
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
}
