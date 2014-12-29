/**
 * @author Rodrigo Duarte Louro
 * @dateJul 30, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.LoadingLayer;

public class NewGameScene extends BaseSceneWithHUD implements IOnMenuItemClickListener, HTTPResponseListener {

	private MenuScene menuNewGame; 
	private final int ITEM_FACEBOOK_FRIEND = 0;
	private final int ITEM_RANDOM_OPPONNENT = 1;
	
	@Override
	public void createScene() {
		createBackground();
		createMenu();
		createHUD(); 
	}
	
	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.blueBackground, vbom); 
		attachChild(background);
	}
	
	private void createMenu() {
		menuNewGame = new MenuScene(camera);
		menuNewGame.setPosition(0, 0); 

		final IMenuItem itemFacebookFriend = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ITEM_FACEBOOK_FRIEND, resourcesManager.blueButton, vbom), 0.8f, 1);
		
		final IMenuItem itemRandomOpponent = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ITEM_RANDOM_OPPONNENT, resourcesManager.blueButton, vbom), 0.8f, 1);
		
		
		Text fbText = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Amigo do Facebook", vbom); 
		fbText.setPosition(itemFacebookFriend.getWidth() * 0.5f, itemFacebookFriend.getHeight() * 0.7f);
		fbText.setScale(0.9f); 
		fbText.setColor(Color.WHITE); 
		itemFacebookFriend.attachChild(fbText);
		
		Text randomText = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Oponente Casual", vbom); 
		randomText.setPosition(itemFacebookFriend.getWidth() * 0.5f, itemFacebookFriend.getHeight() * 0.7f);
		randomText.setScale(0.9f);
		randomText.setColor(Color.WHITE); 
		itemRandomOpponent.attachChild(randomText);
		
		menuNewGame.addMenuItem(itemFacebookFriend);
		menuNewGame.addMenuItem(itemRandomOpponent); 
		
		menuNewGame.buildAnimations();
		menuNewGame.setBackgroundEnabled(false);

		menuNewGame.setOnMenuItemClickListener(this); 

		setChildScene(menuNewGame);
		
		itemFacebookFriend.setY(itemFacebookFriend.getY() + 5);
		itemRandomOpponent.setY(itemRandomOpponent.getY() - 5);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().createMainMenuScene(); 
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.NEWGAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadNewGameScene(); 
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		switch (pMenuItem.getID()) {
			case ITEM_FACEBOOK_FRIEND:
				SceneManager.getInstance().createNewFriendPickerScene();
				return true; 
			case ITEM_RANDOM_OPPONNENT:
				new HTTPPostRequester().asyncPost(this, MakeParameters.randomOpponent());
				return true; 

			default:
				break;
		}
		return false;
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			json = json.getJSONObject("dados");
			GameManager.getInstance().setFriendID(json.getString("id")); 
			GameManager.getInstance().setFriendPictureURL(json.getString("foto"));
			GameManager.getInstance().setFriendName("nome");
			SceneManager.getInstance().createChoiceScene(); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
