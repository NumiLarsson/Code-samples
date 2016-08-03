/**
 * @file Key.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Key item class to rep a key
 */
public class Key extends Item {
    boolean notUsed;

    /**
     * Constructor for the key
     * @param name Name of the key
     */
    public Key(String name){
	super.name = name;
	super.size = 1;
	
	this.notUsed = true;
    }

    /**
     * Use the key
     * @return true if used else false
     */
    public boolean useKey (){
	if (this.notUsed){
	    this.notUsed = false;
	    return true;
	}
	return false;
    }

    /**
     * Print key name and size
     */
    public void print(){
	System.out.print("Key: \"" + this.name );
	if (notUsed) System.out.print("\" has not been used. Weight: ");
	else System.out.print("\" has been used. Weight: ");
	System.out.println(this.size);
    }
}
