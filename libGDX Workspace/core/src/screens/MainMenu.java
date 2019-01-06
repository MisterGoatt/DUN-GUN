package screens;

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

import BackEnd.Mutagen;

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
	static Music themeMusic = Mutagen.manager.get("music/Dun-Gun2.mp3", Music.class);
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	private float mX, mY;

	public MainMenu(final Mutagen game) {
		this.game = game;
		mainMenuScreen = Mutagen.manager.get("screens/menuScreen.jpg", Texture.class);
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
		mX = mouse_position.x;
		mY = mouse_position.y;
		game.batch.draw(mainMenuScreen, 0, 0); // draw background screen

		//START goes to player mode screen
		if (130 < mX && mX < 380 && 37 < mY && mY < 95)  {
			activeMenuText.draw(game.batch, "START", 196, 90);
		}else {
			inactiveMenuText.draw(game.batch, "START", 196, 90);
		}

		//OPTIONS
		if (435 < mX && mX < 756 && 37 < mY && mY < 95){
			activeMenuText.draw(game.batch, "OPTIONS", 494, 90);
		}else {
			inactiveMenuText.draw(game.batch, "OPTIONS", 494, 90);
		}
		//CREDITS
		if (790 < mX && mX < 1102 && 37 < mY && mY < 95){
			activeMenuText.draw(game.batch, "CREDITS", 860, 90);
		}else {
			inactiveMenuText.draw(game.batch, "CREDITS", 860, 90);
		}

		//QUIT
		if (1160 < mX && mX < 1380 && 37 < mY && mY < 95){
			activeMenuText.draw(game.batch, "QUIT", 1220, 90);
		}else{
			inactiveMenuText.draw(game.batch, "QUIT", 1220, 90);
		}

		//***********************************		
		//FRAMES PER SECOND
		//**********************************
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
			if (130 < mX && mX < 380 && 37 < mY && mY < 95)  {
				Mutagen.clicking();
				game.setScreen(new DifficultyScreen(game));
			}

			//OPTIONS
			if (435 < mX && mX < 756 && 37 < mY && mY < 95){
				themeMusic.stop();
				Mutagen.clicking();
				game.setScreen(new Options(game));
			}
			//CREDITS
			if (790 < mX && mX < 1102 && 37 < mY && mY < 95){
				themeMusic.stop();
				Mutagen.clicking();
				game.setScreen(new Credits(game));
			}

			//QUIT
			if (1160 < mX && mX < 1380 && 37 < mY && mY < 95){
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
