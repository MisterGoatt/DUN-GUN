package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import BackEnd.LogFileHandler;
import BackEnd.Mutagen;
import levels.Level1;
import levels.Level2;
import levels.Level3;
import screens.DifficultyScreen;
import screens.GunSelectionScreen;
import screens.PlayerMode;

public class PlayerOne extends Sprite implements Disposable{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private TextureAtlas revolverTextureAtlas, axeSwingTextureAtlas, rifleTextureAtlas, shotgunTextureAtlas, 
	assaultRifleTextureAtlas, laserTextureAtlas;
	private Texture p1HP, p1HPBG;
	private Animation <TextureRegion> revolverAnimation, rifleAnimation, shotgunAnimation, assaultRifleAnimation, 
	laserAnimation, axeSwingAnimation;

	private TextureRegion revolverStandingRegion, axeStandingRegion, rifleStandingRegion, 
	shotgunStandingRegion, assaultRifleStandingRegion, laserStandingRegion;

	private Sound assaultRifleShot, axeSwing, laserShot, shotgunShot, rifleShot, gunShot, hit1, hit2, hit3, hit4, 
	deadSfx, oneLifeLeft;
	//sound effect of the player's movement
	public static Sound runningSound;
	private float speed = 3, oldSpeed, speedAB = .707f, waitToShootL = 0, timeSinceLastShot = 60f, timePassed = 0, slowedCounter, rotationSpeed = 4, storedHP, secondWind = 1, olA; //speed of the player, Sqrt 2 divided by 2
	public static float  angle, p1PosX, p1PosY, axeSwingTimer = 0, mouseBoundValue = 4.46f; //get distance between mouse and player in radians
	//amount of damage each weapon deals
	public static float laserLanceDamage = 60, battleAxeDamage = 175, assaultRifleDamage = 22, shotgunDamage = 38f, 
			rifleDamage = 150, revolverDamage = 75;
	public static int player1HP, player1MaxHP1 = 200, player1MaxHP2 = 150;
	public static boolean p1Dead = false;
	private boolean shootAnimation = false, running = false, startLaserCount = false, shootBehind = false;
	public static boolean axeBodyRemoval = false, slowed = false, slowRestart = false, soundStop = false,  axeSwinging = false,  
			isShooting = false, timeToShake = false, movHalt = false;
	private int ID;
	public static Array<CreateBullet> pellets = new Array<CreateBullet>();
	public static Array<CreateBullet> bullets = new Array<CreateBullet>();
	public static Array<CreateBullet> lasers = new Array<CreateBullet>();
	public static Vector2 player1SpawnPos = new Vector2(0,0);
	private Vector3 mousePosition = new Vector3(0,0, 0);
	public CreateBullet createBullet;

	LogFileHandler lfh = new LogFileHandler();
	
	public PlayerOne(World world) {
		
		try {
			this.world = world;
			//determines the health depending on the difficulty
			if (DifficultyScreen.difficulty == 1) {
				player1HP = player1MaxHP1;			
			}
			if (DifficultyScreen.difficulty == 2) {
				player1HP = player1MaxHP2;			
			}
			slowedCounter = 0;
			p1Dead = false;
			slowRestart = false;
			
			//retrieves atlas assets loaded from asset manager and creates animation and standing region objects
			revolverTextureAtlas = Mutagen.manager.get("sprites/player1/playerRevolver.atlas", TextureAtlas.class);
			revolverAnimation = new Animation <TextureRegion>(1f/15f, revolverTextureAtlas.getRegions());
			revolverStandingRegion = revolverTextureAtlas.findRegion("tile000");

			rifleTextureAtlas = Mutagen.manager.get("sprites/player1/rifleAnimation.atlas", TextureAtlas.class);
			rifleAnimation = new Animation <TextureRegion>(1f/15f, rifleTextureAtlas.getRegions());
			rifleStandingRegion = rifleTextureAtlas.findRegion("tile000");

			shotgunTextureAtlas = Mutagen.manager.get("sprites/player1/shotgun.atlas", TextureAtlas.class);
			shotgunAnimation = new Animation <TextureRegion>(1f/15f, shotgunTextureAtlas.getRegions());
			shotgunStandingRegion = shotgunTextureAtlas.findRegion("tile000");

			assaultRifleTextureAtlas = Mutagen.manager.get("sprites/player1/assaultRifle.atlas", TextureAtlas.class);
			assaultRifleAnimation = new Animation<TextureRegion>(1f/15f, assaultRifleTextureAtlas.getRegions());
			assaultRifleStandingRegion = assaultRifleTextureAtlas.findRegion("tile000");

			laserTextureAtlas = Mutagen.manager.get("sprites/player1/laserAnimation.atlas", TextureAtlas.class);
			laserAnimation = new Animation <TextureRegion>(1f/15f, laserTextureAtlas.getRegions());
			laserStandingRegion = laserTextureAtlas.findRegion("tile000");

			axeSwingTextureAtlas = Mutagen.manager.get("sprites/player1/axeSwingAnimation.atlas", TextureAtlas.class);
			axeSwingAnimation = new Animation <TextureRegion>(1f/15f, axeSwingTextureAtlas.getRegions());
			axeStandingRegion = axeSwingTextureAtlas.findRegion("tile000");

			runningSound = Gdx.audio.newSound(Gdx.files.internal("sound effects/running.mp3"));
			
			//GETS ASSETS THAT HAVE BEEN LOADED BY ASSET MANAGER
			p1HP = Mutagen.manager.get("sprites/player1/hp.png", Texture.class);
			p1HPBG = Mutagen.manager.get("sprites/player1/hpBg.png", Texture.class);
			gunShot = Mutagen.manager.get("sound effects/shooting/pistol_shot.mp3", Sound.class);
			rifleShot = Mutagen.manager.get("sound effects/shooting/rifleShot.mp3", Sound.class);
			shotgunShot = Mutagen.manager.get("sound effects/shooting/shotgun2.mp3", Sound.class);
			assaultRifleShot = Mutagen.manager.get("sound effects/shooting/assaultRifle.mp3", Sound.class);
			laserShot = Mutagen.manager.get("sound effects/shooting/laserBlast3.mp3", Sound.class);
			axeSwing = Mutagen.manager.get("sound effects/shooting/axeSwing.mp3", Sound.class);
			hit1 = Mutagen.manager.get("sound effects/impacts/hit1.ogg", Sound.class);
			hit2 = Mutagen.manager.get("sound effects/impacts/hit2.ogg", Sound.class);
			hit3 = Mutagen.manager.get("sound effects/impacts/hit3.ogg", Sound.class);
			hit4 = Mutagen.manager.get("sound effects/impacts/hit4.ogg", Sound.class);
			deadSfx = Mutagen.manager.get("sound effects/playerDead.mp3");
			oneLifeLeft = Mutagen.manager.get("sound effects/lifeRemaining.mp3");

			ID = 1; //defines if player is player 1 or player 2 for the CreateBullet class
			storedHP = player1HP;

			definePlayer();

		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	//create b2body physics body, set spawn location, masking, and category bits
	public void definePlayer() { 
		try {
			//define player body
			bdef.position.set(player1SpawnPos);
			bdef.type = BodyDef.BodyType.DynamicBody;
			//create body in the world
			b2body = world.createBody(bdef);

			FixtureDef fdef = new FixtureDef();
			CircleShape shape = new CircleShape();
			shape.setRadius(12 / Mutagen.PPM);

			fdef.shape = shape;
			fdef.filter.categoryBits = Mutagen.PLAYER;
			fdef.filter.maskBits = Mutagen.ENEMY | Mutagen.ENEMY_BULLET | Mutagen.WALL | Mutagen.SHOOT_OVER | Mutagen.HP_PICKUP;

			b2body.createFixture(fdef).setUserData("player");;
			
			if (!PlayerMode.OneP) {
				angle = 0;
			}
			shape.dispose();

		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	public void renderSprite(SpriteBatch batch) {
		
		try {
			float posX = b2body.getPosition().x;
			float posY = b2body.getPosition().y;

			//if player is on a certain level then assign correct static vector3 mouse positions
			if (Mutagen.level == "1") {
				mousePosition = Level1.mousePosition;
			}else if (Mutagen.level == "2") {
				mousePosition = Level2.mousePosition;
			}else if (Mutagen.level == "3") {
				mousePosition = Level3.mousePosition;
			}

			//If Single Player
			if (PlayerMode.OneP) {
				angle = MathUtils.atan2(mousePosition.y - getY(), mousePosition.x - getX()) * MathUtils.radDeg; //find the distance between mouse and player
				angle = angle - 90; //makes it a full 360 degrees
				if (angle < 0) {
					angle += 360 ;
				}
			}
			
			//DEPENDING ON THE WEAPON THEN IT DETERMINES THE SPEED OF PLAYER AS WELL AS THE ANIMATIONS TO USE FOR THE PLAYER

			//revolver			
			if (GunSelectionScreen.p1WeaponSelected == "revolver") {
				speed = 1.5f;
				if (shootAnimation) {
					batch.draw(revolverAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();
					if(revolverAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				}else {
					batch.draw(revolverStandingRegion, posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
				}
			}
			//BoltAction Rifle
			else if (GunSelectionScreen.p1WeaponSelected == "rifle") {
				speed = 1.4f;
				if (shootAnimation) {
					batch.draw(rifleAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();

					if(rifleAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				}else {
					batch.draw(rifleStandingRegion, posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
				}
			}
			//Shotgun
			else if (GunSelectionScreen.p1WeaponSelected == "shotgun") {
				speed = 1.2f;
				if (shootAnimation) {
					batch.draw(shotgunAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();

					if(shotgunAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				}else {
					batch.draw(shotgunStandingRegion, posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
				}

			}
			//Assault Rifle
			else if (GunSelectionScreen.p1WeaponSelected == "assault rifle") {
				speed = 1.3f;
				if (shootAnimation) {
					batch.draw(assaultRifleAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();

					if(assaultRifleAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				}else {
					batch.draw(assaultRifleStandingRegion,posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle );
				}
			}

			//Laser
			else if (GunSelectionScreen.p1WeaponSelected == "laser") {
				speed = 1f;	
				if (shootAnimation) {
					batch.draw(laserAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();

					if(laserAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				}else {
					batch.draw(laserStandingRegion, posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle );
				}
			}

			//battle axe
			else if (GunSelectionScreen.p1WeaponSelected == "battle axe") {
				speed = 2f;
				if (shootAnimation) {
					batch.draw(axeSwingAnimation.getKeyFrame(timePassed), posX - .35f, posY - .3f, 35 / Mutagen.PPM, 30 / Mutagen.PPM, 70 / Mutagen.PPM, 70 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();

					if(axeSwingAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
						axeBodyRemoval = true;
						axeSwinging = false;
					}	
				}else {
					batch.draw(axeStandingRegion, posX - .35f, posY - .3f, 35 / Mutagen.PPM, 30 / Mutagen.PPM, 70 / Mutagen.PPM, 70 / Mutagen.PPM, 1, 1, angle);
				}
			}

			oldSpeed = speed; //original speed to go back to after being slowed

			//PLAYER ONE HEALTH
			if (DifficultyScreen.difficulty == 1) {
				batch.draw(p1HPBG, p1PosX - .30f, p1PosY - .28f, player1MaxHP1 / (Mutagen.PPM + 250), 3f / Mutagen.PPM); //gray backing behind HP bar	
				batch.draw(p1HP, p1PosX - .30f, p1PosY - .28f, player1HP / (Mutagen.PPM + 250), 3f / Mutagen.PPM); //HP bar

			}else{
				batch.draw(p1HPBG, p1PosX - .3f, p1PosY - .28f, player1MaxHP2 / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //gray backing behind HP bar	
				batch.draw(p1HP, p1PosX - .3f, p1PosY - .28f, player1HP / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //HP bar			
			}

			//PLAYER DIES
			if (player1HP <= 0) {
				//player's second life
				if (secondWind == 1) {
					if (DifficultyScreen.difficulty ==1) {
						player1HP = player1MaxHP1;	
						long oLL = oneLifeLeft.play(Mutagen.sfxVolume)
								;				}
					secondWind += 1;
				}else if (secondWind > 1 || DifficultyScreen.difficulty == 2){
					//player dies
					world.destroyBody(this.b2body);
					p1PosX = 0;
					p1PosY = 0;
					p1Dead = true;
					runningSound.stop();	
					long pD = deadSfx.play(Mutagen.sfxVolume);
				}
			}

			//HIT SFX
			if (storedHP > player1HP) {

				int hitSelect = (int) (Math.random()* 4 + 1);
				switch (hitSelect) {
				case 1: 
					long h1 = hit1.play(Mutagen.sfxVolume);
					break;
				case 2:
					long h2 = hit2.play(Mutagen.sfxVolume);
					break;
				case 3:
					long h3 = hit3.play(Mutagen.sfxVolume);
					break;
				case 4:
					long h4 = hit4.play(Mutagen.sfxVolume);
					break;
				}
				storedHP = player1HP;
			}
			else if (player1HP == player1MaxHP1) {
				storedHP = player1HP;
			}


		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");


		}


	}
	//CALLED WHEN PLAYER SHOOTS GUN AND HANDLES SFX AND CALLS CreateBullet.java
	public void shootGun() {

		try {
			//laser delay for build up of power effect
			if (startLaserCount) {
				waitToShootL += 1;
			}
			//deletes the battle axe
			if (GunSelectionScreen.p1WeaponSelected == "battle axe" && PlayerOne.axeBodyRemoval) {
				world.destroyBody(createBullet.b2body);
				axeBodyRemoval = false;
				bullets.clear();
				createBullet.b2body = null;
			}
			if (isShooting) {
				//waitToShootL += 1;
				switch (GunSelectionScreen.p1WeaponSelected) {
				case "laser":
					startLaserCount = true;
					if (Mutagen.sfxVolume != 0) {
						long lsId = laserShot.play(Mutagen.sfxVolume - .7f);
					}
					break;
				case "revolver":
					if (Mutagen.sfxVolume != 0) {
						long gsId = gunShot.play(Mutagen.sfxVolume - .7f);
					}
					break;
				case "rifle":
					if (Mutagen.sfxVolume != 0) {
						long rsId = rifleShot.play(Mutagen.sfxVolume - .7f);
					}
					break;
				case "shotgun":
					//controls how many shotgun shells are shot
					for (int i = 0; i < 6; i++) {
						createBullet = new CreateBullet(world, ID);
						pellets.add(createBullet);
					}
					//level1.shake(.1f, 200);

					if (Mutagen.sfxVolume != 0) {

						long sS = shotgunShot.play(Mutagen.sfxVolume - .7f);
					}
					break;
				case "assault rifle":
					if (Mutagen.sfxVolume != 0) {
						long arsId = assaultRifleShot.play(Mutagen.sfxVolume - .7f);
					}
					break;
				case "battle axe":
					if (Mutagen.sfxVolume != 0) {
						long baId = axeSwing.play(Mutagen.sfxVolume - .7f);
					}
					axeSwinging = true;
					break;
				}

				if (GunSelectionScreen.p1WeaponSelected != "shotgun" && GunSelectionScreen.p1WeaponSelected != "laser") {				
					createBullet = new CreateBullet(world, ID);
					bullets.add(createBullet);	
				}
				isShooting = false;
				timeToShake = true;
			}
			
			//SHOOTS THE LASERS DELAYED (hence the counter) AFTER MOUSE CLICKED 
			if (startLaserCount) {
				if (waitToShootL >= 0 && waitToShootL < 1){
					createBullet = new CreateBullet(world, ID);
					lasers.add(createBullet);
				}

				if (waitToShootL >=17 && waitToShootL < 18) {
					createBullet = new CreateBullet(world, ID);
					lasers.add(createBullet);
				}

				if (waitToShootL >= 27 && waitToShootL < 28) {
					createBullet = new CreateBullet(world, ID);
					lasers.add(createBullet);
					waitToShootL = 0;
					startLaserCount = false;
				}
			}

		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");

		}

	}
	
	//PLAYER INPUT FOR MOVEMENT AND SHOOTING
	public void handleInput(float delta) {
		try {
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + (5 / Mutagen.PPM));
			p1PosX = b2body.getPosition().x;
			p1PosY = b2body.getPosition().y;
			timeSinceLastShot -= 1;
			this.b2body.setLinearVelocity(0, 0);

			if (slowed) {
				if (slowRestart) {
					slowedCounter = 0;
					slowRestart = false;
				}
				speed = speed / 2;
				slowedCounter +=1;

			}else {
				speed = oldSpeed;
			}
			if (slowed && slowedCounter > 200) {
				slowedCounter = 0;
				slowed = false;
			}
			//Aim style is rotational

			if (!Level3.cin) {
				if (GunSelectionScreen.p1AimStyle != 2) {
					if (!movHalt) {

						if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)){
							this.b2body.setLinearVelocity(-speed * speedAB, speed * speedAB);
							mouseBoundValue = 3.8f;
						}else if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)){
							this.b2body.setLinearVelocity(speed * speedAB, speed * speedAB);
						}else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)){
							this.b2body.setLinearVelocity(-speed * speedAB, -speed * speedAB );   
						}else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)){
							this.b2body.setLinearVelocity(speed * speedAB, -speed * speedAB );
						}
						else if(Gdx.input.isKeyPressed(Input.Keys.W)){
							this.b2body.setLinearVelocity(0f, speed);

						}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
							this.b2body.setLinearVelocity(0f, -speed);

						}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
							this.b2body.setLinearVelocity(-speed, 0f);
							mouseBoundValue = 3.8f;

						}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
							this.b2body.setLinearVelocity(speed, 0f);
						}
					}			
				}


				shootGun();
				//CONTROLS THE SPEED OF FIRE & SINGLE PLAYER
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && PlayerMode.OneP) {
					if (timeSinceLastShot <=0) {
						isShooting = true;
						shootAnimation = true;
						switch (GunSelectionScreen.p1WeaponSelected) {
						case "revolver": timeSinceLastShot = 50;
						break;
						case "rifle": timeSinceLastShot = 90;
						break;
						case "shotgun": timeSinceLastShot = 70;
						break;
						case "assault rifle": timeSinceLastShot = 7;
						break;
						case "laser": timeSinceLastShot = 85;
						break;
						case "battle axe": timeSinceLastShot = 110;
						break;
						}
					}
				}

				//CO-OP
				else if (!PlayerMode.OneP) {

					//rotational 
					if (GunSelectionScreen.p1AimStyle == 1) {
						if (Gdx.input.isKeyPressed(Input.Keys.G)){
							angle += rotationSpeed;
						}
						else if (Gdx.input.isKeyPressed(Input.Keys.H)){
							angle -= rotationSpeed;
						}				
					}//directional aim style
					else {

						if (!movHalt) {
							if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)){
								this.b2body.setLinearVelocity(-speed * speedAB, speed * speedAB);
								if (!shootBehind) {
									angle = 45;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)){
								this.b2body.setLinearVelocity(speed * speedAB, speed * speedAB);
								if (!shootBehind) {
									angle = 315;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)){
								this.b2body.setLinearVelocity(-speed * speedAB, -speed * speedAB );
								if (!shootBehind) {
									angle = 135;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)){
								this.b2body.setLinearVelocity(speed * speedAB, -speed * speedAB );
								if (!shootBehind) {
									angle = 225;
								}
							}
							else if(Gdx.input.isKeyPressed(Input.Keys.W)){
								this.b2body.setLinearVelocity(0f, speed);
								if (!shootBehind) {
									angle = 0;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
								this.b2body.setLinearVelocity(0f, -speed);
								if (!shootBehind) {
									angle = 180;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
								this.b2body.setLinearVelocity(-speed, 0f);
								if (!shootBehind) {
									angle = 90;
								}
							}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
								this.b2body.setLinearVelocity(speed, 0f);
								if (!shootBehind) {
									angle = 270;
								}
							}

							if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
								shootBehind = true;
							}else {
								shootBehind = false;
							}
						}
					}

					if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
						if (timeSinceLastShot <=0) {
							isShooting = true;
							shootAnimation = true;
							switch (GunSelectionScreen.p1WeaponSelected) {
							case "revolver": timeSinceLastShot = 50;
							break;
							case "rifle": timeSinceLastShot = 90;
							break;
							case "shotgun": timeSinceLastShot = 70;
							break;
							case "assault rifle": timeSinceLastShot = 7;
							break;
							case "laser": timeSinceLastShot = 85;
							break;
							case "battle axe": timeSinceLastShot = 110;
							break;
							}
						}
					}	
				}
			}

			if (b2body.getLinearVelocity().x > 0 || b2body.getLinearVelocity().x < 0 || b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0) {
				if (running) {	
					if (Mutagen.sfxVolume != 0) {
						long rS = runningSound.loop(Mutagen.sfxVolume);
					}
					running = false;
				}

			}else if (!running && !p1Dead && !soundStop) {
				runningSound.stop();
				running = true;
			}

		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");


		}

	}


	@Override
	public void dispose() {

	}
}