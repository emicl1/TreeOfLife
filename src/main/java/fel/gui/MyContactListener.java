package fel.gui;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fel.jsonFun.SmallBugConfig;

public class MyContactListener implements ContactListener {
    public int groundContacts = 0;
    public boolean isOnGround = false;


    private BodyDoorItemRemoveManager bodyDoorItemRemoveManager;

    public MyContactListener(BodyDoorItemRemoveManager bodyDoorItemRemoveManager) {
        this.bodyDoorItemRemoveManager = bodyDoorItemRemoveManager;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) ||
                isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
            groundContacts++;
            bodyDoorItemRemoveManager.changeOnGround(true);
        }
        if ((isFixturePlayer(fixA) && isFixtureItem(fixB)) ||
                (isFixturePlayer(fixB) && isFixtureItem(fixA))) {
            System.out.println("Item collected");
            handleItemCollection(fixA, fixB);
        }

        if (isFixtureButton(fixA) || isFixtureButton(fixB)) {
            handleButtonsTouch(fixA, fixB);
        }

        if (isFixtureMoveableObj(fixA) || isFixtureMoveableObj(fixB)) {
            System.out.println("Moveable object touched");
        }

        if ((isFixtureEnemy(fixA) || isFixtureEnemy(fixB)) && (isFixturePlayer(fixA) || isFixturePlayer(fixB))){
            System.out.println("Enemy touched");
        }

    }

    @Override
    public void endContact(Contact contact) {
        if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) ||
                isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
            groundContacts--;
            if (groundContacts <= 0) {
                bodyDoorItemRemoveManager.changeOnGround(false);
                groundContacts = 0;  // Reset to prevent negative counts
            }
        }
        if (isFixtureButton(contact.getFixtureA()) || isFixtureButton(contact.getFixtureB())) {
            handleButtonRelease(contact.getFixtureA(), contact.getFixtureB());
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

    private void handleItemCollection(Fixture fixA, Fixture fixB) {
        Fixture itemFixture = fixA.getUserData() instanceof Item ? fixA : fixB;

        if (itemFixture.getUserData() instanceof Item) {
            Item collectedItem = (Item) itemFixture.getUserData();
            System.out.println("Item collected: " + collectedItem.name);
            if (collectedItem.isCollectable) {
                bodyDoorItemRemoveManager.removeItem(collectedItem);
                bodyDoorItemRemoveManager.removeBody(collectedItem.getBody());
            }
        } else {
            System.out.println("Error: Non-item fixture involved in item collection");
        }
    }

    private void handleButtonsTouch(Fixture fixA, Fixture fixB) {
        Fixture buttonFixture = fixA.getUserData() instanceof Button ? fixA : fixB;

        if (buttonFixture.getUserData() instanceof Button) {
            Button button = (Button) buttonFixture.getUserData();
            System.out.println("Button pressed");
            Array<Door> doors = button.getDoors();
            for (Door door : doors) {
                bodyDoorItemRemoveManager.removeDoor(door);
            }
            button.isNotPressed = true;
        } else {
            System.out.println("Error: Non-button fixture involved in button press");
        }
    }

    private void handleButtonRelease(Fixture fixA, Fixture fixB) {
        Fixture buttonFixture = fixA.getUserData() instanceof Button ? fixA : fixB;

        if (buttonFixture.getUserData() instanceof Button) {
            Button button = (Button) buttonFixture.getUserData();
            System.out.println("Button released");
            Array<Door> doors = button.getDoors();
            for (Door door : doors) {
                bodyDoorItemRemoveManager.doorsToOpen(door);
            }
            button.isNotPressed = false;
        } else {
            System.out.println("Error: Non-button fixture involved in button release");
        }
    }
}
