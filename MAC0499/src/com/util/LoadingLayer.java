/**
 * @author Rodrigo Duarte Louro
 * @dateSep 25, 2014
 */
package com.util;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;

import com.managers.ResourcesManager;

public class LoadingLayer extends HUD {
	
	private Sprite loadingBackground;  
	private Sprite loadingIcon;  
	
	public LoadingLayer() {
		createBackground();
		createLoadingIcon();
		createLoadingRotation(); 
	}
	
	private void createBackground() {
		ResourcesManager resources = ResourcesManager.getInstance();
		loadingBackground = new Sprite(Constants.CAMERA_WIDTH * 0.5f, Constants.CAMERA_HEIGHT * 0.5f,
				resources.loadingRegion, resources.vbom); 
		loadingBackground.setScaleX(Constants.CAMERA_WIDTH / loadingBackground.getWidth() + 5f);
		loadingBackground.setScaleY(Constants.CAMERA_HEIGHT / loadingBackground.getHeight() + 5f);
		loadingBackground.setColor(0.0f, 0.0f, 0.0f, 0.8f); 
		
		attachChild(loadingBackground); 
	}
	
	private void createLoadingIcon() {
		ResourcesManager resources = ResourcesManager.getInstance();
		loadingIcon = new Sprite(Constants.CAMERA_WIDTH * 0.5f, Constants.CAMERA_HEIGHT * 0.5f,
				resources.loadingIconRegion, resources.vbom);
		
		loadingIcon.setPosition(Constants.CAMERA_WIDTH * 0.5f, Constants.CAMERA_HEIGHT * 0.5f);
		attachChild(loadingIcon);
	}
	
	private void createLoadingRotation() {
		LoopEntityModifier rotationLoop = new LoopEntityModifier(new RotationModifier(1.0f, 0, 360));
		loadingIcon.registerEntityModifier(rotationLoop); 
	}
	
	public void insertLoadingLayer(Camera camera) {
		camera.setHUD(this); 
	}
	
}
