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

public class Turret {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 300;
	public static int atkDmg = 25;
	private TextureAtlas turretAtkAtlas;
	private Animation <TextureRegion> turretAtkAnimation;
	private TextureRegion turretStandingRegion;
	public static Vector2 turretPos = new Vector2(0, 0);
	private float shootTimer = 20, timePassed = 0;
	public static Vector2 turretSpawnPos = new Vector2(0,0);
	private boolean shootAnimation = false;
	private Sound turretShoot;
	TurretBullets tB;
	public static Array<Turret> turrets = new Array<Turret>();

	public Turret(World world) {
		this.world = world;
		turretAtkAtlas = Mutagen.manager.get("sprites/turret/turretAtkAnimation.atlas");
		turretAtkAnimation = new Animation <TextureRegion>(1f/15f, turretAtkAtlas.getRegions());
		turretStandingRegion = turretAtkAtlas.findRegion("tile000");
		turretShoot = Mutagen.manager.get("sound effects/turretAtk.mp3");
		defineTurret();
	}

	public void defineTurret() {
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
		fdef.filter.categoryBits = Mutagen.TURRET;
		fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.WALL | Mutagen.BULLET; //collides with everything
		b2body.createFixture(fdef).setUserData("turret"); 
	}

	public void renderSprite(SpriteBatch batch) {
		float differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
		float differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
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
	}

	public void shooting(float angle2) {
		if (!PlayerOne.p1Dead) {
			shootTimer += .50;
			if (shootTimer >= 20) {
				if (Mutagen.sfxVolume != 0) {
					Long tS = turretShoot.play(Mutagen.sfxVolume - .8f);					
				}
				shootAnimation = true;
				for (int i = 0; i < 2; i++) {
					tB = new TurretBullets(world, this.b2body.getPosition().x, this.b2body.getPosition().y, angle2);	
				}
				shootTimer = 0;
			}
		}
	}
}
