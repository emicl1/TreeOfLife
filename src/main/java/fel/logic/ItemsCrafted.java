package fel.logic;

public class ItemsCrafted extends Items{

    public String item1Name;
    public String item2Name;

    public ItemsCrafted(String name, String description, String item1Name, String item2Name) {
        super(name, description);
        this.item1Name = item1Name;
        this.item2Name = item2Name;
    }

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
