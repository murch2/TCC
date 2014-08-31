/**
 * @author Rodrigo Duarte Louro
 * @dateAug 20, 2014
 */
package com.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;

public class ChoiceScene extends BaseSceneWithHUD implements HTTPResponseListener, IOnSceneTouchListener{
	
	@Override
	public void createScene() {
		//Aqui faz o Request Com as informações, antes eu tenho que setar as coisas do GameManager para fazer o request.
//		Por enquanto
		new HTTPPostRequester().asyncPost(this, MakeParameters.newGame());
	}
	
	private void createItensScene (JSONArray dados) {
		createBackground();
		createRoullete(dados); 
		this.setOnSceneTouchListener(this); 
	}

	private void createBackground() {
		setBackground(new Background(Color.PINK));
	}

	private void createRoullete(JSONArray dados) {
		//Aqui vou fazer a animação pro cara poder escolher a porra de um tipo de carta. 
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.CHOISE_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub	
	}
	
	

//	Aqui devolve as informações, devo criar um array de um objeto que contem essas informações, tem que ter um if pra ver se deu tudo ok.  
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json != null) {
				JSONArray dados = json.getJSONArray("dados"); 
				createItensScene(dados);
			}
			else 
				System.out.println("COCO FUDEU TUDO");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		GameManager.getInstance().setCardID(1);
		SceneManager.getInstance().createGameScene();
		System.out.println("Toquei na ChoiceScene");
		return true; 
	}
	

}
