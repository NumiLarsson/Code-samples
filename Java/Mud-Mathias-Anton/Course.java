/**
 * @file Course.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Course class for a course in the mud
 */
import java.util.*;

public class Course {
    private String name;
    private Book book;
    private int hp;
    private String question;
    private String[] alternatives;
    private int answer;

    /**
     * Constructor for a course
     * @param name Name of course
     * @param book Book to the course
     * @param hp Points of course
     */
    public Course(String name, Book book, int hp){
	this.name = name;
	this.book = book;
	this.hp = hp;
    }

    /**
     * Constructor for a course
     * @param fromFile Row of course file
     * @param book Course book
     * @param question Question to a course
     * @param alternatives Alternatives to the questions
     * @param answer Answer to the question
     */
    public Course(String fromFile, Book book, String question, String[] alternatives, int answer){
	char searchValue = ';';
	int index = fromFile.indexOf(searchValue);
	this.name = fromFile.substring(0, index);
	int startIndex = index+2;

	index = fromFile.indexOf(searchValue, index);
	index += 2;

	index = fromFile.indexOf(searchValue, index);
	index += 2;
	
	this.hp = Integer.parseInt(fromFile.substring(index,fromFile.length()));
	this.book = book;
	this.question = question;
	this.alternatives = alternatives;
	this.answer = answer;
    }
    
    /**
     * Get the course name
     * @return Course name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Get the course book
     * @return Course book
     */
    public Book getBook() {
	return this.book;
    }

    /**
     * Get course question
     * @return Course question
     */
    public String getQuestion() {
	return this.question;
    }

    /**
     * Get question alternatives
     * @return String array of alternatives
     */
    public String[] getalts() {
	return this.alternatives;
    }

    /**
     * Course points
     * @return Points
     */
    public int getPoints() {
	return this.hp;
    }

    /**
     * Answer to question
     * @return Answer
     */
    public int getAnswer() {
	return this.answer;
    }
}
