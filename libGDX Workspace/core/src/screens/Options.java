package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.LogFileHandler;
import BackEnd.Mutagen;




public class Options implements Screen, InputProcessor {
	final Mutagen game;
	private Viewport gamePort;
	private OrthographicCamera cam;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	Texture allSelected;
	Texture noneSelected;
	Texture musicSelected;
	Texture sfxSelected;
	static boolean music = true, sfx = true;
	private boolean buttonPressed;
	private float mX, mY;
	LogFileHandler lfh = new LogFileHandler();

	public Options(final Mutagen game) {
		this.game = game;

		try {
			cam = new OrthographicCamera();
			gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam); // fits view port to match map's
			// dimensions (in this case 320x320) and
			// scales. Adds black bars to adjust
			cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
			allSelected = Mutagen.manager.get("screens/options/optionsScreenAll.jpg");
			noneSelected = Mutagen.manager.get("screens/options/optionsScreenNone.jpg");
			musicSelected = Mutagen.manager.get("screens/options/optionsScreenMusic.jpg");
			sfxSelected = Mutagen.manager.get("screens/options/optionsScreenSFX.jpg");
			allSelected.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			noneSelected.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			musicSelected.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			sfxSelected.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

			Gdx.input.setInputProcessor(this);
			if (Mutagen.musicVolume == 0) {
				music = false;
			}
			if (Mutagen.sfxVolume == 0) {
				sfx = false;
			}
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}

	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		try {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			game.batch.begin();
			mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(mouse_position); // gets mouse coordinates within viewport
			game.batch.setProjectionMatrix(cam.combined);

			// mouse x and y
			mX = mouse_position.x;
			mY = mouse_position.y;

			// Which screens to display depending on the on/off of the options
			if (music && sfx) {
				game.batch.draw(allSelected, 0, 0);
			} else if (music && !sfx) {
				game.batch.draw(musicSelected, 0, 0);
			} else if (!music && sfx) {
				game.batch.draw(sfxSelected, 0, 0);
			} else if (!music && !sfx) {
				game.batch.draw(noneSelected, 0, 0);
			}
			game.batch.end();
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
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

	}

	@Override
	public void hide() {

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
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!buttonPressed) {
			// MUSIC ON/OFF
			if (550 > mY && mY > 480 && 700 < mX && mX < 770) {
				if (music) {
					Mutagen.clicking();
					music = false;
					Mutagen.musicVolume = 0f;
				} else {
					music = true;
					Mutagen.musicVolume = .7f;
					Mutagen.clicking();

				}
			}
			// SFX ON/OFF
			if (409 > mY && mY > 346 && 700 < mX && mX < 790) {
				if (sfx) {
					sfx = false;
					Mutagen.sfxVolume = 0f;
				} else {
					sfx = true;
					Mutagen.sfxVolume = 1f;
					Mutagen.clicking();
				}
			}
			if (mY < 82 && mX < 153 && mY < 79 && mY > 22) {
				Mutagen.clicking();
				game.setScreen(new MainMenu(game));
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
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
