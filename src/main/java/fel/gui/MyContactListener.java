package fel.gui;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fel.controller.MyGame;
import org.slf4j.Logger;

/**
 * This class is used to handle the contact between fixtures in the game
 * It uses BodyDoorItemRemoveManager interface to remove bodies, doors and items
 * from the game
 */
public class MyContactListener implements ContactListener {
    private int groundContacts = 0;
    private MyGame game;

    private BodyDoorItemRemoveManager bodyDoorItemRemoveManager;

    private Logger log;

    public MyContactListener(BodyDoorItemRemoveManager bodyDoorItemRemoveManager, MyGame game) {
        this.bodyDoorItemRemoveManager = bodyDoorItemRemoveManager;
        this.game = game;
        createLogger();
    }

    private void createLogger() {
        log = game.getRootLogger();
        ;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) || isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
            groundContacts++;
            bodyDoorItemRemoveManager.changeOnGround(true);
        }
        if ((isFixturePlayer(fixA) && isFixtureItem(fixB)) || (isFixturePlayer(fixB) && isFixtureItem(fixA))) {
            handleItemCollection(fixA, fixB);
        }

        if (isFixtureButton(fixA) || isFixtureButton(fixB)) {
            handleButtonsTouch(fixA, fixB);
        }

        if (isFixtureMoveableObj(fixA) || isFixtureMoveableObj(fixB)) {

        }

        if ((isFixtureEnemy(fixA) || isFixtureEnemy(fixB)) && (isFixturePlayer(fixA) || isFixturePlayer(fixB))) {
            log.info("Enemy touched player");
            game.playerTakeDamage(20);
            if (game.getPlayerHealth() <= 0) {
                game.setPlayerAlive(false);
            }
        }

        if ((isFixtureSword(fixA) && isFixtureEnemy(fixB))) {
            log.info("Sword touched enemy");
            String name = ((Enemy) fixB.getUserData()).getName();
            System.out.println(name);
            game.playerAttack(name);
            if (!game.isEnemyAlive(name)) {
                game.removeEnemy(name);
                bodyDoorItemRemoveManager.removeEnemy((Enemy) fixA.getUserData());
                bodyDoorItemRemoveManager.removeBody(fixB.getBody());
            }
        }

        if ((isFixtureSword(fixB) && isFixtureEnemy(fixA))) {
            log.info("Sword touched enemy");
            String name = ((Enemy) fixA.getUserData()).getName();
            System.out.println(name);
            game.playerAttack(name);
            if (!game.isEnemyAlive(name)) {
                game.removeEnemy(name);
                bodyDoorItemRemoveManager.removeEnemy((Enemy) fixA.getUserData());
                bodyDoorItemRemoveManager.removeBody(fixA.getBody());
            }
        }

        if ((isFixturePlayer(fixA) && isFixtureFriendlyNPC(fixB)) || (isFixturePlayer(fixB) && isFixtureFriendlyNPC(fixA))) {
            log.info("Friendly NPC touched player");
            FriendlyNPC friendlyNPC = (FriendlyNPC) (isFixtureFriendlyNPC(fixA) ? fixA.getUserData() : fixB.getUserData());
            bodyDoorItemRemoveManager.addFriendlyNPCsInContact(friendlyNPC);
        }

    }

    @Override
    public void endContact(Contact contact) {
        if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) || isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
            groundContacts--;
            if (groundContacts <= 0) {
                bodyDoorItemRemoveManager.changeOnGround(false);
                groundContacts = 0;  // Reset to prevent negative counts
            }
        }
        if (isFixtureButton(contact.getFixtureA()) || isFixtureButton(contact.getFixtureB())) {
            handleButtonRelease(contact.getFixtureA(), contact.getFixtureB());
        }

        if ((isFixturePlayer(contact.getFixtureA()) && isFixtureFriendlyNPC(contact.getFixtureB())) || (isFixturePlayer(contact.getFixtureB()) && isFixtureFriendlyNPC(contact.getFixtureA()))) {
            log.info("Friendly NPC stopped touching player");
            FriendlyNPC friendlyNPC = (FriendlyNPC) (isFixtureFriendlyNPC(contact.getFixtureA()) ? contact.getFixtureA().getUserData() : contact.getFixtureB().getUserData());
            bodyDoorItemRemoveManager.removeFriendlyNPCsInContact(friendlyNPC);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }


    private boolean isFixturePlayer(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData().equals("player");
    }

    private boolean isFixtureGround(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData().equals("ground");
    }

    private boolean isFixtureItem(Fixture fixture) {
        return fixture.getUserData() instanceof Item;
    }

    private boolean isFixtureButton(Fixture fixture) {
        return fixture.getUserData() instanceof Button;
    }

    private boolean isFixtureMoveableObj(Fixture fixture) {
        return fixture.getUserData() instanceof MoveableObj;
    }

    private boolean isFixtureEnemy(Fixture fixture) {
        return fixture.getUserData() instanceof Enemy;
    }

    private boolean isFixtureSword(Fixture fixture) {
        return fixture.getUserData() instanceof Sword;
    }

    private boolean isFixtureFriendlyNPC(Fixture fixture) {
        return fixture.getUserData() instanceof FriendlyNPC;
    }

    private void handleItemCollection(Fixture fixA, Fixture fixB) {
        Fixture itemFixture = fixA.getUserData() instanceof Item ? fixA : fixB;

        if (itemFixture.getUserData() instanceof Item) {
            Item collectedItem = (Item) itemFixture.getUserData();
            log.info("Item collected: " + collectedItem.getName());
            if (collectedItem.isCollectable) {
                bodyDoorItemRemoveManager.addItemToInventory(collectedItem);
                bodyDoorItemRemoveManager.removeItem(collectedItem);
                bodyDoorItemRemoveManager.removeBody(collectedItem.getBody());
            }
        } else {
            log.error("Error: Non-item fixture involved in item collection");
        }
    }

    private void handleButtonsTouch(Fixture fixA, Fixture fixB) {
        Fixture buttonFixture = fixA.getUserData() instanceof Button ? fixA : fixB;

        if (buttonFixture.getUserData() instanceof Button) {
            Button button = (Button) buttonFixture.getUserData();
            log.info("Button pressed");
            Array<Door> doors = button.getDoors();
            for (Door door : doors) {
                bodyDoorItemRemoveManager.closeDoor(door);
            }
            button.isNotPressed = true;
        } else {
            log.error("Error: Non-button fixture involved in button press");
        }
    }

    private void handleButtonRelease(Fixture fixA, Fixture fixB) {
        Fixture buttonFixture = fixA.getUserData() instanceof Button ? fixA : fixB;

        if (buttonFixture.getUserData() instanceof Button) {
            Button button = (Button) buttonFixture.getUserData();
            log.info("Button released");
            Array<Door> doors = button.getDoors();
            for (Door door : doors) {
                bodyDoorItemRemoveManager.doorsToOpen(door);
            }
            button.isNotPressed = false;
        } else {
            log.error("Error: Non-button fixture involved in button release");
        }
    }
}
