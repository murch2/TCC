/**
 * @author Rodrigo Duarte Louro
 * @dateAug 31, 2014
 */
package com.scenes;

import org.andengine.engine.camera.Camera;
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

import android.view.MenuItem;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerItem;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;

public class GameScene extends BaseScene implements HTTPResponseListener, IOnMenuItemClickListener {
	
	private MenuScene tipsMenu = new MenuScene(ResourcesManager.getInstance().camera);
	private Requests currentRequest; 
	
	private enum Requests {
		RANDOM_CARD, 
		FINISH_NEWROUND
	}

	@Override
	public void createScene() {
		currentRequest = Requests.RANDOM_CARD; 
		new HTTPPostRequester().asyncPost(this, MakeParameters.randomCard());
	}

	private void createItensScene (JSONObject json) {
		createBackground();
		createTips(json); 
	}

	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createTips(JSONObject json){
		IMenuItem tipItem;
		JSONArray tipsArray;
		try {
//			GameManager.getInstance().setCardID(json.getJSONObject("dados").getInt("idCarta")); 
			tipsArray = json.getJSONObject("dados").getJSONArray("dicas");
			tipsMenu.setPosition(- Constants.CAMERA_WIDTH * 0.4f, 0);
			float x = 50; 
			for (int i = 0; i < 5; i++) {
				json = tipsArray.getJSONObject(i); 
				tipItem = new ScaleMenuItemDecorator(new SpriteMenuItem(i, resourcesManager.btnTipRegion, vbom), 0.8f, 1);
				tipItem.setUserData(json.getString("texto")); 
				tipsMenu.addMenuItem(tipItem);
				System.out.println("X depois de add = " + tipsMenu.getChildByIndex(i).getX());
			}
			
			tipsMenu.buildAnimations();
			tipsMenu.setBackgroundEnabled(false);
			tipsMenu.setOnMenuItemClickListener(this); 
			tipsMenu = setMenuLayoutToHorizontal(tipsMenu, 30);
			setChildScene(tipsMenu);
			
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
		return SceneType.GAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void onResponse(JSONObject json) {
		if (currentRequest == Requests.RANDOM_CARD)
			createItensScene(json);
		else if (currentRequest == Requests.FINISH_NEWROUND)
//			Aqui eu tenho que ir pra proxima cena, que nesse caso é o mainMenu.
			System.out.println("Retornou do finishNewRound");
	}

	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("Cliquei!"); 
		System.out.println("A dica: \n" + pMenuItem.getUserData());
		currentRequest = Requests.FINISH_NEWROUND; 
		new HTTPPostRequester().asyncPost(this, MakeParameters.finishNewRound(3459, false));
		return true;
	}

	private MenuScene setMenuLayoutToHorizontal(MenuScene menu, int padding){
	    if (menu.getChildCount()<=1) return menu;
	    System.out.println(menu.getChildByIndex(0).getX());
	    for(int i=1; i<menu.getChildCount();i++){
	    	System.out.println("Posicao = " + menu.getChildByIndex(i-1).getX());
	        menu.getChildByIndex(i).setPosition(
	                menu.getChildByIndex(i-1).getX()+menu.getChildByIndex(i).getWidth()+padding,
	                     menu.getChildByIndex(0).getY());
	    }
	    return menu;

	}
}
