package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;



public class Level1 implements Screen{
	private OrthographicCamera cam;
	final DunGun game;
	//TMapLocations level1Map;
	private Viewport gamePort;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; //references map itself
	private OrthogonalTiledMapRenderer renderer; //renders map to the screen
	
	public Level1(final DunGun game) {
		this.game = game;
		//level1Map = new L1TMLoad();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 320); // centers camera on the 20x20 tile map(which equals 320x320)
		gamePort = new FitViewport(DunGun.V_WIDTH, DunGun.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust

		
		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Tutorial/TileMap1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
	}

	@Override
	public void render(float delta) {
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(cam.combined);

		//int mX = Gdx.input.getX();
		//int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		
		if (Gdx.input.isTouched()) {
			cam.translate(Gdx.input.getDeltaX() * (-1), Gdx.input.getDeltaY());
		}
		
		
		/*if (Gdx.input.justTouched()) {
			Vector3 pos = cam.unproject(new Vector3 (Gdx.input.getX(), Gdx.input.getY(), 0)); //getting location of the screen and converts to game world coordinates
			TileType type = level1Map.getTileTypeByLocation(0, pos.x, pos.y);
			if (type != null) {
				System.out.println("id: " + type.getId() + " " + type.getName() + " " + type.isCollidable() + " " + type.getDamage());
			}
		} */
		//cam.update();
		renderer.setView(cam);
		renderer.render();

		//level1Map.render(cam);
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
		map.dispose();
		renderer.dispose();
	}
}
