package com.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
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
	
//	public ITextureRegion anaRegion; 
	
	public Sprite teste; 
	
	//Ainda acho que essas coisas deveriam ser private. 
	public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //Splash
    public ITextureRegion splashRegion; 
    private BitmapTextureAtlas splashTextureAtlas;
    
    ///Connect Scene
    public ITextureRegion facebookConnectRegion;
    private BuildableBitmapTextureAtlas facebookConnectMenuAtlas; 
    
    public Font font; 

    //Header
    public ITextureRegion headerBackgroundRegion;
    public ITextureRegion headerProfileRegion;
    //loading
    public ITextureRegion loadingRegion;
    public ITextureRegion loadingIconRegion; 
    private BuildableBitmapTextureAtlas headerAtlas;
    
    //MainMenu
    public ITextureRegion newGameMenuRegion;
    public ITextureRegion gameItemBackGroundRegion;
    public ITextureRegion defaultPictureRegion2;
    private BuildableBitmapTextureAtlas mainMenuAtlas;
    
    //NewGame
    public ITextureRegion facebookFriendsMenuRegion;
    public ITextureRegion randomOpponentMenuRegion;
    private BuildableBitmapTextureAtlas newGameMenuAtlas;
    
    //FriendPicker
    public ITextureRegion friendPickerCellBackGroundRegion; 
    public ITextureRegion defaultPictureRegion; 
    private BuildableBitmapTextureAtlas friendPickerCellAtlas; 
    
    //ChoiseScene
    public ITextureRegion btnPlayBigRegion;
    public ITextureRegion btnPlaySmallRegion;
    public ITextureRegion roulleteRegion;
    public ITextureRegion btnRoulleteRegion;
    public ITextureRegion backgroundRegion;
    private BuildableBitmapTextureAtlas choiseSceneAtlas;
    
    //GameScene
    public ITextureRegion btnTipRegion;
    private BuildableBitmapTextureAtlas gameSceneAtlas; 
    
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
    	//o loadHeader talvez tenha que sair daqui. 
    	loadHeader();
    	loadMainMenuGraphics(); 
    }

    public synchronized void unloadMainMenuScene() {

    }
    
    private synchronized void loadMainMenuGraphics() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/mainMenu/"); 
    	mainMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR); 
    	newGameMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "btnNewGame.png");
    	gameItemBackGroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "backGroundCell.png");
    	defaultPictureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "man_default.png");
    	try {
			this.mainMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.mainMenuAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    //Vou carregar a prite de loading junto com o header
    public synchronized void loadHeader() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Header/"); 
    	headerAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 750, 300, TextureOptions.BILINEAR); 
    	headerBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "GreenBar.png");
    	headerProfileRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "profile.png");
    	loadingRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "pixel.png");
    	loadingIconRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "loadingIcon.png");
    	try {
			this.headerAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.headerAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadHeader() {
    	
    }
    
    public synchronized void loadNewGameScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/newGame/"); 
    	newGameMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 390, 180, TextureOptions.BILINEAR); 
    	facebookFriendsMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(newGameMenuAtlas, activity, "btnFacebookFriends.png");
    	randomOpponentMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(newGameMenuAtlas, activity, "btnRandomOpponent.png");
    	try {
			this.newGameMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.newGameMenuAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    
    public synchronized void unloadNewGameScene() {

    }
    
    public synchronized void loadFriendPickerScene() {
    	loadFriendPickerCell(); 
    }
    
    public synchronized void unloadFriendPickerScene() {

    }
    
    private synchronized void  loadFriendPickerCell() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/friendPicker/"); 
    	friendPickerCellAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 490, 210, TextureOptions.BILINEAR); 
    	friendPickerCellBackGroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(friendPickerCellAtlas, activity, "backGroundCell.png");
    	defaultPictureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(friendPickerCellAtlas, activity, "man_default.png");
    	try {
			this.friendPickerCellAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.friendPickerCellAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    private synchronized void  unloadFriendPickerCell() {
    }
    
    public synchronized void loadChoiceScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/choiseScene/");
    	choiseSceneAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 490, 210, TextureOptions.BILINEAR);
    	btnPlayBigRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiseSceneAtlas, activity, "btnPlayBig.png"); //380x80
        btnPlaySmallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiseSceneAtlas, activity, "btnPlaySmall.png");
        btnRoulleteRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiseSceneAtlas, activity, "btnRoullete.png");
//        roulleteRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiseSceneAtlas, activity, "");
//        backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiseSceneAtlas, activity, "");
        
        try {
			this.choiseSceneAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.choiseSceneAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadChoiceScene() {
    }
    
    public synchronized void loadGameScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/game/");
    	gameSceneAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 60, 60, TextureOptions.BILINEAR);
    	btnTipRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "tip1.png");
    	try {
			this.gameSceneAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			this.gameSceneAtlas.load(); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadGameScene() {
    }
    
    
    
    
}
