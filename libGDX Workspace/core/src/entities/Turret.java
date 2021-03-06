package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

public class Turret {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 200;
	public static int atkDmg = 12;
	private TextureAtlas turretAtkAtlas;
	private Animation <TextureRegion> turretAtkAnimation;
	private TextureRegion turretStandingRegion;
	public static Vector2 turretPos = new Vector2(0, 0);
	private float shootTimer = 20, timePassed = 0, differenceX, differenceY;
	public static Vector2 turretSpawnPos = new Vector2(0,0);
	private boolean shootAnimation = false;
	private Sound turretShoot;
	TurretBullets tB;
	public static Array<Turret> turrets = new Array<Turret>();
	LogFileHandler lfh = new LogFileHandler();

	public Turret(World world) {
		this.world = world;
		try {
			turretAtkAtlas = Mutagen.manager.get("sprites/turret/turretAtkAnimation.atlas");
			turretAtkAnimation = new Animation <TextureRegion>(1f/15f, turretAtkAtlas.getRegions());
			turretStandingRegion = turretAtkAtlas.findRegion("tile000");
			turretShoot = Mutagen.manager.get("sound effects/enemies/turretAtk.mp3");
			defineTurret();
		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");

		}
	}

	public void defineTurret() {
		try {
			bdef.position.set(turretSpawnPos);
			turretPos = bdef.position;
			bdef.type = BodyDef.BodyType.DynamicBody;
			//create body in the world
			b2body = world.createBody(bdef);
			b2body.setUserData(this);
			FixtureDef fdef = new FixtureDef();
			CircleShape shape = new CircleShape();
			shape.setRadius(10 / Mutagen.PPM);
			b2body.setLinearDamping(10);
			fdef.density = 100000; //made extremely dense to prevent anything from moving it
			fdef.shape = shape;
			fdef.filter.categoryBits = Mutagen.ENEMY;
			fdef.filter.maskBits =  Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET;
			b2body.createFixture(fdef).setUserData("turret");
		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");

		} 
	}

	public void renderSprite(SpriteBatch batch) {
		 try {
			//Selects which player to attack depending on which player is closer by using the pythagorean theorem
			if (!PlayerMode.OneP) {
				//PlayerOne
				float differencePlayerX =  PlayerOne.p1PosX - b2body.getPosition().x;
				float differencePlayerY =  PlayerOne.p1PosY - b2body.getPosition().y;
				
				//PlayerTwo
				float differencePlayer2X =  PlayerTwo.p2PosX - b2body.getPosition().x;
				float differencePlayer2Y =  PlayerTwo.p2PosY - b2body.getPosition().y;
				
				float player1Dif = (float) Math.sqrt((differencePlayerX*differencePlayerX)+(differencePlayerY*differencePlayerY));
				float player2Dif = (float) Math.sqrt((differencePlayer2X*differencePlayer2X)+(differencePlayer2Y*differencePlayer2Y));
				
				if (player1Dif < player2Dif) {
					differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
					differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
				}else {
					differenceX = PlayerTwo.p2PosX - b2body.getPosition().x;
					differenceY = PlayerTwo.p2PosY - b2body.getPosition().y;
				}
			}else {
				differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
				differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
			}
			float angle2 = MathUtils.atan2(differenceY, differenceX);
			float angle = angle2 * MathUtils.radDeg;
			angle = angle - 90; //makes it a full 360 degrees
			if (angle < 0) {
				angle += 360 ;
			}
			float posX = this.b2body.getPosition().x;
			float posY = this.b2body.getPosition().y;
			if (shootAnimation) {
				batch.draw(turretAtkAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 60 / Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if(turretAtkAnimation.isAnimationFinished(timePassed)) {
					shootAnimation = false;
					timePassed = 0;
				}
			}else {
				batch.draw(turretStandingRegion, posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 60 / Mutagen.PPM, 1, 1, angle);
			}
			shooting(angle);
		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

	public void shooting(float angle2) {
		try {
			if (!PlayerOne.p1Dead || !PlayerTwo.p2Dead) {
				shootTimer += .50;
				if (shootTimer >= 35) {
					if (Mutagen.sfxVolume != 0) {
						Long tS = turretShoot.play(Mutagen.sfxVolume - .9f);					
					}
					shootAnimation = true;
					for (int i = 0; i < 1; i++) {
						tB = new TurretBullets(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle2);	
					}
					shootTimer = 0;
				}
			}
		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}
}
