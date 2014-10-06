/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

import java.util.Vector;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.MenuItem;

import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerItem;
import com.model.FriendPickerMenu;
import com.util.Constants;
import com.util.FacebookFacade;

public class FriendPickerScene extends BaseScene implements Callback, IOnMenuItemClickListener {

	private JSONObject jsonFriends; 
	
	@Override
	public void createScene() { 
		new FacebookFacade().getFriends(this);
	}
	
	private void createItensScene () {
		createBackground();
		makeFriendsMenu();  
	}
	
	private void createBackground() {
		setBackground(new Background(Color.RED));
	}
	
//	Talvez tivesse que ter uma classe pra esse menu (Pra ele ser scrolavel e tals)
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
		// TODO Auto-generated method stub
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
