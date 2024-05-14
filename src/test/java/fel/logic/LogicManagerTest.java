package fel.logic;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import fel.jsonFun.LevelConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to test the LogicManager class
 * It uses Mockito to mock the Universe class and Logger class
 */
@ExtendWith(MockitoExtension.class)
public class LogicManagerTest {

    public String[] pathsToJsons = {"levels/BaseScreen.json", "levels/EastWoods1.json",
            "levels/EastWoodsBase.json", "levels/WestWoodsBase.json", "levels/WestWoodsPuzzle.json", "levels/WestWoodsFinal.json",
            "levels/EastWoodsFinal.json"};
    public String pathToPlayerJson = "player/Player.json";
    public String pathToItemsToCraftJson = "itemsToCraft/itemsToCraft.json";

    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    private List<ItemsCrafted> itemsToCraft = new ArrayList<>();

    LogicManager logicManager;
    List<Items> inventory;

    @Mock
    private Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

    @Mock
    public Universe universe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rootLogger = mock(Logger.class);
        universe = mock(Universe.class);
        logicManager = new LogicManager(pathsToJsons, universe, pathToPlayerJson, pathToItemsToCraftJson, rootLogger);
        logicManager.setUniverse(universe);
        logicManager.setConfig(new LevelConfig());
        logicManager.setLogger(rootLogger);
        logicManager.setItemsToCraft(itemsToCraft);
        inventory = logicManager.getInventory();
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(universe);
    }

    @Test
    public void testPlayerTakeDamage() {
        PlayerChar player = mock(PlayerChar.class);
        when(player.getHealth()).thenReturn(100);

        logicManager.player = player;

        logicManager.playerTakeDamage(20);

        verify(player).setHealth(80);  // Check if health is set to 80 after taking 20 damage
    }

    @Test
    public void testPlayerAddItemNormal() {
        Items item = new Items("Sword", "",false);
        PlayerChar player = mock(PlayerChar.class);

        logicManager.player = player;

        boolean result = logicManager.playerAddItem(item);
        verify(player).addItem(item);
        assertFalse(result);  // Assuming false is returned if not a winning item
    }

    @Test
    public void testPlayerAddItemWinning() {
        Items item = new Items("MagicKey", "", true);  // Winning item
        PlayerChar player = mock(PlayerChar.class);

        logicManager.player = player;

        boolean result = logicManager.playerAddItem(item);
        assertTrue(result);  // Expect true as it's a winning item
    }

    @Test
    public void testPlayerAttack() {
        Enemies enemy = mock(Enemies.class);
        PlayerChar player = mock(PlayerChar.class);
        when(player.getAttackDamage()).thenReturn(30);

        logicManager.player = player;

        logicManager.playerAttack(enemy);

        verify(enemy).takeDamage(30, logicManager.getLogger());
    }

    @Test
    void testUnlockLocation() {
        String itemName = "Key";
        List<Locations> locations = new ArrayList<>();
        Locations location1 = mock(Locations.class);
        when(location1.getItemNeededToUnlock()).thenReturn(itemName);
        when(location1.getName()).thenReturn("Mystic Forest");

        locations.add(location1);
        when(universe.getLocations()).thenReturn(locations);

        logicManager.unlockLocation(itemName);

        verify(location1).setIsLocked(false); // Verify the location is set to unlocked
        verify(logicManager.getLogger()).info("Location Mystic Forest unlocked"); // Check if the logger is called with correct info
    }

    @Test
    public void testIsItemInInventory_WithExistingItem() {

        inventory.add(new Items("shield", "", false));
        inventory.add(new Items("sword", "", false));
        logicManager.setInventory(inventory);

        assertTrue(logicManager.isItemInInventory("shield"));
    }

    @Test
    public void testIsItemInInventory_WithNonExistingItem() {
        inventory.add(new Items("shield", "", false));
        logicManager.setInventory(inventory);

        assertFalse(logicManager.isItemInInventory("sword"));
    }

    @Test
    public void testIsItemInInventory_EmptyInventory() {
        assertFalse(logicManager.isItemInInventory("sword"));
    }

    @Test
    public void testIsItemInInventory_NullItemName() {
        inventory.add(new Items("shield", "", false));
        logicManager.setInventory(inventory);

        assertFalse(logicManager.isItemInInventory(null));
    }

    @Test
    void testCanBeCrafted_True() {
        Items item1 = new Items("Wood", "", false);
        Items item2 = new Items("Stone", "", false);
        ItemsCrafted craftableItem = mock(ItemsCrafted.class);

        when(craftableItem.canBeCrafted(item1, item2)).thenReturn(true);
        when(craftableItem.getName()).thenReturn("Axe");
        itemsToCraft.add(craftableItem);

        assertTrue(logicManager.canBeCrafted(item1, item2));
        verify(logicManager.getLogger()).info("item Axe can be crafted from Wood and Stone");
    }

    @Test
    void testCanBeCrafted_False() {
        Items item1 = new Items("Wood", "", false);
        Items item2 = new Items("Stone", "", false);

        assertFalse(logicManager.canBeCrafted(item1, item2));
    }

    @Test
    void testFindItemToCraft_Found() {
        Items item1 = new Items("Iron", "", false);
        Items item2 = new Items("Coal", "", false);
        ItemsCrafted craftableItem = mock(ItemsCrafted.class);

        when(craftableItem.canBeCrafted(item1, item2)).thenReturn(true);
        itemsToCraft.add(craftableItem);

        assertSame(craftableItem, logicManager.findItemToCraft(item1, item2));
    }

    @Test
    void testFindItemToCraft_NotFound() {
        Items item1 = new Items("Iron", "", false);
        Items item2 = new Items("Coal", "", false);

        assertNull(logicManager.findItemToCraft(item1, item2));
    }
}