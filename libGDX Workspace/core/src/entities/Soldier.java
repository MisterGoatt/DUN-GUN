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

import screens.Mutagen;

public class Soldier {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 150;
	public static int atkDmg = 15;
	private TextureAtlas soldierAtkAtlas;
	private Animation <TextureRegion> soldierAtkAnimation;
	private TextureRegion soldierStandingRegion;
	public static Vector2 soldierPos = new Vector2(0, 0);
	private float shootTimer = 20, timePassed = 0, angle, angle2;
	public static Vector2 soldierSpawnPos = new Vector2(0,0);
	private boolean shootAnimation = false;
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
		bdef.position.set(PlayerOne.player1SpawnPos.x + .5f, PlayerOne.player1SpawnPos.y);
		soldierPos = bdef.position;
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
		fdef.filter.categoryBits = Mutagen.SOLDIER;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.PLAYER_TWO | Mutagen.WALL | Mutagen.BULLET | Mutagen.FLAYER | Mutagen.GRUNT | 
				Mutagen.SCIENTIST | Mutagen.SOLDIER | Mutagen.TURRET | Mutagen.SHOOT_OVER;
		b2body.createFixture(fdef).setUserData("soldier"); 
	}
	public void renderSprite(SpriteBatch batch) {
		float differenceX = PlayerOne.p1PosX - this.b2body.getPosition().x;
		float differenceY = PlayerOne.p1PosY - this.b2body.getPosition().y;
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
		shooting();
	}
	public void shooting() {
		if (!PlayerOne.p1Dead) {
			shootTimer += .50;
			if (shootTimer >= 18) {
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
