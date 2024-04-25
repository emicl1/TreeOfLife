package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;

public class EnemySmallBug extends Enemy{

    public EnemySmallBug(World world, String[] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary) {
        super(world, pathToAnimations, startPosition, leftBoundary, rightBoundary);
    }
}

