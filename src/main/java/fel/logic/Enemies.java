package fel.logic;

import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;

public class Enemies extends NPC{


    public Enemies(String name, int health, int attackDamage, boolean isAlive,  boolean isHostile) {
        super(name, 100, attackDamage, isAlive, true);
    }

    public int Attack(){
        return getAttackDamage();
    }

    public void takeDamage(int damage, Logger logger){
        logger.info(getName() + " took " + damage + " damage.");
        this.setHealth(this.getHealth() - damage);
        if (this.getHealth() <= 0){
            this.setIsAlive(false);
        }
    }

}
