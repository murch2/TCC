/**
 * @author Rodrigo Duarte Louro
 * @dateOct 5, 2014
 */
package com.model;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import com.managers.ResourcesManager;

import android.content.res.Resources;
import android.provider.CalendarContract.Colors;

public class VersusCell extends Sprite {
	
//	Precisa fazer uma porra de imagem pra colocaar aqui
	public VersusCell() {
		super(0, 0, 
				ResourcesManager.getInstance().backgroundChoiceRegion.getWidth(),
				ResourcesManager.getInstance().backgroundChoiceRegion.getHeight(),
				ResourcesManager.getInstance().backgroundChoiceRegion,
				ResourcesManager.getInstance().vbom);	
	}

}
