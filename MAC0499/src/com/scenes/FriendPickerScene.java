/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

import java.util.Map;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerCell;
import com.util.Constants;
import com.util.FacebookFacade;

public class FriendPickerScene extends BaseScene implements Callback {

	@Override
	public void createScene() {
		new FacebookFacade().getFriends(this);
	}
	
	private void createItensScene () {
		createBackground();
		makeCells(); 
	}
	
	private void createBackground() {
		setBackground(new Background(Color.RED));
	}
	
	private void makeCells() {
		//Aqui vai ter um for e receber um JSON com todos os amigos. 
		FriendPickerCell cell = new FriendPickerCell();
		//Essa posição vai mudando com o for
		cell.setPosition(Constants.CAMERA_WIDTH * 0.5f, Constants.CAMERA_HEIGHT * 0.5f); 
		attachChild(cell); 
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
		JSONObject json = graphObject.getInnerJSONObject() ;
		
		try {
			System.out.println(json.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createItensScene(); 
	}

}
