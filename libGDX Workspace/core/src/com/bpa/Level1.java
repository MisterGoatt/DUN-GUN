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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Level1 implements Screen{
	final DunGun game;
	//TMapLocations level1Map;
	public OrthographicCamera cam;
	public Viewport viewport;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; 
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	TextureAtlas textureAtlas;
	Sprite p1;
	TextureRegion textureRegion;
	MapLayer objectLayer;
	private ShapeRenderer shapeRenderer;
	public static Vector3 mouse_position = new Vector3(0, 0, 0);
	private Vector3 camPos = new Vector3(0, 0, 0);


	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	
	public Level1(final DunGun game) {
		
		
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/top-downTest.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH, DunGun.V_HEIGHT, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust

		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/TDPlayer.atlas"));
		textureRegion = textureAtlas.findRegion("TDPlayer");
		p1 = new Player(new Sprite(new Texture("sprites/TDPlayer.png")), (TiledMapTileLayer)map.getLayers().get(0));
		p1.setPosition(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);

		shapeRenderer = new ShapeRenderer();
        
		
		Gdx.input.setInputProcessor((InputProcessor) p1);
		cam.zoom -= .70;
	}
	
	public void cameraUpdate(float delta) {

		camPos.set(Math.round(p1.getX()), Math.round(p1.getY()), 0);
		cam.position.set(camPos);
		cam.unproject(camPos);
		cam.update();
	}
	
	@Override
	public void render(float delta) {
		
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	 	if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			cam.zoom -= .01;

		}
	 	if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			cam.zoom += .01;
			
		}
		
        
        mapRenderer.render();
       
        /*shapeRenderer.setProjectionMatrix(cam.combined); //keeps circle from doing weird out of sync movement
        shapeRenderer.setColor(100, 100, 100, 0);
        shapeRenderer.begin(ShapeType.Line);
        //shapeRenderer.circle(p1.getX() + 16, p1.getY() + 10, 10);
        shapeRenderer.rect(p1.getX() + 7, p1.getY(), 16, 16);
        shapeRenderer.end();
        */
        //cam.position.set(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight()/ 2, 0);
        //cam.update(); //updates orthographic camera

        mapRenderer.setView(cam);
        cameraUpdate(delta);
        
        
        
        //render our game map
        //mapRenderer.render(); // renders map
		//mapRenderer.render(layerBackround); //renders layer in Tiled that p1 covers
        game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement
		
        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
        
        game.batch.begin(); //starts sprite spriteBatch

        p1.draw(game.batch); //draws p1 sprite

        game.batch.end(); //starts sprite spriteBatch


        //mapRenderer.render(layerAfterBackground); //renders layer of Tiled that hides p1
		
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); //updates the viewport camera
		float pW = p1.getWidth(); //Keeps p1 scaled
		float pH = p1.getHeight(); // ^
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
		shapeRenderer.dispose();
		

	}

	@Override
	public void show() {

		
	}
	
}
