package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;


public class MainMenu implements Screen{
	
	final DunGun game;
	
//	OrthographicCamera camera;

	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	Texture mainMenuScreen;
	boolean skipToMainM = false;
	boolean onMenu = false;
	
	public MainMenu(final DunGun game) {
		this.game = game;
		
		
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titleScreen.jpg");
		musicScreen = new Texture("screens/musicscreen.jpg");
		mainMenuScreen = new Texture("screens/main_menu.png");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));

		
	}
	
	@Override
	public void render(float delta) {

		game.batch.begin(); 
		boolean singleClick = false; //prevent held down mouse click

		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		

		//DRAWS INTRO SCREENS
		//**********************************
		if (counter <= 10.2 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			skipToMainM = true;
			singleClick = true;
		}
		
		if (skipToMainM == false) {
			if (counter <= 2) {
					game.batch.draw(publisherScreen, 0, 0);
				}
			else if (counter <=  5 && counter > 2) {
					game.batch.draw(creditScreen, 0, 0);
				}
			else if (counter >= 5.1 && counter <= 7.1) {
					game.batch.draw(musicScreen, 0, 0);
				}
			else if (counter >= 7.2 && counter <= 10.2){
					game.batch.draw(titleScreen, 0, 0);
				} 
			else {
					game.batch.draw(mainMenuScreen, 0, 0);
					onMenu = true;
				}			
	}else if (skipToMainM == true && singleClick == false){
		game.batch.draw(mainMenuScreen, 0, 0);
		onMenu = true;
	}
		
		
		//**********************************
		//TO LEVEL1
		
		if (onMenu == true && 679 < mX  && mX < 836 && 531 < mY && mY < 573 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			game.setScreen(new Level1(game));
			System.out.println("TAPPING");

		}
		
		
		
		
		
		System.out.println(mX + " " + mY);

		
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
		mainMenuScreen.dispose();
	}

}
