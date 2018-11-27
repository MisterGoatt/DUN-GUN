package com.bpa;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.utils.Disposable;

public class PlayerOne extends Sprite implements Disposable{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	private float speed = 3 ;
	private boolean running;
	private TextureAtlas textureAtlas;
	private Animation <TextureRegion> animation;
	private TextureRegion standingRegion;
	private float timePassed = 0;
	private Sound runningSound;
	private float timeSinceLastShot = 60f;
	public static float p1PosX;
	public static float p1PosY;
	public static float angle2; //get distance between mouse and player in radians
	public static float angle;
	private boolean shootAnimation = false;
	
	public PlayerOne(World world) {
		this.world = world;
		definePlayer();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/player1/playerRevolver.atlas"));
		animation = new Animation <TextureRegion>(1f/15f, textureAtlas.getRegions());
		standingRegion = textureAtlas.findRegion("tile000");
		
		//sprite.setOrigin((sprite.getWidth() / 2) / DunGun.PPM, (float) ((sprite.getHeight() / 2) / DunGun.PPM - .08));
		runningSound = Gdx.audio.newSound(Gdx.files.internal("sound effects/running.mp3"));
		
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
		fdef.filter.categoryBits = DunGun.PLAYER;
		fdef.filter.maskBits = DunGun.WALL;
		b2body.createFixture(fdef).setUserData("player");;

		shape.dispose();
	}
	
	public void renderSprite(SpriteBatch batch) {
		
		
		
		float posX = b2body.getPosition().x;
		float posY = b2body.getPosition().y;

		//sprite.setSize(32 / DunGun.PPM, 32 / DunGun.PPM);
		//sprite.setPosition(posX2, posY2);
		//float mouseX = Level1.mouse_position.x; //grabs cam.unproject x vector value
		//float mouseY = Level1.mouse_position.y; //grabs cam.unproject y vector value
		
		angle = MathUtils.atan2(Level1.mouse_position.y - getY(), Level1.mouse_position.x - getX()) * MathUtils.radDeg; //find the distance between mouse and player

        angle = angle - 90; //makes it a full 360 degrees
	    if (angle < 0) {
	    	angle += 360 ;
	    }
	    angle2 = MathUtils.atan2(Level1.mouse_position.y - getY(), Level1.mouse_position.x - getX()); //get distance between mouse and player in radians
	    b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angle2); //sets the position of the body to the position of the body and implements rotation
		//sprite.setRotation(angle); //rotates sprite
		System.out.println(timePassed);
	    if (shootAnimation) {
			batch.draw(animation.getKeyFrame(timePassed), posX - .2f, posY - .2f, 20 / DunGun.PPM, 20 / DunGun.PPM, 40 / DunGun.PPM, 50 / DunGun.PPM, 1, 1, angle);
			timePassed += Gdx.graphics.getDeltaTime();

			if(animation.isAnimationFinished(timePassed)) {
				shootAnimation = false;
				timePassed = 0;
			}
		}else {
			System.out.println("mate");
			batch.draw(standingRegion, posX - .2f, posY - .2f, 20 / DunGun.PPM, 20 / DunGun.PPM, 40 / DunGun.PPM, 50 / DunGun.PPM, 1, 1, angle);
		}
	}
	
	public void handleInput(float delta) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + (5 / DunGun.PPM));
		
		p1PosX = b2body.getPosition().x;
		p1PosY = b2body.getPosition().y;
		
		timeSinceLastShot -= 1f;
		
		this.b2body.setLinearVelocity(0, 0);

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
	        this.b2body.setLinearVelocity(0f, speed);
	    }if(Gdx.input.isKeyPressed(Input.Keys.S)){
	        this.b2body.setLinearVelocity(0f, -speed);
	    }if(Gdx.input.isKeyPressed(Input.Keys.A)){
	        this.b2body.setLinearVelocity(-speed, 0f);

	    }if(Gdx.input.isKeyPressed(Input.Keys.D)){
	        this.b2body.setLinearVelocity(speed, 0f);

	    }if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)){
	        this.b2body.setLinearVelocity(-speed, speed);

	    }if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)){
	    	this.b2body.setLinearVelocity(speed, speed);
	    }
	    if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)){
	        this.b2body.setLinearVelocity(-speed, -speed );

	    }if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)){
	        this.b2body.setLinearVelocity(speed, -speed);
	    } 
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
	    	if (timeSinceLastShot <=0) {
	    		//createBullet = new CreateBullet(world);
	    		Level1.isShooting = true;
	    		shootAnimation = true;
	    		timeSinceLastShot = 40;
	    		//timeSinceLastShot = shootDelay; //reset timeSinceLast Shot

	    	}
	    }

		if (b2body.getLinearVelocity().x > 0 || b2body.getLinearVelocity().x < 0 || b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0) {
			if (!running) {	
			runningSound.loop();
			running = true;
			}


	    }else {
	    	runningSound.stop();
	    	running = false;
	    }
	}
	

	@Override
	public void dispose() {
		runningSound.dispose();
		textureAtlas.dispose();
		
	}

}

