package fel.logic;

import fel.jsonFun.*;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogicManager {

    public Universe universe;
    public String[] pathsToJsons;

    public LevelConfig config;
    public PlayerConfig configPlayer;
    public String pathToPlayerJson;
    public String pathToItemsToCraftJson;
    public PlayerChar player;

    public List<Items> inventory = new ArrayList<>();
    public List<ItemsCrafted> itemsToCraft = new ArrayList<>();

    public Logger logger;

    public LogicManager(String[] pathsToJsons, Universe universe, String pathToPlayerJson, String pathToItemsToCraftJson, Logger logger) {
        this.pathsToJsons = pathsToJsons;
        this.universe = universe;
        this.pathToPlayerJson = pathToPlayerJson;
        this.pathToItemsToCraftJson = pathToItemsToCraftJson;
        this.logger = logger;
    }


    public void create(){
        createUniverse();
        goThroughScenesAndAddThemToLocations();
        createPlayer();
        createItemsToCraft();
        logger.info("LogicManager created");
        unlockLocationsWithItemsInInventory();
    }

    public void createUniverse(){
        List<Locations> locations = new ArrayList<>();
        universe = new Universe("TreeOfLife", locations);
        logger.info("Universe created");
    }

    public void goThroughScenesAndAddThemToLocations(){
        LevelLoader levelLoad = new LevelLoader();

        for (String path : pathsToJsons){
            String testPathString  = path.substring(7);
            Path testPath = Paths.get("src/main/resources/saveLevels/" + testPathString);

            if (testPath.toFile().exists()){
                logger.info("loading level from file");
                path = path.substring(7);
                path = "src/main/resources/saveLevels/" + path;
            }else {
                logger.info("loading level from resources");
            }

            config = levelLoad.loadLevel(path);
            // make a list of Enemies

            List<Enemies> smallBugs = new ArrayList<>();
            List<Enemies> bigBugs = new ArrayList<>();
            List<FriendlyNPC> friendlyNPCs = new ArrayList<>();

            List<Items> items = new ArrayList<>();

            if (config.smallBugs != null) {
                for (SmallBugConfig bug : config.smallBugs) {
                    logger.info("Created Small bug name: " + bug.name);
                    Enemies smallBug = new Enemies(bug.name, bug.health, bug.attackDamage, true, true);
                    smallBugs.add(smallBug);
                }
            }
            if (config.bigBugs != null) {
                for (BigBugConfig bug : config.bigBugs) {
                    logger.info("Created Big bug name: " + bug.name);
                    Enemies BigBug = new Enemies(bug.name, bug.health, bug.attackDamage, true, true);
                    bigBugs.add(BigBug);
                }
            }
            if (config.items != null) {
                logger.debug("Items in scene: " + config.items.size());
                for (ItemConfig itemC : config.items) {
                    logger.info("Created Item name: " + itemC.name);
                    Items item = new Items(itemC.name, "", itemC.isWinningItem);
                    items.add(item);
                }
            }

            if (config.friendlyNPCs != null) {
                for (FriendlyNPCConfig npc : config.friendlyNPCs) {
                    logger.info("Created Friendly NPC name: " + npc.name);
                    FriendlyNPC friendlyNPC = new FriendlyNPC(npc.name, 100, 0, true, false, npc.itemToGive, npc.itemToReceive, npc.itemToReceive2, npc.dialogueBeforeReceiving, npc.dialogueAfterReceiving);
                    friendlyNPCs.add(friendlyNPC);
                }
            }

            Scenes scene = new Scenes(config.name, "", smallBugs, bigBugs, items, friendlyNPCs);

            if (universe.getLocations() == null){
                List<Scenes> scenes = new ArrayList<>();
                scenes.add(scene);
                Locations location = new Locations(config.locationName, scenes, config.isLocked, config.itemNeededToUnlock);
                logger.info("Created location: " + location.getName());
                logger.info("Added scene: " + scene.getName() + " to location: " + location.getName());

                universe.addLocation(location);
            }else {
                boolean checkIfSceneBeenAdded = false;
                for (Locations location : universe.getLocations()){
                    if (Objects.equals(location.getName(), config.locationName)){
                        logger.info("Added scene: " + scene.getName() + " to location: " + location.getName());
                        location.addScene(scene);
                        checkIfSceneBeenAdded = true;
                        break;
                    }
                }
                if (!checkIfSceneBeenAdded){
                    List<Scenes> scenes = new ArrayList<>();
                    scenes.add(scene);
                    Locations location = new Locations(config.locationName, scenes, config.isLocked, config.itemNeededToUnlock);
                    logger.info("Created location: " + location.getName());
                    logger.info("Added scene: " + scene.getName() + " to location: " + location.getName());
                    universe.addLocation(location);
                }
            }
        }

        printLocationAndScenesInthem();
    }

    public void createPlayer(){
        PlayerLoader playerLoader = new PlayerLoader();
        configPlayer = playerLoader.loadPlayer(pathToPlayerJson);
        inventory = createPlayerInventory(configPlayer);
        logger.info("Player created with health: " + configPlayer.health + " and attack damage: " + configPlayer.attackDamage);
        player = new PlayerChar("Player", (int )configPlayer.health, (int)configPlayer.attackDamage, true, null, inventory);
    }

    public List<Items> createPlayerInventory(PlayerConfig playerConfig){
        InventoryLoader inventoryLoader = new InventoryLoader();
        Path path = Path.of("src/main/resources/savePlayer/inventory.json");

        if (path.toFile().exists()) {
            InventoryConfig inventoryConfig = inventoryLoader.loadInventory("src/main/resources/savePlayer/inventory.json");
            List<Items> items = new ArrayList<>();
            for (ItemConfig item : inventoryConfig.items){
                Items item1 = new Items(item.name, "", item.isWinningItem);
                items.add(item1);
            }
            logger.info("Player inventory loaded from file");
            return items;
        } else {
            List<Items> items = new ArrayList<>();
            logger.info("Player inventory created");
            return items;
        }
    }

    public void createItemsToCraft(){
        ItemsToCraftLoader itemsToCraftLoader = new ItemsToCraftLoader();
        ItemsToCraftConfig itemsToCraftConfig = itemsToCraftLoader.loadItemsToCraft(pathToItemsToCraftJson);

        for (CraftItemConfig item : itemsToCraftConfig.itemsToCraft){
            logger.info("Item for crafting " + item.name + " created");
            ItemsCrafted itemToCraft = new ItemsCrafted(item.name, "", item.item1Name, item.item2Name, false);
            itemsToCraft.add(itemToCraft);
        }
    }

    public boolean isPlayerAlive(){
        return player.getIsAlive();
    }

    public void setPlayerAlive(boolean alive){
        player.setIsAlive(alive);
        player.setHealth(100);
    }

    public void setPlayerHealth(int health){
        player.setHealth(health);
    }

    public int getPlayerHealth(){
        return player.getHealth();
    }

    public void playerTakeDamage(int damage){
        player.setHealth(player.getHealth() - damage);
        logger.info("Player took " + damage + " damage.");
    }

    public void playerAttack(Enemies enemy){
        enemy.takeDamage(player.getAttackDamage(), logger);
        logger.info("Player attacked " + enemy.getName() + " for " + player.getAttackDamage() + " damage.");
    }

    public void enemyAttack(Enemies enemy){
        player.takeDamage(enemy.getAttackDamage());
    }

    public void playerHeal(int heal){
        player.setHealth(player.getHealth() + heal);
    }

    public boolean playerAddItem(Items item){
        if (item == null) {
            return false;
        }
        player.addItem(item);
        if (canItemUnlockLocation(item.getName())){
            unlockLocation(item.getName());
        }
        if (item.isWinningItem()){
            return true;
        }
        logger.info("Item " + item.getName() + " added to inventory");
        return false;
    }

    public void playerRemoveItem(Items item){
       if (item == null) {
           return;
       }
        player.removeItem(item);
        logger.info("Item " + item.getName() + " removed from inventory");

    }

    public Enemies findEnemyWithName(String name){
        for (Locations location : universe.getLocations()){
            for (Scenes scene : location.getScenes()){
                for (Enemies enemy : scene.getSmallBugs()){
                    if (Objects.equals(enemy.getName(), name)){
                        return enemy;
                    }
                }
                for (Enemies enemy : scene.getBigBugs()){
                    if (Objects.equals(enemy.getName(), name)){
                        return enemy;
                    }
                }
            }
        }
        return null;
    }

    public Items findItemWithName(String name){
        for (Locations location : universe.getLocations()){
            for (Scenes scene : location.getScenes()){
                for (Items item : scene.getItems()){
                    if (Objects.equals(item.getName(), name)){
                        logger.info("Item " + item.getName() + " found in scene: " + scene.getName());
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public Items findItemWithNameInInvetory(String name){
        for (Items item : inventory){
            if (Objects.equals(item.getName(), name)){
                logger.info("Item " + item.getName() + " found in inventory");
                return item;
            }
        }
        return null;
    }

    public Scenes findSceneWithItem(Items item){
        for (Locations location : universe.getLocations()){
            for (Scenes scene : location.getScenes()){
                for (Items item1 : scene.getItems()){
                    if (item1 == null){
                        continue;
                    }

                    if (Objects.equals(item1.getName(), item.getName())){
                        logger.info("Item" + item.getName() + " found in scene: " + scene.getName());
                        return scene;
                    }
                }
            }
        }
        return null;
    }

    public void deleteItemFromScene(Items item){
        if (item == null) {
            return ;
        }
        Scenes scene = findSceneWithItem(item);
        logger.info("Item" + item.getName() + " deleted from scene: " + scene.getName());
        scene.removeItem(item);
    }


    public boolean isEnemyAlive(Enemies enemy){
        return enemy.getIsAlive();
    }

    public void removeEnemy(Enemies enemy){
        enemy.setIsAlive(false);
    }

    public boolean canBeCrafted(Items item1, Items item2){
        for (ItemsCrafted item : itemsToCraft){

            if (item.canBeCrafted(item1, item2)){
                logger.info("item" + item.getName() + " can be crafted from" + item1.getName() + " and " + item2.getName());
                return true;
            }
        }
        return false;
    }

    public ItemsCrafted findItemToCraft(Items item1, Items item2){
        for (ItemsCrafted item : itemsToCraft){
            if (item.canBeCrafted(item1, item2)){
                return item;
            }
        }
        return null;
    }

    public void printLocationAndScenesInthem(){
        for (Locations location : universe.getLocations()){
            logger.debug("Location name: " + location.getName());
            for (Scenes scene : location.getScenes()){
                logger.debug("Scene name: " + scene.getName());
            }
        }
    }

    public void printInventory(){
        for (Items item : inventory){
            logger.debug("Item name: " + item.getName());
        }
    }

    public List<Items> getInventory() {
        return inventory;
    }

    public FriendlyNPC findFriendlyNPCWithName(String name){
        for (Locations location : universe.getLocations()){
            for (Scenes scene : location.getScenes()){
                for (FriendlyNPC npc : scene.getFriendlyNPCs()){
                    if (Objects.equals(npc.getName(), name)){
                        logger.info("NPC " + npc.getName() + " found in" + scene.getName() + " scene");
                        return npc;
                    }
                }
            }
        }
        return null;
    }


    public String getDialogFromFriendlyNPC(String name){
        FriendlyNPC npc = findFriendlyNPCWithName(name);
        String dialog = npc.getDialogue(logger);
        npc.setHasSpoken(true);
        return dialog;
    }

    public String getItemFromFriendlyNPC(String name, String itemName){
        FriendlyNPC npc = findFriendlyNPCWithName(name);
        String itemToGiveName = npc.giveItem(itemName);

        if (!itemToGiveName.equals("")){
            Items itemToGive = new Items(itemToGiveName, "", false);
            if (isItemInInventory(itemToGive.getName())){
                logger.info("Item " + itemToGive.getName() + " already in inventory");
                return "";
            }
            playerAddItem(itemToGive);
            playerRemoveItem(findItemWithNameInInvetory(itemName));
            logger.info("Item " + itemToGive.getName() + " given to player");
            return itemToGive.getName();
        }else
            return "";

    }

    public boolean isItemInInventory(String itemName){
        for (Items item : inventory){
            if (Objects.equals(item.getName(), itemName)){
                return true;
            }
        }
        return false;
    }


    public boolean canItemUnlockLocation(String itemName){
        for (Locations location : universe.getLocations()){
            if (Objects.equals(location.getName(), location.getName())){
                if (Objects.equals(location.getItemNeededToUnlock(), itemName)){
                    logger.info("Item " + itemName + " can unlock location " + location.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public void unlockLocation(String itemName){
        for (Locations location : universe.getLocations()){
            if (Objects.equals(location.getItemNeededToUnlock(), itemName)){
                location.setIsLocked(false);
                config.isLocked = false;
                logger.info("Location " + location.getName() + " unlocked");
            }
        }
    }

    public boolean isLocationLocked(String locationName){
        for (Locations location : universe.getLocations()){
            if (Objects.equals(location.getName(), locationName)){
                return location.getIsLocked();

            }
        }
        return false;
    }


    public void unlockLocationsWithItemsInInventory(){
        for (Items item : inventory){
            if (canItemUnlockLocation(item.getName())){
                unlockLocation(item.getName());
            }
        }
    }

}
