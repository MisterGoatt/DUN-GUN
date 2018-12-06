package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GunSelectionScreen implements Screen{
	final DunGun game;
	private Texture gunPickScreen;
	public Viewport gamePort;

	private OrthographicCamera cam;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	public static String weaponSelected;
	private int wait = 0;
	
	public GunSelectionScreen(final DunGun game) {
		this.game = game;
		gunPickScreen = DunGun.manager.get("screens/gun_selection.jpg", Texture.class);

		cam = new OrthographicCamera();		
		gamePort = new StretchViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
	
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void render(float delta) {
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	    
		wait += 1;
		
		cam.unproject(mouse_position); //gets mouse coordinates within viewport
		
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && wait >= 30) {
	    	//REVOLVER 
	    	if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 512 && mouse_position.y < 615) {
	    		weaponSelected = "revolver";
				game.setScreen(new Level1(game));

	    	}
	    	//bolt-action rifle
	    	else if (mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 380 && mouse_position.y < 480) {
	    		weaponSelected = "rifle";
				game.setScreen(new Level1(game));

	    	}
	    	//Assault rifle
	    	else if (mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 520 && mouse_position.y < 619) {
	    		weaponSelected = "assault rifle";
				game.setScreen(new Level1(game));
	    	}
	    	//shotgun
	    	else if ((mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 247 && mouse_position.y < 350)) {
	    		weaponSelected = "shotgun";
	    		game.setScreen(new Level1(game));
	    	}
	    	//Laser
	    	if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 380 && mouse_position.y < 487) {
	    		weaponSelected = "laser";
				game.setScreen(new Level1(game));
	    	}
	    	//Battle axe
	    	if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 248 && mouse_position.y < 365) {
	    		weaponSelected = "battle axe";
				game.setScreen(new Level1(game));
	    	}
				
	    	//BACK BUTTON
	    	if (mouse_position.x > 33 && mouse_position.x < 153 && mouse_position.y > 34 && mouse_position.y < 83) {
				game.setScreen(new MainMenu(game));

	    	}
	    	
	    }
	    game.batch.begin();

		game.batch.draw(gunPickScreen, 0, 0);
		game.batch.end();
		cam.update();

	}


	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		gunPickScreen.dispose();
	}
}
