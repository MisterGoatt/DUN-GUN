package com.bpa;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player1 extends Sprite{
	public World world;
	public Body b2body;
	private TextureRegion player1Stand;
	
	
	public Player1(World world, Level1 screen) {
		super(screen.getAtlas().findRegion("p1")); //name of sprite png 
		this.world = world;
		definePlayer1();
		player1Stand = new TextureRegion(getTexture(), 0, 0, 32, 64); //dimensions of p1 sprite texture: x, y, width, height
		setBounds(0, 0, 32 / DunGun.PPM, 64 / DunGun.PPM);
		setRegion(player1Stand);
		
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
	}
	    
	
	public void definePlayer1() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(1400 / DunGun.PPM, 400 / DunGun.PPM); //sets sprite position on screen
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(12 / DunGun.PPM); //sets size of circle on the sprite
		

		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
}
