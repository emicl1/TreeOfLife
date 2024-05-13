package fel.logic;

import java.util.List;

/**
 * PlayerChar class that represents the player character in the game
 * The player character has a name, health, attack damage, and can explore locations
 * The player character can also have an inventory of items
 */
public class PlayerChar extends Character{

    private Locations[] exploredLocations;
    public List<Items> inventory;

    public PlayerChar(String name, int health, int attackDamage, boolean isAlive, Locations[] exploredLocations, List<Items> inventory) {
        super(name, health, attackDamage, isAlive);
        this.exploredLocations = exploredLocations;
        this.inventory = inventory;
    }

    public void addItem(Items item) {
        inventory.add(item);
    }

    public void removeItem(Items item) {
        inventory.remove(item);
    }
}
