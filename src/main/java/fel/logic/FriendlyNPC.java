package fel.logic;

import ch.qos.logback.classic.Logger;

import java.util.List;

/**
 * FriendlyNPC class that represents the friendly NPCs in the game
 * The friendly NPCs can give the player items and have dialogue
 */
public class FriendlyNPC extends NPC {
    private String itemToGive;
    private String itemToReceive;
    private List<String> dialogueBeforeReceiving;
    private List<String> dialogueAfterReceiving;
    private boolean hasGivenItem = false;
    private boolean hasSpoken = false;


    public FriendlyNPC(String name, int health, int attackDamage, boolean isAlive, boolean isHostile, String itemToGive, String itemToReceive, String itemToReceive2, List<String> dialogueBeforeReceiving, List<String> dialogueAfterReceiving) {
        super(name, 100, 0, isAlive, false);
        this.itemToGive = itemToGive;
        this.itemToReceive = itemToReceive;
        this.dialogueBeforeReceiving = dialogueBeforeReceiving;
        this.dialogueAfterReceiving = dialogueAfterReceiving;

    }

    /**
     * Method to give the player an item
     *
     * @param item the item to give
     * @return the item to give
     */
    public String giveItem(String item) {
        if (itemToReceive.equals(item)) {
            hasGivenItem = true;
            return itemToGive;
        } else {
            return "";
        }
    }

    public void setItemToGive(String itemToGive) {
        this.itemToGive = itemToGive;
    }

    public void setItemToReceive(String itemToReceive) {
        this.itemToReceive = itemToReceive;
    }

    public void setHasSpoken(boolean hasSpoken) {
        this.hasSpoken = hasSpoken;
    }

    /**
     * Method to get the dialogue of the NPC
     * it has several dialogue options
     * depending on if the player has spoken to the NPC before
     * and if the player has received the item
     * the first dialogue option is always the same
     * and the rest are random
     *
     * @param logger the logger
     * @return the dialogue of the NPC
     */
    public String getDialogue(Logger logger) {
        if (hasSpoken) {
            if (hasGivenItem) {
                // choose random dialogue from dialogueAfterReceiving
                logger.info("Dialogue after receiving item");
                return dialogueAfterReceiving.get((int) (Math.random() * dialogueAfterReceiving.size()));
            } else {
                // choose random dialogue from dialogueBeforeReceiving
                logger.info("Dialogue before receiving item here");
                return dialogueBeforeReceiving.get((int) (Math.random() * dialogueBeforeReceiving.size()));
            }
        } else {
            if (hasGivenItem) {
                logger.info("Dialogue after receiving item");
                return dialogueAfterReceiving.getFirst();
            } else {
                logger.info("Dialogue before receiving item");
                return dialogueBeforeReceiving.getFirst();
            }
        }
    }
}
