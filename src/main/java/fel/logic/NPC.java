package fel.logic;

public class NPC extends Character{

    public boolean isHostile;
    public boolean hasInformation;

    public NPC(String name, int health, int attackDamage, boolean isAlive,  boolean isHostile, boolean hasInformation) {
        super(name, health, attackDamage, isAlive);
        this.isHostile = isHostile;
        this.hasInformation = hasInformation;
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
