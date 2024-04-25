package fel.logic;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;

public class Enemies extends NPC{


    public Enemies(String name, int health, int attackDamage, boolean isAlive, int speed, int x, int y, boolean isHostile, boolean hasInformation, Items[] items, String[] quests) {
        super(name, 100, attackDamage, isAlive, speed, x, y, true, hasInformation, items, quests);
    }

    public int Attack(){
        return getAttackDamage();
    }
}
