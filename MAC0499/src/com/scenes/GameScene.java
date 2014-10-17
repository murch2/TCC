/**
 * @author Rodrigo Duarte Louro
 * @dateAug 31, 2014
 */
package com.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
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
		createPopup(); 
	}

//	Posso ficar trocando de scene com da popUp com a menu, ou tentar tirar a menu, posicionar os items diretamente 
//	e colocar essa scene por cima com opacidade 0.95.
	
	private void createPopup () {
		Scene s = new Scene(); 
		Sprite t = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.backgroundChoiceRegion, vbom); 
		t.setWidth(200); 
		t.setHeight(200);
		s.attachChild(t); 
		this.setChildSceneModal(s);
	}
	
	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createTips(JSONObject json){
		IMenuItem tipItem;
		JSONArray tipsArray;
		try {	
			tipsArray = json.getJSONObject("dados").getJSONArray("dicas");
			tipsMenu.setPosition(- Constants.CAMERA_WIDTH * 0.4f, 0);
			 
			for (int i = 0; i < 10; i++) {
				json = tipsArray.getJSONObject(i); 
				tipItem = new ScaleMenuItemDecorator(new SpriteMenuItem(i, resourcesManager.btnTipRegion[i], vbom), 0.8f, 1);
				tipItem.setUserData(json.getString("texto")); 
				tipsMenu.addMenuItem(tipItem);
				System.out.println("X depois de add = " + tipsMenu.getChildByIndex(i).getX());
			}
			
			tipsMenu.buildAnimations();
			tipsMenu.setBackgroundEnabled(false);
			tipsMenu.setOnMenuItemClickListener(this); 
			tipsMenu = setMenuLayoutToHorizontal(tipsMenu, 1);
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
			System.out.println("DEBUG - Retornou do finishNewRound devo ir para MainMenu");
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) { 
		currentRequest = Requests.FINISH_NEWROUND; 
		new HTTPPostRequester().asyncPost(this, MakeParameters.finishNewRound(3459, false));
		return true;
	}

	private MenuScene setMenuLayoutToHorizontal(MenuScene menu, int padding){
	    if (menu.getChildCount() <= 1) 
	    	return menu;
	  
	    menu.getChildByIndex(0).setPosition(menu.getChildByIndex(0).getX() + 10, 500);
	    
	    for (int i = 1; i < menu.getChildCount() / 2; i++) {
	        menu.getChildByIndex(i).setPosition(
	                menu.getChildByIndex(i-1).getX()+menu.getChildByIndex(i).getWidth()+padding,
	                     500);
	    }
	    
	    for (int i = menu.getChildCount() / 2; i < menu.getChildCount(); i++) {
	        menu.getChildByIndex(i).setPosition(
	                menu.getChildByIndex(i-5).getX()+padding,
	                     410);
	    }
	    
	    return menu;
	}
}
