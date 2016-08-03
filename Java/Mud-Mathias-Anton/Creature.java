/**
 * @file Creature.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief General class to rep a creature
 */
public class Creature {
    //Teachers needs a name

    /**
     * Creature name
     */
    protected String name;

    /**
     * Constructor for the creature
     * @param name Name of creature
     */
    public Creature(String name) {
	this.name = name;
    }

    /**
     * Get the creature name
     * @return Name
     */
    public String getName() {
	return this.name;
    }
    public String toString() {
	return this.name;
    }
}
