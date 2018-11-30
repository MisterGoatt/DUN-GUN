package com.bpa;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener{

	//CreateBullet collisionResult = new CreateBullet(null);
	//private Array<Body> bodiesToRemove;
	public static Array<Body> bodiesToRemove;
	
	public MyContactListener() {
		bodiesToRemove = new Array<Body>();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
		
		
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("walls") || fb.getUserData().equals("walls")) {
				bodiesToRemove.add(fb.getBody());
				//bodiesToRemove.add(fb.getBody());
			}
		}
		if (fa.getUserData().equals("bullets") || fb.getUserData().equals("bullets")) {
			if (fa.getUserData().equals("grunt") || fb.getUserData().equals("grunt")) {
				bodiesToRemove.add(fa.getBody());
				bodiesToRemove.add(fb.getBody());
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
	//checks what kind of fixtures we have --- checks between two different types of user types/objects
	//if statements check if a or b is a bullet or if a or b are a wall
	/*private boolean isBulletWallContact(Fixture a, Fixture b) {
		if (a.getUserData().equals("bullets") || b.getUserData().equals("bullets")) {
			if (a.getUserData().equals("walls") || b.getUserData().equals("walls")) {
				return true;
			}
		}return false;
		*/
		

