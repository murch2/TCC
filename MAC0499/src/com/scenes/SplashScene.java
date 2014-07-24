package com.scenes;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import com.managers.SceneManager.SceneType;
import com.util.Constants;

public class SplashScene extends BaseScene {	

	private Sprite splash; 

	@Override
	public void createScene() {
		splash = new Sprite(Constants.CAMERA_WIDTH / 2 ,Constants.CAMERA_HEIGHT / 2, resourcesManager.splashRegion, vbom) {
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
    	};
    	attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
	
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SPLASH_SCENE;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf(); 
		splash.dispose(); 
		this.detachSelf(); 
		this.dispose(); 
	}
}
