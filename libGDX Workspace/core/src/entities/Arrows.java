package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import BackEnd.Mutagen;

public class Arrows extends Sprite implements Disposable{

	public World world;
	public Body b2body;
	private BodyDef bdef = new BodyDef();
	private Animation <TextureRegion> arrowAnimation;
	private TextureAtlas arrowAtlas;
	public static Vector2 textPos = new Vector2(0,0);
	private float timePassed = 0, arrowAng;
	public static Array<Arrows> arrows = new Array<Arrows>();
	public static Vector2 arrowPos = new Vector2(0,0);
	public static float arrowAngle = 0;
	
	
	public Arrows(World world) {
		this.world = world;
		arrowAtlas = Mutagen.manager.get("sprites/arrowAnimation.atlas");
		arrowAnimation = new Animation <TextureRegion>(1f/15f, arrowAtlas.getRegions());	
		arrowAng = arrowAngle;
		
		defineArrow();

	}
	
	
	public void defineArrow() {
		bdef.position.set(arrowPos);
		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		b2body.setLinearDamping(5f);
		b2body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(10 / Mutagen.PPM);
		fdef.density = 400;
		fdef.shape = shape;
		fdef.filter.categoryBits = Mutagen.ARROWS;
		fdef.filter.maskBits = 0; //collides with everything
		b2body.createFixture(fdef);
	}
	
	
	
	public void renderSprite(SpriteBatch batch) {
		float posX = this.b2body.getPosition().x;
		float posY = this.b2body.getPosition().y;
		batch.draw(arrowAnimation.getKeyFrame(timePassed, true), posX - .17f, posY - .13f, 21 / Mutagen.PPM, 32 / Mutagen.PPM, 42 / Mutagen.PPM, 65 / Mutagen.PPM, 1, 1, arrowAng);
		timePassed += Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
