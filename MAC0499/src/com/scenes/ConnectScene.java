/**
 * @author Rodrigo Duarte Louro
 * @dateJul 14, 2014
 */
package com.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.managers.GameManager;
import com.managers.SceneManager;
import com.managers.SceneManager.SceneType;
import com.server.HTTPPostRequester;
import com.server.HTTPResponseListener;
import com.server.MakeParameters;
import com.util.Constants;
import com.util.FacebookFacade;

public class ConnectScene extends BaseScene implements IOnMenuItemClickListener, GraphUserCallback, HTTPResponseListener {

	private MenuScene facebookMenu; 
	private final int MENU_FACEBOOK_CONNECT = 0;
	private FacebookFacade fb;  
	
	@Override
	public void createScene() { 
		fb = new FacebookFacade();
		createBackground();
		createWelcomeMessage(); 
		createFacebookConnectMenu();		
	}
	
	@Override
	public void onBackKeyPressed() {

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.CONNECT_SCENE; 
	}

	@Override
	public void disposeScene() {

	}

	//Mudar para uma imagem talvez depois. 
	private void createBackground() {
		setBackground(new Background(Color.BLACK));
	}

	private void createFacebookConnectMenu() {
		facebookMenu = new MenuScene(camera);
		facebookMenu.setPosition(0, 0); 

		final IMenuItem facebookConnectItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_FACEBOOK_CONNECT, resourcesManager.facebookConnectRegion, vbom), 0.8f, 1);
	
		createFacebookConnectText(facebookConnectItem); 
		
		facebookMenu.addMenuItem(facebookConnectItem);
		facebookMenu.buildAnimations();
		facebookMenu.setBackgroundEnabled(false);

		//Poderia ter uma classe aqui que é responsavel por isso. (O click do botão de menu). 
		facebookMenu.setOnMenuItemClickListener(this); 

		setChildScene(facebookMenu);
	}
	
	private void createFacebookConnectText(IMenuItem item) {
		Text text = new Text(0, 0, resourcesManager.font, "Login with Facebook", vbom);
		text.setPosition(item.getWidth() * 0.5f, item.getHeight() * 0.5f);  
		text.setColor(Color.WHITE); 
		item.attachChild(text); 
	}
	
	private void createWelcomeMessage() {
		Text text = new Text(0, 0, resourcesManager.font, "Welcome!\n Connect with Facebook\n and gain bonus! ", vbom);
		text.setPosition(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT * 0.8f);
		text.setColor(Color.WHITE);
		//Acho que aqui tem que ser a facebookmenu cena
		attachChild(text);
	}

	//Não sei se esse metodo precisa ser boolean 
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_FACEBOOK_CONNECT:
			System.out.println("DEBUG - chamando o login 2");
			fb.login(this); 
			return true; 

		default:
			break;
		}
		return false;
	}


	@Override
	public void onCompleted(GraphUser user, Response response) {
		//Se a classe só faz um pedido ao facebook tranquilo. 
		//agora se a classe fizer dois dá pra criar uma variavel de estado pra guardar 
		//qual foi o ultimo que se faz e se pode fazer pq num tem nenhum sendo feito. 
		if (user != null) {
			try {
				System.out.println("Infos do user = " + user.getInnerJSONObject().toString(4));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new HTTPPostRequester().asyncPost(this, MakeParameters.signUpParams(user));
			GameManager.getInstance().setUserName(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName()); 
			GameManager.getInstance().setUserID(user.getId());
			fb.getUserPicture(); 
			System.out.println("DEBUG - O cara está logado na connect scene  " + GameManager.getInstance().getUserName());
			//Aqui eu chamo o layer de loading. 
		}
		else {
			//Deu erro na conexão com o facebook. 
		}
	}
	
	@Override
	public void onResponse(JSONObject json) {
		try {
			if (json.getString("status").equals("ok")) {
				//Talvez aqui ou no fim da criação do mainMenuseja retirado o layer de loading. 
				GameManager.getInstance().setLoggedUser(true); 
				GameManager.getInstance().getDataInMemory().saveData(Constants.FACEBOOK_LOGIN, true);
				System.out.println("DEBUG - estou indo da connect scene para o mainMenu");
				//Tem que rodar um bagulho aqui pra pegar a foto do cara ainda (PQP)
				SceneManager.getInstance().createMainMenuScene(); 
			}
			else { //Deu merda na inserção do cara. 
				System.out.println("DEBUG - Deu Merda!");
			}
		} catch (JSONException e) {
			//Aqui tb deu merda na inserção do cara.
			e.printStackTrace();
		}
		
	}

}
