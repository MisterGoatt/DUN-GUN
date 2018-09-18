package com.bpa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;


public class DunGun extends Game{
	public SpriteBatch batch;
	/*Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display*/
	private OrthographicCamera camera;


	@Override
	public void create () {
		batch = new SpriteBatch();
		/*publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titlescreen_placeholder.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));*/
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {

		super.render(); //REMEMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		/*publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		framerate.dispose();*/
		
	}
}
