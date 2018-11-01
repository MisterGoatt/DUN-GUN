package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor{

	private Vector2 velocity = new Vector2();
	private float speed = 150;
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
			velocity.y = speed;
		 	}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	//p1.translateX(speed);
        	//p1.translate(speed, 0);
        	velocity.x = speed;
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //p1.translateX(-speed);
        	//p1.translate(-speed, 0);
        	velocity.x = -speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	//p1.translate(0, -speed);
        	//p1.translateY(-speed);
        	velocity.y = -speed;
        }

        
        
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);

    }
    
    @Override
    public boolean keyDown(int keycode) {
    	switch(keycode) {
    	case Keys.W:
    		velocity.y = speed;
    		break;
    	case Keys.A:
    		velocity.x = -speed;
    		break;
    	case Keys.S:
    		velocity.y = -speed;
    		break;
    	case Keys.D:
    		velocity.x = speed;
    		break;
    	} return true;
    }



	@Override
	public boolean keyUp(int keycode) {

		switch(keycode) {
		case Keys.W:
			velocity.y = 0;
			break;
		case Keys.A:
			velocity.x = 0;
			break;
		case Keys.S:
			velocity.y = 0;
			break;
		case Keys.D:
			velocity.x = 0;
			break;
		}
		
		return true;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
}
