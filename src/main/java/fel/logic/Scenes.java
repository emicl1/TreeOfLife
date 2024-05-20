package fel.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Scenes class that represents the scenes in the game (equals to screens in GUI)
 * The scenes have a name, description, small bugs, big bugs, items, and friendly NPCs
 * The scenes can also have items and friendly NPCs
 */
public class Scenes {
    private String name;
    private String description;
    private List<Enemies> smallBugs = new ArrayList<>();
    private List<Enemies> bigBugs = new ArrayList<>();

    private List<Items> items = new ArrayList<>();
    private List<FriendlyNPC> friendlyNPCs = new ArrayList<>();


    public Scenes(String name, String description, List<Enemies> smallBugs, List<Enemies> bigBugs, List<Items> items, List<FriendlyNPC> friendlyNPCs) {
        this.name = name;
        this.description = description;
        this.smallBugs = smallBugs;
        this.bigBugs = bigBugs;
        this.items = items;
        this.friendlyNPCs = friendlyNPCs;
    }

    public String getName() {
        return name;
    }


    public List<Enemies> getSmallBugs() {
        return smallBugs;
    }


    public List<Enemies> getBigBugs() {
        return bigBugs;
    }


    public List<Items> getItems() {
        return items;
    }


    public List<FriendlyNPC> getFriendlyNPCs() {
        return friendlyNPCs;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public void removeItem(Items item) {
        items.remove(item);
    }
}
