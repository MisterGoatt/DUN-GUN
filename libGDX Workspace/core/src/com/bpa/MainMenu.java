package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;


public class MainMenu implements Screen{
	
	final DunGun game;
	
//	OrthographicCamera camera;

	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display
	BitmapFont menuText;
	BitmapFont menuTextRed;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	Texture mainMenuScreen;
	boolean skipToMainM = false;
	boolean onMenu = false;
	boolean justClicked = false;
	private Viewport gamePort;
	private OrthographicCamera cam;
	
	public MainMenu(final DunGun game) {
		this.game = game;
		
		cam = new OrthographicCamera();		
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titleScreen.jpg");
		musicScreen = new Texture("screens/musicscreen.jpg");
		mainMenuScreen = new Texture("screens/main_menu_2.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
		menuText = new BitmapFont(Gdx.files.internal("fonts/HBM Foista Regular36.fnt"));
		menuTextRed = new BitmapFont(Gdx.files.internal("fonts/HBM Foista Regular36 (Red).fnt"));
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); //centers the map to center of screen
		
	}
	
	@Override
	public void render(float delta) {

		
		game.batch.begin(); 
		game.batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		

		

		
		
		//DRAWS INTRO SCREENS
		//**********************************

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
	}else if (skipToMainM == true){
		game.batch.draw(mainMenuScreen, 0, 0,cam.viewportWidth, cam.viewportHeight);
		onMenu = true;
	}
		
		if (onMenu == true && justClicked == false) {
			

			
			//TO LEVEL1
			if (680 < mX && mX < 836 && 531 < mY && mY < 573)  {
				menuTextRed.draw(game.batch, "start", 710, 560);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new Level1(game));
				
				}}else {
				menuText.draw(game.batch, "start", 710, 560);

			}
			//QUITS GAME
			if (680 < mX && mX < 836 && 265 < mY && mY < 310){
				menuTextRed.draw(game.batch, "quit", 720, 295);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					Gdx.app.exit();
					
				}}else {
				menuText.draw(game.batch, "quit", 720, 295);

			}
			//OPTIONS
			if (680 < mX && mX < 836 && 464 < mY && mY < 504){
				menuTextRed.draw(game.batch, "options", 687, 492);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new Options(game));

				}}else {
					menuText.draw(game.batch, "options", 687, 492);
				}
			//TUTORIAL
			if (680 < mX && mX < 836 && 407 < mY && mY < 438){
				menuTextRed.draw(game.batch, "tutorial", 677, 429);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new Tutorial(game));

				}}else {
					menuText.draw(game.batch, "tutorial", 677, 429);
				}
			//CREDITS
			if (680 < mX && mX < 836 && 338 < mY && mY < 374){
				menuTextRed.draw(game.batch, "credits", 690, 362);

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new Credits(game));

				}}else {
					menuText.draw(game.batch, "credits", 690, 362);

				}
		}


		
		
		System.out.println(mX + " " + mY);

		//***********************************
		//Keeps mouse from being held down
		justClicked = false;

		if ( (onMenu == true || counter <= 10.2) && Gdx.input.isTouched(Input.Buttons.LEFT)) {
			skipToMainM = true;
			justClicked = true;
		}
		
		
		//FRAMES PER SECOND
		//**********************************
		//System.out.println((System.currentTimeMillis() - startTime) / 1000);
		counter = (System.currentTimeMillis() - startTime) / 1000;
		int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
		String frames = Integer.toString(f); //converts frames per second to a string
		framerate.draw(game.batch, frames, 5, 785); //displays frames per second as text in top left
		//**********************************
		
		cam.update();
		game.batch.end(); 
		
	}


	@Override
	public void show() {
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
		// TODO Auto-generated method stub
		game.batch.dispose();
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		framerate.dispose();
		mainMenuScreen.dispose();
		menuText.dispose();
	}

}
