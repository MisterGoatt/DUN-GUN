package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class PlayerOne extends Sprite{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private TextureRegion playerStand;
	private BodyDef bdef = new BodyDef();
	TextureAtlas textureAtlas;
	Sprite sprite;
	TextureRegion textureRegion;
	

	public PlayerOne(World world) {
		this.world = world;
		definePlayer();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/TDPlayer.atlas"));
		textureRegion = textureAtlas.findRegion("TDPlayer");
		sprite =new Sprite(new Texture("sprites/TDPlayer.png"));
		sprite.setOrigin((sprite.getWidth() / 2) / DunGun.PPM, (float) ((sprite.getHeight() / 2) / DunGun.PPM - .08));

    }

	
	public void definePlayer() {
		//define player body
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
	
	public void renderSprite(SpriteBatch batch) {
		float posX = b2body.getPosition().x;
		float posY = b2body.getPosition().y;
		float posX2 = (float) (posX - .14);
		float posY2 = (float) (posY - .1);
		sprite.setSize(32 / DunGun.PPM, 32 / DunGun.PPM);
		sprite.setPosition(posX2, posY2);
		float mouseX = Level1.mouse_position.x; //grabs cam.unproject x vector value
		float mouseY = Level1.mouse_position.y; //grabs cam.unproject y vector value
		
		float angle = MathUtils.atan2(mouseY - getY(), mouseX - getX()) * MathUtils.radDeg; //find the distance between mouse and player

        angle = angle - 90; //makes it a full 360 degrees
	    if (angle < 0) {
	    	angle += 360 ;
	    }
	    float angle2 = MathUtils.atan2(mouseY - getY(), mouseX - getX()); //get distance between mouse and player in radians
	    b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
		sprite.setRotation(angle); //rotates sprite
		sprite.draw(batch); //draws sprite
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
	}

}

