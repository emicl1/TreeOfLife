package fel.logic;

import java.util.ArrayList;
import java.util.List;

public class Locations{
    private String name;
    private List<Scenes>  scenes;
    private boolean isLocked;
    private boolean isVisited;
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


    public boolean getIsVisited() {
        return isVisited;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }


    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
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

    public void setItemNeededToUnlock(String itemNeededToUnlock) {
        this.itemNeededToUnlock = itemNeededToUnlock;
    }


    public void unlockLocation(String item) {
        if (item.equals(itemNeededToUnlock)) {
            isLocked = false;
        }
    }
}
