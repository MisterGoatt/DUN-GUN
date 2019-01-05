package levels;

import java.util.Random;

import javax.swing.plaf.synth.SynthSplitPaneUI;

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

import collisions.B2DWorldCreator;
import collisions.CollisionDetector;
import entities.CreateBullet;
import entities.Grunt;
import entities.HealthPickUp;
import entities.PlayerOne;
import entities.Scientist;
import entities.Soldier;
import entities.SoldierBullets;
import entities.Turret;
import entities.TurretBullets;
import screens.GunSelectionScreen;
import screens.MainMenu;
import screens.Mutagen;
import screens.PlayerMode;
import screens.levelCompleted;

public class Level1 implements Screen{
	final Mutagen game;
	public OrthographicCamera cam;
	public Viewport gamePort;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; 
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	TextureRegion textureRegion;
	MapLayer objectLayer;

	private World world;
	private Box2DDebugRenderer b2dr; //graphical representation of body fixtures
	private PlayerOne playerOne;
	private Grunt grunt;
	private Scientist scientist;
	public CreateBullet createBullet;
	private CollisionDetector cd;
	private Turret turret;
	private Soldier soldier;
	private HealthPickUp hpPickUp;

	//private int[] layerBackround = {0, 1, 2, 3};
	//private int[] layerAfterBackground = {4};
	private Music levelOneMusic;
	private Texture mouseCursor, axeMouseCursor, pauseMenu;
	private boolean lockCursor = true;
	private boolean gamePaused = false, spawnEnemies = false, spawnOnce = true;
	private float elapsed = 0, duration, intensity, radius, randomAngle;	
	public static boolean bulletImpact = false;
	private boolean room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, 
			room6 = true, room6t = true, room7 = true, room8 = true, room9 = true, hpSpawn = true; //room spawn control
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
		map = new TmxMapLoader().load("tileMaps/Level1/Level1Partial.tmx", params);
		mouseCursor = Mutagen.manager.get("crosshair 1.png", Texture.class);
		axeMouseCursor = Mutagen.manager.get("axeCursor.png");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Mutagen.PPM);

		//Box2d variables
		world = new World(new Vector2(0, 0), true); // no gravity and yes we want to sleep objects (won't calculate simulations for bodies at rest)
		b2dr = new Box2DDebugRenderer();
		MapLayer playerLayer = map.getLayers().get("Player");
		for (MapObject mo : playerLayer.getObjects()) {
			PlayerOne.player1SpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
			PlayerOne.player1SpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
			playerOne = new PlayerOne(world); //must be created after world creation or will crash
		}
		if (!PlayerMode.OneP) {
//			for (MapObject mo : playerLayer.getObjects()) {
//				PlayerTwo.player2SpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//				PlayerTwo.player2SpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//				playerTwo = new PlayerTwo(world);
//			}
		}

		cd = new CollisionDetector();
		new B2DWorldCreator(world, map);
		random = new Random();

		//emptying the arrays of bullet textures and setting static variables to default
		Grunt.grunts.clear();
		Scientist.scientists.clear();
		Turret.turrets.clear();
		PlayerOne.pellets.clear();
		PlayerOne.lasers.clear();
		PlayerOne.bullets.clear();
		TurretBullets.turretBullets.clear();
		HealthPickUp.hpPickUp.clear();
		Soldier.soldiers.clear();
		SoldierBullets.soldierBullets.clear();
		if (!PlayerMode.OneP) {
//			PlayerTwo.pellets.clear();
//			PlayerTwo.lasers.clear();
//			PlayerTwo.bullets.clear();
		}

		pauseMenu = Mutagen.manager.get("screens/Pause.jpg", Texture.class);
		levelOneMusic = Mutagen.manager.get("music/levelOne.mp3");
		levelOneMusic.setLooping(true);
		levelOneMusic.setVolume(Mutagen.musicVolume);
		levelOneMusic.play();
		this.world.setContactListener(cd);
		Gdx.input.setInputProcessor(null);
		soldier = new Soldier(world);
		Soldier.soldiers.add(soldier);

	}

	//Creation of bullet objects and playing shooting and swinging sound effects
	public void shootGun() {
		if (PlayerOne.timeToShake) {
			//waitToShootL += 1;
			switch (GunSelectionScreen.p1WeaponSelected) {
			case "laser":
				shake(.2f, 400);
				break;
			case "revolver":
				shake(.08f, 100);
				break;
			case "rifle":
				shake(.1f, 200);
				break;
			case "shotgun":
				shake(.1f, 200);
				break;
			case "assault rifle":
				shake(.08f, 100);
				break;
			}
			PlayerOne.timeToShake = false;
		}
//		if (PlayerTwo.timeToShake) {
//			switch (GunSelectionScreen.p2WeaponSelected) {
//			case "laser":
//				shake(.2f, 400);
//				break;
//			case "revolver":
//				shake(.08f, 100);
//				break;
//			case "rifle":
//				shake(.1f, 200);
//				break;
//			case "shotgun":
//				shake(.1f, 200);
//				break;
//			case "assault rifle":
//				shake(.08f, 100);
//				break;
//			}
//			PlayerTwo.timeToShake = false;
//		}
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
		System.out.println(PlayerOne.p1PosY);
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
				if (GunSelectionScreen.p1WeaponSelected == "rifle" || GunSelectionScreen.p1WeaponSelected == "revolver" 
						|| GunSelectionScreen.p1WeaponSelected == "assault rifle") {
					PlayerOne.bullets.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.p1WeaponSelected == "laser") {
					PlayerOne.lasers.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.p1WeaponSelected == "shotgun") {
					PlayerOne.pellets.removeValue((CreateBullet)b.getUserData(), true);
				}
				//Player Two
//				if (GunSelectionScreen.p2WeaponSelected == "rifle" || GunSelectionScreen.p2WeaponSelected == "revolver" 
//						|| GunSelectionScreen.p2WeaponSelected == "assault rifle") {
//					PlayerTwo.bullets.removeValue((CreateBullet)b.getUserData(), true);
//				}
//				else if (GunSelectionScreen.p2WeaponSelected == "laser") {
//					PlayerTwo.lasers.removeValue((CreateBullet)b.getUserData(), true);
//				}
//				else if (GunSelectionScreen.p2WeaponSelected == "shotgun") {
//					PlayerTwo.pellets.removeValue((CreateBullet)b.getUserData(), true);
//				}
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Grunt) {
				Grunt.grunts.removeValue((Grunt) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Scientist) {
				Scientist.scientists.removeValue((Scientist) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Turret) {
				Turret.turrets.removeValue((Turret) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof TurretBullets) {
				TurretBullets.turretBullets.removeValue((TurretBullets) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof SoldierBullets) {
				SoldierBullets.soldierBullets.removeValue((SoldierBullets) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof HealthPickUp) {
				HealthPickUp.hpPickUp.removeValue((HealthPickUp) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Soldier) {
				Soldier.soldiers.removeValue((Soldier) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
		}
		bodiesToRemove.clear();
	}

	public void spawningLocations() {

//		//SPAWN HEALTH PICK_UPS
//		if (hpSpawn) {
//			MapLayer hpLayer = map.getLayers().get("hp");
//			for (MapObject mo : hpLayer.getObjects()) {
//				HealthPickUp.hpSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//				HealthPickUp.hpSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//				hpPickUp = new HealthPickUp(world);
//				HealthPickUp.hpPickUp.add(hpPickUp);
//			}
//			hpSpawn = false;
//		}


//		//SPAWN ENEMIES GIVEN PLAYER LOCATION
//		if (playerOne.b2body.getPosition().x < 6.6 && playerOne.b2body.getPosition().x > 6.5 && 
//				playerOne.b2body.getPosition().y < 3.9 && playerOne.b2body.getPosition().y > 3.2){
//			if (room2) {
//				MapLayer layer = map.getLayers().get("room2g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				room2 = false;
//			}
//		}
//
//		if (playerOne.b2body.getPosition().x < 4.8 && playerOne.b2body.getPosition().x > 4.1 && 
//				playerOne.b2body.getPosition().y < 1.4 && playerOne.b2body.getPosition().y > 1.3){
//			if (room1) {
//				MapLayer layer = map.getLayers().get("room1g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				room1 = false;
//			}
//
//
//		}
//		if (playerOne.b2body.getPosition().x < 8.6 && playerOne.b2body.getPosition().x > 8 && 
//				playerOne.b2body.getPosition().y < 4.2 && playerOne.b2body.getPosition().y > 4.1){
//			if (room3) {
//				MapLayer layer = map.getLayers().get("room3g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				room3 = false;
//			}
//
//
//		}
//		if (playerOne.b2body.getPosition().x < 8.6 && playerOne.b2body.getPosition().x > 8 && 
//				playerOne.b2body.getPosition().y < 6.5 && playerOne.b2body.getPosition().y > 6.4){
//			if (room4) {
//				MapLayer layer = map.getLayers().get("room4g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				MapLayer layer2 = map.getLayers().get("room4s");
//				for (MapObject mo : layer2.getObjects()) {
//					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					scientist = new Scientist(world);
//					Scientist.scientists.add(scientist);
//				}
//				room4 = false;
//			}
//
//
//		}
//		if (playerOne.b2body.getPosition().x < 11.8 && playerOne.b2body.getPosition().x > 11.1 && 
//				playerOne.b2body.getPosition().y < 10.3 && playerOne.b2body.getPosition().y > 10.2){
//			if (room5) {
//				MapLayer layer = map.getLayers().get("room5g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				MapLayer layer2 = map.getLayers().get("room5s");
//				for (MapObject mo : layer2.getObjects()) {
//					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					scientist = new Scientist(world);
//					Scientist.scientists.add(scientist);
//				}
//				room5 = false;
//			}
//		}
//		if (playerOne.b2body.getPosition().x < 7.4 && playerOne.b2body.getPosition().x > 7.3 && 
//				playerOne.b2body.getPosition().y < 12.8 && playerOne.b2body.getPosition().y > 12.1){
//			if (room6) {
//				MapLayer layer = map.getLayers().get("room6g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				room6 = false;
//			}
//		}
//		if (playerOne.b2body.getPosition().x < 9.9 && playerOne.b2body.getPosition().x > 9.8 && 
//				playerOne.b2body.getPosition().y < 16 && playerOne.b2body.getPosition().y > 15){
//			if (room6t) {
//				MapLayer layer = map.getLayers().get("room6t");
//				for (MapObject mo : layer.getObjects()) {
//					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					turret = new Turret(world);
//					Turret.turrets.add(turret);
//				}
//				room6t = false;
//			}
//		}
//		if (playerOne.b2body.getPosition().x < 10.1 && playerOne.b2body.getPosition().x > 10 && 
//				playerOne.b2body.getPosition().y < 21.4 && playerOne.b2body.getPosition().y > 20.7){
//			if (room8) {
//				MapLayer layer = map.getLayers().get("room8g");
//				for (MapObject mo : layer.getObjects()) {
//					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					grunt = new Grunt(world);
//					Grunt.grunts.add(grunt);
//				}
//				room8 = false;
//			}
//		}
//		if (playerOne.b2body.getPosition().x < 4.8 && playerOne.b2body.getPosition().x > 4.7 && 
//				playerOne.b2body.getPosition().y < 20.8 && playerOne.b2body.getPosition().y > 20.1){
//			if (room9) {
//				MapLayer layer = map.getLayers().get("room9s");
//				for (MapObject mo : layer.getObjects()) {
//					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					scientist = new Scientist(world);
//					Scientist.scientists.add(scientist);
//				}
//				room9 = false;
//			}
//		}
//		//GAME BEATEN
//		if (playerOne.b2body.getPosition().x < .6 && playerOne.b2body.getPosition().x > 0 &&
//				playerOne.b2body.getPosition().y < 20.8 && playerOne.b2body.getPosition().y > 19.8) {
//			PlayerOne.runningSound.stop();
//			Gdx.input.setCursorCatched(false);
//			levelOneMusic.stop();
//			game.setScreen(new levelCompleted(game));
//		}
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
			PlayerOne.runningSound.stop();
			//PlayerTwo.runningSound.stop();
			game.batch.begin(); //starts sprite spriteBatch
			cam.position.x = 0;
			cam.position.y = 0;
			game.batch.draw(pauseMenu, 0 - (350/Mutagen.PPM), 0 - (200 / Mutagen.PPM), 1500 / 200,  800 / 200);
			
			lockCursor = false;	
			if (Gdx.input.isButtonPressed(Input.Keys.LEFT)) {
				//RESUME
				if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < 0.88 && mousePosition.y > .31) {
					Mutagen.clicking();
					gamePaused = false;
					lockCursor = true;
				}
				//QUIT TO MENU
				if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < 0.221 && mousePosition.y > -.38) {
					levelOneMusic.stop();
					Mutagen.clicking();
					game.setScreen(new MainMenu(game));
				}	
				//QUIT GAME
				else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.46 && mousePosition.y > -1.06) {
					Gdx.app.exit();
				}
			}
			cam.update();
			game.batch.end(); //starts sprite spriteBatch

		}else if (!gamePaused){ //********PLAY********

			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				cam.zoom += .1f;
			}
			cameraUpdate(delta);
			mapRenderer.render();
			b2dr.render(world, cam.combined);
			game.batch.begin(); //starts sprite spriteBatch

			//RENDER DIFFERENT TEXTURES AND ANIMATIONS OVER BODY OBJECTS
			for (int i = 0; i < Grunt.grunts.size; i++) {
				Grunt.grunts.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < Scientist.scientists.size; i++) {
				Scientist.scientists.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < PlayerOne.lasers.size; i++) {
				PlayerOne.lasers.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < PlayerOne.pellets.size; i++) {
				PlayerOne.pellets.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < PlayerOne.bullets.size; i++) {
				PlayerOne.bullets.get(i).renderSprite(game.batch);
			}
//			if (!PlayerMode.OneP) {
//				for (int i = 0; i < PlayerOne.lasers.size; i++) {
//					PlayerTwo.lasers.get(i).renderSprite(game.batch);
//				}
//				for (int i = 0; i < PlayerOne.pellets.size; i++) {
//					PlayerTwo.pellets.get(i).renderSprite(game.batch);
//				}
//				for (int i = 0; i < PlayerOne.bullets.size; i++) {
//					PlayerTwo.bullets.get(i).renderSprite(game.batch);
//
//				}
//			}
			for (int i = 0; i < Soldier.soldiers.size; i++) {
				Soldier.soldiers.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < SoldierBullets.soldierBullets.size; i++) {
				SoldierBullets.soldierBullets.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < Turret.turrets.size; i++) {
				Turret.turrets.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < TurretBullets.turretBullets.size; i++) {
				TurretBullets.turretBullets.get(i).renderSprite(game.batch);
			}

				for (int i = 0; i < HealthPickUp.hpPickUp.size; i++) {
				HealthPickUp.hpPickUp.get(i).renderSprite(game.batch);
			}
			//Goes to method that handles spawning the enemies
			spawningLocations();
			//Player One
			if (!PlayerOne.p1Dead) {
				playerOne.handleInput(delta);
				playerOne.renderSprite(game.batch);	        	
			}
			//Player Two
//			if (!PlayerTwo.p2Dead) {
//				playerTwo.handleInput(delta);
//				playerTwo.renderSprite(game.batch);
//			}

			if (PlayerMode.OneP) {
				if (GunSelectionScreen.p1WeaponSelected == "battle axe") {
					game.batch.draw(axeMouseCursor, mousePosition.x - .05f, mousePosition.y - .05f, 21 / Mutagen.PPM, 21/ Mutagen.PPM);
				}else { 
					game.batch.draw(mouseCursor, mousePosition.x - .05f, mousePosition.y - .05f, 13 / Mutagen.PPM, 13 / Mutagen.PPM);
				}				
			}

			mapRenderer.setView(cam);
			game.batch.end(); //starts sprite spriteBatch

		} //closing bracket for game not paused

		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePosition); //gets mouse coordinates within viewport
		game.batch.setProjectionMatrix(cam.combined); //keeps player sprite from doing weird out of sync movement
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
