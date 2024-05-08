package fel.controller;

import ch.qos.logback.classic.Level;
import com.badlogic.gdx.Game;
import fel.gui.BaseScreen;
import fel.jsonFun.*;
import fel.logic.Enemies;
import fel.logic.Items;
import fel.logic.LogicManager;
import fel.logic.Universe;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;



import java.util.List;

public class MyGame extends Game {

    public LogicManager logicManager;
    public Universe universe;
    public String[] pathsToJsons = {"levels/BaseScreen.json", "levels/EastWoods1.json",
            "levels/EastWoodsBase.json", "levels/WestWoodsBase.json", "levels/WestWoodsPuzzle.json"};
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
        this.setScreen(new BaseScreen(this, 15, 4, "levels/BaseScreen.json"));
    }

    public boolean isPlayerAlive(){
        return logicManager.isPlayerAlive();
    }

    public void setPlayerAlive(boolean alive){
        logicManager.setPlayerAlive(alive);
    }

    public void setPlayerHealth(int health){
        logicManager.setPlayerHealth(health);
    }

    public int getPlayerHealth(){
        return logicManager.getPlayerHealth();
    }

    public void playerTakeDamage(int damage){
        logicManager.playerTakeDamage(damage);
    }

    public void playerAttack(String enemyName){
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        logicManager.playerAttack(enemy);
    }

    public boolean isEnemyAlive(String enemyName){
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        return logicManager.isEnemyAlive(enemy);
    }

    public void removeEnemy(String enemyName){
        Enemies enemy = logicManager.findEnemyWithName(enemyName);
        logicManager.removeEnemy(enemy);
    }

    public void playerAddItem(String itemName){
        rootLogger.info("Adding item to player: " + itemName);
        Items item = logicManager.findItemWithName(itemName);
        logicManager.playerAddItem(item);
        logicManager.deleteItemFromScene(item);
    }

    public boolean canCraftItem(String item1Name, String item2Name){
        Items item1 = logicManager.findItemWithNameInInvetory(item1Name);
        Items item2 = logicManager.findItemWithNameInInvetory(item2Name);
        if (item1 == null || item2 == null){
            return false;
        }
        logicManager.printInventory();
        return logicManager.canBeCrafted(item1, item2);
    }


    public String craftItem(String item1Name, String item2Name){
        Items item1 = logicManager.findItemWithNameInInvetory(item1Name);
        Items item2 = logicManager.findItemWithNameInInvetory(item2Name);
        Items itemToCraft = logicManager.findItemToCraft(item1, item2);
        logicManager.playerAddItem(itemToCraft);
        logicManager.playerRemoveItem(item1);
        logicManager.playerRemoveItem(item2);
        logicManager.printInventory();
        return itemToCraft.name;
    }

    public void saveGame(LevelConfig levelConfig, PlayerConfig playerConfig){

        levelSaver.saveLevel(levelConfig, "src/main/resources/saveLevels/" + levelConfig.name + ".json");
        playerSaver.savePlayer(playerConfig, "src/main/resources/savePlayer/" + playerConfig.name + ".json");
    }

    public List<Items> getInventory(){
        return logicManager.getInventory();
    }

    public Logger getRootLogger(){
        return rootLogger;
    }

    public void interactWithFriendlyNPC(String friendlyNPCName){
        logicManager.getDialogFromFriendlyNPC(friendlyNPCName);
        System.out.println(logicManager.getDialogFromFriendlyNPC(friendlyNPCName));
    }



}
