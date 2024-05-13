package fel.logic;

/**
 * ItemsCrafted class that represents the items that can be crafted in the game
 * The items have a name, description, and can be crafted by combining two other items
 */
public class ItemsCrafted extends Items{

    private String item1Name;
    private String item2Name;

    public ItemsCrafted(String name, String description, String item1Name, String item2Name, boolean isWinningItem) {
        super(name, description,   isWinningItem);
        this.item1Name = item1Name;
        this.item2Name = item2Name;
    }

    /**
     * Checks if the two items can be crafted together
     * to create this item
     * @param item1 the first item
     * @param item2 the second item
     * @return true if the two items can be crafted together, false otherwise
     */
    public boolean canBeCrafted(Items item1, Items item2){
        if(item1.getName().equals(item1Name) && item2.getName().equals(item2Name)){
            return true;
        }
        if(item1.getName().equals(item2Name) && item2.getName().equals(item1Name)){
            return true;
        }
        return false;
    }

}
