package fel.controller;

import ch.qos.logback.classic.Level;
import com.badlogic.gdx.Game;
import fel.gui.Item;
import fel.gui.MenuScreen;
import fel.gui.WinScreen;
import fel.jsonFun.*;
import fel.logic.Enemies;
import fel.logic.Items;
import fel.logic.LogicManager;
import fel.logic.Universe;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create the game and manage the game
 * both in the GUI and the logic, in MVC pattern it should
 * take the role of the controller
 */
public class MyGame extends Game {

    public LogicManager logicManager;
    public Universe universe;
    public String[] pathsToJsons = {"levels/BaseScreen.json", "levels/EastWoods1.json",
            "levels/EastWoodsBase.json", "levels/WestWoodsBase.json", "levels/WestWoodsPuzzle.json", "levels/WestWoodsFinal.json",
            "levels/EastWoodsFinal.json"};
    public String pathToPlayerJson = "player/Player.json";
    public String pathToItemsToCraftJson = "itemsToCraft/itemsToCraft.json";
    public LevelSaver levelSaver = new LevelSaver();
    public PlayerSaver playerSaver = new PlayerSaver();

    //private final boolean loggingEnabled;
    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    private Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

    public MyGame(boolean loggingEnabled) {
        if (!loggingEnabled) {
            rootLogger.setLevel(Level.OFF);
        } else {
            rootLogger.setLevel(Level.DEBUG);
        }
        rootLogger.info("Logging enabled: " + loggingEnabled);
        rootLogger.info("Logger Created :]");

    }


    @Override
    public void create() {
        rootLogger.info("Creating game");
        logicManager = new LogicManager(pathsToJsons, universe, pathToPlayerJson, pathToItemsToCraftJson, rootLogger);
        logicManager.create();
        this.setScreen(new MenuScreen(this));
        //this.setScreen(new BaseScreen(this, 15, 4, "levels/BaseScreen.json"));
    }

    public boolean isPlayerAlive() {
        return logicManager.isPlayerAlive();
    }

    public void setPlayerAlive(boolean alive) {
        logicManager.setPlayerAlive(alive);
    }

    public void setPlayerHealth(int health) {
        logicManager.setPlayerHealth(health);
    }

    public int getPlayerHealth() {
        return logicManager.getPlayerHealth();
    }


    /**
     * This method is used to take damage from enemy
     *
     * @param damage Amount of damage to take
     */
    public void playerTakeDamage(int damage) {
        logicManager.playerTakeDamage(damage);
    }

    /**
     * This method is used to make the enemy take damage
     *
     * @param enemyName Name of the enemy
     */
    public void playerAttack(String enemyName) {
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        logicManager.playerAttack(enemy);
    }

    /**
     * This method is used to check if the enemy is alive
     * by finding the enemy with the name in a scene and
     * checking if the enemy is alive
     *
     * @param enemyName Name of the enemy
     * @return boolean if the enemy is alive
     */
    public boolean isEnemyAlive(String enemyName) {
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        return logicManager.isEnemyAlive(enemy);
    }

    /**
     * This method is used to remove an enemy from the game
     *
     * @param enemyName Name of the enemy
     */
    public void removeEnemy(String enemyName) {
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        logicManager.removeEnemy(enemy);
    }

    /**
     * This method is used to add Item to the player
     *
     * @param itemName Name of the item
     */
    public void playerAddItem(String itemName) {
        rootLogger.info("Adding item to player: " + itemName);
        Items item = logicManager.findItemWithName(itemName);
        if (logicManager.playerAddItem(item)) {
            setScreen(new WinScreen(this));
        }
        logicManager.deleteItemFromScene(item);
    }

    /**
     * This method is used to check if the player can craft an item
     *
     * @param item1Name Name of the first item
     * @param item2Name Name of the second item
     * @return boolean if the player can craft the item
     */
    public boolean canCraftItem(String item1Name, String item2Name) {
        Items item1 = logicManager.findItemWithNameInInvetory(item1Name);
        Items item2 = logicManager.findItemWithNameInInvetory(item2Name);
        logicManager.printInventory();
        rootLogger.debug("in the canCraftItem method");
        if (item1 == null || item2 == null) {
            return false;
        }
        logicManager.printInventory();
        return logicManager.canBeCrafted(item1, item2);
    }

    /**
     * This method is used to craft an item in the game
     *
     * @param item1Name Name of the first item
     * @param item2Name Name of the second item
     * @return String of name of the item crafted
     */
    public String craftItem(String item1Name, String item2Name) {
        rootLogger.debug("Crafting item in MyGame item1: " + item1Name + " item2: " + item2Name);
        Items item1 = logicManager.findItemWithNameInInvetory(item1Name);
        Items item2 = logicManager.findItemWithNameInInvetory(item2Name);
        Items itemToCraft = logicManager.findItemToCraft(item1, item2);
        logicManager.playerAddItem(itemToCraft);
        logicManager.playerRemoveItem(item1);
        logicManager.playerRemoveItem(item2);
        logicManager.printInventory();
        return itemToCraft.name;
    }

    /**
     * This method is used to save the game
     *
     * @param levelConfig  LevelConfig
     * @param playerConfig PlayerConfig
     */
    public void saveGame(LevelConfig levelConfig, PlayerConfig playerConfig) {
        levelSaver.saveLevel(levelConfig, "src/main/resources/saveLevels/" + levelConfig.name + ".json");
        playerSaver.savePlayer(playerConfig, "src/main/resources/savePlayer/" + playerConfig.name + ".json");
    }

    /**
     * This method is used to get the inventory of the player
     *
     * @return List of items in the inventory
     */
    public List<Items> getInventory() {
        return logicManager.getInventory();
    }

    /**
     * This method is used to get the root logger
     *
     * @return Logger of the root logger
     */
    public Logger getRootLogger() {
        return rootLogger;
    }

    /**
     * This method is used to move the player in the game
     *
     * @param friendlyNPCName Name of the friendly NPC
     * @return String of the dialog from the friendly NPC
     */
    public String interactWithFriendlyNPC(String friendlyNPCName) {
        return logicManager.getDialogFromFriendlyNPC(friendlyNPCName);
    }

    /**
     * This method is used to get the item from the friendly NPC
     *
     * @param friendlyNPCName Name of the friendly NPC
     * @param itemToReceive   Name of the item to receive
     * @return String of the item received
     */
    public String getItemFromFriendlyNPC(String friendlyNPCName, String itemToReceive) {
        return logicManager.getItemFromFriendlyNPC(friendlyNPCName, itemToReceive);
    }

    /**
     * This method is used to check if the location is locked
     *
     * @param locationName Name of the location
     * @return boolean if the location is locked
     */
    public boolean isLocationLocked(String locationName) {
        return logicManager.isLocationLocked(locationName);
    }

    /**
     * This method is used to check if the item is in the inventory
     *
     * @param itemName Name of the item
     * @return boolean if the item is in the inventory
     */
    public boolean isItemInInventory(String itemName) {
        return logicManager.isItemInInventory(itemName);
    }

    /**
     * This method is used to print the inventory of the logic manager
     */
    public void printInventory() {
        logicManager.printInventory();
    }

    /**
     * This method is used to check if the inventory in the logic manager
     * and the inventory in the game match
     *
     * @param gameInventory List of items in the game
     * @return boolean if the inventories match
     */
    public boolean checkIfLogicAndGameInventoryMatch(List<Item> gameInventory) {
        List<Items> logicInventory = logicManager.getInventory();

        if (gameInventory.size() != logicInventory.size()) {
            return false;
        }

        for (Items item : logicInventory) {
            boolean found = false;
            for (Item gameItem : gameInventory) {
                if (item.name.equals(gameItem.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;

    }

    /**
     * This method is used to get the inventory of the logic manager
     *
     * @return List of strings of the names in the inventory
     */
    public List<String> getLogicInventory() {
        List<Items> logicInventory = logicManager.getInventory();
        List<String> LogicInventoryNames = new ArrayList<>();
        for (Items item : logicInventory) {
            LogicInventoryNames.add(item.name);
        }
        return LogicInventoryNames;
    }

    /**
     * This method is used to load the manager after the game has been created
     * Intended to be used when creating new game so that it loads the
     * manager with correct JSONs
     */
    public void loadManager() {
        logicManager = new LogicManager(pathsToJsons, universe, pathToPlayerJson, pathToItemsToCraftJson, rootLogger);
        logicManager.create();
    }


    public Object getLogicManager() {
        return logicManager;
    }
}
