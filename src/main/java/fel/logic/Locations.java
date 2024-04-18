package fel.logic;

public class Locations{
    private String name;
    private Scenes[] scenes;
    private boolean isLocked;
    private boolean isVisited;
    private String description;

    public Locations(String name, Scenes[] scenes, boolean isLocked, boolean isVisited, String description) {
        this.name = name;
        this.scenes = scenes;
        this.isLocked = isLocked;
        this.isVisited = isVisited;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Scenes[] getScenes() {
        return scenes;
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

    public void setScenes(Scenes[] scenes) {
        this.scenes = scenes;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public void addScene(Scenes scene) {
        Scenes[] temp = new Scenes[scenes.length + 1];
        for (int i = 0; i < scenes.length; i++) {
            temp[i] = scenes[i];
        }
        temp[scenes.length] = scene;
        scenes = temp;
    }

    public void getScenesInfo() {
        for (int i = 0; i < scenes.length; i++) {
            System.out.println(scenes[i].getName());
        }
    }

    public void getSceneDescription() {
        for (int i = 0; i < scenes.length; i++) {
            System.out.println(scenes[i].getDescription());
        }
    }






}
