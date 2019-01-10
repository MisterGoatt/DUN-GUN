package screens;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.Mutagen;

public class IntroductionScreens implements Screen{
	final Mutagen game;
	boolean skipToMainM;
	private long counter;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	Texture musicScreen;
	private long startTime = System.currentTimeMillis();
	private Viewport gamePort;
	private OrthographicCamera cam;

	public IntroductionScreens(final Mutagen game) {
		this.game = game;

		cam = new OrthographicCamera();		
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		publisherScreen = Mutagen.manager.get("screens/intro screens/ctm_placeholder.jpg");
		creditScreen = Mutagen.manager.get("screens/intro screens/introCredits.jpg");
		titleScreen = Mutagen.manager.get("screens/intro screens/introTitle.jpg");
		musicScreen = Mutagen.manager.get("screens/intro screens/musicScreen.jpg");
		publisherScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		creditScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		titleScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		musicScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		//DRAWS INTRO SCREENS
		//**********************************
		game.batch.begin();
		if (skipToMainM == false) {
			if (counter <= 1.5) {
				game.batch.draw(publisherScreen, 0, 0);
			}
			else if (counter <=  3.5 && counter > 1.5) {
				game.batch.draw(titleScreen, 0, 0);

			}
			else if (counter >= 3.6 && counter < 6.6) {
				game.batch.draw(creditScreen, 0, 0);
			}
			//RYAN'S CREDIT
			else if (counter >= 6.7 && counter <= 7.7){
				game.batch.draw(musicScreen, 0, 0);

			}else game.setScreen(new MainMenu(game));
			//skips the introduction screens
			if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
				Mutagen.clicking();
				game.setScreen(new MainMenu(game));
			}
			else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				Mutagen.clicking();
				game.setScreen(new MainMenu(game));

			}

			//FRAMES PER SECOND
			//**********************************
			counter = (System.currentTimeMillis() - startTime) / 1000;
			game.batch.end();
			cam.update();


		}
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
	public void show() {
		// TODO Auto-generated method stub

	}


	@Override
	public void dispose() {
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		musicScreen.dispose();

	}
}
