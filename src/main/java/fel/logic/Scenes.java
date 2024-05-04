package fel.logic;

import java.util.ArrayList;
import java.util.List;

public class Scenes {
    private String name;
    private String description;
    private List<Enemies> smallBugs = new ArrayList<>();
    private List<Enemies> bigBugs = new ArrayList<>();

    private List<Items> items = new ArrayList<>();
    private FriendlyNPC[] friendlyNPCs;


    public Scenes(String name, String description, List<Enemies> smallBugs, List<Enemies>  bigBugs,  List<Items> items, FriendlyNPC[] friendlyNPCs) {
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

    public FriendlyNPC[] getFriendlyNPCs() {
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

    public void setFriendlyNPCs(FriendlyNPC[] friendlyNPCs) {
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
        FriendlyNPC[] temp = new FriendlyNPC[friendlyNPCs.length + 1];
        for (int i = 0; i < friendlyNPCs.length; i++) {
            temp[i] = friendlyNPCs[i];
        }
        temp[friendlyNPCs.length] = friendlyNPC;
        friendlyNPCs = temp;
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
        FriendlyNPC[] temp = new FriendlyNPC[friendlyNPCs.length - 1];
        int j = 0;
        for (int i = 0; i < friendlyNPCs.length; i++) {
            if (friendlyNPCs[i] != friendlyNPC) {
                temp[j] = friendlyNPCs[i];
                j++;
            }
        }
        friendlyNPCs = temp;
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
        friendlyNPCs = new FriendlyNPC[0];
    }

    public void clearAll() {
        smallBugs.clear();
        bigBugs.clear();
        items.clear();
        friendlyNPCs = new FriendlyNPC[0];
    }


}
