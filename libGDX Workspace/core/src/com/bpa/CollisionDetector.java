package com.bpa;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector implements ContactListener{

	private Array<Body> enemies;
	private Array<Grunt> gruntHealth = new Array<Grunt>();
	private Array<Body> bodiesToRemove;
	Grunt grunt;
	private Sound bulletHitWall;
	private Sound laserHitWall;
	private Sound pelletHitWall;
	private Sound bulletBodyImpact;

	
	public CollisionDetector() {	
		enemies= new Array<Body>();
//		bulletsToRemove = new Array<Body>();
//		gruntsToRemove = new Array<Body>();
		bodiesToRemove = new Array<Body>();
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
					bodiesToRemove.add(fa.getBody()); // bullet

				}
				//FB
				if(fb.getUserData().equals("bullets")){
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
					bodiesToRemove.add(fb.getBody()); // bullet

				}
			}
		}
		//BULLET AND GRUNT COLLISIONS
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {
				if (fa.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected != "battle axe" ) {
						bodiesToRemove.add(fa.getBody()); //bullet
					}
				}
				if(fb.getUserData().equals("bullets")){
					if (GunSelectionScreen.weaponSelected != "battle axe") {
						bodiesToRemove.add(fb.getBody()); //bullet
					}

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
					gruntHealth.clear();
					enemies.clear();
					
					if (grunt.health <= 0) {
						bodiesToRemove.add(fa.getBody()); //grunt
						grunt.tookDamage = false;

					}


				}
				
				if(fb.getUserData().equals("grunt")){
					
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
					gruntHealth.clear();
					enemies.clear();
					
					if (grunt.health <= 0) {
						bodiesToRemove.add(fb.getBody()); //grunt
						grunt.tookDamage = false;
					}

				}
			}
		}

	}

		
	public Array <Body> getbodiesToRemove(){
		return bodiesToRemove;
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

		

