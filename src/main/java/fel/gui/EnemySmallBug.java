package fel.gui;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an enemy small bug in the game in GUI
 * He is just a small bug that moves left and right
 */
public class EnemySmallBug extends Enemy {

    public EnemySmallBug(World world, String name, String[] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary) {
        super(world, name, pathToAnimations, startPosition, leftBoundary, rightBoundary);
    }
}

