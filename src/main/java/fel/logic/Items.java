package fel.logic;

/**
 * Items class that represents the items in the game
 * The items have a name, description, and can be winning items
 */
public class Items {
    public String name;
    private String description;
    private boolean isWinningItem;

    public Items(String name, String description, boolean isWinningItem) {
        this.name = name;
        this.description = description;
        this.isWinningItem = isWinningItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * check if the game can be won with this item
     * @return the description of the item
     */
    public boolean isWinningItem() {
        return isWinningItem;
    }
}
