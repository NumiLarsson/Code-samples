/**
 * @file Book.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Class to rep a book in our mud
 */
public class Book extends Item{
    private String author;
    private int year;

    /**
     * Constructor fot the book
     * @param course Course name
     * @param name Book name
     * @param size Book size
     */
    public Book(String course, String name, int size){
	super.name = course;
	super.size = size;
	this.author = name;
    }

    /**
     * Build a book from file
     * @param fromFile File name
     */
    public Book(String fromFile){
	char searchValue = ';';
	int index = fromFile.indexOf(searchValue);
	super.name = fromFile.substring(0, index);
	
	int startIndex = index+2;
	index = fromFile.indexOf(searchValue, startIndex);
	this.author = fromFile.substring(startIndex,index);
	
	startIndex = index+2;
	index = fromFile.indexOf(searchValue, startIndex);
	this.year =  Integer.parseInt(fromFile.substring(startIndex,index));

	startIndex = index+2;
	super.size = Integer.parseInt(fromFile.substring(startIndex, fromFile.length()));
	
    }
    
    /**
     * Get the course bound to the book
     * @return Course name
     */
    public String getCourse(){
	return this.name;
    }
    /**
     * Print out the book
     */
    public void print(){
	System.out.println("Book: " + this.name +
			 " author: " + this.author +
			 " weight: " + this.size);
    }
}
