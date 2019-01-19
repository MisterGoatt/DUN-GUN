package levels;

import java.util.Random;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

import BackEnd.Lvl1EntityPositions;
import BackEnd.Lvl2EntityPositions;
import BackEnd.Mutagen;
import collisions.B2DWorldCreator;
import collisions.CollisionDetector;
import entities.CreateBullet;
import entities.Flayer;
import entities.FlayerThorns;
import entities.Grunt;
import entities.HealthPickUp;
import entities.PlayerOne;
import entities.PlayerTwo;
import entities.Scientist;
import entities.Soldier;
import entities.SoldierBullets;
import entities.Turret;
import entities.TurretBullets;
import screens.GunSelectionScreen;
import screens.MainMenu;
import screens.PlayerMode;
import screens.levelCompleted;

public class Level2 implements Screen{
	final Mutagen game;
	public OrthographicCamera cam;
	public Viewport gamePort;
	private TiledMap map; 
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	TextureRegion textureRegion;
	MapLayer objectLayer;
	private World world;
	private Box2DDebugRenderer b2dr; //graphical representation of body fixtures
	private PlayerOne playerOne;
	private PlayerTwo playerTwo;
	public CreateBullet createBullet;
	private CollisionDetector cd;
	private Lvl2EntityPositions lvl2EP;
	private Music levelTwoMusicAll, lvlComplete, secretRoomMusic;
	private Texture mouseCursor, axeMouseCursor, pauseMenu;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private boolean lockCursor = true, gamePaused = false, sMusic = false;
	private float elapsed = 0, duration, intensity, gameOver = 0, fadeOut = 0;
	public static boolean bulletImpact = false;
	public static Vector3 mousePosition = new Vector3(0, 0, 0);
	private Vector2 boundaryAbs = new Vector2(0, 0);
	private Vector2 boundary = new Vector2(0, 0);
	Random random;


	public Level2(final Mutagen game) {
		this.game = game;

//					Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
//			        Gdx.graphics.setFullscreenMode(currentMode);
//					System.out.println(currentMode);

		Mutagen.level = "2";
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(Mutagen.V_WIDTH / Mutagen.PPM, Mutagen.V_HEIGHT / Mutagen.PPM, cam);
		cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		cam.zoom -= .40;
		pauseMenu = Mutagen.manager.get("screens/Pause.jpg", Texture.class);

		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;
		map = new TmxMapLoader().load("tileMaps/Levels/Level2Partial.tmx", params);
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
			MapLayer player2Layer = map.getLayers().get("Player 2");
			for (MapObject mo : player2Layer.getObjects()) {
				PlayerTwo.player2SpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
				PlayerTwo.player2SpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
				playerTwo = new PlayerTwo(world);
			}
		}

		lvl2EP = new Lvl2EntityPositions();
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
		Flayer.flayers.clear();
		FlayerThorns.flayerThorns.clear();


		if (!PlayerMode.OneP) {
			PlayerTwo.pellets2.clear();
			PlayerTwo.lasers2.clear();
			PlayerTwo.bullets2.clear();
		}


		levelTwoMusicAll = Mutagen.manager.get("music/songAll.mp3");
		lvlComplete = Mutagen.manager.get("music/lvlComplete.mp3");
		secretRoomMusic = Mutagen.manager.get("music/whistling masterpiece.mp3");
		
		//levelOneMusi.setLooping(true);
		if (Mutagen.musicVolume > 0) {
			levelTwoMusicAll.setLooping(true);
			levelTwoMusicAll.play();
			
			levelTwoMusicAll.setVolume(Mutagen.musicVolume - .3f);
			secretRoomMusic.setVolume(Mutagen.musicVolume);
			
			lvlComplete.setLooping(true);
			lvlComplete.setVolume(Mutagen.musicVolume - .3f);
		}
		this.world.setContactListener(cd);
		Gdx.input.setInputProcessor(null);
//        Gdx.graphics.setWindowedMode(1500, 800);

	}

	//Creation of bullet objects and playing shooting and swinging sound effects
	public void shootGun() {
		if (PlayerOne.timeToShake) {
			//waitToShootL += 1;
			switch (GunSelectionScreen.p1WeaponSelected) {
			case "laser":
				shake(.15f, 400);
				break;
			case "revolver":
				shake(.1f, 100);
				break;
			case "rifle":
				shake(.1f, 200);
				break;
			case "shotgun":
				shake(.15f, 200);
				break;
			case "assault rifle":
				//shake(.01f, 100);
				break;
			}
			PlayerOne.timeToShake = false;
		}
		if (PlayerTwo.timeToShake) {
			switch (GunSelectionScreen.p2WeaponSelected) {
			case "laser":
				shake(.15f, 400);
				break;
			case "revolver":
				shake(.1f, 100);
				break;
			case "rifle":
				shake(.1f, 200);
				break;
			case "shotgun":
				shake(.15f, 200);
				break;
			case "assault rifle":
				//shake(.05f, 100);
				break;
			}
			PlayerTwo.timeToShake = false;
		}
	}

	public void shake(float intensity, float duration) {
		this.elapsed = 0;
		this.duration = duration / 1000f;
		this.intensity = intensity;
	}

	public void cameraUpdate(float delta) {
		world.step(1/60f, 6, 2); //tells game how many times per second for Box2d to make its calculations
		removeBodies(); //goes to method that removes physics bodies
		shootGun(); //sees if gun is shooting
		/* a = current camera position b = target
		 * a+(b-a)*lerp
		 * the higher the lerp value the more instantaneous
		 */
		if (PlayerMode.OneP) {
			cam.position.x = cam.position.x + (playerOne.b2body.getPosition().x - cam.position.x) * .05f;
			cam.position.y = cam.position.y + (playerOne.b2body.getPosition().y - cam.position.y) * .05f;

			//GOES TO MAIN MENU IF BOTH PLAYERS ARE DEAD
			if (PlayerOne.p1Dead) {
				gameOver +=1;

				if (gameOver > 120) {
					Gdx.input.setCursorCatched(false);
					game.setScreen(new MainMenu(game));					
				}

			}
		} else {
			//prevents co-op players from moving out of the screen if moving is separate directions
			if (!PlayerOne.p1Dead && !PlayerTwo.p2Dead) {

				boundaryAbs.x = Math.abs(playerOne.b2body.getPosition().x - playerTwo.b2body.getPosition().x);
				boundaryAbs.y = Math.abs(playerOne.b2body.getPosition().y - playerTwo.b2body.getPosition().y);
				boundary.x = playerOne.b2body.getPosition().x - playerTwo.b2body.getPosition().x;
				boundary.y = playerOne.b2body.getPosition().y - playerTwo.b2body.getPosition().y;
				if (boundaryAbs.x > 8.9) {
					PlayerOne.movHalt = true;
					PlayerTwo.movHalt = true;

				}else {
					PlayerOne.movHalt = false;
					PlayerTwo.movHalt = false;
				}
				if (boundaryAbs.y > 4.7) {
					PlayerOne.movHalt = true;
					PlayerTwo.movHalt = true;
				}else {
					PlayerOne.movHalt = false;
					PlayerTwo.movHalt = false;
				}

				if (PlayerOne.movHalt && PlayerTwo.movHalt) {
					//p2 is right of p1
					if (boundary.x >= 0 ) {
						playerOne.b2body.applyForceToCenter(2, 0, true);
						playerTwo.b2body.applyForceToCenter(-2, 0, true);
					}
					//p2 if left of p1
					else {
						playerOne.b2body.applyForceToCenter(-2, 0, true);
						playerTwo.b2body.applyForceToCenter(2, 0, true);

					}
					//p2 is below p1
					if (boundary.y >= 0 ) {
						playerOne.b2body.applyForceToCenter(0, -2f, true);
						playerTwo.b2body.applyForceToCenter(0, 2f, true);

					}
					//p2 is below of p1
					else {
						playerOne.b2body.applyForceToCenter(0, 2f, true);
						playerTwo.b2body.applyForceToCenter(0, -2f, true);
					}

				}

				cam.position.x = (playerTwo.b2body.getPosition().x - playerOne.b2body.getPosition().x) / 2;
				if (playerOne.b2body.getPosition().x > playerTwo.b2body.getPosition().x) {
					cam.position.x = playerTwo.b2body.getPosition().x + Math.abs(cam.position.x);
				}else {
					cam.position.x = playerOne.b2body.getPosition().x + Math.abs(cam.position.x);
				}
				cam.position.y = (playerTwo.b2body.getPosition().y - playerOne.b2body.getPosition().y) / 2;
				if (playerOne.b2body.getPosition().y > playerTwo.b2body.getPosition().y) {
					cam.position.y = playerTwo.b2body.getPosition().y + Math.abs(cam.position.y);
				}else {
					cam.position.y = playerOne.b2body.getPosition().y + Math.abs(cam.position.y);
				}
			}else if (PlayerOne.p1Dead) {
				//Player Two
				cam.position.x = cam.position.x + (playerTwo.b2body.getPosition().x - cam.position.x) * .05f;
				cam.position.y = cam.position.y + (playerTwo.b2body.getPosition().y - cam.position.y) * .05f;	
			}else {
				//Player One
				cam.position.x = cam.position.x + (playerOne.b2body.getPosition().x - cam.position.x) * .05f;
				cam.position.y = cam.position.y + (playerOne.b2body.getPosition().y - cam.position.y) * .05f;	
			}
			//GOES TO MAIN MENU IF BOTH PLAYERS ARE DEAD
			if (PlayerOne.p1Dead && PlayerTwo.p2Dead) {
				gameOver +=1;

				if (gameOver > 120) {
					Gdx.input.setCursorCatched(false);
					game.setScreen(new MainMenu(game));
				}
			}

		}
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
				if (GunSelectionScreen.p2WeaponSelected == "rifle" || GunSelectionScreen.p2WeaponSelected == "revolver" 
						|| GunSelectionScreen.p2WeaponSelected == "assault rifle") {
					PlayerTwo.bullets2.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.p2WeaponSelected == "laser") {
					PlayerTwo.lasers2.removeValue((CreateBullet)b.getUserData(), true);
				}
				else if (GunSelectionScreen.p2WeaponSelected == "shotgun") {
					PlayerTwo.pellets2.removeValue((CreateBullet)b.getUserData(), true);
				}
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Grunt) {
				//Grunt.grunts.removeValue((Grunt) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Scientist) {
				//Scientist.scientists.removeValue((Scientist) b.getUserData(), true);
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
				//Soldier.soldiers.removeValue((Soldier) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof Flayer) {
				//Soldier.soldiers.removeValue((Soldier) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
			if (u instanceof FlayerThorns) {
				FlayerThorns.flayerThorns.removeValue((FlayerThorns) b.getUserData(), true);
				world.destroyBody(b);
				b = null;
			}
		}
		bodiesToRemove.clear();
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
			cam.position.x = 0;
			cam.position.y = 0;
			game.batch.begin(); //starts sprite spriteBatch

			game.batch.draw(pauseMenu, 0 - (350/Mutagen.PPM), 0 - (200 / Mutagen.PPM), 1500 / 200,  800 / 200);

			game.batch.end();

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
					levelTwoMusicAll.stop();
					Mutagen.clicking();
					game.setScreen(new MainMenu(game));
				}	
				//QUIT GAME
				else if (mousePosition.x > -1.02 && mousePosition.x < 1 && mousePosition.y < -.46 && mousePosition.y > -1.06) {
					Gdx.app.exit();
				}
			}
			cam.update();

		}else if (!gamePaused){ //********PLAY********

			cameraUpdate(delta);
			mapRenderer.render();
			//b2dr.render(world, cam.combined);
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
			if (!PlayerMode.OneP) {
				for (int i = 0; i < PlayerTwo.lasers2.size; i++) {
					PlayerTwo.lasers2.get(i).renderSprite(game.batch);
				}
				for (int i = 0; i < PlayerTwo.pellets2.size; i++) {
					PlayerTwo.pellets2.get(i).renderSprite(game.batch);
				}
				for (int i = 0; i < PlayerTwo.bullets2.size; i++) {
					PlayerTwo.bullets2.get(i).renderSprite(game.batch);
				}
			}
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
			for (int i = 0; i < Flayer.flayers.size; i++) {
				Flayer.flayers.get(i).renderSprite(game.batch);
			}
			for (int i = 0; i < FlayerThorns.flayerThorns.size; i++) {
				FlayerThorns.flayerThorns.get(i).renderSprite(game.batch);
			}

			for (int i = 0; i < HealthPickUp.hpPickUp.size; i++) {
				HealthPickUp.hpPickUp.get(i).renderSprite(game.batch);
			}
			//Goes to method that handles spawning the enemies
			lvl2EP.SpawnEntities(world, map);
			
			//Secret Room Music
			if (PlayerOne.p1PosX > 10.8 && PlayerOne.p1PosX < 11 && PlayerOne.p1PosY > 44 && PlayerOne.p1PosY < 46.6 && !sMusic) {
				levelTwoMusicAll.stop();
				secretRoomMusic.play();
				sMusic = true;
			}
			if (PlayerOne.p1PosX > 13 && PlayerOne.p1PosX < 13.5 && PlayerOne.p1PosY > 44 && PlayerOne.p1PosY < 46.6 && sMusic) {
				secretRoomMusic.stop();
				levelTwoMusicAll.play();
				sMusic = false;
			}

			//LEVEL END
			if (PlayerOne.p1PosX > 97 && PlayerOne.p1PosX < 98.4 && PlayerOne.p1PosY > 25.3 && PlayerOne.p1PosY < 26.7) {
				PlayerOne.runningSound.stop();					

				if (!PlayerMode.OneP) {
					PlayerTwo.runningSound.stop();
				}
				Gdx.input.setCursorCatched(false);
				levelTwoMusicAll.stop();

				lvlComplete.play();
				gameOver+=1;
				shapeRenderer.setProjectionMatrix(cam.combined);

				shapeRenderer.begin(ShapeType.Filled);
				Gdx.gl.glEnable(GL20.GL_BLEND);
				Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				fadeOut += .01f;
				shapeRenderer.setColor(0, 0, 0, fadeOut);
				shapeRenderer.rect(PlayerOne.p1PosX - 15, PlayerOne.p1PosY- 15, 30, 30);
				shapeRenderer.end();
				Gdx.gl.glDisable(GL20.GL_BLEND);

				if (gameOver > 180) {
					game.setScreen(new levelCompleted(game));

				}

			}
			else if (PlayerTwo.p2PosX > 97 && PlayerTwo.p2PosX < 98.4 && PlayerTwo.p2PosY > 25.3 && PlayerTwo.p2PosY < 26.7) {
				PlayerOne.runningSound.stop();
				PlayerTwo.runningSound.stop();
				Gdx.input.setCursorCatched(false);
				levelTwoMusicAll.stop();

				lvlComplete.play();
				gameOver+=1;
				shapeRenderer.setProjectionMatrix(cam.combined);

				shapeRenderer.begin(ShapeType.Filled);
				Gdx.gl.glEnable(GL20.GL_BLEND);
				Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				fadeOut += .01f;
				shapeRenderer.setColor(0, 0, 0, fadeOut);
				shapeRenderer.rect(PlayerOne.p1PosX - 15, PlayerOne.p1PosY- 15, 30, 30);
				shapeRenderer.end();
				Gdx.gl.glDisable(GL20.GL_BLEND);

				if (gameOver > 180) {
					game.setScreen(new levelCompleted(game));

				}
			}
			//Player One
			if (!PlayerOne.p1Dead) {
				playerOne.handleInput(delta);
				playerOne.renderSprite(game.batch);	        	
			}

			if (!PlayerMode.OneP) {
				//Player Two
				if (!PlayerTwo.p2Dead) {
					playerTwo.handleInput(delta);
					playerTwo.renderSprite(game.batch);
				}	
			}

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
