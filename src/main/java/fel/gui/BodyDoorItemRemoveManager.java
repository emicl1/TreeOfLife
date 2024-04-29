package fel.gui;

import com.badlogic.gdx.physics.box2d.Body;

public interface BodyDoorItemRemoveManager {
    void removeBody(Body body);
    void removeDoor(Door door);
    void removeItem(Item item);
    void changeOnGround(boolean isOnGround);
    void doorsToOpen(Door door);
}