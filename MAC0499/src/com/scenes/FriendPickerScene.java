/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerItem;
import com.util.Constants;
import com.util.FacebookFacade;

public class FriendPickerScene extends BaseScene implements Callback, IOnMenuItemClickListener {

	private JSONObject jsonFriends; 
	private Text title; 
	
	@Override
	public void createScene() { 
		new FacebookFacade().getFriends(this);
	}
	
	private void createItensScene () {
		createBackground();
		createTitle(); 
		makeFriendsMenu();  
	}
	
	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.blueBackground, vbom); 
		attachChild(background);
	}
	
	private void createTitle() {
		title = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Select a \n Facebook Friend!", new TextOptions(HorizontalAlign.CENTER), vbom);
		title.setColor(0f, 0f, 139.0f/255); 
		title.setScale(1.5f);
		title.setPosition(Constants.CENTER_X, Constants.CAMERA_HEIGHT * 0.8f); 
		attachChild(title);
	} 
	
	private void makeFriendsMenu() {
		JSONArray array;
		JSONObject json; 
		IMenuItem item; 
		try {
			array = jsonFriends.getJSONArray("data");
			MenuScene menu = new MenuScene(ResourcesManager.getInstance().camera);
			for (int i = 0; i < array.length(); i++) {
				json = array.getJSONObject(i); 
				item = new FriendPickerItem(i, json.getString("uid"), json);
				menu.addMenuItem(item); 
			}		
			
			menu.buildAnimations();
			menu.setBackgroundEnabled(false);
			menu.setOnMenuItemClickListener(this); 

			setChildScene(menu);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.FRIENDPICKER_SCENE; 
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadFriendPickerScene();  
	}

	@Override
	public void onCompleted(Response response) {	
		GraphObject graphObject = response.getGraphObject();
		jsonFriends = graphObject.getInnerJSONObject();
		createItensScene(); 
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		FriendPickerItem item = (FriendPickerItem) pMenuItem; 
		
		if (item != null) {
			GameManager.getInstance().setFriendID(item.getFriendID()); 
			System.out.println("FDP - urlItem = " + item.getFriendURLPicture());
			GameManager.getInstance().setFriendPictureURL(item.getFriendURLPicture());
			GameManager.getInstance().setFriendName(item.getFriendName()); 
			SceneManager.getInstance().createChoiceScene(); 
			return true;
		}
		return false; 
	}
}
