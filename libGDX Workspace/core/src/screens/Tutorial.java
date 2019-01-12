package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.Mutagen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Tutorial implements Screen, InputProcessor{
	final Mutagen game;
	Texture tutorialOptions;
	private OrthographicCamera cam;
	private Viewport gamePort;
	boolean justClicked = false;
	boolean onCrash = true, buttonPressed;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	BitmapFont inactiveMenuText;
	BitmapFont activeMenuText;
	private int wait = 0;
	private float mX, mY;


	public Tutorial(final Mutagen game) {
		this.game = game;
		tutorialOptions = Mutagen.manager.get("screens/tutorials/tutorialOptions.jpg");
		tutorialOptions.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		cam = new OrthographicCamera();		
		gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0); //centers the map to center of screen
		Gdx.input.setInputProcessor(this);

		inactiveMenuText = Mutagen.manager.get("fonts/inactiveMenu(36).fnt", BitmapFont.class);
		activeMenuText = Mutagen.manager.get("fonts/activeMenu(36).fnt", BitmapFont.class);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mouse_position);
		game.batch.begin();
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.draw(tutorialOptions, 0, 0);
		mX = mouse_position.x;
		mY = mouse_position.y;

			if (0 < mX && mX < 130 && 0 < mY && mY < 80)
			{
				inactiveMenuText.draw(game.batch, "BACK", 10, 55);
			}else {
				inactiveMenuText.draw(game.batch, "BACK", 10, 55);
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
		tutorialOptions.dispose();
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
			
			if (784 < mX && mX < 1479 && 389 < mY && mY < 594)
			{game.setScreen(new HowToPlay(game));

				}
			
			if (20 < mX && mX < 708 && 394 < mY && mY < 597 )
			{
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new ControlScreen(game));
				}
			}

			if (7 < mX && mX < 123 && 8 < mY && mY < 61)
			{
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					game.setScreen(new MainMenu(game));

				}
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
