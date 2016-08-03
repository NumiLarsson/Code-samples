import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathiaspalm
 */
public class BackpackTest {

    /**
     * Test of getItem method, of class Backpack.
     */
    @Test
    public void testGetItem() {
        System.out.println("getItem");
        String name = "key";
        Backpack instance = new Backpack(10);
	Item result = instance.getItem(name);
	assertNull(result);
    }

    /**
     * Test of addItem method, of class Backpack.
     */
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        Item item = new Key("key");
        Backpack instance = new Backpack(10);
	boolean expResult = true;
        boolean result = instance.addItem(item);
        assertEquals(expResult, result);
    }

    /**
     * Test of dropItemFromBag method, of class Backpack.
     */
    @Test
    public void testDropItemFromBag() {
        System.out.println("dropItemFromBag");
	Key item = new Key("key");
        Backpack instance = new Backpack(10);
        instance.addItem(item);
        String name = "key";
        Item result = instance.dropItemFromBag(name);
	assertNotNull(result);
    }

    /**
     * Test of removeKey method, of class Backpack.
     */
    @Test
    public void testRemoveKey() {
        System.out.println("removeKey");
	Item item = new Key("key");
        Backpack instance = new Backpack(10);
        instance.addItem(item);
        instance.removeKey();
    }

    /**
     * Test of hasBookInBag method, of class Backpack.
     */
    @Test
    public void testHasBookInBag() {
        System.out.println("hasBookInBag");
        Book b = new Book("book", "book 1", 3);
        Backpack instance = new Backpack(10);
	instance.addItem(b);
        boolean expResult = true;
        boolean result = instance.hasBookInBag(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasKey method, of class Backpack.
     */
    @Test
    public void testHasKey() {
        System.out.println("hasKey");
	Item item = new Key("key");
        Backpack instance = new Backpack(10);
        instance.addItem(item);
        boolean expResult = true;
        boolean result = instance.hasKey();
        assertEquals(expResult, result);
    }
}
