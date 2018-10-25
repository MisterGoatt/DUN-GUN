package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	Sprite player;
	TextureRegion textureRegion;
	MapLayer objectLayer;
	Texture texture;
	//private TiledMapTileLayer terrainLayer;
	//private int[] decorationLayersIndices;
	private int[] layerBackround = {0};
	private int[] layerAfterBackground = {2};
	private int pSpeed = 3;
	
	
	public Level1(final DunGun game) {
		
		
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/Level1PlaceHolder5.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH, DunGun.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		//viewport.apply();
		       //cam.position.set((viewport.getWorldWidth() / DunGun.PPM) / 2, viewport.getWorldHeight() / DunGun.PPM, 0);

		//viewport.apply();
		//spriteBatch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/p1.atlas"));
		textureRegion = textureAtlas.findRegion("p1");
		player = new Sprite(textureRegion);
		player.setPosition((viewport.getWorldWidth() / 2), //places the player at the center of the camera
				(viewport.getWorldHeight() / 2)) ;
		//cam.position.x = (viewport.getWorldWidth() / 2) * DunGun.PPM;
		//cam.position.y = (viewport.getWorldHeight() / 2) * DunGun.PPM;
        
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
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			//player.translateY(pSpeed);
			//cam.translate(0, .02f);
			player.translate(0, pSpeed); 
		 	}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	//player.translateX(pSpeed);
        	player.translate(pSpeed, 0);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //player.translateX(-pSpeed);
        	player.translate(-pSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	player.translate(0, -pSpeed);
        	//player.translateY(-pSpeed);
        }
        

        cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight()/ 2, 0);
        cam.update(); //updates orthographic camera

        mapRenderer.setView(cam);
        
        //render our game map
        //mapRenderer.render(); // renders map
		mapRenderer.render(layerBackround); //renders layer in Tiled that player covers
        game.batch.setProjectionMatrix(cam.combined); 

		game.batch.begin(); //starts sprite spriteBatch

        player.draw(game.batch); //draws player sprite
        game.batch.end(); //starts sprite spriteBatch
        
        mapRenderer.render(layerAfterBackground); //renders layer of Tiled that hides player


        
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
		float pW = player.getWidth() / cam.zoom; //Keeps player scaled
		float pH = player.getHeight() / cam.zoom; // ^
		player.setSize(pW, pH); // Keeps players size matched regardless of zoom
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
