import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathiaspalm
 */
public class CourseTest {

    /**
     * Test of getName method, of class Course.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Course instance = new Course("c1", null, 10);
        String expResult = "c1";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBook method, of class Course.
     */
    @Test
    public void testGetBook() {
	System.out.println("getBook");
	Book b = new Book("book", "book 1", 3);
        Course instance = new Course("c1", b, 10);
        Book result = instance.getBook();
	assertNotNull(result);
    }

    /**
     * Test of getPoints method, of class Course.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        Course instance = new Course("c1", null, 10);
        int expResult = 10;
        int result = instance.getPoints();
        assertEquals(expResult, result);
    }    
}
