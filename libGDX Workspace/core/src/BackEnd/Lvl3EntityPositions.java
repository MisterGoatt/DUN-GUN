package BackEnd;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import entities.Flayer;
import entities.Grunt;
import entities.HealthPickUp;
import entities.Ivanov;
import entities.PlayerOne;
import entities.PlayerTwo;
import entities.Scientist;
import entities.Soldier;
import entities.Turret;

public class Lvl3EntityPositions {

	private Grunt grunt;
	private Scientist scientist;
	private Turret turret;
	private Soldier soldier;
	private Flayer flayer;
	private HealthPickUp hpPickUp;
	private Ivanov ivanov;
	// Every spawn point trigger's boolean
	public static boolean hpSpawn = false, spawnGrunts = false, spawnScientists = false, spawnSoldiers = false,
			spawnFlayers = false;
	private float p1X, p1Y, p2X, p2Y;
	LogFileHandler lfh = new LogFileHandler();

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

			p1X = PlayerOne.p1PosX;
			p1Y = PlayerOne.p1PosY;
			p2X = PlayerTwo.p2PosX;
			p2Y = PlayerTwo.p2PosY;

			// spawn grunts
			if (spawnGrunts) {
				MapLayer MutLayer = map.getLayers().get("mut");
				for (MapObject mo : MutLayer.getObjects()) {
					Grunt.gruntPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Grunt.gruntPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					grunt = new Grunt(world);
					Grunt.grunts.add(grunt);
				}
				spawnGrunts = false;
			}
			// spawn scientists
			if (spawnScientists) {
				MapLayer SciLayer = map.getLayers().get("sci");
				for (MapObject mo : SciLayer.getObjects()) {
					Scientist.scientistPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Scientist.scientistPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					scientist = new Scientist(world);
					Scientist.scientists.add(scientist);
				}
				spawnScientists = false;
			}
			// spawn soldiers
			if (spawnSoldiers) {

				MapLayer Layer = map.getLayers().get("sol");
				for (MapObject mo : Layer.getObjects()) {
					Soldier.soldierSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Soldier.soldierSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					soldier = new Soldier(world);
					Soldier.soldiers.add(soldier);
				}
				spawnSoldiers = false;
			}
			// spawn flayers
			if (spawnFlayers) {

				MapLayer FlayLayer = map.getLayers().get("flay");
				for (MapObject mo : FlayLayer.getObjects()) {
					Flayer.flayerSpawnPos.x = (float) mo.getProperties().get("x") / Mutagen.PPM;
					Flayer.flayerSpawnPos.y = (float) mo.getProperties().get("y") / Mutagen.PPM;
					flayer = new Flayer(world);
					Flayer.flayers.add(flayer);
				}
				spawnFlayers = false;
			}
			
		} catch (Exception e) {
			// Logs that this method of this class triggered an exception
			String name = Thread.currentThread().getStackTrace()[1].getMethodName();
			lfh.fileLog(this.getClass().getSimpleName() + " ", name + " ", "ERROR");
		}

	}

}
