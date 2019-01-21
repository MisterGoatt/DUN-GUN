package entities;

import com.badlogic.gdx.Gdx;
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
import screens.PlayerMode;

public class Ivanov {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public static int health = 10000, target = 0;
	public static boolean contAtk = false;
	public static Vector2 ivanovPos = new Vector2(0, 0);
	public static Vector2 ivanovSpawnPos = new Vector2(0,0);
	private Vector2 tPos = new Vector2(0, 0);
	private float shootTimer = 30, timePassed = 0, angle, angle3, differenceX, differenceY, boundary = .7f, speed = 1, tPosDifX, 
			tPosDifY, wait, oldX, oldY, player1Dif, player2Dif;
	private boolean shootAnimation = false, tooFarAway = false, startAnimation = true;
	private TextureRegion ivanovStandingRegion;
	private TextureAtlas ivanovTransAtlas;
	private Animation <TextureRegion> ivanovTransAnimation;
	public static Array<CreateBullet> ivanov = new Array<CreateBullet>();

	public Ivanov(World world) {
		this.world = world;
		ivanovTransAtlas = Mutagen.manager.get("sprites/ivanov/ivanovTransformation.atlas");
		ivanovTransAnimation = new Animation <TextureRegion>(1f/15f, ivanovTransAtlas.getRegions());
		ivanovStandingRegion = ivanovTransAtlas.findRegion("tile078");
		defineIvanov();
	}
	
	public void defineIvanov() {
		bdef.position.set(ivanovSpawnPos);
		health = 10000;
		contAtk = false;
		ivanovPos = bdef.position;

		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(18 / Mutagen.PPM);
		b2body.setLinearDamping(5f);
		fdef.density = 800; //made extremely dense to prevent anything from moving it
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.ENEMY;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET | Mutagen.SHOOT_OVER;
		b2body.createFixture(fdef).setUserData("ivanov"); 
	}
	public void renderSprite(SpriteBatch batch) {

		//Selects which player to attack depending on which player is closer by using the pythagorean theorem
		if (this.health > 0){
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
					//checks to see how far away the ivanov is from the targeted player to know whether or not to get closer before shooting
					if (player1Dif > 4.7) {
						tooFarAway = true;
					}else {
						tooFarAway = false;
					}
					
				}else {
					//fire at player 2
					differenceX = PlayerTwo.p2PosX - b2body.getPosition().x;
					differenceY = PlayerTwo.p2PosY - b2body.getPosition().y;
					//checks to see how far away the ivanov is from the targeted player to know whether or not to get closer before shooting
					if (player2Dif > 4.7) {
						tooFarAway = true;
					}else {
						tooFarAway = false;
					}
				}
				if (tooFarAway) {
					//move towards the player to get closer
					angle3 = MathUtils.atan2(differenceY, differenceX);
					tPos.x = (float) (Math.cos(angle3) * speed);
					tPos.y = (float) (Math.sin(angle3) * speed);
					this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);	
				}
			}else {
				//single player
				differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
				differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
				player1Dif = (float) Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
				//checks to see how far away the ivanov is from the targeted player to know whether or not to get closer before shooting
				if (player1Dif > 5) {
					tooFarAway = true;
				}else {
					tooFarAway = false;
				}
				if (tooFarAway) {
					//move towards the player to get closer
					angle3 = MathUtils.atan2(differenceY, differenceX);
					tPos.x = (float) (Math.cos(angle3) * speed);
					tPos.y = (float) (Math.sin(angle3) * speed);
					
					this.b2body.applyLinearImpulse(tPos.x, tPos.y, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);				
				}
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
//			if (shootAnimation) {
//				batch.draw(ivanovAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM, 16 / Mutagen.PPM, 40 / Mutagen.PPM, 50/ Mutagen.PPM, 1, 1, angle);
//				timePassed += Gdx.graphics.getDeltaTime();
//				if(ivanovAtkAnimation.isAnimationFinished(timePassed)) {
//					shootAnimation = false;
//					timePassed = 0;
//				}
//			}
			if(startAnimation) {
				Level3.cin = true;
				batch.draw(ivanovTransAnimation.getKeyFrame(timePassed), posX - .37f, posY - .37f,  40 / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80/ Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if(ivanovTransAnimation.isAnimationFinished(timePassed)) {
					startAnimation = false;
					timePassed = 0;
					Level3.cin = false;

				}
			}
			else {
				batch.draw(ivanovStandingRegion, posX - .37f, posY - .37f, 40  / Mutagen.PPM, 40 / Mutagen.PPM, 80 / Mutagen.PPM, 80 / Mutagen.PPM, 2, 2, angle);					
			}
			
			oldX = this.b2body.getPosition().x;
			oldY = this.b2body.getPosition().y;
		}

	}
}
