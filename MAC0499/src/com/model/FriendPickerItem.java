/**
 * @author Rodrigo Duarte Louro
 * @dateAug 18, 2014
 */
package com.model;

import java.util.StringTokenizer;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;

public class FriendPickerItem extends SpriteMenuItem {

	public Sprite backgroundCell;
	private Sprite friendPicture; 
	private String friendID;
	private String friendURLPicture; 
	private String friendNameString;
	private Text friendNameText;
	private Text friendMiddleNameText; 
	private JSONObject jsonFriendInfo;
	private final int MAX_CHAR_NAME = 12; 
	
	public FriendPickerItem(int idButton, String idFriend, JSONObject friendInfo) { 
		super(idButton, ResourcesManager.getInstance().friendPickerCellBackGroundRegion, ResourcesManager.getInstance().vbom);
		this.jsonFriendInfo = friendInfo; 
		setFriendID(idFriend); 
		createPicture(); 
		createNameText(); 
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
				String link; 
				try {
					link = jsonFriendInfo.getString("pic_big");
					setFriendURLPicture(link);
					Sprite updatePicture = ImageDownloader.downloadImage(link);
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
			friendNameString = this.jsonFriendInfo.getString("name");
			JSONObject normalizedName = nameTreatment(friendNameString);
			String name = normalizedName.getString("name");
			friendNameText = new Text(0, 0, ResourcesManager.getInstance().gameFont, name, ResourcesManager.getInstance().vbom);
			friendNameText.setAnchorCenter(0.5f, 0.5f); 
			friendNameText.setPosition(Constants.WIDTH_PICKER_CELL * 0.6f, Constants.HEIGHT_PICKER_CELL * 0.78f);  
			friendNameText.setColor(255.0f/255, 239.0f/255, 191.0f/255); 
			attachChild(friendNameText);
			
			String middleName = normalizedName.getString("middleName");
			friendMiddleNameText = new Text(0, 0, ResourcesManager.getInstance().gameFont, middleName, ResourcesManager.getInstance().vbom);
			friendMiddleNameText.setAnchorCenter(0.5f, 0.5f); 
			friendMiddleNameText.setPosition(Constants.WIDTH_PICKER_CELL * 0.6f, Constants.HEIGHT_PICKER_CELL * 0.35f);  
			friendMiddleNameText.setColor(255.0f/255, 239.0f/255, 191.0f/255); 
			attachChild(friendMiddleNameText);
			
			
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
	
	private JSONObject nameTreatment(String name) {
		StringTokenizer str = new StringTokenizer(name); 
		String strName = "";
		String strMiddleName = "";  
		JSONObject result = new JSONObject(); 
		
		if (str.hasMoreTokens()) {
			strName = str.nextToken(); 
			if (strName.length() > MAX_CHAR_NAME) {
				strName = strName.substring(0, MAX_CHAR_NAME - 1);
				strName.concat("."); 
			}
			if (str.hasMoreTokens()) { 
				strMiddleName = str.nextToken();
				if (strMiddleName.length() > MAX_CHAR_NAME) {
					strMiddleName = strMiddleName.substring(0, MAX_CHAR_NAME - 1);
					strMiddleName.concat(".");
				}
			}
			try {
				result.put("name", strName);
				result.put("middleName", strMiddleName);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		return result; 
	}
}
