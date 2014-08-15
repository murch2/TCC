/**
 * @author Rodrigo Duarte Louro
 * @dateAug 9, 2014
 */
package com.util;

import java.io.File;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.graphics.Bitmap;

import com.activities.GameActivity;
import com.managers.ResourcesManager;

public class BitmapTex {
    private ITextureRegion mRegion;

    public BitmapTex (GameActivity pContext, Bitmap pBitmap) {
    	
    	File imageFile = new File("https://scontent-a-mia.xx.fbcdn.net/hphotos-xaf1/t1.0-9/10491202_674349032641577_3918211576773003285_n.jpg"); 
    	FileBitmapTextureAtlasSource fileTextureSource = FileBitmapTextureAtlasSource.create(imageFile); 
    	
    	BuildableBitmapTextureAtlas testeAtlas = 
    			new BuildableBitmapTextureAtlas(ResourcesManager.getInstance().activity.getTextureManager(), 470, 120, TextureOptions.BILINEAR);
 
    	ITextureRegion anaRegion = 
    			BitmapTextureAtlasTextureRegionFactory.createFromSource(testeAtlas, fileTextureSource); 
    	
    	try {
			testeAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			testeAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    
    	
    }

    public ITextureRegion getRegion() {
            return mRegion;
    }
    

    public void unload() {
                    mRegion.getTexture().unload();
                    mRegion = null;
    }
}
