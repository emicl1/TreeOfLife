package tree.of.life.logic;

public class FriendlyNPC extends NPC{
    public FriendlyNPC(String name, int health, int attackDamage, boolean isAlive) {
        super(name, health, attackDamage, isAlive);
    }

    public void giveItem(Items item){
        return;
    }
}
