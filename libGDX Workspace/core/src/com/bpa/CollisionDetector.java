package com.bpa;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector implements ContactListener{

	private Array<Body> tempBodyArray, bodiesToRemove;
	private Array<Grunt> gruntBodyTarget = new Array<Grunt>();
	private Array<Scientist> scientistBodyTarget = new Array<Scientist>();
	private Array<Turret> turretBodyTarget = new Array<Turret>();
	private Array<CreateBullet> bulletBodyTarget = new Array<CreateBullet>();

	Grunt grunt;
	Scientist scientist;
	Turret turret;
	CreateBullet createBullet;
	private Sound bulletHitWall, bulletBodyImpact, pelletHitWall, laserHitWall, turretHit;

	public CollisionDetector() {	
		tempBodyArray= new Array<Body>();
		bodiesToRemove = new Array<Body>();
		bulletHitWall = Mutagen.manager.get("sound effects/bulletImpact.mp3");
		laserHitWall = Mutagen.manager.get("sound effects/laserImpact.mp3");
		pelletHitWall = Mutagen.manager.get("sound effects/pelletImpact.mp3");
		bulletBodyImpact = Mutagen.manager.get("sound effects/bulletBodyImpact.mp3");
		turretHit = Mutagen.manager.get("sound effects/turretHit.mp3");
		gruntBodyTarget.clear();
		scientistBodyTarget.clear();
		tempBodyArray.clear();
		bulletBodyTarget.clear();
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
		
//		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")){
//			if (fa.getUserData().equals("bullets") && GunSelectionScreen.weaponSelected == "laser") {
//				tempBodyArray.add(fa.getBody());
//				Body b = tempBodyArray.first();
//				bulletBodyTarget.add((CreateBullet) b.getUserData()); //casts Grunt on the physics body to get the class instance
//				createBullet = bulletBodyTarget.get(0);
//				createBullet.explosion= true;
//			}
//			if (fb.getUserData().equals("bullets") && GunSelectionScreen.weaponSelected == "laser") {
//				tempBodyArray.add(fb.getBody());
//				Body b = tempBodyArray.first();
//				bulletBodyTarget.add((CreateBullet) b.getUserData()); //casts Grunt on the physics body to get the class instance
//				createBullet = bulletBodyTarget.get(0);
//				createBullet.explosion= true;
//			}
//		}
		
		//BULLET AND WALL COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("walls") || fb.getUserData().equals("walls")) {
				//FA
				if (fa.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
							|| GunSelectionScreen.weaponSelected == "assault rifle" ) {
						if (Mutagen.sfxVolume != 0) {
							long bhwId = bulletHitWall.play(Mutagen.sfxVolume - .8f);
						}
					}
					else if (GunSelectionScreen.weaponSelected == "laser") {
						
						if (Mutagen.sfxVolume != 0) {
							laserHitWall.play(Mutagen.sfxVolume);
						}
					}
					else if (GunSelectionScreen.weaponSelected == "shotgun") {
						if (Mutagen.sfxVolume != 0) {
							long phwId = pelletHitWall.play(Mutagen.sfxVolume - .8f);
						}
					}

					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fa.getBody());
					}

				}
				//FB
				if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
							|| GunSelectionScreen.weaponSelected == "assault rifle" ) {
						if (Mutagen.sfxVolume != 0) {
							long bhwId = bulletHitWall.play(Mutagen.sfxVolume - .8f);
						}
					}
					else if (GunSelectionScreen.weaponSelected == "laser") {
						if (Mutagen.sfxVolume != 0) {
							laserHitWall.play(Mutagen.sfxVolume);
						}
					}
					else if (GunSelectionScreen.weaponSelected == "shotgun") {
						if (Mutagen.sfxVolume != 0) {
							long phwId = pelletHitWall.play(Mutagen.sfxVolume - .8f);
						}
					}
					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fb.getBody());
					}

				}
			}
		}
		//BULLET AND GRUNT COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {
				if (fa.getUserData().equals("bullets")){

					if (GunSelectionScreen.weaponSelected != "battle axe") {
						CreateBullet.explosion= true;	
						bodiesToRemove.add(fa.getBody()); //bullet

					}
				}
				if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected != "battle axe") {	
						CreateBullet.explosion= true;	

						bodiesToRemove.add(fb.getBody()); //bullet
					}

				}				
				if (fa.getUserData().equals("grunt")){
					if (Mutagen.sfxVolume != 0) {
						long bBI = bulletBodyImpact.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData()); //casts Grunt on the physics body to get the class instance
					grunt = gruntBodyTarget.get(0);
					grunt.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){
					case "battle axe": grunt.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": grunt.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": grunt.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": grunt.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": grunt.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": grunt.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					gruntBodyTarget.clear();
					tempBodyArray.clear();

					if (grunt.health <= 0) {
						bodiesToRemove.add(fa.getBody()); //grunt
						grunt.tookDamage = false;
					}
				}

				if(fb.getUserData().equals("grunt")){
					if (Mutagen.sfxVolume != 0) {
						long bBI = bulletBodyImpact.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData());
					grunt = gruntBodyTarget.get(0);
					grunt.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){					
					case "battle axe": grunt.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": grunt.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": grunt.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": grunt.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": grunt.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": grunt.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					gruntBodyTarget.clear();
					tempBodyArray.clear();

					if (grunt.health <= 0) {
						bodiesToRemove.add(fb.getBody()); //grunt
						grunt.tookDamage = false;
					}
				}
			}
		}
		//GRUNT AND PLAYER COLLISIONS
		if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {

				if(fa.getUserData().equals("grunt")){
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData());
					grunt = gruntBodyTarget.get(0);
					grunt.contAtk = true;
					gruntBodyTarget.clear();
					tempBodyArray.clear();
				}
				if(fb.getUserData().equals("grunt")){
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData());
					grunt = gruntBodyTarget.get(0);
					grunt.contAtk = true;
					gruntBodyTarget.clear();
					tempBodyArray.clear();
				}
			}
		}
		//SCIENTIST AND PLAYER COLLISIONS
		if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
			if (fa.getUserData().equals("scientist") || fb.getUserData().equals("scientist")) {

				if(fa.getUserData().equals("scientist")){
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData());
					scientist = scientistBodyTarget.get(0);
					scientist.contAtk = true;
					scientistBodyTarget.clear();
					tempBodyArray.clear();
				}
				if(fb.getUserData().equals("scientist")){
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData());
					scientist = scientistBodyTarget.get(0);
					scientist.contAtk = true;
					scientistBodyTarget.clear();
					tempBodyArray.clear();
				}
			}
		}




		//BULLET AND GRUNT COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("scientist") || fb.getUserData().equals("scientist")) {
				if (fa.getUserData().equals("bullets")){

					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fa.getBody()); //bullet

					}
				}
				if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fb.getBody()); //bullet
					}

				}				
				if (fa.getUserData().equals("scientist")){
					if (Mutagen.sfxVolume != 0) {
						long bBI = bulletBodyImpact.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData()); //casts Grunt on the physics body to get the class instance
					scientist = scientistBodyTarget.get(0);
					//not needed yet
					//scientist.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){
					case "battle axe": scientist.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": scientist.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": scientist.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": scientist.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": scientist.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": scientist.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					scientistBodyTarget.clear();
					tempBodyArray.clear();

					if (scientist.health <= 0) {
						bodiesToRemove.add(fa.getBody()); //grunt
						scientist.tookDamage = false;
					}
				}

				if(fb.getUserData().equals("scientist")){
					if (Mutagen.sfxVolume != 0) {
						long bBI = bulletBodyImpact.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData()); //casts Grunt on the physics body to get the class instance
					scientist = scientistBodyTarget.get(0);
					//not needed yet
					//scientist.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){
					case "battle axe": scientist.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": scientist.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": scientist.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": scientist.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": scientist.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": scientist.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					scientistBodyTarget.clear();
					tempBodyArray.clear();

					if (scientist.health <= 0) {
						bodiesToRemove.add(fb.getBody()); //grunt
						scientist.tookDamage = false;
					}
				}
			}
		}
		//BULLET AND TURRET COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("turret") || fb.getUserData().equals("turret")) {
				if (fa.getUserData().equals("bullets")){

					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fa.getBody()); //bullet
					}
				}
				if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fb.getBody()); //bullet
					}
				}				
				if (fa.getUserData().equals("turret")){
					if (Mutagen.sfxVolume != 0) {
						long tH = turretHit.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					turretBodyTarget.add((Turret) b.getUserData()); //casts Grunt on the physics body to get the class instance
					turret = turretBodyTarget.get(0);
					//not needed yet
					//scientist.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){
					case "battle axe": turret.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": turret.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": turret.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": turret.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": turret.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": turret.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					turretBodyTarget.clear();
					tempBodyArray.clear();

					if (turret.health <= 0) {
						bodiesToRemove.add(fa.getBody()); //grunt
						//turret.tookDamage = false;
					}
				}
				if (fb.getUserData().equals("turret")){
					if (Mutagen.sfxVolume != 0) {
						long tH = turretHit.play(Mutagen.sfxVolume - .2f);
					}
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					turretBodyTarget.add((Turret) b.getUserData()); //casts Grunt on the physics body to get the class instance
					turret = turretBodyTarget.get(0);
					//not needed yet
					//scientist.tookDamage = true;

					switch (GunSelectionScreen.weaponSelected){
					case "battle axe": turret.health -= PlayerOne.battleAxeDamage;
					break;
					case "revolver": turret.health -= PlayerOne.revolverDamage;
					break;
					case "rifle": turret.health -= PlayerOne.rifleDamage;
					break;	
					case "assault rifle": turret.health -= PlayerOne.assaultRifleDamage;
					break;
					case "laser": turret.health -= PlayerOne.laserLanceDamage;
					break;
					case "shotgun": turret.health -= PlayerOne.shotgunDamage;
					break;
					default: break;
					}
					turretBodyTarget.clear();
					tempBodyArray.clear();

					if (turret.health <= 0) {
						bodiesToRemove.add(fb.getBody()); //grunt
						//turret.tookDamage = false;
					}
				}

			}

		}

		//TURRET BULLETS AND WALL COLLISIONS
		if (fa.getUserData().equals("turret bullets") || fb.getUserData().equals("turret bullets")) {
			if (fa.getUserData().equals("walls") || fb.getUserData().equals("walls")) {
				//FA
				if (fa.getUserData().equals("turret bullets")){
					if (Mutagen.sfxVolume != 0) {
						long bhwId = bulletHitWall.play(Mutagen.sfxVolume - .8f);
					}
					bodiesToRemove.add(fa.getBody());
				}
				//FB
				if(fb.getUserData().equals("turret bullets")){
					if (Mutagen.sfxVolume != 0) {
						long bhwId = bulletHitWall.play(Mutagen.sfxVolume - .8f);
					}
					bodiesToRemove.add(fb.getBody());
				}

			}
		}
		//TURRET BULLETS AND PLAYER COLLISIONS
		if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
			if (fa.getUserData().equals("turret bullets") || fb.getUserData().equals("turret bullets")) {

				if(fa.getUserData().equals("turret bullets")){

					PlayerOne.player1HP -= Turret.atkDmg;
					bodiesToRemove.add(fa.getBody()); 

				}
				if(fb.getUserData().equals("turret bullets")){
					PlayerOne.player1HP -= Turret.atkDmg;
					bodiesToRemove.add(fb.getBody());
				}
			}
		}
	}


	public Array <Body> getbodiesToRemove(){
		return bodiesToRemove;
	}		


// *******************************************************END CONTACT********************************************************

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;

		//GRUNT AND PLAYER COLLISIONS
		if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {

				if(fa.getUserData().equals("grunt")){
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData());
					grunt = gruntBodyTarget.get(0);
					grunt.contAtk = false;
					gruntBodyTarget.clear();
					tempBodyArray.clear();
				}

				if(fb.getUserData().equals("grunt")){
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					gruntBodyTarget.add((Grunt) b.getUserData());
					grunt = gruntBodyTarget.get(0);
					grunt.contAtk = false;
					gruntBodyTarget.clear();
					tempBodyArray.clear();
				}
			}
		}
		//SCIENTIST AND PLAYER COLLISIONS
		if (fa.getUserData().equals("player") || fb.getUserData().equals("player")) {
			if (fa.getUserData().equals("scientist") || fb.getUserData().equals("scientist")) {
				if(fa.getUserData().equals("scientist")){
					tempBodyArray.add(fa.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData());
					scientist = scientistBodyTarget.get(0);
					scientist.contAtk = false;
					scientistBodyTarget.clear();
					tempBodyArray.clear();
				}

				if(fb.getUserData().equals("scientist")){
					tempBodyArray.add(fb.getBody());
					Body b = tempBodyArray.first();
					scientistBodyTarget.add((Scientist) b.getUserData());
					scientist = scientistBodyTarget.get(0);
					scientist.contAtk = false;
					scientistBodyTarget.clear();
					tempBodyArray.clear();
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}

}



