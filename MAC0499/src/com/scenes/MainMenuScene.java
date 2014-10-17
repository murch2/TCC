/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.GameItem;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.LoadingLayer;
import com.util.Constants;
import com.util.FacebookFacade;

public class MainMenuScene extends BaseSceneWithHUD implements HTTPResponseListener, GraphUserCallback, IOnMenuItemClickListener {

	private MenuScene menuNewGame; 
	private MenuScene menuMyGames;
	private final int MENU_NEWGAME = 0;
	private int numRequests;
	private JSONObject jsonMyGames; 

	@Override
	public void createScene() {
		createBackground(); 
		putLoading();
		if (!GameManager.getInstance().isLoggedUser()) {
			new FacebookFacade().login(this); 
		} else {
			numRequests = 2;
			new HTTPPostRequester().asyncPost(this, MakeParameters.getUserInfo(GameManager.getInstance().getUserID()));
			new HTTPPostRequester().asyncPost(this, MakeParameters.myGames(GameManager.getInstance().getUserID()));  
		}
	}

	private void createItensScene() {
		createBtnNewGame();
		createMenuMyGames(); 
		createHUD(); 
	}

	public void putLoading() {
		LoadingLayer loading = new LoadingLayer();
		loading.insertLoadingLayer(camera); 
	}
	
	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createBtnNewGame() {
		menuNewGame = new MenuScene(camera);
		menuNewGame.setPosition(0, 0.15f * Constants.CAMERA_HEIGHT); 

		final IMenuItem itemNewGame = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_NEWGAME, resourcesManager.newGameMenuRegion, vbom), 0.8f, 1);
		
		menuNewGame.addMenuItem(itemNewGame);
		menuNewGame.buildAnimations();
		menuNewGame.setBackgroundEnabled(false);
		
		menuNewGame.setOnMenuItemClickListener(this); 

		setChildScene(menuNewGame);
	}
	
	private void createMenuMyGames() {
		JSONArray array;
		JSONObject json; 
		IMenuItem item;
		
		try {
			array = jsonMyGames.getJSONArray("dados");
			menuMyGames = new MenuScene(ResourcesManager.getInstance().camera);
			for (int i = 0; i < array.length(); i++) {
				json = array.getJSONObject(i); 
				item = new GameItem(i, json.getString("idOpponent"), json);
				item.setPosition(Constants.CAMERA_WIDTH * 0.5f, 300 * i);
				menuMyGames.addMenuItem(item); 
			}
			
			menuMyGames.buildAnimations();
			menuMyGames.setBackgroundEnabled(false);
			menuMyGames.setOnMenuItemClickListener(this); 

			setChildScene(menuMyGames);
			
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
		return SceneType.MAINMENU_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void onCompleted(GraphUser user, Response response) {
		if (user != null) {
			GameManager.getInstance().setUserID(user.getId()); 
			numRequests = 2; 
			new HTTPPostRequester().asyncPost(this, MakeParameters.getUserInfo(user.getId()));
			new HTTPPostRequester().asyncPost(this, MakeParameters.myGames(GameManager.getInstance().getUserID())); 
			GameManager.getInstance().setLoggedUser(true);  
			GameManager.getInstance().setUserName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()); 
		}
	}
	
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json != null && json.getString("status").equals("ok")) { 
				if (json.getString("requestID").equals("UserInfo")) {
					numRequests--; 
					GameManager.getInstance().setLoggedUser(true); 
					GameManager.getInstance().setUserCoins(json.getInt("moedas")); 
					GameManager.getInstance().setUserPowerUps(json.getInt("rodadas")); 
					GameManager.getInstance().setUserName(json.getString("nome"));
					GameManager.getInstance().setUserPictureURL(json.getString("foto"));
				}
				else if (json.getString("requestID").equals("MyGames")) {
					numRequests--;
					jsonMyGames = json;  
				}
				
				if (numRequests == 0) {
					createItensScene();
				}
			}
			else {  
				System.out.println("DEBUG - Erro ao recuperar dados do usuário");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_NEWGAME:
			SceneManager.getInstance().createNewGameScene();
			return true; 

		default:
			break;
		}
		return false;
		
	}
}
