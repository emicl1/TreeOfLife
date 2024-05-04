package fel.gui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Sword {
    public float x;
    public float y;
    public float width;
    public float height;

    public Body playerBody;
    public World world;
    public Body swordBody;

    public Sword(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void createAndAttachSword(World world, Body playerBody, float swordWidth, float swordHeight, float playerHeight) {
        this.world = world;
        this.playerBody = playerBody;
        BodyDef swordBodyDef = new BodyDef();
        swordBodyDef.type = BodyDef.BodyType.DynamicBody;
        swordBodyDef.position.set(playerBody.getPosition().x, playerBody.getPosition().y + playerHeight); // Spawn above the player

        swordBody = world.createBody(swordBodyDef);

        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(swordWidth, swordHeight); // Adjust size as necessary

        FixtureDef swordFixtureDef = new FixtureDef();
        swordFixtureDef.shape = swordShape;
        swordFixtureDef.density = 0.5f;
        swordFixtureDef.isSensor = true; // Make it a sensor if it should not cause physical collisions

        swordBody.createFixture(swordFixtureDef).setUserData(this);
        swordShape.dispose();

        attachSwordToPlayer(swordBody, playerHeight);
    }

    private void attachSwordToPlayer(Body swordBody, float playerHeight) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = playerBody;
        jointDef.bodyB = swordBody;
        jointDef.collideConnected = false;
        jointDef.localAnchorA.set(0, playerHeight*0.7f); // Attach point on the player
        jointDef.localAnchorB.set(0, 0); // Center of the sword
        jointDef.enableLimit = true;
        jointDef.lowerAngle =  MathUtils.PI; // -90 degrees in radians
        jointDef.upperAngle =  MathUtils.PI; // 90 degrees in radians

        world.createJoint(jointDef);
    }

    public void update() {
        float targetAngle = MathUtils.PI * 190 / 180; // Target angle in radians (190 degrees)
        float currentAngle = swordBody.getAngle() % (2 * MathUtils.PI); // Normalize angle

        if (Math.abs(currentAngle) < targetAngle) {
            swordBody.applyTorque(-110f, true); // Apply torque to rotate
        } else {
            swordBody.setAngularVelocity(0); // Stop rotating once the target angle is reached
        }
    }

    public void dispose() {
        world.destroyBody(swordBody);
    }

    public void setActive(boolean active) {
        swordBody.setActive(active);
    }

    public boolean isActive() {
        return swordBody.isActive();
    }

    public void setInActive() {
        swordBody.setActive(false);
    }

    public float getAngle() {
        return swordBody.getAngle();
    }

}
