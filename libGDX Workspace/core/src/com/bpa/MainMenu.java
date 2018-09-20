package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;


public class MainMenu implements Screen{
	
	final DunGun game;
	
	OrthographicCamera camera;

	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture dv;
	boolean isPressed = false; //for mouse input spam
	BitmapFont dvf;



	
	public MainMenu(final DunGun game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 800);
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titlescreen_placeholder.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
		dv = new Texture("hqdefault.jpg");
		dvf = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
	}
	
	@Override
	public void render(float delta) {

		int mX = Gdx.input.getX();
		int mY = Gdx.input.getY();
		
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen
		
		//System.out.println((System.currentTimeMillis() - startTime) / 1000);
		counter = (System.currentTimeMillis() - startTime) / 1000;
		//Mouse Input
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (isPressed == false)) {
			isPressed = true;
			if (mX >= 700 && mX <= 800 && mY >= 300 &&  mY <= 400) {
				System.out.println("DEUS VULT");
				game.batch.draw(dv, mX, mY);
				dvf.draw(game.batch, "DEUS VULT", mX, mY - 100);
			}
		}
		//keeps from holding down spam
		if (!(Gdx.input.isButtonPressed(Input.Buttons.LEFT))) {
			
			isPressed = false;
		}
		//draws publisher screen, credit screen, and then title screen
		if (counter <= 2) {
			game.batch.draw(publisherScreen, 0, 0);
		}else if (counter <=  5 && counter > 2) {
			game.batch.draw(creditScreen, 0, 0);
		}else if (counter >= 5.1 && counter <= 7.1){
			game.batch.draw(titleScreen, 0, 0);
		}
		int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
		String frames = Integer.toString(f); //converts frames per second to a string
		framerate.draw(game.batch, frames, 5, 785); //displays frames per second as text in top left
		game.batch.end(); //what actually displays everything to the screen
		System.out.println(mX + " " + mY);

		
	}

	@Override
	public void show() {
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
		// TODO Auto-generated method stub
		game.batch.dispose();
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		framerate.dispose();
		dv.dispose();
	}

}
