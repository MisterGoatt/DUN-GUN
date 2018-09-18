package com.bpa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bpa.DunGun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new DunGun(), config);
		
		config.width=1500;
		config.height=800;
		config.title = "DUN-GUN";
		config.foregroundFPS = 60;
		config.backgroundFPS = 30;
	}
}
