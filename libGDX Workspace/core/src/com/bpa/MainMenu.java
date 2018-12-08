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
	
	final DunGun game;
	
//	OrthographicCamera camera;

	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display
	BitmapFont menuText;
	BitmapFont menuTextRed;
	Texture mainMenuScreen;
	boolean skipToMainM = false;
	boolean onMenu = true;
	boolean justClicked = false;
	private Viewport gamePort;
	private OrthographicCamera cam;
	private boolean mStart = false;
	static Music themeMusic = DunGun.manager.get("music/Dun-Gun2.mp3", Music.class);
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	private int wait = 0;
	static boolean alreadyPlaying = false;

	
	public MainMenu(final DunGun game) {
		this.game = game;
		
		mainMenuScreen = DunGun.manager.get("screens/main_menu_2.jpg", Texture.class);
		
		framerate = DunGun.manager.get("fonts/CourierNew32.fnt", BitmapFont.class) ;
		menuText = DunGun.manager.get("fonts/HBM Foista Regular36.fnt", BitmapFont.class);
		menuTextRed = DunGun.manager.get("fonts/HBM Foista Regular36 (Red).fnt", BitmapFont.class);
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

	}
	
	public void music(boolean mStart) {
		themeMusic.setLooping(true);
		themeMusic.play();
		alreadyPlaying = true;
	}
	
	@Override
	public void render(float delta) {

		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
		
		game.batch.begin(); 
		game.batch.setProjectionMatrix(cam.combined);

		//System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
		
		//mouse x and y
		float mX = mouse_position.x;
		float mY = mouse_position.y;
		
		//MUSIC START
		if (!alreadyPlaying) {
			mStart = true;
			music(mStart);
		}
		
		game.batch.draw(mainMenuScreen, 0, 0); // draw background screen
		if (wait < 11) {
			wait += 1;
			}
		if (wait > 10){
			if (onMenu == true && justClicked == false) { //prevents hold down mouse click
				//START
				if (680 < mX && mX < 836 && 531 < mY && mY < 573)  {
					menuTextRed.draw(game.batch, "start", 710, 560);
	
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						game.setScreen(new GunSelectionScreen(game));
					
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
						themeMusic.stop();
						game.setScreen(new Options(game));
	
					}}else {
						menuText.draw(game.batch, "options", 687, 492);
					}
				//TUTORIAL
				if (680 < mX && mX < 836 && 407 < mY && mY < 438){
					menuTextRed.draw(game.batch, "tutorial", 677, 429);
	
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						themeMusic.stop();
						game.setScreen(new Tutorial(game));
	
					}}else {
						menuText.draw(game.batch, "tutorial", 677, 429);
					}
				//CREDITS
				if (680 < mX && mX < 836 && 338 < mY && mY < 374){
					menuTextRed.draw(game.batch, "credits", 690, 362);
	
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						themeMusic.stop();
						game.setScreen(new Credits(game));
	
					}}else {
						menuText.draw(game.batch, "credits", 690, 362);
	
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
		gamePort.apply();
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
		framerate.dispose();
		menuText.dispose();
		themeMusic.dispose();
		mainMenuScreen.dispose();
	}

}
