package com.activities;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.facebook.Session;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.util.Constants;

public class GameActivity extends BaseGameActivity {
	
	//Talvez a camera e a engine Tenham que ir para o ResourcesManager. 
	private Camera camera; 
	private ResourcesManager resourcesManager; 
 
	
//	(Método para o facebook funcionar)
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	System.out.println("BUCETA 6");
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		System.out.println("BUCETA 5");
	    camera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR, 
	    		new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}


	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
		System.out.println("BUCETA 4");
		ResourcesManager.prepareResourcesManager(mEngine, this, camera, getVertexBufferObjectManager());
		resourcesManager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	//Primeira cena criada
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		System.out.println("BUCETA 3");
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		System.out.println("BUCETA 2");
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	
	                mEngine.unregisterUpdateHandler(pTimerHandler);
	                //Tenho que confirmar se a primeira vez deve começar com falso. 
	                if (GameManager.getInstance().getDataInMemory().alreadyLogedInFacebook()) {
	                	SceneManager.getInstance().createMainMenuScene();
	                }
//	                Talvez aqui eu tenha que fazer mais alguma verificação pra ver se eu consigo pegar os dados do usuario 
//	                pra confirmar se ele está mesmo logado. 
	                else {
	                	SceneManager.getInstance().createConnectScene();
	                }
	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		System.out.println("BUCETA 1");
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override 
	protected void onDestroy() {
		super.onDestroy(); 
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {  
//	    if (keyCode == KeyEvent.KEYCODE_BACK) {
//	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
//	    }
//	    return false; 
//	}

}
