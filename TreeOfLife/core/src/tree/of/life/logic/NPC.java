package tree.of.life.logic;

public class NPC extends Character{

    public boolean isHostile;
    public boolean hasInformation;
    public String[] quests;

    public NPC(String name, int health, int attackDamage, boolean isAlive, int speed, int x, int y, boolean isHostile, boolean hasInformation, Items[] items, String[] quests) {
        super(name, health, attackDamage, isAlive, speed, x, y, items);
        this.isHostile = isHostile;
        this.hasInformation = hasInformation;
        this.quests = quests;
    }

    public boolean getIsHostile(){
        return isHostile;
    }

    public boolean getHasInformation(){
        return hasInformation;
    }

    public void interact( PlayerChar player ){
        return;
    }

    public void giveItem(Items item){
        return;
    }

    public void giveQuest(String quest){
        return;
    }
}
