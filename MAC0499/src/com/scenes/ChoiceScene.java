/**
 * @author Rodrigo Duarte Louro
 * @dateAug 20, 2014
 */
package com.scenes;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.VersusCell;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.LoadingLayer;

public class ChoiceScene extends BaseSceneWithHUD implements
HTTPResponseListener, IOnMenuItemClickListener {

	private MenuScene bigPlayMenu;
	private MenuScene smallPlayMenu;
	
	private IMenuItem itemSmallPlay; 
	private IMenuItem itemRotate;
	
	private boolean playFlag;
	private boolean isNewGame; 

	private Text newGameText;
	private Sprite[] roullete; 
	private TimerHandler timer;

	private final int PLAY_BIG = 0;
	private final int PLAY_SMALL = 1;
	private final int ROTATE = 2;
	
	public ChoiceScene (boolean isNewGame) {
//		Aqui ele vai precisar fazer destinção sobre qual request está fazendo pra poder 
//		fazer o fluxo certo. 
		this.isNewGame = isNewGame; 
		if (isNewGame)
			new HTTPPostRequester().asyncPost(this, MakeParameters.newGame());
		else {
			new HTTPPostRequester().asyncPost(this, MakeParameters.playDesafio());
		}	
		putLoading();
	}
	
	public void createItensScene(JSONObject dados) {
		System.out.println("AQUI !!!!! - - - - - - - - ");
		createHUD();
		createCells(); 
		createBigPlayMenu();
	}

	@Override
	public void createScene() {
		createBackground(); 
	}
	
	public void putLoading() {
		LoadingLayer loading = new LoadingLayer();
		loading.insertLoadingLayer(camera); 
	}

	private void createItensSceneNewGame(JSONArray dados) {
		createRoullete(dados);
		createHUD();
		createCells(); 
		createNewGameText();
		createBigPlayMenu();
		createSmallPlayMenu();
	}

	private void createCells() {
		
		GameManager GM = GameManager.getInstance(); 
		
		System.out.println("UserPictureURL" + GM.getUserPictureURL());
		System.out.println("UserName" + GM.getUserName());
		
		System.out.println("FriendPictureURL" + GM.getFriendPictureURL());
		System.out.println("FriendName" + GM.getFriendName());
		
		VersusCell myCell = new VersusCell(GM.getUserPictureURL(), false, GM.getUserName());
		myCell.setPosition(myCell.getWidth() * 0.5f, Constants.CAMERA_HEIGHT * 0.8f);
		attachChild(myCell);

		VersusCell friendCell = new VersusCell(GM.getFriendPictureURL(), true, GM.getFriendName());
		friendCell.setPosition(Constants.CAMERA_WIDTH - friendCell.getWidth() * 0.5f, Constants.CAMERA_HEIGHT * 0.8f);
		attachChild(friendCell);
	}

	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.blueBackground, vbom); 
		attachChild(background);
	}

	private void createRoullete(JSONArray dados) {
		roullete = new Sprite[4]; 

		roullete[0] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resourcesManager.roulleteLeftTopRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[0].setAlpha(0.3f);

		roullete[1] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resourcesManager.roulleteRightTopRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[1].setScaleX(-1);
		roullete[1].setAlpha(0.3f); 

		roullete[2] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resourcesManager.roulleteRightBotRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[2].setScaleX(-1);
		roullete[2].setScaleY(-1);
		roullete[2].setAlpha(0.3f);

		roullete[3] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resourcesManager.roulleteLeftBotRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[3].setScaleY(-1);
		roullete[3].setAlpha(0.3f);
		roullete[3].setTag(3);
	}

	private void createNewGameText() {
		newGameText = new Text(0, 0, ResourcesManager.getInstance().gameFont,
				"NOVO \nJOGO!", new TextOptions(HorizontalAlign.CENTER), vbom);
		newGameText.setColor(255.0f/255, 239.0f/255, 191.0f/255);
		newGameText.setScale(2.0f);
		newGameText.setPosition(Constants.CENTER_X, Constants.CENTER_Y);
		attachChild(newGameText);
	}

	private void shuffleRoullete() {
		timer = new TimerHandler(0.1f, true, new ITimerCallback() {
			int count = 0;
			Random r = new Random(); 
			int max = r.nextInt(20) + 20;

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (count == max) {	
					count = count - 1; 
					engine.unregisterUpdateHandler(timer);
					playFlag = true; 
					itemSmallPlay.setAlpha(1.0f);
					GameManager.getInstance().setCardTypeID(count%4 + 1);
				} 
				else {
					if (count > 1) {
						roullete[(count-1)%4].setAlpha(0.3f);
					}
					roullete[count%4].setAlpha(1);
				}
				count++;
			}
		});
		engine.registerUpdateHandler(timer); 
	}

	private void createBigPlayMenu() {
		bigPlayMenu = new MenuScene(camera);
		bigPlayMenu.setPosition(0, -Constants.CAMERA_HEIGHT * 0.4f);

		final IMenuItem itemBigPlay = new ScaleMenuItemDecorator(
				new SpriteMenuItem(PLAY_BIG, resourcesManager.btnBlueRegion,
						vbom), 0.8f, 1);

		Text playText = new Text(0, 0, ResourcesManager.getInstance().gameFont,
				"PLAY", vbom);
		playText.setColor(Color.WHITE);
		playText.setScale(1.25f);
		playText.setPosition(itemBigPlay.getWidth() * 0.5f, itemBigPlay.getHeight() * 0.65f);
		itemBigPlay.attachChild(playText);
		
		bigPlayMenu.addMenuItem(itemBigPlay);
		bigPlayMenu.buildAnimations();
		bigPlayMenu.setBackgroundEnabled(false);

		bigPlayMenu.setOnMenuItemClickListener(this);

		setChildScene(bigPlayMenu);
	}

	private void createSmallPlayMenu() {
		smallPlayMenu = new MenuScene(camera);
		smallPlayMenu.setPosition(-Constants.CAMERA_WIDTH * 0.2f,
				-Constants.CAMERA_HEIGHT * 0.4f);

		itemSmallPlay = new ScaleMenuItemDecorator(
				new SpriteMenuItem(PLAY_SMALL,
						resourcesManager.btnBlueSmallRegion, vbom), 0.8f, 1);
		
		Text smallPlayText = new Text(0, 0, ResourcesManager.getInstance().arialFont,
				"Jogar", vbom);
		smallPlayText.setColor(Color.WHITE);
		smallPlayText.setScale(1.3f);
		smallPlayText.setPosition(itemSmallPlay.getWidth() * 0.5f, itemSmallPlay.getHeight() * 0.5f);
		itemSmallPlay.attachChild(smallPlayText);

		itemSmallPlay.setAlpha(0.2f);
		playFlag = false; 

		itemRotate = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ROTATE, resourcesManager.btnBlueSmallRegion,
						vbom), 0.8f, 1);

		Text rotateText = new Text(0, 0, ResourcesManager.getInstance().arialFont,
				"Girar", vbom);
		rotateText.setColor(Color.WHITE);
		rotateText.setScale(1.3f);
		rotateText.setPosition(itemRotate.getWidth() * 0.5f, itemRotate.getHeight() * 0.5f);
		itemRotate.attachChild(rotateText);
		
		smallPlayMenu.addMenuItem(itemRotate);
		smallPlayMenu.addMenuItem(itemSmallPlay);

		smallPlayMenu.buildAnimations();
		smallPlayMenu.setBackgroundEnabled(false);
		smallPlayMenu.setOnMenuItemClickListener(this);
		smallPlayMenu = setMenuLayoutToHorizontal(smallPlayMenu, 20);
	}

	private void touchBigPlay() {
		clearChildScene();
		setChildScene(smallPlayMenu);
		newGameText.detachSelf();
		attachRoullete();
	}

	private void attachRoullete() {
		for (int i = 0; i < roullete.length; i++) {
			attachChild(roullete[i]);
		}
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
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadMainMenuScene(); 
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			System.out.println(json.toString(4));

			
			if (isNewGame) {
				JSONArray dados = json.getJSONArray("dados");
				createItensSceneNewGame(dados);
			}

			else {
				JSONObject dados = json.getJSONObject("dados");
				createItensScene(dados); 
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {

		switch (pMenuItem.getID()) {

		case PLAY_BIG:
			touchBigPlay();
			return true;
		case PLAY_SMALL:
			if (playFlag) {
				SceneManager.getInstance().createGameScene(); 
			} 
			return true;
		case ROTATE:
			shuffleRoullete();
			return true;

		default:
			break;
		}

		return false;
	}

	private MenuScene setMenuLayoutToHorizontal(MenuScene menu, int padding) {
		if (menu.getChildCount() <= 1)
			return menu;

		for (int i = 1; i < menu.getChildCount(); i++) {
			menu.getChildByIndex(i).setPosition(menu.getChildByIndex(i - 1).getX()
					+ menu.getChildByIndex(i).getWidth() + padding, menu.getChildByIndex(0).getY());
		}
		return menu;
	}
}
