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
	 
	public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    //General
    public ITextureRegion blueBackground; 
    private BitmapTextureAtlas generalTextureAtlas;
    
    //Splash
    public ITextureRegion splashRegion; 
    private BitmapTextureAtlas splashTextureAtlas;
    
    ///Connect Scene
    public ITextureRegion facebookConnectRegion;
    private BuildableBitmapTextureAtlas facebookConnectMenuAtlas; 
    
    public Font gameFont;
    public Font arialFont; 

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
    public ITextureRegion btnStatusPlayRegion; 
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
    public ITextureRegion roulleteLeftTopRegion;
    public ITextureRegion roulleteRightTopRegion;
    public ITextureRegion roulleteRightBotRegion;
    public ITextureRegion roulleteLeftBotRegion;
    public ITextureRegion btnRotateRegion;
    public ITextureRegion backgroundChoiceRegion;
    private BuildableBitmapTextureAtlas choiceSceneAtlas;
    
    //GameScene
    public ITextureRegion[] btnTipRegion;
    public ITextureRegion btnMoreTipsRegion;
    public ITextureRegion btnAnswerRegion;
    public ITextureRegion backgroundTipLayerRegion; 
    public ITextureRegion gameBackgrounRegion;
    public ITextureRegion gameTitleRegion;
    private BuildableBitmapTextureAtlas gameSceneAtlas;
    
    
    //EndGameScene
    public ITextureRegion btnNextRegion;
    public ITextureRegion borderRegion;
    private BuildableBitmapTextureAtlas endGameSceneAtlas;
    
    public static ResourcesManager getInstance() {
        return INSTANCE;
    }
	
    public static void prepareResourcesManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    public synchronized void loadSplashScene() { 
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/splash/"); 
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 500, 810, TextureOptions.BILINEAR); 
    	splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "SplashScreen.png", 0, 0); 
    	splashTextureAtlas.load();
    	
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/game/");
    	generalTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 500, 810, TextureOptions.BILINEAR); 
    	blueBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(generalTextureAtlas, activity, "teste1.png", 0, 0); 
    	generalTextureAtlas.load();
    }
    
    public synchronized void unloadSplashScene() {
    	splashTextureAtlas.unload();
    	splashRegion = null;
    	System.gc();
    }
    
    public synchronized void loadConnectScene() {
    	if (gameFont == null) {
    		createFont(); 
    	}
    	
    	if (facebookConnectMenuAtlas == null) {
    		createConnectGraphics(); 
    	}
    	gameFont.load(); 
    	facebookConnectMenuAtlas.load();
    }
    
    public void createConnectGraphics() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/connect/"); 
    	facebookConnectMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 470, 120, TextureOptions.BILINEAR); 
    	facebookConnectRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(facebookConnectMenuAtlas, activity, "facebookLoginBtn.png");
    	try {
			facebookConnectMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadConnectScene() {
    	facebookConnectMenuAtlas.unload(); 
    	System.gc();
    }
   
    private void createFont() {
    	FontFactory.setAssetBasePath("font/");
        
    	final ITexture mainFontTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        gameFont = FontFactory.createFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "gamefont.ttf", 30, true, Color.WHITE);
        gameFont.load(); 
        
        final ITexture arialFontTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        arialFont = FontFactory.createFromAsset(activity.getFontManager(), arialFontTexture, activity.getAssets(), "arial.ttf", 30, true, Color.WHITE);
        arialFont.load();
    }
    
//    final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);    
//    this.mFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), &quot;Plok.ttf&quot;, 48, true, Color.WHITE);
//    4
//    this.mFont.load();

    
//    private synchronized void loadConnectGraphics() {
//    	
//    }
//    
    public synchronized void loadMainMenuScene() {
    	if (gameFont == null) {
    		createFont(); 
    	}
    	 
    	if (headerAtlas == null) {
    		createHeader(); 
    	}
    	
    	if (mainMenuAtlas == null) {
    		createMainMenuGraphics(); 
    	}
    	
    	gameFont.load();
    	headerAtlas.load();
    	mainMenuAtlas.load(); 
    }

    public synchronized void unloadMainMenuScene() {
    	headerAtlas.unload();
    	mainMenuAtlas.unload();
    }
    
    private synchronized void createMainMenuGraphics() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/mainMenu/"); 
    	mainMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR); 
    	newGameMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "btnNewGame.png");
    	gameItemBackGroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "backGroundCell.png");
    	defaultPictureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "man_default.png");
    	btnStatusPlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainMenuAtlas, activity, "btnStatusPlay.png");
    	try {
			mainMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    

    public synchronized void createHeader() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Header/"); 
    	headerAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 750, 300, TextureOptions.BILINEAR); 
    	headerBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "GreenBar.png");
    	headerProfileRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "profile.png");
    	loadingRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "pixel.png");
    	loadingIconRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(headerAtlas, activity, "loadingIcon.png");
    	try {
			this.headerAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1))); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void loadNewGameScene() {
    	if (newGameMenuAtlas == null) {
    		createNewGameScene(); 
    	}
    	newGameMenuAtlas.load();
    }
    
    private void createNewGameScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/newGame/"); 
    	newGameMenuAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 390, 180, TextureOptions.BILINEAR); 
    	facebookFriendsMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(newGameMenuAtlas, activity, "btnFacebookFriends.png");
    	randomOpponentMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(newGameMenuAtlas, activity, "btnRandomOpponent.png");
    	try {
			newGameMenuAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1))); 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadNewGameScene() {
    	newGameMenuAtlas.unload(); 
    }
    
    public synchronized void loadFriendPickerScene() {
    	if (friendPickerCellAtlas == null) {
    		createFriendPickerCell(); 
    	}
    	friendPickerCellAtlas.load(); 
    }
    
    public synchronized void unloadFriendPickerScene() {
    	friendPickerCellAtlas.unload(); 
    }
    
    private void createFriendPickerCell() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/friendPicker/"); 
    	friendPickerCellAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 490, 210, TextureOptions.BILINEAR); 
    	friendPickerCellBackGroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(friendPickerCellAtlas, activity, "backGroundCell.png");
    	defaultPictureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(friendPickerCellAtlas, activity, "man_default.png");
    	try {
			this.friendPickerCellAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void loadChoiceScene() {
    	if (choiceSceneAtlas == null) {
    		createChoiceScene();
    	}
    	choiceSceneAtlas.load(); 
    }
    
    public synchronized void createChoiceScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/choiseScene/");
    	choiceSceneAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	btnPlayBigRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "btnPlayBig.png"); 
        btnPlaySmallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "btnPlaySmall.png");
        btnRotateRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "btnRoullete.png");
        backgroundChoiceRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "BackgroundChoice.png");
        backgroundChoiceRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "BackgroundChoice.png");
        roulleteLeftTopRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "yellowCircle.png");
        roulleteRightTopRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "blueCircle.png");
        roulleteLeftBotRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "greenCircle.png");
        roulleteRightBotRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(choiceSceneAtlas, activity, "purpleCircle.png");
        
        try {
			choiceSceneAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadChoiceScene() {
    	choiceSceneAtlas.unload(); 
    }
    
    public synchronized void loadGameScene() {
    	if (gameSceneAtlas == null) {
    		createGameScene(); 
    	}
    	gameSceneAtlas.load();
    }
    
    public synchronized void createGameScene() {
    	btnTipRegion = new ITextureRegion[10]; 
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/game/");
    	gameSceneAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
    	String name = ""; 
    	
    	for (int i = 0; i < btnTipRegion.length; i++) {
			name = String.format("tip%d.png", i+1); 
			System.out.println("name = " + name);
			btnTipRegion[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, name);
		}
    	
    	btnMoreTipsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "gameButton.png");
    	btnAnswerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "gameButton.png");
    	backgroundTipLayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "tipLayerBackground.png");
    	gameBackgrounRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "teste1.png");
    	gameTitleRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneAtlas, activity, "title.png");
    	try {
			gameSceneAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
			 
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadGameScene() {
    	gameSceneAtlas.unload();
    }
    
    public synchronized void loadEndGameScene() {
    	if (endGameSceneAtlas == null) {
    		createEndGameScene(); 
    	}
    	endGameSceneAtlas.load(); 
    }
    
    private synchronized void createEndGameScene() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/scenes/endGame/");
    	endGameSceneAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
    	borderRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(endGameSceneAtlas, activity, "endGameLayer.png");
    	btnNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(endGameSceneAtlas, activity, "btNext.png");
        
        try {
        	endGameSceneAtlas.build((new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized void unloadEndGameScene() {
        endGameSceneAtlas.unload(); 
    }
    
    

}
