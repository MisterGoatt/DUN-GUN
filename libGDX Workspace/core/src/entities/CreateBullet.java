package entities;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Disposable;

import BackEnd.Mutagen;
import levels.Level1;
import screens.GunSelectionScreen;
import screens.PlayerMode;

public class CreateBullet{
	public World world; // world player will live in
	public Body b2body; //creates body for player
	private BodyDef bdef = new BodyDef();
	public String id = "BULLET";
	private float speed = 10, posX, posY, timePassed = 0, angleD, angleR;
	private int playerID;
	private Animation <TextureRegion> laserAnimation, pelletAnimation, bulletAnimation;
	private TextureAtlas pelletTextureAtlas, bulletTextureAtlas, laserTextureAtlas;



	public CreateBullet(World world, int ID) {
		this.world = world;
		laserTextureAtlas = Mutagen.manager.get("sprites/player1/laserBlastAnimation.atlas", TextureAtlas.class);
		laserAnimation = new Animation <TextureRegion>(1f/15f, laserTextureAtlas.getRegions());
		laserTextureAtlas = Mutagen.manager.get("sprites/player1/pellet.atlas", TextureAtlas.class);
		pelletTextureAtlas = Mutagen.manager.get("sprites/player1/pellet.atlas", TextureAtlas.class);
		pelletAnimation = new Animation <TextureRegion>(1f / 15f, pelletTextureAtlas.getRegions());
		bulletTextureAtlas = Mutagen.manager.get("sprites/player1/bulletAnimation.atlas", TextureAtlas.class);
		bulletAnimation = new Animation <TextureRegion>(1f / 15f, bulletTextureAtlas.getRegions());
		playerID = ID;
		defineBullet();
	}

	public void defineBullet() {
		
		if (playerID == 1) {
			bdef.position.set(PlayerOne.p1PosX, PlayerOne.p1PosY);

		}
		else if (playerID == 2) {
			bdef.position.set(PlayerTwo.p2PosX, PlayerTwo.p2PosY);
		}
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.setUserData(this);

		FixtureDef fdef = new FixtureDef();
		//polygon shape for the laser
		if (playerID == 1) {
			if (GunSelectionScreen.p1WeaponSelected == "laser") {
				PolygonShape shape = new PolygonShape();
				Vector2[] vertice = new Vector2[4];
				vertice[0] = new Vector2(2, 50).scl(1/Mutagen.PPM);
				vertice[1] = new Vector2(8, 50).scl(1/Mutagen.PPM);
				vertice[2] = new Vector2(2, 10).scl(1/Mutagen.PPM);
				vertice[3] = new Vector2(8, 10).scl(1/Mutagen.PPM);
				shape.set(vertice);
				fdef.shape = shape;
				//shape.dispose();

			}
			//polygon shape for battle axe
			else if(GunSelectionScreen.p1WeaponSelected == "battle axe") {
				PolygonShape shape = new PolygonShape();
				Vector2[] vertice = new Vector2[4];
				vertice[0] = new Vector2(-15, 30).scl(1/Mutagen.PPM);
				vertice[1] = new Vector2(15, 30).scl(1/Mutagen.PPM);
				vertice[2] = new Vector2(-15, 10).scl(1/Mutagen.PPM);
				vertice[3] = new Vector2(15, 10).scl(1/Mutagen.PPM);
				shape.set(vertice);
				fdef.shape = shape;
			}
			else {
				CircleShape shape = new CircleShape();
				fdef.shape = shape;
				if (GunSelectionScreen.p1WeaponSelected == "shotgun") {
					shape.setPosition(new Vector2(5, 7).scl(1/Mutagen.PPM));
					shape.setRadius(2 / Mutagen.PPM);
				}
				else {
					shape.setPosition(new Vector2(5, 7).scl(1/Mutagen.PPM));
					shape.setRadius(4 / Mutagen.PPM);
				}
			}
			//Sets size of the physics bodies depending on the type of gun
			fdef.filter.categoryBits = Mutagen.BULLET; //identifies the category bit is
			fdef.filter.maskBits = Mutagen.WALL | Mutagen.ENEMY; // what masking bit the category bit collides with
			b2body.createFixture(fdef).setUserData("bullets");
			if (PlayerMode.OneP) {
				float differenceX = Level1.mousePosition.x - b2body.getPosition().x;
				float differenceY = Level1.mousePosition.y - b2body.getPosition().y;
				angleR = MathUtils.atan2(differenceY, differenceX);
				angleD = MathUtils.atan2( differenceY, differenceX)* MathUtils.radDeg; //find the angle between mouse and player in degrees
			}
			//CO-OP
			else {
				angleR = PlayerOne.angle * MathUtils.degreesToRadians + 1.5078f;
				angleD = PlayerOne.angle + 90;
			}

			if (GunSelectionScreen.p1WeaponSelected == "shotgun") {
				float speedVary = (int)(Math.random() * 10 + 5f);
				float angleVary = (int)(Math.random() * 40 - 19);
				angleVary = angleVary / Mutagen.PPM;
				angleR = angleR + angleVary;
				posX = (float) (Math.cos(angleR)) * speedVary;
				posY = (float) (Math.sin(angleR)) * speedVary;
			}

			else {
				if (GunSelectionScreen.p1WeaponSelected == "laser"){
					speed = 4f;
				}
				posX = (float) (Math.cos(angleR)) * speed;
				posY = (float) (Math.sin(angleR)) * speed;
				angleR = angleR - 1.57f ;
				b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angleR); //sets the position of the body to the position of the body and implements rotation
			}
			if (GunSelectionScreen.p1WeaponSelected != "battle axe") {
				b2body.applyLinearImpulse(posX, posY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
			}
		}
		if (!PlayerMode.OneP) {
			//SECOND PLAYER **********************************************************************
			if (playerID == 2) {

				angleR = PlayerTwo.angle * MathUtils.degreesToRadians + 1.5078f;
				angleD = PlayerTwo.angle;
				
				if (GunSelectionScreen.p2WeaponSelected == "laser") {
					PolygonShape shape = new PolygonShape();
					Vector2[] vertice = new Vector2[4];
					vertice[0] = new Vector2(2, 50).scl(1/Mutagen.PPM);
					vertice[1] = new Vector2(8, 50).scl(1/Mutagen.PPM);
					vertice[2] = new Vector2(2, 10).scl(1/Mutagen.PPM);
					vertice[3] = new Vector2(8, 10).scl(1/Mutagen.PPM);
					shape.set(vertice);
					fdef.shape = shape;
					//shape.dispose();

				}
				//polygon shape for battle axe
				else if(GunSelectionScreen.p2WeaponSelected == "battle axe") {
					PolygonShape shape = new PolygonShape();
					Vector2[] vertice = new Vector2[4];
					vertice[0] = new Vector2(-15, 30).scl(1/Mutagen.PPM);
					vertice[1] = new Vector2(15, 30).scl(1/Mutagen.PPM);
					vertice[2] = new Vector2(-15, 10).scl(1/Mutagen.PPM);
					vertice[3] = new Vector2(15, 10).scl(1/Mutagen.PPM);
					shape.set(vertice);
					fdef.shape = shape;
				}
				else {
					CircleShape shape = new CircleShape();
					fdef.shape = shape;
					if (GunSelectionScreen.p2WeaponSelected == "shotgun") {
						shape.setPosition(new Vector2(5, 7).scl(1/Mutagen.PPM));
						shape.setRadius(2 / Mutagen.PPM);
					}
					else {
						shape.setPosition(new Vector2(5, 7).scl(1/Mutagen.PPM));
						shape.setRadius(4 / Mutagen.PPM);
					}
				}
				//Sets size of the physics bodies depending on the type of gun
				fdef.filter.categoryBits = Mutagen.BULLET; //identifies the category bit is
				fdef.filter.maskBits = Mutagen.WALL | Mutagen.ENEMY; // what masking bit the category bit collides with
				b2body.createFixture(fdef).setUserData("bullets2");

				//CO-OP
				//						angleR = PlayerOne.angle * MathUtils.degreesToRadians + 1.5078f;
				//						angleD = PlayerOne.angle;

				if (GunSelectionScreen.p2WeaponSelected == "shotgun") {
					float speedVary = (int)(Math.random() * 10 + 5f);
					float angleVary = (int)(Math.random() * 40 - 20);
					angleVary = angleVary / Mutagen.PPM;
					angleR = angleR + angleVary;
					posX = (float) (Math.cos(angleR)) * speedVary;
					posY = (float) (Math.sin(angleR)) * speedVary;
				}

				else {
					if (GunSelectionScreen.p2WeaponSelected == "laser"){
						speed = 4f;
					}
					posX = (float) (Math.cos(angleR)) * speed;
					posY = (float) (Math.sin(angleR)) * speed;
					angleR = angleR - 1.57f ;
					b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, angleR); //sets the position of the body to the position of the body and implements rotation
				}
				if (GunSelectionScreen.p2WeaponSelected != "battle axe") {
					b2body.applyLinearImpulse(posX, posY, b2body.getWorldCenter().x, b2body.getWorldCenter().y, true);
				}
			}
		}
	}
	//Render all of the textures for bullets and lasers
	public void renderSprite(SpriteBatch batch) {
		if (PlayerMode.OneP) {
			float differenceX = Level1.mousePosition.x - b2body.getPosition().x;
			float differenceY = Level1.mousePosition.y - b2body.getPosition().y;
			angleR = MathUtils.atan2(differenceY, differenceX);
		}
		else if (!PlayerMode.OneP) {
			if (playerID == 1 && GunSelectionScreen.p1WeaponSelected == "battle axe") {
				angleR = PlayerOne.angle * MathUtils.degreesToRadians + 1.507f;
				angleD = PlayerOne.angle;
			}
			//else if player ID == 2 *****
		}
		if (playerID == 1) {

			switch (GunSelectionScreen.p1WeaponSelected) {
	
			case "laser":
				batch.draw(laserAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 10 / Mutagen.PPM, 45 / Mutagen.PPM, 1, 1, angleD - 90);
				timePassed += Gdx.graphics.getDeltaTime();
				break;
			case "shotgun":
				batch.draw(pelletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 9 / Mutagen.PPM, 9 / Mutagen.PPM, 1, 1, angleD - 90);
				timePassed += Gdx.graphics.getDeltaTime();
				break;
			case "battle axe":
				b2body.setTransform(PlayerOne.p1PosX, PlayerOne.p1PosY, angleR - 1.507f); //sets the position of the body to the position of the body and implements rotation
				break;
			default: 
				batch.draw(bulletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 5 / Mutagen.PPM, 20 / Mutagen.PPM, 1, 1, angleD - 90);	
				timePassed += Gdx.graphics.getDeltaTime();

				break;
			}
		}
			else if (playerID == 2){
				switch (GunSelectionScreen.p2WeaponSelected) {
				case "laser":
					batch.draw(laserAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 10 / Mutagen.PPM, 45 / Mutagen.PPM, 1, 1, angleD );
					timePassed += Gdx.graphics.getDeltaTime();
					break;
				case "shotgun":
					batch.draw(pelletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 9 / Mutagen.PPM, 9 / Mutagen.PPM, 1, 1, angleD);
					timePassed += Gdx.graphics.getDeltaTime();
					break;
				case "battle axe":
					b2body.setTransform(PlayerOne.p1PosX, PlayerOne.p1PosY, angleR); //sets the position of the body to the position of the body and implements rotation
					break;
				default: 
					batch.draw(bulletAnimation.getKeyFrame(timePassed, true), b2body.getPosition().x, b2body.getPosition().y, 0,  0, 5 / Mutagen.PPM, 20 / Mutagen.PPM, 1, 1, angleD);	
					timePassed += Gdx.graphics.getDeltaTime();
	
					break;
				}
			}
	}
}
