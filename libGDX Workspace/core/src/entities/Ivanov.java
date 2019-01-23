package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import BackEnd.Mutagen;
import levels.Level3;
import screens.DifficultyScreen;
import screens.PlayerMode;

public class Ivanov {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public static int health = 0, target = 0, thornAtk = 50;
	public static boolean contAtk = false;
	public static Vector2 ivanovPos = new Vector2(0, 0);
	public static Vector2 ivanovSpawnPos = new Vector2(0,0);
	private Vector2 tPos = new Vector2(0, 0);
	private float shootTimer = 0, chargeTimer = 0, timePassed = 0, angle, angle3, differenceX, differenceY, boundary = .7f, speed = 9, tPosDifX, 
			tPosDifY, wait, oldAngle, player1Dif, player2Dif, atk = 15, thornSpeed = 2, maxHP = 0;
	private boolean alreadyShot = false, tooFarAway = false, startAnimation = true, animationFinished = true;
	private TextureRegion ivanovStandingRegion;
	private Texture HP, HPBG;
	private TextureAtlas ivanovTransAtlas, atkAtlas, thornAtlas;
	private Animation <TextureRegion> ivanovTransAnimation, atkAnimation, thornAnimation;
	private IvanovThorns iT;
	//public static Array<CreateBullet> ivanov = new Array<CreateBullet>();

	public Ivanov(World world) {
		this.world = world;
		HP = Mutagen.manager.get("sprites/ivanov/ivanovHP.png");
		HPBG = Mutagen.manager.get("sprites/ivanov/ivanovHPBG.png");
		ivanovTransAtlas = Mutagen.manager.get("sprites/ivanov/ivanovTransformation.atlas");
		ivanovTransAnimation = new Animation <TextureRegion>(1f/15f, ivanovTransAtlas.getRegions());
		atkAtlas = Mutagen.manager.get("sprites/ivanov/atkAnimation.atlas");
		atkAnimation = new Animation <TextureRegion>(1f/15f, atkAtlas.getRegions());
		thornAtlas = Mutagen.manager.get("sprites/ivanov/thornAnimation.atlas");
		thornAnimation = new Animation <TextureRegion>(1f/15f, thornAtlas.getRegions());
		ivanovStandingRegion = ivanovTransAtlas.findRegion("tile078");
		defineIvanov();
	}

	public void defineIvanov() {
		bdef.position.set(ivanovSpawnPos);
		health = 5000;
		maxHP = health;
		contAtk = false;
		ivanovPos = bdef.position;

		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(30 / Mutagen.PPM);
		b2body.setLinearDamping(3f);
		fdef.density = 800; //made extremely dense to prevent anything from moving it
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.ENEMY;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET | Mutagen.SHOOT_OVER;
		b2body.createFixture(fdef).setUserData("ivanov"); 
	}
	public void renderSprite(SpriteBatch batch) {

		//Selects which player to attack depending on which player is closer by using the pythagorean theorem
		if (health > 0 && !startAnimation && !alreadyShot && !contAtk && animationFinished){
			//CO-OP
			if (!PlayerMode.OneP) {
				//PlayerOne
				float differencePlayerX =  PlayerOne.p1PosX - b2body.getPosition().x;
				float differencePlayerY =  PlayerOne.p1PosY - b2body.getPosition().y;

				//PlayerTwo
				float differencePlayer2X =  PlayerTwo.p2PosX - b2body.getPosition().x;
				float differencePlayer2Y =  PlayerTwo.p2PosY - b2body.getPosition().y;

				player1Dif = (float) Math.sqrt((differencePlayerX*differencePlayerX)+(differencePlayerY*differencePlayerY));
				player2Dif = (float) Math.sqrt((differencePlayer2X*differencePlayer2X)+(differencePlayer2Y*differencePlayer2Y));
				//fire at players 1
				if (player1Dif < player2Dif) {
					differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
					differenceY = PlayerOne.p1PosY - b2body.getPosition().y;

				}else {
					//fire at player 2
					differenceX = PlayerTwo.p2PosX - b2body.getPosition().x;
					differenceY = PlayerTwo.p2PosY - b2body.getPosition().y;

				}

				angle3 = MathUtils.atan2(differenceY, differenceX);
				tPos.x = (float) (Math.cos(angle3) * speed);
				tPos.y = (float) (Math.sin(angle3) * speed);
				this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);	
				//				}
			}else {
				//single player
				differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
				differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
				player1Dif = (float) Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));

				angle3 = MathUtils.atan2(differenceY, differenceX);
				tPos.x = (float) (Math.cos(angle3) * speed);
				tPos.y = (float) (Math.sin(angle3) * speed);

				this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);				
			}
			//starts the counter for the shooting attack and charging at players
			shootTimer += .1f;
			chargeTimer += .1f;
		}


		float angle2 = MathUtils.atan2(differenceY, differenceX);
		angle2 += 3.14159;
		angle = angle2 * MathUtils.radDeg;
		angle = angle - 90; //makes it a full 360 degrees
		if (angle < 0) {
			angle += 360 ;
		}
		float posX = this.b2body.getPosition().x;
		float posY = this.b2body.getPosition().y;

		if(startAnimation) {
			Level3.cin = true;
			batch.draw(ivanovTransAnimation.getKeyFrame(timePassed), posX - .37f, posY - .37f,  40 / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80/ Mutagen.PPM, 2, 2, angle - 90);
			timePassed += Gdx.graphics.getDeltaTime();
			if(ivanovTransAnimation.isAnimationFinished(timePassed)) {
				startAnimation = false;
				timePassed = 0;
				Level3.cin = false;
			}
			//Ivanov's melee atk
		}else if (contAtk || !animationFinished) {
			animationFinished = false;
			batch.draw(atkAnimation.getKeyFrame(timePassed), posX - .37f, posY - .37f, 40 / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80/ Mutagen.PPM, 2, 2, angle);
			timePassed += Gdx.graphics.getDeltaTime();
			if(atkAnimation.isAnimationFinished(timePassed)) {
				//shootAnimation = false;
				if (target == 1) {
					PlayerOne.player1HP -= atk;
				}else if (target == 2) {
					PlayerTwo.player2HP -= atk;
				}
				animationFinished = true;
				timePassed = 0;

			}
		}else if (shootTimer >= 100) {

			alreadyShot = true;
			batch.draw(thornAnimation.getKeyFrame(timePassed), posX - .37f, posY - .37f, 40 / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80/ Mutagen.PPM, 2, 2, angle);
			timePassed += Gdx.graphics.getDeltaTime();
			if(thornAnimation.isAnimationFinished(timePassed)) {
				timePassed = 0;
				shootTimer = 0;
				shooting();
				alreadyShot = false;
			}
		}
		else {
			batch.draw(ivanovStandingRegion, posX - .37f, posY - .37f, 40  / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80 / Mutagen.PPM, 2, 2, angle);					
		}


		//CHARGE AT PLAYER(S)
		if (chargeTimer <= 40) {
			speed = 9;
		}else if (chargeTimer > 40) {
			speed = 30;
			if (chargeTimer > 55) {
				chargeTimer = 0;

			}
		}

		//IVANOV'S HEALTH
		if (DifficultyScreen.difficulty == 1 || DifficultyScreen.difficulty == 2) {
			if (!Level3.cin) {
				batch.draw(HPBG, this.b2body.getPosition().x - .5f, this.b2body.getPosition().y- .66f, maxHP / ( maxHP - 100), 9f / Mutagen.PPM); //gray backing behind HP bar	
				batch.draw(HP, this.b2body.getPosition().x - .5f, this.b2body.getPosition().y - .63f, health / (maxHP - 100), 3f / Mutagen.PPM); //HP bar					
			}

		}
		//			else{
		//				batch.draw(p1HPBG, p1PosX - .3f, p1PosY - .28f, player1MaxHP2 / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //gray backing behind HP bar	
		//				batch.draw(p1HP, p1PosX - .3f, p1PosY - .28f, player1HP / (Mutagen.PPM + 150), 3f / Mutagen.PPM); //HP bar			
		//			}

	}
	//Shoot out thorns
	public void shooting() {

		if (PlayerMode.OneP) {
			if (!PlayerOne.p1Dead) {
				oldAngle = angle;

				//shoots 36 thorns
				for (int p = 0; p < 2; p++) {
					
					for (int i = 0; i <= 360; i += 10) {
						if (p == 0) {
							thornSpeed = 2;
						}else if (p == 1) {
							thornSpeed = 1.5f;
						}
						angle = oldAngle;
						angle += i;
						iT = new IvanovThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle, thornSpeed);	

					}

				}
			}
		}
		else {
			if (!PlayerOne.p1Dead || !PlayerTwo.p2Dead) {

				oldAngle = angle;

				//shoots 36 thorns
				for (int p = 0; p < 2; p++) {
					
					for (int i = 0; i <= 360; i += 10) {
						if (p == 0) {
							thornSpeed = 2;
						}else if (p == 1) {
							thornSpeed = 1.5f;
						}
						angle = oldAngle;
						angle += i;
						iT = new IvanovThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle, thornSpeed);	

					}

				}
			}
		}
	}

}
