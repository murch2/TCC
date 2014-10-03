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
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.LoadingLayer;

public class NewGameScene extends BaseSceneWithHUD implements IOnMenuItemClickListener, HTTPResponseListener {

	private MenuScene menuNewGame; 
	private final int ITEM_FACEBOOK_FRIEND = 0;
	private final int ITEM_RANDOM_OPPONNENT = 1;
	
	@Override
	public void createScene() {
		createBackground();
		createMenu(); 
	}
	
	private void createBackground() {
		setBackground(new Background(Color.YELLOW));
	}
	
	private void createMenu() {
		menuNewGame = new MenuScene(camera);
		menuNewGame.setPosition(0, 0); 

		final IMenuItem itemFacebookFriend = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ITEM_FACEBOOK_FRIEND, resourcesManager.facebookFriendsMenuRegion, vbom), 0.8f, 1);
		
		final IMenuItem itemRandomOpponent = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ITEM_RANDOM_OPPONNENT, resourcesManager.randomOpponentMenuRegion, vbom), 0.8f, 1);
		
		menuNewGame.addMenuItem(itemFacebookFriend);
		menuNewGame.addMenuItem(itemRandomOpponent); 
		
		menuNewGame.buildAnimations();
		menuNewGame.setBackgroundEnabled(false);

		//Poderia ter uma classe aqui que é responsavel por isso. (O click do botão de menu). 
		menuNewGame.setOnMenuItemClickListener(this); 

		setChildScene(menuNewGame);

	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.NEWGAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		LoadingLayer loading = new LoadingLayer();
		
		switch (pMenuItem.getID()) {
			case ITEM_FACEBOOK_FRIEND:
				SceneManager.getInstance().createNewFriendPickerScene();
//				loading.insertLoadingLayer(camera); 
				System.out.println("Cliquei no Facebook");
				return true; 
			case ITEM_RANDOM_OPPONNENT:
				System.out.println("Cliquei no Random Opp");
				new HTTPPostRequester().asyncPost(this, MakeParameters.randomOpponent());
//				loading.insertLoadingLayer(camera); 
				return true; 

			default:
				break;
		}
		return false;
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			System.out.println("Chegou resposta!!!!!!! COCO");
			System.out.println("Vai Foder");
			
			if (json != null) {
				System.out.println("Json não eh null");
				System.out.println(json.toString(4));
			} else {	
				System.out.println("Json eh null e a gente se fudeu");
			}
			
			json = json.getJSONObject("dados"); 
			System.out.println("Fudeu");
			GameManager.getInstance().setFriendID(json.getString("id")); 
			System.out.println("UM");
			GameManager.getInstance().setFriendPictureURL(json.getString("foto"));
			System.out.println("DOIS");
			GameManager.getInstance().setFriendName("nome");
			System.out.println("Vou trocar de cena");
			SceneManager.getInstance().createChoiceScene(); 
		} catch (JSONException e) {
			System.out.println("Caiu no cat");
			e.printStackTrace();
			
		}
	}

}
