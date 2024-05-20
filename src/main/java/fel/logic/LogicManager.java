package fel.logic;

import fel.jsonFun.*;
import ch.qos.logback.classic.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogicManager {

    private Universe universe;
    private final String[] pathsToJsons;

    private LevelConfig config;
    private PlayerConfig configPlayer;
    private final String pathToPlayerJson;
    private final String pathToItemsToCraftJson;
    public PlayerChar player;

    private List<Items> inventory = new ArrayList<>();
    private List<ItemsCrafted> itemsToCraft = new ArrayList<>();

    private Logger logger;

    public LogicManager(String[] pathsToJsons, Universe universe, String pathToPlayerJson, String pathToItemsToCraftJson, Logger logger) {
        this.pathsToJsons = pathsToJsons;
        this.universe = universe;
        this.pathToPlayerJson = pathToPlayerJson;
        this.pathToItemsToCraftJson = pathToItemsToCraftJson;
        this.logger = logger;
    }

    /**
     * Method to create the LogicManager
     */
    public void create() {
        createUniverse();
        goThroughScenesAndAddThemToLocations();
        createPlayer();
        createItemsToCraft();
        logger.info("LogicManager created");
        unlockLocationsWithItemsInInventory();
    }


    private void createUniverse() {
        List<Locations> locations = new ArrayList<>();
        universe = new Universe("TreeOfLife", locations);
        logger.info("Universe created");
    }


    private void goThroughScenesAndAddThemToLocations() {
        LevelLoader levelLoad = new LevelLoader();

        for (String path : pathsToJsons) {
            String testPathString = path.substring(7);
            Path testPath = Paths.get("src/main/resources/saveLevels/" + testPathString);

            if (testPath.toFile().exists()) {
                logger.info("loading level from file");
                path = path.substring(7);
                path = "src/main/resources/saveLevels/" + path;
            } else {
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

            if (universe.getLocations() == null) {
                List<Scenes> scenes = new ArrayList<>();
                scenes.add(scene);
                Locations location = new Locations(config.locationName, scenes, config.isLocked, config.itemNeededToUnlock);
                logger.info("Created location: " + location.getName());
                logger.info("Added scene: " + scene.getName() + " to location: " + location.getName());

                universe.addLocation(location);
            } else {
                boolean checkIfSceneBeenAdded = false;
                for (Locations location : universe.getLocations()) {
                    if (Objects.equals(location.getName(), config.locationName)) {
                        logger.info("Added scene: " + scene.getName() + " to location: " + location.getName());
                        location.addScene(scene);
                        checkIfSceneBeenAdded = true;
                        break;
                    }
                }
                if (!checkIfSceneBeenAdded) {
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

    private void createPlayer() {
        PlayerLoader playerLoader = new PlayerLoader();
        configPlayer = playerLoader.loadPlayer(pathToPlayerJson);
        inventory = createPlayerInventory(configPlayer);
        logger.info("Player created with health: " + configPlayer.health + " and attack damage: " + configPlayer.attackDamage);
        player = new PlayerChar("Player", (int) configPlayer.health, (int) configPlayer.attackDamage, true, null, inventory);
    }

    private List<Items> createPlayerInventory(PlayerConfig playerConfig) {
        InventoryLoader inventoryLoader = new InventoryLoader();
        Path path = Path.of("src/main/resources/savePlayer/inventory.json");

        if (path.toFile().exists()) {
            InventoryConfig inventoryConfig = inventoryLoader.loadInventory("src/main/resources/savePlayer/inventory.json");
            List<Items> items = new ArrayList<>();
            for (ItemConfig item : inventoryConfig.items) {
                Items item1 = new Items(item.name, "", item.isWinningItem);
                items.add(item1);
            }
            logger.info("Player inventory loaded from file in logicManager and printing inventory");
            printInventory();
            return items;
        } else {
            List<Items> items = new ArrayList<>();
            logger.info("Player inventory created in LogicManager and printing inventory");
            printInventory();
            return items;
        }

    }

    private void createItemsToCraft() {
        ItemsToCraftLoader itemsToCraftLoader = new ItemsToCraftLoader();
        ItemsToCraftConfig itemsToCraftConfig = itemsToCraftLoader.loadItemsToCraft(pathToItemsToCraftJson);

        for (CraftItemConfig item : itemsToCraftConfig.itemsToCraft) {
            logger.info("Item for crafting " + item.name + " created");
            ItemsCrafted itemToCraft = new ItemsCrafted(item.name, "", item.item1Name, item.item2Name, false);
            itemsToCraft.add(itemToCraft);
        }
    }

    /**
     * Method to check if player is alive
     *
     * @return boolean
     */
    public boolean isPlayerAlive() {
        return player.getIsAlive();
    }

    public void setPlayerAlive(boolean alive) {
        player.setIsAlive(alive);
        player.setHealth(100);
    }

    public void setPlayerHealth(int health) {
        player.setHealth(health);
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    /**
     * Method to take damage from enemy
     *
     * @param damage int
     */
    public void playerTakeDamage(int damage) {
        player.setHealth(player.getHealth() - damage);
        logger.info("Player took " + damage + " damage.");
    }

    /**
     * Method to attack enemy
     *
     * @param enemy Enemies
     */
    public void playerAttack(Enemies enemy) {
        enemy.takeDamage(player.getAttackDamage(), logger);
        logger.info("Player attacked " + enemy.getName() + " for " + player.getAttackDamage() + " damage.");
    }

    /**
     * Method to add item to player inventory
     * also checks if location can be unlocked
     * or if item is winning item
     *
     * @param item Items
     * @return boolean
     */
    public boolean playerAddItem(Items item) {
        if (item == null) {
            return false;
        }
        player.addItem(item);
        if (canItemUnlockLocation(item.getName())) {
            unlockLocation(item.getName());
        }
        if (item.isWinningItem()) {
            return true;
        }
        logger.info("Item " + item.getName() + " added to inventory");
        return false;
    }

    /**
     * Method to remove item from player inventory
     *
     * @param item Items
     */
    public void playerRemoveItem(Items item) {
        if (item == null) {
            return;
        }
        player.removeItem(item);
        logger.info("Item " + item.getName() + " removed from inventory");

    }

    /**
     * Go through all locations and scenes to find enemy with name
     *
     * @param name String
     * @return Enemies
     */
    public Enemies findEnemyWithName(String name) {
        for (Locations location : universe.getLocations()) {
            for (Scenes scene : location.getScenes()) {
                for (Enemies enemy : scene.getSmallBugs()) {
                    if (Objects.equals(enemy.getName(), name)) {
                        return enemy;
                    }
                }
                for (Enemies enemy : scene.getBigBugs()) {
                    if (Objects.equals(enemy.getName(), name)) {
                        return enemy;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Go through all locations and scenes to find item with name
     *
     * @param name String
     * @return Items
     */
    public Items findItemWithName(String name) {
        for (Locations location : universe.getLocations()) {
            for (Scenes scene : location.getScenes()) {
                for (Items item : scene.getItems()) {
                    if (Objects.equals(item.getName(), name)) {
                        logger.info("Item " + item.getName() + " found in scene: " + scene.getName());
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Got through player's inventory to find item with name
     *
     * @param name String
     * @return Items
     */
    public Items findItemWithNameInInvetory(String name) {
        for (Items item : inventory) {
            if (Objects.equals(item.getName(), name)) {
                logger.info("Item " + item.getName() + " found in inventory");
                return item;
            }
        }
        return null;
    }

    private Scenes findSceneWithItem(Items item) {
        for (Locations location : universe.getLocations()) {
            for (Scenes scene : location.getScenes()) {
                for (Items item1 : scene.getItems()) {
                    if (item1 == null) {
                        continue;
                    }

                    if (Objects.equals(item1.getName(), item.getName())) {
                        logger.info("Item" + item.getName() + " found in scene: " + scene.getName());
                        return scene;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method to delete item from scene
     *
     * @param item Items
     */
    public void deleteItemFromScene(Items item) {
        if (item == null) {
            return;
        }
        Scenes scene = findSceneWithItem(item);
        logger.info("Item" + item.getName() + " deleted from scene: " + scene.getName());
        scene.removeItem(item);
    }

    /**
     * Method to check if enemy is alive
     *
     * @param enemy Enemies
     * @return boolean
     */
    public boolean isEnemyAlive(Enemies enemy) {
        return enemy.getIsAlive();
    }

    /**
     * Method to remove enemy from the game
     *
     * @param enemy Enemies
     */
    public void removeEnemy(Enemies enemy) {
        enemy.setIsAlive(false);
    }

    /**
     * Method to check if item can be crafted
     * by going through all itemsToCraft
     *
     * @param item1 Items
     * @param item2 Items
     * @return boolean
     */
    public boolean canBeCrafted(Items item1, Items item2) {
        for (ItemsCrafted item : itemsToCraft) {

            if (item.canBeCrafted(item1, item2)) {
                logger.info("item " + item.getName() + " can be crafted from " + item1.getName() + " and " + item2.getName());
                return true;
            }
        }
        return false;
    }

    /**
     * Method to find item that can be crafted
     *
     * @param item1 Items
     * @param item2 Items
     * @return ItemsCrafted
     */
    public ItemsCrafted findItemToCraft(Items item1, Items item2) {
        for (ItemsCrafted item : itemsToCraft) {
            if (item.canBeCrafted(item1, item2)) {
                return item;
            }
        }
        return null;
    }


    private void printLocationAndScenesInthem() {
        for (Locations location : universe.getLocations()) {
            logger.debug("Location name: " + location.getName());
            for (Scenes scene : location.getScenes()) {
                logger.debug("Scene name: " + scene.getName());
            }
        }
    }

    /**
     * Method to print inventory in console
     * this is used for debugging
     */
    public void printInventory() {
        for (Items item : inventory) {
            logger.debug("Item name: " + item.getName());
        }
    }

    /**
     * Method to get inventory
     *
     * @return List of Items
     */
    public List<Items> getInventory() {
        return inventory;
    }

    public void setInventory(List<Items> inventory) {
        this.inventory = inventory;
    }

    /**
     * Method to find friendly NPC with name
     *
     * @param name String
     * @return FriendlyNPC
     */
    public FriendlyNPC findFriendlyNPCWithName(String name) {
        for (Locations location : universe.getLocations()) {
            for (Scenes scene : location.getScenes()) {
                for (FriendlyNPC npc : scene.getFriendlyNPCs()) {
                    if (Objects.equals(npc.getName(), name)) {
                        logger.info("NPC " + npc.getName() + " found in" + scene.getName() + " scene");
                        return npc;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method to get dialog from friendly NPC
     *
     * @param name String
     * @return String
     */
    public String getDialogFromFriendlyNPC(String name) {
        FriendlyNPC npc = findFriendlyNPCWithName(name);
        assert npc != null;
        String dialog = npc.getDialogue(logger);
        npc.setHasSpoken(true);
        return dialog;
    }

    /**
     * Method to get item from friendly NPC
     * handles removing and adding items to player inventory
     *
     * @param name     String
     * @param itemName String
     * @return String
     */
    public String getItemFromFriendlyNPC(String name, String itemName) {
        FriendlyNPC npc = findFriendlyNPCWithName(name);
        assert npc != null;
        String itemToGiveName = npc.giveItem(itemName);

        if (!itemToGiveName.isEmpty()) {
            Items itemToGive = new Items(itemToGiveName, "", false);
            if (isItemInInventory(itemToGive.getName())) {
                logger.info("Item " + itemToGive.getName() + " already in inventory");
                return "";
            }
            playerAddItem(itemToGive);
            playerRemoveItem(findItemWithNameInInvetory(itemName));
            logger.info("Item " + itemToGive.getName() + " given to player");
            return itemToGive.getName();
        } else
            return "";
    }

    /**
     * Method to check if item is in inventory
     *
     * @param itemName String
     * @return boolean
     */
    public boolean isItemInInventory(String itemName) {
        for (Items item : inventory) {
            if (Objects.equals(item.getName(), itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if item can unlock location
     * by going through all locations
     * it is public for testing purposes
     *
     * @param itemName String
     * @return boolean
     */
    public boolean canItemUnlockLocation(String itemName) {
        for (Locations location : universe.getLocations()) {
            if (Objects.equals(location.getItemNeededToUnlock(), itemName)) {
                logger.info("Item " + itemName + " can unlock location " + location.getName());
                return true;
            }
        }
        return false;
    }


    /**
     * Method to unlock location
     *
     * @param itemName String
     */
    public void unlockLocation(String itemName) {
        for (Locations location : universe.getLocations()) {
            if (Objects.equals(location.getItemNeededToUnlock(), itemName)) {
                location.setIsLocked(false);
                config.isLocked = false;
                logger.info("Location " + location.getName() + " unlocked");
            }
        }
    }

    /**
     * Method to check if location is locked
     *
     * @param locationName String
     * @return boolean
     */
    public boolean isLocationLocked(String locationName) {
        for (Locations location : universe.getLocations()) {
            if (Objects.equals(location.getName(), locationName)) {
                return location.getIsLocked();

            }
        }
        return false;
    }

    /**
     * Method to unlock location
     * with items in inventory
     * this is used when loading game to be sure
     * it is public for testing purposes
     */
    public void unlockLocationsWithItemsInInventory() {
        for (Items item : inventory) {
            if (canItemUnlockLocation(item.getName())) {
                unlockLocation(item.getName());
            }
        }
    }

    //SETTERS AND GETTERS for testing purposes mainly
    public Logger getLogger() {
        return logger;
    }


    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    public void setUniverse(Universe universe) {
        this.universe = universe;
    }


    public void setConfig(LevelConfig config) {
        this.config = config;
    }


    public void setItemsToCraft(List<ItemsCrafted> itemsToCraft) {
        this.itemsToCraft = itemsToCraft;
    }
}
