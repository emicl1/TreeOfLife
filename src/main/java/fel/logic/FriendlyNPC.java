package fel.logic;


import ch.qos.logback.classic.Logger;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class FriendlyNPC extends NPC{
    public String itemToGive;
    public String itemToReceive;
    public List<String> dialogueBeforeReceiving;
    public List<String> dialogueAfterReceiving;
    public boolean hasGivenItem = false;
    public boolean hasSpoken = false;


    public FriendlyNPC(String name, int health, int attackDamage, boolean isAlive, boolean isHostile, String itemToGive, String itemToReceive, String itemToReceive2, List<String> dialogueBeforeReceiving, List<String> dialogueAfterReceiving) {
        super(name, 100, 0, isAlive, false);
        this.itemToGive = itemToGive;
        this.itemToReceive = itemToReceive;
        this.dialogueBeforeReceiving = dialogueBeforeReceiving;
        this.dialogueAfterReceiving = dialogueAfterReceiving;

    }

    public String giveItem( String item){
        if (itemToReceive.equals(item)){
            hasGivenItem = true;
            return itemToGive;
        }
        else{
            return "";
        }
    }

    public boolean hasGivenItem(){
        return hasGivenItem;
    }

    public boolean hasSpoken(){
        return hasSpoken;
    }

    public void setHasGivenItem(boolean hasGivenItem){
        this.hasGivenItem = hasGivenItem;
    }

    public void setHasSpoken(boolean hasSpoken){
        this.hasSpoken = hasSpoken;
    }


    public String getDialogue(Logger logger){
        if(hasSpoken){
            if(hasGivenItem){
                // choose random dialogue from dialogueAfterReceiving
                logger.info("Dialogue after receiving item");
                return dialogueAfterReceiving.get((int)(Math.random() * dialogueAfterReceiving.size()));
            }
            else{
                // choose random dialogue from dialogueBeforeReceiving
                logger.info("Dialogue before receiving item here");
                return dialogueBeforeReceiving.get((int)(Math.random() * dialogueBeforeReceiving.size()));
            }
        }else {
            if (hasGivenItem){
                logger.info("Dialogue after receiving item");
                return dialogueAfterReceiving.getFirst();
            }
            else{
                logger.info("Dialogue before receiving item");
                return dialogueBeforeReceiving.getFirst();
            }
        }
    }





}
