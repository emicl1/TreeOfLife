package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import  com.badlogic.gdx.utils.Array;

public class EnemyBigBug extends Enemy{

    public String [] attackPaths;
    public Animation<TextureRegion> attackAnimation;

    public EnemyBigBug(World world, String[] paths,  String []attackPath, Vector2 startPosition, float leftBound, float rightBound) {
        super(world, paths, startPosition, leftBound, rightBound, 2.0f, 1.0f);
        this.attackPaths = attackPath;

    }

    public EnemyBigBug(World world, String[] paths, String []attackPath, Vector2 startPosition, float leftBound, float rightBound, float boxWidth, float boxHeight, float speedPatrol, float speedFollow) {
        super(world, paths, startPosition, leftBound, rightBound, boxWidth, boxHeight, speedPatrol, speedFollow);
        this.attackPaths = attackPath;
    }

    public void loadAttackAnimation(){
        Array<TextureRegion> frames = new Array<>();
        for (String path : attackPaths){
            Texture texture = new Texture(path);
            TextureRegion region = new TextureRegion(texture);
            frames.add(region);
        }
        attackAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        TextureRegion currentFrame;
        if (currentState.equals("FOLLOWING")){
            currentFrame = attackAnimation.getKeyFrame(stateTime);
        } else {
            currentFrame = walkAnimation.getKeyFrame(stateTime);
        }

        float PPM = 250f; // Pixels per meter for drawing
        float playerX = body.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float playerY = body.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        float width = currentFrame.getRegionWidth() / PPM;
        float height = currentFrame.getRegionHeight() / PPM + 0.5f;

        if (!isFacingRight) {
            batch.draw(currentFrame, playerX + width, playerY, -width, height);
        } else
            batch.draw(currentFrame, playerX, playerY, width, height);

    }

}
