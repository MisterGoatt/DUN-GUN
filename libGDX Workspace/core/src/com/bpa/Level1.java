package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Level1 implements Screen{
	final DunGun game;
	//TMapLocations level1Map;
	private OrthographicCamera cam;
	private Viewport viewport;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; //references map itself
	private OrthogonalTiledMapRenderer mapRenderer; //renders map to the screen
	
	
	
	public Level1(final DunGun game) {
		
		
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/Level1PlaceHolder4.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ DunGun.PPM);
		
		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH / DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		//viewport.apply();
		//cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		//cam.position.set(300, 20, 0); //moves camera to bottom left of map
		//viewport.apply();

		//cam.zoom -= .45;
	}
	

	 public void handleInput(float dt){
	        //control our player using immediate impulses
/*
	        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
	            player.b2body.setLinearVelocity(new Vector2(0, velocity2));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
	            player.b2body.setLinearVelocity(new Vector2(velocity2, 0));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
	            player.b2body.setLinearVelocity(new Vector2(- velocity2, 0));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
	            player.b2body.setLinearVelocity(new Vector2(0, -velocity2));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
	        	player.b2body.setLinearVelocity(new Vector2(-velocity, velocity));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
	        	player.b2body.setLinearVelocity(new Vector2(velocity, velocity));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
	        	player.b2body.setLinearVelocity(new Vector2(-velocity, -velocity));
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
	        	player.b2body.setLinearVelocity(new Vector2(velocity, -velocity));
	        }
		 
		 */
		 	if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
    			cam.zoom -= .01;

    			//cam.translate(Gdx.input.getDeltaX() * (-1), Gdx.input.getDeltaY());
    		}
    		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
    			cam.zoom += .01;
    			
    		}
	 }

	
	
	
	
	public void update(float dt){
        //handle user input first
        handleInput(dt);
        
		/*cam.position.x = player.b2body.getPosition().x;
		cam.position.y = player.b2body.getPosition().y;
        */

        
        //tell our renderer to draw only what our camera can see in our game world.
        mapRenderer.setView(cam);
        cam.update();

}
	
	
	
	
	@Override
	public void render(float delta) {
		
        //separate our update logic from render
		update(delta);
		
		//System.out.println(player.b2body.getLinearVelocity().y + " " + player.b2body.getLinearVelocity().x);
		
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render our game map
        mapRenderer.render();

		game.batch.setProjectionMatrix(cam.combined); 
		game.batch.begin();
		game.batch.end();
		
		
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		if (mY < 100 && mX < 100 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));

		}
		
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
