/**
 * @file Teacher.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Class to represent a teacher
 */

import java.util.*;

public class Teacher extends Creature {
    private Course course;

    /**
     * Constructor for teacher
     * @param name Name of teacher
     * @param course Course teacher teach
     */
    public Teacher (String name, Course course){
	super(name);
	this.course = course;
    }

    /**
     * Get teachers course
     * @return Course
     */
    public Course getCourse(){
	return this.course;
    }

    /**
     * Get course points
     * @return Points
     */
    public int getCoursePoints() {
	return this.course.getPoints();
    }

    /**
     * Ask a question to complete a course
     * @param hasBook If user has book true else false
     * @return True if right else false
     */
    public boolean askQuestion(boolean hasBook) {
	Scanner scan = new Scanner(System.in);
	System.out.println(course.getQuestion());
	Random rand = new Random();
	String[] alts = course.getalts();
	int ans = course.getAnswer();
	if (hasBook) {
	    int ran = rand.nextInt(1);
	    if (ran < 1) {
		System.out.println("\tAnswer [1]:" + alts[ans]);	
	    }
	    if (ans < 2) {
		System.out.println("\tAnswer [2]:" + alts[ans+1]); 
	    } else {
		System.out.println("\tAnswer [2]:" + alts[ans-1]); 
	    }
	    if (ran > 0) {
		System.out.println("\tAnswer [1]:" + alts[ans]);
	    }
	} else {
	    int i = 1;
	    for(String a: alts) {
		System.out.println("\tAnswer ["+i+"]:"+a);
		++i;
	    }
	}
	System.out.print("\nWhats your answer [a number]: ");
	int x = scan.nextInt();
	if (x == ans) {
	    System.out.println("\n\tCorrect!");
	    return true;
	} else {
	    System.out.println("\n\tWrong!");
	    return false;
	}
    }
}
