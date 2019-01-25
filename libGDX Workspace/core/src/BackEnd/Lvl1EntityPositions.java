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
	LogFileHandler lfh = new LogFileHandler();
	// public Lvl1EntityPositions() {
	//
	// }
	private Grunt grunt;
	private Scientist scientist;
	private Turret turret;
	private Soldier soldier;
	private HealthPickUp hpPickUp;
	// Every spawn point trigger's boolean
	private boolean hpSpawn = true, room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, room6 = true,
			room7 = true, room8 = true, room9 = true, room10 = true, room11 = true, room12 = true, room13 = true,
			room14 = true, room15 = true, room16 = true, room17 = true, room18 = true, room19 = true, room20 = true,
			room21 = true, room22 = true, room23 = true, room24 = true, room25 = true, room26 = true;
	private float p1X, p1Y, p2X, p2Y;

	public void SpawnEntities(World world, TiledMap map) {

		try {
			// SPAWN HEALTH PICK_UPS
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

			// if (p1X > && p1X < x && p1Y > y && p1Y < y) {
			//
			// }else if (p2X > x && p2X < x && p2Y > y && p2Y < y) {
			//
			// }

			p1X = PlayerOne.p1PosX;
			p1Y = PlayerOne.p1PosY;
			p2X = PlayerTwo.p2PosX;
			p2Y = PlayerTwo.p2PosY;

			// Sol1
			if (room1) {
				if (p1X > 23 && p1X < 23.9 && p1Y > 45 && p1Y < 49.2) {
					MapLayer Layer = map.getLayers().get("Sol1");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room1 = false;
				}

				else if (p2X > 23 && p2X < 23.9 && p2Y > 45 && p2Y < 55.6) {
					MapLayer Layer = map.getLayers().get("Sol1");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room1 = false;
				}
			}
			// Sol2
			if (room2) {
				if (p1X > 25.8 && p1X < 25.9 && p1Y > 46 && p1Y < 49.3) {
					MapLayer Layer = map.getLayers().get("Sol2");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room2 = false;
				}

				else if (p2X > 25.8 && p2X < 25.9 && p2Y > 46 && p2Y < 49.3) {
					MapLayer Layer = map.getLayers().get("Sol2");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room2 = false;
				}
			}
			// Sol3
			if (room3) {
				if (p1X > 29.0 && p1X < 29.2 && p1Y > 46 && p1Y < 49.3) {
					MapLayer Layer = map.getLayers().get("Sol3");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room3 = false;
				}

				else if (p2X > 29 && p2X < 29.2 && p2Y > 46 && p2Y < 49.3) {
					MapLayer Layer = map.getLayers().get("Sol3");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room3 = false;
				}
			}
			// Sol4 Sci1
			if (room4) {
				if (p1X > 35.8 && p1X < 36.2 && p1Y > 37.9 && p1Y < 57.8) {
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
					room4 = false;
				}

				else if (p2X > 35.8 && p2X < 36.2 && p2Y > 37.9 && p2Y < 57.8) {
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
					room4 = false;
				}
			}
			// Sci2
			if (room5) {
				if (p1X > 39.9 && p1X < 40.9 && p1Y > 45.5 && p1Y < 45.7) {

					MapLayer SciLayer = map.getLayers().get("Sci2");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room5 = false;
				}

				else if (p2X > 39.9 && p2X < 40.9 && p2Y > 45.5 && p2Y < 45.7) {

					MapLayer SciLayer = map.getLayers().get("Sci2");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room5 = false;
				}
			}
			// Sci3
			if (room6) {
				if (p1X > 40.2 && p1X < 40.6 && p1Y > 50 && p1Y < 50.2) {

					MapLayer SciLayer = map.getLayers().get("Sci3");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room6 = false;
				}

				else if (p2X > 40.2 && p2X < 40.6 && p2Y > 50 && p2Y < 50.2) {

					MapLayer SciLayer = map.getLayers().get("Sci3");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room6 = false;
				}
			}
			// Sol5 Sci4
			if (room7) {
				if (p1X > 38.9 && p1X < 42.1 && p1Y > 51 && p1Y < 52) {
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

				else if (p2X > 38.9 && p2X < 42.1 && p2Y > 51 && p2Y < 52) {
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
			// Sol6 Sci5
			if (room8) {
				if (p1X > 44.5 && p1X < 44.7 && p1Y > 53.1 && p1Y < 53.2) {
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

				else if (p2X > 44.5 && p2X < 44.7 && p2Y > 53.1 && p2Y < 53.2) {
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
			// Sol7 Sci6
			if (room9) {
				if (p1X > 42.4 && p1X < 45.5 && p1Y > 50 && p1Y < 50.2) {
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

				else if (p2X > 42.4 && p2X < 45.5 && p2Y > 50 && p2Y < 50.2) {
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
			// Sol8
			if (room10) {
				if (p1X > 42.1 && p1X < 45.5 && p1Y > 46.1 && p1Y < 46.3) {
					MapLayer Layer = map.getLayers().get("Sol8");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room10 = false;
				}

				else if (p2X > 42.1 && p2X < 45.5 && p2Y > 46.1 && p2Y < 46.3) {
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
			// Sol9 Sci8
			if (room11) {
				if (p1X > 45.3 && p1X < 45.5 && p1Y > 39.9 && p1Y < 40.7) {
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

				else if (p2X > 45.3 && p2X < 45.5 && p2Y > 39.9 && p2Y < 40.7) {
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
			// Sol10 Sci9
			if (room12) {
				if (p1X > 47 && p1X < 48 && p1Y > 41.6 && p1Y < 41.8) {
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

				else if (p2X > 47 && p2X < 48 && p2Y > 41.6 && p2Y < 41.8) {
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
			// Sol11 Sci10
			if (room13) {
				if (p1X > 45.6 && p1X < 49.4 && p1Y > 49.3 && p1Y < 49.5) {
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

				else if (p2X > 45.6 && p2X < 49.4 && p2Y > 49.3 && p2Y < 49.5) {
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
			// Sol12 Sci11
			if (room14) {
				if (p1X > 49.9 && p1X < 50.1 && p1Y > 47.2 && p1Y < 57.7) {
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

				else if (p2X > 49.9 && p2X < 50.1 && p2Y > 47.2 && p2Y < 57.7) {
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
			// Sol13 Sci12
			if (room15) {
				if (p1X > 59.5 && p1X < 59.6 && p1Y > 45.1 && p1Y < 57.4) {
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

				else if (p2X > 59.5 && p2X < 59.6 && p2Y > 45.1 && p2Y < 57.4) {
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

			// Sol14 Sci13
			if (room16) {
				if (p1X > 49.5 && p1X < 74.7 && p1Y > 41.6 && p1Y < 41.9) {
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

				else if (p2X > 49.5 && p2X < 74.7 && p2Y > 41.6 && p2Y < 41.9) {
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
			// Sol15 Sci14 Tur1
			if (room17) {
				if (p1X > 49 && p1X < 74.7 && p1Y > 38.1 && p1Y < 38.6) {
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

				else if (p2X > 49 && p2X < 74.7 && p2Y > 38.1 && p2Y < 38.6) {
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
			// Sol16 Sci15 Tur2
			if (room18) {
				if (p1X > 53 && p1X < 56.4 && p1Y > 31.3 && p1Y < 31.4) {
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

				else if (p2X > 53 && p2X < 56.4 && p2Y > 31.3 && p2Y < 31.4) {
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
			// Sol17 Sci16 Tur3
			if (room19) {
				if (p1X > 53 && p1X < 56.5 && p1Y > 24 && p1Y < 24.2) {
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

				else if (p2X > 53 && p2X < 56.5 && p2Y > 24 && p2Y < 24.2) {
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
			
			if (room21) {
				if (p1X > 43.4 && p1X < 46.8 && p1Y > 20 && p1Y < 20.4) {
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

				else if (p2X > 43.4 && p2X < 46.8 && p2Y > 20 && p2Y < 20.4) {
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
			// Sol20 Sci19 Tur4
			if (room22) {
				if (p1X > 43.4 && p1X < 46.8 && p1Y > 13.1 && p1Y < 13.2) {
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

				else if (p2X > 43.4 && p2X < 46.8 && p2Y > 13.1 && p2Y < 13.2) {
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
			// Sci20
			if (room23) {
				if (p1X > 51.3 && p1X < 41.6 && p1Y > 10.7 && p1Y < 12) {

					MapLayer SciLayer = map.getLayers().get("Sci20");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}

					room23 = false;
				}

				else if (p2X > 51.3 && p2X < 41.6 && p2Y > 10.7 && p2Y < 12) {

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
			// Tur5
			if (room24) {
				if (p1X > 57 && p1X < 57.1 && p1Y > 11.3 && p1Y < 12.2) {

					MapLayer TurLayer = map.getLayers().get("Tur5");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					room24 = false;
				}

				else if (p2X > 57 && p2X < 57.1 && p2Y > 11.3 && p2Y < 12.2) {

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
			// Gru1
			if (room25) {
				if (p1X > 72.2 && p1X < 72.3 && p1Y > 10.7 && p1Y < 12.5) {

					MapLayer GruLayer = map.getLayers().get("Gru1");
					for (MapObject mo : GruLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					room25 = false;
				}

				else if (p2X > 72.2 && p2X < 72.3 && p2Y > 10.7 && p2Y < 12.5) {

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
			// Tur6
			if (room26) {
				if (p1X > 78 && p1X < 78.2 && p1Y > 9.2 && p1Y < 13.8) {

					MapLayer TurLayer = map.getLayers().get("Tur6");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					room26 = false;
				}

				else if (p2X > 78 && p2X < 78.2 && p2Y > 9.2 && p2Y < 13.8) {

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
			// Logs that this method of this class worked properly
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "Working");
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");

		}

	}
}
