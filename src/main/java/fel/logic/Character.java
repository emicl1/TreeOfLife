package fel.logic;

public class Character {
    private String name;
    private int health;
    private int attackDamage;
    private boolean isAlive;

    public Character(String name, int health, int attackDamage, boolean isAlive){
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.isAlive = isAlive;
    }

    public String getName(){
        return name;
    }

    public int getHealth(){
        return health;
    }

    public int getAttackDamage(){
        return attackDamage;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public void takeDamage(int damage){
        health -= damage;
        if(health <= 0){
            isAlive = false;
        }
    }

    public void attack(Character target){
        target.takeDamage(attackDamage);
    }

    public void heal(int heal){
        health += heal;
    }

}
