/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
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
		Text title = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Escolha um", new TextOptions(HorizontalAlign.CENTER), vbom);
		title.setColor(0f, 0f, 139.0f/255); 
		title.setScale(1.35f);
		title.setPosition(Constants.CENTER_X, Constants.CAMERA_HEIGHT * 0.85f); 
		attachChild(title);
		
		Text title2 = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Amigo", new TextOptions(HorizontalAlign.CENTER), vbom);
		title2.setColor(0f, 0f, 139.0f/255); 
		title2.setScale(1.35f);
		title2.setPosition(Constants.CENTER_X, Constants.CAMERA_HEIGHT * 0.75f); 
		attachChild(title2);
	} 
	
	private JSONArray teste() {
		JSONArray array = new JSONArray(); 
		
		JSONObject json = new JSONObject();
		try {
			json.put("uid", 0);
			json.put("pic_big", "https://scontent-b-mia.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/10696211_712180428858437_2864614006783494214_n.jpg?oh=37f839472ca06d5a2f48d67f5e9750db&oe=54BBDB2A");
			json.put("name", "Ana Carolina");
			array.put(0, json); 
			json = new JSONObject();
			json.put("uid", 1);
			json.put("pic_big", "https://scontent-b-mia.xx.fbcdn.net/hphotos-xpa1/v/t1.0-9/10511227_668037466624053_2740939400989464565_n.jpg?oh=18d33e1052b5f8c061aff8794e3d36e1&oe=54BAE839");
			json.put("name", "Ronaldo Duarte");
			array.put(1, json);
			json = new JSONObject();
			json.put("uid", 2);
			json.put("pic_big", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/381838_200435380037904_66433787_n.jpg?oh=c97436e0cec6445c3ab6f4c218b8062a&oe=54ED65BD&__gda__=1421544218_d009ab474940c47fe5631bfc2cfbfbdc");
			json.put("name", "Rodrigo Duarte");
			array.put(2, json);
			json = new JSONObject();
			json.put("uid", 3);
			json.put("pic_big", "https://scontent-a-mia.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/1450206_705755686102918_1192310898_n.jpg?oh=0f29bb2cca32f52e628173ec62898140&oe=54D32634");
			json.put("name", "Daniel Paulino");
			array.put(3, json);
			json = new JSONObject();
			json.put("uid", 4);
			json.put("pic_big", "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-9/10646744_685195901563537_5798702077653289710_n.jpg?oh=1a9d4e56dba346bedca73c1554dfba4b&oe=54E3281D&__gda__=1424565413_1c93dae8fa975919b562d66e4ed68d93");
			json.put("name", "Renata Queroz");
			array.put(4, json);
		} catch (JSONException e) {
		
			e.printStackTrace();
		} 
		
		
		return array; 
	}
	
	private void makeFriendsMenu() {
		JSONArray array;
		JSONObject json; 
		IMenuItem item; 
		try {
			array = jsonFriends.getJSONArray("data");
//			array = teste(); 
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
			
			menu.setPosition(menu.getX(), menu.getY() - 200);
			
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
			GameManager.getInstance().setFriendPictureURL(item.getFriendURLPicture());
			GameManager.getInstance().setFriendName(item.getFriendName()); 
			SceneManager.getInstance().createChoiceScene(); 
			return true;
		}
		return false; 
	}
}
