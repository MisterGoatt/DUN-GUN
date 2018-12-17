package com.bpa;

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
import com.badlogic.gdx.maps.MapObject;
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
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr; //graphical representation of body fixtures
	private PlayerOne playerOne;
	private Grunt grunt;
	public CreateBullet createBullet;
	private CollisionDetector cd;
;
	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	private Sound assaultRifleShot, axeSwing, laserShot, shotgunShot, rifleShot, gunShot;
	private Texture p1HP, mouseCursor, pauseMenu;
	private boolean lockCursor = true;
	private boolean gamePaused = false, viewPortChangeOnce = false, startLaserCount = false, spawnEnemies = false, spawnOnce = true;
	private float waitToShootL = 0;	
	public static boolean axeSwinging = false, bulletImpact = false, isShooting = false;
	private boolean room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, room6 = true, room7 = true, room8 = true, room9 = true; //room spawn control
	//arrays of different game objects
	static Array<CreateBullet> lasers = new Array<CreateBullet>();
	static Array<Grunt> grunts = new Array<Grunt>();
	static Array<CreateBullet> pellets = new Array<CreateBullet>();
	static Array<CreateBullet> bullets = new Array<CreateBullet>();
	public static Vector2 gruntPos = new Vector2(0,0);
	public static Vector2 player1SpawnPos = new Vector2(0,0);
	public static Vector3 mousePosition = new Vector3(0, 0, 0);


	
	public Level1(final DunGun game) {
		this.game = game;
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(DunGun.V_WIDTH / DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		cam.zoom -= .40;
		
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;
		map = new TmxMapLoader().load("tileMaps/Level1/untitled.tmx", params);
		mouseCursor = DunGun.manager.get("crosshair 1.png", Texture.class);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / DunGun.PPM);
       
		//Box2d variables
		world = new World(new Vector2(0, 0), true); // no gravity and yes we want to sleep objects (won't calculate simulations for bodies at rest)
		b2dr = new Box2DDebugRenderer();
		
		MapLayer playerLayer = map.getLayers().get("player");
		for (MapObject mo : playerLayer.getObjects()) {
			player1SpawnPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
			player1SpawnPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
			playerOne = new PlayerOne(world); //must be created after world creation or will crash

		}
		
		cd = new CollisionDetector();
		//emptying the arrays of bullet textures and setting static variables to default
		grunts.clear();
		pellets.clear();
		lasers.clear();
		bullets.clear();		
		
		new B2DWorldCreator(world, map);
		
		//frame rate = DunGun.manager.get("fonts/CourierNew32.fnt", BitmapFont.class) ;
		
		gunShot = DunGun.manager.get("sound effects/pistol_shot.mp3", Sound.class);
		rifleShot = DunGun.manager.get("sound effects/rifleShot.mp3", Sound.class);
		shotgunShot = DunGun.manager.get("sound effects/shotgun2.mp3", Sound.class);
		assaultRifleShot = DunGun.manager.get("sound effects/assaultRifle.mp3", Sound.class);
		laserShot = DunGun.manager.get("sound effects/laserBlast3.mp3", Sound.class);
		axeSwing = DunGun.manager.get("sound effects/axeSwing.mp3", Sound.class);
		pauseMenu = DunGun.manager.get("screens/Pause.jpg", Texture.class);
		p1HP = DunGun.manager.get("sprites/player1/hp.png");
		this.world.setContactListener(cd);
	}
	
	//Creation of bullet objects and playing shooting and swinging sound effects
	public void shootGun() {
		if (isShooting) {
			//waitToShootL += 1;
			switch (GunSelectionScreen.weaponSelected) {
			
			case "laser":
				startLaserCount = true;
				long lsId = laserShot.play(.5f);
				break;
			case "revolver":
				long gsId = gunShot.play(.3f);

				break;
			case "rifle":
				long rsId = rifleShot.play(.3f);
				break;
			case "shotgun":
				//controls how many shotgun shells are shot
				for (int i = 0; i < 6; i++) {
					createBullet = new CreateBullet(world);
					pellets.add(createBullet);
				}
				long sS = shotgunShot.play(.3f);
				break;
			case "assault rifle":
				long arsId = assaultRifleShot.play(.3f);
				break;
			case "battle axe":
				long baId = axeSwing.play(.7f);
				axeSwinging = true;
				break;
			}
			
			if (GunSelectionScreen.weaponSelected != "shotgun" && GunSelectionScreen.weaponSelected != "laser") {
				System.out.println("here");
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
			startLaserCount = false;
			}
	}

	public void cameraUpdate(float delta) {
		

		//timeStep = 60 times a second, velocity iterations = 6, position iterations = 2
		world.step(1/60f, 6, 2); //tells game how many times per second for Box2d to make its calculations
		//remove bullets
		Array<Body> bodiesToRemove = cd.getbodiesToRemove();


		//removes bullets when they collide with wall
		for (int i = 0; i < bodiesToRemove.size; i ++) {
			Body b = bodiesToRemove.get(i);
				
			Object u = b.getUserData();
			if (u instanceof CreateBullet) {	
				if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
						|| GunSelectionScreen.weaponSelected == "assault rifle" || GunSelectionScreen.weaponSelected == "battle axe" ) {
					bullets.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.weaponSelected == "laser") {
					lasers.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.weaponSelected == "shotgun") {
					pellets.removeValue((CreateBullet)b.getUserData(), true);
				}
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Grunt) {
				grunts.removeValue((Grunt) b.getUserData(), true);
				world.destroyBody(b);

				b = null;
			}
		}
		bodiesToRemove.clear();

		
		
//		if (GunSelectionScreen.weaponSelected == "battle axe" && PlayerOne.axeBodyRemoval) {
//			world.destroyBody(createBullet.b2body);
//			PlayerOne.axeBodyRemoval = false;
//			bullets.clear();
//		}
		
        shootGun(); //sees if gun is shooting

		cam.position.x = playerOne.b2body.getPosition().x;
		cam.position.y = playerOne.b2body.getPosition().y;
		cam.update();
	}
	
	public void viewPortSwitch() {
		gamePort = new StretchViewport(1500, 800, cam);
		viewPortChangeOnce = false;
	}
	
	
	public void spawningLocations() {

		if (playerOne.b2body.getPosition().x < 6.6 && playerOne.b2body.getPosition().x > 6.5 && 
				playerOne.b2body.getPosition().y < 3.9 && playerOne.b2body.getPosition().y > 3.2){
			if (room2) {
				MapLayer layer = map.getLayers().get("room2g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room2 = false;
			}

		}
		
		if (playerOne.b2body.getPosition().x < 4.8 && playerOne.b2body.getPosition().x > 4.1 && 
				playerOne.b2body.getPosition().y < 1.4 && playerOne.b2body.getPosition().y > 1.3){
			if (room1) {
				MapLayer layer = map.getLayers().get("room1g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room1 = false;
			}

		
		}
		if (playerOne.b2body.getPosition().x < 8.6 && playerOne.b2body.getPosition().x > 8 && 
				playerOne.b2body.getPosition().y < 4.2 && playerOne.b2body.getPosition().y > 4.1){
			if (room3) {
				MapLayer layer = map.getLayers().get("room3g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room3 = false;
			}

		
		}
		if (playerOne.b2body.getPosition().x < 8.6 && playerOne.b2body.getPosition().x > 8 && 
				playerOne.b2body.getPosition().y < 6.5 && playerOne.b2body.getPosition().y > 6.4){
			if (room4) {
				MapLayer layer = map.getLayers().get("room4g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room4 = false;
			}

		
		}
		if (playerOne.b2body.getPosition().x < 11.8 && playerOne.b2body.getPosition().x > 11.1 && 
				playerOne.b2body.getPosition().y < 10.3 && playerOne.b2body.getPosition().y > 10.2){
			if (room5) {
				MapLayer layer = map.getLayers().get("room5g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room5 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 7.4 && playerOne.b2body.getPosition().x > 7.3 && 
				playerOne.b2body.getPosition().y < 12.8 && playerOne.b2body.getPosition().y > 12.1){
			System.out.println("room 6");

			if (room6) {
				MapLayer layer = map.getLayers().get("room6g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room6 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 10.1 && playerOne.b2body.getPosition().x > 10 && 
				playerOne.b2body.getPosition().y < 21.4 && playerOne.b2body.getPosition().y > 20.7){
			System.out.println("room 8");
			if (room8) {
				MapLayer layer = map.getLayers().get("room8g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room8 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 4.8 && playerOne.b2body.getPosition().x > 4.7 && 
				playerOne.b2body.getPosition().y < 20.8 && playerOne.b2body.getPosition().y > 20.1){
			System.out.println("room 9");

			if (room9) {
				MapLayer layer = map.getLayers().get("room9g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / DunGun.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / DunGun.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room9 = false;
			}
		}
			
		
	}
	

	@Override
	public void render(float delta) {

		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//pauses game and pulls up menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !gamePaused) {
			gamePaused = true;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && gamePaused) {
			gamePaused = false;
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
  		
        //*********GAME IS PAUSED*********
        if (gamePaused) {
            game.batch.begin(); //starts sprite spriteBatch

        	cam.position.x = 0;
        	cam.position.y = 0;
        	game.batch.draw(pauseMenu, 0 - (350/DunGun.PPM), 0 - (200 / DunGun.PPM), 1500 / 200,  800 / 200);
        	lockCursor = false;
        	if (viewPortChangeOnce) {
        		viewPortSwitch();
        	}
        	
        	if (Gdx.input.isButtonPressed(Input.Keys.LEFT)) {
        		//RESUME
        		if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < 0.6 && mousePosition.y > -.02) {
        			gamePaused = false;
        			lockCursor = true;
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
            game.batch.end(); //starts sprite spriteBatch
            
        }else if (!gamePaused){ //********GAME IS NOT PAUSED********
        	//laser delay for build up of power effect
        	if (startLaserCount) {
				waitToShootL += 1;
			}
        	
			cameraUpdate(delta);
			mapRenderer.render();
	        //b2dr.render(world, cam.combined);
	        game.batch.begin(); //starts sprite spriteBatch
	        
	        //RENDER DIFFERENT TEXTURES AND ANIMATIONS OVER GAME OBJECTS

	        for (int i = 0; i < grunts.size; i++) {
	    		//grunt.renderSprite(game.batch);
	        	grunts.get(i).renderSprite(game.batch);
	        	grunt = grunts.get(i);
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
	        
	        spawningLocations();
	        
	        if (!PlayerOne.p1Dead) {
				playerOne.handleInput(delta);

	        	playerOne.renderSprite(game.batch);	        	
		    	game.batch.draw(p1HP, PlayerOne.p1PosX - .35f, PlayerOne.p1PosY - .18f, PlayerOne.player1HP / (DunGun.PPM + 50), 3f / DunGun.PPM);

	        }
	    	game.batch.draw(mouseCursor, mousePosition.x - .05f, mousePosition.y - .05f, 13 / DunGun.PPM, 13 / DunGun.PPM);
	    	//game.batch.draw(p1HP, PlayerOne.p1PosX, PlayerOne.p1PosY - .5f, PlayerOne.player1HP, 1);
	    	mapRenderer.setView(cam);
	        game.batch.end(); //starts sprite spriteBatch
	        
        } //closing bracket for game not paused
        
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mousePosition); //gets mouse coordinates within viewport
        game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement
        //System.out.println(mousePosition);
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height); //updates the viewport camera

	}
	@Override
	public void pause() {

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
	}

	@Override
	public void show() {

		
	}
	
}
