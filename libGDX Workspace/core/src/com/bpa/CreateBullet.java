package com.bpa;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class CreateBullet extends Sprite implements Disposable{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public String id = "BULLET";
	Sprite sprite;
	public static float angle;
	private float speed = 2;
	private float posX;
	private float posY;
	private float timePassed = 0;
	private float angle2;
	
	private TextureAtlas laserTextureAtlas;
	private Animation <TextureRegion> laserAnimation;
	private TextureAtlas pelletTextureAtlas;
	private Animation <TextureRegion> pelletAnimation;
	private TextureAtlas bulletTextureAtlas;
	private Animation <TextureRegion> bulletAnimation;

	private TextureRegion pelletTextureRegion;


	//static ArrayList<Integer> laserDestroyManager;
	
	//public static ArrayList gruntList;
	
	public CreateBullet(World world) {
		this.world = world;
		

		
		defineBullet();
		laserTextureAtlas = DunGun.manager.get("sprites/player1/laserBlastAnimation.atlas", TextureAtlas.class);
		laserAnimation = new Animation <TextureRegion>(1f/15f, laserTextureAtlas.getRegions());
		laserTextureAtlas = DunGun.manager.get("sprites/player1/pellet.atlas", TextureAtlas.class);
		pelletTextureAtlas = DunGun.manager.get("sprites/player1/pellet.atlas", TextureAtlas.class);
		pelletAnimation = new Animation <TextureRegion>(1f / 15f, pelletTextureAtlas.getRegions());
		pelletTextureRegion = laserTextureAtlas.findRegion("tile000");
		bulletTextureAtlas = DunGun.manager.get("sprites/player1/bulletAnimation.atlas", TextureAtlas.class);
		bulletAnimation = new Animation <TextureRegion>(1f / 15f, bulletTextureAtlas.getRegions());
		}
	
	public void defineBullet() {
		bdef.position.set(PlayerOne.p1PosX, PlayerOne.p1PosY);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.setUserData(this);
		
		FixtureDef fdef = new FixtureDef();
		//polygon shape for the laser
		if (GunSelectionScreen.weaponSelected == "laser") {
			PolygonShape shape = new PolygonShape();
			Vector2[] vertice = new Vector2[4];
			vertice[0] = new Vector2(2, 50).scl(1/DunGun.PPM);
			vertice[1] = new Vector2(8, 50).scl(1/DunGun.PPM);
			vertice[2] = new Vector2(2, 10).scl(1/DunGun.PPM);
			vertice[3] = new Vector2(8, 10).scl(1/DunGun.PPM);
			shape.set(vertice);
			fdef.shape = shape;
			//shape.dispose();
		}
		//polygon shape for battle axe
		else if(GunSelectionScreen.weaponSelected == "battle axe") {
			PolygonShape shape = new PolygonShape();
			Vector2[] vertice = new Vector2[4];
			vertice[0] = new Vector2(-15, 30).scl(1/DunGun.PPM);
			vertice[1] = new Vector2(15, 30).scl(1/DunGun.PPM);
			vertice[2] = new Vector2(-15, 10).scl(1/DunGun.PPM);
			vertice[3] = new Vector2(15, 10).scl(1/DunGun.PPM);
			shape.set(vertice);
			fdef.shape = shape;
		}
		else {
			CircleShape shape = new CircleShape();
			fdef.shape = shape;
			if (GunSelectionScreen.weaponSelected == "shotgun") {
				shape.setRadius(2 / DunGun.PPM);
				}
			else {
				shape.setPosition(new Vector2(5, 7).scl(1/DunGun.PPM));
				shape.setRadius(4 / DunGun.PPM);
			}
			//shape.dispose();
		}
		//Sets size of the physics bodies depending on the type of gun
		fdef.filter.categoryBits = DunGun.BULLET; //identifies the category bit is
		fdef.filter.maskBits = DunGun.WALL | DunGun.GRUNT; // what masking bit the category bit collides with
		b2body.createFixture(fdef).setUserData("bullets");
		//b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, PlayerOne.angle2); //sets the position of the body to the position of the body and implements rotation
		//fdef.shape.dispose();
		float differenceX = Level1.mousePosition.x - b2body.getPosition().x;
		float differenceY = Level1.mousePosition.y - b2body.getPosition().y;
		angle = MathUtils.atan2(differenceY, differenceX);
		angle2 = MathUtils.atan2( differenceY, differenceX)* MathUtils.radDeg; //find the distance between mouse and player

		//float posX = (float) (Math.cos(90)) ;
		if (GunSelectionScreen.weaponSelected == "shotgun") {
			float speedVary = (int)(Math.random() * 10 + 5);
			//float speedVary = .5f;
			float angleVary = (int)(Math.random() * 40 - 19);
			angleVary = angleVary / 100;
			angle = angle + angleVary;
			
			posX = (float) (Math.cos(angle)) * speedVary;
			posY = (float) (Math.sin(angle)) * speedVary;
		}

		else {
			if (GunSelectionScreen.weaponSelected == "laser"){
				speed = 2f;
			}

			posX = (float) (Math.cos(angle)) * speed;
			posY = (float) (Math.sin(angle)) * speed;
		    angle = angle - 1.57f ;
			b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angle); //sets the position of the body to the position of the body and implements rotation
		}
		if (GunSelectionScreen.weaponSelected != "battle axe") {
			b2body.applyLinearImpulse(posX, posY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
		}
	}
	//Render all of the textures for bullets and lasers
	public void renderSprite(SpriteBatch batch) {
		float differenceX = Level1.mousePosition.x - b2body.getPosition().x;
		float differenceY = Level1.mousePosition.y - b2body.getPosition().y;
		angle = MathUtils.atan2(differenceY, differenceX);

		if (GunSelectionScreen.weaponSelected == "laser") {
			batch.draw(laserAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 10 / DunGun.PPM, 45 / DunGun.PPM, 1, 1, angle2 - 90);	
			timePassed += Gdx.graphics.getDeltaTime();
		}else if (GunSelectionScreen.weaponSelected == "shotgun") {
			batch.draw(pelletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 9 / DunGun.PPM, 9 / DunGun.PPM, 1, 1, angle2 - 90);	
			timePassed += Gdx.graphics.getDeltaTime();
		}
		//all the gun powder weapons
		else if (GunSelectionScreen.weaponSelected != "shotgun" && GunSelectionScreen.weaponSelected != "laser" && GunSelectionScreen.weaponSelected != "battle axe" ) {
			batch.draw(bulletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 5 / DunGun.PPM, 20 / DunGun.PPM, 1, 1, angle2 - 90);	
			timePassed += Gdx.graphics.getDeltaTime();
		}
		else if (GunSelectionScreen.weaponSelected == "battle axe"){
			b2body.setTransform(PlayerOne.p1PosX, PlayerOne.p1PosY, angle - 1.57f); //sets the position of the body to the position of the body and implements rotation

		}
	}
	

	
	@Override
	public void dispose() {
		laserTextureAtlas.dispose();
		pelletTextureAtlas.dispose();
	}
}
