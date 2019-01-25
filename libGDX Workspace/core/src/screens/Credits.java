package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.LogFileHandler;
import BackEnd.Mutagen;

public class Credits implements Screen, InputProcessor {
	final Mutagen game;
	Texture credits;
	private Viewport gamePort;
	private OrthographicCamera cam;
	private float yPos = -5700;
	private boolean buttonPressed = false;
	Music creditsMusic = Mutagen.manager.get("music/creditsSong.ogg");
	LogFileHandler lfh = new LogFileHandler();

	public Credits(final Mutagen game) {
		this.game = game;
		try {
			credits = Mutagen.manager.get("screens/ScrollingCN.jpg");
			credits.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

			cam = new OrthographicCamera();
			gamePort = new FitViewport(1500, 800, cam);
			cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
			creditsMusic.setVolume(Mutagen.musicVolume);
			creditsMusic.play();
			Gdx.input.setInputProcessor(this);

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
			game.batch.begin();
			game.batch.setProjectionMatrix(cam.combined);

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// Controls the speed at which the credits scroll
			if (yPos < 0) {
				yPos += 3;
			}

			// displays the credits
			game.batch.draw(credits, 0, yPos);

			// mouse x and y
			int mX = Gdx.input.getX();
			int mY = Gdx.graphics.getHeight() - Gdx.input.getY();

			cam.update();

			game.batch.end();
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	@Override
	public void resize(int width, int height) {
		try {
			gamePort.update(width, height);
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
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
		try {
			game.batch.dispose();
			credits.dispose();
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
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
			creditsMusic.stop();
			game.setScreen(new MainMenu(game));
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
