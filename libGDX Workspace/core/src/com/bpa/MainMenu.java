package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;


public class MainMenu implements Screen{
	
	final Mutagen game;
	
	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display
	BitmapFont inactiveMenuText;
	BitmapFont activeMenuText;
	Texture mainMenuScreen;
	boolean skipToMainM = false;
	boolean onMenu = true;
	boolean justClicked = false;
	private Viewport gamePort;
	private OrthographicCamera cam;
	static Music themeMusic = Mutagen.manager.get("music/Dun-Gun2.mp3", Music.class);
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	private int wait = 0;
	
	public MainMenu(final Mutagen game) {
		this.game = game;
		
		mainMenuScreen = Mutagen.manager.get("screens/menuScreen.jpg", Texture.class);
		framerate = Mutagen.manager.get("fonts/CourierNew32.fnt", BitmapFont.class) ;
		inactiveMenuText = Mutagen.manager.get("fonts/inactiveMenu(36).fnt", BitmapFont.class);
		activeMenuText = Mutagen.manager.get("fonts/activeMenu(36).fnt", BitmapFont.class);
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		themeMusic.setLooping(true);
		themeMusic.play();
		themeMusic.setVolume(Mutagen.musicVolume);

	}
	

	
	@Override
	public void render(float delta) {

		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
		//System.out.println(mouse_position);
		game.batch.begin(); 
		game.batch.setProjectionMatrix(cam.combined);

		
		//mouse x and y
		float mX = mouse_position.x;
		float mY = mouse_position.y;
		
		game.batch.draw(mainMenuScreen, 0, 0); // draw background screen
		if (wait < 11) {
			wait += 1;
			}
		if (wait > 2){
			if (onMenu == true && justClicked == false) { //prevents hold down mouse click
				//START
				if (130 < mX && mX < 380 && 37 < mY && mY < 95)  {
					activeMenuText.draw(game.batch, "START", 196, 90);
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						game.setScreen(new GunSelectionScreen(game));
					}}else {
					inactiveMenuText.draw(game.batch, "START", 196, 90);
				}

				//OPTIONS
				if (435 < mX && mX < 756 && 37 < mY && mY < 95){
					activeMenuText.draw(game.batch, "OPTIONS", 494, 90);
	
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						themeMusic.stop();
						game.setScreen(new Options(game));
					}
				}else {
					inactiveMenuText.draw(game.batch, "OPTIONS", 494, 90);
				}
				

				//CREDITS
				if (790 < mX && mX < 1102 && 37 < mY && mY < 95){
					activeMenuText.draw(game.batch, "CREDITS", 860, 90);
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						themeMusic.stop();
						game.setScreen(new Credits(game));
					}}else {
						inactiveMenuText.draw(game.batch, "CREDITS", 860, 90);
					}

				//QUIT
				if (1160 < mX && mX < 1380 && 37 < mY && mY < 95){
					activeMenuText.draw(game.batch, "QUIT", 1220, 90);
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						Gdx.app.exit();
					}
				}else {
					inactiveMenuText.draw(game.batch, "QUIT", 1220, 90);
					}
			}
		}
		//***********************************
		//Keeps mouse from being held down
		justClicked = false;

		if ( (onMenu == true || counter <= 10.2) && Gdx.input.isTouched(Input.Buttons.LEFT)) {
			skipToMainM = true;
			justClicked = true;
		}
		
		//FRAMES PER SECOND
		//**********************************
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

	}

}
