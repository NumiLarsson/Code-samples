/**
 * @file Studet.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Class for our students in the mud
 */
public class Student extends Creature {
    private Course activeCourse;
    private Course completeCourse;
    private Book oldBook;
    private int answer;

    /**
     * Constructor for the student
     * @param name Name of student
     * @param activeCourse Active course is enrrold to
     * @param completeCourse Course student has completed
     */
    public Student(String name, Course activeCourse, Course completeCourse){
	super(name);
	this.activeCourse = activeCourse;
	this.completeCourse = completeCourse;
	this.oldBook = completeCourse.getBook();
	this.answer = completeCourse.getAnswer();
    }

    /**
     * Method to trade a book from his completed course with student
     * @param offeredBook Book to offer
     * @return Book to recive
     */
    public Book tradeBook(Book offeredBook){
	Book temp = null;
	try {
	    if (!offeredBook.getName().equals(oldBook.getName())) {
		temp = oldBook;
		oldBook = offeredBook;
	    }
	} catch(Exception e){};
	return temp;
    }

    /**
     * Trade book for answer for his active course
     * @param offeredBook Book to offer
     * @return Answer to recive
     */
    public int tradeForAnswer(Book offeredBook) {
	Book b = tradeBook(offeredBook);
	if (b == null) return 0;
	return completeCourse.getAnswer();
    }

    /**
     * Get the complete course book name
     * @return Book name
     */
    public String getBookName() {
	return this.oldBook.getName();
    }

    /**
     * Get students active Course
     * @return Course
     */
    public Course getActiveCourse() {
	return this.activeCourse;
    }

    /**
     * Get students completed course
     * @return Course
     */
    public Course getCompleteCourse() {
	return this.completeCourse;
    }
}
