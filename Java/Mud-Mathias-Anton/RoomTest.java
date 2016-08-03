import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathiaspalm
 */
public class RoomTest {
    /**
     * Test of getName method, of class Room.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Room instance = new Room("Room 1205; X; X; X; Hallway 7; X; X; X; False;");
        String expResult = "Room 1205";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    /**
     * Test of getItem method, of class Room.
     */
    @Test
    public void testGetItem() {
        System.out.println("getItem");
	Room instance = new Room("Room 1205; X; X; X; Hallway 7; X; X; X; False;");
	Key k = new Key("key");
	instance.addItem(k);
	String name = "key";
        Item expResult = k;
        Item result = instance.getItem(name);
	assertEquals(expResult, result);
    }

    /**
     * Test of move method, of class Room.
     */
    @Test
    public void testMove() {
        System.out.println("move");
	Room instance = new Room("Hallway 1; X; Room 1248; Hallway 2; Room 1249; X; False; True; True; ");
        String st = "south";
        String expResult = "Hallway 2";
        String result = instance.move(st);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdRoom method, of class Room.
     */
    @Test
    public void testGetAdRoom() {
        System.out.println("getAdRoom");
        Room instance = new Room("Hallway 1; X; Room 1248; Hallway 2; Room 1249; X; False; True; True; ");
        String st = "south";
        String expResult = "Hallway 2";
        String result = instance.getAdRoom(st);
        assertEquals(expResult, result);
    }

    /**
     * Test of moveHelper method, of class Room.
     */
    @Test
    public void testMoveHelper() {
        System.out.println("moveHelper");
        String st = "north";
        Room instance = new Room("Room 1205; X; X; X; Hallway 7; X; X; X; False;");
        int expResult = 0;
        int result = instance.moveHelper(st);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkDoor method, of class Room.
     */
    @Test
    public void testCheckDoor() {
        System.out.println("checkDoor");
        int i = 2;
        Room instance = new Room("Hallway 1; X; Room 1248; Hallway 2; Room 1249; X; False; True; True; ");
        boolean expResult = true;
        boolean result = instance.checkDoor(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of isSfinx method, of class Room.
     */
    @Test
    public void testIsSfinx() {
        System.out.println("isSfinx");
        Room instance = new Room("Room 1205; X; X; X; Hallway 7; X; X; X; False;");
        boolean expResult = true;
        boolean result = instance.isSfinx();
        assertEquals(expResult, result);
    }

    /**
     * Test of unlockDoorToRoom method, of class Room.
     */
    @Test
    public void testUnlockDoorToRoom() {
        System.out.println("unlockDoorToRoom");
        String room = "Room 1249";
	Room instance = new Room("Hallway 1; X; Room 1248; Hallway 2; Room 1249; X; False; True; False; ");
	boolean expResult = false;
	boolean result = instance.checkDoor(3);
	assertEquals(expResult, result);
        instance.unlockDoorToRoom(room);
	expResult = true;
        result = instance.checkDoor(3);
	assertEquals(expResult, result);
    }
}
