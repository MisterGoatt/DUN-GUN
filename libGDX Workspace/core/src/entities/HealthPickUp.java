package entities;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import screens.Mutagen;

public class HealthPickUp {

	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private Texture hpImg;
	public static Vector2 hpSpawnPos = new Vector2(0, 0);
	private Vector2 hpPos = new Vector2(0, 0);

	
	private float hpValue;
	public static Array<HealthPickUp> hpPickUp= new Array<HealthPickUp>();
	
	public HealthPickUp(World world) {
		this.world = world;
		hpImg = Mutagen.manager.get("heart.png");
		defineHP();
	}
	
	public void defineHP() {
		bdef.position.set(hpSpawnPos);
		hpPos = bdef.position;
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(10 / Mutagen.PPM);
		shape.setPosition(new Vector2(15, 10).scl(1/Mutagen.PPM));
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.HP_PICKUP;
		fdef.filter.maskBits = Mutagen.PLAYER;
		b2body.createFixture(fdef).setUserData("hpPickUp"); 
	}
	public void renderSprite(SpriteBatch batch) {
		batch.draw(hpImg, this.b2body.getPosition().x, this.b2body.getPosition().y, 32 / Mutagen.PPM, 32 / Mutagen.PPM);
	}
}
