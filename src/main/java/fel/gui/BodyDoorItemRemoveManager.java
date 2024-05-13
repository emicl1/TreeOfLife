package fel.gui;

import com.badlogic.gdx.physics.box2d.Body;


/**
 * Manages the interaction and lifecycle of game objects within the environment,
 * including bodies, doors, items, enemies, and NPCs.
 * This interface provides a central mechanism for adding and removing various entities
 * from the game world and managing interactions such as inventory management and
 * environmental interactions like opening doors or removing obstacles.
 * It is used for convenience with using my implementation of ContactListener.
 */
public interface BodyDoorItemRemoveManager {

    /**
     * Removes a physical body from the game.
     *
     * @param body the body to be removed
     */
    void removeBody(Body body);

    /**
     * Closes a door in the game environment.
     *
     * @param door the door to be removed
     */
    void closeDoor(Door door);

    /**
     * Removes an item from the game world.
     *
     * @param item the item to be removed
     */
    void removeItem(Item item);

    /**
     * Adds an item to the player's inventory.
     *
     * @param item the item to be added to inventory
     */
    void addItemToInventory(Item item);

    /**
     * Updates the player's 'on ground' status which affects the player's ability to jump.
     *
     * @param isOnGround true if the player is on the ground, false otherwise
     */
    void changeOnGround(boolean isOnGround);

    /**
     * Schedules a door to be opened in the game environment, allowing the player to pass through.
     *
     * @param door the door that needs to be opened
     */
    void doorsToOpen(Door door);

    /**
     * Removes an enemy from the game.
     *
     * @param enemy the enemy to remove
     */
    void removeEnemy(Enemy enemy);

    /**
     * Adds a friendly NPC to the list of NPCs currently in contact with the player.
     *
     * @param friendlyNPC the friendly NPC to add to contacts
     */
    void addFriendlyNPCsInContact(FriendlyNPC friendlyNPC);

    /**
     * Removes a friendly NPC from the list of NPCs currently in contact with the player.
     *
     * @param friendlyNPC the friendly NPC to remove from contacts
     */
    void removeFriendlyNPCsInContact(FriendlyNPC friendlyNPC);
}

