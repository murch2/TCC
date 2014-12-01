/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.model;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.util.Constants;


public class TopHUD extends HUD { 
	private Text powerUps; 
	private Text coins;  
	
	public TopHUD() {
		this.setPosition(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2); 
		createPowerUps(); 
		createCoins(); 
	}
		
	private void createPowerUps() {
		String powerUp = "Especiais: " + GameManager.getInstance().getUserPowerUps(); 
		powerUps = new Text(- Constants.CAMERA_WIDTH * 0.48f, Constants.CAMERA_HEIGHT * 0.45f, ResourcesManager.getInstance().gameFont, powerUp, ResourcesManager.getInstance().vbom);
		powerUps.setScale(0.95f); 
		powerUps.setAnchorCenter(0.0f, 0.5f);
		attachChild(powerUps);
	}
	
	private void createCoins() {
		String coinsString = "Moedas: " + GameManager.getInstance().getUserCoins(); 
		coins = new Text(Constants.CAMERA_WIDTH * 0.48f, Constants.CAMERA_HEIGHT * 0.45f, ResourcesManager.getInstance().gameFont, coinsString, ResourcesManager.getInstance().vbom);
		coins.setScale(0.95f); 
		coins.setAnchorCenter(1.0f, 0.5f); 
		attachChild(coins);
	}
}
