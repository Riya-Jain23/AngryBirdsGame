package com.cantyouc.angrybirds;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.cantyouc.angrybirds.misc.AngryBirds;
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Angry Birds");
		config.setFullscreenMode(config.getDisplayMode());
		new Lwjgl3Application(new AngryBirds(), config);
	}
}
