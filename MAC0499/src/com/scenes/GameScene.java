/**
 * @author Rodrigo Duarte Louro
 * @dateAug 31, 2014
 */
package com.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.TipLayer;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;

public class GameScene extends BaseScene implements HTTPResponseListener, IOnMenuItemClickListener {
	
	private MenuScene tipsMenu = new MenuScene(ResourcesManager.getInstance().camera);
	private Sprite title; 
	private Requests currentRequest; 
	private TipLayer tipLayer;
	private String cardName; 
	private String cardPictureURL;
	private Text myScoreText;
	private boolean[] tipsRead; 
	private IMenuItem answerItem; 
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
			GameManager.getInstance().setCardName(cardName); 
			cardPictureURL = json.getJSONObject("dados").getString("foto"); 
			GameManager.getInstance().setCardPictureURL(cardPictureURL); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GameManager.getInstance().setMyScore(Constants.INITIAL_SCORE);
		createBackground();
		createTitle(); 
		createTips(json);  
		createMyScoreText();
		
	}
	
	public void createTitle() {
		title = new Sprite(Constants.CENTER_X, Constants.CAMERA_HEIGHT * 0.75f, resourcesManager.gameTitleRegion, vbom); 
		attachChild(title);
	}
	
	public void createTextDialog() {
		answerItem = new ScaleMenuItemDecorator(new SpriteMenuItem(0, ResourcesManager.getInstance().btnAnswerRegion,
				ResourcesManager.getInstance().vbom), 0.8f, 1){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) { 
					answerItem.setScale(0.8f);
					TimerHandler timer = new TimerHandler(0.15f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							answerItem.setScale(1.0f);
							ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									final EditText nameEditText = new EditText(ResourcesManager.getInstance().activity); 

									AlertDialog.Builder builder = new AlertDialog.Builder(ResourcesManager.getInstance().activity); 
									builder.setTitle("Quem é?");
									builder.setMessage("Digite o nome da personalidade");
									builder.setView(nameEditText);

									builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
									}); 

									builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
										
									});

									final AlertDialog alert = builder.create();
									alert.show(); 
								}
							});
						}
					});

					this.registerUpdateHandler(timer); 
				}
				return true;
			}
		};
		answerItem.setAnchorCenter(1.0f, 0.0f); 
		answerItem.setPosition(200, 200);
		registerTouchArea(answerItem); 
		attachChild(answerItem);

	}
	
	private void createTipLayer(String tipString) {
		tipsMenu.setUserData(tipsMenu.getOnMenuItemClickListener());
		tipsMenu.setOnMenuItemClickListener(null);
		final Scene s = this; 
		
		tipLayer = new TipLayer(cardName){
			@Override
			public void onDetached() {
				unregister(s); 

				if (tipLayer.tryToAnswer) {
					tryToAnswer = true; 
					if (tipLayer.answerString != null && tipLayer.answerString.equals(cardName)) {
						GameManager.getInstance().setWin(true); 
					} else {
						GameManager.getInstance().setWin(false); 
					} 
				} 
				else {
				}
				tipsMenu.setOnMenuItemClickListener((IOnMenuItemClickListener) tipsMenu.getUserData());
				if (tipLayer.tryToAnswer)
					SceneManager.getInstance().createEndGameScene();
				super.onDetached();
			}
		};
		tipLayer.registerMenu(this);
		tipLayer.setTipText(tipString); 
		tipsMenu.attachChild(tipLayer);
	}
	
	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.blueBackground, vbom); 
		attachChild(background);
	}
	
	private void createTips(JSONObject json){
		IMenuItem tipItem;
		JSONArray tipsArray;
		try {	
			tipsArray = json.getJSONObject("dados").getJSONArray("dicas");
			tipsMenu.setPosition(- Constants.CAMERA_WIDTH * 0.4f, 0);
			tipsRead = new boolean[10]; 
			
			for (int i = 0; i < 10; i++) {
				tipsRead[i] = false; 
				json = tipsArray.getJSONObject(i); 
				tipItem = new ScaleMenuItemDecorator(new SpriteMenuItem(i, resourcesManager.btnTipRegion[i], vbom), 0.8f, 1);
				tipItem.setUserData(json.getString("texto")); 
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
	
	private void createMyScoreText() { 		
		myScoreText = new Text(0, 0, ResourcesManager.getInstance().gameFont, "Pontos: " + String.valueOf(GameManager.getInstance().getMyScore()), 
				new TextOptions(HorizontalAlign.LEFT), 
				ResourcesManager.getInstance().vbom);
		myScoreText.setColor(Color.WHITE);
		myScoreText.setScale(1.5f); 
		myScoreText.setAnchorCenter(0.5f, 0.5f);
		myScoreText.setPosition(Constants.CAMERA_WIDTH * 0.5f, Constants.CAMERA_HEIGHT * 0.1f);
		attachChild(myScoreText); 
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
		camera.setHUD(null); 
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadGameScene();  
	}
 
	@Override
	public void onResponse(JSONObject json) {
		if (currentRequest == Requests.RANDOM_CARD)
			createItensScene(json);
		else if (currentRequest == Requests.FINISH_NEWROUND)
			;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) { 
		
		int id = pMenuItem.getID();
		if (tipsRead[id] == false) {
			GameManager.getInstance().setMyScore(GameManager.getInstance().getMyScore() - Constants.SCORE_LOSE_BY_TIP);
			myScoreText.setText("Pontos: " + String.valueOf(GameManager.getInstance().getMyScore())); 
			tipsRead[id] = true; 
		}
		
		createTipLayer(pMenuItem.getUserData().toString()); 
//		currentRequest = Requests.FINISH_NEWROUND; 
//		new HTTPPostRequester().asyncPost(this, MakeParameters.finishNewRound(3459, false));
		return true;
	}

	private MenuScene setMenuLayoutToHorizontal(MenuScene menu, int padding){
	    if (menu.getChildCount() <= 1) 
	    	return menu;
	  
	    menu.getChildByIndex(0).setPosition(menu.getChildByIndex(0).getX() + 10, 400);
	    
	    for (int i = 1; i < menu.getChildCount() / 2; i++) {
	        menu.getChildByIndex(i).setPosition(
	                menu.getChildByIndex(i-1).getX()+menu.getChildByIndex(i).getWidth()+padding,
	                     400);
	    }
	    
	    for (int i = menu.getChildCount() / 2; i < menu.getChildCount(); i++) {
	        menu.getChildByIndex(i).setPosition(
	                menu.getChildByIndex(i-5).getX()+padding,
	                     310);
	    }
	    
	    return menu;
	}
}
