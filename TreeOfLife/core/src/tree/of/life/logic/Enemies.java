package tree.of.life.logic;

public class Enemies extends NPC{


    public Enemies(String name, int health, int attackDamage, boolean isAlive) {
        super(name, health, attackDamage, isAlive);
    }

    public int Attack(){
        return getAttackDamage();
    }
}
