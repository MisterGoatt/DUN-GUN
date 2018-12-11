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

	//CreateBullet collisionResult = new CreateBullet(null);
	//private Array<Body> bodiesToRemove;
	public static Array<Body> bulletsToRemove;
	public static Array<Body> gruntsToRemove;
	private Array<Body> enemies;
	private Array<Grunt> gruntHealth = new Array<Grunt>();
	Grunt grunt;
	private Sound bulletHitWall;
	private Sound laserHitWall;
	private Sound pelletHitWall;
	private Sound bulletBodyImpact;
	
	public CollisionDetector() {
		
		enemies= new Array<Body>();
		bulletsToRemove = new Array<Body>();
		gruntsToRemove = new Array<Body>();
		bulletHitWall = DunGun.manager.get("sound effects/bulletImpact.mp3");
		laserHitWall = DunGun.manager.get("sound effects/laserImpact.mp3");
		pelletHitWall = DunGun.manager.get("sound effects/pelletImpact.mp3");
		bulletBodyImpact = DunGun.manager.get("sound effects/bulletBodyImpact.mp3");
		
		gruntHealth.clear();
		enemies.clear();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
		
		//BULLET AND WALL COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("walls") || fb.getUserData().equals("walls")) {
				//FA
				if (fa.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
							|| GunSelectionScreen.weaponSelected == "assault rifle" ) {
						long bhwId = bulletHitWall.play(.1f);
					}
					else if (GunSelectionScreen.weaponSelected == "laser") {
						laserHitWall.play();
					}
					else if (GunSelectionScreen.weaponSelected == "shotgun") {
						long phwId = pelletHitWall.play(.1f);
					}
					bulletsToRemove.add(fa.getBody()); // bullet

				}
				//FB
				else if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected == "rifle" || GunSelectionScreen.weaponSelected == "revolver" 
							|| GunSelectionScreen.weaponSelected == "assault rifle" ) {
						long bhwId = bulletHitWall.play(.1f);
					}
					else if (GunSelectionScreen.weaponSelected == "laser") {
						laserHitWall.play();
					}
					else if (GunSelectionScreen.weaponSelected == "shotgun") {
						long phwId = pelletHitWall.play(.1f);
					}
					bulletsToRemove.add(fb.getBody()); // bullet
					
				}
			}
		}
		//BULLET AND GRUNT COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {
				if (fa.getUserData().equals("bullets")){
					bulletsToRemove.add(fa.getBody()); //bullet

					}
				else if(fb.getUserData().equals("bullets")){
					bulletsToRemove.add(fb.getBody()); //bullet

				}				
				if (fa.getUserData().equals("grunt")){
					long bBI = bulletBodyImpact.play(.8f);

					enemies.add(fa.getBody());
					Body b = enemies.first();
					gruntHealth.add((Grunt) b.getUserData()); //casts Grunt on the physics body to get the class instance
					grunt = gruntHealth.get(0);
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
					
					if (grunt.health <= 0) {
						gruntsToRemove.add(fa.getBody()); //grunt
					}
					System.out.println(grunt.health);

				}
				else if(fb.getUserData().equals("grunt")){
					
					long bBI = bulletBodyImpact.play(.8f);
					enemies.add(fb.getBody());
					Body b = enemies.first();
					gruntHealth.add((Grunt) b.getUserData());
					grunt = gruntHealth.get(0);
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
					
					if (grunt.health <= 0) {
						gruntsToRemove.add(fb.getBody()); //grunt
					}
					System.out.println(grunt.health);
				}
			}
		}
	}

		
	

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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

		

