package com.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;

import com.activities.GameActivity;

public class ResourcesManager {
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	//Ainda acho que essas coisas deveriam ser private. 
	public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //Splash
    public ITextureRegion splashRegion; 
    private BitmapTextureAtlas splashTextureAtlas;
    
    ///GameModeMenu
    public ITextureRegion facebookConnectRegion;
    private BuildableBitmapTextureAtlas facebookConnectMenuAtlas; 
    
    public Font font; 

    //Header
    public ITextureRegion headerBackgroundRegion;
    public ITextureRegion headerProfileRegion;
    private BuildableBitmapTextureAtlas headerAtlas;
    
	public static ResourcesManager getInstance() {
        return INSTANCE;
    }
	
	//Só deverá ser chamado uma vez. 
    public static void prepareResourcesManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    public synchronized void loadSplashScene() { 
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/splash/"); 
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR); 
    	splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splashScreen.png", 0, 0); 
    	splashTextureAtlas.load();
    }
    
    public synchronized void unloadSplashScene() {
    	splashTextureAtlas.unload();
    	splashRegion = null;
    	System.gc();
    }
    
    public synchronized void loadConnectScene() {
    	loadFonts(); 
    	loadConnectGraphics(); 
    }
    
    //Não sei se precisa desse método pq ele volta pro MainMenu então não sei se é bom 
//    ficar load e unload no mainMenu (que é muito pesado)
    public synchronized void unloadConnectScene() {
    	System.gc();
    }
    
    //Depois tenho que ver o que fazer com as fonts no livro, 
//    Talvez tenha que ter um desse pra cada tela ou subtipo de font dentro de uma tela
    private synchronized void loadFonts() {
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 35, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }
    
    private synchronized void loadConnectGraphics() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/connect/"); 
    	facebookConnectMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 470, 120, TextureOptions.BILINEAR); 
    	facebookConnectRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(facebookConnectMenuAtlas, activity, "facebookLoginBtn.png");
    	
    	try {
			this.facebookConnectMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.facebookConnectMenuAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void loadMainMenuScene() {
    	loadFonts();
    	loadHeader(); 
    }

    public synchronized void unloadMainMenuScene() {

    }
    
    public synchronized void loadHeader() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Header/"); 
    	headerAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 490, 110, TextureOptions.BILINEAR); 
    	headerBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "GreenBar.png");
    	headerProfileRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "profile.png");
    	try {
			this.headerAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.headerAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadHeader() {
    	
    }
    
    
}
