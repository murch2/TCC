/**
 * @author Rodrigo Duarte Louro
 * @dateAug 20, 2014
 */
package com.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.ContactsContract.CommonDataKinds.Contactables;

import com.managers.GameManager;
import com.managers.ResourcesManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.model.VersusCell;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.LoadingLayer;

public class ChoiceScene extends BaseSceneWithHUD implements HTTPResponseListener, IOnSceneTouchListener{
	
	@Override
	public void createScene() { 
		new HTTPPostRequester().asyncPost(this, MakeParameters.newGame());
	}
	
	private void createItensScene (JSONArray dados) {
		createBackground();
		createRoullete(dados); 
		this.setOnSceneTouchListener(this);
		createHUD();
		createCells(); 
		if (SceneManager.getInstance().getCurrentLastSceneType().equals(SceneType.NEWGAME_SCENE) || 
			SceneManager.getInstance().getCurrentLastSceneType().equals(SceneType.FRIENDPICKER_SCENE)) {
			makeNewGameText(); 
			//TODO eh uma newGame e o cara tem que ver o label de newGame
		} else {
			//TODO pensar na lógica de como mostrar o placar atual para o cara. 
		}
	}

	private void createCells() {
		VersusCell myCell = new VersusCell(GameManager.getInstance().getUserPictureURL(), false, GameManager.getInstance().getUserName()); 
		myCell.setPosition(myCell.getWidth() * 0.5f, Constants.CAMERA_HEIGHT * 0.8f); 
		attachChild(myCell);
		
		VersusCell friendCell = new VersusCell(GameManager.getInstance().getFriendPictureURL(), true, GameManager.getInstance().getFriendName()); 
		friendCell.setPosition(Constants.CAMERA_WIDTH - friendCell.getWidth() * 0.5f, Constants.CAMERA_HEIGHT * 0.8f); 
		attachChild(friendCell);
		
	}
	
	private void createBackground() {
		setBackground(new Background(Color.PINK));
	}

	private void createRoullete(JSONArray dados) {
		//Aqui vou fazer a animação pro cara poder escolher a porra de um tipo de carta.
		//TODO
	}
	
	private void makeNewGameText() {
		Text newGameText;
		newGameText = new Text(0, 0, ResourcesManager.getInstance().font, "NEW GAME!", ResourcesManager.getInstance().vbom);
		newGameText.setColor(Color.BLACK);
		newGameText.setScale(2.0f);
		newGameText.setPosition(Constants.CENTER_X, Constants.CENTER_Y);
		attachChild(newGameText);
	}
	
	private void makeTotalScore() {
		//TODO
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.CHOISE_SCENE; 
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub	
	}
	
	

//	Aqui devolve as informações, devo criar um array de um objeto que contem essas informações, tem que ter um if pra ver se deu tudo ok.  
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json != null) {
				System.out.println("Recebi o JSON Da choice Scene");
				System.out.println(json.toString(4));
				JSONArray dados = json.getJSONArray("dados"); 
				createItensScene(dados);
			}
			else 
				System.out.println("COCO FUDEU TUDO");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		GameManager.getInstance().setCardTypeID(1);
		SceneManager.getInstance().createGameScene();
		System.out.println("Toquei na ChoiceScene");
		return true; 
	}
	

}
