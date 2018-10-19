package com.bpa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	TextureAtlas textureAtlas;
	Sprite sprite;
	SpriteBatch batch;
	TextureRegion textureRegion;
	
	
	
	public Level1(final DunGun game) {
		
		
		this.game = game;

		maploader = new TmxMapLoader();
		map = maploader.load("tileMaps/Level1/Level1PlaceHolder4.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ DunGun.PPM);
		
		cam = new OrthographicCamera();		
		viewport = new FitViewport(DunGun.V_WIDTH / DunGun.PPM, DunGun.V_HEIGHT / DunGun.PPM, cam); //fits view port to match map's dimensions (in this case 320x320) and scales. Adds black bars to adjust
		//viewport.apply();
		       //cam.position.set((viewport.getWorldWidth() / DunGun.PPM) / 2, viewport.getWorldHeight() / DunGun.PPM, 0);

		//viewport.apply();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/p1.atlas"));
		textureRegion = textureAtlas.findRegion("p1");
		sprite = new Sprite(textureRegion);
		sprite.setPosition((viewport.getWorldWidth() / 2) * DunGun.PPM, //places the player at the center of the camera
				(viewport.getWorldHeight() / 2) * DunGun.PPM);

		cam.zoom -= .45;
	}
	
	@Override
	public void render(float delta) {
		
		//clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		float pW = sprite.getTexture().getWidth() / cam.zoom; //Keeps player scaled
		float pH = sprite.getTexture().getHeight() / cam.zoom; // ^
		sprite.setSize(pW, pH); // Keeps players size matched regardless of zoom
		
	 	if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			cam.zoom -= .01;

			//cam.translate(Gdx.input.getDeltaX() * (-1), Gdx.input.getDeltaY());
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			cam.zoom += .01;
			
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			//sprite.translateY(pSpeed);
			cam.translate(0, .02f);
		 	}
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	//sprite.translateX(pSpeed);
        	cam.translate(.02f, 0);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //sprite.translateX(-pSpeed);
        	cam.translate(-.02f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	cam.translate(0, -.02f);
        	//sprite.translateY(-pSpeed);
        }
        

        System.out.println(sprite.getY());
        cam.update();

        //render our game map
        mapRenderer.render();
		game.batch.setProjectionMatrix(cam.combined); 
        mapRenderer.setView(cam);
        batch.begin();
        sprite.draw(batch);
        batch.end();

		/*
		//mouse x and y
		int mX = Gdx.input.getX();
		int mY = Gdx.graphics.getHeight() - Gdx.input.getY();
		if (mY < 100 && mX < 100 && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(new MainMenu(game));

		}*/
		
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
