package tree.of.life;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class TreeOfLife extends GameBeta {
	private Player player;
	private BaseActor background;

	public void initialize() {

		background = new BaseActor(0, 0, mainStage);
		background.loadTexture("assets/homelocation/treetrunk.jpg");
		background.setSize(1600, 1200);


		player = new Player(-380, -250, mainStage);
	}

	public void update(float dt) {

	}

}
