package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import BackEnd.PointSystem;
import levels.Level2;
import levels.Level3;

public class levelCompleted implements Screen{
	final Mutagen game;
	Texture levelComplete;
	Texture highScore;
	private Viewport gamePort;
	private OrthographicCamera cam;
	private float yPos = -1000;
	private Music lvlComplete;

	BitmapFont inactiveMenuText;
	BitmapFont activeMenuText;
	private Vector3 mousePosition = new Vector3(0, 0, 0);
	private float mX, mY;

	public levelCompleted(final Mutagen game) {
		this.game = game;
		levelComplete = Mutagen.manager.get("screens/levelCompletedScreen.jpg");
		levelComplete.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		highScore = Mutagen.manager.get("screens/highScore.jpg");
		highScore.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		inactiveMenuText = Mutagen.manager.get("fonts/inactiveMenu(36).fnt", BitmapFont.class);
		activeMenuText = Mutagen.manager.get("fonts/activeMenu(36).fnt", BitmapFont.class);


		cam = new OrthographicCamera();
		gamePort = new FitViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		lvlComplete = Mutagen.manager.get("music/lvlComplete.mp3");
		if (Mutagen.musicVolume > 0) {
			lvlComplete.setVolume(Mutagen.musicVolume - .3f);
			lvlComplete.setLooping(true);
			lvlComplete.play();
		}
	}


	@Override
	public void render(float delta) {
		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePosition);
		
		mX = mousePosition.x;
		mY = mousePosition.y;

		game.batch.begin(); 
		game.batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Mutagen.level != "3") {
			game.batch.draw(levelComplete, 0, yPos);
		}else if (Mutagen.level == "3") {
			game.batch.draw(highScore, 0, yPos);
		}
		if (yPos > -1570) { // Moves the screen down
			yPos -= 10;
		}
		else {
			if (Mutagen.level != "3") {
				game.batch.draw(levelComplete, 0, yPos);
			
				String summed = Integer.toString(PointSystem.sum); 
				inactiveMenuText.draw(game.batch, "CURRENT SCORE: " + summed, 515, 100);
				
			}
			
			else if (Mutagen.level == "3") {// plays the mission complete screen after level 3
				
				inactiveMenuText.draw(game.batch, "CURRENT SCORE: " + PointSystem.y, 500, 290);
				inactiveMenuText.draw(game.batch, "HIGH SCORE: " + PointSystem.x, 500, 490);
				
				if (35 < mX && mX < 290 && 57 < mY && mY < 110){
					activeMenuText.draw(game.batch, "MAIN MENU", 42, 88);
					
					if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
						lvlComplete.stop();
						game.setScreen(new MainMenu(game));
					}
				}else {
					inactiveMenuText.draw(game.batch, "MAIN MENU", 42, 88);
				}
			}
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if (Mutagen.level == "1") {
					lvlComplete.stop();
					game.setScreen(new Level2(game));
					
				}
				else if (Mutagen.level == "2") {
					lvlComplete.stop();
					game.setScreen(new Level3(game));
				}
			}
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
		game.batch.dispose();
		levelComplete.dispose();
	}

}
