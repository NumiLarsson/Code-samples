/**
 * @file Avatar.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Handels avtar and user player
 */
import java.util.*;

public class Avatar {
    
    private ArrayList<Course> unfinishedCourses;
    private ArrayList<Course> finishedCourses;
    private int hPoints;
    private Backpack backpack;

    /**
     * Constructor to creat an avatar
     * @param finished List of finished courses
     * @param backpackSize backpack size
     * @param startingHP starting points
     */
    public Avatar(Course[] finished, int backpackSize, int startingHP) {
	this.finishedCourses = new ArrayList<Course>();
	this.unfinishedCourses = new ArrayList<Course>();
	
	for ( Course x : finished ){
	    this.addFinishedCourse(x);
	}
	
	this.hPoints = startingHP;
	backpack = new Backpack(backpackSize);
	Key k = new Key("Key x");
	backpack.addItem(k);
    }

    /**
     * Method to add new course to the user
     * @param course Course to add
     * @return True if added succsesfully
     */
    public boolean addNewCourse(Course course) {
	if ( this.unfinishedCourses.contains(course) ) {
	    return false;
	}
	else if ( this.finishedCourses.contains(course) ) {
	    return false;
	}
	return this.unfinishedCourses.add(course);
    }

    /**
     * Private method to move from unfinished to finished courses
     * @param course Course to move
     */
    private void addFinishedCourse(Course course) {
	if ( this.unfinishedCourses.contains(course) ) {
	    return;
	}
	else if ( this.finishedCourses.contains(course) ) {
	    return;
	}
	this.finishedCourses.add(course);
    }
    /**
     * Move course from finished to unfinished
     * @param c Course to move
     */
    public void removeFinishedCourse(Course c) {
	Iterator<Course> itr = this.finishedCourses.iterator();
	Course tempCourse;
	while ( itr.hasNext() ){
	    tempCourse = itr.next();
	    if (tempCourse.getName().equals(c.getName())) {
		itr.remove();
		this.hPoints -= c.getPoints();
		this.unfinishedCourses.add(c);
	    }
	}
    }
    /**
     * Medthod to add course to finished courses
     * @param course Course to add
     * @return True if added succsesfully
     */
    public boolean finishCourse(Course course) {
	Iterator<Course> itr = this.unfinishedCourses.iterator();
	Course tempCourse;
	while ( itr.hasNext() ){
	    tempCourse = itr.next();
	    if (tempCourse.getName().equals(course.getName())) {
		itr.remove();
		this.hPoints += course.getPoints();
		this.finishedCourses.add(course);
		return true;
	    }
	}
	return false;
    }
    /**
     * Check if a course is finished or not
     * @param course Course to check
     * @return True if finished else false
     */
    public boolean isFinishedCourse(Course course) {
	Iterator<Course> itr = this.finishedCourses.iterator();
	Course tempCourse;
	while ( itr.hasNext() ){
	    tempCourse = itr.next();
	    if (tempCourse.getName().equals(course.getName())) {
		return true;
	    }
	}
	return false;
    }
    /**
     * Print finished courses to console
     */
    public void printFinishedCourses() {
	Iterator<Course> itr = this.finishedCourses.iterator();
	Course tempCourse;
	
	while (itr.hasNext() ){
	    tempCourse = itr.next();
	    System.out.println("Course: " + tempCourse.getName() +
			       " is finished.");
	}
    }
    /**
     * Print unfinished courses to console
     */
    public void printUnfinishedCourses() {
	Iterator<Course> itr = this.unfinishedCourses.iterator();
	Course tempCourse;
	
	while (itr.hasNext() ){
	    tempCourse = itr.next();
	    System.out.println("Course: " + tempCourse.getName()
			       +" is unfinished.");
	}
    }
    /**
     * Check if user has finished or unfinished course
     * @param c Course to check
     * @return True if found else false
     */    
    public boolean userHasCourse(Course c) {
	if ( this.unfinishedCourses.contains(c) ) {
	    return false;
	}
	else if ( this.finishedCourses.contains(c) ) {
	    return false;
	}
	return true;
    }
    /**
     * Check if user has a key
     * @return True if exists else false
     */    
    public boolean userHasKey() {
	return backpack.hasKey();
    }
    /**
     * Check if user has course book
     * @param course Course to check
     * @return True if exists else false
     */        
    public boolean hasBook(Course course) {
	return backpack.hasBookInBag(course.getBook());
    }
    /**
     * Gets course book from given course if book exits
     * @param cours Course to check for book
     * @return course book or null if not exits
     */         
    public Book getBookFromBag(Course cours) {
	Item item = backpack.getItem(cours.getBook().getName());
	if (item instanceof Book) {
	    return (Book)item;
	}
	return null;
    }
    /**
     * Remove key from user inventory
     */        
    public void removeKey() {
	backpack.removeKey();
    }
    /**
     * Print items in backpack
     */        
    public void printBackpack() {
	backpack.print();
    }
    /**
     * Drops item from backpack if exits
     * @param name Name of item (not case sensetive)
     * @return item from backpack if found else null
     */
    public Item dropItemFromBackpack(String name) {
	return backpack.dropItemFromBag(name);
    }
    /**
     * Add item to backpack
     * @param c item to add
     * @return true if succsesfull else false
     */
    public boolean addToBackpack(Item c) {
	return backpack.addItem(c);
    }
    /**
     * Get any item from backpack without deletion
     * @param itemName Name of item (not case sensetive)
     * @return item from backpack if found else null
     */
    public Item getFromBackpack(String itemName) {
	return this.backpack.getItem(itemName);
    }
    /**
     * Get the current user points
     * @return int value representing user points
     */
    public int getHP() {
	return this.hPoints;
    }
}
