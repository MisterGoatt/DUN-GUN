package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

import BackEnd.Mutagen;
import levels.Level1;
import levels.Level2;
import levels.Level3;
import screens.DifficultyScreen;
import screens.GunSelectionScreen;
import screens.PlayerMode;

public class PlayerTwo {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private TextureAtlas revolverTextureAtlas, axeSwingTextureAtlas, rifleTextureAtlas, shotgunTextureAtlas, 
	assaultRifleTextureAtlas, laserTextureAtlas;
	private Texture p2HP, p2HPBG;
	private Animation <TextureRegion> revolverAnimation, rifleAnimation, shotgunAnimation, assaultRifleAnimation, 
	laserAnimation, axeSwingAnimation;

	private TextureRegion revolverStandingRegion, axeStandingRegion, rifleStandingRegion, 
	shotgunStandingRegion, assaultRifleStandingRegion, laserStandingRegion;
	private Sound assaultRifleShot, axeSwing, laserShot, shotgunShot, rifleShot, gunShot, hit1, hit2, hit3, hit4,
	oneLifeLeft, deadSfx;
	//sound effect of the player's movement
	public static Sound runningSound;
	private float speed = 3, storedHP, oldSpeed, speedAB = .707f, waitToShootL = 0, timeSinceLastShot = 60f, timePassed = 0, 
			slowedCounter, rotationSpeed = 4, secondWind = 1; //speed of the player, Sqrt 2 divided by 2
	public static float  angle, p2PosX, p2PosY; //get distance between mouse and player in radians
	//amount of damage each weapon deals
	public static float laserLanceDamage = 60, battleAxeDamage = 175, assaultRifleDamage = 22, shotgunDamage = 38f, 
			rifleDamage = 150, revolverDamage = 75;
	public static int player2HP, player2MaxHP1 = 200, player2MaxHP2 = 150;
	public static boolean p2Dead = false;
	private boolean shootAnimation = false, running = false, startLaserCount = false, shootBehind;
	public static boolean axeBodyRemoval = false, slowed = false, slowRestart = false, soundStop = false,  axeSwinging = false,  
			isShooting = false, timeToShake = false, movHalt = false;
	public static float axeSwingTimer = 0;
	private int ID;
	public static Array<CreateBullet> pellets2 = new Array<CreateBullet>();
	public static Array<CreateBullet> bullets2 = new Array<CreateBullet>();
	public static Array<CreateBullet> lasers2 = new Array<CreateBullet>();
	public static Vector2 player2SpawnPos = new Vector2(0,0);
	private Vector3 mousePosition = new Vector3(0,0, 0);
	public CreateBullet createBullet;

	public PlayerTwo(World world) {
		this.world = world;
		
		//determines the health depending on the difficulty
		if (DifficultyScreen.difficulty == 1) {
			player2HP = player2MaxHP1;			
		}
		if (DifficultyScreen.difficulty == 2) {
			player2HP = player2MaxHP2;			
		}
		//if player is on a certain level then assign correct static vector3 mouse positions
		if (Mutagen.level == "1") {
			mousePosition = Level1.mousePosition;
		}else if (Mutagen.level == "2") {
			mousePosition = Level2.mousePosition;
		}else if (Mutagen.level == "3") {
			mousePosition = Level3.mousePosition;
		}
		storedHP = player2HP;
		slowedCounter = 0;
		p2Dead = false;
		slowRestart = false;
		
		//retrieves atlas assets loaded from asset manager and creates animation and standing region objects
		revolverTextureAtlas = Mutagen.manager.get("sprites/player2/revolverP2.atlas", TextureAtlas.class);
		revolverAnimation = new Animation <TextureRegion>(1f/15f, revolverTextureAtlas.getRegions());
		revolverStandingRegion = revolverTextureAtlas.findRegion("tile000");

		rifleTextureAtlas = Mutagen.manager.get("sprites/player2/rifleP2.atlas", TextureAtlas.class);
		rifleAnimation = new Animation <TextureRegion>(1f/15f, rifleTextureAtlas.getRegions());
		rifleStandingRegion = rifleTextureAtlas.findRegion("tile000");

		shotgunTextureAtlas = Mutagen.manager.get("sprites/player2/shotgunP2.atlas", TextureAtlas.class);
		shotgunAnimation = new Animation <TextureRegion>(1f/15f, shotgunTextureAtlas.getRegions());
		shotgunStandingRegion = shotgunTextureAtlas.findRegion("tile000");

		assaultRifleTextureAtlas = Mutagen.manager.get("sprites/player2/assaultRifleP2.atlas", TextureAtlas.class);
		assaultRifleAnimation = new Animation<TextureRegion>(1f/15f, assaultRifleTextureAtlas.getRegions());
		assaultRifleStandingRegion = assaultRifleTextureAtlas.findRegion("tile000");

		laserTextureAtlas = Mutagen.manager.get("sprites/player2/laserP2.atlas", TextureAtlas.class);
		laserAnimation = new Animation <TextureRegion>(1f/15f, laserTextureAtlas.getRegions());
		laserStandingRegion = laserTextureAtlas.findRegion("tile000");

		axeSwingTextureAtlas = Mutagen.manager.get("sprites/player2/battleAxeP2.atlas", TextureAtlas.class);
		axeSwingAnimation = new Animation <TextureRegion>(1f/15f, axeSwingTextureAtlas.getRegions());
		axeStandingRegion = axeSwingTextureAtlas.findRegion("tile000");
		runningSound = Gdx.audio.newSound(Gdx.files.internal("sound effects/running.mp3"));
		
		//GETS ASSETS THAT HAVE BEEN LOADED BY ASSET MANAGER
		p2HP = Mutagen.manager.get("sprites/player2/hp2.png");
		p2HPBG = Mutagen.manager.get("sprites/player2/hpBg2.png");
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
		oneLifeLeft = Mutagen.manager.get("sound effects/lifeRemaining.mp3");
		deadSfx = Mutagen.manager.get("sound effects/playerDead.mp3");
		ID = 2; //defines if player is player 1 or player 2 for the CreateBullet class
		definePlayer();
	}

	public void definePlayer() {
		//define player body
		bdef.position.set(player2SpawnPos);

		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(12 / Mutagen.PPM);

		fdef.shape = shape;
		//		fdef.filter.categoryBits = Mutagen.PLAYER_TWO;
		//		fdef.filter.maskBits = Mutagen.WALL | Mutagen.GRUNT | Mutagen.SCIENTIST | Mutagen.HP_PICKUP |Mutagen.SOLDIER |Mutagen.SHOOT_OVER | Mutagen.PLAYER_TWO | Mutagen.FLAYER | Mutagen.FLAYER_SPIKES;
		b2body.createFixture(fdef).setUserData("player2");
		angle = 0;

		shape.dispose();
	}
	//create b2body physics body, set spawn location, masking, and category bits
	public void renderSprite(SpriteBatch batch) {
		float posX = b2body.getPosition().x;
		float posY = b2body.getPosition().y;
		
		//DEPENDING ON THE WEAPON THEN IT DETERMINES THE SPEED OF PLAYER AS WELL AS THE ANIMATIONS TO USE FOR THE PLAYER
		//revolver			
		if (GunSelectionScreen.p2WeaponSelected == "revolver") {
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
		else if (GunSelectionScreen.p2WeaponSelected == "rifle") {
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
		else if (GunSelectionScreen.p2WeaponSelected == "shotgun") {
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
		else if (GunSelectionScreen.p2WeaponSelected == "assault rifle") {
			speed = 1.2f;
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
		else if (GunSelectionScreen.p2WeaponSelected == "laser") {
			speed = 1f;	
			if (shootAnimation) {
				batch.draw(laserAnimation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();

				if(laserAnimation.isAnimationFinished(timePassed)) {
					shootAnimation = false;
					timePassed = 0;
				}
			}else {
				batch.draw(laserStandingRegion,posX - .2f, posY - .2f, 20 / Mutagen.PPM, 20 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle );
			}
		}

		//battle axe
		else if (GunSelectionScreen.p2WeaponSelected == "battle axe") {
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
			batch.draw(p2HPBG, p2PosX - .30f, p2PosY - .28f, player2MaxHP1 / (Mutagen.PPM +250), 3f / Mutagen.PPM); //gray backing behind HP bar	
			batch.draw(p2HP, p2PosX - .30f, p2PosY - .28f, player2HP / (Mutagen.PPM + 250), 3f / Mutagen.PPM); //HP bar
		}else{
			batch.draw(p2HPBG, p2PosX - .3f, p2PosY - .28f, player2MaxHP2 / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //gray backing behind HP bar	
			batch.draw(p2HP, p2PosX - .3f, p2PosY - .28f, player2HP / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //HP bar
		}

		//HIT SFX
		if (storedHP > player2HP) {

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
			storedHP = player2HP;
		} else if (player2HP == player2MaxHP1) {
			storedHP = player2HP;
		}

		//PLAYER DIES
		if (player2HP <= 0) {
			//player's second life
			if (secondWind == 1) {
				if (DifficultyScreen.difficulty ==1) {
					player2HP = player2MaxHP1;	
					long oLL = oneLifeLeft.play(Mutagen.sfxVolume);

				}
				secondWind += 1;
			}else if (secondWind > 1 || DifficultyScreen.difficulty == 2){
				//player dies
				world.destroyBody(this.b2body);
				p2PosX = 0;
				p2PosY = 0;
				p2Dead = true;
				runningSound.stop();
				long pD = deadSfx.play(Mutagen.sfxVolume);

			}
		}

	}
	//CALLED WHEN PLAYER SHOOTS GUN AND HANDLES SFX AND CALLS CreateBullet.java
	public void shootGun() {

		//laser delay for build up of power effect
		if (startLaserCount) {
			waitToShootL += 1;
		}
		//deletes the battle axe
		if (GunSelectionScreen.p2WeaponSelected == "battle axe" && PlayerTwo.axeBodyRemoval) {
			world.destroyBody(createBullet.b2body);
			axeBodyRemoval = false;
			bullets2.clear();
			createBullet.b2body = null;
		}
		if (isShooting) {
			switch (GunSelectionScreen.p2WeaponSelected) {
			case "laser":
				startLaserCount = true;
				if (Mutagen.sfxVolume != 0) {
					long lsId = laserShot.play(Mutagen.sfxVolume / 2);
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
					pellets2.add(createBullet);
				}
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

			if (GunSelectionScreen.p2WeaponSelected != "shotgun" && GunSelectionScreen.p2WeaponSelected != "laser") {				
				createBullet = new CreateBullet(world, ID);
				bullets2.add(createBullet);	
			}
			isShooting = false;
			timeToShake = true;
		}
		//laser blast delay
		if (startLaserCount) {
			if (waitToShootL >= 0 && waitToShootL < 1){
				createBullet = new CreateBullet(world, ID);
				lasers2.add(createBullet);
			}

			if (waitToShootL >=17 && waitToShootL < 18) {
				createBullet = new CreateBullet(world, ID);
				lasers2.add(createBullet);
			}

			if (waitToShootL >= 27 && waitToShootL < 28) {
				createBullet = new CreateBullet(world, ID);
				lasers2.add(createBullet);
				waitToShootL = 0;
				startLaserCount = false;
			}
		}
	}
	//PLAYER INPUT FOR MOVEMENT AND SHOOTING
	public void handleInput(float delta) {
		//setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + (5 / Mutagen.PPM));
		p2PosX = b2body.getPosition().x;
		p2PosY = b2body.getPosition().y;
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

		if (!Level3.cin) {

			if (GunSelectionScreen.p2AimStyle == 1) {
				if (!movHalt) {
					if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed * speedAB, speed * speedAB);
					}else if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed * speedAB, speed * speedAB);
					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed * speedAB, -speed * speedAB );   
					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed * speedAB, -speed * speedAB );
					}
					else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
						this.b2body.setLinearVelocity(0f, speed);

					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
						this.b2body.setLinearVelocity(0f, -speed);
					}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed, 0f);

					}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed, 0f);
					}
				}
			}

			shootGun();
			//If player has chosen the rotational or directional aim style
			if (GunSelectionScreen.p2AimStyle == 1) {
				if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)){
					angle += rotationSpeed;
				}
				else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_9)){
					angle -= rotationSpeed;
				}			
			}else {
				if (!movHalt) {
					if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed * speedAB, speed * speedAB);
					}else if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed * speedAB, speed * speedAB);
					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed * speedAB, -speed * speedAB );   
					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed * speedAB, -speed * speedAB );
					}
					else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
						this.b2body.setLinearVelocity(0f, speed);
						if (!shootBehind) {
							angle = 0;
						}
					}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
						this.b2body.setLinearVelocity(0f, -speed);

						if (!shootBehind) {
							angle = 180;
						}
					}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
						this.b2body.setLinearVelocity(-speed, 0f);
						if (!shootBehind) {
							angle = 90;
						}
					}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
						this.b2body.setLinearVelocity(speed, 0f);
						if (!shootBehind) {
							angle = 270;
						}
					} 
					if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
						shootBehind = true;

					}else {
						shootBehind = false;
					}

				}
			}

			if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
				if (timeSinceLastShot <=0) {
					isShooting = true;
					shootAnimation = true;
					switch (GunSelectionScreen.p2WeaponSelected) {
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

		if (b2body.getLinearVelocity().x > 0 || b2body.getLinearVelocity().x < 0 || b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0) {
			if (running) {	
				if (Mutagen.sfxVolume != 0) {
					long rS = runningSound.loop(Mutagen.sfxVolume);
				}
				running = false;
			}

		}else if (!running && !p2Dead && !soundStop) {
			runningSound.stop();
			running = true;
		}

	}
}
