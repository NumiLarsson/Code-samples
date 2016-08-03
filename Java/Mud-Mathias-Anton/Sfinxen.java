/**
 * @file Sfinxen.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Class for the main boss in our mud
 */
import java.util.Random;
import java.io.*;

public class Sfinxen {
    private String[] wisdomWords;
    private int pointsToGraduate; /* Change this to change HP to
				   graduate */
    
    /**
     * Constructor to construct a boss
     * @param wisdomWords Array of wise words
     * @param pointsToGraduate Points to Defete boss
     */
    public Sfinxen(String[] wisdomWords, int pointsToGraduate) {
	this.wisdomWords = wisdomWords;
	this.pointsToGraduate = pointsToGraduate;
    }

    /**
     * Method to talk to the boss and hear some sweet wise words
     */
    public void talk(){
	Random rand = new Random();
	int length = wisdomWords.length;
	int randomNum = rand.nextInt(length);
	System.out.println(wisdomWords[randomNum]);
    }

    /**
     * Method to graduate aka defete the bosse
     * @param avatar The User
     * @return true if win else false
     */
    public boolean graduate(Avatar avatar){
	if ( avatar.getHP() >= pointsToGraduate ){
	    return true;
	}
	return false;
    }
}
