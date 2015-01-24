/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.managers;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.scenes.BaseScene;
import com.scenes.ChoiceScene;
import com.scenes.ConnectScene;
import com.scenes.EndGameScene;
import com.scenes.FriendPickerScene;
import com.scenes.GameScene;
import com.scenes.MainMenuScene;
import com.scenes.NewGameScene;
import com.scenes.SplashScene;

public class SceneManager {
	
	private static final SceneManager INSTANCE = new SceneManager();
	private SceneType currentSceneType = SceneType.SPLASH_SCENE;
	private SceneType currentLastSceneType;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;
	
	private BaseScene splashScene; 
	private BaseScene connectScene; 
	private BaseScene mainMenuScene;
	private BaseScene newGameScene; 
	private BaseScene friendPickerScene;
	private BaseScene choiseScene;
	private BaseScene gameScene; 
	private BaseScene endGameScene; 
	
	
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
		GAME_SCENE, 
		ENDGAME_SCENE,
	}
	
	public void setScene(BaseScene scene) { 
		engine.setScene(scene);
		setCurrentLastSceneType(currentSceneType); 
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
			break; 
		case GAME_SCENE:
			setScene(gameScene);
		case ENDGAME_SCENE: 
			setScene(endGameScene); 
		default:
			break;
		}
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().camera.setHUD(null);
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

	 
//	TODO ver metodos dispose
	public void createConnectScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
	    ResourcesManager.getInstance().loadConnectScene(); 
	    connectScene = new ConnectScene(); 
	    setScene(connectScene);
	    disposeSplashScene();
	}
	
	public void createMainMenuScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
	    ResourcesManager.getInstance().loadMainMenuScene(); 
	    mainMenuScene = new MainMenuScene(); 
	    setScene(mainMenuScene); 
	}
	
	public void createNewGameScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
		ResourcesManager.getInstance().loadNewGameScene(); 
		newGameScene = new NewGameScene(); 
		setScene(newGameScene); 
	}
	
	public void createNewFriendPickerScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
		ResourcesManager.getInstance().loadFriendPickerScene();  
		friendPickerScene = new FriendPickerScene(); 
		setScene(friendPickerScene); 
	}
	
	public void createChoiceScene(boolean isNewGame) {
		ResourcesManager.getInstance().camera.setHUD(null);
		ResourcesManager.getInstance().loadChoiceScene();  
		choiseScene = new ChoiceScene(isNewGame); 
		setScene(choiseScene); 
	}
	
	public void createGameScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
		ResourcesManager.getInstance().loadGameScene();  
		gameScene = new GameScene(); 
		setScene(gameScene);
	}
	
	public void createEndGameScene() {
		ResourcesManager.getInstance().camera.setHUD(null);
		ResourcesManager.getInstance().loadEndGameScene();  
		endGameScene = new EndGameScene(); 
		setScene(endGameScene);
	}
	
	public BaseScene getCurrentScene() {
		return currentScene; 
	}

	public SceneType getCurrentLastSceneType() {
		return currentLastSceneType;
	}

	public void setCurrentLastSceneType(SceneType currentLastSceneType) {
		this.currentLastSceneType = currentLastSceneType;
	}
}
