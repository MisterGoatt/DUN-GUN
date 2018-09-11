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
	Texture img;
	BitmapFont font; //instantiates font
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("SillyBoi.jpg");
		font = new BitmapFont(); //uses default bitmap font
		font.getData().setScale(8); //increases the default scale of the font
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		//how to determine what a bitmap looks like when the bitmap gets drawn at a larger size than it was 
		//originally designed to be drawn in
		//Linear filter makes sure text is not pixely when drawn larger
		
	}

	@Override
	
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen
		batch.begin(); //begins sprite batch
		batch.draw(img, 525, 200); //draws image
		font.draw(batch, "DUN-GUN", 475, 120); //draws text to screen
		batch.end(); //what actually displays everything to the screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
