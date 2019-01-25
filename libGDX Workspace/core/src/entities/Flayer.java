package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

import BackEnd.LogFileHandler;
import BackEnd.Mutagen;
import screens.PlayerMode;

public class Flayer {
	public World world; // world player will live in
	public Body b2body; // creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 200;
	public static int atkDmg = 8;
	private TextureAtlas flayerAtkAtlas;
	private Animation<TextureRegion> flayerAtkAnimation;
	private TextureAtlas flayerDamagedAtlas;
	private Animation<TextureRegion> flayerDamagedAnimation;
	private TextureRegion flayerStandingRegion;

	private Texture blood;
	public static Vector2 flayerPos = new Vector2(0, 0);
	private float shootTimer = 0, timePassed = 0, angle, angleRad, oldAngle, differenceX, differenceY, boundary = .7f,
			speed = 1, tPosDifX, tPosDifY, wait, oldX, oldY, player1Dif, player2Dif;
	public static Vector2 flayerSpawnPos = new Vector2(0, 0);
	private Vector2 originPos = new Vector2(0, 0);
	private Vector2 tPos = new Vector2(0, 0);
	private Vector2 targetPos = new Vector2(0, 0);
	private boolean shootAnimation = false, findTarget = true, stationary = false, tooFarAway = false;
	public boolean tookDamage = false;
	LogFileHandler lfh = new LogFileHandler();

	private Sound flayerShoot;
	FlayerThorns fT;
	public static Array<Flayer> flayers = new Array<Flayer>();

	public Flayer(World world) {
		try {
			this.world = world;
			flayerAtkAtlas = Mutagen.manager.get("sprites/flayer/flayerAtkAnimation.atlas");
			flayerAtkAnimation = new Animation<TextureRegion>(1f / 15f, flayerAtkAtlas.getRegions());
			flayerDamagedAtlas = Mutagen.manager.get("sprites/flayer/flayerDamaged.atlas");
			flayerDamagedAnimation = new Animation<TextureRegion>(1f / 15f, flayerDamagedAtlas.getRegions());
			flayerStandingRegion = flayerAtkAtlas.findRegion("tile000");
			flayerShoot = Mutagen.manager.get("sound effects/enemies/thorn throw.mp3");
			blood = Mutagen.manager.get("sprites/MutantBlood.png");
			defineFlayer();
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	public void defineFlayer() {
		try {
			bdef.position.set(flayerSpawnPos);
			flayerPos = bdef.position;
			originPos = bdef.position;

			bdef.type = BodyDef.BodyType.DynamicBody;
			// create body in the world
			b2body = world.createBody(bdef);
			b2body.setUserData(this);
			FixtureDef fdef = new FixtureDef();
			CircleShape shape = new CircleShape();
			shape.setRadius(12 / Mutagen.PPM);
			b2body.setLinearDamping(5f);
			fdef.density = 400; // made extremely dense to prevent anything from moving it
			fdef.shape = shape;
			fdef.filter.categoryBits = Mutagen.ENEMY;
			fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET | Mutagen.SHOOT_OVER;
			b2body.createFixture(fdef).setUserData("flayer");
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	public void renderSprite(SpriteBatch batch) {

		try {
			// Selects which player to attack depending on which player is closer by using
			// the pythagorean theorem
			if (this.health > 0) {
				if (!PlayerMode.OneP) {
					// PlayerOne
					float differencePlayerX = PlayerOne.p1PosX - b2body.getPosition().x;
					float differencePlayerY = PlayerOne.p1PosY - b2body.getPosition().y;

					// PlayerTwo
					float differencePlayer2X = PlayerTwo.p2PosX - b2body.getPosition().x;
					float differencePlayer2Y = PlayerTwo.p2PosY - b2body.getPosition().y;

					player1Dif = (float) Math
							.sqrt((differencePlayerX * differencePlayerX) + (differencePlayerY * differencePlayerY));
					player2Dif = (float) Math.sqrt(
							(differencePlayer2X * differencePlayer2X) + (differencePlayer2Y * differencePlayer2Y));
					// fire at players 1
					if (player1Dif < player2Dif) {
						differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
						differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
						// checks to see how far away the flayer is from the targeted player to know
						// whether or not to get closer before shooting
						if (player1Dif > 4.7) {
							tooFarAway = true;
						} else {
							tooFarAway = false;
						}

					} else {
						// fire at player 2
						differenceX = PlayerTwo.p2PosX - b2body.getPosition().x;
						differenceY = PlayerTwo.p2PosY - b2body.getPosition().y;
						// checks to see how far away the flayer is from the targeted player to know
						// whether or not to get closer before shooting
						if (player2Dif > 4.7) {
							tooFarAway = true;
						} else {
							tooFarAway = false;
						}
					}
					if (tooFarAway) {
						// move towards the player to get closer
						angleRad = MathUtils.atan2(differenceY, differenceX);
						tPos.x = (float) (Math.cos(angleRad) * speed);
						tPos.y = (float) (Math.sin(angleRad) * speed);
						this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x,
								b2body.getWorldCenter().y, true);
					}
				} else {
					// single player
					differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
					differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
					player1Dif = (float) Math.sqrt((differenceX * differenceX) + (differenceY * differenceY));
					// checks to see how far away the flayer is from the targeted player to know
					// whether or not to get closer before shooting
					if (player1Dif > 5) {
						tooFarAway = true;
					} else {
						tooFarAway = false;
					}
					if (tooFarAway) {
						// move towards the player to get closer
						angleRad = MathUtils.atan2(differenceY, differenceX);
						tPos.x = (float) (Math.cos(angleRad) * speed);
						tPos.y = (float) (Math.sin(angleRad) * speed);

						this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x,
								b2body.getWorldCenter().y, true);
					}
				}

				float angle2 = MathUtils.atan2(differenceY, differenceX);
				angle = angle2 * MathUtils.radDeg;
				angle = angle - 90; // makes it a full 360 degrees

				if (angle < 0) {
					angle += 360;
				}
				float posX = this.b2body.getPosition().x;
				float posY = this.b2body.getPosition().y;
				if (shootAnimation && !tookDamage) {
					batch.draw(flayerAtkAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM,
							16 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					timePassed += Gdx.graphics.getDeltaTime();
					if (flayerAtkAnimation.isAnimationFinished(timePassed)) {
						shootAnimation = false;
						timePassed = 0;
					}
				} else {
					if (!tookDamage) {
						batch.draw(flayerStandingRegion, posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM,
								40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
					} else if (tookDamage) {
						batch.draw(flayerDamagedAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f,
								20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
						timePassed += Gdx.graphics.getDeltaTime();
						if (flayerDamagedAnimation.isAnimationFinished(timePassed)) {
							timePassed = 0;
							tookDamage = false;
						}
					}
				}

				if (!stationary && !tooFarAway) {
					// movement
					if (findTarget) {
						// X
						float minX = originPos.x - boundary;
						// Y
						float minY = originPos.y - boundary;

						targetPos.x = (float) (Math.random() * (boundary * 2) + minX);
						targetPos.y = (float) (Math.random() * (boundary * 2) + minY);
						tPosDifX = targetPos.x - this.b2body.getPosition().x;
						tPosDifY = targetPos.y - this.b2body.getPosition().y;
						angleRad = MathUtils.atan2(tPosDifY, tPosDifX);
						tPos.x = (float) (Math.cos(angleRad) * speed);
						tPos.y = (float) (Math.sin(angleRad) * speed);
						findTarget = false;
					}

					if (!findTarget) {
						if (this.b2body.getPosition().x > targetPos.x - .2
								&& this.b2body.getPosition().x < targetPos.x + .2
								&& this.b2body.getPosition().y > targetPos.y - .2f
								&& this.b2body.getPosition().y < targetPos.y + .2f) {
							findTarget = true;
							stationary = true;
						}
						this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x,
								b2body.getWorldCenter().y, true);
					}

				} else {
					wait += 1;
					if (wait > 45) {
						wait = 0;
						stationary = false;
					}
				}

				oldX = this.b2body.getPosition().x;
				oldY = this.b2body.getPosition().y;
				if (!tooFarAway) {
					shooting();
				}

			} else {
				batch.draw(blood, oldX, oldY, 32 / Mutagen.PPM, 32 / Mutagen.PPM);
			}
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}

	}

	public void shooting() {
		try {
			oldAngle = angle;
			if (PlayerMode.OneP) {
				if (!PlayerOne.p1Dead) {
					shootTimer += .50;
					if (shootTimer >= 65) {
						if (Mutagen.sfxVolume != 0) {
							Long tS = flayerShoot.play(Mutagen.sfxVolume - .8f);
						}
						shootAnimation = true;

						for (int i = 0; i < 3; i++) {
							angle = oldAngle;

							if (i == 0) {
								angle = angle - 23;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							} else if (i == 1) {
								angle = oldAngle;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							} else if (i == 2) {
								angle = angle + 23;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							}

						}

						shootTimer = 0;
					}
				}
			} else {
				if (!PlayerOne.p1Dead || !PlayerTwo.p2Dead) {
					shootTimer += .50;
					if (shootTimer >= 65) {
						if (Mutagen.sfxVolume != 0) {
							Long tS = flayerShoot.play(Mutagen.sfxVolume - .8f);
						}
						shootAnimation = true;
						for (int i = 0; i < 3; i++) {
							angle = oldAngle;

							if (i == 0) {
								angle = angle - 23;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							} else if (i == 1) {
								angle = oldAngle;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							} else if (i == 2) {
								angle = angle + 23;
								fT = new FlayerThorns(world, this.b2body.getPosition().x, this.b2body.getPosition().y,
										angle);
							}

						}
						shootTimer = 0;
					}
				}
			}
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

}
