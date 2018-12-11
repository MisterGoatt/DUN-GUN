package com.bpa;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.awt.Font;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class DunGun extends Game{
	public SpriteBatch batch;
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 1500;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100; //Pixels Per Meter
	public static final short PLAYER = 0x0001;
	public static final short BULLET = 0x0002;
	public static final short WALL = 0x0004;
	public static final short GRUNT = 0x0008;
	public static AssetManager manager;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		manager = new AssetManager();
	
		//PlayerOne
		manager.load("sprites/player1/playerRevolver.atlas", TextureAtlas.class);
		manager.load("sprites/player1/rifleAnimation.atlas", TextureAtlas.class);
		manager.load("sprites/player1/shotgun.atlas", TextureAtlas.class);
		manager.load("sprites/player1/assaultRifle.atlas", TextureAtlas.class);
		manager.load("sprites/player1/laserAnimation.atlas", TextureAtlas.class);
		manager.load("sprites/player1/axeSwingAnimation.atlas", TextureAtlas.class);
		manager.load("sound effects/running.mp3", Sound.class);
		
		//Level1
		manager.load("crosshair 1.png", Texture.class);
		manager.load("screens/Pause.jpg", Texture.class);	
		
		//Gun sound effects
		manager.load("sound effects/pistol_shot.mp3", Sound.class);
		manager.load("sound effects/rifleShot.mp3", Sound.class);
		manager.load("sound effects/shotgun2.mp3", Sound.class);
		manager.load("sound effects/laser_lance.mp3", Sound.class);
		manager.load("sound effects/assaultRifle.mp3", Sound.class);
		manager.load("sound effects/laserBlast3.mp3", Sound.class);
		manager.load("sound effects/bulletImpact.mp3", Sound.class);
		manager.load("sound effects/laserImpact.mp3", Sound.class);
		manager.load("sound effects/pelletImpact.mp3", Sound.class);
		manager.load("sound effects/pelletImpact.mp3", Sound.class);
		manager.load("sound effects/bulletBodyImpact.mp3", Sound.class);
		manager.load("sound effects/axeSwing.mp3", Sound.class);

		//Credit Screen
		manager.load("screens/ScrollingC.jpg", Texture.class);
		manager.load("music/creditsSong.mp3", Music.class);
		
		//IntroductionScreens
		manager.load("screens/ctm_placeholder.jpg", Texture.class);
		manager.load("screens/credits_placeholder.jpg", Texture.class);
		manager.load("screens/titleScreen.jpg", Texture.class);
		manager.load("screens/musicScreen.jpg", Texture.class);
		
		//MainMenu
		manager.load("music/Dun-Gun2.mp3", Music.class);
		manager.load("screens/main_menu_2.jpg", Texture.class);
		manager.load("fonts/CourierNew32.fnt", BitmapFont.class);
		manager.load("fonts/HBM Foista Regular36.fnt", BitmapFont.class);
		manager.load("fonts/HBM Foista Regular36 (Red).fnt", BitmapFont.class);
		
		//CreateBullet
		manager.load("sprites/player1/bulletAnimation.atlas", TextureAtlas.class);
		manager.load("sprites/player1/bullet.png", Texture.class);
		manager.load("sprites/player1/laserBlastAnimation.atlas", TextureAtlas.class);
		manager.load("sprites/player1/pellet.atlas", TextureAtlas.class);
		manager.load("sprites/player1/pellet.png", Texture.class);

		//GunSelectionScreen
		manager.load("screens/gun_selection.jpg", Texture.class);
		
		//Grunt
		manager.load("sprites/grunt/mutantAtkAnimation.atlas", TextureAtlas.class);
		manager.load("sprites/grunt/gruntDamaged.atlas", TextureAtlas.class);

		
		manager.finishLoading();
		this.setScreen(new IntroductionScreens(this));
	}

	@Override
	public void render () {
		super.render(); //REMEMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!			
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}
