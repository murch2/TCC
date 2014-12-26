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
import org.andengine.opengl.util.GLState;
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

public class ChoiceScene extends BaseSceneWithHUD implements
HTTPResponseListener, IOnMenuItemClickListener {

	private MenuScene bigPlayMenu;
	private MenuScene smallPlayMenu;
	
	private IMenuItem itemSmallPlay; 
	private IMenuItem itemRotate;
	
	private boolean playFlag; 

	private Text newGameText;
	private Sprite[] roullete; 
	private TimerHandler timer;

	private final int PLAY_BIG = 0;
	private final int PLAY_SMALL = 1;
	private final int ROTATE = 2;

	@Override
	public void createScene() {
		new HTTPPostRequester().asyncPost(this, MakeParameters.newGame());
	}

	private void createItensScene(JSONArray dados) {
		createBackground();
//TODO	Se eu num vou escrever o que eh cada cor eu não preciso dos dados.
		createRoullete(dados);
		createHUD();
		createCells();
		if (SceneManager.getInstance().getCurrentLastSceneType()
				.equals(SceneType.NEWGAME_SCENE)
				|| SceneManager.getInstance().getCurrentLastSceneType()
				.equals(SceneType.FRIENDPICKER_SCENE)) {
			createNewGameText();
		} else {
			// TODO pensar na lógica de como mostrar o placar atual para o usuário.
		}
		createBigPlayMenu();
		createSmallPlayMenu();
	}

	private void createCells() {
		VersusCell myCell = new VersusCell(GameManager.getInstance()
				.getUserPictureURL(), false, GameManager.getInstance()
				.getUserName());
		myCell.setPosition(myCell.getWidth() * 0.5f,
				Constants.CAMERA_HEIGHT * 0.8f);
		attachChild(myCell);

		VersusCell friendCell = new VersusCell(GameManager.getInstance()
				.getFriendPictureURL(), true, GameManager.getInstance()
				.getFriendName());
		friendCell.setPosition(Constants.CAMERA_WIDTH - friendCell.getWidth()
				* 0.5f, Constants.CAMERA_HEIGHT * 0.8f);
		attachChild(friendCell);
	}

	private void createBackground() {
		Sprite background = new Sprite(Constants.CENTER_X, Constants.CENTER_Y, resourcesManager.blueBackground, vbom); 
		attachChild(background);
	}

	private void createRoullete(JSONArray dados) {
		roullete = new Sprite[4]; 

		ResourcesManager resources = ResourcesManager.getInstance();

		roullete[0] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resources.roulleteLeftTopRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[0].setAlpha(0.3f);

		roullete[1] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resources.roulleteRightTopRegion, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		roullete[1].setScaleX(-1);
		roullete[1].setAlpha(0.3f); 

		roullete[2] = new Sprite(Constants.CENTER_X,
				Constants.CENTER_Y - 40, resources.roulleteRightBotRegion, vbom) {
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
				Constants.CENTER_Y - 40, resources.roulleteLeftBotRegion, vbom) {
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
				"NEW GAME!", vbom);
		newGameText.setColor(Color.BLACK);
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
				new SpriteMenuItem(PLAY_BIG, resourcesManager.btnPlayBigRegion,
						vbom), 0.8f, 1);

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
						resourcesManager.btnPlaySmallRegion, vbom), 0.8f, 1);

		itemSmallPlay.setAlpha(0.2f);
		playFlag = false; 

		itemRotate = new ScaleMenuItemDecorator(
				new SpriteMenuItem(ROTATE, resourcesManager.btnRotateRegion,
						vbom), 0.8f, 1);

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
			JSONArray dados = json.getJSONArray("dados");
			createItensScene(dados);
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
