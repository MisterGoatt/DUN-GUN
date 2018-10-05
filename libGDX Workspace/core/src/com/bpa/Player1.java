package com.bpa;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player1 extends Sprite{
	public World world;
	public Body b2body;
	
	
	public Player1(World world) {
		this.world = world;
		definePlayer1();
	}
	
	public void definePlayer1() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(750 / DunGun.PPM, 300 / DunGun.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(10 / DunGun.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
}
