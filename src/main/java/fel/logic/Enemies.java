package fel.logic;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;

public class Enemies extends NPC{


    public Enemies(String name, int health, int attackDamage, boolean isAlive,  boolean isHostile, boolean hasInformation) {
        super(name, 100, attackDamage, isAlive, true, hasInformation);
    }

    public int Attack(){
        return getAttackDamage();
    }

    public void takeDamage(int damage){
        this.setHealth(this.getHealth() - damage);
        if (this.getHealth() <= 0){
            this.setIsAlive(false);
        }
    }

}
