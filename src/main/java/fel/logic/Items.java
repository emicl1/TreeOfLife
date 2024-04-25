package fel.logic;

public class Items {
    public String name;
    public String description;
    public boolean isAbility;

    public Items(String name, String description, boolean isAbility) {
        this.name = name;
        this.description = description;
        this.isAbility = isAbility;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsAbility() {
        return isAbility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsAbility(boolean isAbility) {
        this.isAbility = isAbility;
    }

    public void useItem() {
        if (isAbility) {
            System.out.println("You used " + name + "!");
        } else {
            System.out.println("You can't use " + name + "!");
        }
    }

    public void getItemInfo() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        if (isAbility) {
            System.out.println("This item is an ability.");
        } else {
            System.out.println("This item is not an ability.");
        }
    }

    public void getItemsInfo() {
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        if (isAbility) {
            System.out.println("This item is an ability.");
        } else {
            System.out.println("This item is not an ability.");
        }
    }




}
