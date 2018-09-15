package com.bpa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Start extends ApplicationAdapter {
	SpriteBatch batch;
	Texture publisherScreen;
	Texture creditScreen;
	Texture menuScreen;
	private long startTime = System.currentTimeMillis();
	private long counter;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		menuScreen = new Texture("screens/titlescreen_placeholder.jpg");


	}

	@Override
	
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen
		
		
		System.out.println((System.currentTimeMillis() - startTime) / 1000);
		counter = (System.currentTimeMillis() - startTime) / 1000;
		batch.begin(); //begins sprite batch

		if (counter < 2) {
			System.out.println("A");
			batch.draw(publisherScreen, 0, 0); //draws image
		}else if (counter <=  3 && counter >= 2) {
			System.out.println("b");
			batch.draw(creditScreen, 0, 0);
		}else if (counter > 3){
			System.out.println("c");
			batch.draw(menuScreen, 0, 0);
		}
		
		batch.end(); //what actually displays everything to the screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		publisherScreen.dispose();
		creditScreen.dispose();
		menuScreen.dispose();
		
	}
}
