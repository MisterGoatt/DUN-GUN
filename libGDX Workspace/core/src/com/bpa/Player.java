package com.bpa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Sprite implements InputProcessor{

	private Vector2 velocity = new Vector2();
	private float speed = 3 ;
	private Circle boundingCircle;
	private float angle;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        
        //boundingCircle.set();
    	super(sprite);
    	this.collisionLayer = collisionLayer;
    	this.setOrigin(16, 10);

    }


    @Override
    public void draw(Batch batch) {
    	update(Gdx.graphics.getDeltaTime()); //allows movement of player
    	super.draw(batch);
    }
    
    public void update(float delta) {

    	//save old position
    	float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
    	boolean collisionX = false, collisionY = false;
    	
    	//move on x 
        setX(getX() + velocity.x);
        if (velocity.x < 0) {
        	//top left
        	collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int)((getY() + getHeight()) / tileHeight))
        			.getTile().getProperties().containsKey("blocked");
        	
        	//middle left
        	
        	if(!collisionX)
	        	collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int)((getY() + getHeight() / 2) / tileHeight))
	        			.getTile().getProperties().containsKey("blocked");
        	//bottom left
        	if(!collisionX)
        		collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight))
        			.getTile().getProperties().containsKey("blocked");
        }else if (velocity.x > 0) {
        	//top right
        	collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
        			.getTile().getProperties().containsKey("blocked");
        	//middle right
        	if(!collisionX)
        		collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight))
        		.getTile().getProperties().containsKey("blocked");

        	//bottom right
    		if(!collisionX)
	        	collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight))
	    		.getTile().getProperties().containsKey("blocked");
        }
        //react to X collision
        if (collisionX) {
        	setX(oldX);
        	velocity.x = 0;
        }
        
        //move on y
        setY(getY() + velocity.y );

        if (velocity.y < 0) { 
        	//bottom left
        	collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight))
    		.getTile().getProperties().containsKey("blocked");
        	
        	//bottom middle
	        if(!collisionY)	
	        	collisionY = collisionLayer.getCell((int) ((getX() +getWidth() / 2 ) / tileWidth), (int) (getY() / tileHeight))
	    			.getTile().getProperties().containsKey("blocked");
	        
        	//bottom right
        	if (!collisionY)
	        	collisionY = collisionLayer.getCell((int) ((getX() + getWidth() ) / tileWidth), (int) (getY() / tileHeight))
	    			.getTile().getProperties().containsKey("blocked");
        }else if (velocity.y > 0) {
        	//top left
        	collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight))
    		.getTile().getProperties().containsKey("blocked");
        	
        	//top middle
	        if(!collisionY)	
	        	collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2 ) / tileWidth), (int) (getY() + getHeight() / tileHeight))
	    			.getTile().getProperties().containsKey("blocked");
	        
        	//top right
        	if (!collisionY)
	        	collisionY = collisionLayer.getCell((int) ((getX() + getWidth() ) / tileWidth), (int) (getY() / tileHeight))
	    			.getTile().getProperties().containsKey("blocked");
        }


        //react to Y collision
        if(collisionY) {
        	setY(oldY);
        	velocity.y = 0;
        }
        
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

		float mouseX = Level1.mouse_position.x; //grabs cam.unproject x vector value
		float mouseY = Level1.mouse_position.y; //grabs cam.unproject y vector value
		
		angle = MathUtils.atan2(mouseY - getY(), mouseX - getX()) * MathUtils.radDeg;

        angle = angle - 90;
	    if (angle < 0) {
	    	angle += 360 ;
	    }
       
       setRotation(angle);
		
        return true;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
