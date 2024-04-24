package fel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fel.controller.TreeOfLife;
import fel.gui.BaseScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//Game myGame = new TreeOfLife();
		SimpleGame myGame = new SimpleGame();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Tree of Life");
		config.setWindowedMode(2000,1600);
		new Lwjgl3Application(myGame, config);

	}
}
