/**
 * @author Rodrigo Duarte Louro
 * @dateAug 7, 2014
 */
package com.model;

import java.io.File;
import java.net.URL;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.managers.ResourcesManager;

public class FriendPickerCell extends Entity {
	//Pode ser que não seja sprite
	private Sprite backgroundCell;
	private Sprite friendPicture; 
	private String friendID;
	
	
	//Aqui eu tenho que passar as informações 
	public FriendPickerCell() {
		createBackground();
		
//		createFriendPicture(); 
	}
	
	private void createBackground() {
		backgroundCell = new Sprite(0, 0, 400, 120,
				ResourcesManager.getInstance().friendPickerCellBackGroundRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		attachChild(backgroundCell);
	}
	
//	private void createFriendPicture() {
//		final String link = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xaf1/t1.0-1/p50x50/10491202_674349032641577_3918211576773003285_n.jpg";
//		File imageFile = new File(link); 
//		IBitmapTextureAtlasSource fileTextureSource = FileBitmapTextureAtlasSource.create(imageFile); 
//		ITextureRegion textureRegion = 
//				BitmapTextureAtlasTextureRegionFactory.createFromSource(mBitmapTextureAtlas, fileTextureSource, 0, 0);
//	}
	
//	private void createFriendPicture() {
//		//Talvez tenha que tirar o escape de barras que vem com o response. 
//		final String link = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xaf1/t1.0-1/p50x50/10491202_674349032641577_3918211576773003285_n.jpg";
//		
//		try {
//            ITexture mTexture = new BitmapTexture(ResourcesManager.getInstance().engine.getTextureManager(), new IInputStreamOpener() {
//                @Override
//                public InputStream open() throws IOException {
//
//                      URL url = new URL(link);
//
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        connection.setDoInput(true);
//                        connection.connect();
//                        InputStream input = connection.getInputStream();
//                BufferedInputStream in = new BufferedInputStream(input);    
//                return in;
//                }
//            });
//mTexture.load();
//                TextureRegion MyImageFromWeb = TextureRegionFactory.extractFromTexture(mTexture);
//                
//                friendPicture = new Sprite(0, 0, 200, 200,
//        				MyImageFromWeb, ResourcesManager.getInstance().vbom) {
//        			@Override
//        			protected void preDraw(GLState pGLState, Camera pCamera) {
//        				super.preDraw(pGLState, pCamera);
//        				pGLState.enableDither();
//        			}
//        		};
//        		attachChild(friendPicture);
//                
//            } catch (IOException e) {
//                Debug.e(e);
//            }
//	}
}
