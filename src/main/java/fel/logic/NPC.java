package fel.logic;

public class NPC extends Character{

    public boolean isHostile;


    public NPC(String name, int health, int attackDamage, boolean isAlive,  boolean isHostile) {
        super(name, health, attackDamage, isAlive);
        this.isHostile = isHostile;
    }

}
