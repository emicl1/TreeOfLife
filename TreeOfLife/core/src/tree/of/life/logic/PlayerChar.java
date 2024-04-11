package tree.of.life.logic;

public class PlayerChar extends Character{
    private Items[] items;
    private Items[] abilities;


    public PlayerChar(String name, int health, int attackDamage, boolean isAlive) {
        super(name, health, attackDamage, isAlive);
    }
}
