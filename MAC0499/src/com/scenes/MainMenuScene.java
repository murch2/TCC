/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
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

public class MainMenuScene extends BaseSceneWithHUD implements HTTPResponseListener, GraphUserCallback {

	//Talvez isso vá pra uma das classes singleton. 
	private FacebookFacade fb; 

	@Override
	public void createScene() {
		createBackground();
		createHUD(); 
		if (!GameManager.getInstance().isLoggedUser()) {
			fb = new FacebookFacade(); 
			fb.login(this); 
		} else {
			new HTTPPostRequester().asyncPost(this, MakeParameters.getUserInfo(GameManager.getInstance().getUserID()));
		}
	}

	private void createBackground() {
		setBackground(new Background(Color.BLUE));
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
		//acho que aqui que sempre será retirado o layer de loading.
		try {
			if (json.getString("status").equals("ok")) { 
				GameManager.getInstance().setLoggedUser(true); 
				GameManager.getInstance().setUserCoins(json.getInt("moedas")); 
				GameManager.getInstance().setUserPowerUps(json.getInt("rodadas")); 
				GameManager.getInstance().setUserName(json.getString("nome")); 
			}
			else { //Deu merda pra recuperar os dados do cara no banco de dados.  
				
			}
		} catch (JSONException e) {
			//Aqui tb deu merda pra recuperar os dados do cara no BD
			e.printStackTrace();
		}
	}
}
