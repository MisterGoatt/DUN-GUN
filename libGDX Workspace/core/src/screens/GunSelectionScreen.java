package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import BackEnd.Mutagen;
import levels.Level1;

public class GunSelectionScreen implements Screen, InputProcessor{
	final Mutagen game;
	private Texture gunPickScreen, p1GS, p2GS, p1b, p1rot, p1dir, p2b, p2rot, p2dir;
	public Viewport gamePort;

	private OrthographicCamera cam;
	private Vector3 mousePosition = new Vector3(0, 0, 0);
	public static String p1WeaponSelected, p2WeaponSelected;
	private boolean buttonPressed = false, p1Screen = true, gunScr = true, next = false;
	public static int p1AimStyle, p2AimStyle;
	ShapeRenderer shapeR;

	public GunSelectionScreen(final Mutagen game) {
		this.game = game;
		gunPickScreen = Mutagen.manager.get("screens/gun_selection.jpg");
		p1GS = Mutagen.manager.get("screens/p1GS.jpg");
		p2GS = Mutagen.manager.get("screens/p2GS.jpg"); 
		p1b = Mutagen.manager.get("screens/aim style/aimStyle1b.jpg");
		p1rot = Mutagen.manager.get("screens/aim style/aimStylerot1.jpg");
		p1dir = Mutagen.manager.get("screens/aim style/aimStyledir1.jpg");
		p2b = Mutagen.manager.get("screens/aim style/aimStyle2b.jpg");
		p2rot = Mutagen.manager.get("screens/aim style/aimStylerot2.jpg");
		p2dir = Mutagen.manager.get("screens/aim style/aimStyledir2.jpg");

		gunPickScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);		
		p1GS.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p2GS.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p1b.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p2b.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p1rot.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p1dir.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p2rot.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		p2dir.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		p1AimStyle = 0;
		p2AimStyle = 0;
		p1AimStyle = 0; 
		p2AimStyle = 0;
		cam = new OrthographicCamera();		
		gamePort = new StretchViewport(1500, 800, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		Gdx.input.setInputProcessor(this);
		shapeR = new ShapeRenderer();
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
		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePosition); //gets mouse coordinates within viewport
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		if (gunScr) {
			if (PlayerMode.OneP) {
				game.batch.draw(gunPickScreen, 0, 0);	
			}else {
				if (p1Screen) {
					game.batch.draw(p1GS, 0, 0);
				}else {
					game.batch.draw(p2GS, 0, 0);
				}
			}
		}else {
			
			if (p1AimStyle == 0) {
				game.batch.draw(p1b, 0, 0);
			}else if (p1AimStyle == 1) {
				game.batch.draw(p1rot, 0, 0);
			}else if (p1AimStyle == 2) {
				game.batch.draw(p1dir, 0, 0);
			}
			
			if (next) {
				if (p2AimStyle == 0) {
					game.batch.draw(p2b, 0, 0);
				}else if (p2AimStyle == 1) {
					game.batch.draw(p2rot, 0, 0);
				}else if (p2AimStyle == 2) {
					game.batch.draw(p2dir, 0, 0);
				}				
			}

			
			
		}
		game.batch.end();
		shapeR.begin(ShapeType.Filled);
		shapeR.setColor(255, 0 ,0, 0);
		shapeR.end();
		
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
		shapeR.dispose();
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
			if (gunScr) {
				//REVOLVER 
				if (mousePosition.x > 785 && mousePosition.x < 1090 && mousePosition.y > 512 && mousePosition.y < 615) {
					if (p1Screen) {
						p1WeaponSelected = "revolver";
						p1Screen = false;
						if (PlayerMode.OneP) {
							MainMenu.themeMusic.stop();
							game.setScreen(new Story(game));					
						}
					}else {
						p2WeaponSelected = "revolver";
						MainMenu.themeMusic.stop();
						gunScr = false;

					}
				}
				//bolt-action rifle
				else if (mousePosition.x > 418 && mousePosition.x < 720 && mousePosition.y > 380 && mousePosition.y < 480) {
					if (p1Screen) {
						p1WeaponSelected = "rifle";
						p1Screen = false;
						if (PlayerMode.OneP) {
							MainMenu.themeMusic.stop();
							Mutagen.clicking();
							game.setScreen(new Story(game));					
						}

					}else {
						p2WeaponSelected = "rifle";
						MainMenu.themeMusic.stop();	
						Mutagen.clicking();
						gunScr = false;

					}
				}
				//Assault rifle
				else if (mousePosition.x > 418 && mousePosition.x < 720 && mousePosition.y > 520 && mousePosition.y < 619) {
					if (p1Screen) {
						p1WeaponSelected = "assault rifle";
						p1Screen = false;
						if (PlayerMode.OneP) {
							MainMenu.themeMusic.stop();
							Mutagen.clicking();
							game.setScreen(new Story(game));					


						}

					}else {
						p2WeaponSelected = "assault rifle";
						MainMenu.themeMusic.stop();	
						Mutagen.clicking();
						gunScr = false;

					}
				}
				//shotgun
				else if ((mousePosition.x > 418 && mousePosition.x < 720 && mousePosition.y > 247 && mousePosition.y < 350)) {
					if (p1Screen) {
						p1WeaponSelected = "shotgun";
						p1Screen = false;
						if (PlayerMode.OneP) {
							MainMenu.themeMusic.stop();
							Mutagen.clicking();
							game.setScreen(new Story(game));					

						}

					}else {
						p2WeaponSelected = "shotgun";
						MainMenu.themeMusic.stop();	
						Mutagen.clicking();
						gunScr = false;

					}
				}
				//Laser
				if (mousePosition.x > 785 && mousePosition.x < 1090 && mousePosition.y > 380 && mousePosition.y < 487) {
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
						gunScr = false;

					}
				}
				//Battle axe
				if (mousePosition.x > 785 && mousePosition.x < 1090 && mousePosition.y > 248 && mousePosition.y < 365) {
					if (p1Screen) {
						p1WeaponSelected = "battle axe";
						p1Screen = false;
						if (PlayerMode.OneP) {
							MainMenu.themeMusic.stop();
							Mutagen.clicking();
							game.setScreen(new Story(game));					

						}

					}else {
						p2WeaponSelected = "battle axe";
						MainMenu.themeMusic.stop();	
						Mutagen.clicking();
						gunScr = false;

					}
				}
				//Back button
				if (mousePosition.x > 41 && mousePosition.x < 194 && mousePosition.y > 30 && mousePosition.y < 108) {
					Mutagen.clicking();
					game.setScreen(new PlayerMode(game));
				}
			}
			else {
				
				//player 1 rotational
				if (mousePosition.x > 116 && mousePosition.x < 341 && mousePosition.y > 56 && mousePosition.y < 134) {
					p1AimStyle = 1;
					Mutagen.clicking();

				}
				//player 1 directional
				else if (mousePosition.x > 354 && mousePosition.x < 586 && mousePosition.y > 56 && mousePosition.y < 134) {
					p1AimStyle = 2;
					Mutagen.clicking();

				}
				//player 2 rotational
				else if (mousePosition.x > 908 && mousePosition.x < 1141 && mousePosition.y > 56 && mousePosition.y < 134) {
					p2AimStyle = 1;
					Mutagen.clicking();

				}
				//player 2 directional
				else if (mousePosition.x > 1153 && mousePosition.x < 1382 && mousePosition.y > 56 && mousePosition.y < 134) {
					p2AimStyle = 2;
					Mutagen.clicking();

				}
				//next button
				if (p1AimStyle > 0 && p2AimStyle == 0) {
					if (mousePosition.x > 639 && mousePosition.x < 857 && mousePosition.y > 44 && mousePosition.y < 150) {
						Mutagen.clicking();
						next = true;
					}
				}
				//play button
				if (p2AimStyle > 0) {
					if (mousePosition.x > 639 && mousePosition.x < 857 && mousePosition.y > 44 && mousePosition.y < 150) {
						Mutagen.clicking();
						game.setScreen(new Level1(game));					
					}
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
