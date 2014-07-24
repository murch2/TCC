/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.managers.ResourcesManager;
import com.scenes.BaseScene;
import com.scenes.ConnectScene;
import com.scenes.MainMenuScene;
import com.scenes.SplashScene;

public class SceneManager {
	
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SPLASH_SCENE;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	private BaseScene splashScene; 
	private BaseScene connectScene; 
	private BaseScene mainMenuScene; 
	
	public static SceneManager getInstance() {
		return INSTANCE; 
	}
	
	public enum SceneType {
		SPLASH_SCENE,
		CONNECT_SCENE, 
		MAINMENU_SCENE
	}
	
	//Talvez esse método possa ser private 
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SPLASH_SCENE:
			setScene(splashScene);
			break;
		case CONNECT_SCENE:
			setScene(connectScene);
		default:
			break;
		}
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScene(); 
		splashScene = new SplashScene(); 
		currentScene = splashScene; 
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene); 
	}
	
	private void disposeSplashScene() {
	    ResourcesManager.getInstance().unloadSplashScene();
	    splashScene.disposeScene();
	    splashScene = null;
	}

	public void createConnectScene() {
	    ResourcesManager.getInstance().loadConnectScene(); 
	    connectScene = new ConnectScene(); 
	    setScene(connectScene);
	    disposeSplashScene();
	}
	
	public void createMainMenuScene() {
	    ResourcesManager.getInstance().loadMainMenuScene(); 
	    mainMenuScene = new MainMenuScene(); 
	    setScene(mainMenuScene);
	    //Acho que aqui tem que ter um if se o cara vem da connectScene ai da um dispose nela. 
//	    disposeSplashScene(); 
	}
	
	
	
	public BaseScene getCurrentScene() {
		return currentScene; 
	}
}
