package BackEnd;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import entities.Grunt;
import entities.HealthPickUp;
import entities.PlayerOne;
import entities.PlayerTwo;
import entities.Scientist;
import entities.Soldier;
import entities.Turret;

public class Lvl2EntityPositions {

	private Grunt grunt;
	private Scientist scientist;
	private Turret turret;
	private Soldier soldier;
	private HealthPickUp hpPickUp;
	//Every spawn point trigger's boolean
	private boolean hpSpawn = true, room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, room6 = true, room7 = true,
			room8 = true, room9 = true, room10 = true, room11 = true, room12 = true, room13 = true, room14 = true, room15 = true, 
			room16 = true, room17 = true, room18 = true, room19 = true, room20 = true, room21 = true, room22 = true,
			room23 = true, room24 = true, room25 = true, room26 = true;
	private float p1X, p1Y, p2X, p2Y;

	public void SpawnEntities(World world, TiledMap map) {

		//SPAWN HEALTH PICK_UPS
		if (hpSpawn) {
			MapLayer hpLayer = map.getLayers().get("Health Pickups");
			for (MapObject mo : hpLayer.getObjects()) {
				HealthPickUp.hpSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
				HealthPickUp.hpSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
				hpPickUp = new HealthPickUp(world);
				HealthPickUp.hpPickUp.add(hpPickUp);
			}
			hpSpawn = false;
		}

		//		if (p1X >  && p1X < x && p1Y > y && p1Y < y) {
		//
		//		}else if (p2X > x && p2X < x && p2Y > y && p2Y < y) {
		//
		//		}

		p1X = PlayerOne.p1PosX;
		p1Y = PlayerOne.p1PosY;
		p2X = PlayerTwo.p2PosX;
		p2Y = PlayerTwo.p2PosY;

	}

}
