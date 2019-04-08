package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import screens.Tutorial;
import BackEnd.LogFileHandler;
import BackEnd.Mutagen;
import BackEnd.PointSystem;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


public class MainMenu implements Screen, InputProcessor{

	final Mutagen game;

	private long startTime = System.currentTimeMillis();
	BitmapFont framerate; //font for frame rate display
	BitmapFont inactiveMenuText;
	BitmapFont activeMenuText;
	Texture mainMenuScreen;
	boolean skipToMainM = false;
	boolean onMenu = true;
	boolean buttonPressed = false;
	private Viewport gamePort;
	private OrthographicCamera cam;
	static Music themeMusic = Mutagen.manager.get("music/Dun-Gun2.ogg", Music.class);
	private Vector3 mousePosition = new Vector3(0, 0, 0);
	private float mX, mY;
	Cursor mouseOriginal;
	public static boolean pointStart = true;
	public static boolean pointsMenu = false;
	LogFileHandler lfh = new LogFileHandler();


	public MainMenu(final Mutagen game) {
		this.game = game;
//		try {
			mainMenuScreen = Mutagen.manager.get("screens/menuScreen.jpg", Texture.class);
			mainMenuScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			framerate = Mutagen.manager.get("fonts/CourierNew32.fnt", BitmapFont.class) ;
			inactiveMenuText = Mutagen.manager.get("fonts/inactiveMenu(36).fnt", BitmapFont.class);
			activeMenuText = Mutagen.manager.get("fonts/activeMenu(36).fnt", BitmapFont.class);
			
			cam = new OrthographicCamera();		
			gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
			cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
			Gdx.input.setInputProcessor(this);
			themeMusic.setLooping(true);
			themeMusic.play();
			themeMusic.setVolume(Mutagen.musicVolume);
			pointsMenu = true;
			System.out.println("skeet skeet");
			mouseOriginal = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("mouseOriginal.png")), 1, 1);
			System.out.println("breet breet");
			Gdx.graphics.setCursor(mouseOriginal);
			System.out.println("yeet deet");

//		} 
//		catch (Exception e) {
//			//Logs that this method of this class triggered an exception
//			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
//			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
//
//		}

	}



	@Override
	public void render(float delta) {

//		try {
			//clears screen
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			
			mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(mousePosition); //gets mouse coordinates within viewport
			//System.out.println(mousePosition);
			game.batch.begin(); 
			game.batch.setProjectionMatrix(cam.combined);

			//mouse x and y
			mX = mousePosition.x;
			mY = mousePosition.y;
			game.batch.draw(mainMenuScreen, 0, 0); // draw background screen

			//START goes to player mode screen
			if (88 < mX && mX < 311 && 40 < mY && mY < 110)  {
				activeMenuText.draw(game.batch, "START", 140, 90);
			}else {
				inactiveMenuText.draw(game.batch, "START", 140, 90);
			}

			//OPTIONS
			if (645 < mX && mX < 874 && 40 < mY && mY < 110){
				activeMenuText.draw(game.batch, "OPTIONS", 670, 90);
			}else {
				inactiveMenuText.draw(game.batch, "OPTIONS", 670, 90);
			}
			//CREDITS
			if (916 < mX && mX < 1152 && 40< mY && mY < 110){
				activeMenuText.draw(game.batch, "CREDITS", 952, 90);
			}else {
				inactiveMenuText.draw(game.batch, "CREDITS", 952, 90);
			}

			//QUIT
			if (1201 < mX && mX < 1435 && 40 < mY && mY < 110){
				activeMenuText.draw(game.batch, "QUIT", 1270, 90);
			}else{
				inactiveMenuText.draw(game.batch, "QUIT", 1270, 90);
			}
			
			//tutorial
			if (365 < mX && mX < 598 && 40 < mY && mY < 110){
				activeMenuText.draw(game.batch, "TUTORIAL", 382, 90);
			}else {
				inactiveMenuText.draw(game.batch, "TUTORIAL", 382, 90);
				}

			PointSystem.pointFile();
			
			cam.update();
			game.batch.end();
//		} catch (Exception e) {
//			//Logs that this method of this class triggered an exception
//			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
//			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
//		} 
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

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (!buttonPressed) {
			//START goes to player mode screen
			if (88 < mX && mX < 311 && 40 < mY && mY < 100)  {
				Mutagen.clicking();
				game.setScreen(new DifficultyScreen(game));
			}
			//TUTORIAL
			if (365 < mX && mX < 598 && 40 < mY && mY < 110) {
				game.setScreen(new Tutorial(game));
			}
			
			//OPTIONS
			if (645 < mX && mX < 874 && 40 < mY && mY < 110){
				themeMusic.stop();
				Mutagen.clicking();
				game.setScreen(new Options(game));
			}
			//CREDITS
			if (916 < mX && mX < 1152 && 40< mY && mY < 110){
				themeMusic.stop();
				Mutagen.clicking();
				game.setScreen(new Credits(game));
			}

			//QUIT
			if (1201 < mX && mX < 1435 && 40 < mY && mY < 110){
				Gdx.app.exit();
			}
		}
		buttonPressed = true;
		return false;
	}



	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		buttonPressed = false;
		return false;
	}



	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
