package com.bpa;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
	public int health = 300;
	float angle2;
	private TextureAtlas gruntAtkAnimation;
	private TextureAtlas gruntDamagedAtlas;
	private Animation <TextureRegion> gruntDamagedAnimation;
	private float timePassed = 0;
	private TextureRegion gruntStandingRegion;
	static boolean tookDamage = false;
		
		public Grunt(World world) {
			this.world = world;
			gruntAtkAnimation = DunGun.manager.get("sprites/grunt/mutantAtkAnimation.atlas");
			gruntStandingRegion = gruntAtkAnimation.findRegion("tile000");
			gruntDamagedAtlas = DunGun.manager.get("sprites/grunt/gruntDamaged.atlas");
			gruntDamagedAnimation = new Animation <TextureRegion>(1f/15f, gruntDamagedAtlas.getRegions());			
			defineGrunt();
			System.out.println(health);
		}
		
		public void defineGrunt() {
			//define player body
			
			float yRan = (int)(Math.random() * 	4 + 3.5f);
			
			bdef.position.set(450 / DunGun.PPM, yRan);
			
			bdef.type = BodyDef.BodyType.DynamicBody;
			//create body in the world
			b2body = world.createBody(bdef);
			b2body.setLinearDamping(5f);
			b2body.setUserData(this);
			FixtureDef fdef = new FixtureDef();
			CircleShape shape = new CircleShape();
			shape.setRadius(10 / DunGun.PPM);
			fdef.density = 500;
		
			fdef.shape = shape;
			fdef.filter.categoryBits = DunGun.GRUNT;
			fdef.filter.maskBits = DunGun.WALL | DunGun.BULLET;
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
			b2body.setTransform(this.b2body.getPosition().x, this.b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
			float posX = this.b2body.getPosition().x;
			float posY = this.b2body.getPosition().y;
			if (!tookDamage) {
				batch.draw(gruntStandingRegion, posX - .17f, posY - .13f, 20 / DunGun.PPM, 10 / DunGun.PPM, 40 / DunGun.PPM, 32 / DunGun.PPM, 1, 1, angle);
			}else {
				batch.draw(gruntDamagedAnimation.getKeyFrame(timePassed), posX - .20f, posY -.27f, 20 / DunGun.PPM, 25 / DunGun.PPM, 40 / DunGun.PPM, 50 / DunGun.PPM, 1.18f, 1.18f, angle);
				timePassed += Gdx.graphics.getDeltaTime();
				if(gruntDamagedAnimation.isAnimationFinished(timePassed)) {
					timePassed = 0;
					tookDamage = false;
				}
			}

			
			
		}


		@Override
		public void dispose() {
			gruntAtkAnimation.dispose();
		}


}
