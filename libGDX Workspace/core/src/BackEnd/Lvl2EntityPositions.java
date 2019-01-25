package BackEnd;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import entities.Flayer;
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
	private Flayer flayer;
	private HealthPickUp hpPickUp;
	//Every spawn point trigger's boolean
	private boolean hpSpawn = true, room1 = true, room2 = true, room3 = true, room4 = true, room5 = true, room6 = true, room7 = true,
			room8 = true, room9 = true, room10 = true, room11 = true, room12 = true, room13 = true, room14 = true, room15 = true, 
			room16 = true, room17 = true, room18 = true, room19 = true, room20 = true, room21 = true, room22 = true,
			room23 = true, room24 = true, room25 = true, room26 = true;
	private float p1X, p1Y, p2X, p2Y;
	LogFileHandler lfh = new LogFileHandler();
	
	public void SpawnEntities(World world, TiledMap map) {

		try {
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

			p1X = PlayerOne.p1PosX;
			p1Y = PlayerOne.p1PosY;
			p2X = PlayerTwo.p2PosX;
			p2Y = PlayerTwo.p2PosY;


			//Sci1 Sol1 (flay0)
			if (room1) {
				if (p1X > 33.5 && p1X < 37.8 && p1Y > 26.8 && p1Y < 27.2) {
					MapLayer Layer = map.getLayers().get("Sol1");
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
					MapLayer FlayLayer = map.getLayers().get("Flay0");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room1= false;
				}

				else if (p2X > 33.5 && p2X < 37.8 && p2Y > 26.8 && p2Y < 27.2) {
					MapLayer Layer = map.getLayers().get("Sol1");
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
					room1= false;
				}
			}
			//Sol2
			if (room2) {
				if (p1X > 33.4 && p1X < 33.5 && p1Y > 30.9 && p1Y < 37.8) {
					MapLayer Layer = map.getLayers().get("Sol2");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room2= false;
				}

				else if (p2X > 33.4 && p2X < 33.5 && p2Y > 30.9 && p2Y < 37.8) {
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
			//Sci2 Sol3
			if (room3) {
				if (p1X > 37.7 && p1X < 37.8 && p1Y > 30.9 && p1Y < 32) {
					MapLayer Layer = map.getLayers().get("Sol3");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci2");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room3= false;
				}

				else if (p2X > 37.7 && p2X < 37.8 && p2Y > 30.9 && p2Y < 32) {
					MapLayer Layer = map.getLayers().get("Sol3");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci2");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room3= false;
				}
			}
			//Sol4
			if (room4) {
				if (p1X > 35.1 && p1X < 36.2 && p1Y > 33.6 && p1Y < 33.7) {
					MapLayer Layer = map.getLayers().get("Sol4");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room4= false;
				}

				else if (p2X > 35.1 && p2X < 36.2 && p2Y > 33.6 && p2Y < 33.7) {
					MapLayer Layer = map.getLayers().get("Sol4");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room4= false;
				}
			}
			//Sol5
			if (room5) {
				if (p1X > 35 && p1X < 37.2 && p1Y > 36.3 && p1Y < 36.4) {
					MapLayer Layer = map.getLayers().get("Sol5");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room5= false;
				}

				else if (p2X > 35 && p2X < 37.2 && p2Y > 36.3 && p2Y < 36.4) {
					MapLayer Layer = map.getLayers().get("Sol5");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room5= false;
				}
			}
			//Sci10
			if (room6) {
				if (p1X > 37.7 && p1X < 37.8 && p1Y > 30.9 && p1Y < 32) {

					MapLayer SciLayer = map.getLayers().get("Sci10");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room6= false;
				}

				else if (p2X > 37.7 && p2X < 37.8 && p2Y > 30.9 && p2Y < 32) {

					MapLayer SciLayer = map.getLayers().get("Sci10");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room6= false;
				}
			}
			//Sci11 Mut9 Flay6
			if (room7) {
				if (p1X > 28.4 && p1X < 28.5 && p1Y > 38 && p1Y < 40) {

					MapLayer SciLayer = map.getLayers().get("Sci11");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					MapLayer MutLayer = map.getLayers().get("Mut9");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay6");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room7= false;
				}

				else if (p2X > 28.4 && p2X < 28.5 && p2Y > 38 && p2Y < 40) {

					MapLayer SciLayer = map.getLayers().get("Sci11");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					MapLayer MutLayer = map.getLayers().get("Mut9");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay6");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room7= false;
				}
			}
			//Sol6
			if (room8) {
				if (p1X > 32.8 && p1X < 38.8 && p1Y > 39.8 && p1Y < 40.1) {
					MapLayer Layer = map.getLayers().get("Sol6");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room8= false;
				}

				else if (p2X > 32.8 && p2X < 38.8 && p2Y > 39.8 && p2Y < 40.1) {
					MapLayer Layer = map.getLayers().get("Sol6");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room8= false;
				}
			}
			//Sol7
			if (room9) {
				if (p1X > 32.4 && p1X < 40.4 && p1Y > 42.6 && p1Y < 43.1) {
					MapLayer Layer = map.getLayers().get("Sol7");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room9= false;
				}

				else if (p2X > 32.4 && p2X < 40.4 && p2Y > 42.6 && p2Y < 43.1) {
					MapLayer Layer = map.getLayers().get("Sol7");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room9= false;
				}
			}
			//Sci3 Sol8
			if (room10) {
				if (p1X > 40.3 && p1X < 40.5 && p1Y > 45 && p1Y < 50.3) {
					MapLayer Layer = map.getLayers().get("Sol8");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci3");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room10= false;
				}

				else if (p2X > 40.3 && p2X < 40.5 && p2Y > 45 && p2Y < 50.3) {
					MapLayer Layer = map.getLayers().get("Sol8");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci3");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					room10= false;
				}
			}		
			//Sci4 Sol9 Mut1
			if (room11) {
				if (p1X > 44.4 && p1X < 44.8 && p1Y > 45.5 && p1Y < 48) {
					MapLayer Layer = map.getLayers().get("Sol9");
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
					MapLayer MutLayer = map.getLayers().get("Mut1");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					room11= false;
				}

				else if (p2X > 44.4 && p2X < 44.8 && p2Y > 45.5 && p2Y < 48) {
					MapLayer Layer = map.getLayers().get("Sol9");
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
					MapLayer MutLayer = map.getLayers().get("Mut1");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					room11= false;
				}
			}
			//Sci5 Sol10 Mut2 Tur1
			if (room12) {
				if (p1X > 50 && p1X < 50.2 && p1Y > 44 && p1Y < 46.1) {
					MapLayer Layer = map.getLayers().get("Sol10");
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
					MapLayer MutLayer = map.getLayers().get("Mut2");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur1");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					room12= false;
				}

				else if (p2X > 50 && p2X < 50.2 && p2Y > 44 && p2Y < 46.1) {
					MapLayer Layer = map.getLayers().get("Sol10");
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
					MapLayer MutLayer = map.getLayers().get("Mut1");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur1");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					room12 = false;
				}
			}
			//Sol11
			if (room13) {
				if (p1X > 54.8 && p1X < 55 && p1Y > 44.3 && p1Y < 45.8) {
					MapLayer Layer = map.getLayers().get("Sol11");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					room13= false;
				}

				else if (p2X > 54.8 && p2X < 55 && p2Y > 44.3 && p2Y < 45.8) {
					MapLayer Layer = map.getLayers().get("Sol11");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}

					room13= false;
				}
			}
			//Sci6 Mut3
			if (room14) {
				if (p1X > 61.4 && p1X < 61.5 && p1Y > 44.5 && p1Y < 45.5) {

					MapLayer SciLayer = map.getLayers().get("Sci6");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					MapLayer MutLayer = map.getLayers().get("Mut3");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room14= false;
				}

				else if (p2X > 61.4 && p2X < 61.5 && p2Y > 44.5 && p2Y < 45.5) {

					MapLayer SciLayer = map.getLayers().get("Sci6");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}
					MapLayer MutLayer = map.getLayers().get("Mut3");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room14 = false;
				}
			}
			//Mut4 Flay1
			if (room15) {
				if (p1X > 71.3 && p1X < 71.4 && p1Y > 43.9 && p1Y < 45.9) {

					MapLayer FlayLayer = map.getLayers().get("Flay1");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut4");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room15= false;
				}

				else if (p2X > 71.3 && p2X < 71.4 && p2Y > 43.9 && p2Y < 45.9) {

					MapLayer FlayLayer = map.getLayers().get("Flay1");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut4");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room15 = false;
				}
			}
			//Mut5 Flay2
			if (room16) {
				if (p1X > 74.3 && p1X < 74.6 && p1Y > 40.5 && p1Y < 44.8) {

					MapLayer FlayLayer = map.getLayers().get("Flay2");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut5");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room16= false;
				}

				else if (p2X > 74.3 && p2X < 74.6 && p2Y > 40.5 && p2Y < 44.8) {

					MapLayer FlayLayer = map.getLayers().get("Flay2");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut5");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room16 = false;
				}
			}
			//Mut6 Flay3
			if (room17) {
				if (p1X > 77.8 && p1X < 77.9 && p1Y > 39.5 && p1Y < 42.6) {

					MapLayer FlayLayer = map.getLayers().get("Flay3");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut6");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room17 = false;
				}

				else if (p2X > 77.8 && p2X < 77.9 && p2Y > 39.5 && p2Y < 42.6) {

					MapLayer FlayLayer = map.getLayers().get("Flay3");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					MapLayer MutLayer = map.getLayers().get("Mut6");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}

					room17 = false;
				}
			}
			//Mut7 Tur2 Flay4
			if (room18) {
				if (p1X > 78.8 && p1X < 84.8 && p1Y > 38.1 && p1Y < 38.4) {

					MapLayer MutLayer = map.getLayers().get("Mut7");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur2");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay4");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room18= false;
				}

				else if (p2X > 78.8 && p2X < 84.8 && p2Y > 38.1 && p2Y < 38.4) {

					MapLayer MutLayer = map.getLayers().get("Mut7");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur2");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay4");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room18 = false;
				}
				
			}
			//Sci7 Sol12
			if (room19) {
				if (p1X > 78.9 && p1X < 83.9 && p1Y > 34.3 && p1Y < 34.4) {
					MapLayer Layer = map.getLayers().get("Sol12");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci7");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}

					room19= false;
				}

				else if (p2X > 78.9 && p2X < 83.9 && p2Y > 34.3 && p2Y < 34.4) {
					MapLayer Layer = map.getLayers().get("Sol12");
					for (MapObject mo : Layer.getObjects()) {
						Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						soldier = new Soldier(world);
						Soldier.soldiers.add(soldier);
					}
					MapLayer SciLayer = map.getLayers().get("Sci7");
					for (MapObject mo : SciLayer.getObjects()) {
						Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						scientist = new Scientist(world);
						Scientist.scientists.add(scientist);
					}

					room19 = false;
				}
			}
			//Sci8 Sol13
			if (room20) {
				if (p1X > 80.7 && p1X < 83.9 && p1Y > 31.7 && p1Y < 32) {
					
					System.out.println("mate");
					MapLayer Layer = map.getLayers().get("Sol13");
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

					room20= false;
				}

				else if (p2X > 78.9 && p2X < 83.9 && p2Y > 31.7 && p2Y < 32) {
					MapLayer Layer = map.getLayers().get("Sol13");
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

					room20 = false;
				}
			}
			//Sci9 Sol14 Mut8 Tur3 Flay5
			if (room21) {
				if (p1X > 83.5 && p1X < 83.6 && p1Y > 20.6 && p1Y < 28.8) {
					MapLayer Layer = map.getLayers().get("Sol14");
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
					MapLayer MutLayer = map.getLayers().get("Mut8");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur3");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay5");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room21= false;
				}

				else if (p2X > 83.5 && p2X < 83.6 && p2Y > 20.6 && p2Y < 28.8) {
					MapLayer Layer = map.getLayers().get("Sol14");
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
					MapLayer MutLayer = map.getLayers().get("Mut8");
					for (MapObject mo : MutLayer.getObjects()) {
						Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						grunt = new Grunt(world);
						Grunt.grunts.add(grunt);
					}
					MapLayer TurLayer = map.getLayers().get("Tur3");
					for (MapObject mo : TurLayer.getObjects()) {
						Turret.turretSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Turret.turretSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						turret = new Turret(world);
						Turret.turrets.add(turret);
					}
					MapLayer FlayLayer = map.getLayers().get("Flay5");
					for (MapObject mo : FlayLayer.getObjects()) {
						Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
						Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
						flayer = new Flayer(world);
						Flayer.flayers.add(flayer);
					}
					room21 = false;
				}
			}
		} catch (Exception e) {
			//Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}
	}

}
