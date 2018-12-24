package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CrashScreen implements Screen {
	final Mutagen game;
	Texture crashScreen;
	private OrthographicCamera cam;
	private Viewport gamePort;
	boolean justClicked = false;
	boolean onCrash = true;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	BitmapFont activeCrashText;
	
	
	public CrashScreen(final Mutagen game) {
		this.game = game;
		crashScreen = new Texture("screens/crashScreen.jpg");
		cam = new OrthographicCamera();
		gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam);
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// clears screen
		Gdx.gl.glClearColor(0, 0, 0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mouse_position);
		
		game.batch.begin();
		game.batch.setProjectionMatrix(cam.combined);
		
		game.batch.draw(crashScreen, 0, 0);
		
		
		float mX = mouse_position.x;
		float mY = mouse_position.y;
		
		//System.out.println(mouse_position.x);
		System.out.println(mouse_position.y);
		
		
		if (640 < mX && mX < 860 && 110 < mY && mY < 330)
		{
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				Gdx.app.exit();
			}
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
		crashScreen.dispose();
	}
}
