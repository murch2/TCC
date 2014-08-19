/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.model;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;


//Essa classe não vai mais se chamar cell, vai ser o menu e talvez eu tenha que fazer outra cell. 
public class FriendPickerMenu {

	//Pode ser que não seja sprite
	
//	{
//		menuNewGame = new MenuScene(camera);
//		menuNewGame.setPosition(0, 0.15f * Constants.CAMERA_HEIGHT); 
//
//		final IMenuItem itemNewGame = new ScaleMenuItemDecorator(
//				new SpriteMenuItem(MENU_NEWGAME, resourcesManager.newGameMenuRegion, vbom), 0.8f, 1);
//		
//		menuNewGame.addMenuItem(itemNewGame);
//		menuNewGame.buildAnimations();
//		menuNewGame.setBackgroundEnabled(false);
//
//		//Poderia ter uma classe aqui que é responsavel por isso. (O click do botão de menu). 
//		menuNewGame.setOnMenuItemClickListener(this); 
//
//		setChildScene(menuNewGame);
//
//	}

//	public FriendPickerMenu(JSONArray jsonArray) {
//		super(ResourcesManager.getInstance().camera);
//		this.jsonFriendInfo = json;
//		try {
//			this.friendID = json.getString("uid");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		createBackground();
////		createPicture(); 
//		createNameText();
//	}
	
	//Aqui eu tenho que passar as informações 
//	public FriendPickerCell(JSONObject json) {
//		this.jsonFriendInfo = json;
//		try {
//			this.friendID = json.getString("uid");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} 
//		createBackground();
////		O create picture precisa do link pra atualizar
//		createPicture(); 
//		createNameText();
////		createFriendPicture(); 
//	}
	
	
	

	
}
