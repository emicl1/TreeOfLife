package fel.gui;

import com.badlogic.gdx.physics.box2d.Body;

public interface BodyDoorItemRemoveManager {
    void removeBody(Body body);
    void removeDoor(Door door);
    void removeItem(Item item);
    void addItemToInventory(Item item);
    void changeOnGround(boolean isOnGround);
    void doorsToOpen(Door door);
    void removeEnemy(Enemy enemy);
    void addFriendlyNPCsInContact(FriendlyNPC friendlyNPC);
    void removeFriendlyNPCsInContact(FriendlyNPC friendlyNPC);

}
