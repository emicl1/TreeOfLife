package tree.of.life.logic;

public class PlayerChar extends Character{

    private Items[] abilities;
    private Locations[] exploredLocations;
    private Items[] inventory;

    public PlayerChar(String name, int health, int attackDamage, boolean isAlive, int speed, int x, int y, Items[] items, Items[] abilities, Locations[] exploredLocations, Items[] inventory) {
        super(name, health, attackDamage, isAlive, speed, x, y, items);
        this.abilities = abilities;
        this.exploredLocations = exploredLocations;
        this.inventory = inventory;
    }

    public void addAbility(Items ability){
        return;
    }

    public void addLocation(Locations location){
        return;
    }

    public void addItem(Items item){
        return;
    }

    public void removeItem(Items item){
        return;
    }

    public void CurrentLocation(){
        return;
    }

}
