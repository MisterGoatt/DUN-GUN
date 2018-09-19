package com.bpa;

<<<<<<< HEAD
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DunGun extends Game{
	public SpriteBatch batch;
	Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	private long startTime = System.currentTimeMillis();
	private long counter;
	public BitmapFont framerate; //font for frame rate display
	

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titlescreen_placeholder.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));
		this.setScreen(new MainMenu(this));

	}

	@Override
	
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1); //background in RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears the screen
		
		System.out.println((System.currentTimeMillis() - startTime) / 1000);
		counter = (System.currentTimeMillis() - startTime) / 1000;
		batch.begin(); //begins sprite batch

		//draws publisher screen, credit screen, and then title screen
		if (counter <= 2) {
			System.out.println("A");
			batch.draw(publisherScreen, 0, 0);
		}else if (counter <=  4 && counter > 2) {
			System.out.println("b");
			batch.draw(creditScreen, 0, 0);
		}else if (counter >= 4.1){
			System.out.println("c");
			batch.draw(titleScreen, 0, 0);
		}
		int f = Gdx.graphics.getFramesPerSecond(); // grabs frames per second
		String frames = Integer.toString(f); //converts frames per second to a string
		framerate.draw(batch, frames, 5, 785); //displays frames per second as text in top left
		batch.end(); //what actually displays everything to the screen
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		framerate.dispose();
=======
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;


public class DunGun extends Game{
	public SpriteBatch batch;
	/*Texture publisherScreen;
	Texture creditScreen;
	Texture titleScreen;
	private long startTime = System.currentTimeMillis();
	private long counter;
	BitmapFont framerate; //font for frame rate display*/


	@Override
	public void create () {
		batch = new SpriteBatch();
		/*publisherScreen = new Texture("screens/ctm_placeholder.jpg");
		creditScreen = new Texture("screens/credits_placeholder.jpg");
		titleScreen = new Texture("screens/titlescreen_placeholder.jpg");
		framerate = new BitmapFont(Gdx.files.internal("fonts/CourierNew32.fnt"));*/
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {

		super.render(); //REMEMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		/*publisherScreen.dispose();
		creditScreen.dispose();
		titleScreen.dispose();
		framerate.dispose();*/
>>>>>>> refs/remotes/origin/RichardH
		
	}
}
