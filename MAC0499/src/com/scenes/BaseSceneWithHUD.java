/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.scenes;

import org.andengine.engine.camera.hud.HUD;

import com.model.TopHUD;

public abstract class BaseSceneWithHUD extends BaseScene {
	
	public void createHUD() {
		HUD hud = new TopHUD(); 
		camera.setHUD(hud);
	}
}
