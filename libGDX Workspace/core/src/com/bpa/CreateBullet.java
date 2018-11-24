package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class CreateBullet extends Sprite {
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private static Vector2 bulletTarget;
	private float targetX;
	private float targetY;

	
	
	
	public CreateBullet(World world) {
		this.world = world;

		defineBullet();
		
	}
	
	public void defineBullet() {
		
		

		
		
		bdef.position.set(PlayerOne.p1PosX, PlayerOne.p1PosY + 50 / DunGun.PPM);

	
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.isBullet();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(4 / DunGun.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		//b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, PlayerOne.angle2); //sets the position of the body to the position of the body and implements rotation

		float differenceX = Level1.mouse_position.x - b2body.getPosition().x;
		float differenceY = Level1.mouse_position.y - b2body.getPosition().y;
		float angle = MathUtils.atan2(differenceY, differenceX);
		//float angle = MathUtils.atan2(Level1.mouse_position.y - b2body.getPosition().y, Level1.mouse_position.x - b2body.getPosition().x) * MathUtils.radDeg; //find the distance between mouse and player


		//float posX = (float) (Math.cos(90)) ;

		float posX = (float) (Math.cos(angle));
		float posY = (float) (Math.sin(angle)) ;
		
		//b2body.setLinearVelocity(posX, posY);
		b2body.applyLinearImpulse(posX, posY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
		//b2body.applyLinearImpulse(new Vector2(1 , 1), new Vector2(Level1.mouse_position.x, Level1.mouse_position.y), true);

		System.out.println("mouse X " + Level1.mouse_position.x);	
		System.out.println("mouse Y " + Level1.mouse_position.y);
		
		System.out.println("player X " + b2body.getPosition().x);
		System.out.println("player Y " + b2body.getPosition().y);

		//System.out.println("differenceX " + differenceX);
		//System.out.println("differenceY " + differenceY);
		System.out.println("angle " + angle);

		System.out.println("posX " + posX);
		System.out.println("posY " + posY);

		System.out.println("\n");
		
		
		
		
		
	}
	
	public void update() {
		//System.out.println("mouse X " + Level1.mouse_position.x);	
		//System.out.println("mouse Y " + Level1.mouse_position.y);

	}
	
}
