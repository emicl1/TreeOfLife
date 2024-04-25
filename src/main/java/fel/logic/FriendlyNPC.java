package fel.logic;

public class FriendlyNPC extends NPC{
    public FriendlyNPC(String name, int health, int attackDamage, boolean isAlive, int speed, int x, int y, boolean isHostile, boolean hasInformation, Items[] items, String[] quests) {
        super(name, 100, 0, isAlive, speed, x, y, false, hasInformation, items, quests);
    }

    public void giveItem(Items item){
        return;
    }
}
