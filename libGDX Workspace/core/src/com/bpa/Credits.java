package com.bpa;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;





public class Credits implements Screen{
	final DunGun game;
	Texture credits;
	private Viewport gamePort;
	private OrthographicCamera cam;
	private float yPos = -5700;
	Music creditsMusic = DunGun.manager.get("music/creditsSong.mp3");

	
	public Credits(final DunGun game) {
		this.game = game;
		credits = DunGun.manager.get("screens/ScrollingC.jpg", Texture.class);
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		creditsMusic.play();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		game.batch.begin(); 

		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Controls the speed at which the credits scroll
		if (yPos < 0) {
			yPos += 3;
		}
		
		//displays the credits
		game.batch.draw(credits, 0, yPos);
		
		
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			creditsMusic.stop();
			MainMenu.alreadyPlaying = false;
			game.setScreen(new MainMenu(game));		
		}

		cam.update();

		game.batch.end();
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
		game.batch.dispose();
		credits.dispose();		
	}

}
