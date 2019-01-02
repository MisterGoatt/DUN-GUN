package com.bpa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import screens.Mutagen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Mutagen(), config);
		
		config.width = 1500;
		config.height= 800;
		config.title = "MUTAGEN";
		config.vSyncEnabled = true;

	}
}
