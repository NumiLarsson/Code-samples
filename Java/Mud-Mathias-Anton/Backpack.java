/**
 * @file Backpack.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief User backpack or inventory
 */
import java.util.*;

public class Backpack {
    private int currSize;
    private int maxSize;
    private ArrayList<Item> bagList;

    /**
     * Constructor for backpack
     * @param maxSize Backpack size
     */
    public Backpack(int maxSize){
	this.maxSize = maxSize;
	this.currSize = 0;
	this.bagList = new ArrayList<Item>();
    }

    /**
     * Get item from backpack
     * @param name Name of item
     * @return if item exists else null
     */
    public Item getItem(String name){
	Iterator<Item> itr = this.bagList.iterator();
	Item tempItem;
	
	while ( itr.hasNext() ){
	    tempItem = itr.next();
	    if (tempItem.getName().equals(name)) {
		return tempItem;
	    }
	}
	return null;
    }

    /**
     * Add item to backpack if it fits
     * @param item Item to add
     * @return True if added else false
     */
    public boolean addItem(Item item){
	//return this.bagList.addItem(item);
	if ( this.bagList.contains(item) ){
	    return false;
	}
	if ( (this.currSize + item.getSize() ) <= maxSize ){
	    if ( this.bagList.add(item) ) {
		this.currSize += item.getSize();
		return true;
	    //This is a boolean check because there's two modes to operate
	    //an arrayList, allow duplicates or not.
	    //Returns true if the list changed due to the add.
	    }   
	}
	else System.out.println("Bag is full!");
	return false;
    }

    /**
     * Drop an item from backpack
     * @param name Item to drop
     * @return Item that was droped or null if it didnt exist
     */
    public Item dropItemFromBag(String name){
	Item i = null;
	Iterator<Item> iter = this.bagList.iterator();
	Item tempItem;
	while (iter.hasNext()) {
	    tempItem = iter.next();
	    if (tempItem.getName().toLowerCase().equals(name.toLowerCase())) {
		i = tempItem;
		iter.remove();
	    }
	}
	return i;
    }

    /**
     * If a key exists then remove it
     */
    public void removeKey() {
	Iterator<Item> iter = this.bagList.iterator();
	boolean notDelete = true;
	while (iter.hasNext() && notDelete) {
	    if (iter.next() instanceof Key) {
		iter.remove();
		notDelete = false;
	    }
	}
    }

    /**
     * User has the given book in bag
     * @param b Book to check
     * @return True if exists else false
     */
    public boolean hasBookInBag(Book b) {
	Iterator<Item> itr = this.bagList.iterator();
	Item tempItem;

	while ( itr.hasNext() ){
	    tempItem = itr.next();
	    if (tempItem instanceof Book) {
		if (tempItem.getName().equals(b.getName())) {
		    return true;   
		}
	    }
	}
	return false;
    }
    
    /**
     * Check if key exists in backpack
     * @return True if exsists else false
     */
    public boolean hasKey() {
	Iterator<Item> itr = this.bagList.iterator();
	Item tempItem;

	while ( itr.hasNext() ){
	    tempItem = itr.next();
	    if (tempItem instanceof Key) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Print out the backpack
     */
    public void print(){
	Iterator<Item> itr = this.bagList.iterator();
	Item tempItem;
	while ( itr.hasNext() ){
	    tempItem = itr.next();
	    System.out.println("\tItem: " + tempItem.getName() +
			       " Weight: " + tempItem.getSize() );
	}
    }
}
