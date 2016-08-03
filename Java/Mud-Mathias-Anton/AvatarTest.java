import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathiaspalm
 */
public class AvatarTest {
   

    /**
     * Test of addNewCourse method, of class Avatar.
     */
    @Test
    public void testAddNewCourse() {
	System.out.println("addNewCourse");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {gaming};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
        boolean expResult = false;
        boolean result = instance.addNewCourse(gaming);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFinishedCourse method, of class Avatar.
     */
    @Test
    public void testRemoveFinishedCourse() {
        System.out.println("removeFinishedCourse");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {gaming};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
	instance.removeFinishedCourse(gaming);
	boolean expResult = false;
        boolean result = instance.addNewCourse(gaming);
        assertEquals(expResult, result);
    }

    /**
     * Test of finishCourse method, of class Avatar.
     */
    @Test
    public void testFinishCourse() {
        System.out.println("finishCourse");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
	instance.addNewCourse(gaming);
        boolean result = instance.finishCourse(gaming);
	boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of isFinishedCourse method, of class Avatar.
     */
    @Test
    public void testIsFinishedCourse() {
        System.out.println("isFinishedCourse");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {gaming};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
        boolean expResult = true;
        boolean result = instance.isFinishedCourse(gaming);
        assertEquals(expResult, result);
    }

    /**
     * Test of userHasCourse method, of class Avatar.
     */
    @Test
    public void testUserHasCourse() {
        System.out.println("userHasCourse");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {gaming};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
        boolean expResult = false;
        boolean result = instance.userHasCourse(gaming);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHP method, of class Avatar.
     */
    @Test
    public void testGetHP() {
        System.out.println("getHP");
	Book gamingBook = new Book("Starcraft", "Gaming", 3);
	Course gaming = new Course("Gaming", gamingBook, 5);
	Course[] finishedCourses = {gaming};
	Avatar instance = new Avatar(finishedCourses, 10, 60);
        int expResult = 60;
        int result = instance.getHP();
        assertEquals(expResult, result);
    }
    
}
