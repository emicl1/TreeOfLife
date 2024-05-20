package fel.logic;

import ch.qos.logback.classic.Logger;

/**
 * Enemy class that represents the enemies in the game
 */
public class Enemies extends NPC {

    public Enemies(String name, int health, int attackDamage, boolean isAlive, boolean isHostile) {
        super(name, 100, attackDamage, isAlive, true);
    }

    public void takeDamage(int damage, Logger logger) {
        logger.info(getName() + " took " + damage + " damage.");
        this.setHealth(this.getHealth() - damage);
        if (this.getHealth() <= 0) {
            this.setIsAlive(false);
        }
    }

}
