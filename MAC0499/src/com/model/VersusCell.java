/**
 * @author Rodrigo Duarte Louro
 * @dateOct 5, 2014
 */
package com.model;

import java.util.StringTokenizer;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.managers.ResourcesManager;
import com.util.Constants;
import com.util.ImageDownloader;

import android.content.res.Resources;
import android.provider.CalendarContract.Colors;
import android.text.InputFilter.LengthFilter;

public class VersusCell extends Sprite {
	
	private Sprite picture;
	private Text nameText;
	private Text middleNameText;
	
	boolean isFriend;
	public final int MAX_CHAR_NAME = 8; 
	
	public VersusCell(String url, boolean isFriend, String name) {
		super(0, 0, 
				ResourcesManager.getInstance().backgroundChoiceRegion.getWidth(),
				ResourcesManager.getInstance().backgroundChoiceRegion.getHeight(),
				ResourcesManager.getInstance().backgroundChoiceRegion,
				ResourcesManager.getInstance().vbom);
		
		this.isFriend = isFriend; 
		createPicture(url);
		createName(name); 
		
// TODO Ver se o jogo é novo ou não.
	}
	
	
	private void createPicture(final String url) {
		picture = new Sprite(0, 0, ResourcesManager.getInstance().defaultPictureRegion, ResourcesManager.getInstance().vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		if (isFriend)
			picture.setPosition(picture.getWidth() * 0.6f, this.getHeight() * 0.5f);
		else 
			picture.setPosition(this.getWidth() - picture.getWidth() * 0.6f, this.getHeight() * 0.5f);
		
		attachChild(picture);
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Foto = " + url);
				Sprite updatePicture = ImageDownloader.testeImage(url);
				updatePicture.setWidth(picture.getWidth());
				updatePicture.setHeight(picture.getHeight()); 
				updatePicture.setPosition(picture); 
				detachChild(picture);
				detachChild(picture);
				picture = updatePicture; 
				attachChild(picture); 
			}
		}); 
	}
	 
	private void createName (String name) {
		JSONObject names = nameTreatment(name); 
		String middleName = ""; 
		try {
			middleName = names.getString("middleName");
			name = names.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		nameText = new Text(0, 0, ResourcesManager.getInstance().gameFont, name, ResourcesManager.getInstance().vbom);
		nameText.setColor(Color.BLACK);
		if (isFriend) {
			nameText.setAnchorCenter(0f, 0.5f);
			nameText.setPosition(getWidth() * 0.4f, Constants.HEIGHT_PICKER_CELL * 0.85f);
		}
		else {
			nameText.setAnchorCenter(0.0f, 0.5f);
			nameText.setPosition(15, Constants.HEIGHT_PICKER_CELL * 0.85f);
		}
		attachChild(nameText);
		
		middleNameText = new Text(0, 0, ResourcesManager.getInstance().gameFont, middleName, ResourcesManager.getInstance().vbom);
		middleNameText.setColor(Color.BLACK);
		if (isFriend) {
			middleNameText.setAnchorCenter(0f, 0.5f);
			middleNameText.setPosition(getWidth() * 0.4f, Constants.HEIGHT_PICKER_CELL * 0.4f);
		}
		else {
			middleNameText.setAnchorCenter(0.0f, 0.5f);
			middleNameText.setPosition(15, Constants.HEIGHT_PICKER_CELL * 0.4f);
		}
		attachChild(middleNameText);
	}
	
	private JSONObject nameTreatment(String name) {
		StringTokenizer str = new StringTokenizer(name); 
		String strName = "";
		String strMiddleName = "";  
		JSONObject result = new JSONObject(); 
		
		if (str.hasMoreTokens()) {
			strName = str.nextToken(); 
			System.out.println("Name = " + strName);
			if (strName.length() > MAX_CHAR_NAME) {
				strName = strName.substring(0, MAX_CHAR_NAME - 1);
				strName.concat("."); 
			}
			if (str.hasMoreTokens()) { 
				strMiddleName = str.nextToken();
				System.out.println("MiddleName = " + strMiddleName);
				if (strMiddleName.length() > MAX_CHAR_NAME) {
					strMiddleName = strMiddleName.substring(0, MAX_CHAR_NAME - 1);
					strMiddleName.concat(".");
				}
			}
			try {
				result.put("name", strName);
				result.put("middleName", strMiddleName);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		return result; 
	}
}
