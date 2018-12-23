package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Options implements Screen{
	final Mutagen game;
	
	private Viewport gamePort;
	private OrthographicCamera cam;
	private Vector3 mouse_position = new Vector3(0, 0, 0);
	Texture allSelected;
	Texture noneSelected;
	Texture musicSelected;
	Texture sfxSelected;
	static boolean music = true, sfx = true, clickWait = false;
	private int clickWaitTimer = 0;

	public Options(final Mutagen game) {
		this.game = game;
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(Mutagen.V_WIDTH, Mutagen.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		allSelected = Mutagen.manager.get("screens/credits/optionsScreenAll.jpg");
		noneSelected = Mutagen.manager.get("screens/credits/optionsScreenNone.jpg");
		musicSelected = Mutagen.manager.get("screens/credits/optionsScreenMusic.jpg");
		sfxSelected = Mutagen.manager.get("screens/credits/optionsScreenSFX.jpg");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin(); 
        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
		System.out.println(mouse_position);
		game.batch.setProjectionMatrix(cam.combined);

		//mouse x and y
		float mX = mouse_position.x;
		float mY = mouse_position.y;
		
		if (mY < 82 && mX < 153 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));
		}
		
		//MUSIC ON/OFF
		if (550 > mY && mY > 480 && 700 < mX && mX < 780 && Gdx.input.isButtonPressed(Buttons.LEFT) && !clickWait) {
			clickWait = true;
			clickWaitTimer = 0;
			if (music) {
				music = false;
				Mutagen.musicVolume = 0f;
			}else {
				music = true;
				Mutagen.musicVolume = 1f;
			}
		}
		//SFX ON/OFF
		else if (409 > mY && mY > 346 && 700 < mX && mX < 790 && Gdx.input.isButtonPressed(Buttons.LEFT) && !clickWait) {
			clickWait = true;
			clickWaitTimer = 0;
			if (sfx) {
				sfx = false;
				Mutagen.sfxVolume = 0f;

			}else {
				sfx = true;
				Mutagen.sfxVolume = 1f;
			}
		}
		
		//Which screens to display depending on the on/off of the options
		if (music && sfx) {
			game.batch.draw(allSelected, 0, 0);
		}else if (music && !sfx) {
			game.batch.draw(musicSelected, 0, 0);
		}else if (!music && sfx) {
			game.batch.draw(sfxSelected, 0, 0);
		}else if (!music && !sfx) {
			game.batch.draw(noneSelected, 0, 0);
		}
		clickWaitTimer +=1;
		if (clickWaitTimer > 10) {
			clickWait = false;
		}
		
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
	public void dispose() {
		game.batch.dispose();
	}

}
