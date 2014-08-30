/**
 * @author Rodrigo Duarte Louro
 * @dateAug 20, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;

public class ChoiceScene extends BaseSceneWithHUD implements HTTPResponseListener{

	@Override
	public void createScene() {
		//Aqui faz o Request Com as informações, antes eu tenho que setar as coisas do GameManager para fazer o request.
//		Por enquanto
		new HTTPPostRequester().asyncPost(this, MakeParameters.newGame());
		System.out.println("Fazendo a Requisição");
	}
	
	private void createItensScene () {
		createBackground(); 
	}

	private void createBackground() {
		setBackground(new Background(Color.PINK));
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
			//Aqui depois eu tenho que perguntar se o status eh ok pra poder continuar.
			if (json != null) {
				System.out.println("COCO = " + json.toString(4));
				createItensScene();
			}
			else 
				System.out.println("COCO FUDEU TUDO");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
