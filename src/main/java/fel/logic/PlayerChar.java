package fel.logic;


import java.util.List;
import fel.jsonFun.ItemConfig;

public class PlayerChar extends Character{

    private Locations[] exploredLocations;
    public List<Items> inventory;

    public PlayerChar(String name, int health, int attackDamage, boolean isAlive, Locations[] exploredLocations, List<Items> inventory) {
        super(name, health, attackDamage, isAlive);
        this.exploredLocations = exploredLocations;
        this.inventory = inventory;
    }

    public Locations[] getExploredLocations() {
        return exploredLocations;
    }

    public void setExploredLocations(Locations[] exploredLocations) {
        this.exploredLocations = exploredLocations;
    }

    public List<Items> getInventory() {
        return inventory;
    }

    public void setInventory(List<Items> inventory) {
        this.inventory = inventory;
    }

    public void addLocation(Locations location) {
        if (exploredLocations == null) {
            exploredLocations = new Locations[1];
            exploredLocations[0] = location;
        } else {
            Locations[] temp = new Locations[exploredLocations.length + 1];
            for (int i = 0; i < exploredLocations.length; i++) {
                temp[i] = exploredLocations[i];
            }
            temp[exploredLocations.length] = location;
            exploredLocations = temp;
        }
    }

    public void addItem(Items item) {
        inventory.add(item);
    }

    public void removeItem(Items item) {
        inventory.remove(item);
    }

    public void takeDamage(int damage){
        this.setHealth(this.getHealth() - damage);
        if (this.getHealth() <= 0){
            this.setIsAlive(false);
        }
    }

}
