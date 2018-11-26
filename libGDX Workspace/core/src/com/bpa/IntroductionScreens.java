package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class IntroductionScreens implements Screen{
	final DunGun game;
	boolean skipToMainM;
	int counter;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	
	
	public IntroductionScreens(final DunGun game) {
		this.game = game;
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titleScreen.jpg");
		musicScreen = new Texture("screens/musicscreen.jpg");
		mainMenuScreen = new Texture("screens/main_menu_2.jpg");
	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		//DRAWS INTRO SCREENS
		//**********************************
		
		if (skipToMainM == false) {
			if (counter <= 2) {
					game.batch.draw(publisherScreen, 0, 0);
				}
			else if (counter <=  5 && counter > 2) {
					game.batch.draw(creditScreen, 0, 0);
				}
			else if (counter >= 5.1 && counter < 7.12) {
					game.batch.draw(musicScreen, 0, 0);
				}
			else if (counter >= 7.12 && counter <= 10.77){
					game.batch.draw(titleScreen, 0, 0);
				} 
			else {
					game.batch.draw(mainMenuScreen, 0, 0);
					onMenu = true;
				}			
		}else if (skipToMainM == true){
			game.batch.draw(mainMenuScreen, 0, 0,cam.viewportWidth, cam.viewportHeight);
			onMenu = true;
		}
	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		mainMenuScreen.dispose();
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		
	}
}
