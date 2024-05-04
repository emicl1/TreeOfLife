package fel.logic;

import java.util.ArrayList;
import java.util.List;

public class Locations{
    private String name;
    private List<Scenes>  scenes;
    private boolean isLocked;
    private boolean isVisited;
    private String description;

    public Locations(String name, List<Scenes> scenes, boolean isLocked) {
        this.name = name;
        this.scenes = scenes;
        this.isLocked = isLocked;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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


}
