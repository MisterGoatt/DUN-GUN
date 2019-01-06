package com.bpa;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class IntroductionScreens implements Screen{
	final Mutagen game;
	boolean skipToMainM;
	private long counter;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	private long startTime = System.currentTimeMillis();
	private Viewport gamePort;
	private OrthographicCamera cam;
	
	public IntroductionScreens(final Mutagen game) {
		this.game = game;
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		publisherScreen = Mutagen.manager.get("screens/ctm_placeholder.jpg");
		creditScreen = Mutagen.manager.get("screens/credits_placeholder.jpg");
		titleScreen = Mutagen.manager.get("screens/titleScreen.jpg");
		musicScreen = Mutagen.manager.get("screens/musicScreen.jpg");
	
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
		game.batch.begin();
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
				}else game.setScreen(new MainMenu(game));
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				game.setScreen(new MainMenu(game));
			}
			
			//FRAMES PER SECOND
			//**********************************
			counter = (System.currentTimeMillis() - startTime) / 1000;
			//int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
			//String frames = Integer.toString(f); //converts frames per second to a string
			//framerate.draw(game.batch, frames, 5, 785); //displays frames per second as text in top left
			//**********************************
			game.batch.end();
			cam.update();

			
		}
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
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		musicScreen.dispose();
		
	}
}
