/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;

import com.facebook.Request.Callback;
import com.facebook.Response;
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
		//Aqui vai ter um for e receber um JSON. 
		FriendPickerCell cell = new FriendPickerCell();
		//Essa pposição vai mudando com o for
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
		System.out.println("Amigos que chegaram na FriendPickerScene = " + response);
		createItensScene(); 
	}

}
