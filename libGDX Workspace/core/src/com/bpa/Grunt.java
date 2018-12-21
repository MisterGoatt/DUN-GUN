package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class Grunt extends Sprite implements Disposable{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public int health = 100;
	float angle2;
	private TextureAtlas gruntAtkAtlas;
	private Animation <TextureRegion> gruntAtkAnimation;
	private TextureAtlas gruntDamagedAtlas;
	private Animation <TextureRegion> gruntDamagedAnimation;
	private float timePassed = 0;
	private TextureRegion gruntStandingRegion;
	private Sound atkSwoosh;
	boolean tookDamage = false;
	private float runSpeed = 2f;
	public boolean attack = false;
	public int atkdmg = 2;
	public boolean contAtk = false;
		
		public Grunt(World world) {
			this.world = world;
			gruntAtkAtlas = Mutagen.manager.get("sprites/grunt/mutantAtkAnimation.atlas");
			gruntAtkAnimation = new Animation <TextureRegion>(1f/15f, gruntAtkAtlas.getRegions());			
			gruntStandingRegion = gruntAtkAtlas.findRegion("tile000");
			gruntDamagedAtlas = Mutagen.manager.get("sprites/grunt/gruntDamaged.atlas");
			gruntDamagedAnimation = new Animation <TextureRegion>(1f/15f, gruntDamagedAtlas.getRegions());
			atkSwoosh = Mutagen.manager.get("sound effects/gruntSwoosh.mp3");

			defineGrunt();
		}
		
		public void defineGrunt() {
			//define player body
			
			bdef.position.set(Level1.gruntPos);
			
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
			fdef.filter.categoryBits = Mutagen.GRUNT;
			fdef.filter.maskBits = -1; //collides with everything
			b2body.createFixture(fdef).setUserData("grunt");
		}
		
		public void renderSprite(SpriteBatch batch) {
			float differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
			float differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
			angle2 = MathUtils.atan2(differenceY, differenceX);
			float angle = angle2 * MathUtils.radDeg;
	        
			
			angle = angle - 90; //makes it a full 360 degrees
		    if (angle < 0) {
		    	angle += 360 ;
		    }
			float posX = this.b2body.getPosition().x;
			float posY = this.b2body.getPosition().y;
			float gposX = (float) (Math.cos(angle2)) * runSpeed;
			float gposY = (float) (Math.sin(angle2)) * runSpeed;
			
			if (!tookDamage && !attack && !contAtk) {
				batch.draw(gruntStandingRegion, posX - .17f, posY - .13f, 20 / Mutagen.PPM, 10 / Mutagen.PPM, 40 / Mutagen.PPM, 32 / Mutagen.PPM, 1, 1, angle);
			}
			else if (contAtk) {
				batch.draw(gruntAtkAnimation.getKeyFrame(timePassed), posX - .17f, posY - .13f, 20 / Mutagen.PPM, 10 / Mutagen.PPM, 40 / Mutagen.PPM, 32 / Mutagen.PPM, 1, 1, angle);
				timePassed += Gdx.graphics.getDeltaTime();

				if(gruntAtkAnimation.isAnimationFinished(timePassed)) {
					long aSId = atkSwoosh.play(1f);

					timePassed = 0;
					PlayerOne.player1HP -= atkdmg;
					System.out.println(PlayerOne.player1HP);

				}
				
			}
			
			else {
				batch.draw(gruntDamagedAnimation.getKeyFrame(timePassed), posX - .20f, posY -.27f, 20 / Mutagen.PPM, 25 / Mutagen.PPM, 40 / Mutagen.PPM, 50 / Mutagen.PPM, 1.18f, 1.18f, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if(gruntDamagedAnimation.isAnimationFinished(timePassed)) {
					timePassed = 0;
					tookDamage = false;
				}
			}
			
			if (!PlayerOne.p1Dead) {
				b2body.applyLinearImpulse(gposX, gposY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
		        //this.b2body.setLinearVelocity(gposX, gposY);
	
				b2body.setTransform(this.b2body.getPosition().x, this.b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
			}

			
			
		}


		@Override
		public void dispose() {
		}


}
