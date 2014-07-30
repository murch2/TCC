/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.model;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.facebook.Session;
import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.util.Constants;

//Por algum motivo desconhecido a origem do HUD não é no canto inferior esquerdo e sim no meio. 

public class TopHUD extends HUD implements IOnMenuItemClickListener {

	private Sprite backGround; 
	private MenuScene profileMenu; 
	private Text powerUps; 
	private Text coins; 
	private final int MENU_PROFILE = 0; 
	
	
	public TopHUD() {
		this.setPosition(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2); 
		createBackGround(); 
		createMenuProfile();
		createPowerUps(); 
		createCoins(); 
	}
	
	private void createBackGround() {
		backGround = new Sprite(0, Constants.CAMERA_HEIGHT * 0.45f,
				ResourcesManager.getInstance().headerBackgroundRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};	
		this.attachChild(backGround); 
	}
	
	private void createMenuProfile() {
		profileMenu = new MenuScene(ResourcesManager.getInstance().camera); 
		final IMenuItem profileItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PROFILE, ResourcesManager.getInstance().headerProfileRegion, ResourcesManager.getInstance().vbom), 0.8f, 1);
		
		createProfileText(profileItem); 
		
		profileMenu.addMenuItem(profileItem);
		profileMenu.buildAnimations();
		profileMenu.setBackgroundEnabled(false);
		profileMenu.setOnMenuItemClickListener(this);  
		profileMenu.setPosition(profileItem.getWidth() / 2 - Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT * 0.45f); 

		this.setChildScene(profileMenu); 
	}
	
	private void createProfileText(IMenuItem item) {
		
	}
	
	private void createPowerUps() {
		String powerUp = "PowerUps: " + GameManager.getInstance().getUserPowerUps(); 
		powerUps = new Text(0, 0, ResourcesManager.getInstance().font, powerUp, ResourcesManager.getInstance().vbom);
		powerUps.setPosition(this.backGround.getWidth() * 0.41f, this.backGround.getHeight() * 0.5f);  
		powerUps.setColor(Color.WHITE); 
		backGround.attachChild(powerUps);
	}
	
	private void createCoins() {
		String coinsString = "Coins: " + GameManager.getInstance().getUserCoins(); 
		coins = new Text(0, 0, ResourcesManager.getInstance().font, coinsString, ResourcesManager.getInstance().vbom);
		coins.setPosition(this.backGround.getWidth() * 0.8f, this.backGround.getHeight() * 0.5f);  
		coins.setColor(Color.WHITE); 
		backGround.attachChild(coins);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_PROFILE:
			return true; 

		default:
			break;
		}
		return false; 
	}
}
