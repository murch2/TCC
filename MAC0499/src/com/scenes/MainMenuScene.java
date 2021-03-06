/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.GameItem;
import com.model.GameItem.Status;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.FacebookFacade;
import com.util.LoadingLayer;

public class MainMenuScene extends BaseSceneWithHUD implements
		HTTPResponseListener, GraphUserCallback, IOnMenuItemClickListener,
		IScrollDetectorListener, IOnSceneTouchListener {

	private IMenuItem newGameItem;

	private MenuScene menuMyGames;
	private final int GAME_ITEM = 1;
	private int numRequests;
	private JSONObject jsonMyGames;
	private SurfaceScrollDetector scrollDetector = null;
	private boolean swipe = false;
	private boolean shouldSwipe = false;

	@Override
	public void createScene() {
		createBackground();
		putLoading();
		if (!GameManager.getInstance().isLoggedUser()) {
			new FacebookFacade().login(this);
		} else {
			numRequests = 2;
			new HTTPPostRequester().asyncPost(this, MakeParameters
					.getUserInfo(GameManager.getInstance().getUserID()));
			new HTTPPostRequester().asyncPost(this, MakeParameters
					.myGames(GameManager.getInstance().getUserID()));
		}
		this.setOnSceneTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
	}

	private void createItensScene() {
		createnewGameItem();
		createMenuMyGames();
		createHUD();
	}

	public void putLoading() {
		LoadingLayer loading = new LoadingLayer();
		loading.insertLoadingLayer(camera);
	}

	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y,
				resourcesManager.blueBackground, vbom);
		attachChild(background);
	}

	private void createnewGameItem() {
		newGameItem = new ScaleMenuItemDecorator(new SpriteMenuItem(0,
				resourcesManager.newGameMenuRegion,
				ResourcesManager.getInstance().vbom), 0.8f, 1) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				newGameItem.setScale(0.8f);

				int action = pSceneTouchEvent.getAction();

				if (action == TouchEvent.ACTION_UP) {
					TimerHandler timer = new TimerHandler(0.15f,
							new ITimerCallback() {
								@Override
								public void onTimePassed(
										TimerHandler pTimerHandler) {
									newGameItem.setScale(1.0f);
									SceneManager.getInstance()
											.createNewGameScene();
								}
							});

					this.registerUpdateHandler(timer);
				}
				return true;
			}
		};
		newGameItem.setPosition(Constants.CENTER_X,
				Constants.CAMERA_HEIGHT * 0.7f);
		this.registerTouchArea(newGameItem);
		attachChild(newGameItem);

		Text newGameText = new Text(0, 0,
				ResourcesManager.getInstance().gameFont, "Novo Jogo",
				ResourcesManager.getInstance().vbom);
		newGameText.setPosition(newGameItem.getWidth() * 0.5f,
				newGameItem.getHeight() * 0.7f);
		newGameText.setColor(Color.WHITE);
		newGameText.setScale(1.2f);
		newGameItem.attachChild(newGameText);

	}

	private void createMenuMyGames() {
		JSONArray array;
		JSONObject json;
		IMenuItem item;

		try {
			array = jsonMyGames.getJSONArray("dados");
			menuMyGames = new MenuScene(ResourcesManager.getInstance().camera);
			menuMyGames.buildAnimations();

			for (int i = 0; i < array.length(); i++) {
				shouldSwipe = true;
				json = array.getJSONObject(i);
				GameItem gameItem = new GameItem(GAME_ITEM, json.getString("idOpponent"), json); 
				item = new ScaleMenuItemDecorator(gameItem, 0.8f, 1);
				item.setUserData(gameItem); 
				item.setPosition(Constants.CENTER_X, -i * 125
						+ Constants.CENTER_X + 200);
				menuMyGames.addMenuItem(item);
			}

			menuMyGames.setBackgroundEnabled(false);
			menuMyGames.setOnMenuItemClickListener(this);
			menuMyGames.setPosition(0, 0);

			setChildScene(menuMyGames);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.MAINMENU_SCENE;
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadMainMenuScene();
	}

	@Override
	public void onCompleted(GraphUser user, Response response) {
		if (user != null) {
			GameManager.getInstance().setUserID(user.getId());
			numRequests = 2;
			new HTTPPostRequester().asyncPost(this,
					MakeParameters.getUserInfo(user.getId()));
			new HTTPPostRequester().asyncPost(this, MakeParameters
					.myGames(GameManager.getInstance().getUserID()));
			GameManager.getInstance().setLoggedUser(true);
			GameManager.getInstance().setUserName(
					user.getFirstName() + " " + user.getMiddleName() + " "
							+ user.getLastName());
		}
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json != null && json.getString("status").equals("ok")) {
				if (json.getString("requestID").equals("UserInfo")) {
					numRequests--;
					GameManager.getInstance().setLoggedUser(true);
					GameManager.getInstance().setUserCoins(
							json.getInt("moedas"));
					GameManager.getInstance().setUserPowerUps(
							json.getInt("rodadas"));
					GameManager.getInstance().setUserName(
							json.getString("nome"));
					GameManager.getInstance().setUserPictureURL(
							json.getString("foto"));
				} else if (json.getString("requestID").equals("MyGames")) {
					numRequests--;
					jsonMyGames = json;
				}

				if (numRequests == 0) {
					createItensScene();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		GameItem item = (GameItem) pMenuItem.getUserData(); 

		if (item != null) {
			Status status = item.getStatus();
	
			if (status == Status.POKE) {
				return true; 
			}
			
			GameManager GM = GameManager.getInstance(); 
			
			GM.setFriendID(item.getFriendID()); 
			GM.setFriendName(item.getFriendName()); 
			GM.setFriendPictureURL(item.getFriendURLPicture()); 
			
			switch (pMenuItem.getID()) {
				case GAME_ITEM:
					SceneManager.getInstance().createChoiceScene(false);
					return true;
				default:
					break;
			}
		}
		
		return false;

	}

	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

		if (shouldSwipe) {
			int offset = 20;
			int n = menuMyGames.getChildCount();
			IEntity item = null;

			if (n > 0) {
				item = menuMyGames.getChildByIndex(0);
			}

			if (n > 4 && swipe) {
				float topLimit = (n - 4) * (item.getHeight() + 10);
				if (pDistanceY < 0) {
					if (getChildScene().getY() < topLimit) {
						newGameItem.setY(newGameItem.getY() + offset);
						getChildScene().setY(getChildScene().getY() + offset);
					}
				} else if (pDistanceY > 0) {
					if (getChildScene().getY() > 0) {
						newGameItem.setY(newGameItem.getY() - offset);
						getChildScene().setY(getChildScene().getY() - offset);
					}
				}
			}
		}
	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.scrollDetector == null) {
			this.scrollDetector = new SurfaceScrollDetector(this);
		}

		scrollDetector.onManagedTouchEvent(pSceneTouchEvent);

		int action = pSceneTouchEvent.getAction();

		switch (action) {
		case TouchEvent.ACTION_DOWN:
			swipe = true;
			break;
		case TouchEvent.ACTION_MOVE:
			swipe = true;
			break;
		case TouchEvent.ACTION_UP:
			swipe = false;
			break;

		}

		return true;
	}
}
