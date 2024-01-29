package com.wurm.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setWindowedMode(dm.width / 3 * 2, dm.height / 3 * 2);
		config.setForegroundFPS(60);
		config.setTitle("Da ist der Wurm drin");
		config.setWindowIcon("img/gameIcon.png");
		new Lwjgl3Application(new Wurm(), config);
	}
}
