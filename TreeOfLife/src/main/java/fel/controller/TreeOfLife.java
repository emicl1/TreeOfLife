package fel.controller;

import fel.gui.BaseActor;


import fel.gui.GameBeta;
import fel.gui.Player;

public class TreeOfLife extends GameBeta {
	private Player player;
	private BaseActor background;

	public void initialize() {

		background = new BaseActor(0, 0, mainStage);
		background.loadTexture("/home/alex/IdeaProjects/TreeOfLife1.1/TreeOfLIfe1.1/src/main/resources/homelocation/treetrunk.jpg");
		background.setSize(1600, 1200);


		player = new Player(-380, -250, mainStage);
	}

	public void update(float dt) {

	}

}
