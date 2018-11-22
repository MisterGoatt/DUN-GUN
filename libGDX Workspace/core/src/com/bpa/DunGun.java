package com.bpa;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;


public class DunGun extends Game{
	public SpriteBatch batch;
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 1500;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100; //Pixels Per Meter
	
	
	@Override
	public void create() {
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
