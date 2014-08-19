/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

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
import com.managers.ResourcesManager;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerItem;
import com.model.FriendPickerMenu;
import com.util.Constants;
import com.util.FacebookFacade;

public class FriendPickerScene extends BaseScene implements Callback, IOnMenuItemClickListener {

	private JSONObject jsonFriends; 
	
	@Override
	public void createScene() {
		this.setTouchAreaBindingOnActionDownEnabled(true); 
		new FacebookFacade().getFriends(this);
	}
	
	private void createItensScene () {
		createBackground();
		makeFriendsMenu();  
	}
	
	private void createBackground() {
		setBackground(new Background(Color.RED));
	}
	
	private void makeFriendsMenu() {
		try {
			JSONArray array = jsonFriends.getJSONArray("data");
			MenuScene menu = new MenuScene(ResourcesManager.getInstance().camera);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i); 
				final IMenuItem item = new FriendPickerItem(i, json);
				menu.addMenuItem(item); 
			}
			
			menu.buildAnimations();
			menu.setBackgroundEnabled(false);
			menu.setOnMenuItemClickListener(this); 

			setChildScene(menu);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		
//		try {
//			JSONArray array = jsonFriends.getJSONArray("data");		
//			FriendPickerCell cell; 
//			float offSetY = 10;
//			float currentY = Constants.CAMERA_HEIGHT * 0.7f;
////			Na verdade todas essas celulas tem que ser colocadas em um scroll 
//			for (int i = 0; i < array.length(); i++) {
//				cell = new FriendPickerCell(array.getJSONObject(i)); 
//				cell.setPosition(Constants.CAMERA_WIDTH * 0.5f, currentY);
//				currentY -= (Constants.HEIGHT_PICKER_CELL + offSetY);
//				System.out.println("DEBUG - VOltou pra cena");
//				registerTouchArea(cell);
//				attachChild(cell);
//			}
//		} 
//		catch (JSONException e) {
//			e.printStackTrace();
//		} 
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
		jsonFriends = graphObject.getInnerJSONObject() ;
		createItensScene(); 
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("DEBUG - clicou");
		return true;
	}
}
