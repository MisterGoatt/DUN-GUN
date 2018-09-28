package com.bpa;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bpa.world.GameMap;
import com.bpa.world.TiledGameMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class DunGun extends Game{
	public SpriteBatch batch;
	OrthographicCamera cam;
	GameMap gameMap;
	Texture img;
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		

		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		
		super.render(); //REMEMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		

	}
}
