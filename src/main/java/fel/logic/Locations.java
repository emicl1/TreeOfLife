package fel.logic;


import java.util.List;

/**
 * Locations class that represents the locations in the game
 * The locations have a name, scenes, and can be locked or unlocked
 * The locations can also have an item needed to unlock them
 */
public class Locations {
    private String name;
    private List<Scenes> scenes;
    private boolean isLocked;
    private String itemNeededToUnlock;

    public Locations(String name, List<Scenes> scenes, boolean isLocked, String itemNeededToUnlock) {
        this.name = name;
        this.scenes = scenes;
        this.isLocked = isLocked;
        this.itemNeededToUnlock = itemNeededToUnlock;
    }


    public String getName() {
        return name;
    }


    public boolean getIsLocked() {
        return isLocked;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }


    public void addScene(Scenes scene) {
        scenes.add(scene);
    }


    public List<Scenes> getScenes() {
        return scenes;
    }


    public String getItemNeededToUnlock() {
        return itemNeededToUnlock;
    }
}
