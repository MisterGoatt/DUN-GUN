package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Credits implements Screen{
	final DunGun game;
	Texture credits;
	
	public Credits(final DunGun game) {
		this.game = game;
		credits = new Texture("screens/Credits.jpg");

		
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
		game.batch.draw(credits, 0, 0);
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if (mY < 100 && mX < 100 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));

		}
		
		game.batch.end();
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
		game.batch.dispose();
		credits.dispose();		
	}

}
