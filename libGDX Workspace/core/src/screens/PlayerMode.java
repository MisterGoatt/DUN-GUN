package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import entities.Mutagen;

public class PlayerMode implements Screen, InputProcessor{

	final Mutagen game;
	public Viewport gamePort;
	private Texture playerModeScreen;
	private OrthographicCamera cam;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	public static boolean OneP = false;
	private float mX, mY;
	private boolean buttonPressed = false;
	BitmapFont activeText, inactiveText, backText, backActiveText;

	public PlayerMode(final Mutagen game) {
		this.game = game;
		playerModeScreen = Mutagen.manager.get("screens/playerModeScreen.jpg");
		cam = new OrthographicCamera();		
		gamePort = new StretchViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		Gdx.input.setInputProcessor(this);
		backText = Mutagen.manager.get("fonts/backText(68).fnt", BitmapFont.class);
		inactiveText =  Mutagen.manager.get("fonts/inactiveText(100).fnt", BitmapFont.class);
		activeText = Mutagen.manager.get("fonts/activeText(100).fnt", BitmapFont.class);
		backActiveText = Mutagen.manager.get("fonts/backActiveText(68).fnt", BitmapFont.class);
	}


	@Override
	public void render(float delta) {

		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
		game.batch.setProjectionMatrix(cam.combined);
		
		//mouse x and y
		mX = mouse_position.x;
		mY = mouse_position.y;
		game.batch.begin();
		game.batch.draw(playerModeScreen, 0, 0);
		//single player
		if ( mX < 1145 && mX > 407 && mY < 660 && mY > 509) {
			activeText.draw(game.batch, "SINGLE PLAYER", 525, 620);
		}else {
			inactiveText.draw(game.batch, "SINGLE PLAYER", 525, 620);
		}
		//co-op
		if ( mX < 1145 && mX > 407 && mY < 431 && mY > 282) {
			activeText.draw(game.batch, "CO-OP", 675, 390);

		}else {
			inactiveText.draw(game.batch, "CO-OP", 675, 390);

		}
		//back
		if (mX < 271 && mX > 104 && mY < 110 && mY > 40) {
			backActiveText.draw(game.batch, " BACK", 115, 98);

		}else {
			backText.draw(game.batch, " BACK", 115, 98);
			
		}

		game.batch.end();
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	
	@Override
	public void show() {
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

	}


	@Override
	public boolean keyDown(int keycode) {
		
		
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			if (!buttonPressed){
				//single player
				if ( mX < 1145 && mX > 407 && mY < 660 && mY > 509) {
		    		game.setScreen(new GunSelectionScreen(game));
		    		OneP = true;
				}
				//two player
				if ( mX < 1145 && mX > 407 && mY < 431 && mY > 282) {
		    		game.setScreen(new GunSelectionScreen(game));
		    		OneP = false;
				}			
				//back button
				if ( mX < 271 && mX > 104 && mY < 110 && mY > 40) {
					game.setScreen(new MainMenu(game));
				}
			}			
		}
		buttonPressed = true;
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if (button!= Input.Buttons.LEFT) {
			buttonPressed = false;	
		}
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
