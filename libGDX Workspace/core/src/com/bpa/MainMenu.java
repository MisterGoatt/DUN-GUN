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
	BitmapFont dvf;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	Texture dv;
	boolean unlimiter = false;
	boolean isPressed = false; //for mouse input spam
	boolean skip1 = false;


	
	public MainMenu(final DunGun game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1500, 800);
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titlescreen_placeholder.jpg");
		musicScreen = new Texture("screens/musicscreen.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
		dv = new Texture("hqdefault.jpg");
		dvf = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
	}
	
	@Override
	public void render(float delta) {

		game.batch.begin(); 

		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.input.getY();
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen	
		
		//MOUSE INPUT
		//**********************************
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (isPressed == false) && unlimiter == false){
			isPressed = true;
			if (mX >= 700 && mX <= 800 && mY >= 300 &&  mY <= 400) {
				game.batch.draw(dv, mX, mY);
				dvf.draw(game.batch, "DEUS VULT", mX, mY - 50);
			}
		}else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && unlimiter == true)  {
			if (mX >= 700 && mX <= 800 && mY >= 300 &&  mY <= 400) {
				game.batch.draw(dv, mX, mY);
				dvf.draw(game.batch, "DEUS VULT", mX, mY - 50);
			}
		}
		//turns off mouse input limiter
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {

			if (unlimiter == false){
				unlimiter = true;
				
			}else if (unlimiter == true) {
				unlimiter = false;
			}

		}
		
		//mouse input limiter
		if (unlimiter == false){
			if (!(Gdx.input.isButtonPressed(Input.Buttons.LEFT))) {
				isPressed = false;
			}			
		}
		//**********************************

		

		//DRAWS INTRO SCREENS
		//**********************************
		if (counter <= 7.1 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			skip1 = true;
		}
		if (skip1 == false) {
			if (counter <= 2) {
					game.batch.draw(publisherScreen, 0, 0);
				}else if (counter <=  5 && counter > 2) {
					game.batch.draw(creditScreen, 0, 0);
				}else if (counter >= 5.1 && counter <= 7.1) {
					game.batch.draw(musicScreen, 0, 0);
				}
				else if (counter >= 7.2 && counter <= 10.2){
					game.batch.draw(titleScreen, 0, 0);
				}
	}
		//**********************************

		//System.out.println(mX + " " + mY);

		
		//FRAMES PER SECOND
		//**********************************
		//System.out.println((System.currentTimeMillis() - startTime) / 1000);
		counter = (System.currentTimeMillis() - startTime) / 1000;
		int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
		String frames = Integer.toString(f); //converts frames per second to a string
		framerate.draw(game.batch, frames, 5, 785); //displays frames per second as text in top left
		//**********************************
		
		
		game.batch.end(); 
		
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
