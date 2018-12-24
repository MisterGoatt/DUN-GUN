package com.bpa;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1 implements Screen{
	final Mutagen game;
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
	private Scientist scientist;
	public CreateBullet createBullet;
	private CollisionDetector cd;
	private Turret turret;
	
	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	private Sound assaultRifleShot, axeSwing, laserShot, shotgunShot, rifleShot, gunShot;
	private Music levelOneMusic;
	private Texture mouseCursor, axeMouseCursor, pauseMenu;
	private boolean lockCursor = true;
	private boolean gamePaused = false, startLaserCount = false, spawnEnemies = false, spawnOnce = true;
	private float waitToShootL = 0, elapsed = 0, duration, intensity, radius, randomAngle;	
	public static boolean axeSwinging = false, bulletImpact = false, isShooting = false;
	private boolean room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, room6 = true, room6t = true, room7 = true, room8 = true, room9 = true; //room spawn control

	//arrays of different game objects
	static Array<CreateBullet> lasers = new Array<CreateBullet>();
	static Array<Grunt> grunts = new Array<Grunt>();
	static Array<Scientist> scientists = new Array<Scientist>();
	static Array<Turret> turrets = new Array<Turret>();
	static Array<CreateBullet> pellets = new Array<CreateBullet>();
	static Array<CreateBullet> bullets = new Array<CreateBullet>();
	public static Vector2 gruntPos = new Vector2(0,0);
	public static Vector2 scientistPos = new Vector2(0,0);
	public static Vector2 player1SpawnPos = new Vector2(0,0);
	public static Vector3 mousePosition = new Vector3(0, 0, 0);
	
	Random random;



	public Level1(final Mutagen game) {
		this.game = game;

		cam = new OrthographicCamera();		
		gamePort = new FitViewport(Mutagen.V_WIDTH / Mutagen.PPM, Mutagen.V_HEIGHT / Mutagen.PPM, cam);
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		cam.zoom -= .40;
		
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;
		map = new TmxMapLoader().load("tileMaps/Level1/untitled.tmx", params);
		mouseCursor = Mutagen.manager.get("crosshair 1.png", Texture.class);
		axeMouseCursor = Mutagen.manager.get("axeCursor.png");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Mutagen.PPM);

		//Box2d variables
		world = new World(new Vector2(0, 0), true); // no gravity and yes we want to sleep objects (won't calculate simulations for bodies at rest)
		b2dr = new Box2DDebugRenderer();

		MapLayer playerLayer = map.getLayers().get("player");
		for (MapObject mo : playerLayer.getObjects()) {
			player1SpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
			player1SpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
			playerOne = new PlayerOne(world); //must be created after world creation or will crash
		}
		cd = new CollisionDetector();
		new B2DWorldCreator(world, map);
		random = new Random();

		//emptying the arrays of bullet textures and setting static variables to default
		grunts.clear();
		scientists.clear();
		turrets.clear();
		pellets.clear();
		lasers.clear();
		bullets.clear();
		TurretBullets.turretBullets.clear();

		gunShot = Mutagen.manager.get("sound effects/pistol_shot.mp3", Sound.class);
		rifleShot = Mutagen.manager.get("sound effects/rifleShot.mp3", Sound.class);
		shotgunShot = Mutagen.manager.get("sound effects/shotgun2.mp3", Sound.class);
		assaultRifleShot = Mutagen.manager.get("sound effects/assaultRifle.mp3", Sound.class);
		laserShot = Mutagen.manager.get("sound effects/laserBlast3.mp3", Sound.class);
		axeSwing = Mutagen.manager.get("sound effects/axeSwing.mp3", Sound.class);
		pauseMenu = Mutagen.manager.get("screens/Pause.jpg", Texture.class);

		levelOneMusic = Mutagen.manager.get("music/levelOne.mp3");
		levelOneMusic.setLooping(true);
		levelOneMusic.setVolume(Mutagen.musicVolume);
		levelOneMusic.play();
		this.world.setContactListener(cd);
	}

	//Creation of bullet objects and playing shooting and swinging sound effects
	public void shootGun() {
		if (isShooting) {
			//waitToShootL += 1;
			switch (GunSelectionScreen.weaponSelected) {
			case "laser":
				startLaserCount = true;
				if (Mutagen.sfxVolume != 0) {
					long lsId = laserShot.play(Mutagen.sfxVolume / 2);
				}
				shake(.2f, 400);
				break;
			case "revolver":
				if (Mutagen.sfxVolume != 0) {
					long gsId = gunShot.play(Mutagen.sfxVolume - .7f);
				}
				shake(.08f, 100);

				break;
			case "rifle":
				if (Mutagen.sfxVolume != 0) {
					long rsId = rifleShot.play(Mutagen.sfxVolume - .7f);
				}
				shake(.1f, 200);
				break;
			case "shotgun":
				//controls how many shotgun shells are shot
				for (int i = 0; i < 6; i++) {
					createBullet = new CreateBullet(world);
					pellets.add(createBullet);
				}
				shake(.1f, 200);

				if (Mutagen.sfxVolume != 0) {

					long sS = shotgunShot.play(Mutagen.sfxVolume - .7f);
				}
				break;
			case "assault rifle":
				if (Mutagen.sfxVolume != 0) {
					long arsId = assaultRifleShot.play(Mutagen.sfxVolume - .7f);
				}
				shake(.08f, 100);
				break;
			case "battle axe":
				if (Mutagen.sfxVolume != 0) {
					long baId = axeSwing.play(Mutagen.sfxVolume - .7f);
				}
				axeSwinging = true;
				break;
			}

			if (GunSelectionScreen.weaponSelected != "shotgun" && GunSelectionScreen.weaponSelected != "laser") {				
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

	public void shake(float intensity, float duration) {
		this.elapsed = 0;
		this.duration = duration / 1000f;
		this.intensity = intensity;
	}

	public void cameraUpdate(float delta) {
		//timeStep = 60 times a second, velocity iterations = 6, position iterations = 2
		world.step(1/60f, 6, 2); //tells game how many times per second for Box2d to make its calculations
		removeBodies(); //goes to method that removes physics bodies

		shootGun(); //sees if gun is shooting
		/* a = current camera position b = target
		 * a+(b-a)*lerp
		 * the higher the lerp value the more instantaneous
		 */
		cam.position.x = cam.position.x + (playerOne.b2body.getPosition().x - cam.position.x) * .05f;
		cam.position.y = cam.position.y + (playerOne.b2body.getPosition().y - cam.position.y) * .05f;
		// Only screen shake when required.
		if (elapsed < duration) {
			// Calculate the amount of shake based on how long it has been shaking already
			float currentPower = intensity * cam.zoom * ((duration - elapsed) / duration);
			float x = (random.nextFloat() - 0.5f) * currentPower;
			float y = (random.nextFloat() - 0.5f) * currentPower;
			cam.translate(-x, -y);
			// Increase the elapsed time by the delta provided.
			elapsed += delta;
		}
		cam.update();
	}

	public void removeBodies() {
		//remove bullets
		Array<Body> bodiesToRemove = cd.getbodiesToRemove();
		//removes bullets when they collide with wall
		for (int i = 0; i < bodiesToRemove.size; i ++) {
			Body b = bodiesToRemove.get(i);
			Object u = b.getUserData();
			if (u instanceof CreateBullet) {	
				if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
						|| GunSelectionScreen.weaponSelected == "assault rifle") {
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
			if (u instanceof Scientist) {
				scientists.removeValue((Scientist) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Turret) {
				turrets.removeValue((Turret) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof TurretBullets) {
				TurretBullets.turretBullets.removeValue((TurretBullets) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
		}
		bodiesToRemove.clear();
		if (GunSelectionScreen.weaponSelected == "battle axe" && PlayerOne.axeBodyRemoval) {
			world.destroyBody(createBullet.b2body);
			PlayerOne.axeBodyRemoval = false;
			bullets.clear();
			createBullet.b2body = null;
		}
	}

	public void spawningLocations() {

		if (playerOne.b2body.getPosition().x < 6.6 && playerOne.b2body.getPosition().x > 6.5 && 
				playerOne.b2body.getPosition().y < 3.9 && playerOne.b2body.getPosition().y > 3.2){
			if (room2) {
				MapLayer layer = map.getLayers().get("room2g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
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
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
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
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
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
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				MapLayer layer2 = map.getLayers().get("room4s");
				for (MapObject mo : layer2.getObjects()) {
					scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					scientists.add(scientist);
				}
				room4 = false;
			}


		}
		if (playerOne.b2body.getPosition().x < 11.8 && playerOne.b2body.getPosition().x > 11.1 && 
				playerOne.b2body.getPosition().y < 10.3 && playerOne.b2body.getPosition().y > 10.2){
			if (room5) {
				MapLayer layer = map.getLayers().get("room5g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				MapLayer layer2 = map.getLayers().get("room5s");
				for (MapObject mo : layer2.getObjects()) {
					scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					scientists.add(scientist);
				}
				room5 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 7.4 && playerOne.b2body.getPosition().x > 7.3 && 
				playerOne.b2body.getPosition().y < 12.8 && playerOne.b2body.getPosition().y > 12.1){

			if (room6) {
				MapLayer layer = map.getLayers().get("room6g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room6 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 9.9 && playerOne.b2body.getPosition().x > 9.8 && 
				playerOne.b2body.getPosition().y < 16 && playerOne.b2body.getPosition().y > 15){
			System.out.println("hello?");
			if (room6t) {
				MapLayer layer = map.getLayers().get("room6t");
				for (MapObject mo : layer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					turrets.add(turret);
				}
				room6t = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 10.1 && playerOne.b2body.getPosition().x > 10 && 
				playerOne.b2body.getPosition().y < 21.4 && playerOne.b2body.getPosition().y > 20.7){
			if (room8) {
				MapLayer layer = map.getLayers().get("room8g");
				for (MapObject mo : layer.getObjects()) {
					gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					grunts.add(grunt);
				}
				room8 = false;
			}
		}
		if (playerOne.b2body.getPosition().x < 4.8 && playerOne.b2body.getPosition().x > 4.7 && 
				playerOne.b2body.getPosition().y < 20.8 && playerOne.b2body.getPosition().y > 20.1){
			if (room9) {
				MapLayer layer = map.getLayers().get("room9s");
				for (MapObject mo : layer.getObjects()) {
					scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					scientists.add(scientist);
				}
				room9 = false;
			}
		}


		//GAME BEATEN
		if (playerOne.b2body.getPosition().x < .6 && playerOne.b2body.getPosition().x > 0 &&
				playerOne.b2body.getPosition().y < 20.8 && playerOne.b2body.getPosition().y > 19.8) {
			playerOne.runningSound.stop();
			Gdx.input.setCursorCatched(false);
			levelOneMusic.stop();
			game.setScreen(new levelCompleted(game));
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
		//hides the mouse and displays cross hair		
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
			playerOne.runningSound.stop();
			game.batch.begin(); //starts sprite spriteBatch
			cam.position.x = 0;
			cam.position.y = 0;
			game.batch.draw(pauseMenu, 0 - (350/Mutagen.PPM), 0 - (200 / Mutagen.PPM), 1500 / 200,  800 / 200);
			lockCursor = false;	
			if (Gdx.input.isButtonPressed(Input.Keys.LEFT)) {
				//RESUME
				if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < 0.6 && mousePosition.y > -.02) {
					gamePaused = false;
					lockCursor = true;
				}
				//MAIN MENU
				else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.13 && mousePosition.y > -.78) {
					levelOneMusic.stop();
					game.setScreen(new MainMenu(game));
				}
				//QUIT
				else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.86 && mousePosition.y > -1.49) {
					Gdx.app.exit();
				}
			}
			cam.update();
			game.batch.end(); //starts sprite spriteBatch

		}else if (!gamePaused){ //********PLAY********
			//laser delay for build up of power effect
			if (startLaserCount) {
				waitToShootL += 1;
			}
			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				cam.zoom += .1f;
			}
			cameraUpdate(delta);
			mapRenderer.render();
			//b2dr.render(world, cam.combined);
			game.batch.begin(); //starts sprite spriteBatch

			//RENDER DIFFERENT TEXTURES AND ANIMATIONS OVER BODY OBJECTS
			for (int i = 0; i < grunts.size; i++) {
				grunts.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < scientists.size; i++) {
				scientists.get(i).renderSprite(game.batch);
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
			for (int i = 0; i < turrets.size; i++) {
				turrets.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < TurretBullets.turretBullets.size; i++) {
				TurretBullets.turretBullets.get(i).renderSprite(game.batch);
			}
			//Goes to method that handles spawning the enemies
			spawningLocations();
			if (!PlayerOne.p1Dead) {
				playerOne.handleInput(delta);
				playerOne.renderSprite(game.batch);	        	
			}
			if (GunSelectionScreen.weaponSelected == "battle axe") {
				game.batch.draw(axeMouseCursor, mousePosition.x - .05f, mousePosition.y - .05f, 21 / Mutagen.PPM, 21/ Mutagen.PPM);
			}else { 
				game.batch.draw(mouseCursor, mousePosition.x - .05f, mousePosition.y - .05f, 13 / Mutagen.PPM, 13 / Mutagen.PPM);
			}
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
