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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Level1 implements Screen{
	final DunGun game;
	//TMapLocations level1Map;
	public OrthographicCamera cam;
	public Viewport gamePort;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; 
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	//Sprite p1;
	TextureRegion textureRegion;
	MapLayer objectLayer;
	public static Vector3 mousePosition = new Vector3(0, 0, 0);
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr; //graphical representation of body fixtures
	private PlayerOne playerOne;
	private Grunt grunt;
	public CreateBullet createBullet;
	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	public static boolean isShooting = false;
	//ArrayList<CreateBullet> bulletManager = new ArrayList<CreateBullet>();
	private Sound gunShot;
	private Sound rifleShot;
	private Sound shotgunShot;
	private Sound laserShot;
	private Sound assaultRifleShot;
	private Sound bulletHitWall;
	private Sound laserHitWall;
	private Sound pelletHitWall;
	private Sound axeSwing;
	private Texture mouseCursor;
	private boolean lockCursor = true;
	private Texture pauseMenu;
	private boolean gamePaused = false;
	private boolean once = false; //makes sure the viewport on pause menu screen only changes once
	private float waitToShootL = 0;
	private boolean start = false;
	public static boolean axeSwinging = false;
	public static boolean bulletImpact = false;
	//arrays of different game objects
	static Array<CreateBullet> lasers = new Array<CreateBullet>();
	static Array<Grunt> grunts = new Array<Grunt>();
	static Array<CreateBullet> pellets = new Array<CreateBullet>();
	static Array<CreateBullet> bullets = new Array<CreateBullet>();


	//	BitmapFont framerate; //font for frame rate display
//	private long startTime = System.currentTimeMillis();
//	private long counter;
	
	public Level1(final DunGun game) {
		this.game = game;
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(DunGun.V_WIDTH / DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		cam.zoom -= .50;
		
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;
		map = new TmxMapLoader().load("tileMaps/Level1/customset2.tmx", params);
		mouseCursor = DunGun.manager.get("crosshair 1.png", Texture.class);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / DunGun.PPM);
       
		//Box2d variables
		world = new World(new Vector2(0, 0), true); // no gravity and yes we want to sleep objects (won't calculate simulations for bodies at rest)
		b2dr = new Box2DDebugRenderer();
		playerOne = new PlayerOne(world); //must be created after world creation or will crash
		
		//
		grunts.clear();
		pellets.clear();
		lasers.clear();
		bullets.clear();
		
		grunt = new Grunt(world);
		grunts.add(grunt);

		new B2DWorldCreator(world, map);
		
		//framerate = DunGun.manager.get("fonts/CourierNew32.fnt", BitmapFont.class) ;
		
		gunShot = DunGun.manager.get("sound effects/pistol_shot.mp3", Sound.class);
		rifleShot = DunGun.manager.get("sound effects/rifleShot.mp3", Sound.class);
		shotgunShot = DunGun.manager.get("sound effects/shotgun2.mp3", Sound.class);
		assaultRifleShot = DunGun.manager.get("sound effects/assaultRifle.mp3", Sound.class);
		laserShot = DunGun.manager.get("sound effects/laserBlast3.mp3", Sound.class);
		bulletHitWall = DunGun.manager.get("sound effects/bulletImpact.mp3", Sound.class);
		laserHitWall = DunGun.manager.get("sound effects/laserImpact.mp3", Sound.class);
		pelletHitWall = DunGun.manager.get("sound effects/pelletImpact.mp3", Sound.class);
		axeSwing = DunGun.manager.get("sound effects/axeSwing.mp3", Sound.class);
		pauseMenu = DunGun.manager.get("screens/Pause.jpg", Texture.class);
		this.world.setContactListener(new MyContactListener());
	}
	//Creation of bullet objects and playing shooting and swinging sound effects
	public void shootGun() {
		if (isShooting) {
			//waitToShootL += 1;
			
			
			if (GunSelectionScreen.weaponSelected == "laser") {
				start = true;
				long lsId = laserShot.play(.5f);
			}
			else if (GunSelectionScreen.weaponSelected == "revolver") {
				long gsId = gunShot.play(.5f);
			}
			else if (GunSelectionScreen.weaponSelected == "rifle") {
				long rsId = rifleShot.play(.5f);
			}

			else if (GunSelectionScreen.weaponSelected == "shotgun") {
				//controls how many shotgun shells are shot
				for (int i = 0; i < 6; i++) {
					createBullet = new CreateBullet(world);
					pellets.add(createBullet);
				}
				shotgunShot.play();
			}
			else if (GunSelectionScreen.weaponSelected == "assault rifle") {
				long arsId = assaultRifleShot.play(.5f);
			}
			else if (GunSelectionScreen.weaponSelected == "battle axe"){
				createBullet = new CreateBullet(world);
				long baId = axeSwing.play(.7f);
				axeSwinging = true;
				
				
			}
			
			if (GunSelectionScreen.weaponSelected != "shotgun" && GunSelectionScreen.weaponSelected != "laser"  && GunSelectionScreen.weaponSelected != "battle axe") {
				createBullet = new CreateBullet(world);
				bullets.add(createBullet);
			}
			isShooting = false;
			
		}
		//laser blast delay
		if (waitToShootL >= 20){
			createBullet = new CreateBullet(world);
			lasers.add(createBullet);
			waitToShootL = 0;
			start = false;
			}
	}

		


	public void cameraUpdate(float delta) {
		
	
		//timeStep = 60 times a second, velocity iterations = 6, position iterations = 2
		world.step(1/60f, 6, 2); //tells game how many times per second for Box2d to make its calculations
		
		//remove bullets

		Array<Body> bulletBodies = MyContactListener.bulletsToRemove;
		Array<Body> gruntBodies = MyContactListener.gruntsToRemove;
		//removes bullets when they collide with wall
		for (int i = 0; i < bulletBodies.size; i ++) {
			Body b = bulletBodies.get(i);
			if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
					|| GunSelectionScreen.weaponSelected == "assault rifle") {
				long bhwId = bulletHitWall.play(.1f);
				bullets.removeValue((CreateBullet)b.getUserData(), true);
			}
			if (GunSelectionScreen.weaponSelected == "laser") {
				lasers.removeValue((CreateBullet)b.getUserData(), true);
				laserHitWall.play();
			}
			else if (GunSelectionScreen.weaponSelected == "shotgun") {
				pellets.removeValue((CreateBullet)b.getUserData(), true);
				long phwId = pelletHitWall.play(.1f);
			}
			world.destroyBody(b);
		}
		
		bulletBodies.clear(); //empties list of bodies
		
		
		
		
		
		//REMOVE GRUNT BODIES AND REMOVE FROM MANAGER LIST
		for (int e = 0; e < gruntBodies.size; e ++) {
			Body g = gruntBodies.get(e);
			grunts.removeValue((Grunt) g.getUserData(), true);
			world.destroyBody(g);
		}
	
		gruntBodies.clear();
		
		
		cam.position.x = playerOne.b2body.getPosition().x;
		cam.position.y = playerOne.b2body.getPosition().y;
		cam.update();
	}
	
	public void viewPortSwitch() {
		gamePort = new StretchViewport(1500, 800, cam);
		once = false;
	}
	
	
	
	@Override
	public void render(float delta) {

		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  
		//laser delay for build up of power effect
		if (start) {
			waitToShootL += 1;
		}
		
		//hides the mouse and displays crosshair		
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !lockCursor) {
			lockCursor = true;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && lockCursor) {
			lockCursor = false;
		}
		if (lockCursor) {
			Gdx.input.setCursorCatched(true);
		}else Gdx.input.setCursorCatched(false);
		
		//pauses game and pulls up menu
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !gamePaused) {
			gamePaused = true;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && gamePaused) {
			gamePaused = false;
		}
		//GAME NOT PAUSED!!
		if (!gamePaused) { 
			cameraUpdate(delta);
			playerOne.handleInput(delta);
			mapRenderer.render();
	        //b2dr.render(world, cam.combined);
        }
		//mapRenderer.render(layerBackround); //renders layer in Tiled that p1 covers		
        
        game.batch.begin(); //starts sprite spriteBatch
    	if (axeSwinging) {
    		
    		createBullet.renderSprite(game.batch);
    	}
        
        //RENDER DIFFERENT TEXTURES AND ANIMATIONS OVER GAME OBJECTS
        for (int i = 0; i < grunts.size; i++) {
    		//grunt.renderSprite(game.batch);
            grunts.get(i).renderSprite(game.batch);
    	}
        
        for (int i = 0; i < lasers.size; i++) {
        	lasers.get(i).renderSprite(game.batch);
        }
        for (int i = 0; i < pellets.size; i++) {
        	pellets.get(i).renderSprite(game.batch);
        }
        for (int i = 0; i < bullets.size; i++) {
        	bullets.get(i).renderSprite(game.batch);
        }

//        if (CreateBullet.laserManager != null) {
//	        for (int i = 0; i < CreateBullet.laserManager.size; i++) {
//	            CreateBullet.laserManager.get(i).renderSprite(game.batch);
//	    	}
//    	}
        
        
       //GAME IS PAUSED*******************
        if (gamePaused) {
        	cam.position.x = 0;
        	cam.position.y = 0;
        	game.batch.draw(pauseMenu, 0 - (350/DunGun.PPM), 0 - (200 / DunGun.PPM), 1500 / 200,  800 / 200);
        	lockCursor = false;
        	if (once) {
        		viewPortSwitch();
        	}
        	
        	if (Gdx.input.isButtonPressed(Input.Keys.LEFT)) {
        		//RESUME
        		if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < 0.6 && mousePosition.y > -.02) {
        			gamePaused = false;
        		}
        		//MAIN MENU
        		else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.13 && mousePosition.y > -.78) {
        			game.setScreen(new MainMenu(game));
        		}
        		//QUIT
        		else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.86 && mousePosition.y > -1.49) {
					Gdx.app.exit();
        		}
        	}
        	
        	
        	cam.update();
        	//game.batch.draw(pauseMenu, playerOne.b2body.getPosition().x - (350 / DunGun.PPM), playerOne.b2body.getPosition().y - (200 / DunGun.PPM), 1500 / 200,  800 / 200);
        	
        }
        if (!gamePaused) {
            shootGun(); //sees if gun is shooting
        	playerOne.renderSprite(game.batch);
        	game.batch.draw(mouseCursor, Level1.mousePosition.x - .05f, Level1.mousePosition.y - .05f, 13 / DunGun.PPM, 13 / DunGun.PPM);
        	mapRenderer.setView(cam);
        }

        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        
        cam.unproject(mousePosition); //gets mouse coordinates within viewport
		
//		//FRAMES PER SECOND
//		//**********************************
//		counter = (System.currentTimeMillis() - startTime) / 1000;
//		int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
//		String frames = Integer.toString(f); //converts frames per second to a string
//		framerate.draw(game.batch, frames, 5, 785); //displays frames per second as text in top left

		//**********************************
		game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement

        game.batch.end(); //starts sprite spriteBatch
        //mapRenderer.render(layerAfterBackground); //renders layer of Tiled that hides p1
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height); //updates the viewport camera

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
		pauseMenu.dispose();
	}

	@Override
	public void show() {

		
	}
	
}
