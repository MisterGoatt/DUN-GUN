package collisions;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import BackEnd.LogFileHandler;
import BackEnd.Mutagen;
import entities.Soldier;

public class B2DWorldCreator {
	public String id = "WALL";
	LogFileHandler lfh = new LogFileHandler();

	public B2DWorldCreator(World world, TiledMap map) {
		try {
			BodyDef bdef = new BodyDef(); // defines what a body consists of
			PolygonShape shape = new PolygonShape(); // shape of the fixture
			FixtureDef fdef = new FixtureDef(); // before we create a fixture we need to define what that fixture
												// consists of
			Body body; // creates a body

			// below is creating a wall body and fixture
			// going to layer 2 of Tiled and getting rectangle objects
			for (MapObject object : map.getLayers().get("Wall").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mutagen.PPM,
						(rect.getY() + rect.getHeight() / 2) / Mutagen.PPM); // center of the rectangle

				body = world.createBody(bdef);// adds body to the 2d world
				shape.setAsBox(rect.getWidth() / 2 / Mutagen.PPM, rect.getHeight() / 2 / Mutagen.PPM);// defines polygon
																										// shape
				fdef.shape = shape; // sets the polygon shape as a shape (?)
				fdef.filter.categoryBits = Mutagen.WALL;
				fdef.filter.maskBits = -1;
				body.createFixture(fdef).setUserData("walls");
				; // adds fixture to body

			}

			for (MapObject object : map.getLayers().get("Shoot Over").getObjects()
					.getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mutagen.PPM,
						(rect.getY() + rect.getHeight() / 2) / Mutagen.PPM); // center of the rectangle
				body = world.createBody(bdef);// adds body to the 2d world
				shape.setAsBox(rect.getWidth() / 2 / Mutagen.PPM, rect.getHeight() / 2 / Mutagen.PPM);// defines polygon
																										// shape
				fdef.shape = shape; // sets the polygon shape as a shape (?)
				fdef.filter.categoryBits = Mutagen.SHOOT_OVER;
				fdef.filter.maskBits = Mutagen.PLAYER | Mutagen.ENEMY;
				body.createFixture(fdef).setUserData("shoot over");
				; // adds fixture to body

			}
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}

	}
}
