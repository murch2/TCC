/**
 * @author Rodrigo Duarte Louro
 * @dateJul 22, 2014
 */
package com.scenes;

import org.andengine.engine.camera.hud.HUD;

import com.model.TopHUD;

public abstract class BaseSceneWithHUD extends BaseScene {
	
	//Do jeito que está feito agora toda cena vai ter um desse novo, o jeito de contornar isso seria ter 
//	um Header carregado num HUD e eu vou colocando e tirando das cenas.  
	public void createHUD() {
		HUD hud = new TopHUD(); 
		camera.setHUD(hud); 
	}	
}
