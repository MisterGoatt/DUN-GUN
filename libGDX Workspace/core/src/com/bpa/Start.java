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
	BitmapFont font2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("SillyBoi.jpg");
		font = new BitmapFont(Gdx.files.internal("fonts/ArialBlack36.fnt"));
		font2 = new BitmapFont(Gdx.files.internal("fonts/ArialBlack83.fnt"));
	}

	@Override
	
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen
		batch.begin(); //begins sprite batch
		batch.draw(img, 525, 200); //draws image
		font2.draw(batch, "DUN-GUN", 545, 700); //draws text to screen
		font.draw(batch, "Richard - Plaskett - Tullis - Jeremy",  425, 165);
		batch.end(); //what actually displays everything to the screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
