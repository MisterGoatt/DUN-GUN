package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.Mutagen;
import levels.Level1;

public class GunSelectionScreen implements Screen, InputProcessor{
	final Mutagen game;
	private Texture gunPickScreen, p1GS, p2GS;
	public Viewport gamePort;

	private OrthographicCamera cam;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	public static String p1WeaponSelected, p2WeaponSelected;
	private boolean buttonPressed = false, p1Screen = true;
	public GunSelectionScreen(final Mutagen game) {
		this.game = game;
		gunPickScreen = Mutagen.manager.get("screens/gun_selection.jpg");
		p1GS = Mutagen.manager.get("screens/p1GS.jpg");
		p2GS = Mutagen.manager.get("screens/p2GS.jpg");

		cam = new OrthographicCamera();		
		gamePort = new StretchViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		Gdx.input.setInputProcessor(this);
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}



	@Override
	public void render(float delta) {
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mouse_position); //gets mouse coordinates within viewport
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();    
		if (PlayerMode.OneP) {
			game.batch.draw(gunPickScreen, 0, 0);	
		}else {
			if (p1Screen) {
				game.batch.draw(p1GS, 0, 0);
			}else {
				game.batch.draw(p2GS, 0, 0);
			}
		}
		game.batch.end();
		cam.update();

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
			//REVOLVER 
			if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 512 && mouse_position.y < 615) {
				if (p1Screen) {
					p1WeaponSelected = "revolver";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "revolver";
					MainMenu.themeMusic.stop();	
					game.setScreen(new Level1(game));
				}

			}
			//bolt-action rifle
			else if (mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 380 && mouse_position.y < 480) {
				if (p1Screen) {
					p1WeaponSelected = "rifle";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						Mutagen.clicking();

						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "rifle";
					MainMenu.themeMusic.stop();	
					Mutagen.clicking();

					game.setScreen(new Level1(game));
				}
			}
			//Assault rifle
			else if (mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 520 && mouse_position.y < 619) {
				if (p1Screen) {
					p1WeaponSelected = "assault rifle";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						Mutagen.clicking();

						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "assault rifle";
					MainMenu.themeMusic.stop();	
					Mutagen.clicking();

					game.setScreen(new Level1(game));
				}
			}
			//shotgun
			else if ((mouse_position.x > 418 && mouse_position.x < 720 && mouse_position.y > 247 && mouse_position.y < 350)) {
				if (p1Screen) {
					p1WeaponSelected = "shotgun";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						Mutagen.clicking();
						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "shotgun";
					MainMenu.themeMusic.stop();	
					Mutagen.clicking();
					game.setScreen(new Level1(game));
				}
			}
			//Laser
			if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 380 && mouse_position.y < 487) {
				if (p1Screen) {
					p1WeaponSelected = "laser";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						Mutagen.clicking();
						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "laser";
					MainMenu.themeMusic.stop();	
					Mutagen.clicking();
					game.setScreen(new Level1(game));
				}
			}
			//Battle axe
			if (mouse_position.x > 785 && mouse_position.x < 1090 && mouse_position.y > 248 && mouse_position.y < 365) {
				if (p1Screen) {
					p1WeaponSelected = "battle axe";
					p1Screen = false;
					if (PlayerMode.OneP) {
						MainMenu.themeMusic.stop();
						Mutagen.clicking();
						game.setScreen(new Level1(game));
					}

				}else {
					p2WeaponSelected = "battle axe";
					MainMenu.themeMusic.stop();	
					Mutagen.clicking();
					game.setScreen(new Level1(game));
				}
			}
			//Back button
			if (mouse_position.x > 41 && mouse_position.x < 194 && mouse_position.y > 30 && mouse_position.y < 108) {
				Mutagen.clicking();
				game.setScreen(new PlayerMode(game));
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
