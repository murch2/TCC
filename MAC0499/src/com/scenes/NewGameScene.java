/**
 * @author Rodrigo Duarte Louro
 * @dateJul 30, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;

import com.managers.SceneManager.SceneType;

public class NewGameScene extends BaseSceneWithHUD {

	@Override
	public void createScene() {
		createBackground(); 
	}
	
	private void createBackground() {
		setBackground(new Background(Color.YELLOW));
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.NEWGAME_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}

}
