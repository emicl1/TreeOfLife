package fel.logic;

public class Items {
    public String name;
    public String description;

    public Items(String name, String description) {
        this.name = name;
        this.description = description;
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

}
