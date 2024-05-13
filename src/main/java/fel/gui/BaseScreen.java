package fel.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fel.controller.MyGame;
import fel.jsonFun.*;

import org.slf4j.Logger;


import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

/**
 * Base screen for the game
 * This class is the base screen for the game and contains the main game GUI
 * all other screens should extend this class
 */

public class BaseScreen implements Screen, BodyDoorItemRemoveManager {
    public MyGame game;

    public float x;
    public float y;

    public World world;
    private Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Player player;
    public SpriteBatch batch;
    private Sprite backgroundSprite;
    private Texture backgroundTexture;
    private ContactListener listener;


    private List<Sprite> groundSprites = new ArrayList<>();
    private List<MoveableObj> moveableObjs = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Item> itemsToPutInInventory = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private InventoryConfig inventoryConfig = new InventoryConfig();
    private List<Button> buttons = new ArrayList<>();
    private List<EnemySmallBug> smallBugs;
    private List<EnemyBigBug> bigBugs;
    private List<FriendlyNPC> friendlyNPCs = new ArrayList<>();

    private boolean facingRight = true;

    private float stateTime;

    private boolean isOnGround;

    private boolean isMoving = false;

    private LevelConfig config;

    private String jsonPath;

    private Array<Body> bodiesToDestroy = new Array<Body>();
    private Array<Door> doorsToClose = new Array<Door>();
    private Array<Door> doorsToOpen = new Array<Door>();
    private Array<FriendlyNPC> friendlyNPCsInContact = new Array<FriendlyNPC>();

    private String currentDialogue = "";
    private boolean showDialogue = false;

    public Logger log;

    public Stage uiStage;
    private Skin uiSkin = new Skin();
    private Label label;


    public BaseScreen(MyGame game, float x, float y, String jsonPath) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.jsonPath = jsonPath;
        createLogger();
    }

    private void createLogger() {
        log = game.getRootLogger();
    }

    private void createUi() {
        // Assuming uiStage and uiSkin are initialized elsewhere or here
        uiStage = new Stage(new ScreenViewport());
        uiSkin.add("default-font", new BitmapFont(Gdx.files.internal("myfont.fnt")));
        FileHandle skinFile = Gdx.files.internal("uiskin.json");
        uiSkin.load(skinFile);

        // Create a label for displaying text
        label = new Label("", uiSkin);
        label.setWrap(true); // Enable text wrapping
        label.setWidth(200);
        label.setPosition(400, 200);
        uiStage.addActor(label);

        // Set the input processor to the stage to handle UI input
        Gdx.input.setInputProcessor(uiStage);
    }

    private void createFriendlyNPCs(LevelConfig config) {
        if (config.friendlyNPCs == null) {
            log.info("No friendly NPCs");
            return;
        }

        for (FriendlyNPCConfig friendlyNPC : config.friendlyNPCs) {
            FriendlyNPC newFriendlyNPC = new FriendlyNPC(friendlyNPC, log);
            newFriendlyNPC.createBody(world);
            newFriendlyNPC.loadAnimations();
            friendlyNPCs.add(newFriendlyNPC);
            log.info("Friendly NPC created: " + friendlyNPC.name);
        }
    }


    private void createEnemies(){
        if (config.smallBugs != null){
            for (SmallBugConfig smallBugConfig : config.smallBugs){
                String[] paths = smallBugConfig.paths;
                Vector2 startPosition = new Vector2(smallBugConfig.x, smallBugConfig.y);
                EnemySmallBug enemy = new EnemySmallBug(world, smallBugConfig.name, paths, startPosition, smallBugConfig.leftBound, smallBugConfig.rightBound);
                enemy.loadAnimationEnemy();
                smallBugs.add(enemy);
                log.info("Small bug created " + smallBugConfig.name);
            }
        }
        if (config.bigBugs != null){
            for (BigBugConfig bigBugConfig : config.bigBugs){
                String[] paths = bigBugConfig.paths;
                String[] attackPaths = bigBugConfig.attackPaths;
                Vector2 startPosition = new Vector2(bigBugConfig.x, bigBugConfig.y);
                EnemyBigBug enemy = new EnemyBigBug(world, bigBugConfig.name, paths, attackPaths, startPosition, bigBugConfig.leftBound, bigBugConfig.rightBound, 2.2f, 1.5f, 1.5f, 5f);
                enemy.loadAnimationEnemy();
                enemy.loadAttackAnimation();
                bigBugs.add(enemy);
                log.info("Big bug created " + bigBugConfig.name);
            }
        }
    }


    private void createMoveableObj(LevelConfig config){
        if (config.moveableObjs == null){
            log.info("No moveable objects");
            return;
        }
        for (MoveableObjConfig moveableObj : config.moveableObjs){
            MoveableObj newMoveableObj = new MoveableObj(moveableObj, log);
            newMoveableObj.loadSprite();
            newMoveableObj.createBody(world);
            moveableObjs.add(newMoveableObj);
            log.info("Moveable object created " + moveableObj.path);
        }
    }


    private void createItems(LevelConfig config){
        if (config.items == null){
            log.info("No items");
            return;
        }

        for (ItemConfig item : config.items){
            Item newItem = new Item(item, log);
            newItem.loadSprite();
            newItem.createBody(world);
            items.add(newItem);
            log.info("Item created " + item.name);
        }
    }


    private void createButtons(LevelConfig config) {
        if (config.buttons == null) {
            log.info("No buttons");
            return;
        }

        for (ButtonConfig button : config.buttons) {
            Button newButton = new Button(button, log);
            newButton.loadSprite();
            newButton.createBody(world);
            buttons.add(newButton);
            log.info("Button created " + button.path);
        }
    }


    private void createBackgroundAndGround(LevelConfig config){
        createBackground(config.background);
        createGround(config.groundImage);
        for (GroundConfig ground : config.ground){
            createGroundFromRectangularShape(world, config.groundImage, ground.x, ground.y, ground.x + ground.width, ground.y + ground.height);
        }
    }


    private void createBackground(String path){
        backgroundTexture = new Texture(path);
        backgroundSprite = new Sprite(backgroundTexture);
    }


    private void createWorld(float gravityX, float gravityY){
        stateTime = 0f;  // Accumulate elapsed time

        world = new World(new Vector2(gravityX, gravityY), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        listener = new MyContactListener(this, game);
        world.setContactListener(listener);
    }


    private void createCamera() {
        float w = 30;
        float h = 30 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

        camera = new OrthographicCamera(w, h);
        viewport = new FitViewport(w, h, camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        viewport.apply();
        camera.update();
        log.info("Created camera with w:" + w + "h:" + h);
    }


    private void createGround(String path) {
        float groundWidth = config.groundWidth;
        float groundHeight = config.groundHeight;
        int numGroundSegments = 13;

        Texture groundTexture = new Texture(path);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(groundWidth / 2 , groundHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        for (int i = 0; i < numGroundSegments; i++) {
            // Set the position for each ground body
            bodyDef.position.set(i * groundWidth, 1f);

            // Create the ground body
            Body groundBody = world.createBody(bodyDef);
            groundBody.createFixture(fixtureDef);
            Fixture groundFixture = groundBody.createFixture(fixtureDef);
            groundFixture.setUserData("ground");

            // Create and configure the sprite for each ground body
            Sprite groundSprite = new Sprite(groundTexture);
            groundSprite.setSize(groundWidth, groundHeight);
            groundSprite.setPosition(groundBody.getPosition().x - groundWidth / 2, groundBody.getPosition().y - groundHeight / 2);
            groundSprites.add(groundSprite);
        }

        shape.dispose();

    }


    private void createGroundFromRectangularShape(World world, String path, float x1, float y1, float x2, float y2) {
        float groundWidth = config.groundWidth;  // Width of each ground segment
        float groundHeight = config.groundHeight; // Height of each ground segment
        Texture groundTexture = new Texture(path);

        int numGroundSegmentsX = (int) Math.ceil((x2 - x1) / groundWidth);
        int numGroundSegmentsY = (int) Math.ceil((y2 - y1) / groundHeight);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(groundWidth / 2, groundHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        for (int i = 0; i < numGroundSegmentsX; i++) {
            for (int j = 0; j < numGroundSegmentsY; j++) {
                float posX = x1 + i * groundWidth + groundWidth;
                float posY = y1 + j * groundHeight + groundHeight / 2;
                bodyDef.position.set(posX, posY);

                Body groundBody = world.createBody(bodyDef);
                groundBody.createFixture(fixtureDef).setUserData("ground");

                Sprite groundSprite = new Sprite(groundTexture);
                groundSprite.setSize(groundWidth, groundHeight);
                groundSprite.setPosition(posX - groundWidth / 2, posY - groundHeight / 2);
                groundSprites.add(groundSprite);
            }
        }

        shape.dispose();
    }


    private void createWorldBounds(float worldWidth, float worldHeight) {
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        // Horizontal boundary (top and bottom)
        EdgeShape horizontalEdge = new EdgeShape();
        horizontalEdge.set(new Vector2(0, 0), new Vector2(worldWidth, 0));

        // Vertical boundary (left and right)
        EdgeShape verticalEdge = new EdgeShape();
        verticalEdge.set(new Vector2(0, 0), new Vector2(0, worldHeight));

        // Create four boundaries
        FixtureDef fd = new FixtureDef();
        fd.shape = horizontalEdge;
        fd.density = 0;
        fd.friction = 0.3f;

        // Bottom boundary
        bd.position.set(0, 0);
        Body bottomBoundary = world.createBody(bd);
        bottomBoundary.createFixture(fd);

        // Top boundary
        bd.position.set(0, worldHeight);
        Body topBoundary = world.createBody(bd);
        topBoundary.createFixture(fd);

        fd.shape = verticalEdge;

        // Left boundary
        bd.position.set(0, 0);
        Body leftBoundary = world.createBody(bd);
        leftBoundary.createFixture(fd);

        // Right boundary
        bd.position.set(worldWidth, 0);
        Body rightBoundary = world.createBody(bd);
        rightBoundary.createFixture(fd);

        // Clean up
        horizontalEdge.dispose();
        verticalEdge.dispose();
    }

    /**
     * Function to create player
     * If there is a save file, the player is created from the save file
     * If there is no save file, the player is created from the default file
     * Rewrite this function in the subclass to create a player with different parameters
     */
    public void createPlayer(){
        Path path = Paths.get("src/main/resources/savePlayer/Player.json");

        if (path.toFile().exists()) {
            log.info("Player created from loaded file");
            player = new Player(world, x, y, "src/main/resources/savePlayer/Player.json", log);
        } else {
            log.info("Player created");
            player = new Player(world, x, y, "player/Player.json", log);
        }
    }


    private void createPlayersInventory(){
        Path path = Paths.get("src/main/resources/savePlayer/inventory.json");

        if (path.toFile().exists()) {
            InventoryLoader inventoryLoader = new InventoryLoader();
            inventoryConfig = inventoryLoader.loadInventory("src/main/resources/savePlayer/inventory.json");
            for (ItemConfig item : inventoryConfig.items){
                Item newItem = new Item(item, log);
                newItem.loadSprite();
                newItem.createBody(world);
                newItem.setInactive();
                inventory.add(newItem);
                if (Objects.equals(newItem.name, "sword")) {
                    player.hasSword = true;
                }
            }
        } else {
            inventory = new ArrayList<>();
        }
        log.info("Inventory created");
    }


    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
            facingRight = true;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
            facingRight = false;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && isOnGround) {
            player.jump();
            isOnGround = false;  // Prevent further jumps until grounded again
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.playerBody.applyForceToCenter(0, -10, true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ) {
            if (player.hasSword() ){
                if (player.sword == null) {
                    player.initSword();
                }
                player.attack();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            log.debug("Crafting and printing inventory");
            game.printInventory();
            handleCrafting();
            checkIfInventoryMatches();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            handleSaving();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            handleInteraction();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new HelpScreen(game, this));
        }
    }

    /**
     * Check if the inventory matches the logic inventory and add items to the inventory if they don't match
     * This is a debugging method to ensure that the inventory matches the logic inventory
     * the logical inventory is the superior inventory
     */
    private void checkIfInventoryMatches(){
        List<Item> toRemove = new ArrayList<>();
        if (!game.checkIfLogicAndGameInventoryMatch(inventory)){
            List<String> logicInventory = game.getLogicInventory();
            log.debug("Inventory does not match logic inventory");
            toRemove.addAll(inventory);
            for (String item : logicInventory){
                Item newItem = createNewItemFromCrafting(item);
                if (newItem != null){
                    itemsToPutInInventory.add(newItem);
                }
            }

        }
        inventory.removeAll(toRemove);
    }


    private void handleSaving(){
        game.saveGame(config, player.config);
        InventorySaver inventorySaver = new InventorySaver();
        if (inventoryConfig == null){
            inventoryConfig = new InventoryConfig();
        }
        if (inventoryConfig.items == null){
            inventoryConfig.items = new ArrayList<>();
        }

        inventoryConfig.items.clear();
        for (Item item : inventory){
            ItemConfig itemConfig = new ItemConfig();
            itemConfig.path = item.path;
            itemConfig.name = item.name;
            itemConfig.x = item.x;
            itemConfig.y = item.y;
            itemConfig.width = item.width;
            itemConfig.height = item.height;
            itemConfig.isCollectable = item.isCollectable;
            inventoryConfig.items.add(itemConfig);
        }
        inventorySaver.saveInventory(inventoryConfig, "src/main/resources/savePlayer/inventory.json");
    }


    private void handleCrafting() {
        List<Item> toRemove = new ArrayList<>();
        List<Item> toAdd = new ArrayList<>();

        // Collect items to remove and craft new ones
        for (Item item1 : inventory) {
            for (Item item2 : inventory) {
                log.debug("Item1: {} Item2: {}", item1.getName(), item2.getName());
                if (item1 == null || item2 == null || item1 == item2) {
                    continue;
                }
                if (game.canCraftItem(item1.getName(), item2.getName())) {
                    toRemove.add(item1);
                    toRemove.add(item2);

                    String craftedItemName = game.craftItem(item1.getName(), item2.getName());
                    Item item = createNewItemFromCrafting(craftedItemName);
                    if (item != null) {
                        toAdd.add(item);
                    }

                }
            }
        }

        inventory.removeAll(toRemove);
        inventory.addAll(toAdd);
        handleSaving();
    }


    private void handleInteraction() {  // I need this function :(
        List<Item> toRemove = new ArrayList<>();
        List<Item> toAdd = new ArrayList<>();

        for (FriendlyNPC friendlyNPC : friendlyNPCsInContact) {
            for (Item item : inventory) {
                String itemInQuestion = game.getItemFromFriendlyNPC(friendlyNPC.getName(), item.getName());
                if (!itemInQuestion.equals("")) {
                    log.debug("Item in question: {}", itemInQuestion);
                    Item newItem = createNewItemFromCrafting(itemInQuestion);
                    if (newItem != null) {
                        toAdd.add(newItem);
                        toRemove.add(item);
                        if (Objects.equals(newItem.name, "sword")) {
                            player.hasSword = true;
                        }
                    }

                }
            }
            String dialog = game.interactWithFriendlyNPC(friendlyNPC.getName());
            currentDialogue = dialog;
            showDialogue = true;
            System.out.println("x " + friendlyNPC.x + " y " + friendlyNPC.y);
            System.out.println("dialog: " + dialog);
            // Position of label set relative to the friendlyNPC, so it appears above them slightly to the left
            label.setPosition( (float)((friendlyNPC.x/30) * Gdx.graphics.getWidth() - 2.5* (friendlyNPC.width/30) * Gdx.graphics.getWidth()), (float) (((friendlyNPC.y/20) * Gdx.graphics.getHeight()) +2.5*(friendlyNPC.height/20* Gdx.graphics.getHeight())));
        }
        inventory.removeAll(toRemove);
        inventory.addAll(toAdd);
        handleSaving();
    }


    private Item createNewItemFromCrafting(String itemName) {
        ItemsToCraftLoader itemsToCraftLoader = new ItemsToCraftLoader();
        ItemsToCraftConfig itemsToCraftConfig = itemsToCraftLoader.loadItemsToCraft("src/main/resources/itemsToCraft/itemsToCraft.json");

        for (CraftItemConfig craftItem : itemsToCraftConfig.itemsToCraft) {
            log.debug("Craft item: {}", craftItem.name);

            if (craftItem.name.equals(itemName)) {
                log.debug("Crafting new item: {}", itemName);
                ItemConfig itemConfig = new ItemConfig();
                itemConfig.name = craftItem.name;
                itemConfig.path = craftItem.path;
                itemConfig.x = 0;
                itemConfig.y = 0;
                itemConfig.width = 2;
                itemConfig.height = 2;
                itemConfig.isCollectable = false;
                Item newItem = new Item(itemConfig, log);
                newItem.loadSprite();
                newItem.createBody(world);
                log.info("Crafted and added new item: {}", itemName);
                return newItem;
            }
        }
        return null;
    }



    @Override
    public void show() {
        LevelLoader levelLoader = new LevelLoader();
        config = levelLoader.loadLevel(jsonPath);

        createCamera();
        createWorld(config.gravityX, config.gravityY);
        createBackgroundAndGround(config);
        createPlayer();
        createItems(config);
        createMoveableObj(config);
        createButtons(config);
        createPlayersInventory();
        createFriendlyNPCs(config);
        createUi();

        if (config.items == null){
            log.info("No items in this location");
        }

        smallBugs = new ArrayList<>();
        bigBugs = new ArrayList<>();

        createEnemies();

        log.info("Ratio of height and width: " + (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        //hard code values for better calculations of the world bounds
        createWorldBounds(31, 32 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
    }


    @Override
    public void render(float delta) {
        handleInput();
        updateGameLogic(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        // functions that enable you to go to other levels should be overwritten in the subclass
        goToFunctions();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawGameElements();

        batch.end();

        uiStage.act(delta);
        uiStage.draw();

        isMoving = false;
        debugRenderer.render(world, camera.combined);
    }


    private void updateGameLogic(float delta) {
        world.step(1/60f, 6, 2);
        stateTime += delta;

        for (Body body : bodiesToDestroy) {

            world.destroyBody(body);
        }
        bodiesToDestroy.clear();


        for (Door door : doorsToClose) {
            door.close();
        }
        doorsToClose.clear();

        for (Door door : doorsToOpen) {
            door.open();
        }
        doorsToOpen.clear();

        if (smallBugs != null) {
            Iterator<EnemySmallBug> iterator = smallBugs.iterator();
            while (iterator.hasNext()) {
                EnemySmallBug enemy = iterator.next();
                enemy.update(player.getPosition());
                if (!game.isEnemyAlive(enemy.getName())) {
                    iterator.remove(); // Safe removal during iteration
                }
            }
        }

        if (bigBugs != null) {
            Iterator<EnemyBigBug> iterator = bigBugs.iterator();
            while (iterator.hasNext()) {
                EnemyBigBug enemy = iterator.next();
                enemy.update(player.getPosition());
                if (!game.isEnemyAlive(enemy.getName())) {
                    iterator.remove(); // Safe removal during iteration
                }
            }
        }

        if (itemsToPutInInventory != null){
            for (Item item : itemsToPutInInventory){
                if (item == null){
                    continue;
                }
                item.setInactive();
                inventory.add(item);
                game.playerAddItem(item.getName());
                handleSaving();
            }
        }
        itemsToPutInInventory.clear();

        if (!game.isPlayerAlive()){
            game.setScreen(new BaseScreen(game, 15, 4, "levels/BaseScreen.json"));
            game.setPlayerAlive(true);

        }
    }


    private void drawGameElements() {
        drawOtherElements();
        player.drawPlayer(batch, stateTime, isMoving, false, facingRight);
    }


    private void drawOtherElements() {

        backgroundSprite.draw(batch);
        for (Sprite groundSprite : groundSprites) {
            groundSprite.draw(batch);
        }
        if (items != null) {
            for (Item item : items){
                item.draw(batch);
            }
        }
        if (moveableObjs != null) {
            for (MoveableObj moveableObj : moveableObjs){
                moveableObj.draw(batch);
            }
        }

        if (buttons != null) {
            for (Button button : buttons) {
                button.draw(batch);
                if (!button.isNotPressed) {
                    for (Door door : button.getDoors()) {
                        door.draw(batch);
                    }
                }
            }
        }

        if (smallBugs != null){
            for (EnemySmallBug enemy : smallBugs){
                enemy.draw(batch, stateTime);
            }
        }

        if (bigBugs != null){
            for (EnemyBigBug enemy : bigBugs){
                enemy.draw(batch, stateTime);
            }
        }

        if (friendlyNPCs != null) {
            for (FriendlyNPC friendlyNPC : friendlyNPCs) {
                friendlyNPC.draw(batch, stateTime, player.PPM);
            }
        }

        if (inventory != null){
            float PPM = player.PPM;
            //value so low because PPM
            float x = (Gdx.graphics.getWidth() * 0.009f);
            float y = (Gdx.graphics.getHeight() *0.0009f);
            for (Item item : inventory){
                item.setPosition(x, y);
                item.draw(batch);
                x += (Gdx.graphics.getWidth() * 0.3f)/PPM;
            }
        }

        if (showDialogue) {
            updateUi(currentDialogue);
        }

    }

    private void updateUi(String newText) {
        label.setText(newText);
    }

    /**
     * Function to go to other levels
     * Overwrite this function in the subclass to go to other levels
     */
    public void goToFunctions(){
        goToEastWoods();
        goToWestWoods();
    }

    /**
     * Function to go to East Woods
     * make a function like this to go to other levels
     * ensure it uses the same structure so that levels are saved and loaded correctly
     */
    private void goToEastWoods(){
        Vector2 position = player.getPosition();

        if (position.x < 2 && position.y < 16) {
            if (game.isLocationLocked("EastWoods")) {
                return;
            }

            Path path = Paths.get("src/main/resources/saveLevels/EastWoodsBase.json");
            if (path.toFile().exists()) {
                log.info("Going to east woods from loaded file");
                game.setScreen(new EastWoodsBase(game, 28, 10, "src/main/resources/saveLevels/EastWoodsBase.json"));
            }else {
                log.info("Going to east woods");
                game.setScreen(new EastWoodsBase(game, 28, 10, "levels/EastWoodsBase.json"));
            }
        }
    }


    private void goToWestWoods(){
        Vector2 position = player.getPosition();

        if (position.x > 28 && position.y < 6) {
            if (game.isLocationLocked("WestWoods")) {
                return;
            }
            Path path = Paths.get("src/main/resources/saveLevels/WestWoodsBase.json");

            if (path.toFile().exists()) {
                log.info("Going to west woods from loaded file");
                game.setScreen(new WestWoodsBase(game, 2, 2, "src/main/resources/saveLevels/WestWoodsBase.json"));
            }else {
                log.info("Going to west woods");
                game.setScreen(new WestWoodsBase(game, 2, 2, "levels/WestWoodsBase.json"));
            }
        }
    }


    //BodyDoorItemRemoveManager methods
    @Override
    public void removeBody(Body body) {
        bodiesToDestroy.add(body);
    }

    @Override
    public void closeDoor(Door door) {
        doorsToClose.add(door);
    }

    @Override
    public void removeItem(Item item) {
        Iterator<ItemConfig> iterator = config.items.iterator();

        while (iterator.hasNext()) {
            ItemConfig itemConfig = iterator.next();
            log.debug(itemConfig.name + " " + item.getName());

            if (itemConfig.name.equals(item.getName())) {
                log.info("Removing item: " + item.getName());
                iterator.remove();  // Safely remove using iterator
            }
        }
        items.remove(item);
        handleSaving();
    }


    @Override
    public void addItemToInventory(Item item){
        itemsToPutInInventory.add(item);

        if (inventoryConfig == null) {
            inventoryConfig = new InventoryConfig();
        }

        if (inventoryConfig.items == null) {
            inventoryConfig.items = new ArrayList<>();
        }

        ItemConfig itemConfig = new ItemConfig();
        itemConfig.path = item.path;
        itemConfig.name = item.name;
        itemConfig.x = item.x;
        itemConfig.y = item.y;
        itemConfig.width = item.width;
        itemConfig.height = item.height;
        itemConfig.isCollectable = item.isCollectable;

        inventoryConfig.items.add(itemConfig);
        log.info("Item added to inventory in JSON file: " + item.getName());

        InventorySaver inventorySaver = new InventorySaver();
        inventorySaver.saveInventory(inventoryConfig, "src/main/resources/savePlayer/inventory.json");
    }

    @Override
    public void changeOnGround(boolean isOnGround) {
        BaseScreen.this.isOnGround = isOnGround;
    }

    @Override
    public void doorsToOpen(Door door) {
        doorsToOpen.add(door);
    }

    @Override
    public void removeEnemy(Enemy enemy) {
        if (enemy instanceof EnemySmallBug) {
            smallBugs.remove(enemy);
            Iterator<SmallBugConfig> iterator = config.smallBugs.iterator();

            while (iterator.hasNext()) {
                SmallBugConfig smallBugConfig = iterator.next();

                if (smallBugConfig.name.equals(enemy.getName())) {
                    iterator.remove();  // Safely remove using iterator
                }
            }
        } else if (enemy instanceof EnemyBigBug) {
            bigBugs.remove(enemy);
            Iterator<BigBugConfig> iterator = config.bigBugs.iterator();

            while (iterator.hasNext()) {
                BigBugConfig bigBugConfig = iterator.next();
                if (bigBugConfig.name.equals(enemy.getName())) {
                    iterator.remove();  // Safely remove using iterator
                }
            }
        }
        handleSaving();
    }

    @Override
    public void addFriendlyNPCsInContact(FriendlyNPC friendlyNPC) {
        friendlyNPCsInContact.add(friendlyNPC);
    }

    @Override
    public void removeFriendlyNPCsInContact(FriendlyNPC friendlyNPC) {
        friendlyNPCsInContact.removeValue(friendlyNPC, true);
    }

    //Screen methods
    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        backgroundTexture.dispose();
        player.dispose();

        for (Sprite groundSprite : groundSprites) {
            groundSprite.getTexture().dispose();
        }

        for (Item item : items){
            item.dispose();
        }

        for (MoveableObj moveableObj : moveableObjs){
            moveableObj.dispose();
        }

        for (Button button : buttons) {
            button.dispose();
        }

        for (EnemySmallBug enemy : smallBugs){
            enemy.dispose();
        }

        for (EnemyBigBug enemy : bigBugs){
            enemy.dispose();
        }
    }
}
