/**
 * @author Rodrigo Duarte Louro
 * @dateAug 31, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;

public class GameScene extends BaseScene implements HTTPResponseListener {

	@Override
	public void createScene() {
		new HTTPPostRequester().asyncPost(this, MakeParameters.randomCard());
	}

	private void createItensScene () {
		createBackground();
	}

	private void createBackground() {
		setBackground(new Background(Color.BLUE));
 
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.GAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void onResponse(JSONObject json) {
		try {
			System.out.println("RANDOM = " + json.toString(4));
			createItensScene(); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


}
