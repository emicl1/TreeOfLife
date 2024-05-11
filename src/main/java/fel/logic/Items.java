package fel.logic;

public class Items {
    public String name;
    public String description;
    public boolean isWinningItem;

    public Items(String name, String description, boolean isWinningItem) {
        this.name = name;
        this.description = description;
        this.isWinningItem = isWinningItem;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void getItemInfo() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }

    public boolean isWinningItem() {
        return isWinningItem;
    }

}
