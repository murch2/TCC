/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.model;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;

public class FriendPickerCell extends Entity {
	//Pode ser que não seja sprite
	private Sprite backgroundCell;
	private Sprite friendPicture; 
	private String friendID;
	private String friendURLPicture; 
	private String friendNameString;
	private Text friendNameText;
	private JSONObject jsonFriendInfo; 
	
	//Aqui eu tenho que passar as informações 
	public FriendPickerCell(JSONObject json) {
		this.jsonFriendInfo = json;
		try {
			this.friendID = json.getString("uid");
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		createBackground();
//		O create picture precisa do link pra atualizar
		createPicture(); 
		createNameText(); 
//		createFriendPicture(); 
	}
	
	private void createBackground() {
		backgroundCell = new Sprite(0, 0, Constants.WIDTH_PICKER_CELL, Constants.HEIGHT_PICKER_CELL,
				ResourcesManager.getInstance().friendPickerCellBackGroundRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(backgroundCell);
	}
	
	private void createPicture() {
		friendPicture = new Sprite(0, 0, ResourcesManager.getInstance().defaultPictureRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		float x = - Constants.WIDTH_PICKER_CELL * 0.5f + ResourcesManager.getInstance().defaultPictureRegion.getWidth() * 0.5f + 10; 
		friendPicture.setPosition(x, 0);
		attachChild(friendPicture);
		System.out.println("DEBUG - Colocou a imagem default");
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				String link; 
				try {
					link = jsonFriendInfo.getString("pic_big");
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
			friendNameString = this.jsonFriendInfo.getString("name");
			friendNameText = new Text(0, 0, ResourcesManager.getInstance().font, friendNameString, ResourcesManager.getInstance().vbom);
			friendNameText.setAnchorCenter(0f, 0.5f); 
			friendNameText.setPosition(- Constants.WIDTH_PICKER_CELL * 0.25f, Constants.HEIGHT_PICKER_CELL * 0.3f);  
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
}
