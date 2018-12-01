package com.bpa;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Grunt extends Sprite implements Disposable{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public static Array<Grunt> grunt;
	public int health = 100;
	
	
	float angle2;
		
		public Grunt(World world) {
			this.world = world;
			defineGrunt();
		}

		
		public void defineGrunt() {
			//define player body
			grunt = new Array<Grunt>();

			bdef.position.set(450 / DunGun.PPM, 400 / DunGun.PPM);
			
			bdef.type = BodyDef.BodyType.DynamicBody;
			//create body in the world
			b2body = world.createBody(bdef);
			
			FixtureDef fdef = new FixtureDef();
			CircleShape shape = new CircleShape();
			shape.setRadius(10 / DunGun.PPM);
			
			fdef.shape = shape;
			fdef.filter.categoryBits = DunGun.GRUNT;
			fdef.filter.maskBits = DunGun.WALL | DunGun.BULLET;
			b2body.createFixture(fdef).setUserData("grunt");;
			
			shape.dispose();
		}
		
		public void update() {
			float differenceX = PlayerOne.p1PosX - b2body.getPosition().x;
			float differenceY = PlayerOne.p1PosY - b2body.getPosition().y;
			angle2 = MathUtils.atan2(differenceY, differenceX);
			b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
		}
		
		public void damage() {
			
			if (GunSelectionScreen.weaponSelected == "rifle") {
				health =  health - PlayerOne.rifleDamage; 
				
			}
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}


}
