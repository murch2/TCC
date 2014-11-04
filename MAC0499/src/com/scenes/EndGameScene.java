/**
 * @author Rodrigo Duarte Louro
 * @dateOct 31, 2014
 */
package com.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.GameManager;
import com.managers.ResourcesManager;
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
