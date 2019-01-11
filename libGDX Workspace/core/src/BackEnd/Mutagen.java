package BackEnd;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import screens.CrashScreen;
import screens.HowToPlay;
import screens.IntroductionScreens;
import screens.Tutorial;

import java.awt.Font;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Mutagen extends Game{
	public SpriteBatch batch;
	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 1500,  V_HEIGHT = 800;
	public static final short PLAYER = 0x0001, BULLET = 0x0002, ENEMY = 0x0004, ENEMY_BULLET = 0x0008, SHOOT_OVER = 0x0016, HP_PICKUP = 0x0032, WALL = 0x0064;
	public static AssetManager manager;
	public static float musicVolume = .6f, sfxVolume = 1f, PPM = 100;
	public static Sound click;

	@Override
	public void create() { 
		//try {

			batch = new SpriteBatch();
			manager = new AssetManager();

			//load all of the graphics into memory before game starts

			//Mouse Click
			//manager.load("sound effects/click.mp3", Sound.class);
			click = Gdx.audio.newSound(Gdx.files.internal("sound effects/click.mp3"));

			//Player hit sfx
			manager.load("sound effects/impacts/hit1.ogg", Sound.class);
			manager.load("sound effects/impacts/hit2.ogg", Sound.class);
			manager.load("sound effects/impacts/hit3.ogg", Sound.class);
			manager.load("sound effects/impacts/hit4.ogg", Sound.class);

			//Human and Enemy blood
			manager.load("sprites/Blood.png", Texture.class);
			manager.load("sprites/MutantBlood.png", Texture.class);

			//PlayerOne
			manager.load("sprites/player1/playerRevolver.atlas", TextureAtlas.class);
			manager.load("sprites/player1/rifleAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/player1/shotgun.atlas", TextureAtlas.class);
			manager.load("sprites/player1/assaultRifle.atlas", TextureAtlas.class);
			manager.load("sprites/player1/laserAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/player1/axeSwingAnimation.atlas", TextureAtlas.class);
			manager.load("sound effects/running.mp3", Sound.class);
			manager.load("sprites/player1/hp.png", Texture.class);
			manager.load("sprites/player1/hpBg.png", Texture.class); //change for build path

			//PlayerTwo
			manager.load("sprites/player2/revolverP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/rifleP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/shotgunP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/assaultRifleP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/laserP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/battleAxeP2.atlas", TextureAtlas.class);
			manager.load("sprites/player2/hp2.png", Texture.class);
			manager.load("sprites/player2/hpBg2.png", Texture.class); //change for build path

			//Options Screen
			manager.load("screens/options/optionsScreenAll.jpg", Texture.class);
			manager.load("screens/options/optionsScreenMusic.jpg", Texture.class);
			manager.load("screens/options/optionsScreenSFX.jpg", Texture.class);
			manager.load("screens/options/optionsScreenNone.jpg", Texture.class);

			//Level1
			manager.load("crosshair 1.png", Texture.class);
			manager.load("axeCursor.png", Texture.class);
			manager.load("screens/Pause.jpg", Texture.class);
			manager.load("music/song3.ogg", Music.class);

			//Gun sound effects
			manager.load("sound effects/shooting/pistol_shot.mp3", Sound.class);
			manager.load("sound effects/shooting/rifleShot.mp3", Sound.class);
			manager.load("sound effects/shooting/shotgun2.mp3", Sound.class);
			manager.load("sound effects/shooting/assaultRifle.mp3", Sound.class);
			manager.load("sound effects/shooting/laserBlast3.mp3", Sound.class);
			manager.load("sound effects/shooting/axeSwing.mp3", Sound.class);
			manager.load("sound effects/impacts/bulletImpact.mp3", Sound.class);
			manager.load("sound effects/impacts/laserImpact.mp3", Sound.class);
			manager.load("sound effects/impacts/pelletImpact.mp3", Sound.class);
			manager.load("sound effects/impacts/bulletBodyImpact.mp3", Sound.class);

			//Credit Screen
			manager.load("screens/ScrollingC.jpg", Texture.class);
			manager.load("music/creditsSong.ogg", Music.class);
			manager.load("music/whistling masterpiece.mp3", Music.class);

			//Introduction Screens
			manager.load("screens/intro screens/ctm_placeholder.jpg", Texture.class);
			manager.load("screens/intro screens/introCredits.jpg", Texture.class);
			manager.load("screens/intro screens/introTitle.jpg", Texture.class);
			manager.load("screens/intro screens/musicScreen.jpg", Texture.class);

			//Difficulty Screen
			manager.load("screens/difficultyScreen/difficultyb.jpg", Texture.class);
			manager.load("screens/difficultyScreen/difficultyba.jpg", Texture.class);
			manager.load("screens/difficultyScreen/difficultyn.jpg", Texture.class);
			manager.load("screens/difficultyScreen/difficultyc.jpg", Texture.class);

			//Tutorial Screens
			manager.load("screens/tutorials/controlScreen.jpg", Texture.class);
			manager.load("screens/tutorials/tutorialOptions.jpg", Texture.class);
			manager.load("screens/tutorials/howToPlay.jpg", Texture.class);

			//Main Menu
			manager.load("music/Dun-Gun2.ogg", Music.class);
			manager.load("screens/menuScreen.jpg", Texture.class);
			manager.load("fonts/CourierNew32.fnt", BitmapFont.class);
			manager.load("fonts/inactiveMenu(36).fnt", BitmapFont.class);
			manager.load("fonts/activeMenu(36).fnt", BitmapFont.class);

			//CreateBullet
			manager.load("sprites/player1/bulletAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/player1/bullet.png", Texture.class);
			manager.load("sprites/player1/laserBlastAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/player1/pellet.atlas", TextureAtlas.class);
			manager.load("sprites/player1/pellet.png", Texture.class);

			//GunSelectionScreen
			manager.load("screens/gun_selection.jpg", Texture.class);
			manager.load("screens/p1GS.jpg", Texture.class);
			manager.load("screens/p2GS.jpg", Texture.class);
			manager.load("screens/aimStyle.jpg", Texture.class);

			//Grunt
			manager.load("sprites/grunt/mutantAtkAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/grunt/gruntDamaged.atlas", TextureAtlas.class);
			manager.load("sound effects/enemies/gruntSwoosh.mp3", Sound.class);

			//Scientist
			manager.load("sprites/scientist/scientistAtk.atlas", TextureAtlas.class);
			manager.load("sprites/scientist/scientistDamaged.atlas", TextureAtlas.class);

			manager.load("sound effects/enemies/scientistAtk.mp3", Sound.class);

			//Turret
			manager.load("sprites/turret/turretAtkAnimation.atlas", TextureAtlas.class);
			manager.load("sound effects/enemies/turretAtk.mp3", Sound.class);
			manager.load("sprites/turret/turretBulletAnimation.atlas", TextureAtlas.class);
			manager.load("sound effects/enemies/turretHit.mp3", Sound.class);

			//PlayerModeScreen
			manager.load("screens/playerMode/playerModeBlank.jpg", Texture.class);
			manager.load("screens/playerMode/playerModeBack.jpg", Texture.class);
			manager.load("screens/playerMode/playerModeSingle.jpg", Texture.class);
			manager.load("screens/playerMode/playerModeCO-OP.jpg", Texture.class);
			manager.load("fonts/backText(68).fnt", BitmapFont.class);
			manager.load("fonts/backActiveText(68).fnt", BitmapFont.class);

			manager.load("fonts/inactiveText(100).fnt", BitmapFont.class);
			manager.load("fonts/activeText(100).fnt", BitmapFont.class);

			//Health Pick-UP
			manager.load("heart.png", Texture.class);
			manager.load("sound effects/hpPickUp.ogg", Sound.class);

			//LevelCompleted Screen
			manager.load("screens/levelCompletedScreen.jpg", Texture.class);

			//Soldier
			manager.load("sprites/soldier/soldierAtkAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/soldier/pelletR.atlas", TextureAtlas.class);
			manager.load("sound effects/enemies/soldierShooting.ogg", Sound.class);

			//Mutagen
			manager.load("sprites/flayer/flayerAtkAnimation.atlas", TextureAtlas.class);
			manager.load("sprites/flayer/flayerThorn.atlas", TextureAtlas.class);
			//manager.load("sound effects/enemies/soldierShooting.ogg", Sound.class);
			
			manager.finishLoading();
			this.setScreen(new IntroductionScreens(this));
//		}catch (Exception e) {
//			this.setScreen(new CrashScreen(this));
//		}
	}

	@Override
	public void render () {
		super.render(); //REMEMBER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!			
	}

	public static void clicking() {
		long ck = click.play(sfxVolume);
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose(); //handles the disposing of all assets loaded by manager
	}
}
