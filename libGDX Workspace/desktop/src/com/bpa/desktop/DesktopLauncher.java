package com.bpa.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import BackEnd.Mutagen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1500;
		config.height= 800;
		config.title = "MUTAGEN";
		config.vSyncEnabled = true;
		config.useHDPI = false;
		config.resizable = false;
		//config.fullscreen = true;
		config.addIcon("MIcon.png", FileType.Internal);
		new LwjglApplication(new Mutagen(), config);

	}
}
