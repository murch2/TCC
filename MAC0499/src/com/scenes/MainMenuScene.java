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
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.managers.GameManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.FacebookFacade;

public class MainMenuScene extends BaseSceneWithHUD implements HTTPResponseListener, GraphUserCallback, IOnMenuItemClickListener {

	//Talvez isso vá pra uma das classes singleton. 
	private FacebookFacade fb;
	private MenuScene menuNewGame; 
	private final int MENU_NEWGAME = 0;

	@Override
	public void createScene() {
		createBackground();  
		if (!GameManager.getInstance().isLoggedUser()) {
			fb = new FacebookFacade(); 
			fb.login(this); 
		} else {
			new HTTPPostRequester().asyncPost(this, MakeParameters.getUserInfo(GameManager.getInstance().getUserID()));
		}
	}
	
	private void createItensScene() {
		//Acho que soh no fim desse metodo o loading poderá sair da tela
		createHUD();
		createBtnNewGame(); 
	}

	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createBtnNewGame() {
		menuNewGame = new MenuScene(camera);
		menuNewGame.setPosition(0, 0.15f * Constants.CAMERA_HEIGHT); 

		final IMenuItem facebookConnectItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_NEWGAME, resourcesManager.newGameMenuRegion, vbom), 0.8f, 1);
		
		menuNewGame.addMenuItem(facebookConnectItem);
		menuNewGame.buildAnimations();
		menuNewGame.setBackgroundEnabled(false);

		//Poderia ter uma classe aqui que é responsavel por isso. (O click do botão de menu). 
		menuNewGame.setOnMenuItemClickListener(this); 

		setChildScene(menuNewGame);
	}

	//Aqui vou fazer um pop-up pra sair do jogo. 
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

	//Podia ter uma classe que cuidava disso, e só essa classe seria responsavel pelas respostas do facebook e soh ela implementaria 
	//a interface usergraphCallback. 
	@Override
	public void onCompleted(GraphUser user, Response response) {
		if (user != null) {
			GameManager.getInstance().setUserID(user.getId()); 
			new HTTPPostRequester().asyncPost(this, MakeParameters.getUserInfo(user.getId()));
			GameManager.getInstance().setLoggedUser(true);  
			GameManager.getInstance().setUserName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()); 
			
		}
	}
	
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json.getString("status").equals("ok")) { 
				GameManager.getInstance().setLoggedUser(true); 
				GameManager.getInstance().setUserCoins(json.getInt("moedas")); 
				GameManager.getInstance().setUserPowerUps(json.getInt("rodadas")); 
				GameManager.getInstance().setUserName(json.getString("nome")); 
				createItensScene(); 
			}
			else { //Deu merda pra recuperar os dados do cara no banco de dados.  
				
			}
		} catch (JSONException e) {
			//Aqui tb deu merda pra recuperar os dados do cara no BD
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
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
