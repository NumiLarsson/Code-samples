/**
 * @file Gameplay.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Game engien handles all the io
 */
import java.util.*;

public class Gameplay {
    
    private static final int POINTS_TO_END = 180;
    
    private static World gameMap;
    private static Avatar user;
    
    public static void main (String args[]){
	boolean ready = true;
	String err = "\nERROR: ";
	try {
	    gameMap = new World();
	    Course[] finishedCourses = gameMap.returnFirst6();
	    user = new Avatar(finishedCourses, 10, 60);
	} catch(Exception e) {
	    err += e.getMessage();
	    ready = false;
	}
	Scanner scan = new Scanner(System.in);
	if (ready){
	    System.out.println("\n\n\t----Welcome To POLLAX MUD!-----");

	    boolean keepPlaying = true;
	    String input;
	    while (keepPlaying) {
		System.out.println("\n\nYou hare in " + gameMap.getCurrentRoom().getName() + ".");
		System.out.println("What do you want to do? (Type help for command list)");
		
		input = scan.nextLine().toUpperCase();
	      	
		int i = input.indexOf(" ");
		String st;
		if (i > 0) {
		    st = input.substring(0,i);
		} else {
		    st = input;
		}
	
		try {
		    Action action = Action.valueOf(st);
		    keepPlaying = !makeAction(action, input);
		} catch(Exception e) {
		    System.out.println("Illigal input! Try again");
		}
	    }
	} else {
	    System.out.println(err);
	}
	scan.close();
	if (ready) {
	    System.out.println("\nThx for playing! See you next time!\n");
	}
    }

    /**
     * Check what action user want and take action
     * @param action Action to take
     * @param input User input
     * @return Bolean if exit
     */
    public static boolean  makeAction(Action action, String input) {
	boolean exit = false;
	switch (action) {
	case HELP:
	    showHelp();
	    break;
	case GO:
	    move(input);
	    break;
	case LIST:
	    list();
	    break;
	case USE:
	    use(input);
	    break;
	case PICK:
	    pick(input);
	    break;
	case DROP:
	    drop(input);
	    break;
	case INVENTORY:
	    inventory();
	    break;
	case ENROLL:
	    enroll(input);
	    break;
	case TALK:
	    talk(input);
	    break;
	case GRADUATE:
	    graduate();
	    break;
	case EXIT:
	    exit = true;
	    break;
	default:
	    break;
	}
	return exit;
    }

    /**
     * Action to move to a new room If possibole
     * @param s User input
     */
    public static void move(String s) {
	int i = s.indexOf(" ");
	if (i > 0) {
	    String st = s.substring(i+1, s.length()).toLowerCase();
	    if (st.length() > 2) {
		gameMap.moveToRoom(st);	
	    }
	} else {
	    gameMap.listAdjacentRooms();
	}
	
    }

    /**
     * List all items and creatures in the room
     */
    public static void list() {
	gameMap.listRoomCreaturesAndItems();
	
    }

    /**
     * Use a key to unlock the door
     * @param s User input
     */
    public static void use(String s) {
	int i = getIndex(s);
	if (i > 0) {
	    String st = s.substring(i+1, s.length()).toLowerCase();
	    if (gameMap.isDoorLocked(st)) {
		System.out.println("Door was not locked!");
		gameMap.moveToRoom(st);
	    } else if (user.userHasKey()) {
		boolean suc = gameMap.useKeyToRoom(st);
		if (suc) user.removeKey();
	    } else {
		System.out.println("You do not have any keys! Go pick some up first.");
	    }
	    
	} else {
	    throw new IllegalArgumentException();
	}
	
    }

    /**
     * Method to start the pick up item procedure. 
     * @param s User input
     */
    public static void pick(String s) {
	int padding = s.indexOf(" ") + 1;
	int i = s.indexOf(" ", padding);
	if (i > 0) {
	    String st = s.substring(i+1, s.length()).toLowerCase();
	    Item item = gameMap.getItemFromRoom(st);
	    if (item != null) {
		boolean ad = user.addToBackpack(item);
		if (ad) {
		    System.out.println(item.getName() + " added to backpack!");
		} else {
		    System.out.println("Bag is full or trying to pick up a duplicate!");
		}
	    } else {
		System.out.println("There is no item here named: " + st);
	    }
	}
    }

    /**
     * Method to drop an item from user backpack
     * @param s User input
     */
    public static void drop(String s) {
	int i = s.indexOf(" ") + 1;
	if (i > 0) {
	    String st = s.substring(i, s.length()).toLowerCase();
	    Item drop = user.dropItemFromBackpack(st);
	    if (drop != null) {
		gameMap.addItemToRoom(drop);
		System.out.println("Dropping item: " + st);
	    } else {
		System.out.println("There is no item named: " + st);
	    }
	}
    }

    /**
     * Print user inventory
     */
    public static void inventory() {
	user.printBackpack();
    }

    /**
     * Enroll to a course
     * @param s User input
     */
    public static void enroll(String s) {
	int i = s.indexOf(" ") + 1;
	if (i > 0) {
	    String st = s.substring(i, s.length()).toLowerCase();
	    Creature c = gameMap.getCreatureInRoom(st, true);
	    if (c != null) {
		if (c instanceof Teacher) {
		    Teacher t = (Teacher)c;
		    Course cours = t.getCourse();
		    boolean result = user.addNewCourse(cours);
		    if (result) {
			System.out.println("Enrolled in course: " + cours.getName());		  
		    } else {
			System.out.println("Teacher got to many students try again!");
		    }

		} else {
		    System.out.println("There is no course with that name here!");
		}
	    } else {
		System.out.println("There is no teacher in this room!");
	    }
	}
    }

    /**
     * Method to talk to the diffrent creatures in a room
     * @param s User input
     */
    public static void talk(String s) {
	int i = s.indexOf(" ") + 1;
	if (i > 0) {
	    String st = s.substring(i, s.length()).toLowerCase();
	    Creature c = gameMap.getCreatureInRoom(st, false);
	    if (c != null) {
		if (c instanceof Teacher) {
		    Teacher t = (Teacher)c;
		    teacherActions(t);
		} else if (c instanceof Student) {
		    System.out.println("Your talking to " + c.getName());
		    Student stud = (Student)c;
		    if (user.hasBook(stud.getActiveCourse())) {
			Book b = user.getBookFromBag(stud.getActiveCourse());
			if (b != null) {
			    System.out.println("It seems like you have something in common!");
			    System.out.println("You can trade " + b + " for either " + stud.getBookName() + " or for the answer to course: " + stud.getActiveCourse().getName());
			    System.out.println("Use trade book or trade answer or quit to stop talking to " + stud.getName());
			    studentActions(stud);
			}
		    } else {
			System.out.println("You do not have anything in common with this student yet!");
		    }
		}
	    } else if(gameMap.inSfinxRoom()) {
		gameMap.printSfinxWord();
	    } else {
		System.out.println("No creature named: " + st + " in this room!");
	    }
	}
    }

    /**
     * Method to talk to a teacher
     * @param t User input
     */
    public static void teacherActions(Teacher t) {
	Course cours = t.getCourse();
	boolean finished = user.isFinishedCourse(cours);
	if (user.userHasCourse(cours)) {
	    System.out.println("You'r not enrolled in course: " + cours.getName() + "\nUse enroll [course name to enroll]");
	} else {
	    Random rand = new Random();
	    int fiftyFifty = rand.nextInt(4);
	    boolean hasBook = user.hasBook(cours);
	    if (finished) {
		System.out.println("You have already finished: " + cours.getName());  
		if (fiftyFifty < 2) {
		    System.out.println("Ops Looks like you have to answer a new question!");
		    boolean question = t.askQuestion(hasBook);
		    if (!question) {
			user.removeFinishedCourse(cours);
		    }		    
		}
	    } else {
		if (fiftyFifty < 3) {
		    System.out.println("Get the answer right and your done with course: " + cours.getName());
		    boolean question = t.askQuestion(hasBook);
		    if (question) {
			user.finishCourse(cours);
		    }
		} else {
		    System.out.println("Whoops no question today! try again later!");
		}
	    }   
	}
    }

    /**
     * Helper funtion to trade with student
     * @param stud Student to trade with
     */
    public static void studentActions(Student stud) {
	boolean gotItRight = true;
	Scanner scan = new Scanner(System.in);
	while (gotItRight) {
	    String input = scan.nextLine().toUpperCase();
	    if (input.equals("TRADE BOOK") || input.equals("TRADE ANSWER")) {
		trade(input, stud);
		gotItRight = false;
	    } else if (input.equals("QUIT")) {
		gotItRight = false;
	    } else {
		System.out.println("Wrong input sir!");
	    }
	   
	}

    }

    /**
     * Trade with a student main function
     * @param s User input
     * @param stud Student
     */
    public static void trade(String s , Student stud) {
	Item item = user.dropItemFromBackpack(stud.getBookName());
	if (item != null || item instanceof Book) {
	    if (s.equals("TRADE BOOK")) {
		Book trade = stud.tradeBook((Book)item);
		user.addToBackpack(trade);
	    } else if (s.equals("TRADE ANSWER")) {
		int answer = stud.tradeForAnswer((Book)item);
		System.out.println("Answer for course " + stud.getCompleteCourse().getName() + " is: " + answer);
	    }
	}
    }

    /**
     * Method to graduate
     */
    public static void graduate() {
	if(gameMap.inSfinxRoom()) {
	    if (user.getHP() >= POINTS_TO_END) {
		System.out.println("You have graduated!");
		user.printFinishedCourses();
	    }	  
	} else {
	    System.out.println("You'r not in the same room as sfinxen!");
	}
    }

    /**
     * Print all avible commands
     */
    public static void showHelp() {
	System.out.println("\n\tAvailable Commands:");
	System.out.println("\ngo [direction]           - Move to direction ex: go west");
	System.out.println("go                       - Show possibole moving directions");
	System.out.println("list                     - List creatures and items in room");
	System.out.println("use key with [direction] - Open door to direction");
	System.out.println("pick up [name]           - Pick up item with name");
	System.out.println("drop [name]              - Drop item with name");
	System.out.println("inventory                - Show your invetory");
	System.out.println("enroll [name]            - Enroll to course with name");
	System.out.println("talk [name]              - Talk to creature with name");
	System.out.println("trade [name]             - trade with student");
	System.out.println("graduate                 - can only be used at sfixen to complete game");
	System.out.println("exit                     - Quit game");
    }
    private static int getIndex(String s) {
	int i = s.lastIndexOf(" ");
	if (i > 0) {
	    return i;
	} else {
	    throw new IllegalArgumentException();
	}
    }
    
    /**
     * Enum for diffrent io actions
     */
    public static enum Action {
        HELP, GO, LIST, USE, PICK, DROP, INVENTORY, ENROLL, TALK, GRADUATE, EXIT
    }



    
}
