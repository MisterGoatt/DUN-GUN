package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Level1 implements Screen{
	final DunGun game;
	//TMapLocations level1Map;
	private OrthographicCamera cam;
	private Viewport viewport;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; 
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	TextureAtlas textureAtlas;
	Sprite p1;
	TextureRegion textureRegion;
	MapLayer objectLayer;
	private ShapeRenderer shapeRenderer;
	//private TiledMapTileLayer terrainLayer;
	//private int[] decorationLayersIndices;
	private int[] layerBackround = {0, 1, 2, 3};
	private int[] layerAfterBackground = {4};
	private int pSpeed = 3;
	
	public Level1(final DunGun game) {
		
		
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/top-downMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH, DunGun.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		//viewport.apply();
		       //cam.position.set((viewport.getWorldWidth() / DunGun.PPM) / 2, viewport.getWorldHeight() / DunGun.PPM, 0);

		//viewport.apply();
		//spriteBatch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/soldier1.atlas"));
		textureRegion = textureAtlas.findRegion("soldier1");
		p1 = new Player(new Sprite(textureRegion));
		p1.setPosition((viewport.getWorldWidth() / 2), //places the p1 at the center of the camera
				(viewport.getWorldHeight() / 2)) ;
		//cam.position.x = (viewport.getWorldWidth() / 2) * DunGun.PPM;
		//cam.position.y = (viewport.getWorldHeight() / 2) * DunGun.PPM;
		shapeRenderer = new ShapeRenderer();
        
		
		Gdx.input.setInputProcessor((InputProcessor) p1);
		//cam.zoom -= .45;
	}
	
	@Override
	public void render(float delta) {
		
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
	 	if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			cam.zoom -= .01;

			//cam.translate(Gdx.input.getDeltaX() * (-1), Gdx.input.getDeltaY());
		}
	 	if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			cam.zoom += .01;
			
		}
		
        
        mapRenderer.render();

        shapeRenderer.setProjectionMatrix(cam.combined); //keeps circle from doing weird out of sync movement
        shapeRenderer.setColor(100, 100, 100, 0);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.circle(p1.getX(), p1.getY(), 10);
        shapeRenderer.end();;
        
        cam.position.set(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight()/ 2, 0);
        cam.update(); //updates orthographic camera

        mapRenderer.setView(cam);
        
        //render our game map
        //mapRenderer.render(); // renders map
		//mapRenderer.render(layerBackround); //renders layer in Tiled that p1 covers
        game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement

		game.batch.begin(); //starts sprite spriteBatch

        p1.draw(game.batch); //draws p1 sprite
        game.batch.end(); //starts sprite spriteBatch


        //mapRenderer.render(layerAfterBackground); //renders layer of Tiled that hides p1

        
		/*
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		if (mY < 100 && mX < 100 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));

		}*/
		
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); //updates the viewport camera
		float pW = p1.getWidth() / cam.zoom; //Keeps p1 scaled
		float pH = p1.getHeight() / cam.zoom; // ^
		p1.setSize(pW, pH); // Keeps players size matched regardless of zoom
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
		mapRenderer.dispose();
		textureAtlas.dispose();
		

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
