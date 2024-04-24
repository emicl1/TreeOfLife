package fel.controller;

import fel.gui.BaseGame;
import fel.gui.MenuScreen;


public class TreeOfLife extends  BaseGame{
	public void create()
	{
		setActiveScreen( new MenuScreen() );
	}

}
