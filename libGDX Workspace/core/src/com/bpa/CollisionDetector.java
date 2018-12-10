package com.bpa;

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
	
	public CollisionDetector() {
		
		enemies= new Array<Body>();
		bulletsToRemove = new Array<Body>();
		gruntsToRemove = new Array<Body>();
		
		gruntHealth.clear();
		enemies.clear();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
		
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("walls") || fb.getUserData().equals("walls")) {
				if (fa.getUserData().equals("bullets")){
					bulletsToRemove.add(fa.getBody()); // bullet
				}
				else if(fb.getUserData().equals("bullets")){
					bulletsToRemove.add(fb.getBody()); // bullet
					
				}
			}
		}
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {
				if (fa.getUserData().equals("bullets")){
					bulletsToRemove.add(fa.getBody()); //bullet

					}
				else if(fb.getUserData().equals("bullets")){
					bulletsToRemove.add(fb.getBody()); //bullet

				}				
				if (fa.getUserData().equals("grunt")){

					enemies.add(fa.getBody());
					Body b = enemies.first();
					gruntHealth.add((Grunt) b.getUserData()); //casts Grunt on the physics body to get the class instance
					grunt = gruntHealth.get(0);
					
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
					//gruntsToRemove.add(fb.getBody()); //grunt

					enemies.add(fb.getBody());
					Body b = enemies.first();
					gruntHealth.add((Grunt) b.getUserData());
					grunt = gruntHealth.get(0);
//					if (GunSelectionScreen.weaponSelected == "battle axe") {
//						grunt.health -= PlayerOne.battleAxeDamage;
//					}else if (GunSelectionScreen.weaponSelected == "rifle") {
//						grunt.health -= PlayerOne.rifleDamage;
//					}else if (GunSelectionScreen.weaponSelected == "rifle") {
//						grunt.health -= PlayerOne.rifleDamage;
//					}
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

		

