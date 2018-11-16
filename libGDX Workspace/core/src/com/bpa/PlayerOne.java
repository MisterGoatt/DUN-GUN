package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerOne extends Sprite{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private TextureRegion playerStand;



	public PlayerOne(World world, Level1 screen) {
		super(screen.gettextureAtlas().findRegion("TDPlayer"));
		this.world = world;
		definePlayer();
		playerStand = new TextureRegion(getTexture(), 0, 0, 32, 32);
		this.setOrigin(16 / DunGun.PPM, DunGun.PPM);
		setBounds(0, 0, 32 / DunGun.PPM, 32 / DunGun.PPM);
		setRegion(playerStand); //sets texture applied

    }

	public void definePlayer() {
		//define player body
		BodyDef bdef = new BodyDef();
		bdef.position.set(750 / DunGun.PPM, 400 / DunGun.PPM);
		
		bdef.type = BodyDef.BodyType.DynamicBody;
		//create body in the world
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(12 / DunGun.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
	
	public void handleInput(float delta) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + (5 / DunGun.PPM));

		this.b2body.setLinearVelocity(0, 0);

		if (Gdx.input.isKeyPressed(Input.Keys.W))
			this.b2body.applyLinearImpulse(new Vector2(0, 3f), this.b2body.getWorldCenter(), true); //third parameter wakes body up
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			this.b2body.applyLinearImpulse(new Vector2(3f, 0), this.b2body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			this.b2body.applyLinearImpulse(new Vector2(0, -3f), this.b2body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			this.b2body.applyLinearImpulse(new Vector2(-3f, 0), this.b2body.getWorldCenter(), true);
		
		
		
		float mouseX = Level1.mouse_position.x; //grabs cam.unproject x vector value
		float mouseY = Level1.mouse_position.y; //grabs cam.unproject y vector value
		
		float angle = MathUtils.atan2(mouseY - getY(), mouseX - getX()) * MathUtils.radDeg;

        angle = angle - 90;
	    if (angle < 0) {
	    	angle += 360 ;
	    }
		angle = angle / DunGun.PPM;
	    System.out.println(angle);
       
        this.setRotation(angle);
	}

}

