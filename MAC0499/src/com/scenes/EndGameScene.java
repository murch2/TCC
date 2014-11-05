/**
 * @author Rodrigo Duarte Louro
 * @dateOct 31, 2014
 */
package com.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.MenuItem;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.ImageDownloader;

public class EndGameScene extends BaseScene implements HTTPResponseListener {
	
	private Sprite picture; 
	private Text titleText; 
	private Text nameText;
	private Text scoreText;
	private IMenuItem btnNext; 
	
//	Tenho que passar se o cara perdeu ou ganhou e a foto do cara no gameManager. 
	@Override
	public void createScene() {
//		Aqui eu preciso ver se eh new round mesmo. 
		new HTTPPostRequester().asyncPost(this, MakeParameters.finishNewRound()); 
	}
	
	private void createItensScene() {
		createBackground();
		createTitle(); 
		createPicture();
		createName();
		createScore(); 
		createNextButton(); 
	}
	
	private void createBackground() {
		setBackground(new Background(Color.CYAN));
	}
	
	private void createTitle() {
		String text; 
		
		if (GameManager.getInstance().isWin())
			text = "Você Acertou!"; 
		else 
			text = "Você Errou!"; 
		
		titleText = new Text(0, 0, ResourcesManager.getInstance().font, text, new TextOptions(HorizontalAlign.CENTER), vbom);
		titleText.setColor(Color.WHITE);
		titleText.setAnchorCenter(0.5f, 0.5f);
		titleText.setPosition(Constants.CENTER_X, Constants.CAMERA_HEIGHT * 0.8f);
		attachChild(titleText); 
	}
	
	private void createPicture() {
		picture = new Sprite(0, 0, ResourcesManager.getInstance().defaultPictureRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		picture.setWidth(300);
		picture.setHeight(300); 
		
		picture.setPosition(Constants.CENTER_X, Constants.CENTER_Y);
		attachChild(picture);
		
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				Sprite updatePicture = ImageDownloader.testeImage(GameManager.getInstance().getCardPictureURL());
				updatePicture.setWidth(300);
				updatePicture.setHeight(300); 
				updatePicture.setPosition(picture); 
				detachChild(picture);
				picture = updatePicture; 
				attachChild(picture); 
			}
		}); 
	}
	
	private void createName() {
		nameText = new Text(0, 0, ResourcesManager.getInstance().font, GameManager.getInstance().getCardName(), new TextOptions(HorizontalAlign.CENTER), vbom);
		nameText.setColor(Color.WHITE);
		nameText.setAnchorCenter(0.5f, 0.5f);
		nameText.setPosition(picture.getX(), picture.getY() - picture.getHeight() * 0.5f - 25);
		attachChild(nameText);
	}
	
	public void createScore() {
		String score = "Pontuação: "; 
		if (GameManager.getInstance().isWin())
			score = score + GameManager.getInstance().getMyScore(); 
		else 
			score = score + "0"; 
		
		scoreText = new Text(0, 0, ResourcesManager.getInstance().font, score, new TextOptions(HorizontalAlign.CENTER), vbom);
		scoreText.setColor(Color.WHITE);
		scoreText.setAnchorCenter(0.5f, 0.5f);
		scoreText.setPosition(nameText.getX(), nameText.getY() - nameText.getHeight() * 0.5f - 25);
		attachChild(scoreText);
	}
	
	public void createNextButton() {
		btnNext = new ScaleMenuItemDecorator(new SpriteMenuItem(0, ResourcesManager.getInstance().btnNextRegion,
				ResourcesManager.getInstance().vbom), 0.8f, 1){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				btnNext.setScale(0.8f);

				TimerHandler timer = new TimerHandler(0.15f, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						btnNext.setScale(1.0f);
						SceneManager.getInstance().createMainMenuScene(); 
					}
				});

				this.registerUpdateHandler(timer); 
				return true; 
			}
		};
		btnNext.setAnchorCenter(1.0f, 0.0f); 
		btnNext.setPosition(Constants.CAMERA_WIDTH - 30, 50);
		this.registerTouchArea(btnNext);
		attachChild(btnNext);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.ENDGAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		this.detachSelf();
		this.dispose();
		ResourcesManager.getInstance().unloadChoiceScene();  
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json.getString("status").equals("ok")) 
				createItensScene();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}

}
