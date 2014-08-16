/**
 * @author Rodrigo Duarte Louro
 * @dateAug 15, 2014
 */
package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.managers.ResourcesManager;

public class ImageDownloader {
	 
	
	public static Sprite testeImage(String link) {
		try {
	        URL url = new URL(link);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        if (myBitmap != null)
	        	return converter(myBitmap); 
	        return null; 
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return null; 
	}
	
	private static Sprite converter(Bitmap bit) {
		BitmapTextureAtlasSource source = new BitmapTextureAtlasSource(bit);
		BitmapTextureAtlas texture = new BitmapTextureAtlas(ResourcesManager.getInstance().engine.getTextureManager(),
				bit.getWidth(), bit.getHeight());
		texture.addTextureAtlasSource(source, 0, 0);
		ResourcesManager.getInstance().engine.getTextureManager().loadTexture(texture);
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		
		Sprite foto = new Sprite(0, 0,
				region, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};	
		
		return foto; 
	}
}
