package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Level1 implements Screen{
	private OrthographicCamera cam;
	final DunGun game;
	L1TMap gameMap;
	private Viewport gamePort;

	
	public Level1(final DunGun game) {
		this.game = game;
		gameMap = new TiledGameMap();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 320); // centers camera on the 20x20 tile map(which equals 320x320)
		//cam.zoom = 10;//zooms down on the map
		gamePort = new FitViewport(320, 320, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin(); 
		//int mX = Gdx.input.getX();
		//int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		
		
		if (Gdx.input.isTouched()) {
			cam.translate(Gdx.input.getDeltaX() * (-1), Gdx.input.getDeltaY());
		}
		
		
		if (Gdx.input.justTouched()) {
			Vector3 pos = cam.unproject(new Vector3 (Gdx.input.getX(), Gdx.input.getY(), 0)); //getting location of the screen and converts to game world coordinates
			TileType type = gameMap.getTileTypeByLocation(0, pos.x, pos.y);
			if (type != null) {
				System.out.println("id: " + type.getId() + " " + type.getName() + " " + type.isCollidable() + " " + type.getDamage());
			}
		}
		cam.update();

		gameMap.render(cam);
		game.batch.end();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
