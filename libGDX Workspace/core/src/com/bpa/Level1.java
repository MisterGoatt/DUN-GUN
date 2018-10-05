package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Level1 implements Screen{
	final DunGun game;
	//TMapLocations level1Map;
	private OrthographicCamera cam;
	private Viewport gamePort;
	private TmxMapLoader maploader; //what loads map into game
	private TiledMap map; //references map itself
	private OrthogonalTiledMapRenderer renderer; //renders map to the screen
	
	private World world;
	private Box2DDebugRenderer b2dr;
	private Player1 player;
	private float velocity2 = 2;
	private float velocity = (float) 1.25;
	
	
	
	public Level1(final DunGun game) {
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/Level1PlaceHolder3.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/ DunGun.PPM);
		
		cam = new OrthographicCamera();		
		gamePort = new FitViewport(DunGun.V_WIDTH /DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		cam.position.set(gamePort.getWorldWidth() / DunGun.PPM, gamePort.getWorldHeight() / DunGun.PPM, 0); //centers the map to center of screen
		//cam.position.set(300, 20, 0); //moves camera to bottom left of map
		//cam.zoom -= .5; // zooms in to the map

		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		player = new Player1(world);
		
		//CREATE GROUND BODIES AND FIXTURES
		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / DunGun.PPM, (rect.getY()+ rect.getHeight() / 2) / DunGun.PPM);
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / DunGun.PPM, rect.getHeight() / 2 / DunGun.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		
		}
		cam.zoom -= .25;
	}

	 public void handleInput(float dt){
	        //control our player using immediate impulses
	       
            /*if (Gdx.input.isKeyPressed(Input.Keys.W)  && player.b2body.getLinearVelocity().y <= 2) {
                player.b2body.setLinearVelocity(new Vector2(0, 1));

            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2) {
                player.b2body.setLinearVelocity(new Vector2(1, 0));

            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2) {
                player.b2body.setLinearVelocity(new Vector2(-1, 0));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >= -2) {
                player.b2body.setLinearVelocity(new Vector2(0, -1));

            }*/
            
            
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


        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        //player.update(dt);
        
        
        
		cam.position.x = player.b2body.getPosition().x;
		cam.position.y = player.b2body.getPosition().y;
        
        cam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(cam);

}
	
	
	
	
	@Override
	public void render(float delta) {
		
        //separate our update logic from render
		update(delta);
		
		System.out.println(player.b2body.getLinearVelocity().y + " " + player.b2body.getLinearVelocity().x);
		
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        if (player.b2body.getLinearVelocity().x != 0 || player.b2body.getLinearVelocity().y != 0) {
    		if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            	if (player.b2body.getLinearVelocity().x > 0){
                    //player.b2body.applyLinearImpulse(new Vector2(0, 0), player.b2body.getWorldCenter(), true);
                    player.b2body.setLinearVelocity(new Vector2(0, 0));
                }else if (player.b2body.getLinearVelocity().x < 0 ) {
                   // player.b2body.applyLinearImpulse(new Vector2(0, 0), player.b2body.getWorldCenter(), true);
                    player.b2body.setLinearVelocity(new Vector2(0, 0));

                }
                if (player.b2body.getLinearVelocity().y > 0){
                    //player.b2body.applyLinearImpulse(new Vector2(0, 0), player.b2body.getWorldCenter(), true);
                    player.b2body.setLinearVelocity(new Vector2(0, 0));

                } else if (player.b2body.getLinearVelocity().y < 0 ){
                    //player.b2body.applyLinearImpulse(new Vector2(0, 0), player.b2body.getWorldCenter(), true);
                    player.b2body.setLinearVelocity(new Vector2(0, 0));

                }
    		}

        }

		
        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, cam.combined);
		
		
		game.batch.setProjectionMatrix(cam.combined); //MAY NEED IDK
		
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		if (mY < 100 && mX < 100 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));

		}
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
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
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
