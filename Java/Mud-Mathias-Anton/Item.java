/**
 * @file Item.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief General class for an Item
 */
public class Item {

    /**
     * Name of item
     */
    protected String name;

    /**
     * Size of Item
     */
    protected int size;
    
    /**
     * Get the name of the item
     * @return name
     */
    public String getName(){
	return this.name;
    }

    /**
     * Get size of item
     * @return size
     */
    public int getSize(){
	return this.size;
    }

    public String toString(){
	return "Name: " + this.name + ", size: " + this.size;
    }
    
}
