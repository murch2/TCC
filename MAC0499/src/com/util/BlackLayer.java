/**
 * @author Rodrigo Duarte Louro
 * @dateSep 25, 2014
 */
package com.util;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

import android.transition.Scene;

import com.managers.ResourcesManager;
import com.scenes.BaseSceneWithHUD;

public class BlackLayer extends HUD {
	public BlackLayer() { 
		Rectangle rect = new Rectangle(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, 
				ResourcesManager.getInstance().vbom){
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, 
					final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionUp() && pTouchAreaLocalX < this.getWidth() && pTouchAreaLocalX > 0 && pTouchAreaLocalY < this.getHeight() && pTouchAreaLocalY > 0) {
					System.out.println("Toquei na merda do layer");
				}
				
				return true;
			}
		};
		
		rect.setColor(0f,0f,0f, 0.85f); 
		this.attachChild(rect); 
		this.registerTouchArea(rect); 
		this.setPosition(Constants.CAMERA_WIDTH * 0.5f,Constants.CAMERA_HEIGHT * 0.5f);
	}
	
	private void createBackGround() {
		Rectangle rect = new Rectangle(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, 
				ResourcesManager.getInstance().vbom){
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, 
					final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.isActionUp() && pTouchAreaLocalX < this.getWidth() && pTouchAreaLocalX > 0 && pTouchAreaLocalY < this.getHeight() && pTouchAreaLocalY > 0) {
					System.out.println("Toquei na merda do layer");
				}
				
				return true;
			}
		};
		
		rect.setColor(0f,0f,0f, 0.85f); 
		this.attachChild(rect); 
		this.registerTouchArea(rect); 
		this.setPosition(Constants.CAMERA_WIDTH * 0.5f,Constants.CAMERA_HEIGHT * 0.5f); 
	}
}
