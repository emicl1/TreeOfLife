package tree.of.life.logic;

public class Scenes {
    private String name;
    private String description;
    private Enemies[] enemies;
    private Items[] items;
    private FriendlyNPC[] friendlyNPCs;
    private boolean isLocked;
    private boolean isVisited;



    public Scenes(String name, String description, Enemies[] enemies, Items[] items, FriendlyNPC[] friendlyNPCs, boolean isLocked, boolean isVisited) {
        this.name = name;
        this.description = description;
        this.enemies = enemies;
        this.items = items;
        this.friendlyNPCs = friendlyNPCs;
        this.isLocked = isLocked;
        this.isVisited = isVisited;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Enemies[] getEnemies() {
        return enemies;
    }

    public Items[] getItems() {
        return items;
    }

    public FriendlyNPC[] getFriendlyNPCs() {
        return friendlyNPCs;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnemies(Enemies[] enemies) {
        this.enemies = enemies;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }

    public void setFriendlyNPCs(FriendlyNPC[] friendlyNPCs) {
        this.friendlyNPCs = friendlyNPCs;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public void addEnemy(Enemies enemy) {
        Enemies[] temp = new Enemies[enemies.length + 1];
        for (int i = 0; i < enemies.length; i++) {
            temp[i] = enemies[i];
        }
        temp[enemies.length] = enemy;
        enemies = temp;
    }

    public void addItem(Items item) {
        Items[] temp = new Items[items.length + 1];
        for (int i = 0; i < items.length; i++) {
            temp[i] = items[i];
        }
        temp[items.length] = item;
        items = temp;
    }

    public void addFriendlyNPC(FriendlyNPC friendlyNPC) {
        FriendlyNPC[] temp = new FriendlyNPC[friendlyNPCs.length + 1];
        for (int i = 0; i < friendlyNPCs.length; i++) {
            temp[i] = friendlyNPCs[i];
        }
        temp[friendlyNPCs.length] = friendlyNPC;
        friendlyNPCs = temp;
    }

    public void getEnemiesInfo() {
        for (int i = 0; i < enemies.length; i++) {
            System.out.println(enemies[i].getName());
        }
    }

    public void getItemsInfo() {
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i].getName());
        }
    }

    public void getFriendlyNPCsInfo() {
        for (int i = 0; i < friendlyNPCs.length; i++) {
            System.out.println(friendlyNPCs[i].getName());
        }
    }

    public void getScenesInfo() {
        for (int i = 0; i < enemies.length; i++) {
            System.out.println(enemies[i].getName());
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i].getName());
        }
        for (int i = 0; i < friendlyNPCs.length; i++) {
            System.out.println(friendlyNPCs[i].getName());
        }
    }






}
