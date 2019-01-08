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

import BackEnd.Mutagen;
import screens.PlayerMode;

public class Soldier {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 150;
	public static int atkDmg = 8;
	private TextureAtlas soldierAtkAtlas;
	private Animation <TextureRegion> soldierAtkAnimation;
	private TextureRegion soldierStandingRegion;
	public static Vector2 soldierPos = new Vector2(0, 0);
	private float shootTimer = 20, timePassed = 0, angle, angle3, differenceX, differenceY, boundary = .7f, speed = 1, tPosDifX, tPosDifY, wait;
	public static Vector2 soldierSpawnPos = new Vector2(0,0);
	private Vector2 originPos = new Vector2(0, 0);
	private Vector2 tPos = new Vector2(0, 0);
	private Vector2 targetPos = new Vector2(0, 0);

	
	private boolean shootAnimation = false, findTarget = true, stationary = false;
	private Sound soldierShoot;
	SoldierBullets sB;
	public static Array<Soldier> soldiers = new Array<Soldier>();
	
	public Soldier(World world) {
		this.world = world;
		soldierAtkAtlas = Mutagen.manager.get("sprites/soldier/soldierAtkAnimation.atlas");
		soldierAtkAnimation = new Animation <TextureRegion>(1f/15f, soldierAtkAtlas.getRegions());
		soldierStandingRegion = soldierAtkAtlas.findRegion("tile000");
		soldierShoot = Mutagen.manager.get("sound effects/enemies/soldierShooting.mp3");
		defineSoldier();
	}
	
	public void defineSoldier() {
		bdef.position.set(soldierSpawnPos);
		soldierPos = bdef.position;
		originPos = bdef.position;

		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(12 / Mutagen.PPM);
		b2body.setLinearDamping(5f);
		fdef.density = 400; //made extremely dense to prevent anything from moving it
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.ENEMY;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET | Mutagen.SHOOT_OVER;
		b2body.createFixture(fdef).setUserData("soldier"); 
	}
	public void renderSprite(SpriteBatch batch) {
		
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
		angle = angle2 * MathUtils.radDeg;
		angle = angle - 90; //makes it a full 360 degrees
		if (angle < 0) {
			angle += 360 ;
		}
		float posX = this.b2body.getPosition().x;
		float posY = this.b2body.getPosition().y;
		if (shootAnimation) {
			batch.draw(soldierAtkAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 50/ Mutagen.PPM, 1, 1, angle);
			timePassed += Gdx.graphics.getDeltaTime();
			if(soldierAtkAnimation.isAnimationFinished(timePassed)) {
				shootAnimation = false;
				timePassed = 0;
			}
		}else {
			batch.draw(soldierStandingRegion, posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1, 1, angle);
		}
		
		if (!stationary) {
			//movement
			if (findTarget) {
				//X 
				//float maxX = originPos.x + boundaryf;
				float minX = originPos.x - boundary;
				//Y
				//float maxY= originPos.y + boundaryf;
				float minY = originPos.y - boundary;

				targetPos.x = (float) (Math.random() * (boundary * 2) + minX);
				targetPos.y = (float) (Math.random() * (boundary * 2) + minY);
				tPosDifX = targetPos.x - this.b2body.getPosition().x;
				tPosDifY = targetPos.y - this.b2body.getPosition().y;
				angle3 = MathUtils.atan2(tPosDifY, tPosDifX);
				tPos.x = (float) (Math.cos(angle3) * speed);
				tPos.y = (float) (Math.sin(angle3) * speed);
				findTarget = false;
			}

			if (!findTarget) {
				if (this.b2body.getPosition().x > targetPos.x - .2 && this.b2body.getPosition().x < targetPos.x + .2 && 
						this.b2body.getPosition().y > targetPos.y - .2f && this.b2body.getPosition().y < targetPos.y +.2f) {
					findTarget = true;
					stationary = true;
				}
				this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);				
			}
		}else {
			wait +=1;
			if (wait > 45) {
				wait = 0;
				stationary = false;
			}
		}

		shooting();
	}
	public void shooting() {

		if (PlayerMode.OneP) {
			if (!PlayerOne.p1Dead) {
				shootTimer += .50;
				if (shootTimer >= 26) {
					if (Mutagen.sfxVolume != 0) {
						Long tS = soldierShoot.play(Mutagen.sfxVolume - .8f);					
					}
					shootAnimation = true;
					sB = new SoldierBullets(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle);	
					
					shootTimer = 0;
				}
			}
		}
		else {
			if (!PlayerOne.p1Dead || !PlayerTwo.p2Dead) {
				shootTimer += .50;
				if (shootTimer >= 26) {
					if (Mutagen.sfxVolume != 0) {
						Long tS = soldierShoot.play(Mutagen.sfxVolume - .8f);					
					}
					shootAnimation = true;
					sB = new SoldierBullets(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle);	
					
					shootTimer = 0;
				}
			}
		}
	}
	
}
