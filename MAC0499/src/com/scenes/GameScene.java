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
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.MenuItem;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager.SceneType;
import com.model.FriendPickerItem;
import com.model.TipLayer;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;

public class GameScene extends BaseScene implements HTTPResponseListener, IOnMenuItemClickListener {
	
	private MenuScene tipsMenu = new MenuScene(ResourcesManager.getInstance().camera);
	private Requests currentRequest; 
	private TipLayer tipLayer;
	private String cardName; 
	
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
		try {
			cardName = json.getJSONObject("dados").getString("nomeCarta");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		createBackground();
		createTips(json);  
	}
	
	private void createTipLayer(String tipString) {
		tipsMenu.setUserData(tipsMenu.getOnMenuItemClickListener());
		tipsMenu.setOnMenuItemClickListener(null); 
		tipLayer = new TipLayer(cardName){
			@Override
			public void onDetached() {
				if (tipLayer.tryToAnswer) {
//					Fazer feedback de certo ou errado aqui
					if (tipLayer.answerString != null && tipLayer.answerString.equals(cardName)) {
						System.out.println("YOU ROCK!!!");
//						Fazer um layer Com a foto do cara. escrito que vc venceu e bla bla bla
					} else {
						
					}
				} 
				else {
//					Acho que aqui num precisa fazer nada. 
				}
				tipsMenu.setOnMenuItemClickListener((IOnMenuItemClickListener) tipsMenu.getUserData());
				super.onDetached();
			}
		};
		tipLayer.registerMenu(this);
		tipLayer.setTipText(tipString); 
		tipsMenu.attachChild(tipLayer);
	}
	
	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createTips(JSONObject json){
		try {
			System.out.println("PORRA = " + json.toString(4));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		IMenuItem tipItem;
		JSONArray tipsArray;
		try {	
			tipsArray = json.getJSONObject("dados").getJSONArray("dicas");
			tipsMenu.setPosition(- Constants.CAMERA_WIDTH * 0.4f, 0);
			 
			for (int i = 0; i < 10; i++) {
				json = tipsArray.getJSONObject(i); 
				tipItem = new ScaleMenuItemDecorator(new SpriteMenuItem(i, resourcesManager.btnTipRegion[i], vbom), 0.8f, 1);
				tipItem.setUserData(json.getString("texto")); 
//				dados -> nomeCarta
				tipsMenu.addMenuItem(tipItem);
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
		
		createTipLayer(pMenuItem.getUserData().toString()); 
//		currentRequest = Requests.FINISH_NEWROUND; 
//		new HTTPPostRequester().asyncPost(this, MakeParameters.finishNewRound(3459, false));
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
