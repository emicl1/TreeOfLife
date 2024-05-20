package fel.logic;

/**
 * NPC class that represents the NPC in the game
 * The NPC has a name, health, attack damage, and can be hostile or not
 * The NPC is meant to be inherited by other classes
 */
public class NPC extends Character {

    public boolean isHostile;

    public NPC(String name, int health, int attackDamage, boolean isAlive, boolean isHostile) {
        super(name, health, attackDamage, isAlive);
        this.isHostile = isHostile;
    }
}
