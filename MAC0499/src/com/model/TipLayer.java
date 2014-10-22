/**
 * @author Rodrigo Duarte Louro
 * @dateOct 21, 2014
 */
package com.model;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import android.R;
import android.app.AlertDialog;
import android.app.Notification.Action;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.Request.Callback;
import com.managers.ResourcesManager;
import com.util.Constants;

public class TipLayer extends Sprite {

	private Text tipText; 
	private IMenuItem moreTipsItem; 
	private IMenuItem answerItem;
	private final int MORE_TIPS = 1; 
	private final int ANSWER = 2; 
	//	Esse layer precisa de um botão que quando clicado executa um callback passado na criação (que vai deixar o 
	//	layer invisivel no game) 
	//	Também precisa de algum jeito desabilitar o toque do menu de trás quando o layer estiver aparecendo

	public TipLayer() {
		super(0, 0, 400, 500, ResourcesManager.getInstance().backgroundChoiceRegion, ResourcesManager.getInstance().vbom); 
		setAlpha(0.85f); 
		setPosition(Constants.CENTER_X + 200 , Constants.CENTER_Y);
		createTipText();
		createButtons(); 
	}

	private void createTipText() {
		tipText = new Text(0, 0, ResourcesManager.getInstance().font, Constants.MAX_TIP, ResourcesManager.getInstance().vbom);
		tipText.setAnchorCenterX(0.0f);
		tipText.setAnchorCenterY(1.0f);
		tipText.setPosition(getWidth() * 0.05f, getHeight() * 0.95f);
		tipText.setColor(Color.BLACK);
		attachChild(tipText); 
	}

	public void createButtons() {
		moreTipsItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MORE_TIPS, ResourcesManager.getInstance().btnMoreTipsRegion,
				ResourcesManager.getInstance().vbom), 0.8f, 1){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				moreTipsItem.setScale(0.8f);

				TimerHandler timer = new TimerHandler(0.15f, new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						moreTipsItem.setScale(1.0f);
						moreTipsItem.getParent().detachSelf();
					}
				});

				this.registerUpdateHandler(timer); 
				return true; 
			}
		};
		moreTipsItem.setAnchorCenter(0.0f, 0.0f); 
		moreTipsItem.setPosition(15, 50);
		attachChild(moreTipsItem);

		answerItem = new ScaleMenuItemDecorator(new SpriteMenuItem(ANSWER, ResourcesManager.getInstance().btnAnswerRegion,
				ResourcesManager.getInstance().vbom), 0.8f, 1){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					answerItem.setScale(0.8f);
					TimerHandler timer = new TimerHandler(0.15f, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							answerItem.setScale(1.0f);
							ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									final EditText nameEditText = new EditText(ResourcesManager.getInstance().activity); 

									AlertDialog.Builder builder = new AlertDialog.Builder(ResourcesManager.getInstance().activity); 
									builder.setTitle("Quem é?");
									builder.setMessage("Digite o nome da personalidade");
									builder.setView(nameEditText);

									builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											System.out.println("SE PAH" + nameEditText.getText().toString());
										}
									}); 

									builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
										
									});

									final AlertDialog alert = builder.create();
									alert.show(); 
//									alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {            
//										@Override
//										public void onClick(View v) {
//											alert.dismiss(); 
//										}
//									});
								}
							});
						}
					});

					this.registerUpdateHandler(timer); 
				}
				return true;
			}
		};
		answerItem.setAnchorCenter(1.0f, 0.0f); 
		answerItem.setPosition(getWidth() - 15, 50);
		attachChild(answerItem);
	}

	public void registerMenu(Scene scene) {
		scene.registerTouchArea(moreTipsItem); 
		scene.registerTouchArea(answerItem); 
	}

	public void setTipText(String tipString) {
		this.tipText.setText(getNormalisedString(tipString, 20)); 
	}

	private String getNormalisedString(String string, float textWidth){

		if (!string.contains(" "))
			return string;

		String [] words = string.split(" ");
		String normalisedText = "";
		String line = "";

		int counter = 0; 
		for (int i = 0; i < words.length; i++) {
			counter += words[i].length(); 

			if (counter < textWidth) {
				line += words[i] + " "; 
				counter += 1; 
			} 
			else {
				normalisedText += line + "\n"; 
				line = words[i] + " ";
				counter = words[i].length() + 1; 
			}
		}
		normalisedText += line + "\n";
		return normalisedText;
	}

}
