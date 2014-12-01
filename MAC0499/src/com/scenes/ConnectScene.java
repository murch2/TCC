/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.FacebookFacade;

public class ConnectScene extends BaseScene implements IOnMenuItemClickListener, GraphUserCallback, HTTPResponseListener {

	private MenuScene facebookMenu; 
	private final int MENU_FACEBOOK_CONNECT = 0;
	private FacebookFacade fb;  
	
	@Override
	public void createScene() { 
		fb = new FacebookFacade();
		createBackground();
		createWelcomeMessage(); 
		createFacebookConnectMenu();		
	}
	
	@Override
	public void onBackKeyPressed() {
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.CONNECT_SCENE; 
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadConnectScene(); 
	}
 
	private void createBackground() {
		setBackground(new Background(Color.BLACK));
	}

	private void createFacebookConnectMenu() {
		facebookMenu = new MenuScene(camera);
		facebookMenu.setPosition(0, 0); 

		final IMenuItem facebookConnectItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_FACEBOOK_CONNECT, resourcesManager.facebookConnectRegion, vbom), 0.8f, 1);
	
		createFacebookConnectText(facebookConnectItem); 
		
		facebookMenu.addMenuItem(facebookConnectItem);
		facebookMenu.buildAnimations();
		facebookMenu.setBackgroundEnabled(false);
 
		facebookMenu.setOnMenuItemClickListener(this); 

		setChildScene(facebookMenu);
	}
	
	private void createFacebookConnectText(IMenuItem item) {
		Text text = new Text(0, 0, resourcesManager.gameFont, "Login with Facebook", vbom);
		text.setPosition(item.getWidth() * 0.5f, item.getHeight() * 0.5f);  
		text.setColor(Color.WHITE); 
		item.attachChild(text); 
	}
	
	private void createWelcomeMessage() {
		Text text = new Text(0, 0, resourcesManager.gameFont, "Welcome!\n Connect with Facebook\n and gain bonus! ", vbom);
		text.setPosition(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT * 0.8f);
		text.setColor(Color.WHITE);
		attachChild(text);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_FACEBOOK_CONNECT:
			fb.login(this); 
			return true; 

		default:
			break;
		}
		return false;
	}


	@Override
	public void onCompleted(GraphUser user, Response response) {
		if (user != null) {
			new HTTPPostRequester().asyncPost(this, MakeParameters.signUpParams(user));
			GameManager.getInstance().setUserName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()); 
			GameManager.getInstance().setUserID(user.getId());
			fb.getUserPicture(); 
		}
	}
	
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json.getString("status").equals("ok")) { 
				GameManager.getInstance().setLoggedUser(true); 
				GameManager.getInstance().getDataInMemory().saveData(Constants.FACEBOOK_LOGIN, true);
				SceneManager.getInstance().createMainMenuScene(); 
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
