package fel.logic;

import fel.jsonFun.*;

import java.nio.file.Path;
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

    public LogicManager(String[] pathsToJsons, Universe universe, String pathToPlayerJson, String pathToItemsToCraftJson) {
        this.pathsToJsons = pathsToJsons;
        this.universe = universe;
        this.pathToPlayerJson = pathToPlayerJson;
        this.pathToItemsToCraftJson = pathToItemsToCraftJson;
    }


    public void create(){
        createUniverse();
        goThroughScenesAndAddThemToLocations();
        createPlayer();
        createItemsToCraft();
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

        printLocationAndScenesInthem();
    }

    public void createPlayer(){
        PlayerLoader playerLoader = new PlayerLoader();
        configPlayer = playerLoader.loadPlayer(pathToPlayerJson);
        inventory = createPlayerInventory(configPlayer);
        player = new PlayerChar("Player", (int )configPlayer.health, (int)configPlayer.attackDamage, true, null, inventory);
    }

    public List<Items> createPlayerInventory(PlayerConfig playerConfig){
        InventoryLoader inventoryLoader = new InventoryLoader();
        Path path = Path.of("src/main/resources/savePlayer/inventory.json");
        if (path.toFile().exists()) {
            InventoryConfig inventoryConfig = inventoryLoader.loadInventory("src/main/resources/savePlayer/inventory.json");
            List<Items> items = new ArrayList<>();
            for (ItemConfig item : inventoryConfig.items){
                Items item1 = new Items(item.name, "");
                items.add(item1);
            }
            return items;
        } else {
            List<Items> items = new ArrayList<>();
            return items;
        }
    }


    public void createItemsToCraft(){
        ItemsToCraftLoader itemsToCraftLoader = new ItemsToCraftLoader();
        ItemsToCraftConfig itemsToCraftConfig = itemsToCraftLoader.loadItemsToCraft(pathToItemsToCraftJson);
        for (CraftItemConfig item : itemsToCraftConfig.itemsToCraft){
            ItemsCrafted itemToCraft = new ItemsCrafted(item.name, "", item.item1Name, item.item2Name);
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

    public Items findItemWithName(String name){
        for (Locations location : universe.getLocations()){
            for (Scenes scene : location.getScenes()){
                for (Items item : scene.getItems()){
                    if (Objects.equals(item.getName(), name)){
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
                        return scene;
                    }
                }
            }
        }
        return null;
    }

    public void deleteItemFromScene(Items item){
        Scenes scene = findSceneWithItem(item);
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
            System.out.println("Item to craft "+ item.getName() +" Item1: "  + item1.getName() +  " Item2: " + item2.getName());
            if (item.canBeCrafted(item1, item2)){
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
            System.out.println("Location name: " + location.getName());
            for (Scenes scene : location.getScenes()){
                System.out.println("Scene name: " + scene.getName());
            }
        }
    }

    public void printInventory(){
        for (Items item : inventory){
            System.out.println("Item name: " + item.getName());
        }
    }

    public List<Items> getInventory() {
        return inventory;
    }

}
