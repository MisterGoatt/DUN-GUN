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

import BackEnd.Mutagen;
import screens.PlayerMode;


public class Scientist{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private float runSpeed = 2.2f, timePassed = 0, differenceX, differenceY, oldX, oldY;
	private TextureAtlas scientistDamagedAtlas, scientistAtkAtlas;
	private Animation <TextureRegion> scientistDamagedAnimation, scientistAtkAnimation;
	private TextureRegion scientistStandingRegion;
	private Texture blood;
	public int atkdmg = 12, health = 120, target;
	public boolean attack = false, tookDamage = false, contAtk = false, atkSoundStop = true;
	private Sound atkSound;
	private boolean initialDmg = false; //makes sure the player takes damage at first when the enemy touches player
	public static Array<Scientist> scientists = new Array<Scientist>();
	public static Vector2 scientistPos = new Vector2(0,0);

	public Scientist(World world) {
		this.world = world;

		scientistAtkAtlas = Mutagen.manager.get("sprites/scientist/scientistAtk.atlas");
		scientistAtkAnimation = new Animation <TextureRegion>(1f/15f, scientistAtkAtlas.getRegions());
		scientistStandingRegion = scientistAtkAtlas.findRegion("tile000");
		scientistDamagedAtlas = Mutagen.manager.get("sprites/scientist/scientistDamaged.atlas");
		scientistDamagedAnimation = new Animation <TextureRegion>(1f/15f, scientistDamagedAtlas.getRegions());
		atkSound = Mutagen.manager.get("sound effects/enemies/scientistAtk.mp3");
		blood = Mutagen.manager.get("sprites/Blood.png");
		defineScientist();
	}

	public void defineScientist() {
		bdef.position.set(scientistPos);
		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		b2body.setLinearDamping(5f);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(16 / Mutagen.PPM);
		fdef.density = 400;
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.ENEMY;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET |  Mutagen.SHOOT_OVER;
		b2body.createFixture(fdef).setUserData("scientist");
	}

	public void renderSprite(SpriteBatch batch) {

		if (this.health > 0){


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
			float gposX = (float) (Math.cos(angle2)) * runSpeed;
			float gposY = (float) (Math.sin(angle2)) * runSpeed;

			//TOOK OUT !tookDamage
			if (!attack && !contAtk && !tookDamage) {
				batch.draw(scientistStandingRegion, posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 60 / Mutagen.PPM, 1, 1, angle);
			}
			else if (contAtk && !tookDamage) {

				if (!initialDmg) {
					if (target ==1) {
						PlayerOne.player1HP -= atkdmg;					
					}else {
						PlayerTwo.player2HP -= atkdmg;
					}
					initialDmg = true;
				}
				batch.draw(scientistAtkAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 60 / Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if (!atkSoundStop) {
					if (Mutagen.sfxVolume != 0) {
						long sSId = atkSound.play(Mutagen.sfxVolume);
					}
					atkSoundStop = true;
				}
				if(scientistAtkAnimation.isAnimationFinished(timePassed)) {
					//long sSId = atkSound.play(1f);
					timePassed = 0;
					initialDmg = false;
					if (target ==1) {
						PlayerOne.slowed = true;
						PlayerOne.slowRestart = true;
					}else {
						PlayerTwo.slowed = true;
						PlayerTwo.slowRestart = true;
					}
					atkSoundStop = false;
				}
			}
			else {
				batch.draw(scientistDamagedAnimation.getKeyFrame(timePassed), posX - .17f, posY -.13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 60 / Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if(scientistDamagedAnimation.isAnimationFinished(timePassed)) {
					timePassed = 0;
					tookDamage = false;
				}
			}

			if (!PlayerMode.OneP) {
				if (!PlayerTwo.p2Dead) {
					b2body.applyLinearImpulse(gposX, gposY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
					b2body.setTransform(this.b2body.getPosition().x, this.b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation

				}
				else if (!PlayerOne.p1Dead) {
					b2body.applyLinearImpulse(gposX, gposY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
					b2body.setTransform(this.b2body.getPosition().x, this.b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
				}
			}

			else {
				if (!PlayerOne.p1Dead) {
					b2body.applyLinearImpulse(gposX, gposY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
					b2body.setTransform(this.b2body.getPosition().x, this.b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
				}
			}
			oldX = this.b2body.getPosition().x;
			oldY = this.b2body.getPosition().y;

		}else {
			batch.draw(blood, oldX, oldY, 32 / Mutagen.PPM, 32 / Mutagen.PPM);

		}
	}
}