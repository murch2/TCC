/**
 * @author Rodrigo Duarte Louro
 * @dateOct 5, 2014
 */
package com.model;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;

import com.managers.ResourcesManager;
import com.util.ImageDownloader;

import android.content.res.Resources;
import android.provider.CalendarContract.Colors;

public class VersusCell extends Sprite {
	
	private Sprite friendPicture;
	
//	Precisa fazer uma porra de imagem pra colocaar aqui
	public VersusCell(String url) {
		super(0, 0, 
				ResourcesManager.getInstance().backgroundChoiceRegion.getWidth(),
				ResourcesManager.getInstance().backgroundChoiceRegion.getHeight(),
				ResourcesManager.getInstance().backgroundChoiceRegion,
				ResourcesManager.getInstance().vbom);
		
		createPicture(url); 
	}
	
	
	private void createPicture(final String url) {
		friendPicture = new Sprite(0, 0, ResourcesManager.getInstance().defaultPictureRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		friendPicture.setPosition(this.getWidth() - friendPicture.getWidth() * 0.6f, this.getHeight() * 0.5f);
		attachChild(friendPicture);
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("URL = " + url);
				Sprite updatePicture = ImageDownloader.testeImage(url);
//				updatePicture.setWidth(50f);
//				updatePicture.setHeight(50f); 
//				updatePicture.setPosition(friendPicture); 
//				detachChild(friendPicture);
//				detachChild(friendPicture);
				friendPicture = updatePicture; 
				attachChild(friendPicture); 
			}
		}); 
	}

}
