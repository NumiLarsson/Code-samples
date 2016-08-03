import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathiaspalm
 */
public class BookTest {

    /**
     * Test of getCourse method, of class Book.
     */
    @Test
    public void testGetCourse() {
        System.out.println("getCourse");
        Book instance = new Book("book", "book 1", 4);
        String expResult = "book";
        String result = instance.getCourse();
        assertEquals(expResult, result);
    }    
}
