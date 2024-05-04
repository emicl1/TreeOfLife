package fel.logic;

public class FriendlyNPC extends NPC{
    public FriendlyNPC(String name, int health, int attackDamage, boolean isAlive, boolean isHostile, boolean hasInformation) {
        super(name, 100, 0, isAlive, false, hasInformation);
    }

    public void giveItem(Items item){
        return;
    }
}
