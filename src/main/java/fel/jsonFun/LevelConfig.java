package fel.jsonFun;

import java.util.List;

public class LevelConfig {
    public String name;
    public String locationName;
    public boolean isLocked;
    public String background;
    public String groundImage;
    public List<GroundConfig> ground;
    public List<SmallBugConfig> smallBugs;
    public List<BigBugConfig> bigBugs;
    public List<FriendlyNPCConfig> friendlyNPCs;
    public List<ItemConfig> items;
    public List<MoveableObjConfig> moveableObjs;
    public List<DoorConfig> doors;
    public List<ButtonConfig> buttons;
    public float gravityX;
    public float gravityY;
    public float groundWidth;
    public float groundHeight;

}
