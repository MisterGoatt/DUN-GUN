package com.bpa;


import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.MapLayer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
	//Sprite p1;
	TextureRegion textureRegion;
	MapLayer objectLayer;
	public static Vector3 mouse_position = new Vector3(0, 0, 0);

	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr; //graphical representation of body fixtures
	private PlayerOne playerOne;
	public CreateBullet createBullet;
	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	public static boolean isShooting = false;
	ArrayList<CreateBullet> bulletManager = new ArrayList<CreateBullet>();
	private Sound gunShot;
	private Texture mouseCursor;
	private boolean lockCursor = true;

	
	
	public Level1(final DunGun game) {
		this.game = game;

		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH / DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;
		map = new TmxMapLoader().load("tileMaps/Level1/customset2.tmx", params);
		//map = maploader.load("tileMaps/Level1/customset.tmx");
		mouseCursor = new Texture("crosshair 1.png");
		//maploader.load("tileMaps/Level1/customset.tmx", params);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / DunGun.PPM);
       
		//Box2d variables
		world = new World(new Vector2(0, 0), true); // no gravity and yes we want to sleep objects (won't calculate simulations for bodies at rest)
		b2dr = new Box2DDebugRenderer();
		playerOne = new PlayerOne(world); //must be created after world creation or will crash
		new B2DWorldCreator(world, map);
		cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		cam.zoom -= .50;
		
		gunShot = Gdx.audio.newSound(Gdx.files.internal("sound effects/pistol_shot.mp3"));

		this.world.setContactListener(new MyContactListener());
	}
	
	public void shootGun() {
		if (isShooting) {
			//System.out.println(bulletManager.size());
			createBullet = new CreateBullet(world);
			gunShot.play();
			bulletManager.add(createBullet);
			isShooting = false;
			}
		}


	
	public void cameraUpdate(float delta) {

		//timeStep = 60 times a second, velocity iterations = 6, position iterations = 2
		world.step(1/60f, 6, 2); //tells game how many times per second for Box2d to make its calculations
		
		//remove bullets
		Array<Body> bodies = MyContactListener.bodiesToRemove;
		//removes bullets when they collide with wall
		for (int i = 0; i < bodies.size; i ++) {

			Body b = bodies.get(i);
			CreateBullet.bullets.removeValue((CreateBullet) b.getUserData(), true);
			world.destroyBody(b);
			bulletManager.remove(0);
		}
	
		bodies.clear(); //empties list of bodies
		
		cam.position.x = playerOne.b2body.getPosition().x;

		cam.position.y = playerOne.b2body.getPosition().y;
		cam.update();
	}
	
	@Override
	public void render(float delta) {
		
		
		
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && lockCursor) {
			lockCursor = false;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !lockCursor) {
			lockCursor = true;
		}
		if (!lockCursor) {
			Gdx.input.setCursorCatched(true);
		}else Gdx.input.setCursorCatched(false);
		
		cameraUpdate(delta);
        playerOne.handleInput(delta);
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	 	/*(if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			cam.zoom -= .01;

		}
	 	if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			cam.zoom += .01;
	 	}*/	
		
        
        mapRenderer.render();
        //b2dr.render(world, cam.combined); //renders the Box2d world

  
		//mapRenderer.render(layerBackround); //renders layer in Tiled that p1 covers
        game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement
        shootGun(); //sees if gun is shooting
        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse_position); //gets mouse coordinates within viewport
        game.batch.begin(); //starts sprite spriteBatch
        playerOne.renderSprite(game.batch);
        /*if (bulletManager.size() > 0) {
        	for (int i = 0; i < bulletManager.size(); i++) {
        		createBullet.renderSprite(game.batch);
                bulletManager.get(i).renderSprite(game.batch);
        	}
        }*/
        
        game.batch.draw(mouseCursor, Level1.mouse_position.x - .05f, Level1.mouse_position.y - .05f, 13 / DunGun.PPM, 13 / DunGun.PPM);


        game.batch.end(); //starts sprite spriteBatch
        //mapRenderer.render(layerAfterBackground); //renders layer of Tiled that hides p1
        mapRenderer.setView(cam);

	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height); //updates the viewport camera

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
		world.dispose();
		b2dr.dispose();
		gunShot.dispose();
		mouseCursor.dispose();
	}

	@Override
	public void show() {

		
	}
	
}
