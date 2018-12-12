package com.bpa;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2DWorldCreator {
	public String id = "WALL";
	
	public B2DWorldCreator(World world, TiledMap map) {
		BodyDef bdef = new BodyDef(); //defines what a body consists of
		PolygonShape shape = new PolygonShape(); //shape of the fixture
		FixtureDef fdef = new FixtureDef(); //before we create a fixture we need to define what that fixture consists of
		Body body; //creates a body
		
		//below is creating a wall body and fixture
		//going to layer 2 of Tiled and getting rectangle objects
		for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / DunGun.PPM, (rect.getY() + rect.getHeight() / 2)/ DunGun.PPM ) ; //center of the rectangle
			
			body = world.createBody(bdef);//adds body to the 2d world
			shape.setAsBox(rect.getWidth() / 2 / DunGun.PPM, rect.getHeight() / 2 / DunGun.PPM);//defines polygon shape
			fdef.shape = shape; //sets the polygon shape as a shape (?)
			fdef.filter.categoryBits = DunGun.WALL;
			fdef.filter.maskBits = -1;
			body.createFixture(fdef).setUserData("walls");; //adds fixture to body

		}
	}
}
