package fel.controller;

import com.badlogic.gdx.Game;
import fel.gui.BaseScreen;
import fel.logic.Enemies;
import fel.logic.LogicManager;
import fel.logic.Universe;

public class MyGame extends Game {

    public LogicManager logicManager;
    public Universe universe;
    public String[] pathsToJsons = {"levels/BaseScreen.json", "levels/EastWoods1.json",
            "levels/EastWoodsBase.json", "levels/WestWoodsBase.json", "levels/WestWoodsPuzzle.json"};


    @Override
    public void create() {
        System.out.println("Creating game");
        logicManager = new LogicManager(pathsToJsons, universe, "player/Player.json");
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

}
