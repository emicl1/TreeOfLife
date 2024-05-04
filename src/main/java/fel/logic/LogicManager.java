package fel.logic;

import fel.gui.Enemy;
import fel.gui.Player;
import fel.jsonFun.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogicManager {

    public Universe universe;
    public String[] pathsToJsons;

    public LevelConfig config;
    public PlayerConfig configPlayer;
    public String pathToPlayerJson;
    public PlayerChar player;

    public LogicManager(String[] pathsToJsons, Universe universe, String pathToPlayerJson) {
        this.pathsToJsons = pathsToJsons;
        this.universe = universe;
        this.pathToPlayerJson = pathToPlayerJson;
    }


    public void create(){
        createUniverse();
        goThroughScenesAndAddThemToLocations();
        createPlayer();
    }

    public void createUniverse(){
        List<Locations> locations = new ArrayList<>();
        universe = new Universe("TreeOfLife", locations);
    }

    public void goThroughScenesAndAddThemToLocations(){
        LevelLoader levelLoad = new LevelLoader();
        for (String path : pathsToJsons){
            config = levelLoad.loadLevel(path);
            // make a list of Enemies

            List<Enemies> smallBugs = new ArrayList<>();
            List<Enemies> bigBugs = new ArrayList<>();

            List<Items> items = new ArrayList<>();

            if (config.smallBugs != null) {
                for (SmallBugConfig bug : config.smallBugs) {
                    System.out.println("Created Small bug name: " + bug.name);
                    Enemies smallBug = new Enemies(bug.name, bug.health, bug.attackDamage, true, true, false);
                    smallBugs.add(smallBug);
                }
            }
            if (config.bigBugs != null) {
                for (BigBugConfig bug : config.bigBugs) {
                    System.out.println("Created Big bug name: " + bug.name);
                    Enemies BigBug = new Enemies(bug.name, bug.health, bug.attackDamage, true, true, false);
                    bigBugs.add(BigBug);
                }
            }
            if (config.items != null) {
                for (ItemConfig itemC : config.items) {
                    System.out.println("Created Item name: " + itemC.name);
                    Items item = new Items(itemC.name, "");
                    items.add(item);
                }
            }

            Scenes scene = new Scenes(config.name, "", smallBugs, bigBugs, items, null);

            if (universe.getLocations() == null){
                List<Scenes> scenes = new ArrayList<>();
                scenes.add(scene);
                Locations location = new Locations(config.locationName, scenes, config.isLocked);
                universe.addLocation(location);
            }else {
                boolean checkIfSceneBeenAdded = false;
                for (Locations location : universe.getLocations()){
                    if (Objects.equals(location.getName(), config.locationName)){
                        location.addScene(scene);
                        checkIfSceneBeenAdded = true;
                        break;
                    }
                }
                if (!checkIfSceneBeenAdded){
                    List<Scenes> scenes = new ArrayList<>();
                    scenes.add(scene);
                    Locations location = new Locations(config.locationName, scenes, config.isLocked);
                    universe.addLocation(location);
                }
            }
        }
    }

    public void createPlayer(){
        PlayerLoader playerLoader = new PlayerLoader();
        configPlayer = playerLoader.loadPlayer(pathToPlayerJson);
        player = new PlayerChar("Player", (int )configPlayer.health, (int)configPlayer.attackDamage, true, null, null);
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
    }

    public void playerAttack(Enemies enemy){
        enemy.takeDamage(player.getAttackDamage());
        System.out.println("Enemy health: " + enemy.getHealth());
    }

    public void enemyAttack(Enemies enemy){
        player.takeDamage(enemy.getAttackDamage());
    }

    public void playerHeal(int heal){
        player.setHealth(player.getHealth() + heal);
    }

    public void playerAddItem(Items item){
        player.addItem(item);
    }

    public void playerRemoveItem(Items item){
        player.removeItem(item);
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

    public boolean isEnemyAlive(Enemies enemy){
        return enemy.getIsAlive();
    }

    public void removeEnemy(Enemies enemy){
        enemy.setIsAlive(false);
    }

}
