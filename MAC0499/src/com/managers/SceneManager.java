/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.managers.ResourcesManager;
import com.scenes.BaseScene;
import com.scenes.ChoiceScene;
import com.scenes.ConnectScene;
import com.scenes.FriendPickerScene;
import com.scenes.MainMenuScene;
import com.scenes.NewGameScene;
import com.scenes.SplashScene;

public class SceneManager {
	
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SPLASH_SCENE;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	private BaseScene splashScene; 
	private BaseScene connectScene; 
	private BaseScene mainMenuScene;
	private BaseScene newGameScene; 
	private BaseScene friendPickerScene;
	private BaseScene choiseScene; 
	
	public static SceneManager getInstance() {
		return INSTANCE; 
	}
	
	public enum SceneType {
		SPLASH_SCENE,
		CONNECT_SCENE, 
		MAINMENU_SCENE, 
		NEWGAME_SCENE, 
		FRIENDPICKER_SCENE, 
		CHOISE_SCENE, 
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
			break; 
		case MAINMENU_SCENE:
			setScene(mainMenuScene);
			break; 
		case NEWGAME_SCENE:
			setScene(newGameScene); 
			break; 
		case FRIENDPICKER_SCENE:
			setScene(friendPickerScene);
			break; 
		case CHOISE_SCENE:
			setScene(choiseScene);
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

	//suspeito muito que devo fazer metodos de dispose pra todas as outras cenas assim splashScene. 
	
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
	
	public void createNewGameScene() {
		ResourcesManager.getInstance().loadNewGameScene(); 
		newGameScene = new NewGameScene(); 
		setScene(newGameScene); 
	}
	
	public void createNewFriendPickerScene() {
		ResourcesManager.getInstance().loadFriendPickerScene();  
		friendPickerScene = new FriendPickerScene(); 
		setScene(friendPickerScene); 
	}
	
	public void createChoiceScene() {
		ResourcesManager.getInstance().loadChoiceScene();  
		choiseScene = new ChoiceScene(); 
		setScene(choiseScene); 
	}
	
	public BaseScene getCurrentScene() {
		return currentScene; 
	}
}
