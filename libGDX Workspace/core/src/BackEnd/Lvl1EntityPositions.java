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

public class Lvl1EntityPositions {

	//	public Lvl1EntityPositions() {
	//		
	//	}
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

		//		if (PlayerOne.p1PosX >  && PlayerOne.p1PosX < x && PlayerOne.p1PosY > y && PlayerOne.p1PosY < y) {
		//
		//		}else if (PlayerTwo.p2PosX > x && PlayerTwo.p2PosX < x && PlayerTwo.p2PosY > y && PlayerTwo.p2PosY < y) {
		//
		//		}

		//Sol1
		if (room1) {
			if (PlayerOne.p1PosX > 10.8 && PlayerOne.p1PosX < 12.8 && PlayerOne.p1PosY > 55.6 && PlayerOne.p1PosY < 56.1) {
				MapLayer Layer = map.getLayers().get("Sol1");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room1= false;
			}

			else if (PlayerTwo.p2PosX > 10.8 && PlayerTwo.p2PosX < 12.8 && PlayerTwo.p2PosY > 55.6 && PlayerTwo.p2PosY < 56.1) {
				MapLayer Layer = map.getLayers().get("Sol1");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room1= false;
			}
		}
		//Sol2
		if (room2) {
			if (PlayerOne.p1PosX > 15.5 && PlayerOne.p1PosX < 15.9 && PlayerOne.p1PosY > 46 && PlayerOne.p1PosY < 49.3) {
				MapLayer Layer = map.getLayers().get("Sol2");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room2= false;
			}

			else if (PlayerTwo.p2PosX > 15.5 && PlayerTwo.p2PosX < 15.9 && PlayerTwo.p2PosY > 46 && PlayerTwo.p2PosY < 49.3) {
				MapLayer Layer = map.getLayers().get("Sol2");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room2= false;
			}
		}
		//Sol3
		if (room3) {
			if (PlayerOne.p1PosX > 29.0 && PlayerOne.p1PosX < 29.2 && PlayerOne.p1PosY > 46 && PlayerOne.p1PosY < 49.3) {
				MapLayer Layer = map.getLayers().get("Sol3");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room3= false;
			}

			else if (PlayerTwo.p2PosX > 29 && PlayerTwo.p2PosX < 29.2 && PlayerTwo.p2PosY > 46 && PlayerTwo.p2PosY < 40.3) {
				MapLayer Layer = map.getLayers().get("Sol3");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				room3= false;
			}
		}
		//Sol4 Sci1
		if (room4) {
			if (PlayerOne.p1PosX > 35.8 && PlayerOne.p1PosX < 36.2 && PlayerOne.p1PosY > 37.9 && PlayerOne.p1PosY < 57.8) {
				MapLayer Layer = map.getLayers().get("Sol4");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci1");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room4= false;
			}

			else if (PlayerTwo.p2PosX > 35.8 && PlayerTwo.p2PosX < 36.2 && PlayerTwo.p2PosY > 37.9 && PlayerTwo.p2PosY < 57.8) {
				MapLayer Layer = map.getLayers().get("Sol4");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci1");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room4= false;
			}
		}
		//Sci2
		if (room5) {
			if (PlayerOne.p1PosX > 39.9 && PlayerOne.p1PosX < 40.9 && PlayerOne.p1PosY > 45.5 && PlayerOne.p1PosY < 45.7) {

				MapLayer SciLayer = map.getLayers().get("Sci2");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room5 = false;
			}

			else if (PlayerTwo.p2PosX > 39.9 && PlayerTwo.p2PosX < 40.9 && PlayerTwo.p2PosY > 45.5 && PlayerTwo.p2PosY < 45.7) {

				MapLayer SciLayer = map.getLayers().get("Sci2");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room5= false;
			}
		}
		//Sci3
		if (room6) {
			if (PlayerOne.p1PosX > 40.2 && PlayerOne.p1PosX < 40.6 && PlayerOne.p1PosY > 50 && PlayerOne.p1PosY < 50.2) {

				MapLayer SciLayer = map.getLayers().get("Sci3");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room6 = false;
			}

			else if (PlayerTwo.p2PosX > 40.2 && PlayerTwo.p2PosX < 40.6 && PlayerTwo.p2PosY > 50 && PlayerTwo.p2PosY < 50.2) {

				MapLayer SciLayer = map.getLayers().get("Sci3");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room6= false;
			}
		}
		//Sol5 Sci4
		if (room7) {
			if (PlayerOne.p1PosX > 41.9 && PlayerOne.p1PosX < 42 && PlayerOne.p1PosY > 53.4 && PlayerOne.p1PosY < 54.4) {
				MapLayer Layer = map.getLayers().get("Sol5");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci4");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room7 = false;
			}

			else if (PlayerTwo.p2PosX > 41.9 && PlayerTwo.p2PosX < 42 && PlayerTwo.p2PosY > 53.4 && PlayerTwo.p2PosY < 54.4) {
				MapLayer Layer = map.getLayers().get("Sol5");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci4");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room7 = false;
			}
		}
		//Sol6 Sci5
		if (room8) {
			if (PlayerOne.p1PosX > 44.5 && PlayerOne.p1PosX < 44.7 && PlayerOne.p1PosY > 53.1 && PlayerOne.p1PosY < 53.2) {
				MapLayer Layer = map.getLayers().get("Sol6");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci5");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room8 = false;
			}

			else if (PlayerTwo.p2PosX > 44.5 && PlayerTwo.p2PosX < 44.7 && PlayerTwo.p2PosY > 53.1 && PlayerTwo.p2PosY < 53.2) {
				MapLayer Layer = map.getLayers().get("Sol6");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci5");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room8 = false;
			}
		}
		//Sol7 Sci6
		if (room9) {
			if (PlayerOne.p1PosX > 42.4 && PlayerOne.p1PosX < 45.5 && PlayerOne.p1PosY > 50 && PlayerOne.p1PosY < 50.2) {
				MapLayer Layer = map.getLayers().get("Sol7");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci6");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room9 = false;
			}

			else if (PlayerTwo.p2PosX > 42.4 && PlayerTwo.p2PosX < 45.5 && PlayerTwo.p2PosY > 50 && PlayerTwo.p2PosY < 50.2) {
				MapLayer Layer = map.getLayers().get("Sol7");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci6");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room9 = false;
			}
		}
		//Sol8
		if (room10) {
			if (PlayerOne.p1PosX > 42.1 && PlayerOne.p1PosX < 45.5 && PlayerOne.p1PosY > 46.1 && PlayerOne.p1PosY < 46.3) {
				MapLayer Layer = map.getLayers().get("Sol8");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				
				room10 = false;
			}

			else if (PlayerTwo.p2PosX > 42.4 && PlayerTwo.p2PosX < 45.5 && PlayerTwo.p2PosY > 50 && PlayerTwo.p2PosY < 50.2) {
				MapLayer Layer = map.getLayers().get("Sol8");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}

				room10 = false;
			}
		}
		//Sol9 Sci8
		if (room11) {
			if (PlayerOne.p1PosX > 45.3 && PlayerOne.p1PosX < 45.5 && PlayerOne.p1PosY > 39.9 && PlayerOne.p1PosY < 40.7) {
				MapLayer Layer = map.getLayers().get("Sol9");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci8");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room11 = false;
			}

			else if (PlayerTwo.p2PosX > 45.3 && PlayerTwo.p2PosX < 45.5 && PlayerTwo.p2PosY > 39.9 && PlayerTwo.p2PosY < 40.7) {
				MapLayer Layer = map.getLayers().get("Sol9");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci8");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room11 = false;
			}
		}
		//Sol10 Sci9
		if (room12) {
			if (PlayerOne.p1PosX > 47 && PlayerOne.p1PosX < 48 && PlayerOne.p1PosY > 41.6 && PlayerOne.p1PosY < 41.8) {
				MapLayer Layer = map.getLayers().get("Sol10");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci9");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room12 = false;
			}

			else if (PlayerTwo.p2PosX > 47 && PlayerTwo.p2PosX < 48 && PlayerTwo.p2PosY > 41.6 && PlayerTwo.p2PosY < 41.8) {
				MapLayer Layer = map.getLayers().get("Sol10");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci9");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room12 = false;
			}
		}		
		//Sol11 Sci10
		if (room13) {
			if (PlayerOne.p1PosX > 45.6 && PlayerOne.p1PosX < 49.4 && PlayerOne.p1PosY > 49.3 && PlayerOne.p1PosY < 49.5) {
				MapLayer Layer = map.getLayers().get("Sol11");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci10");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room13 = false;
			}

			else if (PlayerTwo.p2PosX > 45.6 && PlayerTwo.p2PosX < 49.4 && PlayerTwo.p2PosY > 49.3 && PlayerTwo.p2PosY < 49.5) {
				MapLayer Layer = map.getLayers().get("Sol11");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci10");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room13 = false;
			}
		}
		//Sol12 Sci11
		if (room14) {
			if (PlayerOne.p1PosX > 49.9 && PlayerOne.p1PosX < 50.1 && PlayerOne.p1PosY > 47.2 && PlayerOne.p1PosY < 57.7) {
				MapLayer Layer = map.getLayers().get("Sol12");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci11");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room14 = false;
			}

			else if (PlayerTwo.p2PosX > 49.9 && PlayerTwo.p2PosX < 50.1 && PlayerTwo.p2PosY > 47.2 && PlayerTwo.p2PosY < 57.7) {
				MapLayer Layer = map.getLayers().get("Sol12");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci11");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room14 = false;
			}
		}
		//Sol13 Sci12
		if (room15) {
			if (PlayerOne.p1PosX > 59.6 && PlayerOne.p1PosX < 59.5 && PlayerOne.p1PosY > 57.4 && PlayerOne.p1PosY < 45.1) {
				MapLayer Layer = map.getLayers().get("Sol13");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci12");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room15 = false;
			}
			else if (PlayerOne.p1PosX > 53.2 && PlayerOne.p1PosX < 55.7 && PlayerOne.p1PosY > 45.2 && PlayerOne.p1PosY < 45.3) {
				MapLayer Layer = map.getLayers().get("Sol13");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci12");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room15 = false;
			}

			else if (PlayerTwo.p2PosX > 59.6 && PlayerTwo.p2PosX < 59.5 && PlayerTwo.p2PosY > 57.4 && PlayerTwo.p2PosY < 45.1) {
				MapLayer Layer = map.getLayers().get("Sol13");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci12");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room15 = false;
			}
			else if (PlayerTwo.p2PosX > 53.2 && PlayerTwo.p2PosX < 55.7 && PlayerTwo.p2PosY > 45.2 && PlayerTwo.p2PosY < 45.3) {
				MapLayer Layer = map.getLayers().get("Sol13");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci12");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room15 = false;
			}
		}
		
		//Sol14 Sci13
		if (room16) {
			if (PlayerOne.p1PosX > 49.5 && PlayerOne.p1PosX < 74.7 && PlayerOne.p1PosY > 41.6 && PlayerOne.p1PosY < 41.9) {
				MapLayer Layer = map.getLayers().get("Sol14");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci13");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room16 = false;
			}

			else if (PlayerTwo.p2PosX > 49.5 && PlayerTwo.p2PosX < 74.7 && PlayerTwo.p2PosY > 41.6 && PlayerTwo.p2PosY < 41.9) {
				MapLayer Layer = map.getLayers().get("Sol14");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci13");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				room16 = false;
			}
		}
		//Sol15 Sci14 Tur1
		if (room17) {
			if (PlayerOne.p1PosX > 49 && PlayerOne.p1PosX < 74.7 && PlayerOne.p1PosY > 38.1 && PlayerOne.p1PosY < 38.6) {
				MapLayer Layer = map.getLayers().get("Sol15");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci14");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur1");
				for (MapObject mo : TurLayer.getObjects()) {
					System.out.println("Spawned!");
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room17 = false;
			}

			else if (PlayerTwo.p2PosX > 49 && PlayerTwo.p2PosX < 74.7 && PlayerTwo.p2PosY > 38.1 && PlayerTwo.p2PosY < 38.6) {
				MapLayer Layer = map.getLayers().get("Sol15");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci14");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur1");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room17 = false;
			}
		}
		//Sol16 Sci15 Tur2
		if (room18) {
			if (PlayerOne.p1PosX > 53 && PlayerOne.p1PosX < 56.4 && PlayerOne.p1PosY > 31.3 && PlayerOne.p1PosY < 31.4) {
				MapLayer Layer = map.getLayers().get("Sol16");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci15");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur2");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room18 = false;
			}

			else if (PlayerTwo.p2PosX > 53 && PlayerTwo.p2PosX < 56.4 && PlayerTwo.p2PosY > 31.3 && PlayerTwo.p2PosY < 31.4) {
				MapLayer Layer = map.getLayers().get("Sol16");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci15");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur2");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room18 = false;
			}
		}
		//Sol17 Sci16 Tur3
		if (room19) {
			if (PlayerOne.p1PosX > 53 && PlayerOne.p1PosX < 56.5 && PlayerOne.p1PosY > 24 && PlayerOne.p1PosY < 24.2) {
				MapLayer Layer = map.getLayers().get("Sol17");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci16");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur3");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room19 = false;
			}

			else if (PlayerTwo.p2PosX > 53 && PlayerTwo.p2PosX < 56.5 && PlayerTwo.p2PosY > 24 && PlayerTwo.p2PosY < 24.2) {
				MapLayer Layer = map.getLayers().get("Sol17");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci16");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur3");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room19 = false;
			}
		}
//		//Sol18 Sci17 DO NOT EXIST
//		if (room20) {
//			if (PlayerOne.p1PosX > 49.2 && PlayerOne.p1PosX < 49.5 && PlayerOne.p1PosY > 20.3 && PlayerOne.p1PosY < 23.8) {
//				MapLayer Layer = map.getLayers().get("Sol18");
//				for (MapObject mo : Layer.getObjects()) {
//					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					soldier = new Soldier(world);
//					Soldier.soldiers.add(soldier);
//				}
//				MapLayer SciLayer = map.getLayers().get("Sci17");
//				for (MapObject mo : SciLayer.getObjects()) {
//					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					scientist = new Scientist(world);
//					Scientist.scientists.add(scientist);
//				}
//
//				room20 = false;
//			}
//
//			else if (PlayerTwo.p2PosX > 49.2 && PlayerTwo.p2PosX < 49.5 && PlayerTwo.p2PosY > 20.3 && PlayerTwo.p2PosY < 23.8) {
//				MapLayer Layer = map.getLayers().get("Sol18");
//				for (MapObject mo : Layer.getObjects()) {
//					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					soldier = new Soldier(world);
//					Soldier.soldiers.add(soldier);
//				}
//				MapLayer SciLayer = map.getLayers().get("Sci17");
//				for (MapObject mo : SciLayer.getObjects()) {
//					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
//					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
//					scientist = new Scientist(world);
//					Scientist.scientists.add(scientist);
//				}
//
//				room20 = false;
//			}
//		}
		//Sol19 Sci18
		if (room21) {
			if (PlayerOne.p1PosX > 43.4 && PlayerOne.p1PosX < 46.8 && PlayerOne.p1PosY > 20 && PlayerOne.p1PosY < 20.4) {
				MapLayer Layer = map.getLayers().get("Sol19");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci18");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}

				room21 = false;
			}

			else if (PlayerTwo.p2PosX > 43.4 && PlayerTwo.p2PosX < 46.5 && PlayerTwo.p2PosY > 20 && PlayerTwo.p2PosY < 20.4) {
				MapLayer Layer = map.getLayers().get("Sol19");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci18");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}

				room21 = false;
			}
		}
		//Sol20 Sci19 Tur4
		if (room22) {
			if (PlayerOne.p1PosX > 43.4 && PlayerOne.p1PosX < 46.8 && PlayerOne.p1PosY > 13.1 && PlayerOne.p1PosY < 13.2) {
				MapLayer Layer = map.getLayers().get("Sol20");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci19");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur4");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room22 = false;
			}

			else if (PlayerTwo.p2PosX > 43.4 && PlayerTwo.p2PosX < 46.5 && PlayerTwo.p2PosY > 13.1 && PlayerTwo.p2PosY < 13.2) {
				MapLayer Layer = map.getLayers().get("Sol20");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				MapLayer SciLayer = map.getLayers().get("Sci19");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				MapLayer TurLayer = map.getLayers().get("Tur4");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room22 = false;
			}
		}
		//Sci20
		if (room23) {
			if (PlayerOne.p1PosX > 51.3 && PlayerOne.p1PosX < 41.6 && PlayerOne.p1PosY > 10.7 && PlayerOne.p1PosY < 12) {

				MapLayer SciLayer = map.getLayers().get("Sci20");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}

				room23 = false;
			}

			else if (PlayerTwo.p2PosX > 51.3 && PlayerTwo.p2PosX < 41.6 && PlayerTwo.p2PosY > 10.7 && PlayerTwo.p2PosY < 12) {

				MapLayer SciLayer = map.getLayers().get("Sci20");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}

				room23 = false;
			}
		}
		//Tur5
		if (room24) {
			if (PlayerOne.p1PosX > 57 && PlayerOne.p1PosX < 57.1 && PlayerOne.p1PosY > 11.3 && PlayerOne.p1PosY < 12.2) {

				MapLayer TurLayer = map.getLayers().get("Tur5");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room24 = false;
			}

			else if (PlayerTwo.p2PosX > 57 && PlayerTwo.p2PosX < 57.1 && PlayerTwo.p2PosY > 11.3 && PlayerTwo.p2PosY < 12.2) {

				MapLayer TurLayer = map.getLayers().get("Tur5");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room24 = false;
			}
		}
		//Gru1
		if (room25) {
			if (PlayerOne.p1PosX > 72.2 && PlayerOne.p1PosX < 72.3 && PlayerOne.p1PosY > 10.7 && PlayerOne.p1PosY < 12.5) {

				MapLayer GruLayer = map.getLayers().get("Gru1");
				for (MapObject mo : GruLayer.getObjects()) {
					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					Grunt.grunts.add(grunt);
				}
				room25 = false;
			}

			else if (PlayerTwo.p2PosX > 72.2 && PlayerTwo.p2PosX < 72.3 && PlayerTwo.p2PosY > 10.7 && PlayerTwo.p2PosY < 12.5) {

				MapLayer GruLayer = map.getLayers().get("Gru1");
				for (MapObject mo : GruLayer.getObjects()) {
					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					Grunt.grunts.add(grunt);
				}
				room25 = false;
			}
		}
		//Tur6
		if (room26) {
			if (PlayerOne.p1PosX > 78 && PlayerOne.p1PosX < 78.2 && PlayerOne.p1PosY > 9.2 && PlayerOne.p1PosY < 13.8) {

				MapLayer TurLayer = map.getLayers().get("Tur6");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room26 = false;
			}

			else if (PlayerTwo.p2PosX > 78 && PlayerTwo.p2PosX < 78.2 && PlayerTwo.p2PosY > 9.2 && PlayerTwo.p2PosY < 13.8) {

				MapLayer TurLayer = map.getLayers().get("Tur6");
				for (MapObject mo : TurLayer.getObjects()) {
					Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					turret = new Turret(world);
					Turret.turrets.add(turret);
				}
				room26 = false;
			}
		}
		
	}
}
