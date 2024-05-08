package fel.logic;

import java.util.ArrayList;
import java.util.List;

public class Scenes {
    private String name;
    private String description;
    private List<Enemies> smallBugs = new ArrayList<>();
    private List<Enemies> bigBugs = new ArrayList<>();

    private List<Items> items = new ArrayList<>();
    private List<FriendlyNPC> friendlyNPCs = new ArrayList<>();


    public Scenes(String name, String description, List<Enemies> smallBugs, List<Enemies>  bigBugs,  List<Items> items,List<FriendlyNPC> friendlyNPCs) {
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

    public String getDescription() {
        return description;
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


    public void setDescription(String description) {
        this.description = description;
    }


    public void setSmallBugs(List<Enemies> smallBugs) {
        this.smallBugs = smallBugs;
    }


    public void setBigBugs(List<Enemies> bigBugs) {
        this.bigBugs = bigBugs;
    }


    public void setItems(List<Items> items) {
        this.items = items;
    }


    public void setFriendlyNPCs(List<FriendlyNPC> friendlyNPCs) {
        this.friendlyNPCs = friendlyNPCs;
    }


    public void addSmallBug(Enemies smallBug) {
        smallBugs.add(smallBug);
    }


    public void addBigBug(Enemies bigBug) {
        bigBugs.add(bigBug);
    }


    public void addItem(Items item) {
        items.add(item);
    }


    public void addFriendlyNPC(FriendlyNPC friendlyNPC) {
        friendlyNPCs.add(friendlyNPC);
    }


    public void removeSmallBug(Enemies smallBug) {
        smallBugs.remove(smallBug);
    }


    public void removeBigBug(Enemies bigBug) {
        bigBugs.remove(bigBug);
    }


    public void removeItem(Items item) {
        items.remove(item);
    }


    public void removeFriendlyNPC(FriendlyNPC friendlyNPC) {
        friendlyNPCs.remove(friendlyNPC);
    }


    public void clearSmallBugs() {
        smallBugs.clear();
    }


    public void clearBigBugs() {
        bigBugs.clear();
    }


    public void clearItems() {
        items.clear();
    }


    public void clearFriendlyNPCs() {
        friendlyNPCs.clear();
    }


    public void clearAll() {
        smallBugs.clear();
        bigBugs.clear();
        items.clear();
        friendlyNPCs.clear();
    }
}
