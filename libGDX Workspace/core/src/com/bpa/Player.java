package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite{

	private Vector2 velocity = new Vector2();
	private float speed = 3;
	private Circle boundingCircle;

    public Player(Sprite sprite) {
        
        //boundingCircle.set();
    	super(sprite);
        boundingCircle = new Circle();
    }


    @Override
    public void draw(Batch batch) {
    	update(Gdx.graphics.getDeltaTime());
    	super.draw(batch);
    }
    
    public void update(float delta) {

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        //boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			//p1.translateY(speed);
			//cam.translate(0, .02f);
			//p1.translate(0, speed); 
		 	}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	//p1.translateX(speed);
        	//p1.translate(speed, 0);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //p1.translateX(-speed);
        	//p1.translate(-speed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	//p1.translate(0, -speed);
        	//p1.translateY(-speed);
        }
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);

    }
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
}
